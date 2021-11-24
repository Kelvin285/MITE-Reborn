package kelvin.mite.mixin.structures;

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
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.minecraft.world.gen.feature.VillageFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

@Mixin(JigsawFeature.Start.class)
public abstract class VillageGeneratorMixin extends MarginedStructureStart<StructurePoolFeatureConfig>  {
    @Shadow
    private JigsawFeature jigsawFeature;

    public VillageGeneratorMixin(StructureFeature<StructurePoolFeatureConfig> structureFeature, ChunkPos chunkPos, int i, long l) {
        super(structureFeature, chunkPos, i, l);
    }
    @Inject(at = @At("HEAD"), method = "init", cancellable = true)
    public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, ChunkPos chunkPos, Biome biome, StructurePoolFeatureConfig structurePoolFeatureConfig, HeightLimitView heightLimitView, CallbackInfo info) {
        if ((JigsawFeature)(Object)this instanceof VillageFeature) {
            info.cancel();
        }
    }

    /*
    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if (!world.isClient()) {
            List<? extends PlayerEntity> players = world.getPlayers();
            Field iron = null;
            try {
                iron = PlayerEntity.class.getDeclaredField("acquired_iron");
                boolean can_spawn = false;
                for (PlayerEntity p : players) {
                    if (iron.getBoolean(p)) {
                        can_spawn = true;
                        break;
                    }
                }
                if (!can_spawn) {
                    info.setReturnValue(false);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
     */
}
