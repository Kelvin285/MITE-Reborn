package kelvin.fiveminsurvival.blocks;

import java.util.Random;

import kelvin.fiveminsurvival.tileentity.MITEFurnaceTileEntity;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MITEFurnaceBlock extends AbstractFurnaceBlock {
    public int HEAT_LEVEL;

    public MITEFurnaceBlock(Block.Properties builder, int HEAT_LEVEL) {
        super(builder);
        this.HEAT_LEVEL = HEAT_LEVEL;
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new MITEFurnaceTileEntity(HEAT_LEVEL);
    }

    /**
     * Interface for handling interaction with blocks that impliment AbstractFurnaceBlock. Called in onBlockActivated
     * inside AbstractFurnaceBlock.
     */
    protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
        if (worldIn.getTileEntity(pos) instanceof MITEFurnaceTileEntity) {
            if (((MITEFurnaceTileEntity) worldIn.getTileEntity(pos)).HEAT_LEVEL != HEAT_LEVEL) {
                this.createNewTileEntity(worldIn);
            }
        }

        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof MITEFurnaceTileEntity) {
            ((MITEFurnaceTileEntity) tileentity).HEAT_LEVEL = HEAT_LEVEL;
            player.openContainer((MITEFurnaceTileEntity) tileentity);
            player.addStat(Stats.INTERACT_WITH_FURNACE);
        }

    }

    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
     * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
     * of whether the block can receive random update ticks
     */
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(LIT)) {
            double d0 = pos.getX() + 0.5D;
            double d1 = pos.getY();
            double d2 = pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = stateIn.get(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double) direction.getXOffset() * d3 : d4;
            double d6 = rand.nextDouble() * 6.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getZOffset() * d3 : d4;
            worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }
}