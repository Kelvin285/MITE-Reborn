package kelvin.mite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {
	
	@Shadow
	private ScreenHandlerContext context;
	
	public boolean canUse(PlayerEntity player) {
		return true;
	}
}
