package kelvin.fiveminsurvival.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import kelvin.fiveminsurvival.main.resources.Resources;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class AnimalWatcherEntity extends ZombieEntity {
	protected boolean is_destroying_block;
    protected int destroy_block_x;
    protected int destroy_block_y;
    protected int destroy_block_z;
    protected int destroy_block_progress;
    protected int destroy_block_cooloff = 40;
    protected int destroy_pause_ticks;
    
    public World worldObj;
    
    public int RECENTLY_HIT;
    public Random RAND;
    
    public AnimalWatcherEntity(EntityType<? extends ZombieEntity> type, World worldIn) {
        super(type, worldIn);
        this.goalSelector.addGoal(1, new EntityAIWatchAnimal(this));
		this.worldObj = worldIn;
		this.RAND = rand;
     }
    
	public AnimalWatcherEntity(World worldIn) {
		super(worldIn);
		this.goalSelector.addGoal(1, new EntityAIWatchAnimal(this));
		this.worldObj = worldIn;
		this.RAND = rand;
	}
	
	 protected void applyEntityAI() {
	      this.goalSelector.addGoal(2, new ZombieAttackGoal(this, 1.0D, true));
	      this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::isBreakDoorsTaskSet));
	      this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
	      //this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(ZombifiedPiglinEntity.class));
	      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	      this.targetSelector.addGoal(4, new NearestEdibleFoodGoal(this, true));

	      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.TARGET_DRY_BABY));
	   }
	
	public boolean isHoldingItemThatPreventsDigging()
    {
		if (this.getHeldItemMainhand() == null) return false;
        Item held_item = this.getHeldItemMainhand().getItem();
        return held_item instanceof SwordItem;
    }
	
	public boolean isDiggingEnabled()
    {
        return !this.isHoldingItemThatPreventsDigging();
    }
	
	 public boolean blockWillFall(int x, int y, int z)
	    {
	        Block block = this.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock();
	        return block instanceof FallingBlock || block == Blocks.CACTUS || block instanceof TorchBlock || block instanceof WallTorchBlock || block == Blocks.SNOW;
	    }
	 

	    protected void partiallyDestroyBlock()
	    {
	        int x = this.destroy_block_x;
	        int y = this.destroy_block_y;
	        int z = this.destroy_block_z;

	        if (!this.canDestroyBlock(x, y, z, true))
	        {
	            this.cancelBlockDestruction();
	            if (getAttackTarget() != null)
	            	getNavigator().tryMoveToEntityLiving(getAttackTarget(), 1.0);
	        }
	        else
	        {
//	            this.refreshDespawnCounter(-400);
	            World world = this.worldObj;
	            Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

	            Item it = null;
	            if (this.getHeldItemMainhand() != null) {
	            	it = this.getHeldItemMainhand().getItem();
	            }
	            if (block == Blocks.CACTUS && !(it instanceof ToolItem))
	            {
	                this.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	            }

	            if (++this.destroy_block_progress < 10)
	            {
	                this.is_destroying_block = true;
	            }
	            else
	            {
	                this.destroy_block_progress = -1;

	                if (block.getDefaultState().getMaterial() == Material.GLASS)
	                {
	                    world.playEvent(null, 2001, new BlockPos(x, y, z), Block.getStateId(world.getBlockState(new BlockPos(x, y, z))));
	                }

	                world.removeBlock(new BlockPos(x, y, z), false);
	                if (getAttackTarget() != null)
		            	getNavigator().tryMoveToEntityLiving(getAttackTarget(), 1.0);
	                if (this.blockWillFall(x, y + 1, z))
	                {
	                    List<LivingEntity> item_stack = world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().expand(3.0D, 1.0D, 3.0D));

						for (LivingEntity entity_living : item_stack) {
							//	                        EntityAIAttackOnCollide ai = (EntityAIAttackOnCollide)entity_living.getEntityAITask(EntityAIAttackOnCollide.class);
//
//	                        if (ai != null)
//	                        {
//	                            if (ai.ticks_suppressed < 10)
//	                            {
//	                                ai.ticks_suppressed = 10;
//	                            }
//
//	                            if (ai.attackTick < 10)
//	                            {
//	                                ai.attackTick = 10;
//	                            }
//	                        }
						}
	                }

	                ItemStack var11 = this.getHeldItemMainhand();

	                if (var11 != null)
	                {
	                    var11.getItem().onBlockDestroyed(var11, this.world, world.getBlockState(new BlockPos(x, y, z)), new BlockPos(x, y, z), this);
	                }

	                this.is_destroying_block = false;
	                Block var12 = world.getBlockState(new BlockPos(x, y + 1, z)).getBlock();

	                if (var12 instanceof FallingBlock)
	                {
	                    this.is_destroying_block = true;
	                    this.destroy_pause_ticks = 10;
	                }
	                else if (var12 != null && !this.blockWillFall(x, y + 1, z))
	                {
	                    if (y == this.getFootBlockPosY() && this.canDestroyBlock(x, y + 1, z, true))
	                    {
	                        ++this.destroy_block_y;
	                    }
	                    else
	                    {
	                        --this.destroy_block_y;
	                    }

	                    this.is_destroying_block = true;
	                    this.destroy_pause_ticks = 10;
	                }
	                else if (y == this.getFootBlockPosY() + 1 && !world.getBlockState(new BlockPos(this.getPosition().getX(), this.getPosition().getY() + 2, this.getPosition().getZ())).getMaterial().blocksMovement() && this.canDestroyBlock(x, y - 1, z, true))
	                {
	                    this.is_destroying_block = true;
	                    this.destroy_pause_ticks = 10;
	                    --this.destroy_block_y;
	                }

//	                if (var12 instanceof FallingBlock)
//	                {
//	                    ((FallingBlock)var12).fall(world, x, y + 1, z);
//	                }
	                this.cancelBlockDestruction();
	            }

	            watchAnimal(this.getEntityId(), x, y, z, this.destroy_block_progress, this.getEntityWorld());

	            if (block.getDefaultState().getMaterial() == Material.GLASS)
	            {
	                world.playSound((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, Blocks.GLASS.getDefaultState().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, Blocks.GLASS.getDefaultState().getSoundType().getVolume() + 2.0F, Blocks.GLASS.getDefaultState().getSoundType().getPitch() * 1.0F, false);
	            }
	            else
	            {
                    world.playEvent(null, 2001, new BlockPos(x, y, z), Block.getStateId(world.getBlockState(new BlockPos(x, y, z))));

	            }
	        }
	    }
	    
	    public void watchAnimal(int par1, int par2, int par3, int par4, int par5, World world)
	    {
	    	world.sendBlockBreakProgress(par1, new BlockPos(par2, par3, par4), par5);
	    }

	    protected double getCenterPosYForBlockDestroying()
	    {
	        return this.getPosY() + (double)(this.getHeight() * 0.5F);
	    }

	    protected Vector3d getEyePosForBlockDestroying()
	    {
	        return this.getPrimaryPointOfAttack();
	    }
	    
	    public Vector3d getPrimaryPointOfAttack()
	    {
	        return this.getPositionVec().add(0, this.getHeight() * 0.75F, 0);
	    }
	    public double getBlockPosX() {
	    	return this.getPosition().getX();
	    }
	    public double getBlockPosY() {
	    	return this.getPosition().getY();
	    }
	    public double getBlockPosZ() {
	    	return this.getPosition().getZ();
	    }
	    public boolean canSeeTarget(boolean ignore_leaves)
	    {
	        return this.getEntitySenses().canSee(this.getAttackTarget());
	    }

	    protected Vector3d getAttackerLegPosForBlockDestroying()
	    {
	        return new Vector3d(this.getPosX(), this.getPosY() + (double)(this.getHeight() * 0.25F), this.getPosZ());
	    }

	    protected Vector3d getTargetEntityCenterPosForBlockDestroying(LivingEntity entity_living_base)
	    {
	        return new Vector3d(entity_living_base.getPosX(), entity_living_base.getPosY() + (double)(entity_living_base.getHeight() / 2.0F), entity_living_base.getPosZ());
	    }

	    
	    private boolean hasDownwardsDiggingTool()
	    {
	        ItemStack held_item = this.getHeldItemMainhand();
	        return held_item != null && held_item.getItem() instanceof ShovelItem;
	    }

	    private boolean isBlockClaimedByAnother(int x, int y, int z)
	    {
	        AxisAlignedBB bb = new AxisAlignedBB(this.getPosX() - 4.0D, this.getPosY() - 4.0D, this.getPosZ() - 4.0D, this.getPosX() + 4.0D, this.getPosY() + 4.0D, this.getPosZ() + 4.0D);
	        List<Entity> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, bb);

			for (Entity entity : entities) {
				if (entity instanceof AnimalWatcherEntity) {
					AnimalWatcherEntity digger = (AnimalWatcherEntity) entity;

					if (digger.is_destroying_block && digger.destroy_block_x == x && digger.destroy_block_y == y && digger.destroy_block_z == z) {
						return true;
					}
				}
			}

	        return false;
	    }
	    
	    public static float getDistanceFromDeltas(double dx, double dy, double dz)
	    {
	        return MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
	    }

	    public static double getDistanceSqFromDeltas(double dx, double dy, double dz)
	    {
	        return dx * dx + dy * dy + dz * dz;
	    }

	    public static double getDistanceSqFromDeltas(float dx, float dy, float dz)
	    {
	        return dx * dx + dy * dy + dz * dz;
	    }

	    public static double getDistanceSqFromDeltas(double dx, double dz)
	    {
	        return dx * dx + dz * dz;
	    }

	    public static double getDistanceFromDeltas(double dx, double dz)
	    {
	        return MathHelper.sqrt(getDistanceSqFromDeltas(dx, dz));
	    }
	    protected boolean canDestroyBlock(int x, int y, int z, boolean check_clipping)
	    {
	    	if (isBlockClaimedByAnother(x, y, z)) {

	    		return false;
	    		}
	    	if (this.getAttackTarget() != null) {
	    		boolean canBeSeen = false;
	    		if (canBeSeen == false) {
	    			
	    			Vector3d vector3d = new Vector3d(this.getPosX(), this.getPosYEye(), this.getPosZ());
		    	      Vector3d vector3d1 = new Vector3d(getAttackTarget().getPosX(), getAttackTarget().getPosYEye(), getAttackTarget().getPosZ());
		    	      canBeSeen = this.world.rayTraceBlocks(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this)).getType() == RayTraceResult.Type.MISS;
	    		}
    			Vector3d vector3d = new Vector3d(this.getPosX(), this.getPosYEye(), this.getPosZ());
	    	    Vector3d vector3d1 = new Vector3d(getAttackTarget().getPosX(), getAttackTarget().getPosY(), getAttackTarget().getPosZ());
	    	    canBeSeen = this.world.rayTraceBlocks(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this)).getType() == RayTraceResult.Type.MISS;
	    		
	    	    if (canBeSeen) {
	    	    	
		    		this.is_destroying_block = false;
		    		return false;
		    	}
	    	}
	    	
	        if (this.isHoldingItemThatPreventsDigging())
	        {
	        	
	            return false;
	        }
	        else
	        {
	            int foot_y = this.getFootBlockPosY();

	            if (y < foot_y && !this.hasDownwardsDiggingTool())
	            {
	            	
	                return false;
	            }
	            else if (y > foot_y + 1)
	            {
	            	
	                return false;
	            }
	            else
	            {
	                World world = this.worldObj;

	                if (AnimalWatcherEntity.getDistanceSqFromDeltas(this.getPosX() - (double)((float)x + 0.5F), this.getCenterPosYForBlockDestroying() - (double)((float)y + 0.5F), this.getPosZ() - (double)((float)z + 0.5F)) > 4.5D)
	                {
	                    return false;
	                }
	                else
	                {
	                    if (check_clipping)
	                    {
	                    	
//	                        BlockRayTraceResult block = world.rayTraceBlocks(new RayTraceContext(this.getEyePosForBlockDestroying(), this.getLookVec(), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
//	                        if (block != null && (block.getType() == RayTraceResult.Type.ENTITY || block.getType() == RayTraceResult.Type.BLOCK && (block.getPos().getX() != x || block.getPos().getY() != y || block.getPos().getZ() != z)))
//	                        {
//	                            block = world.rayTraceBlocks(new RayTraceContext(this.getAttackerLegPosForBlockDestroying(), this.getLookVec(), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
//	                            
//		                        if (block != null && (block.getType() == RayTraceResult.Type.ENTITY || block.getType() == RayTraceResult.Type.BLOCK && (block.getPos().getX() != x || block.getPos().getY() != y || block.getPos().getZ() != z)))
//	                            {
//		                        	
//	                                return false;
//	                            }
//	                        }
	                    }

	                    Block block1 = world.getBlockState(new BlockPos(x, y, z)).getBlock();

	                    if (block1 == null)
	                    {
	                        return false;
	                    }
	                    else if (block1.getDefaultState() == Blocks.AIR.getDefaultState()) {
	                    	return false;
	                    }
	                    else if (block1.getDefaultState().getMaterial().isLiquid())
	                    {
	                        return false;
	                    }
	                    else
	                    {
//	                        int metadata = world.getBlockMetadata(x, y, z);
//
//	                        if (this instanceof EntityEarthElemental)
//	                        {
//	                            EntityEarthElemental held_item = (EntityEarthElemental)this;
//
//	                            if (block1.getMinHarvestLevel(metadata) <= held_item.getBlockHarvestLevel())
//	                            {
//	                                return true;
//	                            }
//	                        }

//	                        Item held_item1 = this.getHeldItemMainhand() == null ? null : this.getHeldItemMainhand().getItem();
//	                        boolean has_effective_tool = held_item1 instanceof ToolItem && ((ToolItem)held_item1).getStrVsBlock(block1, metadata) > 0.0F;
//	                        return block1.blockMaterial.requiresTool(block1, metadata) && (!this.isFrenzied() || block1.getMinHarvestLevel(metadata) >= 2) && !has_effective_tool && block1 != Block.sand && block1 != Block.dirt && block1 != Block.grass && block1 != Block.gravel && block1 != Block.blockSnow && block1 != Block.tilledField && block1 != Block.blockClay && block1 != Block.leaves && block1 != Block.cloth && (block1 != Block.cactus || !this.isEntityInvulnerable() && !this.isEntityUndead()) && block1 != Block.sponge && !(block1 instanceof BlockPumpkin) && !(block1 instanceof BlockMelon) && block1 != Block.mycelium && block1 != Block.hay && block1 != Block.thinGlass ? false : !this.isBlockClaimedByAnother(x, y, z);
	                    	if (block1.getDefaultState().getMaterial() != Material.ROCK)
	                    		if (block1.getDefaultState().getMaterial() != Material.IRON)
	                    			if (!(block1.getDefaultState().getBlock() instanceof RotatedPillarBlock)) {
	                    				
	                    				return true;
	                    			} else {
	                    				if (this.getHeldItemMainhand() != null) {
	        	                    		Item held_item1 = this.getHeldItemMainhand().getItem();
	        	                    		if (held_item1 instanceof PickaxeItem) {
	        	                    			if (block1.getDefaultState().getMaterial() == Material.ROCK || block1.getDefaultState().getMaterial() == Material.IRON)
	        	                    				return true;
	        	                    		}
	        	                    		if (held_item1 instanceof AxeItem) {
												return block1.getDefaultState().getBlock() instanceof RotatedPillarBlock;
	        	                    		}
	        	                    	}
	                    			}
	                    	
	                    	
	                    	return false;
	                    }
	                }
	            }
	        }
	    }
	    

	    protected boolean setBlockToDig(int x, int y, int z, boolean check_clipping)
	    {
	        if (!this.canDestroyBlock(x, y, z, check_clipping))
	        {
	            return false;
	        }
	        else
	        {
	            this.is_destroying_block = true;
	            if (x == this.destroy_block_x && y == this.destroy_block_y && z == this.destroy_block_z)
	            {
	                return true;
	            }
	            else
	            {
	                if (y == this.getFootBlockPosY() + 1 && this.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.CACTUS && this.canDestroyBlock(x, y - 1, z, check_clipping))
	                {
	                    --y;
	                }
	                this.destroy_block_progress = -1;
	                this.destroy_block_x = x;
	                this.destroy_block_y = y;
	                this.destroy_block_z = z;
	                return true;
	            }
	        }
	    }
	    
	    public int getFootBlockPosX() {
	    	return this.getPosition().getX();
	    }
	    public int getFootBlockPosY() {
	    	return this.getPosition().getY();
	    }
	    public int getFootBlockPosZ() {
	    	return this.getPosition().getZ();
	    }

	    public void cancelBlockDestruction()
	    {
	        if (this.is_destroying_block)
	        {
	        	
	            watchAnimal(this.getEntityId(), this.destroy_block_x, this.destroy_block_y, this.destroy_block_z, -1, this.world);
	            this.is_destroying_block = false;
	            this.destroy_block_progress = -1;
	            this.destroy_block_cooloff = 40;
	        }
	    }

	    public boolean isFrenzied() {
	    	return false;
	    }
	    
	    protected int getCooloffForBlock()
	    {
	        Block block = this.worldObj.getBlockState(new BlockPos(this.destroy_block_x, this.destroy_block_y, this.destroy_block_z)).getBlock();

	        if (block == null)
	        {
	            return 40;
	        }
	        else
	        {
	            int cooloff = (int)(300.0F * this.worldObj.getBlockState(new BlockPos(this.destroy_block_x, this.destroy_block_y, this.destroy_block_z)).getBlockHardness(this.world, new BlockPos(this.destroy_block_x, this.destroy_block_y, this.destroy_block_z)));
	            
	            if (this.isFrenzied())
	            {
	                cooloff /= 2;
	            }

//	            if (this instanceof EntityEarthElemental)
//	            {
//	                EntityEarthElemental held_item = (EntityEarthElemental)this;
//
//	                if (held_item.isNormalClay())
//	                {
//	                    cooloff /= 4;
//	                }
//	                else if (held_item.isHardenedClay())
//	                {
//	                    cooloff /= 6;
//	                }
//	                else
//	                {
//	                    cooloff /= 8;
//	                }
//	            }

	            if (this.getHeldItemMainhand() == null)
	            {
	                return cooloff;
	            }
	            else
	            {
	                Item held_item1 = this.getHeldItemMainhand().getItem();

	                if (held_item1 instanceof ToolItem)
	                {
	                    ToolItem item_tool = (ToolItem)held_item1;
	                    cooloff = 1;
	                }

	                return cooloff;
	            }
	        }
	    }
	    public final int getTicksExistedWithOffset()
	    {
	        return this.ticksExisted + this.getEntityId() * 47;
	    }
	    
	    public ItemEntity attackFood;
	    /**
	     * Called to update the entity's position/logic.
	     */
	    public void tick()
	    {
	    	
	    	if (this.getAttackTarget() == null) {
	    		this.setAttackTarget(world.getClosestPlayer(getPosX(), getPosY(), getPosZ(), 64, false));
	    	}
	    	if (attackFood != null) {
	    		if (this.getAttackTarget() == null) {
	    			this.getNavigator().tryMoveToXYZ(attackFood.getPositionVec().getX(), attackFood.getPositionVec().getY(), attackFood.getPositionVec().getZ(), 1.0);
	    			if (this.getPositionVec().distanceTo(attackFood.getPositionVec()) < 1.5) {
	    				attackFood.remove();
	    			}
	    		}
	    	}
	    	this.RECENTLY_HIT = recentlyHit;
	        if (this.is_destroying_block)
	        {
	            if (this.destroy_pause_ticks == 0)
	            {
	                this.getLookController().setLookPosition((float)this.destroy_block_x + 0.5F, (float)this.destroy_block_y + 0.5F, (float)this.destroy_block_z + 0.5F, 10.0F, (float)this.getVerticalFaceSpeed());

	                if (!this.canDestroyBlock(this.destroy_block_x, this.destroy_block_y, this.destroy_block_z, true))
	                {
	                    this.cancelBlockDestruction();
	                }
	            }
	        }
	        else
	        {
	            this.destroy_block_cooloff = 40;
	            this.destroy_block_progress = -1;
	        }

	        super.tick();
	    }

	    /**
	     * (abstract) Protected helper method to write subclass entity data to NBT.
	     */
	    public void writeAdditional(CompoundNBT par1NBTTagCompound)
	    {
	        super.writeAdditional(par1NBTTagCompound);

	        if (this.is_destroying_block)
	        {
	            par1NBTTagCompound.putBoolean("is_destroying_block", this.is_destroying_block);
	            par1NBTTagCompound.putInt("destroy_block_x", this.destroy_block_x);
	            par1NBTTagCompound.putInt("destroy_block_y", this.destroy_block_y);
	            par1NBTTagCompound.putInt("destroy_block_z", this.destroy_block_z);
	            par1NBTTagCompound.putInt("destroy_block_progress", this.destroy_block_progress);
	            par1NBTTagCompound.putInt("destroy_block_cooloff", this.destroy_block_cooloff);
	        }
	    }

	    /**
	     * (abstract) Protected helper method to read subclass entity data from NBT.
	     */
	    public void readAdditional(CompoundNBT par1NBTTagCompound)
	    {
	        super.readAdditional(par1NBTTagCompound);

	        if (par1NBTTagCompound.contains("is_destroying_block"))
	        {
	            this.is_destroying_block = par1NBTTagCompound.getBoolean("is_destroying_block");
	            this.destroy_block_x = par1NBTTagCompound.getInt("destroy_block_x");
	            this.destroy_block_y = par1NBTTagCompound.getInt("destroy_block_y");
	            this.destroy_block_z = par1NBTTagCompound.getInt("destroy_block_z");
	            this.destroy_block_progress = par1NBTTagCompound.getInt("destroy_block_progress");
	            this.destroy_block_cooloff = par1NBTTagCompound.getInt("destroy_block_cooloff");
	        }
	    }

	    /**
	     * Called when the mob's health reaches 0.
	     */
	    public void onDeath(DamageSource par1DamageSource)
	    {
	        this.cancelBlockDestruction();
	        super.onDeath(par1DamageSource);
	    }

		public float getDistanceToEntity(LivingEntity target) {
			return (float) getPositionVec().distanceTo(target.getPositionVec());
		}

		public boolean isHoldingAnEffectiveTool(Block blockHit) {
			Material mat = blockHit.getDefaultState().getMaterial();
			if (this.getHeldItemMainhand() != null) {
				Item i = this.getHeldItemMainhand().getItem();
				if (mat == Material.EARTH || mat == Material.SAND) {
					if (i instanceof ShovelItem) return true;
				}
				if (mat == Material.ROCK || mat == Material.IRON) {
					if (!(i instanceof PickaxeItem)) return false;
				}
				if (mat == Material.WOOD) {
					return i instanceof AxeItem;
				}
			}
			return true;
		}

		public boolean hasLineOfStrike(Vector3d target_pos)
	    {
			
			List<Vector3d> target_points = new ArrayList<>();
	        target_points.add(getPositionVec());
	        target_points.add(new Vector3d(getPositionVec().x, getPositionVec().y + getHeight() * 0.5, getPositionVec().z));
	        target_points.add(new Vector3d(getPositionVec().x, getPositionVec().y + getHeight() * 0.75, getPositionVec().z));
	        
	        Iterator<Vector3d> i = target_points.iterator();

	        do
	        {
	            if (!i.hasNext())
	            {
	                return false;
	            }
	        }
	        while (!Resources.getBlockCollisionForPhysicalReach(i.next(), target_pos, this.getEntityWorld()).isBlock());

	        return true;
	    }

		
		public boolean hasLineOfStrike(Entity target)
	    {
	        List<Vector3d> target_points = new ArrayList<>();
	        target_points.add(target.getPositionVec());
	        target_points.add(new Vector3d(target.getPositionVec().x, target.getPositionVec().y + target.getHeight() * 0.5, target.getPositionVec().z));
	        target_points.add(new Vector3d(target.getPositionVec().x, target.getPositionVec().y + target.getHeight() * 0.75, target.getPositionVec().z));

			for (Vector3d target_point : target_points) {
				if (this.hasLineOfStrike(target_point)) {
					return true;
				}
			}

			return false;
	    }

	    public boolean isTargetWithinStrikingDistance(LivingEntity target)
	    {
	        if (this.isAIDisabled())
	        {
	            return false;
	        }
	        else
	        {
	            return this.getPositionVec().distanceTo(new Vector3d(target.getPosX(), target.getBoundingBox().minY, target.getPosZ())) <= 1.5;
	        }
	    }
		
		public boolean hasLineOfStrikeAndTargetIsWithinStrikingDistance(LivingEntity target)
	    {
	        return this.isTargetWithinStrikingDistance(target) && this.hasLineOfStrike(target);
	    }
}
