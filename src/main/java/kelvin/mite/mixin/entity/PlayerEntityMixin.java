package kelvin.mite.mixin.entity;

import kelvin.mite.items.*;
import kelvin.mite.main.resources.MiteHungerManager;
import kelvin.mite.entity.PlayerInterface;
import kelvin.mite.registry.ItemRegistry;
import kelvin.mite.structures.MiteVillageStructure;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.input.Input;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerInterface {

	private final double MOVEMENT_SPEED = 0.10000000149011612D;
	
	@Shadow
	private int experienceLevel;
	@Shadow
	private HungerManager hungerManager;

	private int aliveTime = 0;


	private static TrackedData<Float> FRUIT = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);

	private static TrackedData<Float> VEGETABLES = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);

	private static TrackedData<Float> GRAIN = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);

	private static TrackedData<Float> DAIRY = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);

	private static TrackedData<Float> PROTEIN = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}


	@Inject(at = @At("RETURN"), method = "<init>")
	public void Constructor(World world, BlockPos pos, float yaw, GameProfile profile, CallbackInfo info) {
		while (world.getBlockState(pos).isSolidBlock(world, pos)) {
			pos = pos.add(0, 1, 0);
			this.setPos(getX(), getY() + 1, getZ());
		}
	}

	@Inject(at=@At("RETURN"), method = "initDataTracker")
	public void initDataTracker(CallbackInfo info) {
		this.dataTracker.startTracking(FRUIT, 12.0f);
		this.dataTracker.startTracking(VEGETABLES, 12.0f);
		this.dataTracker.startTracking(GRAIN, 12.0f);
		this.dataTracker.startTracking(DAIRY, 12.0f);
		this.dataTracker.startTracking(PROTEIN, 12.0f);
	}

	private boolean hot, cold;
	private float cold_lerp = 0;

	protected void updatePostDeath() {
		super.updatePostDeath();
		aliveTime = 0;
	}

	public void baseTick() {
		aliveTime++;
		this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(Math.min(20, (int)Math.floor(experienceLevel / 5) * 2 + 6));

		super.baseTick();
		MiteVillageStructure.CanGenerateVillage = MiteVillageStructure.canGenerate(world);

		if (world.hasRain(getBlockPos())) {
			this.hungerManager.addExhaustion(0.01f);
		}

		if (this.isOnGround() && this.getVelocity().x != 0 || this.getVelocity().z != 0) {
			((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.GRAIN, 0.01f);
			if (this.isSprinting()) {
				((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.GRAIN, 0.03f);
			}
		}

		hot = false;
		cold = false;

		if (world.getDimension().isUltrawarm()) {
			hot = true;
			cold = false;
		} else
		{
			float temperature = world.getBiome(getBlockPos()).getTemperature();
			hot = temperature > 1.5f;
			cold = temperature < 0.5f;
		}

		if (cold) {
			if (cold_lerp < 1) {
				cold_lerp += 0.01f;
			}
		} else {
			if (cold_lerp > 0) {
				cold_lerp -= 0.01f;
			}
		}

		float hunger_loss = 0.0001f;

		if (cold) {
			hunger_loss = 0.00001f;
		}
		if (hot) {
			hunger_loss = 0.001f;
		}

		this.hungerManager.addExhaustion(hunger_loss);
		((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.GRAIN, hunger_loss);
		((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.PROTEIN, hunger_loss);
		((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.FRUITS, hunger_loss);
		((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.VEGETABLES, hunger_loss);
		((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.DAIRY, hunger_loss);


		if (world.isClient()) {
			((MiteHungerManager)this.hungerManager).setSaturation(MiteHungerManager.HungerCategory.FRUITS, this.getDataTracker().get(FRUIT).floatValue());
			((MiteHungerManager)this.hungerManager).setSaturation(MiteHungerManager.HungerCategory.VEGETABLES, this.getDataTracker().get(VEGETABLES).floatValue());
			((MiteHungerManager)this.hungerManager).setSaturation(MiteHungerManager.HungerCategory.GRAIN, this.getDataTracker().get(GRAIN).floatValue());
			((MiteHungerManager)this.hungerManager).setSaturation(MiteHungerManager.HungerCategory.DAIRY, this.getDataTracker().get(DAIRY).floatValue());
			((MiteHungerManager)this.hungerManager).setSaturation(MiteHungerManager.HungerCategory.PROTEIN, this.getDataTracker().get(PROTEIN).floatValue());
		} else {
			getDataTracker().set(FRUIT, ((MiteHungerManager)this.hungerManager).getSaturation(MiteHungerManager.HungerCategory.FRUITS));
			getDataTracker().set(VEGETABLES, ((MiteHungerManager)this.hungerManager).getSaturation(MiteHungerManager.HungerCategory.VEGETABLES));
			getDataTracker().set(GRAIN, ((MiteHungerManager)this.hungerManager).getSaturation(MiteHungerManager.HungerCategory.GRAIN));
			getDataTracker().set(DAIRY, ((MiteHungerManager)this.hungerManager).getSaturation(MiteHungerManager.HungerCategory.DAIRY));
			getDataTracker().set(PROTEIN, ((MiteHungerManager)this.hungerManager).getSaturation(MiteHungerManager.HungerCategory.PROTEIN));
		}
	}

	public void swingHand(Hand hand, boolean fromServerPlayer) {
		super.swingHand(hand, fromServerPlayer);
		if (this.activeItemStack != null) {
			if (this.activeItemStack.getItem() instanceof ToolItem ||
			this.activeItemStack.getItem() instanceof TridentItem) {
				((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.VEGETABLES, hot ? 0.1f : 0.01f);
			}
		}
	}

	public void onAttacking(Entity target) {
		super.onAttacking(target);
		((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.VEGETABLES, hot ? 0.1f : 0.01f);
	}

	@Inject(at=@At("HEAD"),method="eatFood",cancellable = true)
	public void eatFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> info) {
		if (stack != null && stack.getItem() != null) {
			((MiteHungerManager)this.hungerManager).eatFood(stack.getItem());
		}
	}


	@Inject(at=@At("HEAD"), method="getMovementSpeed", cancellable = true)
	public void getMovementSpeed(CallbackInfoReturnable<Float> info) {
		float speed = (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) + ((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * ((MiteHungerManager)this.hungerManager).getSaturation(MiteHungerManager.HungerCategory.GRAIN) / ((MiteHungerManager)this.hungerManager).getMaxSaturation()) * 0.075f;
		speed = MathHelper.lerp(cold_lerp, speed, speed * 0.75f);
		info.setReturnValue(speed);
	}


	@Inject(at=@At("HEAD"), method="takeShieldHit", cancellable = true)
	public void takeShieldHit(LivingEntity attacker, CallbackInfo info) {
		super.takeShieldHit(attacker);
		((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.PROTEIN, hot ? 0.2f : 0.1f);
		info.cancel();
	}

	public float getReachDistance() {
		if (((PlayerEntity)(Object)this).isCreative()) {
			return 5.0F;
		}
		float reach = 1.5f;

		ItemStack selectedStack = selectedStack = getMainHandStack();
		if (selectedStack != null) {
			Item item = selectedStack.getItem();
			if (item == Items.STICK) reach += 0.25F;
			if (item == ItemRegistry.BRANCH) reach += 1.0F;
			else if (item == Items.BONE)reach += 0.25f;
			else if (item == ItemRegistry.WOODEN_CLUB) reach += 0.5f;
			else if (item == ItemRegistry.WOODEN_CUDGEL)reach += 0.25f;
			else if (item instanceof DaggerItem) reach += 0.5f;
			else if (item instanceof KnifeItem) reach += 0.25f;
			else if (item instanceof HatchetItem || item == Items.STONE_SHOVEL) reach += 0.5f;
			else if (item instanceof MiteWarhammerItem) reach += 0.75f;
			else if (item instanceof MiteMattockItem) reach += 0.75f;
			else if (item instanceof ScytheItem) reach += 1.0f;
			else if (item instanceof SpearItem) reach += 1.25f;
			else if (item instanceof TridentItem) reach += 1.25f;
			else if (item instanceof ShearsItem) reach += 0.5f;
			else if (item instanceof ShovelItem && item != Items.STONE_SHOVEL) reach += 0.75f;
			else if (item instanceof PickaxeItem) reach += 0.75f;
			else if (item instanceof AxeItem) reach += 0.75f;
			else if (item instanceof SwordItem) reach += 0.75f;
			else if (item instanceof HoeItem) reach += 0.75f;

		}
		return reach;
	}


	@Inject(at=@At("HEAD"), method="attackLivingEntity", cancellable = true)
	public void attackLivingEntity(LivingEntity target, CallbackInfo info) {
		((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.PROTEIN, hot ? 0.5f : 0.25f);

		if (target.getPos().distanceTo(getEyePos()) > getReachDistance() - 1.25f) {
			return;
		}
		target.damage(DamageSource.player((PlayerEntity)(Object)this), ((MiteHungerManager)this.hungerManager).getSaturation(MiteHungerManager.HungerCategory.PROTEIN) / ((MiteHungerManager)this.hungerManager).getMaxSaturation());

		if (target instanceof HostileEntity) {
			if (random.nextInt(3) == 0) {
				damage(DamageSource.mob(target), 1);
			}
		}

		super.attackLivingEntity(target);
		info.cancel();
	}

	@Inject(at=@At("HEAD"), method="jump", cancellable = true)
	public void jump(CallbackInfo info) {
		super.jump();
		if (this.isSprinting()) {
			((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.VEGETABLES, hot ? 0.2f : 0.1f);
		} else {
			((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.GRAIN, hot ? 0.01f : 0.005f);
			((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.VEGETABLES, hot ? 0.1f : 0.05f);
		}
		info.cancel();
	}
	
	public void swimUpward(Tag<Fluid> fluid) {
		super.swimUpward(fluid);
		((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.GRAIN, 0.01f);
		((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.VEGETABLES, 0.1f);

		this.setVelocity(this.getVelocity().add(0.0D, 0.02D * ((MiteHungerManager)this.hungerManager).getSaturation(MiteHungerManager.HungerCategory.VEGETABLES) / (((MiteHungerManager)this.hungerManager).getMaxSaturation() * (4.0f / 5.0f)), 0.0D));
	}

	@Inject(at=@At("HEAD"), method="damage", cancellable = true)
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info)
	{
		if (aliveTime < 30 * 20 && source.getAttacker() != null || ((PlayerEntity)(Object)this).isCreative() || ((PlayerEntity)(Object)this).isSpectator()) {
			info.cancel();
		} else {
			if (source == DamageSource.FALL) {
				((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.DAIRY, 0.5f * amount);
				//amount -= ((MiteHungerManager)this.hungerManager).getSaturation(MiteHungerManager.HungerCategory.DAIRY) / ((MiteHungerManager)this.hungerManager).getMaxSaturation();
			}
			((MiteHungerManager)this.hungerManager).addExhaustion(MiteHungerManager.HungerCategory.FRUITS, (hot ? 0.75f : 0.5f) * amount);

			info.setReturnValue(super.damage(source, amount));
		}

	}


	@Shadow
	private PlayerInventory getInventory() {
		return null;
	}

	@Inject(at = @At("HEAD"), method = "tick")
	public void tick(CallbackInfo info) {
		int weight = Resources.GetInventoryWeight(this.getInventory());
		double move_mult = 1;
		double slow_speed = 0.8F;
		if (weight > Resources.MAX_CARRY * 0.65) {
			move_mult = MathHelper.lerp(move_mult, slow_speed, (Resources.MAX_CARRY - weight) / (double)(Resources.MAX_CARRY - Resources.MAX_CARRY * 0.65));
		}
		if (weight > Resources.MAX_CARRY) {
			move_mult = slow_speed;
		}
		this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(MOVEMENT_SPEED * move_mult);
		this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue((hungerManager.getFoodLevel() + hungerManager.getSaturationLevel()) > 0 ? 1 : 0);

		int max_health = Math.min(20, (int)Math.floor(experienceLevel / 5) * 2 + 6);
		this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(max_health);

		if (this.getHealth() > max_health) {
			this.setHealth(max_health);
		}

		if (this.getInventory().count(Items.IRON_INGOT) > 0) {
			acquired_iron = true;
		}
	}
	
	@Inject(at = @At("RETURN"), method = "getBlockBreakingSpeed", cancellable = true)
	public void getBlockBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> info) {
		float speed = this.getInventory().getBlockBreakingSpeed(state);

		float original_speed = speed;

		float h = ((MiteHungerManager)hungerManager).getSaturation(MiteHungerManager.HungerCategory.VEGETABLES) / ((MiteHungerManager)hungerManager).getMaxSaturation();
		speed = MathHelper.lerp(h, speed, speed * 1.25f);

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
			} else {
				if (speed <= 20.0f) {
					speed = 20.0f;
				}
			}
		}
		if (original_speed <= 1.0f) { // no tool
			if (state.getBlock() instanceof MITELogBlock) {
				speed = -1.0f;
			}
			else if (state.getMaterial() == Material.WOOD && (state.getBlock() instanceof PillarBlock || state.getBlock() instanceof MITELogBlock)) {
				speed = -1.0f;
			} else if (state.getMaterial() == Material.METAL) {
				speed = -1.0f;
			} else if (state.getMaterial() == Material.ICE) {
				speed = -1.0f;
			} else if (state.getMaterial() == Material.STONE && state.getBlock() != Blocks.COBBLESTONE) {
				speed = -1.0f;
			} else if (state.getMaterial() == Material.DENSE_ICE) {
				speed = -1.0f;
			} else if (state.getMaterial() == Material.SOIL || BlockRegistry.CanSwapWithGrass(state.getBlock()) || state.getBlock() instanceof MiteGrassBlock) {
				//speed = -1.0f;
				speed *= 0.75f;
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

	public boolean acquired_iron = false;

	@Inject(at = @At("RETURN"), method = "readCustomDataFromNbt", cancellable = true)
	public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
		super.readCustomDataFromNbt(nbt);
		acquired_iron = nbt.getBoolean("AcquiredIron");
		this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(Math.min(20, (int)Math.floor(experienceLevel / 5) * 2 + 6));
		((MiteHungerManager)hungerManager).setSaturation(MiteHungerManager.HungerCategory.FRUITS, nbt.getFloat("Fruit"));
		((MiteHungerManager)hungerManager).setSaturation(MiteHungerManager.HungerCategory.VEGETABLES, nbt.getFloat("Vegetables"));
		((MiteHungerManager)hungerManager).setSaturation(MiteHungerManager.HungerCategory.GRAIN, nbt.getFloat("Grain"));
		((MiteHungerManager)hungerManager).setSaturation(MiteHungerManager.HungerCategory.DAIRY, nbt.getFloat("Dairy"));
		((MiteHungerManager)hungerManager).setSaturation(MiteHungerManager.HungerCategory.PROTEIN, nbt.getFloat("Protein"));
		this.aliveTime = nbt.getInt("AliveTime");
	}

	@Inject(at = @At("RETURN"), method = "writeCustomDataToNbt", cancellable = true)
	public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("AcquiredIron", acquired_iron);
		nbt.putFloat("Fruit", ((MiteHungerManager)hungerManager).getSaturation(MiteHungerManager.HungerCategory.FRUITS));
		nbt.putFloat("Vegetables", ((MiteHungerManager)hungerManager).getSaturation(MiteHungerManager.HungerCategory.VEGETABLES));
		nbt.putFloat("Grain", ((MiteHungerManager)hungerManager).getSaturation(MiteHungerManager.HungerCategory.GRAIN));
		nbt.putFloat("Dairy", ((MiteHungerManager)hungerManager).getSaturation(MiteHungerManager.HungerCategory.DAIRY));
		nbt.putFloat("Protein", ((MiteHungerManager)hungerManager).getSaturation(MiteHungerManager.HungerCategory.PROTEIN));
		nbt.putInt("AliveTime", aliveTime);
	}

	public void damageShield(float amount) {

	}
	public void takeShieldHit(LivingEntity attacker) {

	}

	public void setLastAttackTicks(int attackTicks) {
		this.lastAttackedTicks = attackTicks;
	}

	public int getLastAttackTicks() {
		return this.lastAttackedTicks;
	}

	public int getAliveTime() {
		return this.aliveTime;
	}
}
