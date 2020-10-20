package kelvin.fiveminsurvival.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

@Mixin(AbstractFurnaceTileEntity.class)
public class FurnaceMixins {

	/*
	 * public void read(BlockState state, CompoundNBT nbt) { //TODO: MARK
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
	@Inject(method = "read", at = @At("HEAD"), cancellable = true)
	public void read(BlockState state, CompoundNBT nbt, CallbackInfo info) {
		
	}
}
