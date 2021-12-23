package kelvin.mite.mixin.client.rendering;

import kelvin.mite.blocks.rendering.BlockBufferVertexConsumer;
import kelvin.mite.blocks.rendering.IOverlayVertexConsumer;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.*;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.LightType;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Mixin(BlockModelRenderer.class)
public class BlockModelRendererMixin {

    @Shadow
    private BlockColors colors;

    @Shadow
    private void getQuadDimensions(BlockRenderView world, BlockState state, BlockPos pos, int[] vertexData, Direction face, @Nullable float[] box, BitSet flags) {

    }
    //            this.blockModelRenderer.render(world, bakedModel, state, pos, matrix, vertexConsumer, true, this.random, l, OverlayTexture.DEFAULT_UV);

    //Entry entry, VertexConsumer vertexConsumer, float f, float g, float h, List<BakedQuad> list, int i, int j
    @Overwrite
    private static void renderQuad(MatrixStack.Entry entry, VertexConsumer vertexConsumer, float f, float g, float h, List<BakedQuad> list, int i, int j) {
        BakedQuad bakedQuad;
        float n;
        float o;
        float p;
        for(Iterator var8 = list.iterator(); var8.hasNext(); vertexConsumer.quad(entry, bakedQuad, n, o, p, i, j)) {
            bakedQuad = (BakedQuad)var8.next();
            if (bakedQuad.hasColor()) {
                n = MathHelper.clamp(f, 0.0F, 1.0F);
                o = MathHelper.clamp(g, 0.0F, 1.0F);
                p = MathHelper.clamp(h, 0.0F, 1.0F);
            } else {
                n = 1.0F;
                o = 1.0F;
                p = 1.0F;
            }
        }
    }
    @Overwrite
    public void renderQuad(BlockRenderView world, BlockState state, BlockPos pos, VertexConsumer vertexConsumer, MatrixStack.Entry matrixEntry, BakedQuad quad, float brightness0, float brightness1, float brightness2, float brightness3, int light0, int light1, int light2, int light3, int overlay) {
        float j;
        float k;
        float l;

        boolean windy = world.getLightLevel(LightType.SKY, pos) > 4;

        if (quad.hasColor()) {
            int i = this.colors.getColor(state, world, pos, quad.getColorIndex());
            j = (float)(i >> 16 & 255) / 255.0F;
            k = (float)(i >> 8 & 255) / 255.0F;
            l = (float)(i & 255) / 255.0F;
        } else {
            j = 1.0F;
            k = 1.0F;
            l = 1.0F;
        }

        float flag0 = 0;
        float flag1 = 0;
        float flag2 = 0;
        float flag3 = 0;

        if (state.getBlock() instanceof FluidBlock || !state.getFluidState().isEmpty()) {
            flag0 = 2;
            flag1 = 2;
            flag2 = 2;
            flag3 = 2;
        }

        if (windy) {
            if (state.getBlock() instanceof LeavesBlock || state.getBlock() instanceof AbstractPlantBlock || state.getBlock() instanceof CropBlock || state.getBlock() instanceof PlantBlock || state.getBlock() instanceof VineBlock) {
                flag0 = 1;
                flag1 = 1;
                flag2 = 1;
                flag3 = 1;

                if (!(state.getBlock() instanceof TallPlantBlock || state.getBlock() instanceof TallFlowerBlock) && (state.getBlock() instanceof PlantBlock || state.getBlock() instanceof AbstractPlantBlock || state.getBlock() instanceof CropBlock)) {
                    float[] f = {0, 0, 0, 0};
                    int[] vertexData = quad.getVertexData();
                    int length = vertexData.length / 8;
                    MemoryStack memoryStack = MemoryStack.stackPush();
                    try {
                        ByteBuffer byteBuffer = memoryStack.malloc(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL.getVertexSize());
                        IntBuffer intBuffer = byteBuffer.asIntBuffer();

                        for (int i = 0; i < length; ++i) {
                            intBuffer.clear();
                            intBuffer.put(vertexData, i * 8, 8);
                            float x = byteBuffer.getFloat(0);
                            float y = byteBuffer.getFloat(4);
                            float z = byteBuffer.getFloat(8);
                            if (y > 0.5f) {
                                f[i] = 1;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        memoryStack.close();
                    }
                    if (memoryStack != null) {
                        memoryStack.close();
                    }
                    flag0 = f[0];
                    flag1 = f[1];
                    flag2 = f[2];
                    flag3 = f[3];
                }
            }
        }

        if (vertexConsumer instanceof BlockBufferVertexConsumer) {
            ((BlockBufferVertexConsumer)vertexConsumer).quad(matrixEntry, quad, new float[]{brightness0, brightness1, brightness2, brightness3}, j, k, l, new int[]{light0, light1, light2, light3}, overlay, new float[]{flag0, flag1, flag2, flag3}, true);
        } else {
            if (vertexConsumer instanceof IOverlayVertexConsumer) {
                ((IOverlayVertexConsumer)vertexConsumer).quad(matrixEntry, quad, new float[]{brightness0, brightness1, brightness2, brightness3}, j, k, l, new int[]{light0, light1, light2, light3}, overlay, new float[]{flag0, flag1, flag2, flag3}, true);

            } else {
                vertexConsumer.quad(matrixEntry, quad, new float[]{brightness0, brightness1, brightness2, brightness3}, j, k, l, new int[]{light0, light1, light2, light3}, overlay, true);

            }
        }
    }
}
