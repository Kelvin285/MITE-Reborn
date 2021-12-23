package kelvin.mite.mixin.entity.goal;

import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TrackTargetGoal.class)
public class TrackTargetGoalMixin {
    protected double getFollowRange() {
        return 48;
    }
}
