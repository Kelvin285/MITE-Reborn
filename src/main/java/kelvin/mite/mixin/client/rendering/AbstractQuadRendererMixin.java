package kelvin.mite.mixin.client.rendering;

import kelvin.mite.blocks.rendering.BlockBufferVertexConsumer;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.impl.client.indigo.renderer.RenderMaterialImpl;
import net.fabricmc.fabric.impl.client.indigo.renderer.aocalc.AoCalculator;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.ColorHelper;
import net.fabricmc.fabric.impl.client.indigo.renderer.mesh.MutableQuadViewImpl;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.AbstractQuadRenderer;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderInfo;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.CompatibilityHelper;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainFallbackConsumer;
import net.minecraft.block.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.*;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.LightType;
import org.lwjgl.system.MemoryStack;
import org.spongepowered.asm.mixin.Mixin;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mixin(AbstractQuadRenderer.class)
public abstract class AbstractQuadRendererMixin {
    private void bufferQuad(MutableQuadViewImpl quad, RenderLayer renderLayer) throws Exception {
        newBufferQuad(((Function<RenderLayer, VertexConsumer>)AbstractQuadRenderer.class.getDeclaredField("bufferFunc").get(this)).apply(renderLayer), quad, (Matrix4f)AbstractQuadRenderer.class.getDeclaredMethod("matrix").invoke(this), (int)AbstractQuadRenderer.class.getDeclaredMethod("overlay").invoke(this), (Matrix3f)AbstractQuadRenderer.class.getDeclaredMethod("normalMatrix").invoke(this), (Vec3f)AbstractQuadRenderer.class.getDeclaredField("normalVec").get(this));
    }

    public void newBufferQuad(VertexConsumer buff, MutableQuadViewImpl quad, Matrix4f matrix, int overlay, Matrix3f normalMatrix, Vec3f normalVec) {
        BlockRenderInfo blockInfo = null;
        try {
            blockInfo = (BlockRenderInfo)AbstractQuadRenderer.class.getDeclaredField("blockInfo").get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BlockRenderView world = blockInfo.blockView;
        BlockState state = blockInfo.blockState;
        BlockPos pos = blockInfo.blockPos;

        boolean windy = world.getLightLevel(LightType.SKY, pos) > 4;

        float flag0 = 0;
        float flag1 = 0;
        float flag2 = 0;
        float flag3 = 0;

        if (windy) {
            if (state.getBlock() instanceof LeavesBlock || state.getBlock() instanceof AbstractPlantBlock || state.getBlock() instanceof CropBlock || state.getBlock() instanceof PlantBlock || state.getBlock() instanceof VineBlock) {
                flag0 = 1;
                flag1 = 1;
                flag2 = 1;
                flag3 = 1;

                if (!(state.getBlock() instanceof TallPlantBlock || state.getBlock() instanceof TallFlowerBlock) && (state.getBlock() instanceof PlantBlock || state.getBlock() instanceof AbstractPlantBlock || state.getBlock() instanceof CropBlock)) {
                    float[] f = {0, 0, 0, 0};

                    for (int i = 0; i < 4; i++) {
                        Vec3f vpos = new Vec3f(0, 0, 0);
                        quad.copyPos(i, vpos);
                        if (vpos.getY() > 0.5f) {
                            f[i] = 1;
                        }
                    }
                    flag0 = f[0];
                    flag1 = f[1];
                    flag2 = f[2];
                    flag3 = f[3];
                }
            }
        }

        float[] flags = {flag0, flag1, flag2, flag3};
        final boolean useNormals = quad.hasVertexNormals();

        if (useNormals) {
            quad.populateMissingNormals();
        } else {
            final Vec3f faceNormal = quad.faceNormal();
            normalVec.set(faceNormal.getX(), faceNormal.getY(), faceNormal.getZ());
            normalVec.transform(normalMatrix);
        }

        BlockBufferVertexConsumer bc = (BlockBufferVertexConsumer)buff;

        for (int i = 0; i < 4; i++) {
            buff.vertex(matrix, quad.x(i), quad.y(i), quad.z(i));
            final int color = quad.spriteColor(i, 0);
            buff.color(color & 0xFF, (color >> 8) & 0xFF, (color >> 16) & 0xFF, (color >> 24) & 0xFF);
            buff.texture(quad.spriteU(i, 0), quad.spriteV(i, 0));
            buff.overlay(overlay);
            buff.light(quad.lightmap(i));

            if (useNormals) {
                normalVec.set(quad.normalX(i), quad.normalY(i), quad.normalZ(i));
                normalVec.transform(normalMatrix);
            }

            buff.normal(normalVec.getX(), normalVec.getY(), normalVec.getZ());

            bc.flag(flags[i]);

            buff.next();
        }
    }

}
