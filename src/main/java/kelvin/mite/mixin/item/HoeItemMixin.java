package kelvin.mite.mixin.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import kelvin.mite.blocks.MiteFarmlandBlock;
import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.SaveableVec3;
import kelvin.mite.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Mixin(HoeItem.class)
public class HoeItemMixin {
    @Inject(at=@At("HEAD"), method="useOnBlock", cancellable = true)
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();

        BlockState state = world.getBlockState(blockPos);

        if (BlockRegistry.CanSwapWithFarmland(state.getBlock())) {
            PlayerEntity playerEntity = context.getPlayer();
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isClient) {
                if (playerEntity != null) {
                    context.getStack().damage(1, playerEntity, (p) -> {
                        p.sendToolBreakStatus(context.getHand());
                    });
                }
            }
            MiteFarmlandBlock block = (MiteFarmlandBlock)BlockRegistry.TrySwapWithFarmland(state.getBlock());
            BlockState set = block.getDefaultState();
            world.setBlockState(blockPos, set);
            info.setReturnValue(ActionResult.success(world.isClient));
        }
    }
}
