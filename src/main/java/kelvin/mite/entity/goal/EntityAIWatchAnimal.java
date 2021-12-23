package kelvin.mite.entity.goal;

import kelvin.mite.entity.AnimalWatcherEntity;
import kelvin.mite.main.resources.RaycastCollision;
import kelvin.mite.main.resources.Resources;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AttackGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.EnumSet;

public class EntityAIWatchAnimal extends Goal {
    private AnimalWatcherEntity digger;
    private ZombieEntity zombie;
    private static boolean player_attacks_always_reset_digging = false;

    public EntityAIWatchAnimal(ZombieEntity attacker) {
        this.digger = (AnimalWatcherEntity)attacker;
        this.zombie = attacker;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (this.digger.isHoldingItemThatPreventsDigging())
        {
            //System.out.println("digging prevented");
            return false;
        }
        else if ((this.digger.isDiggingEnabled() || this.digger.canSeeTarget(false)) && (this.digger.recentlyHit() <= 0 || !player_attacks_always_reset_digging))
        {
            //System.out.println("can dig");
            LivingEntity target = zombie.getTarget();

            if (target == null)
            {

                //System.out.println("no target");
                return false;
            }
            else if (this.zombie.getBlockPos().getX() == target.getBlockPos().getX() && this.zombie.getBlockPos().getY() == target.getBlockPos().getY() && this.zombie.getBlockPos().getZ() == target.getBlockPos().getZ())
            {

                //System.out.println("position is the same as target's position");
                return false;
            }
            else if (this.digger.isDestroyingBlock() && this.digger.canDestroyBlock(this.digger.getDestroyBlockX(), this.digger.getDestroyBlockY(), this.digger.getDestroyBlockZ(), true))
            {

                //System.out.println("can destroy block");
                return true;
            }
//            else if (!this.digger.is_destroying_block && this.digger.RAND.nextInt(20) != 0)
//            {
//
////            	System.out.println("random can't destroy block");
//                return false;
//            }
            else
            {
                //System.out.println("probably can destroy block");
                World world = this.zombie.world;
                float distance_to_target = this.zombie.distanceTo(target);

                if (distance_to_target > 16.0F)
                {

                    //System.out.println("too far from target");
                    return false;
                }
                else
                {

                    //System.out.println("close to target");
                    int attacker_foot_y = this.zombie.getBlockPos().getY();

                    if (distance_to_target * distance_to_target > 2.0F)
                    {

                        int vec3_pool = target.getBlockPos().getX();
                        int can_attacker_see_target = target.getBlockPos().getY() + 2;
                        int path = target.getBlockPos().getZ();

                        while (true)
                        {

                            if (can_attacker_see_target < attacker_foot_y)
                            {
                                break;
                            }
                            //System.out.println("setting digging block");
                            if (this.digger.setBlockToDig(vec3_pool, can_attacker_see_target, path, true))
                            {
                                this.digger.setDestroyingBlock(true);
                                //System.out.println("digging block set");
                                return true;
                            }
                            --can_attacker_see_target;

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
                        boolean var11 = isAirOrPassableBlock(this.zombie.getBlockPos().getX(), MathHelper.floor(this.digger.getEyePosForBlockDestroying().getY() + 1.0D), this.zombie.getBlockPos().getZ(), false, zombie.getEntityWorld()) && checkForLineOfPhysicalReach(new Vec3d(this.zombie.getX(), this.digger.getEyePosForBlockDestroying().getY() + 1.0D, this.zombie.getZ()), target.getPos().add(0, target.getHeight() * 0.75f, 0), zombie.getEntityWorld());

                        if (distance_to_target > (var11 ? 8.0F : (this.digger.isFrenzied() ? 16.0F : 8.0F)))
                        {

                            //System.out.println("a bit too far, sorry");
                            return false;
                        }
                        else
                        {
                            Path var12 = this.zombie.getNavigation().findPathTo(target, 32);

                            if (this.zombie.getNavigation().isFollowingPath())
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

                                if (isAirOrPassableBlock(target.getBlockPos().getX(), target.getBlockPos().getY() + target.getHeight() * 0.75 + 1, target.getBlockPos().getZ(), false, this.zombie.world))
                                {
                                    //System.out.println("there is no air here");
                                    rc = Resources.getBlockCollisionForPhysicalReach(this.digger.getEyePosForBlockDestroying(), target_center_pos.add(new Vec3d(0.0F, 1.0F, 0.0F)), zombie.getEntityWorld(), zombie);

                                    if (rc != null && rc.isBlock() && (this.isNotRestrictedBlock(rc.getBlockHit()) || this.digger.isHoldingAnEffectiveTool(rc.getBlockHit()) || this.zombie.getTarget() instanceof PlayerEntity))
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

                                rc = Resources.getBlockCollisionForPhysicalReach(this.digger.getEyePosForBlockDestroying(), target_center_pos, this.zombie.getEntityWorld(), zombie);

                                if (rc != null && rc.isBlock() && (this.isNotRestrictedBlock(rc.getBlockHit()) || this.digger.isHoldingAnEffectiveTool(rc.getBlockHit()) || this.zombie.getTarget() instanceof PlayerEntity))
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

                                rc = Resources.getBlockCollisionForPhysicalReach(this.digger.getAttackerLegPosForBlockDestroying(), target_center_pos, this.zombie.getEntityWorld(), zombie);

                                if (rc == null || rc.getBlockHit() == null) {
                                    return false;
                                }

                                boolean b = rc != null && rc.isBlock() && (this.isNotRestrictedBlock(rc.getBlockHit()) || this.digger.isHoldingAnEffectiveTool(rc.getBlockHit()) || this.zombie.getTarget() instanceof PlayerEntity) && (!isAirOrPassableBlock(rc.block_hit_x, rc.block_hit_y + 1, rc.block_hit_z, false, this.zombie.getEntityWorld()) || this.digger.blockWillFall(rc.block_hit_x, rc.block_hit_y + 1, rc.block_hit_z)) && this.digger.setBlockToDig(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, false);
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
        return Resources.getBlockCollisionForPhysicalReach(origin, limit, world, zombie);
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
            if (!world.isChunkLoaded(new BlockPos(x, y, z)))
            {
                return false;
            }
            else
            {
                int block_id = Block.getRawIdFromState( world.getBlockState(new BlockPos(x, y, z)));

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
        this.digger.setDestroyingBlock(true);
    }

    private RaycastCollision getIntersectingBlock(Vec3d attacker_eye_pos, Vec3d target_pos, World world)
    {
        return this.getBlockCollisionForPhysicalReach(attacker_eye_pos, target_pos, world);
    }

    private boolean couldHitTargetByPathing()
    {
        LivingEntity target = this.zombie.getTarget();

        if (target == null)
        {
            return false;
        }
        else
        {
            Path path = this.zombie.getNavigation().findPathTo(target, 16);

            if (path == null)
            {
                return false;
            }
            else
            {
                PathNode final_point = path.getEnd();
                float x = (float)final_point.x + 0.5F;
                float y = (float)final_point.y;
                float z = (float)final_point.z + 0.5F;
                World var10000 = this.zombie.world;
                return !(Resources.getDistanceFromDeltas((double) x - target.getX(), (double) y - target.getY(), (double) z - target.getZ()) > 1.0F) && !this.getIntersectingBlock(new Vec3d(x, y, z), this.digger.getTargetEntityCenterPosForBlockDestroying(target), this.zombie.getEntityWorld()).isBlock();
            }
        }
    }

    private boolean couldGetCloserByPathing()
    {
        LivingEntity target = this.zombie.getTarget();

        if (target == null)
        {
            return false;
        }
        else
        {
            double distance = Resources.getDistanceFromDeltas(this.zombie.getX() - target.getX(), this.zombie.getY() - target.getY(), this.zombie.getZ() - target.getZ());
            Path path = this.zombie.getNavigation().findPathTo(target, 16);

            if (path == null)
            {
                return false;
            }
            else
            {
                System.out.println("pathing: " + path.isFinished());
                PathNode final_point = path.getEnd();
                float x = (float)final_point.x + 0.5F;
                float y = (float)final_point.y;
                float z = (float)final_point.z + 0.5F;
                return (double)Resources.getDistanceFromDeltas((double)x - target.getX(), (double)y - target.getY(), (double)z - target.getZ()) < distance - 2.0D;
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
            if (zombie.getTarget() != null)
                if (zombie.getPos().distanceTo(zombie.getTarget().getPos()) <= 1.5f) {

                    return false;
                }

            if (this.digger.getDestroyPauseTicks() > 0)
            {

                return this.digger.getDestroyPauseTicks() != 1 || !this.couldGetCloserByPathing();
            }
            else if (!this.digger.isDestroyingBlock())
            {
                return false;
            }
            else if (!this.digger.canDestroyBlock(this.digger.getDestroyBlockX(), this.digger.getDestroyBlockY(), this.digger.getDestroyBlockZ(), true))
            {
                return false;
            }
            else if (this.digger.recentlyHit() > 0 && player_attacks_always_reset_digging)
            {
                return false;
            }
            else
            {
                LivingEntity target = this.zombie.getTarget();
                return target != null && ((this.zombie.getBlockPos().getX() != target.getBlockPos().getX() || this.zombie.getBlockPos().getY() != target.getBlockPos().getY() || this.zombie.getBlockPos().getZ() != target.getBlockPos().getZ()) && (this.digger.getTicksExistedWithOffset() % 10 != 0 || !this.couldHitTargetByPathing()));
            }
        }
    }

    /**
     * Updates the task
     */
    public void tick()
    {


//    	System.out.println(this.digger.destroy_pause_ticks + ", " + this.digger.destroy_block_cooloff);


        if (this.digger.getDestroyPauseTicks() > 0)
        {
            this.digger.decrementDestroyPauseTicks();
            //--this.digger.destroy_pause_ticks;
        }
        else
        {
            if (this.digger.getDestroyBlockCooloff() == 10)
            {
                this.zombie.swingHand(Hand.MAIN_HAND);
                this.zombie.swingHand(Hand.OFF_HAND);
                int x = this.digger.getDestroyBlockX();
                int y = this.digger.getDestroyBlockY();
                int z = this.digger.getDestroyBlockZ();
                World world = this.zombie.getEntityWorld();

                BlockPos pos = new BlockPos(x, y, z);
                world.playSound(null, pos, world.getBlockState(new BlockPos(x, y, z)).getSoundGroup().getPlaceSound(), SoundCategory.HOSTILE, 1, 1);
            }

            this.digger.decrementDestroyBlockCooloff();
            if (this.digger.getDestroyBlockCooloff() <= 0)
            {
                this.digger.setDestroyBlockCooloff(this.digger.getCooloffForBlock());
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
