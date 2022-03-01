package kelvin.mite.blocks.entity;

import kelvin.mite.blocks.properties.MiteBlockProperties;
import kelvin.mite.main.resources.CropNutrients;
import kelvin.mite.main.resources.MoonHelper;
import kelvin.mite.registry.BlockEntityRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CropBlockEntity extends BlockEntity implements BlockEntityProvider  {

    public static int GetGrowthTime(Block block) {
        if (block == Blocks.WHEAT) {
            return 48;
        }
        if (block == Blocks.CARROTS) {
            return 14;
        }
        if (block == Blocks.BEETROOTS) {
            return 10;
        }
        if (block == Blocks.POTATOES) {
            return 16;
        }
        return 14;
    }

    //desert temperature = 2 (30 C)
    //ice temperature = 0 (0 C);
    public static float GetRequiredTemperature(Block block) {
        float max_temp = 30.0f;
        if (block == Blocks.WHEAT) {
            return (23.0f / max_temp) * 2;
        }
        else if (block == Blocks.POTATOES) {
            return (13.0f / max_temp) * 2;
        } else if (block == Blocks.BEETROOTS) {
            return (20.0f / max_temp) * 2;
        } else if (block == Blocks.CARROTS) {
            return (23.0f / max_temp) * 2;
        }
        return (23.0f / max_temp) * 2;
    }

    public int time_until_grown = 0;
    public int max_age = 7;
    public int age = 0;
    public float ticks = 0;
    public long current_time = -1;
    private float growth_speed = 0;
    private boolean hot = false;
    private boolean cold = false;

    private int real_ticks = 0;

    public CropBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CROPS, pos, state);
        this.max_age = ((CropBlock)state.getBlock()).getMaxAge();
        this.time_until_grown = GetGrowthTime(state.getBlock()) * 24000;
    }


    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T t) {
        CropBlockEntity crops = (CropBlockEntity) t;

        boolean sync = false;
        if (crops.current_time == -1) {
            crops.current_time = world.getTime();
            sync = true;
        }

        int potassium = 15;
        int phosphor = 15;
        int nitrogen = 15;

        BlockEntity blockEntity = world.getBlockEntity(blockPos.down());
        FarmlandBlockEntity farmland = null;
        if (blockEntity instanceof FarmlandBlockEntity) {
            farmland = (FarmlandBlockEntity) blockEntity;

            potassium = farmland.potassium;
            phosphor = farmland.phosphorus;
            nitrogen = farmland.nitrogen;
        }

        float temperature = world.getBiome(blockPos).getTemperature();
        float required_temp = GetRequiredTemperature(blockState.getBlock());

        if (temperature > required_temp + 0.5f) {
            crops.hot = true;
        } else {
            crops.hot = false;
        }

        if (temperature < required_temp - 0.5f) {
            crops.cold = true;
        } else {
            crops.cold = false;
        }

        float temp_distance = 2.0f - (float)Math.abs(temperature - required_temp);
        temp_distance /= 2.0f;

        crops.growth_speed = temp_distance;

        float nutrients_speed = ((potassium + nitrogen + phosphor) / 3.0f) / 15.0f;
        if (nutrients_speed < 0.25f) {
            nutrients_speed = 0.25f;
        }
        crops.growth_speed *= nutrients_speed;


        if (world.getTime() > crops.current_time) {
            if (world.getBaseLightLevel(blockPos, 0) > 9) {
                crops.ticks += crops.growth_speed * (world.getTime() - crops.current_time);
            }
            crops.current_time = world.getTime();
        }
        crops.age = (int)Math.floor(crops.max_age * (crops.ticks / crops.time_until_grown));


        if (crops.age > crops.max_age) {
            crops.age = crops.max_age;
        }



        boolean grow = false;
        if (blockState.getBlock() instanceof BeetrootsBlock) {
            if (blockState.get(BeetrootsBlock.AGE) < crops.age) {
                world.setBlockState(blockPos, blockState.with(BeetrootsBlock.AGE, crops.age));
                grow = true;
            }
        } else {
            if (blockState.get(CropBlock.AGE) < crops.age) {
                world.setBlockState(blockPos, blockState.with(CropBlock.AGE, crops.age));
                grow = true;
            }
        }

        if (nitrogen < 6 || potassium < 6 || phosphor < 6) {
            if (blockState.get(MiteBlockProperties.BLIGHTED) == false) {
                world.setBlockState(blockPos, blockState.with(MiteBlockProperties.BLIGHTED, true));
            }
        } else {
            if (blockState.get(MiteBlockProperties.BLIGHTED) == true) {
                world.setBlockState(blockPos, blockState.with(MiteBlockProperties.BLIGHTED, false));
            }
        }

        if (grow) {
            CropNutrients nutrients = CropNutrients.GetNutrients(blockState.getBlock());
            if (farmland != null) {
                farmland.potassium -= nutrients.potassium;
                farmland.nitrogen -= nutrients.nitrogen;
                farmland.phosphorus -= nutrients.phosphorus;
                if (farmland.potassium < 0) farmland.potassium = 0;
                if (farmland.nitrogen < 0) farmland.nitrogen = 0;
                if (farmland.phosphorus < 0) farmland.phosphorus = 0;
            }
        }

        crops.real_ticks++;
        if (crops.real_ticks > 20) {
            crops.real_ticks = 0;
            sync = true;
        }
        if (sync) {
            crops.notifyListeners();
        }
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

    public void notifyListeners() {
        this.markDirty();

        if(world != null && !world.isClient())
            world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    public boolean onSyncedBlockEvent(int type, int data) {
        return false;
    }

    public boolean IsHot() {
        return hot;
    }

    public boolean IsCold() {
        return cold;
    }

    public float GetGrowthSpeed() {
        return growth_speed;
    }

    public double GetDaysUntilGrown() {
        if (age >= max_age) {
            return 0;
        }
        double ticks_left = ((time_until_grown - ticks) / growth_speed) / 24000.0;
        return Math.floor(ticks_left * 10) / 10.0;
    }

    public void writeNbt(NbtCompound compound) {
        super.writeNbt(compound);
        compound.putInt("time_until_grown", time_until_grown);
        compound.putInt("max_age", max_age);
        compound.putInt("age", age);
        compound.putFloat("ticks", ticks);
        compound.putLong("current_time", current_time);
        compound.putFloat("growth_speed", growth_speed);
    }


    public void readNbt(NbtCompound compound) {
        super.readNbt(compound);
        time_until_grown = compound.getInt("time_until_grown");
        max_age = compound.getInt("max_age");
        age = compound.getInt("age");
        ticks = compound.getInt("ticks");
        current_time = compound.getInt("current_time");
        growth_speed = compound.getFloat("growth_speed");
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CropBlockEntity(pos, state);
    }


}
