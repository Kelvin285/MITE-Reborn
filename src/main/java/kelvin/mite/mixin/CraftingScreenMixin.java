package kelvin.mite.mixin;

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
	
	public void tick() {
		super.tick();
		this.recipeBook.update();

		if (crafting) {
			if (crafting_slot.hasStack()) {
				crafting_ticks++;
				System.out.println("crafting: " + crafting_ticks);
				if (crafting_ticks > max_crafting_ticks) {
					crafting_ticks = 0;
					System.out.println("ding ding ding");
					super.onMouseClick(crafting_slot, crafting_slot_id, 0, SlotActionType.QUICK_MOVE);
					this.recipeBook.slotClicked(crafting_slot);
					if (!crafting_slot.hasStack()) {
						crafting = false;
					}
				}
			} else {
				crafting_ticks = 0;
				crafting = false;
			}
		}
	}
	
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		if (this.recipeBook.isOpen() && this.narrow) {
			drawBackground(matrices, delta, mouseX, mouseY);
			this.recipeBook.render(matrices, mouseX, mouseY, delta);
		} else {
			this.recipeBook.render(matrices, mouseX, mouseY, delta);
			super.render(matrices, mouseX, mouseY, delta);
			this.recipeBook.drawGhostSlots(matrices, this.x, this.y, true, delta);
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
		crafting = false;
		if (slot instanceof CraftingResultSlot) {
			if (slot.hasStack()) {
				crafting_slot = slot;
				crafting_slot_id = slotId;
				crafting_ticks = 0;
				max_crafting_ticks = GetCraftingTicks(ItemCraftingDifficulty.GetDifficultyFor(slot.getStack().getItem()));
				crafting = true;
			}
			
		} else {
			super.onMouseClick(slot, slotId, button, actionType);
			this.recipeBook.slotClicked(slot);
		}
	}
}
