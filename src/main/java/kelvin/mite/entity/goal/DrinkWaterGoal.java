package kelvin.mite.entity.goal;

import kelvin.mite.entity.HungryAnimal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.EnumSet;

public class DrinkWaterGoal extends Goal {

    public HungryAnimal hunger;
    public AnimalEntity animal;

    public double targetX, targetY, targetZ;
    public boolean active;

    public DrinkWaterGoal(AnimalEntity animal) {
        this.animal = animal;
        this.hunger = (HungryAnimal) animal;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (hunger.isThirsty()) {
            BlockPos blockPos = this.locateClosestWater(this.animal.world, this.animal, 10);
            if (blockPos != null) {
                this.targetX = (double)blockPos.getX();
                this.targetY = (double)blockPos.getY();
                this.targetZ = (double)blockPos.getZ();
                return true;
            }
        }
        return false;
    }

    public void start() {
        this.animal.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, 1.0D);
        this.active = true;
    }

    public void tick() {
        super.tick();
        if (animal.isSwimming()) {
            hunger.drink();
            stop();
        }
    }

    public boolean shouldContinue() {
        return !this.animal.getNavigation().isIdle();
    }

    public void stop() {
        this.active = false;
    }

    protected BlockPos locateClosestWater(BlockView blockView, Entity entity, int rangeX) {
        BlockPos blockPos = entity.getBlockPos();
        return !blockView.getBlockState(blockPos).getCollisionShape(blockView, blockPos).isEmpty() ? null : (BlockPos)BlockPos.findClosest(entity.getBlockPos(), rangeX, 1, (blockPosx) -> {
            return blockView.getFluidState(blockPosx).isIn(FluidTags.WATER);
        }).orElse(null);
    }
}
