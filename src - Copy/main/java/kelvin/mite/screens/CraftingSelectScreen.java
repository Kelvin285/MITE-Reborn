package kelvin.mite.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class CraftingSelectScreen extends Screen {
    public PlayerEntity player;

    public static final Identifier TEXTURE = new Identifier("mite:textures/gui/container/crafting.png");

    public MinecraftClient client;

    public Slot[] inventory = new Slot[9 * 4];
    public Slot selected_slot;
    public Slot hovered_slot;

    public int x, y, width, height;

    public int mouseX, mouseY;

    public CraftingSelectScreen(Text title, PlayerEntity player) {
        super(title);
        this.player = player;
        CraftingScreen screen;
        client = MinecraftClient.getInstance();
        Window window = client.getWindow();

        this.width = 176;
        this.height = 166;
        this.x = window.getScaledWidth() / 2 - width / 2;
        this.y = window.getScaledHeight() / 2 - height / 2;

        for (int i = 0; i < 9; i++) {
            inventory[i] = new Slot(player.getInventory(), i, 8 + i * 18, 142);
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 3; y++) {
                inventory[x + y * 9 + 9] = new Slot(player.getInventory(), x + y * 9 + 9, 8 + x * 18, 84 + y * 18);
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        Window window = client.getWindow();
        //width = 176, height = 166
        RenderSystem.setShaderTexture(0, TEXTURE);

        int width = 176;
        int height = 166;
        this.x = window.getScaledWidth() / 2 - width / 2;
        this.y = window.getScaledHeight() / 2 - height / 2;

        //draw background image for crafting table
        this.drawTexture(matrices, x + 44, y + 8, 0, 167, 88, 70);

        //draw crafting table
        renderCraftingTable(matrices);
        RenderSystem.setShaderTexture(0, TEXTURE);

        //draw inventory foreground
        this.drawTexture(matrices, x, y, 0, 0, width, height);

        if (hovered_slot != null) {
            fill(matrices, x + hovered_slot.x, y + hovered_slot.y, x + hovered_slot.x + 16, y + hovered_slot.y + 16, -2130706433);
        }

        if (selected_slot != null) {
            this.drawTexture(matrices, x + selected_slot.x, y + selected_slot.y, 176, 30, 16, 16);
        }

        hovered_slot = null;
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                this.drawSlot(matrices, inventory[i]);
            }
        }
        if (client.mouse.wasLeftButtonClicked()) {
            selected_slot = hovered_slot;
        }

        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        InventoryScreen screen;

    }

    public float crafting_pitch = -30;
    public float crafting_yaw = 0;
    //    public void renderItem(ItemStack stack, Mode transformationType, int light, int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int seed) {
    public void renderCraftingTable(MatrixStack matrices) {
        ItemStack crafting_table = new ItemStack(Items.CRAFTING_TABLE);
        RenderSystem.enableDepthTest();
        //44, 8, 88, 70
        int X = 44;
        int Y = 8;
        int W = 88;
        int H = 70;
        ItemEntity entity = new ScreenItemEntity(player.world, 0, 0, 0, crafting_table);
        drawItem(x + X + W / 2, y + Y + H * 2.0F, 0, 300, mouseX, mouseY, crafting_yaw, crafting_pitch, entity);
    }

    public void drawItem(double x, double y, double z, int size, float mouseX, float mouseY, float yaw, float pitch, ItemEntity entity) {
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate(x, y, 1050.0D + z);
        matrixStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        MatrixStack matrixStack2 = new MatrixStack();
        matrixStack2.translate(0.0D, 0.0D, 1000.0D);
        matrixStack2.scale((float)size, (float)size, (float)size);
        Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);

        Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(pitch); // pitch?
        Quaternion quaternion3 = Vec3f.POSITIVE_Y.getDegreesQuaternion(yaw);

        quaternion.hamiltonProduct(quaternion2);
        quaternion.hamiltonProduct(quaternion3);

        matrixStack2.multiply(quaternion);
        float i = entity.getYaw();
        float j = entity.getPitch();
        entity.setYaw(yaw);
        entity.setPitch(pitch);
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        quaternion2.conjugate();
        entityRenderDispatcher.setRotation(quaternion2);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> {
            // 0 (tickDelta)
            float l = MathHelper.sin(((float)entity.getItemAge() + 0) / 10.0F + entity.uniqueOffset) * 0.1F + 0.1F;

            entityRenderDispatcher.render(entity, 0.0D, -l, 0.0D, 0.0F, 0, matrixStack2, immediate, 15728880);

        });

        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        entity.setYaw(i);
        entity.setPitch(j);
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }


    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (client.options.keyInventory.matchesKey(keyCode, scanCode)) {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            selected_slot = hovered_slot;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void drawSlot(MatrixStack matrices, Slot slot) {
        int i = this.x + slot.x;
        int j = this.y + slot.y;
        ItemStack itemStack = slot.getStack();

        if (itemStack != null) {
            if (itemStack.getItem() != null) {
                if (mouseX >= i && mouseY >= j && mouseX < i + 18 && mouseY < j + 18) {
                    //fill(matrices, i, j, i + 16, j + 16, -2130706433);
                    hovered_slot = slot;
                }

                String str = "";
                if (itemStack.getCount() > 1) {
                    str = itemStack.getCount()+"";
                }

                RenderSystem.enableDepthTest();
                this.itemRenderer.renderInGuiWithOverrides(this.client.player, itemStack, i, j, slot.x + slot.y * this.width);
                this.itemRenderer.renderGuiItemOverlay(this.textRenderer, itemStack, i, j, str);
            }
        }

        this.itemRenderer.zOffset = 0.0F;
        this.setZOffset(0);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void onClose() {
        super.onClose();
    }
}
