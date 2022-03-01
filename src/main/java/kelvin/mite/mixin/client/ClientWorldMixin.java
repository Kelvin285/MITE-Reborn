package kelvin.mite.mixin.client;

import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.WorldTimeHelper;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World {

    protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
    }

    @Shadow
    public void setTime(long time) {

    }
    @Shadow
    public void setTimeOfDay(long time) {

    }

    boolean start = false;
    @Inject(at=@At("RETURN"), method="tickTime")
    public void tickTime(CallbackInfo info) {
        WorldTimeHelper timeHelper = ((WorldTimeHelper)properties);
        if (!start) {
            timeHelper.SetDoubleTime(this.properties.getTime());
            start = true;
        }
        timeHelper.SetDoubleTime(timeHelper.GetDoubleTime() + Mite.time_increment);

        if (this.properties.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            this.setTimeOfDay((long)timeHelper.GetDoubleTime());
        } else {
            start = false;
        }

    }
}
