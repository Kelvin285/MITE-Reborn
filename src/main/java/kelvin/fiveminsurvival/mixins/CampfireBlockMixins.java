package kelvin.fiveminsurvival.mixins;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixins {
//func_225533_a_
	@Inject(method = "onBlockActivated", at = @At("HEAD"), cancellable = true)
	public void onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, CallbackInfoReturnable<ActionResultType> info) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
	      if (tileentity instanceof CampfireTileEntity) {
	         CampfireTileEntity campfiretileentity = (CampfireTileEntity)tileentity;
	         ItemStack itemstack = player.getHeldItem(handIn);
	         // VANILLA_BURNS.getOrDefault(item.delegate, 0)
	         if (ForgeHooks.getBurnTime(itemstack) > 0) {
	            if (!worldIn.isRemote && campfiretileentity.addItem(player.abilities.isCreativeMode ? itemstack.copy() : itemstack, ForgeHooks.getBurnTime(itemstack))) {
	               player.addStat(Stats.INTERACT_WITH_CAMPFIRE);
	               info.setReturnValue(ActionResultType.SUCCESS);
		            //info.cancel();
	               return;
	            }

	            info.setReturnValue(ActionResultType.CONSUME);
	            //info.cancel();
	            return;
	         }
	      }
	}
}
