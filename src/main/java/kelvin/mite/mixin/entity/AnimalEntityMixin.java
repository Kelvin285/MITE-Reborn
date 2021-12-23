package kelvin.mite.mixin.entity;

import java.util.Random;
import java.util.UUID;

import kelvin.mite.entity.HungryAnimal;
import kelvin.mite.entity.goal.DrinkWaterGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
public abstract class AnimalEntityMixin extends PassiveEntity implements HungryAnimal {

	private int hunger_timer = 0;
	private int thirst_timer = 0;
	private boolean sick = false;

	@Shadow
	private int loveTicks;
	@Shadow
	private UUID lovingPlayer;

	private static final TrackedData<Boolean> SICK = DataTracker.registerData(AnimalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	protected AnimalEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
	}

	public void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SICK, false);
	}

	public boolean shouldDropLoot() {

		return super.shouldDropLoot() && !isSick();
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("InLove", this.loveTicks);
		if (this.lovingPlayer != null) {
			nbt.putUuid("LoveCause", this.lovingPlayer);
		}
		nbt.putInt("hunger_timer", this.hunger_timer);
		nbt.putInt("thirst_timer", this.thirst_timer);
		nbt.putBoolean("sick", this.sick);
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.loveTicks = nbt.getInt("InLove");
		this.lovingPlayer = nbt.containsUuid("LoveCause") ? nbt.getUuid("LoveCause") : null;
		hunger_timer = nbt.getInt("hunger_timer");
		thirst_timer = nbt.getInt("thirst_timer");
		sick = nbt.getBoolean("sick");
	}

	private boolean init_goals = false;
	public void mobTick() {
		if (!init_goals) {
			init_goals = true;
			this.goalSelector.add(9, new DrinkWaterGoal((AnimalEntity)(Object)this));
		}
		if (age < 5) {
			hunger_timer = random.nextInt(7000);
			thirst_timer = random.nextInt(7000);
		}
		if (this.getBreedingAge() != 0) {
			this.loveTicks = 0;
		}
		hunger_timer++;
		thirst_timer++;
		if (hunger_timer > 24000 || thirst_timer > 24000) {
			sick = true;
		}

		if (!world.isClient()) {
			this.dataTracker.set(SICK, sick);
		} else {
			sick = this.dataTracker.get(SICK);
		}

		super.mobTick();
	}

	@Inject(at = @At("RETURN"), method = "isValidNaturalSpawn", cancellable = true)
	private static void isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world,
			SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> info) {
		info.setReturnValue((world.getBlockState(pos.down()).getBlock() instanceof MiteGrassBlock) && world.getBaseLightLevel(pos, 0) > 8);
	}

	@Inject(at=@At("RETURN"), method="canBreedWith", cancellable = true)
	public void canBreedWith(AnimalEntity other, CallbackInfoReturnable<Boolean> info) {
		if (info.getReturnValue()) {
			info.setReturnValue(!isSick());
		}
	}

	public void eat(PlayerEntity player, Hand hand, ItemStack stack) {
		if (!player.getAbilities().creativeMode) {
			stack.decrement(1);
		}
		eat();
	}

	public boolean damage(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) {
			return false;
		} else {
			this.loveTicks = 0;
			return super.damage(source, isSick() ? amount + 1 : amount);
		}
	}


	public int getXpToDrop(PlayerEntity player) {

		return isSick() ? 0 : 1 + this.world.random.nextInt(3);
	}


	@Override
	public boolean isHungry() {
		return hunger_timer > 6000;
	}

	@Override
	public boolean isThirsty() {
		return thirst_timer > 6000;
	}

	@Override
	public boolean isSick() {
		return sick;
	}

	@Override
	public void eat() {
		if (!isSick()) {
			hunger_timer = random.nextInt(1000);
		}
	}

	@Override
	public void drink() {
		if (!isSick()) {
			thirst_timer = random.nextInt(1000);
		}
	}
}
