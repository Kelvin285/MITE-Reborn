package kelvin.mite.mixin.structures;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import kelvin.mite.structures.MiteVillageStructure;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.VillageFeature;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Predicate;


@Mixin(StructureFeature.class)
public abstract class StructureFeatureMixin<C extends FeatureConfig>  {

//    public boolean canGenerate(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, BiomeSource biomeSource, StructureManager structureManager, long worldSeed, ChunkPos pos, C config, HeightLimitView world, Predicate<Biome> biomePredicate) {

    @Shadow
    @Mutable
    public static StructureFeature<StructurePoolFeatureConfig> VILLAGE;
    @Shadow
    public static BiMap<String, StructureFeature<?>> STRUCTURES;
    @Shadow
    private static Map<StructureFeature<?>, GenerationStep.Feature> STRUCTURE_TO_GENERATION_STEP;


    //private static StructureFeature<StructurePoolFeatureConfig> MITE_VILLAGE = set("Village", 15, "Village", VILLAGE, new MiteVillageStructure(StructurePoolFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);

    @Shadow
    private static <F extends StructureFeature<?>> F register(String name, F structureFeature, GenerationStep.Feature step) {
        return null;
    }

    private static StructureFeature set(String name, int index, String remove, StructureFeature remove_feature, StructureFeature structureFeature, GenerationStep.Feature step) {
        STRUCTURES.remove(remove);
        STRUCTURE_TO_GENERATION_STEP.remove(remove_feature);
        STRUCTURES.put(name.toLowerCase(Locale.ROOT), structureFeature);
        STRUCTURE_TO_GENERATION_STEP.put(structureFeature, step);
        var feature = (StructureFeature)((MutableRegistry)Registry.STRUCTURE_FEATURE).replace(OptionalInt.of(index), Registry.STRUCTURE_FEATURE_KEY, structureFeature, Lifecycle.stable());
        return feature;
    }

    @Inject(at=@At("HEAD"), method="tryPlaceStart", cancellable = true)
    public void tryPlaceStart(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, BiomeSource biomeSource, StructureManager structureManager, long worldSeed, ChunkPos pos, int structureReferences, StructureConfig structureConfig, C config, HeightLimitView world, Predicate<Biome> biomePredicate, CallbackInfoReturnable<StructureStart<?>> info) {
        if ((StructureFeature)(Object)this instanceof VillageFeature) {
            if (!MiteVillageStructure.CanGenerateVillage) info.setReturnValue(StructureStart.DEFAULT);
        }
    }

    @Inject(at=@At("HEAD"), method="canGenerate", cancellable = true)
    public void canGenerate(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, BiomeSource biomeSource, StructureManager structureManager, long worldSeed, ChunkPos pos, C config, HeightLimitView world, Predicate<Biome> biomePredicate, CallbackInfoReturnable<Boolean> info) {
        if ((StructureFeature)(Object)this instanceof VillageFeature) {
            info.setReturnValue(MiteVillageStructure.CanGenerateVillage);
        }
    }

    {
        VILLAGE = set("Village", 15, "Village", VILLAGE, new MiteVillageStructure(StructurePoolFeatureConfig.CODEC), GenerationStep.Feature.SURFACE_STRUCTURES);
    }

    /*
            VILLAGE = register("Village", new VillageFeature(StructurePoolFeatureConfig.CODEC), Feature.SURFACE_STRUCTURES);

            private static <F extends StructureFeature<?>> F register(String name, F structureFeature, Feature step) {
                STRUCTURES.put(name.toLowerCase(Locale.ROOT), structureFeature);
                STRUCTURE_TO_GENERATION_STEP.put(structureFeature, step);
                return (StructureFeature)Registry.register(Registry.STRUCTURE_FEATURE, name.toLowerCase(Locale.ROOT), structureFeature);
            }
     */
}
