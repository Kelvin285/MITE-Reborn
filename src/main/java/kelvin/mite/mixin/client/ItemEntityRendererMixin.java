package kelvin.mite.mixin.client;

import kelvin.mite.screens.ScreenItemEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin extends EntityRenderer<ItemEntity> {
    @Shadow
    private static float field_32924;
    @Shadow
    private static int MAX_COUNT_FOR_4_ITEMS_RENDERED = 48;
    @Shadow
    private static int MAX_COUNT_FOR_3_ITEMS_RENDERED = 32;
    @Shadow
    private static int MAX_COUNT_FOR_2_ITEMS_RENDERED = 16;
    @Shadow
    private static int MAX_COUNT_FOR_1_ITEM_RENDERED = 1;
    @Shadow
    private static float field_32929 = 0.0F;
    @Shadow
    private static float field_32930 = 0.0F;
    @Shadow
    private static float field_32931 = 0.09375F;
    @Shadow
    private ItemRenderer itemRenderer;
    private Random random = new Random();

    protected ItemEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Shadow
    private int getRenderedAmount(ItemStack stack) {
        return 0;
    }
    public void render(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        ItemStack itemStack = itemEntity.getStack();
        int j = itemStack.isEmpty() ? 187 : Item.getRawId(itemStack.getItem()) + itemStack.getDamage();
        this.random.setSeed((long)j);
        BakedModel bakedModel = this.itemRenderer.getModel(itemStack, itemEntity.world, (LivingEntity)null, itemEntity.getId());
        boolean bl = bakedModel.hasDepth();
        int k = this.getRenderedAmount(itemStack);
        float h = 0.25F;
        float l = MathHelper.sin(((float)itemEntity.getItemAge() + g) / 10.0F + itemEntity.uniqueOffset) * 0.1F + 0.1F;
        float m = bakedModel.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.getY();
        matrixStack.translate(0.0D, (double)(l + 0.25F * m), 0.0D);
        float n = itemEntity.getRotation(g);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(n));
        float o = bakedModel.getTransformation().ground.scale.getX();
        float p = bakedModel.getTransformation().ground.scale.getY();
        float q = bakedModel.getTransformation().ground.scale.getZ();
        float v;
        float w;
        if (!bl) {
            float r = -0.0F * (float)(k - 1) * 0.5F * o;
            v = -0.0F * (float)(k - 1) * 0.5F * p;
            w = -0.09375F * (float)(k - 1) * 0.5F * q;
            matrixStack.translate((double)r, (double)v, (double)w);
        }

        for(int u = 0; u < k; ++u) {
            matrixStack.push();

            //removing the part that gives the item a random position

            this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, i, OverlayTexture.DEFAULT_UV, bakedModel);
            matrixStack.pop();
            if (!bl) {
                matrixStack.translate((double)(0.0F * o), (double)(0.0F * p), (double)(0.09375F * q));
            }
        }

        matrixStack.pop();
        super.render(itemEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(ItemEntity itemEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
