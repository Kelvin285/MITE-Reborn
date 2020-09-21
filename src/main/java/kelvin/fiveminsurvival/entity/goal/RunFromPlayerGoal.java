package kelvin.fiveminsurvival.entity.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RunFromPlayerGoal extends PanicGoal {

   public int timer = 1000;
   public RunFromPlayerGoal(CreatureEntity creature, double speedIn) {
	   super(creature, speedIn);

   }
   

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
	   CreatureEntity entity = this.creature;
       BlockPos pos = entity.getPosition();
       World world = entity.getEntityWorld();
       if (entity instanceof WolfEntity || entity instanceof CatEntity) {
		   return false;
	   }
       if (!world.isRemote()) {
     	  for (PlayerEntity e : world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(entity.getPositionVec().subtract(3, 3, 3), entity.getPositionVec().add(3, 3, 3))))
     	  {
     		 if (!e.isCrouching()) {
     		  entity.setRevengeTarget(e);
     		  return true;
     		 }
     	  }
       }
      if (super.shouldExecute()) {
          this.findRandomPosition();
          return true;
      }
      
      
      return false;
   }
   
   public boolean shouldContinueExecuting() {
	   CreatureEntity entity = this.creature;
       BlockPos pos = entity.getPosition();
       World world = entity.getEntityWorld();
       
       if (!world.isRemote()) {
     	  for (PlayerEntity e : world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(entity.getPositionVec().subtract(3, 3, 3), entity.getPositionVec().add(3, 3, 3))))
     	  {
     		  creature.setRevengeTarget(e);
     	  }
       }
       
	   if (super.shouldContinueExecuting()) {
		   
	          if (!world.isRemote()) {
	        	  
	        	  for (CreatureEntity e : world.getEntitiesWithinAABB(CreatureEntity.class, new AxisAlignedBB(entity.getPositionVec().subtract(3, 3, 3), entity.getPositionVec().add(3, 3, 3))))
	        	  {
	        		  if (e != creature)
	        		  e.setRevengeTarget(entity.getRevengeTarget());
	        	  }
	          }
	          return true;
	   }
	   
	   return false;
   }

}