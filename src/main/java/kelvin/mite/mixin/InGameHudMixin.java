package kelvin.mite.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

import java.util.Random;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow
    public MinecraftClient client;
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;
    @Shadow
    private int ticks;
    @Shadow
    private Random random;
    @Shadow
    private int renderHealthValue;
    @Shadow
    private long heartJumpEndTick;

    @Shadow
    private PlayerEntity getCameraPlayer() {
        return null;
    }

    @Shadow
    private int getHeartCount(LivingEntity entity) {
        return 0;
    }

    @Shadow
    private LivingEntity getRiddenEntity() {
        return null;
    }

    @Shadow
    private void renderHealthBar(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {
    }

    @Shadow
    private int getHeartRows(int heartCount) {
        return 0;
    }

        @Inject(at = @At("HEAD"), method = "renderStatusBars", cancellable = true)
    public void renderStatusBars(MatrixStack matrices, CallbackInfo info) {

        PlayerEntity playerEntity = this.getCameraPlayer();

        LivingEntity livingEntity = this.getRiddenEntity();

        if (livingEntity != null) {
            return;
        } else {
            info.cancel();
        }

        HungerManager hungerManager = playerEntity.getHungerManager();
        int foodLevel = hungerManager.getFoodLevel();
        int SW_MINUS = this.scaledWidth / 2 - 91;
        int AIR_X = this.scaledWidth / 2 + 91;
        int SH = this.scaledHeight - 39;

        int player_health = MathHelper.ceil(playerEntity.getHealth());
        boolean heart_jump = this.heartJumpEndTick > (long)this.ticks && (this.heartJumpEndTick - (long)this.ticks) / 3L % 2L == 1L;
        long measuring_time = Util.getMeasuringTimeMs();

        float rendered_health = Math.max((float)playerEntity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH), (float)Math.max(renderHealthValue, player_health));
        int absorption = MathHelper.ceil(playerEntity.getAbsorptionAmount());
        int health_with_absorption = MathHelper.ceil((rendered_health + (float)absorption) / 2.0F / 10.0F);
        int max_absorption_or_three = Math.max(10 - (health_with_absorption - 2), 3);
        int armor_y = SH - (health_with_absorption - 1) * max_absorption_or_three - 10;
        int scaled_height_minus_ten = SH - 10;
        int armor = playerEntity.getArmor();
        int regen_bob = -1;
        if (playerEntity.hasStatusEffect(StatusEffects.REGENERATION)) {
            regen_bob = this.ticks % MathHelper.ceil(rendered_health + 5.0F);
        }

        for(int i = 0; i < 10; ++i) {
            if (armor > 0) {
                int armor_x = SW_MINUS + i * 8;
                if (i * 2 + 1 < armor) {
                    this.drawTexture(matrices, armor_x, armor_y, 34, 9, 9, 9);
                }

                if (i * 2 + 1 == armor) {
                    this.drawTexture(matrices, armor_x, armor_y, 25, 9, 9, 9);
                }

                if (i * 2 + 1 > armor) {
                    this.drawTexture(matrices, armor_x, armor_y, 16, 9, 9, 9);
                }
            }
        }

        this.renderHealthBar(matrices, playerEntity, SW_MINUS, SH, max_absorption_or_three, regen_bob, rendered_health, player_health, renderHealthValue, absorption, heart_jump);

        int heartCount = this.getHeartCount(livingEntity);
        int aa;
        int ab;
        int ad;
        if (heartCount == 0) {
            int hungerCount = getHungerCount(playerEntity);
            /**HUNGER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!HUNGER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!HUNGER!!!!!!!!!!!!!!!!!!!*/
            for(int i = 0; i < hungerCount; ++i) {
                aa = SH;
                ab = 16;
                int ac = 0;
                if (playerEntity.hasStatusEffect(StatusEffects.HUNGER)) {
                    ab += 36;
                    ac = 13;
                }

                if (playerEntity.getHungerManager().getSaturationLevel() <= 0.0F && this.ticks % (foodLevel * 3 + 1) == 0) {
                    aa = SH + (this.random.nextInt(3) - 1);
                }

                ad = AIR_X - i * 8 - 9;
                this.drawTexture(matrices, ad, aa, 16 + ac * 9, 27, 9, 9);
                if (i * 2 + 1 < foodLevel) {
                    this.drawTexture(matrices, ad, aa, ab + 36, 27, 9, 9);
                }

                if (i * 2 + 1 == foodLevel) {
                    this.drawTexture(matrices, ad, aa, ab + 45, 27, 9, 9);
                }
            }

            scaled_height_minus_ten -= 10;
        }

        int max_air = playerEntity.getMaxAir();
        int current_air = Math.min(playerEntity.getAir(), max_air);
        if (playerEntity.isSubmergedIn(FluidTags.WATER) || current_air < max_air) {
            ab = this.getHeartRows(heartCount) - 1;
            scaled_height_minus_ten -= ab * 10;
            int ah = MathHelper.ceil((double)(current_air - 2) * 10.0D / (double)max_air);
            ad = MathHelper.ceil((double)current_air * 10.0D / (double)max_air) - ah;

            for(int aj = 0; aj < ah + ad; ++aj) {
                if (aj < ah) {
                    this.drawTexture(matrices, AIR_X - aj * 8 - 9, scaled_height_minus_ten, 16, 18, 9, 9);
                } else {
                    this.drawTexture(matrices, AIR_X - aj * 8 - 9, scaled_height_minus_ten, 25, 18, 9, 9);
                }
            }
        }


    }

    private int getHungerCount(PlayerEntity playerEntity) {
        return 3 + (int)Math.floor(playerEntity.experienceLevel / 5.0);
    }
}
