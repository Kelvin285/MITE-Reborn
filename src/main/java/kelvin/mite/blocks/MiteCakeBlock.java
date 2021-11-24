package kelvin.mite.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MiteCakeBlock extends CakeBlock {

	public MiteCakeBlock(int hunger, double saturation, int happiness) {
		super(Settings.of(Material.CAKE));
	}
}
