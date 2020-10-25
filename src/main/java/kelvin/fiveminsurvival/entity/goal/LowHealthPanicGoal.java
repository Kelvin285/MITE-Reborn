package kelvin.fiveminsurvival.entity.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.PanicGoal;

public class LowHealthPanicGoal extends PanicGoal {

	public LowHealthPanicGoal(CreatureEntity creature, double speedIn) {
		super(creature, speedIn);
	}
	
	@Override
	public boolean shouldExecute() {
		return creature.getHealth() <= creature.getMaxHealth() / 2;
	}

	@Override
	public boolean shouldContinueExecuting() {
		return creature.getHealth() <= creature.getMaxHealth() / 2;
	}
}
