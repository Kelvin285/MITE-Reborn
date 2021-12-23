package kelvin.mite.mixin.client;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    private HitResult crosshairTarget;
    @Shadow
    public ClientPlayerEntity player;
    @Shadow
    public ClientPlayerInteractionManager interactionManager;

    @Inject(at=@At("HEAD"), method="doAttack", cancellable = true)
    private void doAttack(CallbackInfo info) {
        if (crosshairTarget != null && player != null) {
            if (crosshairTarget.getType() == HitResult.Type.ENTITY) {
                if (crosshairTarget.getPos().distanceTo(player.getEyePos()) > interactionManager.getReachDistance() - 1.25f) {
                    player.swingHand(Hand.MAIN_HAND);
                    info.cancel();
                }
            }
        }
    }
}
