package kelvin.mite.mixin.entity;

import kelvin.mite.entity.AnimalWatcherEntity;
import kelvin.mite.entity.goal.EntityAIWatchAnimal;
import kelvin.mite.entity.goal.FoodTargetGoal;
import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.MoonHelper;
import kelvin.mite.main.resources.Resources;
import net.minecraft.block.*;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity implements AnimalWatcherEntity {
	
	protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	protected boolean is_destroying_block;
	protected int destroy_block_x;
	protected int destroy_block_y;
	protected int destroy_block_z;
	protected int destroy_block_progress;
	protected int destroy_block_cooloff = 40;
	protected int destroy_pause_ticks;

	public int RECENTLY_HIT;
	public Random RAND;

	@Inject(at=@At("HEAD"), method="initCustomGoals")
	protected void initCustomGoals(CallbackInfo info) {
		this.goalSelector.add(1, new EntityAIWatchAnimal((ZombieEntity)(Object)this));
		this.targetSelector.add(3, new ActiveTargetGoal(this, AnimalEntity.class, true));
		FoodTargetGoal foodTargetGoal = new FoodTargetGoal(this, true);
		foodTargetGoal.onlyEatsMeat = true;
		this.targetSelector.add(4, foodTargetGoal);
		this.RAND = random;
	}

	public ItemEntity attackFood;
	@Inject(at = @At("HEAD"), method = "tick")
	public void tick(CallbackInfo info) {
		if (this.getTarget() == null) {
			this.setTarget(world.getClosestPlayer(getX(), getY(), getZ(), 64, false));
		}
		if (attackFood != null) {
			if (this.getTarget() == null) {
				this.getNavigation().startMovingTo(attackFood.getPos().getX(), attackFood.getPos().getY(), attackFood.getPos().getZ(), 1.0);
				if (this.getPos().distanceTo(attackFood.getPos()) < 1.5) {
					attackFood.kill();
				}
			}
		}
		this.RECENTLY_HIT = this.getLastAttackedTime() - this.age;
		if (this.is_destroying_block)
		{
			if (this.destroy_pause_ticks == 0)
			{
				//this.lookControl.lookAt((float)this.destroy_block_x + 0.5F, (float)this.destroy_block_y + 0.5F, (float)this.destroy_block_z + 0.5F, 10.0F, (float)this.getMaxLookPitchChange());
				this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, new Vec3d((float)this.destroy_block_x + 0.5F, (float)this.destroy_block_y + 0.5F, (float)this.destroy_block_z + 0.5F));


				if (!this.canDestroyBlock(this.destroy_block_x, this.destroy_block_y, this.destroy_block_z, true))
				{
					this.cancelBlockDestruction();
				} else {
					isBlockClaimedByAnother(destroy_block_x, destroy_block_y, destroy_block_z);
				}
			}
		}
		else
		{
			this.destroy_block_cooloff = 40;
			this.destroy_block_progress = -1;
		}

	}

	public float getMovementSpeed() {
		return is_destroying_block ? 0 : super.getMovementSpeed();
	}

	public void onDeath(DamageSource par1DamageSource)
	{
		this.cancelBlockDestruction();
		super.onDeath(par1DamageSource);
	}




	public boolean isHoldingItemThatPreventsDigging() {
		if (this.getMainHandStack() == null) return false;
		Item held_item = this.getMainHandStack().getItem();
		return held_item instanceof SwordItem;
	}

	public boolean isDiggingEnabled() {
		return !this.isHoldingItemThatPreventsDigging();
	}


	public int recentlyHit() {
		return RECENTLY_HIT;
	}

	public int getDestroyBlockZ() {
		return destroy_block_z;
	}
	public int getDestroyBlockX() {
		return destroy_block_x;
	}
	public int getDestroyBlockY() {
		return destroy_block_y;
	}

	public int getDestroyBlockProgress() {
		return destroy_block_progress;
	}

	public boolean isBlockClaimedByAnother(int x, int y, int z)
	{
		Box bb = new Box(this.getX() - 4.0D, this.getY() - 4.0D, this.getZ() - 4.0D, this.getX() + 4.0D, this.getY() + 4.0D, this.getZ() + 4.0D);
		List<Entity> entities = this.world.getOtherEntities(this, bb);

		for (Entity entity : entities) {
			if (entity instanceof AnimalWatcherEntity) {
				AnimalWatcherEntity digger = (AnimalWatcherEntity) entity;

				if (digger.isDestroyingBlock() && digger.getDestroyBlockX() == x && digger.getDestroyBlockY() == y && digger.getDestroyBlockZ() == z) {
					if (digger.getDestroyBlockX() == destroy_block_x &&
					digger.getDestroyBlockY() == destroy_block_y &&
					digger.getDestroyBlockZ() == destroy_block_z &&
					isDestroyingBlock()) {
						if (destroy_block_progress < digger.getDestroyBlockProgress()) {
							destroy_block_progress = digger.getDestroyBlockProgress();
						}
					}
					return true;
				}
			}
		}

		return false;
	}

	@Inject(at=@At("HEAD"), method="writeCustomDataToNbt")
	public void writeCustomDataToNbt(NbtCompound par1NBTTagCompound, CallbackInfo info) {
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

	@Inject(at=@At("HEAD"), method="readCustomDataFromNbt")
	public void readCustomDataFromNBT(NbtCompound par1NBTTagCompound, CallbackInfo info)
	{
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

	public float getDistanceToEntity(LivingEntity target) {
		return (float) getPos().distanceTo(target.getPos());
	}

	private static float getDistanceFromDeltas(double dx, double dy, double dz)
	{
		return MathHelper.sqrt((float) (dx * dx + dy * dy + dz * dz));
	}

	private static double getDistanceSqFromDeltas(double dx, double dy, double dz)
	{
		return dx * dx + dy * dy + dz * dz;
	}

	private static double getDistanceSqFromDeltas(float dx, float dy, float dz)
	{
		return dx * dx + dy * dy + dz * dz;
	}

	private static double getDistanceSqFromDeltas(double dx, double dz)
	{
		return dx * dx + dz * dz;
	}

	private static double getDistanceFromDeltas(double dx, double dz)
	{
		return MathHelper.sqrt((float) getDistanceSqFromDeltas(dx, dz));
	}

	public boolean hasDownwardsDiggingTool() {
		ItemStack held_item = this.getMainHandStack();
		return held_item != null && held_item.getItem() instanceof ShovelItem;
	}

	public double getCenterPosYForBlockDestroying() {
		return this.getY() + (double)(this.getHeight() * 0.5F);
	}

	public Vec3d getLookVec() {
		return new Vec3d(this.getLookControl().getLookX(), this.getLookControl().getLookY(), this.getLookControl().getLookZ());
	}

	public boolean canDestroyBlock(int x, int y, int z, boolean check_clipping) {

		if (this.getTarget() != null) {
			boolean canBeSeen = false;
			if (canBeSeen == false) {

				Vec3d vector3d = new Vec3d(this.getX(), this.getEyeY(), this.getZ());
				Vec3d vector3d1 = new Vec3d(getTarget().getX(), getTarget().getEyeY(), getTarget().getZ());
				canBeSeen = this.world.raycast(new RaycastContext(vector3d, vector3d1, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this)).getType() == HitResult.Type.MISS;
			}
			Vec3d vector3d = new Vec3d(this.getX(), this.getEyeY(), this.getZ());
			Vec3d vector3d1 = new Vec3d(getTarget().getX(), getTarget().getY(), getTarget().getZ());
			canBeSeen = this.world.raycast(new RaycastContext(vector3d, vector3d1, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this)).getType() == HitResult.Type.MISS;

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
			int foot_y = this.getBlockPos().getY();

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
				if (getDistanceSqFromDeltas(this.getX() - (double)((float)x + 0.5F), this.getCenterPosYForBlockDestroying() - (double)((float)y + 0.5F), this.getZ() - (double)((float)z + 0.5F)) > 4.5D)
				{
					return false;
				}
				else
				{
					if (check_clipping)
					{
						/*
						HitResult block = world.raycast(new RaycastContext(getEyePosForBlockDestroying(), getLookVec(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
						if (block != null && (block.getType() == HitResult.Type.ENTITY || block.getType() == HitResult.Type.BLOCK && (block.getPos().getX() != x || block.getPos().getY() != y || block.getPos().getZ() != z))) {
							block = world.raycast(new RaycastContext(getAttackerLegPosForBlockDestroying(), getLookVec(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
							if (block != null && (block.getType() == HitResult.Type.ENTITY || block.getType() == HitResult.Type.BLOCK && (block.getPos().getX() != x || block.getPos().getY() != y || block.getPos().getZ() != z))) {
								return false;
							}
						}
						 */
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

						if (
								block1.getDefaultState().getMaterial() != Material.STONE &&
								block1.getDefaultState().getMaterial() != Material.METAL &&
								!(block1.getDefaultState().getBlock() instanceof PillarBlock)) {
							return true;
						} else {
							if (this.getMainHandStack() != null) {
								Item held_item1 = this.getMainHandStack().getItem();
								if (held_item1 instanceof PickaxeItem) {
									if (block1.getDefaultState().getMaterial() == Material.STONE || block1.getDefaultState().getMaterial() == Material.METAL)
										return true;
								}
								if (held_item1 instanceof AxeItem) {
									return block1.getDefaultState().getBlock() instanceof PillarBlock;
								}
							}
						}

						return false;
					}
				}
			}
		}
	}

	public boolean isDestroyingBlock() {
		return is_destroying_block;
	}

	public boolean setBlockToDig(int x, int y, int z, boolean check_clipping) {
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
				if (y == this.getBlockPos().getY() + 1 && this.world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.CACTUS && this.canDestroyBlock(x, y - 1, z, check_clipping))
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

	public void setDestroyingBlock(boolean b) {
		is_destroying_block = b;
	}

	public Vec3d getEyePosForBlockDestroying() {
		return this.getPrimaryPointOfAttack();
	}

	public Vec3d getPrimaryPointOfAttack()
	{
		return this.getPos().add(0, this.getHeight() * 0.75F, 0);
	}

	public boolean isFrenzied() {
		return MoonHelper.IsBloodMoon(Mite.day_time) && world.isNight();
	}

	public boolean hasLineOfStrike(Vec3d target_pos)
	{

		List<Vec3d> target_points = new ArrayList<>();
		target_points.add(getPos());
		target_points.add(new Vec3d(getPos().x, getPos().y + getHeight() * 0.5, getPos().z));
		target_points.add(new Vec3d(getPos().x, getPos().y + getHeight() * 0.75, getPos().z));

		Iterator<Vec3d> i = target_points.iterator();

		do
		{
			if (!i.hasNext())
			{
				return false;
			}
		}
		while (!Resources.getBlockCollisionForPhysicalReach(i.next(), target_pos, this.getEntityWorld(), this).isBlock());

		return true;
	}


	public boolean hasLineOfStrike(Entity target)
	{
		List<Vec3d> target_points = new ArrayList<>();
		target_points.add(target.getPos());
		target_points.add(new Vec3d(target.getPos().x, target.getPos().y + target.getHeight() * 0.5, target.getPos().z));
		target_points.add(new Vec3d(target.getPos().x, target.getPos().y + target.getHeight() * 0.75, target.getPos().z));

		for (Vec3d target_point : target_points) {
			if (this.hasLineOfStrike(target_point)) {
				return true;
			}
		}

		return false;
	}

	public boolean isTargetWithinStrikingDistance(LivingEntity target)
	{
		if (this.isAiDisabled())
		{
			return false;
		}
		else
		{
			return this.getPos().distanceTo(new Vec3d(target.getX(), target.getBoundingBox().minY, target.getZ())) <= 1.5;
		}
	}

	public boolean hasLineOfStrikeAndTargetIsWithinStrikingDistance(LivingEntity target)
	{
		return this.isTargetWithinStrikingDistance(target) && this.hasLineOfStrike(target);
	}

	public Vec3d getTargetEntityCenterPosForBlockDestroying(LivingEntity entity_living_base) {
		return new Vec3d(entity_living_base.getPos().getX(), entity_living_base.getPos().getY() + (double)(entity_living_base.getHeight() / 2.0F), entity_living_base.getPos().getZ());
	}

	public boolean canSeeTarget(boolean b) {
		return this.canSee(getTarget());
	}

	public boolean isHoldingAnEffectiveTool(Block blockHit) {
		Material mat = blockHit.getDefaultState().getMaterial();
		if (this.getMainHandStack() != null) {
			Item i = this.getMainHandStack().getItem();

			if (mat == Material.SOIL || mat == Material.AGGREGATE) {
				if (i instanceof ShovelItem) return true;
			}
			if (mat == Material.STONE || mat == Material.METAL) {
				if (!(i instanceof PickaxeItem)) return false;
			}
			if (mat == Material.WOOD) {
				return i instanceof AxeItem;
			}
		}
		return true;
	}

	public Vec3d getAttackerLegPosForBlockDestroying() {
		return new Vec3d(this.getX(), this.getY() + (double)(this.getHeight() * 0.25F), this.getZ());
	}

	public boolean blockWillFall(int x, int y, int z) {
		Block block = this.world.getBlockState(new BlockPos(x, y, z)).getBlock();
		return block instanceof FallingBlock || block == Blocks.CACTUS || block instanceof TorchBlock || block instanceof WallTorchBlock || block == Blocks.SNOW;
	}

	public int getDestroyPauseTicks() {
		return this.destroy_pause_ticks;
	}

	public int getTicksExistedWithOffset() {
		return this.age + this.getId() * 47;
	}

	public GoalSelector getGoalSelector() {
		return this.goalSelector;
	}

	public void decrementDestroyPauseTicks() {
		destroy_pause_ticks--;
	}

	public int getDestroyBlockCooloff() {
		return destroy_block_cooloff;
	}

	public int getCooloffForBlock() {
		Block block = this.world.getBlockState(new BlockPos(this.destroy_block_x, this.destroy_block_y, this.destroy_block_z)).getBlock();

		if (block == null)
		{
			return 40;
		}
		else
		{
			int cooloff = (int)(300.0F * this.world.getBlockState(new BlockPos(this.destroy_block_x, this.destroy_block_y, this.destroy_block_z)).getHardness(this.world, new BlockPos(this.destroy_block_x, this.destroy_block_y, this.destroy_block_z)));

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

			if (this.getMainHandStack() == null)
			{
				return cooloff;
			}
			else
			{
				Item held_item1 = this.getMainHandStack().getItem();

				if (held_item1 instanceof ToolItem)
				{
					ToolItem item_tool = (ToolItem)held_item1;
					cooloff = 1;
				}

				return cooloff;
			}
		}
	}

	public void setDestroyBlockCooloff(int cooloffForBlock) {
		destroy_block_cooloff = cooloffForBlock;
	}

	public void partiallyDestroyBlock() {
		int x = this.destroy_block_x;
		int y = this.destroy_block_y;
		int z = this.destroy_block_z;

		if (!this.canDestroyBlock(x, y, z, true))
		{
			this.cancelBlockDestruction();
			if (getTarget() != null)
				getNavigation().startMovingTo(getTarget(), 1.0);
		}
		else
		{
//	            this.refreshDespawnCounter(-400);
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

			Item it = null;
			if (this.getMainHandStack() != null) {
				it = this.getMainHandStack().getItem();
			}
			if (block == Blocks.CACTUS && !(it instanceof ToolItem))
			{
				this.damage(DamageSource.CACTUS, 1.0F);
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
					world.syncGlobalEvent(2001, new BlockPos(x, y, z), Block.getRawIdFromState(world.getBlockState(new BlockPos(x, y, z))));
				}

				world.removeBlock(new BlockPos(x, y, z), false);
				if (getTarget() != null)
					getNavigation().startMovingTo(getTarget(), 1.0);
				if (this.blockWillFall(x, y + 1, z))
				{
					List<LivingEntity> item_stack = world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(3.0D, 1.0D, 3.0D),
							(entity) -> {
								return entity instanceof LivingEntity;
							});

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

				ItemStack var11 = this.getMainHandStack();

				if (var11 != null)
				{
					var11.getItem().postMine(var11, this.world, world.getBlockState(new BlockPos(x, y, z)), new BlockPos(x, y, z), this);
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
					if (y == this.getBlockPos().getY() && this.canDestroyBlock(x, y + 1, z, true))
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
				else if (y == this.getBlockPos().getY() + 1 && !world.getBlockState(new BlockPos(this.getPos().getX(), this.getPos().getY() + 2, this.getPos().getZ())).getMaterial().blocksMovement() && this.canDestroyBlock(x, y - 1, z, true))
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

			destroyBlock(this.getId(), x, y, z, this.destroy_block_progress, this.getEntityWorld());

			if (block.getDefaultState().getMaterial() == Material.GLASS)
			{
				world.playSound((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, Blocks.GLASS.getDefaultState().getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, Blocks.GLASS.getDefaultState().getSoundGroup().getVolume() + 2.0F, Blocks.GLASS.getDefaultState().getSoundGroup().getPitch() * 1.0F, false);
			}
			else
			{
				world.syncGlobalEvent(2001, new BlockPos(x, y, z), Block.getRawIdFromState(world.getBlockState(new BlockPos(x, y, z))));


			}
		}
	}

	public void destroyBlock(int id, int x, int y, int z, int progress, World world)
	{
		world.setBlockBreakingInfo(id, new BlockPos(x, y, z), progress);
	}

	public void decrementDestroyBlockCooloff() {
		destroy_block_cooloff--;
	}

	public void cancelBlockDestruction() {
		if (this.is_destroying_block)
		{

			destroyBlock(this.getId(), this.destroy_block_x, this.destroy_block_y, this.destroy_block_z, -1, this.world);
			this.is_destroying_block = false;
			this.destroy_block_progress = -1;
			this.destroy_block_cooloff = 40;
		}
	}
}
