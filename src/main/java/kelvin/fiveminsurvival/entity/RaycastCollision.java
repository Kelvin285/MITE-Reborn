package kelvin.fiveminsurvival.entity;

import net.minecraft.block.Block;

public class RaycastCollision {
	public int block_hit_x, block_hit_y, block_hit_z;
	public Block blockHit;
	public boolean isBlock() {
		return true;
	}
	
	public Block getBlockHit() {
		return blockHit;
	}
}
