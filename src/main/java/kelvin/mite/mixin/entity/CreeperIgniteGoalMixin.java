package kelvin.mite.mixin.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CreeperIgniteGoal.class)
public abstract class CreeperIgniteGoalMixin extends Goal {
    @Shadow
    private CreeperEntity creeper;
    @Shadow
    private LivingEntity target;

    @Override
    public void start() {
        ///this.creeper.getNavigation().stop();
        this.target = this.creeper.getTarget();
    }
}
