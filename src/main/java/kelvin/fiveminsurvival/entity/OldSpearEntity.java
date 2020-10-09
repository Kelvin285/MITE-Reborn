//package kelvin.fiveminsurvival.entity;
//
//import javax.annotation.Nullable;
//
//import kelvin.fiveminsurvival.init.EntityRegistry;
//import kelvin.fiveminsurvival.init.ItemRegistry;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntitySize;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.Pose;
//import net.minecraft.entity.effect.LightningBoltEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.ServerPlayerEntity;
//import net.minecraft.entity.projectile.AbstractArrowEntity;
//import net.minecraft.entity.projectile.TridentEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.network.IPacket;
//import net.minecraft.network.datasync.DataParameter;
//import net.minecraft.network.datasync.DataSerializers;
//import net.minecraft.network.datasync.EntityDataManager;
//import net.minecraft.util.DamageSource;
//import net.minecraft.util.SoundEvent;
//import net.minecraft.util.SoundEvents;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.EntityRayTraceResult;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.World;
//import net.minecraft.world.server.ServerWorld;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.fml.network.NetworkHooks;
//
//public class OldSpearEntity extends AbstractArrowEntity {
//    private static final DataParameter<Byte> LOYALTY_LEVEL = EntityDataManager.createKey(TridentEntity.class, DataSerializers.BYTE);
//    private ItemStack thrownStack = new ItemStack(ItemRegistry.SPEAR.get());
//    private boolean dealtDamage;
//    public int returningTicks;
//
//    public OldSpearEntity(EntityType<? extends OldSpearEntity> p_i50148_1_, World p_i50148_2_) {
//        super(p_i50148_1_, p_i50148_2_);
//    }
//
//    public OldSpearEntity(World p_i48790_1_, LivingEntity p_i48790_2_, ItemStack p_i48790_3_) {
//        super(EntityRegistry.SPEAR_ENTITY.get(), p_i48790_2_, p_i48790_1_);
//        this.thrownStack = p_i48790_3_.copy();
//        this.dataManager.set(LOYALTY_LEVEL, (byte) EnchantmentHelper.getLoyaltyModifier(p_i48790_3_));
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    public OldSpearEntity(World p_i48791_1_, double p_i48791_2_, double p_i48791_4_, double p_i48791_6_) {
//        super(EntityRegistry.SPEAR_ENTITY.get(), p_i48791_2_, p_i48791_4_, p_i48791_6_, p_i48791_1_);
//    }
//
//    public OldSpearEntity(World world) {
//        super(EntityRegistry.SPEAR_ENTITY.get(), world);
//    }
//
//
//    protected void registerData() {
//        super.registerData();
//        this.dataManager.register(LOYALTY_LEVEL, (byte) 0);
//    }
//
//    public EntitySize getSize(Pose pose) {
//        return EntitySize.fixed(0.5f, 0.5f);
//    }
//
//    /**
//     * Called to update the entity's position/logic.
//     */
//    public void tick() {
//        if (this.timeInGround > 4) {
//            this.dealtDamage = true;
//        }
//
////	      System.out.println(this.getPosition());
//
//        Entity entity = this.getShooter();
//        if ((this.dealtDamage || this.getNoClip()) && entity != null) {
//            int i = this.dataManager.get(LOYALTY_LEVEL);
//            if (i > 0 && !this.shouldReturnToThrower()) {
//                if (!this.world.isRemote && this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
//                    this.entityDropItem(this.getArrowStack(), 0.1F);
//                }
//
//                this.remove();
//            } else if (i > 0) {
//                this.setNoClip(true);
//                Vec3d vec3d = new Vec3d(entity.getPosX() - this.getPosX(), entity.getPosY() + (double) entity.getEyeHeight() - this.getPosY(), entity.getPosZ() - this.getPosZ());
//                this.setPosition(getPosX(), vec3d.y * 0.015D * (double) i, getPosZ());
//                if (this.world.isRemote) {
//                    this.lastTickPosY = this.getPosY();
//                }
//
//                double d0 = 0.05D * (double) i;
//                this.setMotion(this.getMotion().scale(0.95D).add(vec3d.normalize().scale(d0)));
//                if (this.returningTicks == 0) {
//                    this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
//                }
//
//                ++this.returningTicks;
//            }
//        }
//
//        super.tick();
//    }
//
//    private boolean shouldReturnToThrower() {
//        Entity entity = this.getShooter();
//        if (entity != null && entity.isAlive()) {
//            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
//        } else {
//            return false;
//        }
//    }
//
//    protected ItemStack getArrowStack() {
//        return this.thrownStack.copy();
//    }
//
//    @Nullable
//    protected EntityRayTraceResult func_213866_a(Vec3d p_213866_1_, Vec3d p_213866_2_) {
//        return this.dealtDamage ? null : super.rayTraceEntities(p_213866_1_, p_213866_2_);
//    }
//
//    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
//
//        Entity entity = p_213868_1_.getEntity();
//        float f = 1.0F;
//        if (entity instanceof LivingEntity) {
//            LivingEntity livingentity = (LivingEntity) entity;
//            f += EnchantmentHelper.getModifierForCreature(this.thrownStack, livingentity.getCreatureAttribute());
//        }
//
//        Entity entity1 = this.getShooter();
//        DamageSource damagesource = DamageSource.causeTridentDamage(this, entity1 == null ? this : entity1);
//        this.dealtDamage = true;
//        SoundEvent soundevent = SoundEvents.ITEM_TRIDENT_HIT;
//        if (entity.attackEntityFrom(damagesource, f) && entity instanceof LivingEntity) {
//            LivingEntity livingentity1 = (LivingEntity) entity;
//            if (entity1 instanceof LivingEntity) {
//                EnchantmentHelper.applyThornEnchantments(livingentity1, entity1);
//                EnchantmentHelper.applyArthropodEnchantments((LivingEntity) entity1, livingentity1);
//            }
//
//            this.arrowHit(livingentity1);
//        }
//
//        this.setMotion(this.getMotion().mul(-0.01D, -0.1D, -0.01D));
//        float f1 = 1.0F;
//        if (this.world instanceof ServerWorld && this.world.isThundering() && EnchantmentHelper.hasChanneling(this.thrownStack)) {
//            BlockPos blockpos = entity.getPosition();
//            if (this.world.canBlockSeeSky(blockpos)) {
//                LightningBoltEntity lightningboltentity = new LightningBoltEntity(this.world, blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5D, false);
//                lightningboltentity.setCaster(entity1 instanceof ServerPlayerEntity ? (ServerPlayerEntity) entity1 : null);
//                ((ServerWorld) this.world).addLightningBolt(lightningboltentity);
//                soundevent = SoundEvents.ITEM_TRIDENT_THUNDER;
//                f1 = 5.0F;
//            }
//        }
//
//        this.playSound(soundevent, f1, 1.0F);
//
//    }
//
//    protected SoundEvent func_213867_k() {
//        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
//    }
//
//    /**
//     * Called by a player entity when they collide with an entity
//     */
//    public void onCollideWithPlayer(PlayerEntity entityIn) {
//        Entity entity = this.getShooter();
//        if (entity == null || entity.getUniqueID() == entityIn.getUniqueID()) {
//            super.onCollideWithPlayer(entityIn);
//        }
//    }
//
//    /**
//     * (abstract) Protected helper method to read subclass entity data from NBT.
//     */
//    public void readAdditional(CompoundNBT compound) {
//        super.readAdditional(compound);
//        if (compound.contains("Spear", 10)) {
//            this.thrownStack = ItemStack.read(compound.getCompound("Spear"));
//        }
//
//        this.dealtDamage = compound.getBoolean("DealtDamage");
//        this.dataManager.set(LOYALTY_LEVEL, (byte) EnchantmentHelper.getLoyaltyModifier(this.thrownStack));
//    }
//
//    public void writeAdditional(CompoundNBT compound) {
//        super.writeAdditional(compound);
//        compound.put("Spear", this.thrownStack.write(new CompoundNBT()));
//        compound.putBoolean("DealtDamage", this.dealtDamage);
//    }
//
//    protected void tryDespawn() {
//        int i = this.dataManager.get(LOYALTY_LEVEL);
//        if (this.pickupStatus != AbstractArrowEntity.PickupStatus.ALLOWED || i <= 0) {
//            super.checkDespawn();
//        }
//
//    }
//
//
//    @Override
//    public IPacket<?> createSpawnPacket() {
//
//        return NetworkHooks.getEntitySpawningPacket(this);
//    }
//
//    protected float getWaterDrag() {
//        return 0.99F;
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    public boolean isInRangeToRender3d(double x, double y, double z) {
//        return true;
//    }
//}
