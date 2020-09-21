package kelvin.fiveminsurvival.entity;

import java.util.EnumSet;

import kelvin.fiveminsurvival.main.resources.Resources;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIWatchAnimal extends Goal {
	private AnimalWatcherEntity digger;
	private static boolean player_attacks_always_reset_digging = false;
	
	
	
	public EntityAIWatchAnimal(AnimalWatcherEntity attacker) {
		this.digger = attacker;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}
	
	@Override
	public boolean shouldExecute() {
		if (this.digger.isHoldingItemThatPreventsDigging())
        {
			//System.out.println("digging prevented");
            return false;
        }
        else if ((this.digger.isDiggingEnabled() || this.digger.canSeeTarget(false)) && (this.digger.RECENTLY_HIT <= 0 || !player_attacks_always_reset_digging))
        {
        	//System.out.println("can dig");
            LivingEntity target = this.digger.getAttackTarget();

            if (target == null)
            {
            	//System.out.println("no target");
                return false;
            }
            else if (this.digger.getBlockPosX() == target.getPosition().getX() && this.digger.getBlockPosY() == target.getPosition().getY() && this.digger.getBlockPosZ() == target.getPosition().getZ())
            {
            	//System.out.println("position is the same as target's position");
                return false;
            }
            else if (this.digger.is_destroying_block && this.digger.canDestroyBlock(this.digger.destroy_block_x, this.digger.destroy_block_y, this.digger.destroy_block_z, true))
            {
            	//System.out.println("can destroy block");
                return true;
            }
            else if (!this.digger.is_destroying_block && this.digger.RAND.nextInt(20) != 0)
            {
            	//System.out.println("random can't destroy block");
                return false;
            }
            else
            {
            	//System.out.println("probably can destroy block");
                World world = this.digger.worldObj;
                float distance_to_target = this.digger.getDistanceToEntity(target);

                if (distance_to_target > 16.0F)
                {
                	//System.out.println("too far from target");
                    return false;
                }
                else
                {
                	//System.out.println("close to target");
                    int attacker_foot_y = this.digger.getFootBlockPosY();

                    if (distance_to_target * distance_to_target > 2.0F)
                    {
                        int vec3_pool = target.getPosition().getX();
                        int can_attacker_see_target = target.getPosition().getY();
                        int path = target.getPosition().getZ();

                        while (true)
                        {
                            --can_attacker_see_target;

                            if (can_attacker_see_target < attacker_foot_y)
                            {
                                break;
                            }
                            //System.out.println("setting digging block");
                            if (this.digger.setBlockToDig(vec3_pool, can_attacker_see_target, path, true))
                            {
                            	//System.out.println("digging block set");
                                return true;
                            }
                        }
                    }

                    if (distance_to_target > 8.0F)
                    {
                    	//System.out.println("TOO FAR (>8)");
                        return false;
                    }
                    else
                    {
//                        Vec3Pool var10 = world.getWorldVec3Pool();
                        boolean var11 = isAirOrPassableBlock(this.digger.getBlockPosX(), MathHelper.floor(this.digger.getEyePosForBlockDestroying().getY() + 1.0D), this.digger.getBlockPosZ(), false, digger.getEntityWorld()) && checkForLineOfPhysicalReach(new Vec3d(this.digger.getPosX(), this.digger.getEyePosForBlockDestroying().getY() + 1.0D, this.digger.getPosZ()), target.getPositionVec().add(0, target.getHeight() * 0.75, 0), digger.getEntityWorld());

                        if (distance_to_target > (var11 ? 8.0F : (this.digger.isFrenzied() ? 6.0F : 4.0F)))
                        {
                        	//System.out.println("a bit too far, sorry");
                            return false;
                        }
                        else
                        {
                            Path var12 = this.digger.getNavigator().getPathToEntity(target, 16);

                            if (!this.digger.getNavigator().noPath())
                            {
                            	//System.out.println("there is a path to the target");
                                return false;
                            }
                            else if (this.digger.hasLineOfStrikeAndTargetIsWithinStrikingDistance(target))
                            {
                            	//System.out.println("target is within striking distance");
                                return false;
                            }
                            else
                            {
                            	//System.out.println("I need to dig");
                                Vec3d target_center_pos = this.digger.getTargetEntityCenterPosForBlockDestroying(target);
                                RaycastCollision rc;
                                
                                if (isAirOrPassableBlock(target.getPosition().getX(), target.getPosition().getY() + target.getHeight() * 0.75 + 1, target.getPosition().getZ(), false, this.digger.world))
                                {
                                	//System.out.println("there is no air here");
                                    rc = Resources.getBlockCollisionForPhysicalReach(this.digger.getEyePosForBlockDestroying(), target_center_pos.add(0.0D, 1.0D, 0.0D), digger.getEntityWorld());

                                    if (rc != null && rc.isBlock() && (this.isNotRestrictedBlock(rc.getBlockHit()) || this.digger.isHoldingAnEffectiveTool(rc.getBlockHit()) || this.digger.getAttackTarget() instanceof PlayerEntity))
                                    {
                                    	//System.out.println("i can mine this");
                                        ++rc.block_hit_y;

                                        while (rc.block_hit_y >= attacker_foot_y)
                                        {
                                        	//System.out.println("setting digging block");
                                            if (this.digger.setBlockToDig(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true))
                                            {
                                            	//System.out.println("digging block set");
                                                return true;
                                            }

                                            --rc.block_hit_y;
                                        }
                                    }
                                }

                                rc = Resources.getBlockCollisionForPhysicalReach(this.digger.getEyePosForBlockDestroying(), target_center_pos, this.digger.getEntityWorld());

                                if (rc != null && rc.isBlock() && (this.isNotRestrictedBlock(rc.getBlockHit()) || this.digger.isHoldingAnEffectiveTool(rc.getBlockHit()) || this.digger.getAttackTarget() instanceof PlayerEntity))
                                {
                                	//System.out.println("I CAN MINE THIS");
                                    ++rc.block_hit_y;

                                    while (rc.block_hit_y >= attacker_foot_y)
                                    {
                                    	//System.out.println("SETTING DIGGING BLOCK");
                                        if (this.digger.setBlockToDig(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true))
                                        {
                                        	//System.out.println("DIGGING BLOCK SET");
                                            return true;
                                        }

                                        --rc.block_hit_y;
                                    }
                                }
                                
                                rc = Resources.getBlockCollisionForPhysicalReach(this.digger.getAttackerLegPosForBlockDestroying(), target_center_pos, this.digger.getEntityWorld());
                                boolean b = rc != null && rc.isBlock() && (this.isNotRestrictedBlock(rc.getBlockHit()) || this.digger.isHoldingAnEffectiveTool(rc.getBlockHit()) || this.digger.getAttackTarget() instanceof PlayerEntity) && (isAirOrPassableBlock(rc.block_hit_x, rc.block_hit_y + 1, rc.block_hit_z, false, this.digger.getEntityWorld()) || this.digger.blockWillFall(rc.block_hit_x, rc.block_hit_y + 1, rc.block_hit_z)) && this.digger.setBlockToDig(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, false);
                                //System.out.println("stuff = " + b);
                                return b;
                            }
                        }
                    }
                }
            }
        }
        else
        {
        	//System.out.println("everything is false");
            return false;
        }
	}

	

	public final RaycastCollision getBlockCollisionForPhysicalReach(Vec3d origin, Vec3d limit, World world)
    {
        return Resources.getBlockCollisionForPhysicalReach(origin, limit, world);
    }

    public final boolean checkForLineOfPhysicalReach(Vec3d origin, Vec3d limit, World world)
    {
        return !this.getBlockCollisionForPhysicalReach(origin, limit, world).isBlock();
    }

	private boolean isNotRestrictedBlock(Block blockHit) {
		return blockHit != Blocks.BEDROCK && blockHit != Blocks.BARRIER;
	}

	public final boolean isAirOrPassableBlock(double x, double y, double z, boolean include_liquid, World world)
    {
        if (y >= 0 && y <= 255)
        {
            if (!world.isAreaLoaded(new BlockPos(x, y, z), 1))
            {
                return false;
            }
            else
            {
                int block_id =Block.getStateId( world.getBlockState(new BlockPos(x, y, z)));

                if (block_id == 0)
                {
                    return true;
                }
                else
                {
                    Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    return block != null && ((include_liquid || !block.getDefaultState().getMaterial().isLiquid()) && !block.getDefaultState().getMaterial().isSolid());
                }
            }
        }
        else
        {
            return true;
        }
    }
	/**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.digger.is_destroying_block = true;
    }
    
    private RaycastCollision getIntersectingBlock(Vec3d attacker_eye_pos, Vec3d target_pos, World world)
    {
        return this.getBlockCollisionForPhysicalReach(attacker_eye_pos, target_pos, world);
    }
    
    private boolean couldHitTargetByPathing()
    {
        LivingEntity target = this.digger.getAttackTarget();

        if (target == null)
        {
            return false;
        }
        else
        {
            Path path = this.digger.getNavigator().getPathToEntity(target, 16);
            
            if (path == null)
            {
                return false;
            }
            else
            {
                PathPoint final_point = path.getFinalPathPoint();
                float x = (float)final_point.x + 0.5F;
                float y = (float)final_point.y;
                float z = (float)final_point.z + 0.5F;
                World var10000 = this.digger.worldObj;
                return !(Resources.getDistanceFromDeltas((double) x - target.getPosX(), (double) y - target.getPosY(), (double) z - target.getPosZ()) > 1.0F) && !this.getIntersectingBlock(new Vec3d(x, y, z), this.digger.getTargetEntityCenterPosForBlockDestroying(target), this.digger.getEntityWorld()).isBlock();
            }
        }
    }
    
    private boolean couldGetCloserByPathing()
    {
        LivingEntity target = this.digger.getAttackTarget();

        if (target == null)
        {
            return false;
        }
        else
        {
            double distance = Resources.getDistanceFromDeltas(this.digger.getPosX() - target.getPosX(), this.digger.getPosY() - target.getPosY(), this.digger.getPosZ() - target.getPosZ());
            Path path = this.digger.getNavigator().getPathToEntity(target, 16);
            
            if (path == null)
            {
                return false;
            }
            else
            {
            	System.out.println("pathing: " + path.isFinished());
                PathPoint final_point = path.getFinalPathPoint();
                float x = (float)final_point.x + 0.5F;
                float y = (float)final_point.y;
                float z = (float)final_point.z + 0.5F;
                return (double)Resources.getDistanceFromDeltas((double)x - target.getPosX(), (double)y - target.getPosY(), (double)z - target.getPosZ()) < distance - 2.0D;
            }
        }
    }
    
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        if (this.digger.isHoldingItemThatPreventsDigging())
        {
            return false;
        }
        else
        {
//            EntityAIAttackOnCollide ai = (EntityAIAttackOnCollide)this.digger.getEntityAITask(EntityAIAttackOnCollide.class);
//
//            if (ai != null)
//            {
//                if (ai.attackTick > 0)
//                {
//                    --ai.attackTick;
//                }
//
//                if (ai.canStrikeTargetNow())
//                {
//                    ai.updateTask();
//                    return false;
//                }
//            }
        	
        	if (digger.getAttackTarget() != null)
        	if (digger.getPositionVec().distanceTo(digger.getAttackTarget().getPositionVec()) <= 1.5f) {
        		return false;
        	}

            if (this.digger.destroy_pause_ticks > 0)
            {
                return this.digger.destroy_pause_ticks != 1 || !this.couldGetCloserByPathing();
            }
            else if (!this.digger.is_destroying_block)
            {
                return false;
            }
            else if (!this.digger.canDestroyBlock(this.digger.destroy_block_x, this.digger.destroy_block_y, this.digger.destroy_block_z, true))
            {
                return false;
            }
            else if (this.digger.RECENTLY_HIT > 0 && player_attacks_always_reset_digging)
            {
                return false;
            }
            else
            {
                LivingEntity target = this.digger.getAttackTarget();
                return target != null && ((this.digger.getBlockPosX() != target.getPosition().getX() || this.digger.getBlockPosY() != target.getPosition().getY() || this.digger.getBlockPosZ() != target.getPosition().getZ()) && (this.digger.getTicksExistedWithOffset() % 10 != 0 || !this.couldHitTargetByPathing()));
            }
        }
    }

    /**
     * Updates the task
     */
    public void tick()
    {
    	
        	
//    	System.out.println(this.digger.destroy_pause_ticks + ", " + this.digger.destroy_block_cooloff);
    	
        if (this.digger.destroy_pause_ticks > 0)
        {
            --this.digger.destroy_pause_ticks;
        }
        else
        {
            if (this.digger.destroy_block_cooloff == 10)
            {
                this.digger.swingArm(Hand.MAIN_HAND);
                this.digger.swingArm(Hand.OFF_HAND);
            }

            if (--this.digger.destroy_block_cooloff <= 0)
            {
                this.digger.destroy_block_cooloff = this.digger.getCooloffForBlock();
                this.digger.partiallyDestroyBlock();
            }
        }
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.digger.cancelBlockDestruction();
    }

}
