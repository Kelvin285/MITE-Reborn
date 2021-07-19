package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

@Mixin(NoiseChunkGenerator.class)
public class NoiseChunkGeneratorMixin {

	@Shadow OctavePerlinNoiseSampler densityNoise;
	
	
	@Inject(at = @At("HEAD"), method = "getRandomDensityAt", cancellable = true)
	private void getRandomDensityAt(int x, int z, CallbackInfoReturnable<Double> info) {
		double d = this.densityNoise.sample((double) (x * 100), 10.0D, (double) (z * 100), 1.0D, 0.0D, true);
		double f;
		if (d < 0.0D) {
			f = -d * 0.3D;
		} else {
			f = d;
		}

		double g = f * 24.575625D - 2.0D;
		info.setReturnValue(g < 0.0D ? g * 0.009486607142857142D : Math.min(g, 1.0D) * 0.006640625D);
	}
}
