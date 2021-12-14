package kelvin.mite.mixin.block;

import kelvin.mite.blocks.properties.MiteBlockProperties;
import kelvin.mite.blocks.properties.MiteBooleanProperty;
import net.minecraft.block.BeetrootsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(BeetrootsBlock.class)
public class BeetrootsBlockMixin extends CropBlock {


    protected BeetrootsBlockMixin(Settings settings) {
        super(settings);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(3) != 0) {
            super.randomTick(state, world, pos, random);
        }
    }

    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE, MiteBlockProperties.BLIGHTED});
    }
}
