package kelvin.mite.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import kelvin.mite.blocks.MiteGrassBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin {
	@Inject(at = @At("RETURN"), method = "isValidNaturalSpawn", cancellable = true)
	private static void isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world,
			SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> info) {
		info.setReturnValue((world.getBlockState(pos.down()).getBlock() instanceof MiteGrassBlock) && world.getBaseLightLevel(pos, 0) > 8);
	}
}
