package kelvin.mite.mixin.entity;

import kelvin.mite.entity.GrassEater;
import kelvin.mite.entity.HorseKicking;
import kelvin.mite.entity.HungryAnimal;
import kelvin.mite.entity.goal.FoodTargetGoal;
import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.MoonHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.DonkeyEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(HorseBaseEntity.class)
public abstract class HorseBaseEntityMixin  extends AnimalEntity implements HorseKicking, InventoryChangedListener, JumpingMount, Saddleable, GrassEater {


    private static final TrackedData<Boolean> KICKING = DataTracker.registerData(HorseBaseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int kicking_ticks = 0;
    private int kicking_cooldown = 0;

    protected HorseBaseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"), method="initGoals")
    public void initGoals(CallbackInfo info) {
        FoodTargetGoal foodTargetGoal = new FoodTargetGoal(this, true);
        foodTargetGoal.onlyEatsPlants = true;
        this.targetSelector.add(4, foodTargetGoal);
    }

    @Inject(at=@At("RETURN"), method="initDataTracker")
    protected void initDataTracker(CallbackInfo info) {
        this.dataTracker.startTracking(KICKING, false);
    }

    @Shadow
    public boolean isEatingGrass() {
        return false;
    }

    public void mobTick() {
        super.mobTick();
        if (isEatingGrass()) {
            ((HungryAnimal)this).eat();
        }
        HorseBaseEntity horse = (HorseBaseEntity) (Object) this;

        if (!this.hasPlayerRider() && !(MoonHelper.IsBlueMoon(Mite.day_time) && world.isNight()) &&
                !horse.isEatingGrass() &&
        !horse.isAngry()) {
           LivingEntity kick_target = null;
           if (!horse.isTame()) {
               PlayerEntity player = world.getClosestPlayer(this, 2);
               if (player != null) {
                   if (!player.isCreative() && !player.isSneaking()) {
                       kick_target = player;
                   }
               }
           }
           //    default <T extends LivingEntity> T getClosestEntity(Class<? extends T> entityClass, TargetPredicate targetPredicate, @Nullable LivingEntity entity, double x, double y, double z, Box box) {
            ZombieEntity zombie = world.getClosestEntity(ZombieEntity.class, TargetPredicate.DEFAULT, null, getX(), getY(), getZ(),
                    this.getBoundingBox().expand(3));
           if (zombie != null) {
               if (zombie.distanceTo(this) <= 2.25f) {
                   kick_target = zombie;
               }
           }

           if (kick_target != null) {
               if (kick_target instanceof ZombieEntity ||
               random.nextInt(100) == 0)
               if (kicking_cooldown <= 0) {
                    kicking_cooldown = 40;
                    kicking_ticks = 20;
                   Vec3d direction = getPos().subtract(kick_target.getPos());
                   direction.normalize();
                   direction.multiply(-1);
                   direction.add(getPos());
                   this.lookControl.lookAt(direction.x, direction.y, direction.z);
                   kick_target.damage(DamageSource.mob(this), 5);
               }
           }
        }
        if (kicking_cooldown > 0) {
            kicking_cooldown--;
        }
        if (kicking_ticks > 0) {
            kicking_ticks--;
        }
        this.dataTracker.set(KICKING, kicking_ticks > 0);
    }

    public boolean isKicking() {
        return dataTracker.get(KICKING);
    }
}
