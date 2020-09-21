package kelvin.fiveminsurvival.survival.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;

public class SelectBlockPlacer extends SimpleBlockPlacer {
	private Block placeIn;
	public SelectBlockPlacer(Block placeIn) {
		super();
		this.placeIn = placeIn;
	}
	public void func_225567_a_(IWorld p_225567_1_, BlockPos p_225567_2_, BlockState p_225567_3_, Random p_225567_4_) {
		BlockPos.Mutable pos = new BlockPos.Mutable(p_225567_2_);
		int y = pos.getY();
		for (int i = 0; i < 3; i++) {
			pos.setPos(pos.getX(), y - i, pos.getZ());
			if (p_225567_1_.getBlockState(pos).getBlock() == placeIn) {
		      p_225567_1_.setBlockState(pos, p_225567_3_, 2);
		      break;
			}
			pos.setPos(pos.getX(), y + i, pos.getZ());
			if (p_225567_1_.getBlockState(pos).getBlock() == placeIn) {
			      p_225567_1_.setBlockState(pos, p_225567_3_, 2);
			      break;
				}
		}
		
	   }
}
