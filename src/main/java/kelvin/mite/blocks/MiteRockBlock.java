package kelvin.mite.blocks;

import kelvin.mite.main.Mite;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class MiteRockBlock extends AbstractButtonBlock {

	public MiteRockBlock(Settings settings) {
		super(false, settings);
		if (Mite.client) BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
	}

	@Override
	protected SoundEvent getClickSound(boolean powered) {
		return null;
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockHitResult hit) {
		return ActionResult.success(world.isClient);
	}

}
