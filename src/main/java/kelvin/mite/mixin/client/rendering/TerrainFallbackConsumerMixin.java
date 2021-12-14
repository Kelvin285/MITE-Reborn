package kelvin.mite.mixin.client.rendering;

import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.impl.client.indigo.renderer.RenderMaterialImpl;
import net.fabricmc.fabric.impl.client.indigo.renderer.mesh.MutableQuadViewImpl;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.CompatibilityHelper;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainFallbackConsumer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TerrainFallbackConsumer.class)
public class TerrainFallbackConsumerMixin {

}
