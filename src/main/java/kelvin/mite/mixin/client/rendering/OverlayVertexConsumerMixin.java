package kelvin.mite.mixin.client.rendering;

import kelvin.mite.blocks.rendering.BlockBufferVertexConsumer;
import kelvin.mite.blocks.rendering.IOverlayVertexConsumer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@Mixin(OverlayVertexConsumer.class)
public abstract class OverlayVertexConsumerMixin extends FixedColorVertexConsumer implements IOverlayVertexConsumer {
    @Shadow
    private VertexConsumer vertexConsumer;
    @Shadow
    private Matrix4f textureMatrix;
    @Shadow
    private Matrix3f normalMatrix;
    @Shadow
    private float x;
    @Shadow
    private float y;
    @Shadow
    private float z;
    @Shadow
    private int u1;
    @Shadow
    private int v1;
    @Shadow
    private int light;
    @Shadow
    private float normalX;
    @Shadow
    private float normalY;
    @Shadow
    private float normalZ;

    private float flags;
    @Nullable
    private VertexFormatElement currentElement;

    public VertexConsumer flag(float flag) {
        this.flags = flag;
        return this;
    }

    private void init() {
        this.x = 0.0F;
        this.y = 0.0F;
        this.z = 0.0F;
        this.u1 = 0;
        this.v1 = 10;
        this.light = 15728880;
        this.normalX = 0.0F;
        this.normalY = 1.0F;
        this.normalZ = 0.0F;
        this.flags = 0.0F;
    }

    public void next() {
        Vec3f vec3f = new Vec3f(this.normalX, this.normalY, this.normalZ);
        vec3f.transform(this.normalMatrix);
        Direction direction = Direction.getFacing(vec3f.getX(), vec3f.getY(), vec3f.getZ());
        Vector4f vector4f = new Vector4f(this.x, this.y, this.z, 1.0F);
        vector4f.transform(this.textureMatrix);
        vector4f.rotate(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
        vector4f.rotate(Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
        vector4f.rotate(direction.getRotationQuaternion());
        float f = -vector4f.getX();
        float g = -vector4f.getY();
        if (vertexConsumer instanceof BlockBufferVertexConsumer) {
            ((BlockBufferVertexConsumer)this.vertexConsumer.vertex((double)this.x, (double)this.y, (double)this.z).color(1.0F, 1.0F, 1.0F, 1.0F).texture(f, g).overlay(this.u1, this.v1).light(this.light).normal(this.normalX, this.normalY, this.normalZ)).flag(this.flags).next();
        } else {
            if (vertexConsumer instanceof IOverlayVertexConsumer) {
                ((IOverlayVertexConsumer)this.vertexConsumer.vertex((double)this.x, (double)this.y, (double)this.z).color(1.0F, 1.0F, 1.0F, 1.0F).texture(f, g).overlay(this.u1, this.v1).light(this.light).normal(this.normalX, this.normalY, this.normalZ)).flag(this.flags).next();

            }
        }
        this.init();
    }

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

    public void vertex(float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ, float flags) {
        this.vertex((double)x, (double)y, (double)z);
        this.color(red, green, blue, alpha);
        this.texture(u, v);
        this.overlay(overlay);
        this.light(light);
        this.normal(normalX, normalY, normalZ);
        this.flag(flags);
        this.next();
    }

}
