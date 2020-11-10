package kelvin.fiveminsurvival.mixins;

import java.util.HashMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

@Mixin(AbstractFurnaceTileEntity.class)
public class FurnaceMixins extends AbstractFurnaceTileEntity{

	protected FurnaceMixins(TileEntityType<?> tileTypeIn, IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn) {
		super(tileTypeIn, recipeTypeIn);
	}

	public HashMap<Item, int[]> counts = new HashMap<Item, int[]>();
	
	@Inject(method = "func_230337_a_", at = @At("HEAD"), cancellable = true)
	public void read(BlockState state, CompoundNBT nbt, CallbackInfo info) {
		//super.read(state, nbt);
	    //this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
	    //ItemStackHelper.loadAllItems(nbt, this.items);
	    //System.out.println("E");
	    //System.exit(0);
	}
//	public boolean ClassEquals(Class<?> c) {
//		return c.getCanonicalName().equals(getClass().getCanonicalName());
//	}
	/*
	public void read(BlockState state, CompoundNBT nbt) { //TODO: MARK
	      super.read(state, nbt);
	      this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
	      ItemStackHelper.loadAllItems(nbt, this.items);
	      this.burnTime = nbt.getInt("BurnTime");
	      this.cookTime = nbt.getInt("CookTime");
	      this.cookTimeTotal = nbt.getInt("CookTimeTotal");
	      this.recipesUsed = this.getBurnTime(this.items.get(1));
	      CompoundNBT compoundnbt = nbt.getCompound("RecipesUsed");

	      for(String s : compoundnbt.keySet()) {
	         this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
	      }

	   }
	   */

	@Override
	protected ITextComponent getDefaultName() {
		return null;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return null;
	}
}
