package kelvin.mite.mixin;

import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Explosion.class)
public class ExplosionMixin {
    @Shadow
    private Entity entity;
    @Shadow
    private List<BlockPos> affectedBlocks;
    @Shadow
    private World world;

    @Inject(at=@At("RETURN"), method="collectBlocksAndDamageEntities")
    public void collectBlocksAndDamageEntities(CallbackInfo info) {
        ArrayList<BlockPos> removing = new ArrayList<BlockPos>();
        if (entity instanceof CreeperEntity) {
            for (BlockPos pos : affectedBlocks) {
                if (world.getBlockState(pos).getBlock() instanceof PillarBlock ||
                world.getBlockState(pos).getMaterial() == Material.STONE ||
                world.getBlockState(pos).getMaterial() == Material.METAL) {
                    removing.add(pos);
                }
            }
        }
        for (BlockPos pos : removing) {
            affectedBlocks.remove(pos);
        }
    }
}
