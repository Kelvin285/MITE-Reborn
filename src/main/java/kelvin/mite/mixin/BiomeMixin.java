package kelvin.mite.mixin;

import kelvin.mite.main.Mite;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import kelvin.mite.registry.BiomeRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {
	@Inject(at = @At("RETURN"), method = "<init>")
	public void Constructor(CallbackInfo info) {
		BiomeRegistry.biomes.add((Biome)(Object)this);
	}

	@Inject(at=@At("RETURN"), method="getTemperature", cancellable = true)
	public void getTemperature(BlockPos pos, CallbackInfoReturnable<Float> info) {
		float temp = info.getReturnValue();

		float min_temp = -0.75f;
		float Season = Mite.season_time / Mite.TicksInYear;
		Season %= 1;

		if (Season < 0.125) {
			float MIX = 1.0f - (Season / 0.125f);

			temp += min_temp * MIX;
		}
		if (Season > 0.625) {

			if (Season > 0.875) {
				float MIX = (Season - 0.875f) / 0.25f;

				temp += (min_temp / 2.0f) * MIX + (min_temp / 2.0f);
			} else {
				float MIX = (Season - 0.625f) / 0.25f;
				temp += (min_temp / 2.0f) * MIX;
			}
		}

		info.setReturnValue(temp);
	}
}
