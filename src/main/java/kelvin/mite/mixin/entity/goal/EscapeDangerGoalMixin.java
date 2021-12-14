package kelvin.mite.mixin.entity.goal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
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
        PlayerEntity close = world.getClosestPlayer(mob.getX(), mob.getY(), mob.getZ(), 4, (p) -> {
            PlayerEntity player = (PlayerEntity) p;
            return !player.isCreative() && !player.isSneaking();
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
        }
    }

    @Overwrite
    public boolean findTarget() {
        Vec3d vec3d = NoPenaltyTargeting.find(this.mob, 5, 4);
        LivingEntity attacker = this.mob.getAttacker();
        if (attacker != null) {
            Vec3d direction = mob.getPos().subtract(attacker.getPos()).normalize();
            vec3d = NoPenaltyTargeting.findTo(this.mob, 5, 4, direction.add(this.mob.getPos()), 15.0D);
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
