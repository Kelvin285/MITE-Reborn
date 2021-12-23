package kelvin.mite.mixin.entity;

import kelvin.mite.entity.HungryAnimal;
import kelvin.mite.entity.goal.FoodTargetGoal;
import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.MoonHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.security.auth.callback.Callback;

@Mixin(PolarBearEntity.class)
public abstract class PolarBearEntityMixin extends AnimalEntity implements Angerable {


    protected PolarBearEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"), method="initGoals")
    public void initGoals(CallbackInfo info) {
        FoodTargetGoal foodTargetGoal = new FoodTargetGoal(this, true);
        this.targetSelector.add(4, foodTargetGoal);
    }

    @Inject(at=@At("HEAD"),method="tick")
    public void tick(CallbackInfo info) {
        PlayerEntity player = world.getClosestPlayer(this, 32);

        if (this.getTarget() == null) {
            if (player != null) {
                if (player.distanceTo(this) <= 6 && !player.isCreative() && this.canSee(player)) {
                    if (random.nextInt(100) == 0) {
                        this.setAngryAt(player.getUuid());
                        this.setTarget(player);
                    }
                }
            }
        }

        if (((HungryAnimal)this).isHungry() && this.getTarget() == null && this.getAngryAt() == null) {
            int probability = 100;
            LivingEntity closest = null;
            float distance = Float.MAX_VALUE;
            var entities = world.getEntitiesByClass(AnimalEntity.class, this.getBoundingBox().expand(32), (entity) -> {
                return !(entity instanceof PolarBearEntity) && this.canSee(entity);
            });
            for (int i = 0; i < entities.size(); i++) {
                float dist = entities.get(i).distanceTo(this);
                if (dist < distance) {
                    distance = dist;
                    closest = entities.get(i);
                }
            }

            boolean flag = false;
            if (player != null) {
                if (player.distanceTo(this) < distance) {
                    flag = true;
                    if (!canSee(player)) {
                        player = null;
                    }
                }
            }
            if (player != null && ((random.nextInt(40) == 0 || flag) || closest == null && !(MoonHelper.IsBlueMoon(Mite.day_time) && world.isNight()))) {
                setTarget(player);
                this.setAngryAt(player.getUuid());
            } else {
                if (closest != null) {
                    setTarget(closest);
                    this.setAngryAt(closest.getUuid());
                }
            }
        }
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
