package kelvin.mite.mixin;

import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.MiteSaveData;
import kelvin.mite.main.resources.SaveableVec3;
import kelvin.mite.main.resources.WorldTimeHelper;
import kelvin.mite.structures.MiteVillageStructure;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {

    @Shadow
    public ServerWorldProperties worldProperties;
    @Shadow
    public boolean shouldTickTime;
    @Shadow
    public void setTimeOfDay(long time) {}
    @Shadow
    public MinecraftServer server;

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
    }

    @Inject(at=@At("RETURN"), method="<init>")
    public void constructor(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> worldKey, DimensionType dimensionType, WorldGenerationProgressListener worldGenerationProgressListener, ChunkGenerator chunkGenerator, boolean debugWorld, long seed, List<Spawner> spawners, boolean shouldTickTime, CallbackInfo info) {
        try {
            Path save_path = ((ServerWorld)(Object)this).getServer().getSavePath(WorldSavePath.ROOT);
            String path = save_path.toFile().getPath();
            File file = new File(path+"\\"+"mite_settings.txt");
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path+"\\"+"mite_settings.txt"));

                MiteSaveData saveData = (MiteSaveData)ois.readObject();
                MiteVillageStructure.CanGenerateVillage = saveData.village_spawns;

                ois.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(at=@At("HEAD"), method="save")
    public void save(@Nullable ProgressListener progressListener, boolean flush, boolean savingDisabled, CallbackInfo info) {

        if (!savingDisabled) {
            try {
                Path save_path = ((ServerWorld)(Object)this).getServer().getSavePath(WorldSavePath.ROOT);
                String path = save_path.toFile().getPath();
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path+"\\"+"mite_settings.txt"));

                MiteSaveData save = new MiteSaveData();
                save.village_spawns = MiteVillageStructure.CanGenerateVillage;
                oos.writeObject(save);

                oos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    boolean start = false;
    public void tickDoubleTime() {
        WorldTimeHelper timeHelper = ((WorldTimeHelper)properties);
        if (!start) {
            timeHelper.SetDoubleTime(this.properties.getTime());
            start = true;
        }
        timeHelper.SetDoubleTime(timeHelper.GetDoubleTime() + Mite.time_increment);
    }

    @Inject(at=@At("RETURN"), method="tickTime")
    public void tickTime(CallbackInfo info) {
        if (this.shouldTickTime) {
            WorldTimeHelper timeHelper = ((WorldTimeHelper)properties);
            tickDoubleTime();
            long l = (long)timeHelper.GetDoubleTime();
            this.worldProperties.setTime(l);
            this.worldProperties.getScheduledEvents().processEvents(this.server, l);
            if (this.properties.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
                //this.setTimeOfDay(this.properties.getTimeOfDay() + 1L);
                this.setTimeOfDay((long)timeHelper.GetDoubleTime());
            } else {
                start = false;
            }

        } else {
            start = false;
        }
    }
}
