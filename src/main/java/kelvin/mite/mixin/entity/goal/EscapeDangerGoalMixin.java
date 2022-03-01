package kelvin.mite.mixin.entity.goal;

import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.MoonHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EscapeDangerGoal.class)
public class EscapeDangerGoalMixin {
    @Shadow
    protected PathAwareEntity mob;
    @Shadow
    protected double speed;
    @Shadow
    protected double targetX;
    @Shadow
    protected double targetY;
    @Shadow
    protected double targetZ;
    @Shadow
    protected boolean active;

    @Inject(at=@At("HEAD"), method="canStart")
    public void canStart(CallbackInfoReturnable<Boolean> info) {

        World world = this.mob.world;
        PlayerEntity close = world.getClosestPlayer(mob.getX(), mob.getY(), mob.getZ(), 8, (p) -> {
            PlayerEntity player = (PlayerEntity) p;
            if (this.mob instanceof HorseBaseEntity) {
                if (((HorseBaseEntity)mob).isTame()) {
                    return false;
                }
            }
            if (this.mob instanceof TameableEntity) {
                if (((TameableEntity)mob).isTamed()) {
                    return false;
                }
            }
            return !player.isCreative() && !player.isSneaking() && player.isSprinting() && !(MoonHelper.IsBlueMoon(Mite.day_time) && mob.world.isNight());
        });
        List<LivingEntity> scared = world.<LivingEntity>getEntitiesByClass(
                LivingEntity.class,
                new Box(mob.getX() - 5, mob.getY() - 5, mob.getZ() - 5, mob.getX() + 5, mob.getY() + 5, mob.getZ() + 5),
                        (e) -> {
                            return e instanceof AnimalEntity && e.getAttacker() != null;
                        }
        );
        if (close != null) {
            this.mob.setAttacker(close);
        } else {
            if (!(MoonHelper.IsBlueMoon(Mite.day_time) && mob.world.isNight())) {
                for (int i = 0; i < scared.size(); i++) {
                    if (scared.get(i).getAttacker() != null) {
                        this.mob.setAttacker(scared.get(i).getAttacker());
                        break;
                    }
                }
            }
        }
    }

    public void start() {
        this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed * 1.15f);
        this.active = true;
    }

    @Overwrite
    public boolean findTarget() {
        Vec3d vec3d = NoPenaltyTargeting.find(this.mob, 5, 4);
        LivingEntity attacker = this.mob.getAttacker();
        if (attacker != null) {
            Vec3d direction = mob.getPos().subtract(attacker.getPos()).normalize();
            vec3d = NoPenaltyTargeting.findTo(this.mob, 10, 4, direction.multiply(4).add(this.mob.getPos()), 15.0D);
        }

        if (vec3d == null) {
            return false;
        } else {
            this.targetX = vec3d.x;
            this.targetY = vec3d.y;
            this.targetZ = vec3d.z;
            return true;
        }
    }
}
