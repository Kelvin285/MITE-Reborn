package kelvin.fiveminsurvival.entity;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class NearestEdibleFoodGoal extends Goal {
	/** The entity that this goal belongs to */
   protected final AnimalWatcherEntity goalOwner;
   protected final boolean shouldCheckSight;
   private final boolean nearbyOnly;
   private int targetSearchStatus;
   private int targetSearchDelay;
   private int targetUnseenTicks;
   protected ItemEntity target;
   protected int unseenMemoryTicks = 60;

   protected final Class<ItemEntity> targetClass = ItemEntity.class;
   protected final int targetChance;
   protected ItemEntity nearestTarget;
   /** This filter is applied to the Entity search. Only matching entities will be targeted. */
   protected EntityPredicate targetEntitySelector;
   
   public NearestEdibleFoodGoal(AnimalWatcherEntity mobIn, boolean checkSight) {
      this(mobIn, checkSight, false);
   }

   public NearestEdibleFoodGoal(AnimalWatcherEntity mobIn, boolean checkSight, boolean p_i50309_3_) {
      this.goalOwner = mobIn;
      this.shouldCheckSight = checkSight;
      this.nearbyOnly = p_i50309_3_;
      this.targetChance = 1;
      this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
      this.targetEntitySelector = (new EntityPredicate()).setDistance(this.getTargetDistance()).setCustomPredicate(null);
   }
   

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      ItemEntity livingentity = this.goalOwner.attackFood;
      if (livingentity == null) {
         livingentity = this.target;
      }

      if (livingentity == null) {
         return false;
      } else if (!livingentity.isAlive()) {
         return false;
      } else {
         Team team = this.goalOwner.getTeam();
         Team team1 = livingentity.getTeam();
         if (team != null && team1 == team) {
            return false;
         } else {
            double d0 = this.getTargetDistance();
            if (this.goalOwner.getDistanceSq(livingentity) > d0 * d0) {
               return false;
            } else {
               if (this.shouldCheckSight) {
                  if (this.goalOwner.getEntitySenses().canSee(livingentity)) {
                     this.targetUnseenTicks = 0;
                  } else if (++this.targetUnseenTicks > this.unseenMemoryTicks) {
                     return false;
                  }
               }

               
              this.goalOwner.attackFood = (livingentity);
              return true;
               
            }
         }
      }
   }
   // field_233820_c_ = movement speed
   // field_233818_a_ = health
   // field_233823_f_ = attack damage
   // field_233819_b_ = follow range
   // field_233826_i_ = armor
   protected double getTargetDistance() {
      ModifiableAttributeInstance iattributeinstance = this.goalOwner.getAttribute(Attributes.FOLLOW_RANGE);
      return iattributeinstance == null ? 16.0D : iattributeinstance.getValue();
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.targetSearchStatus = 0;
      this.targetSearchDelay = 0;
      this.targetUnseenTicks = 0;
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.goalOwner.setAttackTarget(null);
      this.target = null;
   }

   /**
    * checks if is is a suitable target
    */
   protected boolean isSuitableTarget(@Nullable LivingEntity p_220777_1_, EntityPredicate p_220777_2_) {
      if (p_220777_1_ == null) {
         return false;
      } else if (!p_220777_2_.canTarget(this.goalOwner, p_220777_1_)) {
         return false;
      } else if (!this.goalOwner.isWithinHomeDistanceFromPosition(p_220777_1_.getPosition())) {
         return false;
      } else {
         if (this.nearbyOnly) {
            if (--this.targetSearchDelay <= 0) {
               this.targetSearchStatus = 0;
            }

            if (this.targetSearchStatus == 0) {
               this.targetSearchStatus = this.canEasilyReach(p_220777_1_) ? 1 : 2;
            }

            return this.targetSearchStatus != 2;
         }

         return true;
      }
   }

   /**
    * Checks to see if this entity can find a short path to the given target.
    */
   private boolean canEasilyReach(LivingEntity target) {
      this.targetSearchDelay = 10 + this.goalOwner.getRNG().nextInt(5);
      Path path = this.goalOwner.getNavigator().getPathToEntity(target, 0);
      if (path == null) {
         return false;
      } else {
         PathPoint pathpoint = path.getFinalPathPoint();
         if (pathpoint == null) {
            return false;
         } else {
            int i = pathpoint.x - MathHelper.floor(target.getPosX());
            int j = pathpoint.z - MathHelper.floor(target.getPosZ());
            return (double)(i * i + j * j) <= 2.25D;
         }
      }
   }

   public NearestEdibleFoodGoal setUnseenMemoryTicks(int p_190882_1_) {
      this.unseenMemoryTicks = p_190882_1_;
      return this;
   } 
   protected AxisAlignedBB getTargetableArea(double targetDistance) {
	      return this.goalOwner.getBoundingBox().grow(targetDistance, 4.0D, targetDistance);
	   }
   protected void findNearestTarget() {
	      World world = goalOwner.getEntityWorld();
	      List<Entity> entities = world.getEntitiesWithinAABB(ItemEntity.class, getTargetableArea(8));
	      double distance = 16;
	      for (Entity e : entities) {
	    	  if (e instanceof ItemEntity) {
	    		  ItemEntity i = (ItemEntity)e;
	    		  if (i.getItem() != null) {
	    			  if (i.getItem().getItem() != null) {
	    				  Item it = i.getItem().getItem();
	    				  if (it.isFood()) {
	    					  if (i.getPositionVec().distanceTo(goalOwner.getPositionVec()) < distance) {
	    						  distance = i.getPositionVec().distanceTo(goalOwner.getPositionVec());
	    						  this.nearestTarget = i;
	    					  }
	    				  }
	    			  }
	    		  }
	    	  }
	      }
	   }
@Override
public boolean shouldExecute() {
    if (this.targetChance > 0 && this.goalOwner.getRNG().nextInt(this.targetChance) != 0) {
       return false;
    } else {
       this.findNearestTarget();
       return this.nearestTarget != null;
    }
 }
}