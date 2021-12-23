package kelvin.mite.mixin.client.rendering;

import kelvin.mite.blocks.rendering.BlockBufferVertexConsumer;
import net.fabricmc.fabric.impl.client.rendering.fluid.FluidRendererHookContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {


    @Shadow
    private static final float field_32781 = 0.8888889F;
    @Shadow
    private final Sprite[] lavaSprites = new Sprite[2];
    @Shadow
    private final Sprite[] waterSprites = new Sprite[2];
    @Shadow
    private Sprite waterOverlaySprite;

    @Shadow
    private static boolean isSameFluid(BlockView world, BlockPos pos, Direction side, FluidState state) {
        return false;
    }

    @Shadow
    private static boolean isSideCovered(BlockView world, Direction direction, float f, BlockPos pos, BlockState state) {
        return false;
    }

    @Shadow
    private static boolean isSideCovered(BlockView world, BlockPos pos, Direction direction, float maxDeviation) {
        return false;
    }

    @Shadow
    private static boolean isOppositeSideCovered(BlockView world, BlockPos pos, BlockState state, Direction direction) {
        return false;
    }

    @Shadow
    private static boolean shouldRenderSide(BlockRenderView world, BlockPos pos, FluidState fluidState, BlockState blockState, Direction direction) {
        return false;
    }

    @Shadow
    private int getLight(BlockRenderView world, BlockPos pos) {
        return 0;
    }

    @Shadow
    private float getNorthWestCornerFluidHeight(BlockView world, BlockPos pos, Fluid fluid) {
        return 0;
    }

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, FluidState state, CallbackInfoReturnable<Boolean> info) {
        boolean bl = state.isIn(FluidTags.LAVA);
        Sprite[] sprites = bl ? this.lavaSprites : this.waterSprites;
        BlockState blockState = world.getBlockState(pos);
        int i = bl ? 16777215 : BiomeColors.getWaterColor(world, pos);
        float f = (float)(i >> 16 & 255) / 255.0F;
        float g = (float)(i >> 8 & 255) / 255.0F;
        float h = (float)(i & 255) / 255.0F;
        boolean bl2 = !isSameFluid(world, pos, Direction.UP, state);
        boolean bl3 = shouldRenderSide(world, pos, state, blockState, Direction.DOWN) && !isSideCovered(world, pos, Direction.DOWN, 0.8888889F);
        boolean bl4 = shouldRenderSide(world, pos, state, blockState, Direction.NORTH);
        boolean bl5 = shouldRenderSide(world, pos, state, blockState, Direction.SOUTH);
        boolean bl6 = shouldRenderSide(world, pos, state, blockState, Direction.WEST);
        boolean bl7 = shouldRenderSide(world, pos, state, blockState, Direction.EAST);
        if (!bl2 && !bl3 && !bl7 && !bl6 && !bl4 && !bl5) {
            info.setReturnValue(false);
            return;
        } else {
            boolean bl8 = false;
            float j = world.getBrightness(Direction.DOWN, true);
            float k = world.getBrightness(Direction.UP, true);
            float l = world.getBrightness(Direction.NORTH, true);
            float m = world.getBrightness(Direction.WEST, true);
            float n = this.getNorthWestCornerFluidHeight(world, pos, state.getFluid());
            float o = this.getNorthWestCornerFluidHeight(world, pos.south(), state.getFluid());
            float p = this.getNorthWestCornerFluidHeight(world, pos.east().south(), state.getFluid());
            float q = this.getNorthWestCornerFluidHeight(world, pos.east(), state.getFluid());
            double d = (double)(pos.getX() & 15);
            double e = (double)(pos.getY() & 15);
            double r = (double)(pos.getZ() & 15);
            float s = 0.001F;
            float t = bl3 ? 0.001F : 0.0F;
            float u;
            float w;
            float y;
            float aa;
            float x;
            float z;
            float ab;
            float ai;
            float aj;
            if (bl2 && !isSideCovered(world, pos, Direction.UP, Math.min(Math.min(n, o), Math.min(p, q)))) {
                bl8 = true;
                n -= 0.001F;
                o -= 0.001F;
                p -= 0.001F;
                q -= 0.001F;
                Vec3d vec3d = state.getVelocity(world, pos);
                float v;
                Sprite sprite;
                float ac;
                float ad;
                float ae;
                float af;
                if (vec3d.x == 0.0D && vec3d.z == 0.0D) {
                    sprite = sprites[0];
                    u = sprite.getFrameU(0.0D);
                    v = sprite.getFrameV(0.0D);
                    w = u;
                    x = sprite.getFrameV(16.0D);
                    y = sprite.getFrameU(16.0D);
                    z = x;
                    aa = y;
                    ab = v;
                } else {
                    sprite = sprites[1];
                    ac = (float) MathHelper.atan2(vec3d.z, vec3d.x) - 1.5707964F;
                    ad = MathHelper.sin(ac) * 0.25F;
                    ae = MathHelper.cos(ac) * 0.25F;
                    af = 8.0F;
                    u = sprite.getFrameU((double)(8.0F + (-ae - ad) * 16.0F));
                    v = sprite.getFrameV((double)(8.0F + (-ae + ad) * 16.0F));
                    w = sprite.getFrameU((double)(8.0F + (-ae + ad) * 16.0F));
                    x = sprite.getFrameV((double)(8.0F + (ae + ad) * 16.0F));
                    y = sprite.getFrameU((double)(8.0F + (ae + ad) * 16.0F));
                    z = sprite.getFrameV((double)(8.0F + (ae - ad) * 16.0F));
                    aa = sprite.getFrameU((double)(8.0F + (ae - ad) * 16.0F));
                    ab = sprite.getFrameV((double)(8.0F + (-ae - ad) * 16.0F));
                }

                float spr = (u + w + y + aa) / 4.0F;
                ac = (v + x + z + ab) / 4.0F;
                ad = (float)sprites[0].getWidth() / (sprites[0].getMaxU() - sprites[0].getMinU());
                ae = (float)sprites[0].getHeight() / (sprites[0].getMaxV() - sprites[0].getMinV());
                af = 4.0F / Math.max(ae, ad);
                u = MathHelper.lerp(af, u, spr);
                w = MathHelper.lerp(af, w, spr);
                y = MathHelper.lerp(af, y, spr);
                aa = MathHelper.lerp(af, aa, spr);
                v = MathHelper.lerp(af, v, ac);
                x = MathHelper.lerp(af, x, ac);
                z = MathHelper.lerp(af, z, ac);
                ab = MathHelper.lerp(af, ab, ac);
                int ag = this.getLight(world, pos);
                float ah = k * f;
                ai = k * g;
                aj = k * h;
                this.vertex(vertexConsumer, d + 0.0D, e + (double)n, r + 0.0D, ah, ai, aj, u, v, ag);
                this.vertex(vertexConsumer, d + 0.0D, e + (double)o, r + 1.0D, ah, ai, aj, w, x, ag);
                this.vertex(vertexConsumer, d + 1.0D, e + (double)p, r + 1.0D, ah, ai, aj, y, z, ag);
                this.vertex(vertexConsumer, d + 1.0D, e + (double)q, r + 0.0D, ah, ai, aj, aa, ab, ag);
                if (state.method_15756(world, pos.up())) {
                    this.vertex(vertexConsumer, d + 0.0D, e + (double)n, r + 0.0D, ah, ai, aj, u, v, ag);
                    this.vertex(vertexConsumer, d + 1.0D, e + (double)q, r + 0.0D, ah, ai, aj, aa, ab, ag);
                    this.vertex(vertexConsumer, d + 1.0D, e + (double)p, r + 1.0D, ah, ai, aj, y, z, ag);
                    this.vertex(vertexConsumer, d + 0.0D, e + (double)o, r + 1.0D, ah, ai, aj, w, x, ag);
                }
            }

            if (bl3) {
                u = sprites[0].getMinU();
                w = sprites[0].getMaxU();
                y = sprites[0].getMinV();
                aa = sprites[0].getMaxV();
                int v = this.getLight(world, pos.down());
                x = j * f;
                z = j * g;
                ab = j * h;
                this.vertex(vertexConsumer, d, e + (double)t, r + 1.0D, x, z, ab, u, aa, v);
                this.vertex(vertexConsumer, d, e + (double)t, r, x, z, ab, u, y, v);
                this.vertex(vertexConsumer, d + 1.0D, e + (double)t, r, x, z, ab, w, y, v);
                this.vertex(vertexConsumer, d + 1.0D, e + (double)t, r + 1.0D, x, z, ab, w, aa, v);
                bl8 = true;
            }

            int light = this.getLight(world, pos);

            for(int i1 = 0; i1 < 4; ++i1) {
                double v;
                double v1;
                double vec3d;
                double ac;
                Direction ae;
                boolean af;
                if (i1 == 0) {
                    y = n;
                    aa = q;
                    v = d;
                    vec3d = d + 1.0D;
                    v1 = r + 0.0010000000474974513D;
                    ac = r + 0.0010000000474974513D;
                    ae = Direction.NORTH;
                    af = bl4;
                } else if (i1 == 1) {
                    y = p;
                    aa = o;
                    v = d + 1.0D;
                    vec3d = d;
                    v1 = r + 1.0D - 0.0010000000474974513D;
                    ac = r + 1.0D - 0.0010000000474974513D;
                    ae = Direction.SOUTH;
                    af = bl5;
                } else if (i1 == 2) {
                    y = o;
                    aa = n;
                    v = d + 0.0010000000474974513D;
                    vec3d = d + 0.0010000000474974513D;
                    v1 = r + 1.0D;
                    ac = r;
                    ae = Direction.WEST;
                    af = bl6;
                } else {
                    y = q;
                    aa = p;
                    v = d + 1.0D - 0.0010000000474974513D;
                    vec3d = d + 1.0D - 0.0010000000474974513D;
                    v1 = r;
                    ac = r + 1.0D;
                    ae = Direction.EAST;
                    af = bl7;
                }

                if (af && !isSideCovered(world, pos, ae, Math.max(y, aa))) {
                    bl8 = true;
                    BlockPos ag = pos.offset(ae);
                    Sprite ah = sprites[1];
                    if (!bl) {
                        Block block = world.getBlockState(ag).getBlock();
                        if (block instanceof TransparentBlock || block instanceof LeavesBlock) {
                            ah = this.waterOverlaySprite;
                        }
                    }

                    ai = ah.getFrameU(0.0D);
                    aj = ah.getFrameU(8.0D);
                    float ak = ah.getFrameV((double)((1.0F - y) * 16.0F * 0.5F));
                    float al = ah.getFrameV((double)((1.0F - aa) * 16.0F * 0.5F));
                    float am = ah.getFrameV(8.0D);
                    float an = i1 < 2 ? l : m;
                    float ao = k * an * f;
                    float ap = k * an * g;
                    float aq = k * an * h;
                    this.vertex(vertexConsumer, v, e + (double)y, v1, ao, ap, aq, ai, ak, light);
                    this.vertex(vertexConsumer, vec3d, e + (double)aa, ac, ao, ap, aq, aj, al, light);
                    this.vertex(vertexConsumer, vec3d, e + (double)t, ac, ao, ap, aq, aj, am, light);
                    this.vertex(vertexConsumer, v, e + (double)t, v1, ao, ap, aq, ai, am, light);
                    if (ah != this.waterOverlaySprite) {
                        this.vertex(vertexConsumer, v, e + (double)t, v1, ao, ap, aq, ai, am, light);
                        this.vertex(vertexConsumer, vec3d, e + (double)t, ac, ao, ap, aq, aj, am, light);
                        this.vertex(vertexConsumer, vec3d, e + (double)aa, ac, ao, ap, aq, aj, al, light);
                        this.vertex(vertexConsumer, v, e + (double)y, v1, ao, ap, aq, ai, ak, light);
                    }
                }
            }

            info.setReturnValue(bl8);
            return;
        }
    }

    private void vertex(VertexConsumer vertexConsumer, double x, double y, double z, float red, float green, float blue, float u, float v, int light) {
        float flag = 0;
        if (y % 1 > 0.5 || y % 1 == 0) {
            flag = 2;
        }
        ((BlockBufferVertexConsumer)vertexConsumer.vertex(x, y, z).color(red, green, blue, 1.0F).texture(u, v).light(light).normal(0.0F, 1.0F, 0.0F)).flag(flag).next();
    }
}
