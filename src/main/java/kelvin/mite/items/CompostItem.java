package kelvin.mite.items;

import kelvin.mite.blocks.MiteFarmlandBlock;
import kelvin.mite.blocks.entity.FarmlandBlockEntity;
import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.SaveableVec3;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class CompostItem extends BoneMealItem {

    public CompostItem(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(context.getSide());
        if (CompostItem.useOnFertilizable(context.getStack(), world, blockPos)) {
            if (!world.isClient) {
                world.syncWorldEvent(1505, blockPos, 0);
            }

            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    public static boolean useOnFertilizable(ItemStack stack, World world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);

        if (blockState.getBlock() instanceof CropBlock) {

            BlockEntity blockEntity = world.getBlockEntity(pos.down());
            if (blockEntity instanceof FarmlandBlockEntity) {
                FarmlandBlockEntity farmland = (FarmlandBlockEntity) blockEntity;
                if (farmland.potassium < 15) {
                    farmland.potassium++;
                    stack.decrement(1);
                    return true;
                }
            }
        } else if (blockState.getBlock() instanceof MiteFarmlandBlock) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FarmlandBlockEntity) {
                FarmlandBlockEntity farmland = (FarmlandBlockEntity) blockEntity;
                if (farmland.potassium < 15) {
                    farmland.potassium++;
                    stack.decrement(1);
                    return true;
                }
            }
        }
        return false;
    }
}
