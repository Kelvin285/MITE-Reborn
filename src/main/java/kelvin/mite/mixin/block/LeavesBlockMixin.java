package kelvin.mite.mixin.block;

import kelvin.mite.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collections;
import java.util.List;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends Block {

    private static BooleanProperty JUNGLE_OAK = Properties.SNOWY;

    public LeavesBlockMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
        return null;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        Biome biome = ctx.getWorld().getBiome(pos);
        boolean jungle = biome.getCategory() == Biome.Category.JUNGLE;
        return updateDistanceFromLogs((BlockState)this.getDefaultState().with(LeavesBlock.PERSISTENT, true).with(JUNGLE_OAK, jungle), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
       List<ItemStack> stacks = super.getDroppedStacks(state, builder);
       if (state.get(JUNGLE_OAK) == true) {
           for (int i = 0; i < stacks.size(); i++) {
               if (stacks.get(i).getItem() == Items.APPLE) {
                    ItemStack stack = stacks.get(i);
                    stacks.remove(i);
                    stacks.add(new ItemStack(ItemRegistry.ORANGE, stack.getCount()));
               }
           }
       }
       return stacks;
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LeavesBlock.DISTANCE, LeavesBlock.PERSISTENT, JUNGLE_OAK});
    }
}
