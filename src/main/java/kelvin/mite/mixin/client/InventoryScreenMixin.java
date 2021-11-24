package kelvin.mite.mixin.client;

import java.awt.Color;

import kelvin.mite.main.resources.ItemCraftingDifficulty;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
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
	private static final Identifier TEX = new Identifier("textures/gui/container/inventory.png");


	public boolean crafting = false;
	public int crafting_ticks = 0;
	public int max_crafting_ticks = 0;
	public Slot crafting_slot;
	public int crafting_slot_id;
	public PlayerInventory playerInventory;

	private PlayerEntity player;
	
	public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
		this.playerInventory = playerInventory;
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

	@Inject(at = @At("RETURN"), method = "render")
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
		int tex = RenderSystem.getShaderTexture(0);
		RenderSystem.setShaderTexture(0, TEX);

		this.drawTexture(matrices, this.x + 135, this.y + 29, 178, 38, (int)(16 * ((float)crafting_ticks / (float)max_crafting_ticks)), 14);
		RenderSystem.setShaderTexture(0, tex);

		if (crafting) {
			//int percent = (int)((crafting_ticks / (float)max_crafting_ticks) * 100);
			//int offset = percent < 100 ? 0 : -5;
			//this.textRenderer.draw(matrices,  percent + "%", this.x + 153 + offset, this.y + 27 - 10, 0xffffff);
		}
		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
		this.recipeBook.drawTooltip(matrices, this.x, this.y, mouseX, mouseY);
	}

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

	public void handledScreenTick() {
		if (this.client.interactionManager.hasCreativeInventory()) {
			this.client.setScreen(new CreativeInventoryScreen(this.client.player));
		} else {
			this.recipeBook.update();

			if (crafting) {
				if (crafting_slot.hasStack()) {
					if (crafting_ticks < max_crafting_ticks) {
						crafting_ticks++;
					}
				} else {
					crafting_ticks = 0;
					crafting = false;
				}
			}
		}

	}

	public int GetCraftingTicks(int difficulty) {
		/*if (difficulty > 100) {
			return 100 + (int)((Math.pow((difficulty - 100), 0.8f)) + 100);
		} else {
			return difficulty;
		}*/
		return difficulty;
	}

	public void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {

		if (slot instanceof CraftingResultSlot) {
			if (crafting_ticks >= max_crafting_ticks && crafting == true) {
				if (actionType == SlotActionType.QUICK_MOVE ||
						actionType == SlotActionType.QUICK_CRAFT) {
					actionType = SlotActionType.PICKUP;
				}
				if (slot.hasStack() && crafting == false) {
					System.out.println("craft!");
					crafting_slot = slot;
					crafting_slot_id = slotId;
					crafting_ticks = 0;
					max_crafting_ticks = GetCraftingTicks(ItemCraftingDifficulty.GetDifficultyFor(slot.getStack().getItem()));
					crafting = true;
				} else {
					crafting = false;
					crafting_ticks = 0;
				}
				super.onMouseClick(slot, slotId, button, actionType);
				this.recipeBook.slotClicked(slot);
			} else {
				if (slot.hasStack() && crafting == false) {
					System.out.println("craft!");
					crafting_slot = slot;
					crafting_slot_id = slotId;
					crafting_ticks = 0;
					max_crafting_ticks = GetCraftingTicks(ItemCraftingDifficulty.GetDifficultyFor(slot.getStack().getItem()));
					crafting = true;
				}
			}


		} else {
			super.onMouseClick(slot, slotId, button, actionType);
			this.recipeBook.slotClicked(slot);
		}
	}
}
