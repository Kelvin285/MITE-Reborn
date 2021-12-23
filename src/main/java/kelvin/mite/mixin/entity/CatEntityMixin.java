package kelvin.mite.mixin.entity;

import kelvin.mite.entity.HungryAnimal;
import kelvin.mite.entity.TurkeyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.UntamedActiveTargetGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatEntity.class)
public abstract class CatEntityMixin extends TameableEntity {
    protected CatEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"), method="initGoals")
    public void initGoals(CallbackInfo info) {
        this.targetSelector.add(1, new UntamedActiveTargetGoal(this, ChickenEntity.class, false, (entity) -> {
            return true;
        }));
        this.targetSelector.add(1, new UntamedActiveTargetGoal(this, ParrotEntity.class, false, (entity) -> {
            return true;
        }));
        this.targetSelector.add(1, new UntamedActiveTargetGoal(this, TurkeyEntity.class, false, (entity) -> {
            return true;
        }));
    }

    public boolean tryAttack(Entity target) {
        boolean bl = target.damage(DamageSource.mob(this), (float)((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
        if (bl) {
            ((HungryAnimal)this).eat();
            this.applyDamageEffects(this, target);
        }

        return bl;
    }
}
