package kelvin.mite.mixin.client;

import com.mojang.authlib.GameProfile;
import kelvin.mite.main.resources.MiteHungerManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.Tag;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity  {

    @Shadow
    protected int ticksLeftToDoubleTapSprint;
    @Shadow
    public int ticksSinceSprintingChanged;
    @Shadow
    public MinecraftClient client;
    @Shadow
    public Input input;

    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Shadow
    public boolean isWalking() {
        return false;
    }

    private int tick_hunger = 0; // controls the hunger bar in the GUI
    @Inject(at = @At("HEAD"), method = "tickMovement")
    public void tickMovement(CallbackInfo info) {
        tick_hunger = this.hungerManager.getFoodLevel();
        if (tick_hunger > 0) {
            this.hungerManager.setFoodLevel(tick_hunger + 7);
        }
    }

    @Inject(at = @At("RETURN"), method = "tickMovement")
    public void tickMovementReturn(CallbackInfo info) {
        this.hungerManager.setFoodLevel(tick_hunger);
    }

    public void baseTick() {
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((int)Math.floor(experienceLevel / 5) * 2 + 6);

        super.baseTick();
    }

    @Override
    public void swimUpward(Tag<Fluid> fluid) {
        if (!this.hasStatusEffect(StatusEffects.SLOWNESS)) {
            super.swimUpward(fluid);
        }
    }
}
