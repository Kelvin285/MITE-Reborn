package kelvin.mite.mixin.block;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import kelvin.mite.main.resources.FallingBlockHelper;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.tick.OrderedTick;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@Mixin(Block.class)
public class BlockMixin extends AbstractBlock {
	public BlockMixin(Settings settings) {
		super(settings);
	}

	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (!world.isClient()) {
			if (placer != null) {
				if ((Block)(Object)this == Blocks.DIRT ||
						(Block)(Object)this == Blocks.COBBLESTONE || (Block)(Object)this instanceof GrassBlock) {
					FallingBlockHelper.tryToFall(world, pos);
				}
				if ((Block)(Object)this == Blocks.HAY_BLOCK) {
					if (!world.getBlockState(pos.down()).isSolidBlock(world, pos.down())) {
						FallingBlockHelper.tryToFall(world, pos);
					}
				}
			}
		}
	}

	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!world.isClient()) {
			if ((Block)(Object)this == Blocks.DIRT ||
					(Block)(Object)this == Blocks.COBBLESTONE || (Block)(Object)this instanceof GrassBlock) {
				if (!world.getBlockState(pos.down()).isSolidBlock(world, pos.down())) {
					FallingBlockHelper.tryToFall(world, pos);
				}
			}
		}
	}
	
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
			WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!world.isClient()) {
			if ((Block)(Object)this == Blocks.DIRT ||
					(Block)(Object)this == Blocks.COBBLESTONE || (Block)(Object)this instanceof GrassBlock) {

				world.createAndScheduleBlockTick(pos, (Block)(Object)this, 2);
			}
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (!world.isClient()) {
			if ((Block)(Object)this == Blocks.DIRT || (Block)(Object)this == Blocks.COBBLESTONE || (Block)(Object)this instanceof FallingBlock
					|| (Block)(Object)this instanceof GrassBlock) {
				FallingBlockHelper.tryToFall(world, pos);
			}
		}

		entity.handleFallDamage(fallDistance, 1.0F, DamageSource.FALL);
	}

	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {

		if (!world.isClient()) {
			if ((Block)(Object)this == Blocks.DIRT || (Block)(Object)this == Blocks.COBBLESTONE || (Block)(Object)this instanceof FallingBlock
					|| (Block)(Object)this instanceof GrassBlock) {

				if (entity.getVelocity().length() > 0.0f) {
					FallingBlockHelper.tryToFall(world, pos);
				}
			}
		}
	}

	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		/*
		if ((Block)(Object)this == Blocks.DIRT || (Block)(Object)this instanceof FallingBlock
				|| (Block)(Object)this instanceof GrassBlock) {

			world.createAndScheduleBlockTick(pos, (Block)(Object)this, 2);
		}
		 */
	}



	@Shadow
	public Item asItem() {
		return null;
	}


	@Shadow
	protected Block asBlock() {
		return null;
	}
}
