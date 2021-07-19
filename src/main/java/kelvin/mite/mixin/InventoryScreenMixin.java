package kelvin.mite.mixin;

import java.awt.Color;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import kelvin.mite.main.resources.Resources;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {
	
	private PlayerEntity player;
	
	public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}
	
	@Inject(at = @At("RETURN"), method = "<init>")
	public void Constructor(PlayerEntity player, CallbackInfo info) {
		this.player = player;
	}

	@Shadow
	private RecipeBookWidget recipeBook;
	@Shadow
	private static Identifier RECIPE_BUTTON_TEXTURE = new Identifier("textures/gui/recipe_button.png");
	@Shadow
	private float mouseX;
	@Shadow
	private float mouseY;
	@Shadow
	private boolean open;
	@Shadow
	private boolean narrow;
	@Shadow
	private boolean mouseDown;
	
	private TexturedButtonWidget button;
	
	private int RED = new Color(159, 0, 0).getRGB();
	private int YELLOW = new Color(255, 153, 0).getRGB();
	
	@Inject(at = @At("HEAD"), method = "drawForeground", cancellable = true)
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY, CallbackInfo info) {
		//157, 101

		PlayerInventory inventory = this.player.getInventory();
		
		int weight = Resources.GetInventoryWeight(inventory);
		
		matrices.push();
		float scale = 0.75f;
		matrices.scale(scale, scale, scale);
		
		if (weight < Resources.MAX_CARRY * 0.65f) {
			this.textRenderer.draw(matrices, weight + " / " + Resources.MAX_CARRY, (int)((129) * (1.0f / scale)), (int)(71  * (1.0f / scale)), 4210752);
		} else {
			if (weight > Resources.MAX_CARRY) { 
				this.textRenderer.draw(matrices, weight + " / " + Resources.MAX_CARRY, (int)((129) * (1.0f / scale)), (int)(71  * (1.0f / scale)), RED);
			} else {
				this.textRenderer.draw(matrices, weight + " / " + Resources.MAX_CARRY, (int)((129) * (1.0f / scale)), (int)(71  * (1.0f / scale)), YELLOW);
			}
		}
		
		matrices.pop();
	}
	
}
