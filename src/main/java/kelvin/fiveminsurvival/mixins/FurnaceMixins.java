package kelvin.fiveminsurvival.mixins;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;

@Mixin(AbstractFurnaceTileEntity.class)
public class FurnaceMixins extends TileEntity {
	
	public FurnaceMixins(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public int items_in = 1;
	public int items_out = 1;
	
	@Shadow
	protected int burnTime;
	@Shadow
	protected int cookTime;
	
	@Shadow
	protected NonNullList<ItemStack> items;
	
	@Shadow
	protected IRecipeType<? extends AbstractCookingRecipe> recipeType;
	
	@Shadow
	protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
		return false;
		}
	
	@Shadow
	public int getSizeInventory() {
		return 0;
	}
	
	@Shadow
	public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
		
	}
	
	@Shadow
	public boolean isBurning() { return false; }
	
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void tick(CallbackInfo info) {
		  
	      ItemStack stack = this.items.get(0);
	      if (stack != null) {
	    	  if (stack.getCount() < items_in) {
	    		  if (this.isBurning()) {
    		         --this.burnTime;
    		      }
 		          this.cookTime = 0;
	    		  info.cancel();
	    		  return;
	    	  }
	      }
	}
	
	//read func_230337_a_
	@Inject(method = "read", at = @At("HEAD"), cancellable = true)
	public void read(BlockState state, CompoundNBT nbt, CallbackInfo info) {
		items_in = nbt.getInt("ItemsIn");
		items_out = nbt.getInt("ItemsOut");
		if (items_in == 0) items_in = 1;
		if (items_out == 0) items_out = 1;
	}
	
	@Inject(method = "write", at = @At("HEAD"), cancellable = true)
	public void write(CompoundNBT compound, CallbackInfoReturnable<CompoundNBT> info) {
		compound.putInt("ItemsIn", items_in);
		compound.putInt("ItemsOut", items_out);
	}
	
	@Inject(method = "smelt", at = @At("HEAD"), cancellable = true)
	public void smelt(@Nullable IRecipe<?> recipe, CallbackInfo info) {
		if (recipe != null && this.canSmelt(recipe)) {
			ItemStack stack = items.get(0);
			ItemStack stack1 = recipe.getRecipeOutput();
			ItemStack stack2 = items.get(2);
			if (stack2.isEmpty()) {
				this.items.set(2, stack1.copy());
			} else if (stack2.getItem() == stack1.getItem() && stack.getCount() >= this.items_in) {
				stack2.grow(Math.max(this.items_out, stack1.getCount()));
			}
			
			if (!this.world.isRemote) {
				this.setRecipeUsed(recipe);
			}
			
			if (stack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
				this.items.set(1, new ItemStack(Items.WATER_BUCKET));
			}
			
			stack.shrink(items_in);
		}
		return;
	}
		
	@Inject(method = "setInventorySlotContents", at = @At("HEAD"), cancellable = true)
	public void setInventorySlotContents(int index, ItemStack stack, CallbackInfo info) {
		if (index == 0 && stack != null) {
			Item item = stack.getItem();
			this.items_in = item == Items.SAND || item == Items.CLAY_BALL ? 4 : 1;
			this.items_out = item == Items.CLAY_BALL ? 4 : 1;
			System.out.println(item + ", " + items_in);
		}
	}
	
}
