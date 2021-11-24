package kelvin.mite.mixin.item;

import kelvin.mite.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DyeItem.class)
public class DyeItemMixin {
    @Shadow
    private DyeColor color;

    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof CakeBlock && block != BlockRegistry.UNBAKED_CAKE) {
            if (this.color == DyeColor.BLACK) {
                world.setBlockState(pos, BlockRegistry.BLACK_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.GREEN) {
                world.setBlockState(pos, BlockRegistry.GREEN_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.BLUE) {
                world.setBlockState(pos, BlockRegistry.BLUE_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.BROWN) {
                world.setBlockState(pos, BlockRegistry.BROWN_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.CYAN) {
                world.setBlockState(pos, BlockRegistry.CYAN_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.GRAY) {
                world.setBlockState(pos, BlockRegistry.GRAY_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.LIGHT_BLUE) {
                world.setBlockState(pos, BlockRegistry.LIGHT_BLUE_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.LIGHT_GRAY) {
                world.setBlockState(pos, BlockRegistry.LIGHT_GRAY_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.LIME) {
                world.setBlockState(pos, BlockRegistry.LIME_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.MAGENTA) {
                world.setBlockState(pos, BlockRegistry.MAGENTA_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.ORANGE) {
                world.setBlockState(pos, BlockRegistry.ORANGE_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.PINK) {
                world.setBlockState(pos, BlockRegistry.PINK_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.PURPLE) {
                world.setBlockState(pos, BlockRegistry.PURPLE_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.RED) {
                world.setBlockState(pos, BlockRegistry.RED_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.WHITE) {
                world.setBlockState(pos, Blocks.CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
            else if (this.color == DyeColor.YELLOW) {
                world.setBlockState(pos, BlockRegistry.YELLOW_CAKE.getStateWithProperties(world.getBlockState(pos)));
            }
        }
        return ActionResult.PASS;
    }
}
