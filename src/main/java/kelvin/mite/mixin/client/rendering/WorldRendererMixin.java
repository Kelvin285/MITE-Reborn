package kelvin.mite.mixin.client.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.FastNoiseLite;
import kelvin.mite.main.resources.MoonIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    private float wind_time = 0;
    private float wind_strength = 2;
    private float season_time = 0;
    private FastNoiseLite noise = new FastNoiseLite();

    @Shadow
    private static final Identifier MOON_PHASES;

    static {
        MOON_PHASES = new MoonIdentifier("textures/environment/moon_phases.png");
    }

    @Inject(at=@At("HEAD"), method="renderLayer")
    private void renderLayer(RenderLayer renderLayer, MatrixStack matrices, double d, double e, double f, Matrix4f matrix4f, CallbackInfo info) {
        GameRenderer g;
        Mite.season_time = MinecraftClient.getInstance().world.getTimeOfDay() + Mite.StartingDay;
        Mite.day_time = MinecraftClient.getInstance().world.getTimeOfDay();

        GL15.glActiveTexture(GL15.GL_TEXTURE0);

        Shader shader = RenderSystem.getShader();
        if (shader.modelViewMat != null) {
            shader.modelViewMat.set(matrices.peek().getPositionMatrix());
        }

        //day length = 24000
        //days in month = 10
        //months in year = 12
        //days in year = 120
        //ticks in year = 2880000

        long world_time = Mite.season_time;

        season_time = world_time / Mite.TicksInYear;
        season_time %= 1;

        wind_time+=0.0025f;
        if (MinecraftClient.getInstance().world.isRaining()) {
            wind_time+=0.01f - 0.0025f;
        }

        if (shader.getUniform("wind_time") != null &&
        shader.getUniform("wind_dir") != null &&
                shader.getUniform("wind_strength") != null) {
            Vec3f windDir = new Vec3f(noise.GetNoise(wind_time, 0), 0, noise.GetNoise(0, wind_time));
            windDir.normalize();
            shader.getUniform("wind_time").set(wind_time);
            shader.getUniform("wind_dir").set(windDir.getX(), windDir.getY(), windDir.getZ());
            shader.getUniform("wind_strength").set(2.0f);
            //shader.getUniform("Sampler1").set(20);
            //GL20.glUniform1i(GL20.glGetUniformLocation(shader.getProgramRef(), "Sampler1"), 20);
            shader.getUniform("Season").set(season_time);
            shader.getUniform("DEF").set((float)d, (float)e, (float)f);
        }

    }

    @Inject(at=@At("RETURN"), method="renderLayer")
    private void renderLayerReturn(RenderLayer renderLayer, MatrixStack matrices, double d, double e, double f, Matrix4f matrix4f, CallbackInfo info) {
        Shader shader = RenderSystem.getShader();

        if (shader.getUniform("wind_time") != null &&
                shader.getUniform("wind_dir") != null &&
        shader.getUniform("wind_strength") != null) {
            Vec3f windDir = new Vec3f(noise.GetNoise(wind_time, 0), 0, noise.GetNoise(0, wind_time));
            windDir.normalize();
            shader.getUniform("wind_time").set(wind_time);
            shader.getUniform("wind_dir").set(windDir.getX(), windDir.getY(), windDir.getZ());
            shader.getUniform("wind_strength").set(2.0f);
            shader.getUniform("Season").set(season_time);
        }

    }
}
