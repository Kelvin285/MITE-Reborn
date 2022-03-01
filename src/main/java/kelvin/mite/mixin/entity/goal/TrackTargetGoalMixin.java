package kelvin.mite.mixin.entity.goal;

import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TrackTargetGoal.class)
public class TrackTargetGoalMixin {
    @Inject(at=@At("RETURN"), method="getFollowRange", cancellable = true)
    protected void getFollowRange(CallbackInfoReturnable<Float> info) {
        info.setReturnValue(Math.min(info.getReturnValue(), 48));
    }
}
