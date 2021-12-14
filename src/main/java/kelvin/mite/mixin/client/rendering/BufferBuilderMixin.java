package kelvin.mite.mixin.client.rendering;

import kelvin.mite.blocks.rendering.BlockBufferVertexConsumer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vector4f;
import org.lwjgl.system.MemoryStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@Mixin(BufferBuilder.class)
public abstract class BufferBuilderMixin extends FixedColorVertexConsumer implements BufferVertexConsumer, BlockBufferVertexConsumer {

    @Shadow
    private boolean textured;
    @Shadow
    private boolean hasOverlay;
    @Shadow
    private int elementOffset;


    public void quad(MatrixStack.Entry matrixEntry, BakedQuad quad, float[] brightnesses, float red, float green, float blue, int[] lights, int overlay, float[] flags, boolean useQuadColorData) {

        int[] vertexData = quad.getVertexData();
        Vec3i vec3i = quad.getFace().getVector();
        Vec3f normal = new Vec3f((float)vec3i.getX(), (float)vec3i.getY(), (float)vec3i.getZ());
        Matrix4f matrix4f = matrixEntry.getPositionMatrix();
        normal.transform(matrixEntry.getNormalMatrix());
        int length = vertexData.length / 8;
        MemoryStack memoryStack = MemoryStack.stackPush();

        try {
            ByteBuffer byteBuffer = memoryStack.malloc(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL.getVertexSize());
            IntBuffer intBuffer = byteBuffer.asIntBuffer();

            for(int i = 0; i < length; ++i) {
                intBuffer.clear();
                intBuffer.put(vertexData, i * 8, 8);
                float x = byteBuffer.getFloat(0);
                float y = byteBuffer.getFloat(4);
                float z = byteBuffer.getFloat(8);
                float R;
                float G;
                float B;
                float U;
                float V;
                if (useQuadColorData) {
                    float l = (float)(byteBuffer.get(12) & 255) / 255.0F;
                    U = (float)(byteBuffer.get(13) & 255) / 255.0F;
                    V = (float)(byteBuffer.get(14) & 255) / 255.0F;
                    R = l * brightnesses[i] * red;
                    G = U * brightnesses[i] * green;
                    B = V * brightnesses[i] * blue;
                } else {
                    R = brightnesses[i] * red;
                    G = brightnesses[i] * green;
                    B = brightnesses[i] * blue;
                }

                int light = lights[i];
                U = byteBuffer.getFloat(16);
                V = byteBuffer.getFloat(20);

                //float flag = byteBuffer.getFloat(24);
                float flag = flags[i];
                Vector4f position = new Vector4f(x, y, z, 1.0F);
                position.transform(matrix4f);
                this.vertex(position.getX(), position.getY(), position.getZ(), R, G, B, 1.0F, U, V, overlay, light, normal.getX(), normal.getY(), normal.getZ(), flag);
            }
        } catch (Throwable var33) {
            if (memoryStack != null) {
                try {
                    memoryStack.close();
                } catch (Throwable var32) {
                    var33.addSuppressed(var32);
                }
            }

            throw var33;
        }

        if (memoryStack != null) {
            memoryStack.close();
        }

    }

    public void vertex(float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ, float flag) {
        if (this.colorFixed) {
            throw new IllegalStateException();
        } else {

            this.putFloat(0, x);
            this.putFloat(4, y);
            this.putFloat(8, z);
            this.putByte(12, (byte)((int)(red * 255.0F)));
            this.putByte(13, (byte)((int)(green * 255.0F)));
            this.putByte(14, (byte)((int)(blue * 255.0F)));
            this.putByte(15, (byte)((int)(alpha * 255.0F)));
            this.putFloat(16, u);
            this.putFloat(20, v);
            byte j;
            if (this.hasOverlay) {
                this.putShort(24, (short)(overlay & '\uffff'));
                this.putShort(26, (short)(overlay >> 16 & '\uffff'));
                j = 28;
            } else {
                j = 24;
            }

            this.putShort(j + 0, (short)(light & '\uffff'));
            this.putShort(j + 2, (short)(light >> 16 & '\uffff'));
            this.putByte(j + 4, BufferVertexConsumer.packByte(normalX));
            this.putByte(j + 5, BufferVertexConsumer.packByte(normalY));
            this.putByte(j + 6, BufferVertexConsumer.packByte(normalZ));
            this.putFloat(j + 7, flag);
            this.elementOffset += j + 12;
            //this.elementOffset += j + 9;
            this.next();
        }
    }

    /*
    default VertexConsumer color(int red, int green, int blue, int alpha) {
        VertexFormatElement vertexFormatElement = this.getCurrentElement();
        if (vertexFormatElement.getType() != VertexFormatElement.Type.COLOR) {
            return this;
        } else if (vertexFormatElement.getDataType() == VertexFormatElement.DataType.UBYTE && vertexFormatElement.getLength() == 4) {
            this.putByte(0, (byte)red);
            this.putByte(1, (byte)green);
            this.putByte(2, (byte)blue);
            this.putByte(3, (byte)alpha);
            this.nextElement();
            return this;
        } else {
            throw new IllegalStateException();
        }
    }
     */
    @Override
    public VertexConsumer flag(float flag) {
        VertexFormatElement vertexFormatElement = this.getCurrentElement();
        if (vertexFormatElement.getType() != VertexFormatElement.Type.GENERIC) {
            return this;
        } else if (vertexFormatElement.getDataType() == VertexFormatElement.DataType.FLOAT && vertexFormatElement.getLength() == 1) {
            this.putFloat(0, flag);
            this.nextElement();
            return this;
        } else {
            throw new IllegalStateException("Error when setting flags in Mite chunk mesh!");
        }
    }
}
