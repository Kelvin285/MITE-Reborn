package kelvin.mite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import kelvin.mite.registry.BiomeRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;

@Mixin(BiomeAccess.class)
public class BiomeAccessMixin {
	
	@Inject(at = @At("HEAD"), method = "getBiome", cancellable = true)
	public void getBiome(BlockPos pos, CallbackInfoReturnable<Biome> info) {
		//return this.type.getBiome(this.seed, pos.getX(), pos.getY(), pos.getZ(), this.storage);
//		info.setReturnValue(BiomeRegistry.BIOME);
	}
}
