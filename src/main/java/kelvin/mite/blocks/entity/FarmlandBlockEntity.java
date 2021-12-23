package kelvin.mite.blocks.entity;

import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.FastNoiseLite;
import kelvin.mite.main.resources.MoonHelper;
import kelvin.mite.registry.BlockEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class FarmlandBlockEntity extends BlockEntity implements BlockEntityProvider {

    public static final int LOW_FERTILITY = 0, MEDIUM_FERTILITY = 1, HIGH_FERTILITY = 2;

    public int fertility = MEDIUM_FERTILITY;

    public int nitrogen, phosphorus, potassium;

    private static FastNoiseLite fastNoise = new FastNoiseLite();

    public FarmlandBlockEntity(BlockPos pos, BlockState state) {

        super(BlockEntityRegistry.FARMLAND, pos, state);

        float nitrogen_noise = ((fastNoise.GetNoise(pos.getX() / 10.0f, pos.getZ() / 10.0f)) + 1) / 2.0f;
        nitrogen = (int)(15 * nitrogen_noise);

        float phosphorus_noise = ((fastNoise.GetNoise((pos.getX() + 10000) / 10.0f, (pos.getZ() + 10000) / 10.0f)) + 1) / 2.0f;
        phosphorus = (int)(15 * phosphorus_noise);

        float potassium_noise = ((fastNoise.GetNoise((pos.getX() - 10000) / 10.0f, (pos.getZ() - 10000) / 10.0f)) + 1) / 2.0f;
        potassium = (int)(15 * potassium_noise);
        if (nitrogen < 0) nitrogen = 0;
        if (phosphorus < 0) phosphorus = 0;
        if (potassium < 0) potassium = 0;

        if (nitrogen > 15) nitrogen = 15;
        if (phosphorus > 15) phosphorus = 15;
        if (potassium > 15) potassium = 15;
    }

    public void notifyListeners() {
        this.markDirty();

        if(world != null && !world.isClient())
            world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound tag = super.toInitialChunkDataNbt();
        writeNbt(tag);
        return tag;
    }

    public void writeNbt(NbtCompound compound) {
        super.writeNbt(compound);
        compound.putInt("nitrogen", nitrogen);
        compound.putInt("phosphorus", phosphorus);
        compound.putInt("potassium", potassium);
    }


    public void readNbt(NbtCompound compound) {
        nitrogen = compound.getInt("nitrogen");
        phosphorus = compound.getInt("phosphorus");
        potassium = compound.getInt("potassium");
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FarmlandBlockEntity(pos, state);
    }

    private int ticks = 0;
    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T t) {
        if (t instanceof FarmlandBlockEntity) {
            FarmlandBlockEntity farmland = (FarmlandBlockEntity) t;

            if (MoonHelper.IsHarvestMoon(Mite.day_time) && world.isNight()) {
                Random random = t.getWorld().getRandom();
                if (random.nextInt(15 * 20) == 0) {
                    farmland.nitrogen++;
                }
                if (random.nextInt(15 * 20) == 0) {
                    farmland.phosphorus++;
                }
                if (random.nextInt(15 * 20) == 0) {
                    farmland.potassium++;
                }
            }

            farmland.ticks++;

            if (farmland.ticks>20) {
                farmland.ticks=0;
                farmland.notifyListeners();
            }

        }

    }
}
