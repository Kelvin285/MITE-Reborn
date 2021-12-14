package kelvin.mite.blocks.rendering;

import net.minecraft.client.render.BufferVertexConsumer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormatElement;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;

public interface BlockBufferVertexConsumer {
    void vertex(float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ, float flag);

    void quad(MatrixStack.Entry matrixEntry, BakedQuad quad, float[] brightnesses, float red, float green, float blue, int[] lights, int overlay, float[] flags, boolean useQuadColorData);

    VertexConsumer flag(float flag);
}
