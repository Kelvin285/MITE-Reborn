package kelvin.mite.mixin.client.toasts;

import net.minecraft.client.toast.RecipeToast;
import net.minecraft.client.toast.Toast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeToast.class)
public abstract class RecipeToastMixin {

    @Inject(at = @At("HEAD"), method = "draw", cancellable = true)
    private void draw(CallbackInfoReturnable<Toast.Visibility> info) {
        info.setReturnValue(Toast.Visibility.HIDE);
    }
}

