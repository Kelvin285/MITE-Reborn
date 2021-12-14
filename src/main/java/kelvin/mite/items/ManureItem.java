package kelvin.mite.items;

import kelvin.mite.blocks.MiteFarmlandBlock;
import kelvin.mite.blocks.entity.FarmlandBlockEntity;
import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.SaveableVec3;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class ManureItem extends BoneMealItem {

    public ManureItem(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(context.getSide());
        if (ManureItem.useOnFertilizable(context.getStack(), world, blockPos)) {
            if (!world.isClient) {
                world.syncWorldEvent(1505, blockPos, 0);
            }

            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    public static boolean useOnFertilizable(ItemStack stack, World world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof MushroomPlantBlock) {
            Fertilizable fertilizable = (Fertilizable)blockState.getBlock();
            if (fertilizable.isFertilizable(world, pos, blockState, world.isClient)) {
                if (world instanceof ServerWorld) {
                    if (fertilizable.canGrow(world, world.random, pos, blockState)) {
                        fertilizable.grow((ServerWorld)world, world.random, pos, blockState);
                    }

                    stack.decrement(1);
                }

                return true;
            }
        }
        else if (blockState.getBlock() instanceof CropBlock) {

            BlockEntity blockEntity = world.getBlockEntity(pos.down());
            if (blockEntity instanceof FarmlandBlockEntity) {
                FarmlandBlockEntity farmland = (FarmlandBlockEntity) blockEntity;
                if (farmland.nitrogen < 15) {
                    farmland.nitrogen++;
                    stack.decrement(1);
                    return true;
                }
            }
        } else if (blockState.getBlock() instanceof MiteFarmlandBlock) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FarmlandBlockEntity) {
                FarmlandBlockEntity farmland = (FarmlandBlockEntity) blockEntity;
                if (farmland.nitrogen < 15) {
                    farmland.nitrogen++;
                    stack.decrement(1);
                    return true;
                }
            }
        }

        return false;
    }
}
