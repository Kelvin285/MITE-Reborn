package kelvin.fiveminsurvival.items;

import kelvin.fiveminsurvival.init.ItemRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemShaver extends Item {

	public ItemShaver(Properties properties) {
		super(properties);
	}
	
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		
		if (pos != null) {
			boolean cutLog = false;
			if (world.getBlockState(pos).getBlock().getDefaultState() == Blocks.ACACIA_LOG.getDefaultState()) {
				world.setBlockState(pos, Blocks.STRIPPED_ACACIA_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, world.getBlockState(pos).get(RotatedPillarBlock.AXIS)));
				cutLog = true;
			}
			if (world.getBlockState(pos).getBlock().getDefaultState() == Blocks.BIRCH_LOG.getDefaultState()) {
				world.setBlockState(pos, Blocks.STRIPPED_BIRCH_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, world.getBlockState(pos).get(RotatedPillarBlock.AXIS)));
				cutLog = true;
			}
			if (world.getBlockState(pos).getBlock().getDefaultState() == Blocks.DARK_OAK_LOG.getDefaultState()) {
				world.setBlockState(pos, Blocks.STRIPPED_DARK_OAK_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, world.getBlockState(pos).get(RotatedPillarBlock.AXIS)));
				cutLog = true;
			}
			if (world.getBlockState(pos).getBlock().getDefaultState() == Blocks.JUNGLE_LOG.getDefaultState()) {
				world.setBlockState(pos, Blocks.STRIPPED_JUNGLE_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, world.getBlockState(pos).get(RotatedPillarBlock.AXIS)));
				cutLog = true;
			}
			if (world.getBlockState(pos).getBlock().getDefaultState() == Blocks.OAK_LOG.getDefaultState()) {
				world.setBlockState(pos, Blocks.STRIPPED_OAK_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, world.getBlockState(pos).get(RotatedPillarBlock.AXIS)));
				cutLog = true;
			}
			if (world.getBlockState(pos).getBlock().getDefaultState() == Blocks.SPRUCE_LOG.getDefaultState()) {
				world.setBlockState(pos, Blocks.STRIPPED_SPRUCE_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, world.getBlockState(pos).get(RotatedPillarBlock.AXIS)));
				cutLog = true;
			}
			if (cutLog) {
				world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemRegistry.STRIPPED_BARK.get(), random.nextInt(2) + 1)));
				world.playSound(context.getPlayer(), pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			if (cutLog) {
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}
	
	
}
