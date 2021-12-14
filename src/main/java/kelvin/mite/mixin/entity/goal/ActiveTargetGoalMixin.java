package kelvin.mite.mixin.entity.goal;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ActiveTargetGoal.class)
public class ActiveTargetGoalMixin {
    public double getFollowRange() {
        return 48;
    }
}
