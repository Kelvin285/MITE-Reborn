package kelvin.fiveminsurvival.blocks;

import kelvin.fiveminsurvival.init.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class MITECakeBlock extends CakeBlock {

	private int hunger;
	private float saturation;
	private int happiness;
	
	public MITECakeBlock(int hunger, float saturation, int happiness) {
		this(Properties.create(Material.CAKE).hardnessAndResistance(0.1f));
		this.hunger = hunger;
		this.saturation = saturation;
		this.happiness = happiness;
	}
	
	protected MITECakeBlock(Properties properties) {
		super(properties);
	}
	
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
		if (worldIn.isRemote) {
	         ItemStack itemstack = player.getHeldItem(handIn);
	         if (this.eatCake(worldIn, pos, state, player) == ActionResultType.SUCCESS) {
	            return ActionResultType.SUCCESS;
	         }

	         if (itemstack.isEmpty()) {
	            return ActionResultType.CONSUME;
	         }
	      }

	      return this.eatCake(worldIn, pos, state, player);
	   }
	
	private ActionResultType eatCake(IWorld p_226911_1_, BlockPos p_226911_2_, BlockState p_226911_3_, PlayerEntity p_226911_4_) {
		if (this != BlockRegistry.UNBAKED_CAKE.get())
		if (p_226911_4_.getHeldItemMainhand() != null || p_226911_4_.getHeldItemOffhand() != null) {
			if (p_226911_4_.getHeldItemMainhand() != null && p_226911_4_.getHeldItemMainhand().getItem() instanceof DyeItem ||
					p_226911_4_.getHeldItemOffhand() != null && p_226911_4_.getHeldItemOffhand().getItem() instanceof DyeItem) {
				return ActionResultType.PASS;
			}
		}
	      if (!p_226911_4_.canEat(false)) {
	         return ActionResultType.PASS;
	      } else {
	         p_226911_4_.addStat(Stats.EAT_CAKE_SLICE);
	         p_226911_4_.getFoodStats().addStats(hunger, saturation);
	         int i = p_226911_3_.get(BITES);
	         if (i < 6) {
	            p_226911_1_.setBlockState(p_226911_2_, p_226911_3_.with(BITES, Integer.valueOf(i + 1)), 3);
	         } else {
	            p_226911_1_.removeBlock(p_226911_2_, false);
	         }

	         return ActionResultType.SUCCESS;
	      }
	   }
}

