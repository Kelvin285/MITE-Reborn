package kelvin.fiveminsurvival.mixins.server;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import kelvin.fiveminsurvival.game.world.WorldStateHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixins {

	/***
	 * public void onItemPickup(Entity entityIn, int quantity) {
      	super.onItemPickup(entityIn, quantity);
      	this.openContainer.detectAndSendChanges();
   		}
	 */
	
	@Inject(method = "onItemPickup", at = @At("HEAD"), cancellable = true)
	public void onItemPickup(Entity entityIn, int quantity, CallbackInfo info) {
		if (entityIn instanceof ItemEntity) {
			ItemStack stack = ((ItemEntity) entityIn).getItem();
			if (stack != null) {
				Item item = stack.getItem();
				WorldStateHolder holder = WorldStateHolder.get(entityIn.getEntityWorld());
				if (item == Items.BEETROOT)holder.beetroot_found = true;
				else
				if (item == Items.CARROT)holder.carrot_found = true;
				else
				if (item == Items.WHEAT)holder.wheat_found = true;
				else
				if (item == Items.PUMPKIN)holder.pumpkin_found = true;
				else
				if (item == Items.MELON)holder.melon_found = true;
				else
				if (item == Items.POTATO)holder.potato_found = true;
				else
				if (item == Items.PRISMARINE || item == Items.PRISMARINE_CRYSTALS || item == Items.PRISMARINE_SHARD || item == Items.NAUTILUS_SHELL || item == Items.HEART_OF_THE_SEA)holder.prismarine_found = true;
				else
				if (item == Items.NETHERITE_SCRAP || item == Items.NETHERITE_INGOT ||
						item == Items.NETHERITE_AXE || item == Items.NETHERITE_PICKAXE ||
						item == Items.NETHERITE_SWORD || item == Items.NETHERITE_SHOVEL ||
						item == Items.NETHERITE_HOE || item == Items.NETHERITE_INGOT ||
						item == Items.NETHERITE_BOOTS || item == Items.NETHERITE_BRICKS ||
						item == Items.NETHERITE_CHESTPLATE || item == Items.NETHERITE_HELMET ||
						item == Items.NETHERITE_LEGGINGS)holder.netherite_found = true;
				else
				if (item == Items.OBSIDIAN || item == Items.CRYING_OBSIDIAN) holder.obsidian_block = true;
				else
				if (item == Items.BLAZE_ROD || item == Items.BLAZE_POWDER) holder.blaze_rod_found = true;
				else
				if (item == Items.ENDER_EYE) holder.ender_eye_crafted = true; 
			}
		}
	}

}
