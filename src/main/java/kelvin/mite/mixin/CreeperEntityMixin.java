package kelvin.mite.mixin;

import kelvin.mite.entity.CreeperData;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.world.*;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {
    private static final UUID BABY_SPEED_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final EntityAttributeModifier BABY_SPEED_BONUS = new EntityAttributeModifier(BABY_SPEED_ID, "Baby speed boost", 0.5D, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    private static final TrackedData<Boolean> BABY = DataTracker.registerData(CreeperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Shadow
    private static TrackedData<Integer> FUSE_SPEED;
    @Shadow
    private static TrackedData<Boolean> CHARGED;
    @Shadow
    private static TrackedData<Boolean> IGNITED;
    @Shadow
    private int lastFuseTime;
    @Shadow
    private int currentFuseTime;
    @Shadow
    private int fuseTime = 30;
    @Shadow
    private int explosionRadius = 3;
    @Shadow
    private int headsDropped;

    protected CreeperEntityMixin(EntityType<? extends CreeperEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FUSE_SPEED, -1);
        this.dataTracker.startTracking(CHARGED, false);
        this.dataTracker.startTracking(IGNITED, false);
        this.getDataTracker().startTracking(BABY, false);
    }

    @Shadow
    public void ignite(){}
    @Shadow
    public boolean isIgnited(){return false;}

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        float f = difficulty.getClampedLocalDifficulty();

        if (entityData == null) {
            boolean flag = world.getRandom().nextFloat() < 0.05F;
            if (flag) {
                System.out.println("bee");
            }
            entityData = new CreeperData(flag);
        }

        if (entityData instanceof CreeperData) {
            CreeperData creeperData = (CreeperData)entityData;
            if (creeperData.baby) {
                setBaby(true);
                this.explosionRadius = 1;
            }
        }

        return (EntityData)entityData;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if ((Boolean)this.dataTracker.get(CHARGED)) {
            nbt.putBoolean("powered", true);
        }

        nbt.putShort("Fuse", (short)this.fuseTime);
        nbt.putByte("ExplosionRadius", (byte)this.explosionRadius);
        nbt.putBoolean("ignited", this.isIgnited());
        nbt.putBoolean("IsBaby", isBaby());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.dataTracker.set(CHARGED, nbt.getBoolean("powered"));
        if (nbt.contains("Fuse", 99)) {
            this.fuseTime = nbt.getShort("Fuse");
        }

        if (nbt.contains("ExplosionRadius", 99)) {
            this.explosionRadius = nbt.getByte("ExplosionRadius");
        }

        if (nbt.getBoolean("ignited")) {
            this.ignite();
        }

        setBaby(nbt.getBoolean("IsBaby"));
    }
    @Override
    public double getHeightOffset() {
        return isBaby() ? 0.0D : -0.45D;
    }

    @Override
    public boolean isBaby() {
        return (Boolean)this.getDataTracker().get(BABY);
    }

    @Override
    protected int getXpToDrop(PlayerEntity player) {
        if (this.isBaby()) {
            this.experiencePoints = (int)((float)this.experiencePoints * 2.5F);
        }

        return super.getXpToDrop(player);
    }

    @Override
    public void setBaby(boolean baby) {
        this.getDataTracker().set(BABY, baby);
        if (this.world != null && !this.world.isClient) {
            EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            entityAttributeInstance.removeModifier(BABY_SPEED_BONUS);
            if (baby) {
                entityAttributeInstance.addTemporaryModifier(BABY_SPEED_BONUS);
            }
        }

    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (BABY.equals(data)) {
            this.calculateDimensions();
        }

        super.onTrackedDataSet(data);
    }
}
