package kelvin.mite.mixin;

import kelvin.mite.main.resources.WorldTimeHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin implements WorldTimeHelper {

    private double timeOfDayDouble;

    @Override
    public double GetDoubleTime() {
        return timeOfDayDouble;
    }

    @Override
    public void SetDoubleTime(double time) {
        timeOfDayDouble = time;
    }
}
