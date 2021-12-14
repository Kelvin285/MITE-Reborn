package kelvin.mite.mixin.client.rendering;

import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadView;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.NormalHelper;
import net.fabricmc.fabric.impl.client.indigo.renderer.mesh.EncodingFormat;
import net.fabricmc.fabric.impl.client.indigo.renderer.mesh.MutableQuadViewImpl;
import net.fabricmc.fabric.impl.client.indigo.renderer.mesh.QuadViewImpl;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.*;
import org.lwjgl.system.MemoryStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static net.fabricmc.fabric.impl.client.indigo.renderer.mesh.EncodingFormat.*;

@Mixin(MutableQuadViewImpl.class)
public abstract class MutableQuadViewImplMixin extends QuadViewImpl implements QuadEmitter {


    @Overwrite
    public final MutableQuadViewImpl fromVanilla(BakedQuad quad, RenderMaterial material, Direction cullFace) {
       //modified vertex size: 36
        //regular vertex size: 32
        //header size: 3
        //total size: 39
        MutableQuadViewImpl mutable = (MutableQuadViewImpl)(Object)this;

        int[] vertexData = quad.getVertexData();
        Vec3i vec3i = quad.getFace().getVector();
        Vec3f normal = new Vec3f((float)vec3i.getX(), (float)vec3i.getY(), (float)vec3i.getZ());

        int length = vertexData.length / 8;
        MemoryStack memoryStack = MemoryStack.stackPush();

        int I = 3;
        for (int i = 0; i < quad.getVertexData().length; i++) {
            if (I < data.length) {
                data[I] = quad.getVertexData()[i];
            }
            I++;
            if ((I - 3) % 8 == 0 && I > 0) {
                I++;
            }
        }

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
                Color color = new Color(this.spriteColor(i, 0));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                if (quad.hasColor()) {
                    float l = (float)(byteBuffer.get(12) & 255) / 255.0F;
                    U = (float)(byteBuffer.get(13) & 255) / 255.0F;
                    V = (float)(byteBuffer.get(14) & 255) / 255.0F;
                    R = l * red;
                    G = U * green;
                    B = V * blue;
                } else {
                    //R = brightnesses[i] * red;
                    //G = brightnesses[i] * green;
                    //B = brightnesses[i] * blue;
                    R = red;
                    G = green;
                    B = blue;
                }

                U = byteBuffer.getFloat(16);
                V = byteBuffer.getFloat(20);

                //float flag = byteBuffer.getFloat(24);
                //float flag = flags[i];
                //Vector4f position = new Vector4f(x, y, z, 1.0F);
                //position.transform(matrix4f);
                this.pos(i, x, y, z);
                this.sprite(i, 0, U, V);


                this.spriteColor(i, 0, new Color(Math.max(0, Math.min(1, R)), Math.max(0, Math.min(1, G)), Math.max(0, Math.min(1, B)), 1).getRGB());
                this.lightmap(i, 15);
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


        try {
            int HEADER_BITS = EncodingFormat.class.getDeclaredField("HEADER_BITS").getInt((MutableQuadViewImpl)(Object)this);
            data[baseIndex + HEADER_BITS] = (int)EncodingFormat.class.getDeclaredMethod("cullFace", int.class, Direction.class).invoke(null, 0, cullFace);
        } catch (Exception e) {
            e.printStackTrace();
        }

        nominalFace(quad.getFace());
        colorIndex(quad.getColorIndex());
        material(material);
        tag(0);
        shade(quad.hasShade());
        isGeometryInvalid = true;
        return (MutableQuadViewImpl)(Object)this;
    }
}
