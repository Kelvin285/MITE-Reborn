package kelvin.mite.mixin;

import kelvin.mite.main.Mite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class WorldMixin {

    @Inject(at=@At("HEAD"), method = "tickBlockEntities")
    public void tickBlockEntities(CallbackInfo info) {
        if (!((World)(Object)this).isClient) {
            Mite.season_time = ((World)(Object)this).getTimeOfDay() + Mite.StartingDay;
        }
        Mite.day_time = ((World)(Object)this).getTimeOfDay();
    }
}
