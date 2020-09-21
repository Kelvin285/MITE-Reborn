package kelvin.fiveminsurvival.entity;

import kelvin.fiveminsurvival.init.ItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BowlItemEntity extends ItemEntity {

	public BowlItemEntity(EntityType<? extends ItemEntity> item, World p_i50217_2_) {
		super(item, p_i50217_2_);
	}
	
	public void tick() {
		super.tick();
		
		if (this.isInWater()) {
			if (this.getItem().getItem() != ItemRegistry.WATER_BOWL.get()) {
				this.setItem(new ItemStack(ItemRegistry.WATER_BOWL.get(), this.getItem().getCount()));
			}
		}
	}

}
