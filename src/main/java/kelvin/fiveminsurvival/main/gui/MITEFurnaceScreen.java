package kelvin.fiveminsurvival.main.gui;

import net.minecraft.client.gui.recipebook.FurnaceRecipeGui;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MITEFurnaceScreen extends AbstractFurnaceScreen<FurnaceContainer> {
   private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/furnace.png");

   public MITEFurnaceScreen(PlayerEntity player) {
	      super((FurnaceContainer) player.openContainer, new FurnaceRecipeGui(), player.inventory, new TranslationTextComponent("container.crafting"), FURNACE_GUI_TEXTURES);
	   }
   
   public MITEFurnaceScreen(FurnaceContainer p_i51089_1_, PlayerInventory p_i51089_2_, ITextComponent p_i51089_3_) {
      super(p_i51089_1_, new FurnaceRecipeGui(), p_i51089_2_, p_i51089_3_, FURNACE_GUI_TEXTURES);
   }
   
   
   public void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
	   if (slotId == 0 || slotId == 1) {
		   if (this.playerInventory.getItemStack() != null) {
			   if (this.playerInventory.getItemStack().getItem() instanceof BlockItem) {
				   if (MITEFurnaceContainer.STATIC_HEAT_LEVEL <= 0) {
					   return;
				   }
			   }
		   }
	   }
	   if (type == ClickType.QUICK_MOVE) {
		   if (slotIn != null) {
			   if (slotIn.getStack() != null) {
				   if (slotIn.getStack() != null) {
					   if (slotIn.getStack().getItem() instanceof BlockItem) {
						   if (MITEFurnaceContainer.STATIC_HEAT_LEVEL <= 0) {
							   return;
						   }
					   }
				   }
			   }
		   }
	   }
	   

	   super.handleMouseClick(slotIn, slotId, mouseButton, type);
   }
   
   public boolean keyPressed(int key, int a, int b) {
	   if (key == 81 && hoveredSlot != null && hoveredSlot.getStack() != null) {
		   hoveredSlot.slotNumber = hoveredSlot.getSlotIndex();
		   this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.getSlotIndex(), hasControlDown() ? 1 : 0, ClickType.THROW);
		   return true;
	   }
	   return super.keyPressed(key, a, b);
   }
}