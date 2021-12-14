package kelvin.mite.mixin.block;

import kelvin.mite.blocks.MiteFarmlandBlock;
import kelvin.mite.blocks.entity.CropBlockEntity;
import kelvin.mite.blocks.entity.FarmlandBlockEntity;
import kelvin.mite.blocks.properties.MiteBlockProperties;
import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.CropNutrients;
import kelvin.mite.main.resources.SaveableVec3;
import kelvin.mite.registry.BlockEntityRegistry;
import kelvin.mite.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends PlantBlock implements Fertilizable, BlockEntityProvider {

    protected CropBlockMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    public int getMaxAge() {
        return 7;
    }
    @Shadow
    protected int getAge(BlockState state) {
        return 0;
    }
    @Shadow
    public BlockState withAge(int age) {
        return null;
    }
    @Shadow
    private static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        return 0;
    }

    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.FARMLAND) || BlockRegistry.farmland_variants.values().contains(floor.getBlock());
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        if (!isBlighted(state)) {
            return super.getDroppedStacks(state, builder);
        }
        return new ArrayList<ItemStack>();
    }

    protected boolean isBlighted(BlockState state) {
        return (Boolean)state.get(MiteBlockProperties.BLIGHTED);
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return !((CropBlock)(Object)this).isMature(state) && !this.isBlighted(state);
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return !this.isBlighted(state);
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (!this.isBlighted(state)) {
            ((CropBlock)(Object)this).applyGrowth(world, pos, state);
        }
    }

    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{CropBlock.AGE, MiteBlockProperties.BLIGHTED});
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CropBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : type == BlockEntityRegistry.CROPS ? CropBlockEntity::tick : null;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !world.getBlockState(pos.down()).isSolidBlock(world, pos.down()) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

}
