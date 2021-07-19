package kelvin.mite.mixin;

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
			}
		}
	}
	
	private void DropGravelItem() {
		// flint chip = 1/3
		// flint = 1/10
		// copper = 1/50
		// silver = 1/50
		// iron = 1/100
		// gold = 1/150
		// obsidian = 1/1000
		// mithril = 1/1000
		// adamantium = 1/10000
		Item drop = Items.AIR;
		
		Item adamantium = Items.FLINT;
		Item obsidian = Items.FLINT;
		Item mithril = Items.FLINT;
		
		if (Math.random() <= 1.0D / 10000.0D) {
			// adamantium nugget
			drop = adamantium;
		}
		else if (Math.random() <= 1.0D / 1000.0D) {
			if (Math.random() <= 0.5f) {
				// obsidian shard
				drop = obsidian;
			} else {
				// mithril nugget
				drop = mithril;
			}
		}
		else if (Math.random() <= 1.0D / 150.0D) {
			drop = Items.GOLD_NUGGET;
		}
		else if (Math.random() <= 1.0D / 100.0D) {
			drop = Items.IRON_NUGGET;
		}
		else if (Math.random() <= 1.0D / 50.0D) {
			if (Math.random() <= 0.5f) {
				drop = ItemRegistry.SILVER_NUGGET;
			} else {
				drop = ItemRegistry.COPPER_NUGGET;
			}
		}
		else if (Math.random() <= 1.0D / 10.0D) {
			drop = Items.FLINT;
		}
		else if (Math.random() <= 1.0D / 3.0D) {
			drop = ItemRegistry.FLINT_SHARD;
		}
		
		if (drop != Items.AIR) { 
			world.spawnEntity(new ItemEntity(world, getPos().x, getPos().y, getPos().z, new ItemStack(drop)));
		}
	}
	
	
}
