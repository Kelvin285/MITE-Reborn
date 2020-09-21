package kelvin.fiveminsurvival.blocks.TileEntities;

import kelvin.fiveminsurvival.init.ContainerRegistry;
import kelvin.fiveminsurvival.main.gui.MITEFurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MITEFurnaceTileEntity extends AbstractFurnaceTileEntity {
		public int HEAT_LEVEL;
		
		 public MITEFurnaceTileEntity() {
		      super(ContainerRegistry.FURNACE_TILE_ENTITY.get(), IRecipeType.SMELTING);
		      this.HEAT_LEVEL = 0;
		   }
		
	   public MITEFurnaceTileEntity(int HEAT_LEVEL) {
	      super(ContainerRegistry.FURNACE_TILE_ENTITY.get(), IRecipeType.SMELTING);
	      this.HEAT_LEVEL = HEAT_LEVEL;
	   }

	   protected ITextComponent getDefaultName() {
	      return new TranslationTextComponent("container.furnace");
	   }

	   protected Container createMenu(int id, PlayerInventory player) {
	      return new MITEFurnaceContainer(id, player, this, this.furnaceData, HEAT_LEVEL);
	   }
	}