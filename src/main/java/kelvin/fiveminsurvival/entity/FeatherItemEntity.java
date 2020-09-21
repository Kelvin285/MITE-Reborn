package kelvin.fiveminsurvival.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;

public class FeatherItemEntity extends ItemEntity {

	public FeatherItemEntity(EntityType<? extends ItemEntity> item, World p_i50217_2_) {
		super(item, p_i50217_2_);
	}
	
	public void tick() {
		super.tick();
		if (!this.onGround) {
			this.setMotion(getMotion().x, -0.05f, getMotion().z);
			this.setNoGravity(true);
		} else {
			this.setNoGravity(false);
		}
       
	}

}
