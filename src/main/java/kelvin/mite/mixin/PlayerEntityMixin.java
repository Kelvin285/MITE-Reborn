package kelvin.mite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.GameProfile;

import kelvin.mite.blocks.MITELogBlock;
import kelvin.mite.blocks.MiteGrassBlock;
import kelvin.mite.main.resources.Resources;
import kelvin.mite.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	private final double MOVEMENT_SPEED = 0.10000000149011612D;
	
	@Shadow
	private int experienceLevel;
	
	
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("RETURN"), method = "<init>")
	public void Constructor(World world, BlockPos pos, float yaw, GameProfile profile, CallbackInfo info) {
		
	}
	
	@Shadow
	private PlayerInventory getInventory() {
		return null;
	}
	
	@Inject(at = @At("HEAD"), method = "tick")
	public void tick(CallbackInfo info) {
		
		int weight = Resources.GetInventoryWeight(this.getInventory());
		double move_mult = 1;
		if (weight > Resources.MAX_CARRY * 0.65) {
			move_mult = MathHelper.lerp(move_mult, 0, (Resources.MAX_CARRY - weight) / (double)(Resources.MAX_CARRY - Resources.MAX_CARRY * 0.65));
		}
		if (weight > Resources.MAX_CARRY) {
			move_mult = 0;
		}
		this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(MOVEMENT_SPEED * move_mult);
		
		this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((int)Math.round(experienceLevel / 5) * 2 + 6);
	}
	
	@Inject(at = @At("RETURN"), method = "getBlockBreakingSpeed", cancellable = true)
	public void getBlockBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> info) {
		float speed = this.getInventory().getBlockBreakingSpeed(state);
		
		if (state.getBlock() instanceof MiteGrassBlock) {
			state = BlockRegistry.TrySwapFromGrass(state.getBlock()).getDefaultState();
		}
		
		PlayerEntity player = (PlayerEntity)(Object)this;
		if (player.getHungerManager().getFoodLevel() == 0 || player.getHealth() <= 1) {
			speed *= 0.5F;
		}
		if (state.getBlock() instanceof MITELogBlock) {
			if (this.getMainHandStack() != null) {
				if (this.getMainHandStack().getItem() != Items.STONE_AXE) {
					speed = -1.0f;
				}
			}
		}
		if (state.getMaterial() == Material.STONE) {
			Block block = state.getBlock();
			boolean flag = block == Blocks.COBBLESTONE || block == Blocks.COBBLESTONE_SLAB || block == Blocks.COBBLESTONE_STAIRS || block == Blocks.COBBLESTONE_WALL;
			
			if (!flag) {
				if (this.getMainHandStack() != null) {
					if (this.getMainHandStack().getItem() == Items.STONE_PICKAXE) {
						speed = -1.0f;
					}
				}
			}
		}
		if (speed <= 1.0f) { // no tool
			if (state.getBlock() instanceof MITELogBlock) {
				speed = -1.0f;
			}
			else if (state.getMaterial() == Material.WOOD && (state.getBlock() instanceof PillarBlock || state.getBlock() instanceof MITELogBlock)) {
				speed = -1.0f;
			} else if (state.getMaterial() == Material.METAL) {
				speed = -1.0f;
			} else if (state.getMaterial() == Material.ICE) {
				speed = -1.0f;
			} else if (state.getMaterial() == Material.STONE) {
				speed = -1.0f;
			} else if (state.getMaterial() == Material.DENSE_ICE) {
				speed = -1.0f;
			} else if (state.getMaterial() == Material.SOIL || BlockRegistry.CanSwapWithGrass(state.getBlock()) || state.getBlock() instanceof MiteGrassBlock) {
				speed = -1.0f;
			}
		}
		
		if (!state.hasBlockEntity()) {
			speed *= 0.05f;
		}
		
		if (state.getBlock() == Blocks.GRAVEL ||
				BlockRegistry.gravel_variants.contains(state.getBlock())) {
			speed *= 0.75f;
		}
		
		info.setReturnValue(speed);
	}
}
