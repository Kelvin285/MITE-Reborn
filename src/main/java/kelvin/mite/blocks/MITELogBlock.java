package kelvin.mite.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class MITELogBlock extends Block implements Waterloggable {

	public MITELogBlock(Settings settings) {
		super(settings);
	}
	
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}
}
