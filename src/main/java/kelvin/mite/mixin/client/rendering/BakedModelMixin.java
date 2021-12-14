package kelvin.mite.mixin.client.rendering;

import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.WeightedBakedModel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WeightedBakedModel.class)
public class BakedModelMixin {
    TerrainRenderContext c;
}
