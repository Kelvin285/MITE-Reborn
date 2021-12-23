package kelvin.mite.mixin.entity;

import kelvin.mite.entity.goal.FoodTargetGoal;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin  extends HostileEntity implements RangedAttackMob {

    protected SpiderEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }



    @Inject(at=@At("RETURN"), method = "createSpiderAttributes", cancellable = true)
    private static void createSpiderAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
        info.setReturnValue(HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896D));
    }

    public MiteTargetGoal attack_player, attack_golem;

    public EscapeDangerGoal flee;

    public void initGoals() {
        flee = new EscapeDangerGoal(this, 0.4F);
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(4, new MiteAttackGoal((SpiderEntity)(Object)this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 32.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, attack_player = new MiteTargetGoal((SpiderEntity)(Object)this, PlayerEntity.class));
        this.targetSelector.add(3, attack_golem = new MiteTargetGoal((SpiderEntity)(Object)this, IronGolemEntity.class));

        FoodTargetGoal foodTargetGoal = new FoodTargetGoal(this, true);
        foodTargetGoal.onlyEatsMeat = true;
        this.targetSelector.add(4, foodTargetGoal);
    }

    private static class MiteTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
        public MiteTargetGoal(SpiderEntity spider, Class<T> targetEntityClass) {
            super(spider, targetEntityClass, true);
        }

        public boolean canStart() {
            float f = this.mob.getBrightnessAtEyes();
            return f >= 0.5F ? false : super.canStart();
        }
    }

    @Shadow
    public void setClimbingWall(boolean b) {

    }


    private boolean low_health = false;

    public void tick() {
        super.tick();
        if (!this.world.isClient) {
            this.setClimbingWall(this.horizontalCollision);
        }
        if (angry_ticks > 0) {
            angry_ticks--;
        }

        if (flee != null && attack_player != null && attack_golem != null && goalSelector != null) {
            if (getHealth() <= getMaxHealth() / 2) {
                if (!low_health) {
                    this.goalSelector.remove(attack_player);
                    this.goalSelector.remove(attack_golem);
                    this.goalSelector.add(2, flee);
                }
                low_health = true;
            } else {
                if (low_health) {
                    this.goalSelector.add(2, attack_player);
                    this.goalSelector.add(3, attack_golem);
                }
                low_health = false;

                if (getTarget() != null && angry_ticks > 0) {
                    if (random.nextInt(20 * 60) == 0) {
                        attack(getTarget(), 1.0f);
                    }
                }
            }
        }
    }

    public void attack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getArrowType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
        FallingBlockEntity entity = new FallingBlockEntity(world, getX(), getY() + 0.5f, getZ(), Blocks.COBWEB.getDefaultState());
        double delta_x = target.getX() - this.getX();
        double delta_y = target.getBodyY(0.3333333333333333D) - entity.getY();
        double delta_z = target.getZ() - this.getZ();
        double length = Math.sqrt(delta_x * delta_x + delta_z * delta_z);
        setVelocityOf(entity, delta_x, delta_y + length * 0.20000000298023224D, delta_z, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(entity);
    }

    public void setVelocityOf(Entity entity, double x, double y, double z, float speed, float divergence) {
        Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.random.nextGaussian() * 0.007499999832361937D * (double)divergence, this.random.nextGaussian() * 0.007499999832361937D * (double)divergence, this.random.nextGaussian() * 0.007499999832361937D * (double)divergence).multiply((double)speed);
        entity.setVelocity(vec3d);
        double d = vec3d.horizontalLength();
        entity.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875D));
        entity.setPitch((float)(MathHelper.atan2(vec3d.y, d) * 57.2957763671875D));
        entity.prevYaw = entity.getYaw();
        entity.prevPitch = entity.getPitch();
    }

    private int angry_ticks = 0;

    private static class MiteAttackGoal extends MeleeAttackGoal {
        public MiteAttackGoal(SpiderEntity spider) {
            super(spider, 1.0D, true);
        }

        public boolean canStart() {
            return super.canStart() && !this.mob.hasPassengers();
        }

        public boolean shouldContinue() {
            float f = this.mob.getBrightnessAtEyes();
            if (f >= 0.5F && this.mob.getRandom().nextInt(100) == 0 && ((SpiderEntityMixin)mob).angry_ticks <= 0) {
                this.mob.setTarget((LivingEntity)null);
                return false;
            } else {
                return super.shouldContinue();
            }
        }

        public void start() {
            super.start();
            ((SpiderEntityMixin)mob).angry_ticks = 20 * 60 * 4;
        }


        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (double)(4.0F + entity.getWidth());
        }
    }
}
