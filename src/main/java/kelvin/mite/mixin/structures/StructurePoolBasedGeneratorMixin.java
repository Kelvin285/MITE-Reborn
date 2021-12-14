package kelvin.mite.mixin.structures;

import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(StructurePoolBasedGenerator.class)
public class StructurePoolBasedGeneratorMixin {
    @Inject(at=@At("HEAD"), method="generate", cancellable = true)
    private static void generate(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context, StructurePoolBasedGenerator.PieceFactory pieceFactory, BlockPos pos, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>>> info) {

        //return Optional.empty();
    }
}