package kelvin.mite.blocks;

import kelvin.mite.blocks.entity.FarmlandBlockEntity;
import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.SaveableVec3;
import kelvin.mite.registry.BlockEntityRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Random;

public class MiteFarmlandBlock extends FarmlandBlock implements BlockEntityProvider{

    private Block ret;
    public MiteFarmlandBlock(Settings settings, Block ret) {
        super(settings);
        this.ret = ret;
    }

    private static boolean isWaterNearby(WorldView world, BlockPos pos) {
        Iterator var2 = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();

        BlockPos blockPos;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            blockPos = (BlockPos)var2.next();
        } while(!world.getFluidState(blockPos).isIn(FluidTags.WATER));

        return true;
    }

    private static boolean hasCrop(BlockView world, BlockPos pos) {
        Block block = world.getBlockState(pos.up()).getBlock();
        return block instanceof CropBlock || block instanceof StemBlock || block instanceof AttachedStemBlock;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = (Integer)state.get(MOISTURE);
        if (!isWaterNearby(world, pos) && !world.hasRain(pos.up())) {
            if (i > 0) {
                world.setBlockState(pos, (BlockState)state.with(MOISTURE, i - 1), 2);
            } else if (!hasCrop(world, pos)) {
                setDirt(state, world, pos);
            }
        } else if (i < 7) {
            world.setBlockState(pos, (BlockState)state.with(MOISTURE, 7), 2);
        }

    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isClient && world.random.nextFloat() < fallDistance - 0.5F && entity instanceof LivingEntity && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512F) {
            setDirt(state, world, pos);
        }

        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    public void setDirt(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, pushEntitiesUpBeforeBlockChange(state, ret.getDefaultState(), world, pos));
    }

    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{MOISTURE});
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FarmlandBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : type == BlockEntityRegistry.FARMLAND ? FarmlandBlockEntity::tick : null;
    }
}
