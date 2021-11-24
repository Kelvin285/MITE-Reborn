package kelvin.mite.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import kelvin.mite.registry.BlockRegistry;
import kelvin.mite.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MushroomStewItem;
import net.minecraft.world.World;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

	public ItemEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	
	
	/*
	 * public ItemEntity(World world, double x, double y, double z, ItemStack stack) {
	 *	 this(world, x, y, z);
	 *	 this.setStack(stack);
	 * }
	 */
	
	@Shadow
	public ItemStack getStack() {
		return null;
	}
	
	@Shadow
	public void setStack(ItemStack stack) {
		
	}
	
	@Inject(at = @At("HEAD"), method = "tick", cancellable = true)
	public void tick(CallbackInfo info) {
		if (this.isSubmergedInWater()) {
			if (this.getStack() != null) {
				Item item = this.getStack().getItem();
				int count = this.getStack().getCount();
				
				if (item == Items.BOWL || item instanceof MushroomStewItem) {
					this.setStack(new ItemStack(ItemRegistry.WATER_BOWL, count));
					return;
				}
				/*
				if (item instanceof BlockItem) {
					Block block = ((BlockItem)item).getBlock();
					if (BlockRegistry.gravel_variants.contains(block)) {
						DropGravelItem();
						count--;
						if (count <= 0) {
							this.kill();
						} else {
							this.setStack(new ItemStack(item, count));
						}
					}
				}
				*/
			}
		}
	}
	
	
}
