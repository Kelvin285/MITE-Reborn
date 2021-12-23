package kelvin.mite.mixin.entity;

import kelvin.mite.entity.HungryAnimal;
import kelvin.mite.entity.goal.FoodTargetGoal;
import net.minecraft.block.GrassBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RabbitEntity.class)
public abstract class RabbitEntityMixin extends AnimalEntity {
    protected RabbitEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"), method="initGoals")
    public void initGoals(CallbackInfo info) {
        FoodTargetGoal foodTargetGoal = new FoodTargetGoal(this, true);
        foodTargetGoal.onlyEatsPlants = true;
        this.targetSelector.add(4, foodTargetGoal);
    }

    public void tick() {
        super.tick();
        if (world.getBlockState(getBlockPos().down()).getBlock() instanceof GrassBlock) {
            ((HungryAnimal)this).eat();
        }
    }
}
