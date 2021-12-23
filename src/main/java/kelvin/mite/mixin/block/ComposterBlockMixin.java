package kelvin.mite.mixin.block;

import kelvin.mite.registry.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ComposterBlock.class)
public class ComposterBlockMixin {
    @Inject(at = @At("RETURN"), method = "registerDefaultCompostableItems")
    private static void registerDefaultCompostableItems() {

        registerCompostableItem(0.3F, ItemRegistry.BLUEBERRIES);
        registerCompostableItem(0.3F, ItemRegistry.BROWNIE);
        registerCompostableItem(0.3F, ItemRegistry.BRANCH);
        registerCompostableItem(0.3F, ItemRegistry.TWIG);
        registerCompostableItem(0.3F, ItemRegistry.LEMON);
        registerCompostableItem(0.3F, ItemRegistry.BANANA);
        registerCompostableItem(0.3F, ItemRegistry.FLAX_SEEDS);
        registerCompostableItem(0.3F, ItemRegistry.FLAX);
        registerCompostableItem(0.3F, ItemRegistry.COOKIE_DOUGH);
        registerCompostableItem(0.3F, ItemRegistry.CHEESE);
        registerCompostableItem(0.3F, ItemRegistry.DRY_GRASS);
        registerCompostableItem(0.65F, ItemRegistry.UNBAKED_CAKE);
        registerCompostableItem(0.65F, ItemRegistry.DOUGH);
        registerCompostableItem(0.65F, ItemRegistry.ONION);
        registerCompostableItem(0.65F, ItemRegistry.BLUEBERRY_MUFFIN);
    }

    @Shadow
    private static void registerCompostableItem(float levelIncreaseChance, ItemConvertible item) {

    }

    @Inject(at=@At("HEAD"),method="emptyFullComposter",cancellable = true)
    public static void emptyFullComposter(BlockState state, World world, BlockPos pos, CallbackInfoReturnable<BlockState> info) {
        if (!world.isClient) {
            float f = 0.7F;
            double d = (double) (world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
            double e = (double) (world.random.nextFloat() * 0.7F) + 0.06000000238418579D + 0.6D;
            double g = (double) (world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
            ItemEntity itemEntity = new ItemEntity(world, (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + g, new ItemStack(ItemRegistry.COMPOST, world.random.nextInt(2) + 1));
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }

        BlockState f = emptyComposter(state, world, pos);
        world.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_COMPOSTER_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        info.setReturnValue(f);
    }

    @Shadow
    private static BlockState emptyComposter(BlockState state, WorldAccess world, BlockPos pos) {
        return null;
    }
}
