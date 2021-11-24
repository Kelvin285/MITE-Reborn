package kelvin.mite.mixin.entity;

import kelvin.mite.entity.WolfData;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity implements Angerable {

    @Shadow
    private static TrackedData<Boolean> BEGGING;
    @Shadow
    private static TrackedData<Integer> COLLAR_COLOR;
    @Shadow
    private static TrackedData<Integer> ANGER_TIME;

    private static TrackedData<Boolean> DIRE = DataTracker.registerData(WolfEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }



    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData data = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);

        if (data == null) {
            data = new WolfData(world.getBiome(getBlockPos()).getCategory() == Biome.Category.ICY && random.nextInt(50) == 0);
            if (entityData instanceof WolfData) {
                WolfData wolfData = (WolfData) entityData;
                if (wolfData.dire) {
                    setDire(true);
                }
            }
        }

        return data;
    }

    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.isDire()) {
            if (itemStack.isOf(Items.BONE)) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                float roll = this.random.nextFloat();
                int outcome = 0;
                if (roll < 0.2F)
                {
                    outcome = -1;
                }
                else if (roll < 0.4F)
                {
                    outcome = 0;
                }
                else if (roll > 0.95F)
                {
                    outcome = 1;
                }
                else
                {
                    roll += this.random.nextFloat() * (float)player.experienceLevel * 0.02F;
                    outcome = roll < 0.5F ? -1 : (roll < 1.0F ? 0 : 1);
                }

                if (outcome == 1) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.setTarget((LivingEntity)null);
                    this.setSitting(true);
                    this.world.sendEntityStatus(this, (byte)7);
                } else {
                    this.world.sendEntityStatus(this, (byte)6);
                }

                info.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (this.isDire()) {
            if (tamed) {

                this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(24.0D);
                this.setHealth(20.0F);
            } else {
                this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(16.0D);
            }
            this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(5.0D);
        } else
        {
            if (tamed) {

                this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
                this.setHealth(20.0F);
            } else {
                this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(8.0D);
            }
            this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(4.0D);
        }

    }

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    public void tick(CallbackInfo info) {
        if (isDire() && !isTamed() && this.random.nextFloat() < 0.004F) {
            if (this.getAngryAt() != null) {
                PlayerEntity player = world.getClosestPlayer(this, 4);
                if (player != null) {
                    this.setAngryAt(player.getUuid());
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "createWolfAttributes", cancellable = true)
    private static void createWolfAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        info.setReturnValue(MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896D).add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D));
    }

    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BEGGING, false);
        this.dataTracker.startTracking(COLLAR_COLOR, DyeColor.RED.getId());
        this.dataTracker.startTracking(ANGER_TIME, 0);
        this.dataTracker.startTracking(DIRE, false);
    }

    @Shadow
    public DyeColor getCollarColor() {
        return null;
    }

    @Shadow
    public void setCollarColor(DyeColor color) {

    }
    @Inject(at = @At("HEAD"), method = "canBreedWith", cancellable = true)
    public void canBreedWith(AnimalEntity other, CallbackInfoReturnable<Boolean> info) {
        if (isDire()) {
            info.setReturnValue(false);
        }
    }

    public boolean isDire() {
        return this.dataTracker.get(DIRE);
    }

    public void setDire(boolean dire) {
        this.dataTracker.set(DIRE, dire);
    }


    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("CollarColor", (byte)this.getCollarColor().getId());
        this.writeAngerToNbt(nbt);
        nbt.putBoolean("Dire", isDire());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(nbt.getInt("CollarColor")));
        }
        this.readAngerFromNbt(this.world, nbt);
        if (nbt.contains("Dire"))
        {
            this.setDire(nbt.getBoolean("Dire"));
        }
    }
}
