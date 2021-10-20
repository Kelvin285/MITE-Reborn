package kelvin.mite.mixin;

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

@Mixin(Biome.class)
public class BiomeMixin {
	@Inject(at = @At("RETURN"), method = "<init>")
	public void Constructor(CallbackInfo info) {
		BiomeRegistry.biomes.add((Biome)(Object)this);
	}
}
