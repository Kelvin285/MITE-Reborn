package kelvin.fiveminsurvival.mixins;

import java.lang.annotation.Target;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeHooks;

@Mixin(CampfireTileEntity.class)
public class CampfireTileEntityMixins extends TileEntity {

	@Shadow
	private NonNullList<ItemStack> inventory;
	@Shadow
	private int[] cookingTimes;
	@Shadow
	private int[] cookingTotalTimes;
	

	@Shadow
	public void inventoryChanged() {
		
	}
	
	public CampfireTileEntityMixins(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	

	/*
	   private void cookAndDrop() {
	      for(int i = 0; i < this.inventory.size(); ++i) {
	         ItemStack itemstack = this.inventory.get(i);
	         if (!itemstack.isEmpty()) {
	            int j = this.cookingTimes[i]++;
	            if (this.cookingTimes[i] >= this.cookingTotalTimes[i]) {
	               IInventory iinventory = new Inventory(itemstack);
	               ItemStack itemstack1 = this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, iinventory, this.world).map((campfireRecipe) -> {
	                  return campfireRecipe.getCraftingResult(iinventory);
	               }).orElse(itemstack);
	               BlockPos blockpos = this.getPos();
	               InventoryHelper.spawnItemStack(this.world, (double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), itemstack1);
	               this.inventory.set(i, ItemStack.EMPTY);
	               this.inventoryChanged();
	            }
	         }
	      }
	
	   }
	 */
	@Inject(method = "cookAndDrop", at = @At("HEAD"), cancellable = true)
	public void cookAndDrop(CallbackInfo info) {
		boolean canBurn = false;
		for (int i = 0; i < this.inventory.size(); ++i) {
			ItemStack stack = this.inventory.get(i);
			if (ForgeHooks.getBurnTime(stack) > 0) {
				canBurn = true;
				break;
			}
		}
		for (int i = 0; i < this.inventory.size(); ++i) {
			ItemStack stack = this.inventory.get(i);
			if (!stack.isEmpty()) {
				if (ForgeHooks.getBurnTime(stack) > 0 || canBurn) {
					this.cookingTimes[i]++;
				}
				if (this.cookingTimes[i] >= this.cookingTotalTimes[i]) {
					IInventory inv = new Inventory(stack);
					ItemStack stack1 = this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, inv, this.world).map((recipe) -> {
						return recipe.getCraftingResult(inv);
					}).orElse(ForgeHooks.getBurnTime(stack) > 0 ? new ItemStack(Items.AIR) : stack);
					if (stack1.getItem() != Items.AIR) {
						BlockPos pos = this.getPos();
						InventoryHelper.spawnItemStack(this.world, pos.getX(), pos.getY(), pos.getZ(), stack1);
					}

					this.inventory.set(i, ItemStack.EMPTY);
					this.inventoryChanged();
				}
			}
		}
		info.cancel();
	}
}
