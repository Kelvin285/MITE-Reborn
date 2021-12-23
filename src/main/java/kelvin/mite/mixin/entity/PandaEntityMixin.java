package kelvin.mite.mixin.entity;

import kelvin.mite.entity.HungryAnimal;
import kelvin.mite.entity.goal.FoodTargetGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PandaEntity.class)
public abstract class PandaEntityMixin extends AnimalEntity {

    protected PandaEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"), method="initGoals")
    public void initGoals(CallbackInfo info) {
        FoodTargetGoal foodTargetGoal = new FoodTargetGoal(this, true);
        foodTargetGoal.onlyEatsPlants = true;
        this.targetSelector.add(4, foodTargetGoal);
    }

    @Inject(at=@At("HEAD"), method="setEating")
    public void setEating(boolean eating) {
        ((HungryAnimal)this).eat();
    }
}
