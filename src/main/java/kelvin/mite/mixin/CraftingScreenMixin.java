package kelvin.mite.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.FurnaceScreen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import kelvin.mite.main.resources.ItemCraftingDifficulty;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

@Mixin(CraftingScreen.class)
public abstract class CraftingScreenMixin extends HandledScreen<CraftingScreenHandler> implements RecipeBookProvider {
	private static final Identifier FURNACE_TEX = new Identifier("textures/gui/container/furnace.png");

	@Shadow
	private RecipeBookWidget recipeBook;

	@Shadow
	private boolean narrow;

	public boolean crafting = false;
	public int crafting_ticks = 0;
	public int max_crafting_ticks = 0;
	public Slot crafting_slot;
	public int crafting_slot_id;
	public PlayerInventory playerInventory;

	
	public CraftingScreenMixin(CraftingScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		playerInventory = inventory;
	}
	
	public void handledScreenTick() {
		super.handledScreenTick();
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
	
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		//89, 35 (top left)
		//selection: x176, y14, w24, h17
		if (this.recipeBook.isOpen() && this.narrow) {
			drawBackground(matrices, delta, mouseX, mouseY);
			this.recipeBook.render(matrices, mouseX, mouseY, delta);
		} else {
			this.recipeBook.render(matrices, mouseX, mouseY, delta);
			super.render(matrices, mouseX, mouseY, delta);
			this.recipeBook.drawGhostSlots(matrices, this.x, this.y, true, delta);
		}
		int tex = RenderSystem.getShaderTexture(0);
		RenderSystem.setShaderTexture(0, FURNACE_TEX);

		this.drawTexture(matrices, this.x + 89, this.y + 35, 176, 14, (int)(24 * ((float)crafting_ticks / (float)max_crafting_ticks)), 17);
		RenderSystem.setShaderTexture(0, tex);

		if (crafting) {
			this.textRenderer.draw(matrices, (int)((crafting_ticks / (float)max_crafting_ticks) * 100) + "%", this.x + 119, this.y + 20, 0xffffff);
		}
		drawMouseoverTooltip(matrices, mouseX, mouseY);
		this.recipeBook.drawTooltip(matrices, this.x, this.y, mouseX, mouseY);

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
