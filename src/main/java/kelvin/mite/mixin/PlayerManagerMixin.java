package kelvin.mite.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.Collectors;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(at = @At("RETURN"), method = "onPlayerConnect")
    private void onPlayerConnectReturn(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {
        player.unlockRecipes(
                player.server.getRecipeManager().values().stream()
                        .filter(recipe -> {
                            for (var ingredient : recipe.getIngredients()) {
                                if (recipe.getOutput().getItem() == Items.BEDROCK) {
                                    return false;
                                }
                                for (var stack : ingredient.getMatchingStacks()) {
                                    if (stack.getItem() == Items.BEDROCK) {
                                        return false;
                                    }
                                }
                            }
                            return true;
                        })
                        .collect(Collectors.toList()));
    }

}
