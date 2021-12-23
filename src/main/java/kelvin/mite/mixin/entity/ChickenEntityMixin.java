package kelvin.mite.mixin.entity;

import kelvin.mite.blocks.MiteGrassBlock;
import kelvin.mite.entity.GrassEater;
import kelvin.mite.entity.HungryAnimal;
import kelvin.mite.entity.goal.CanEatGrass;
import kelvin.mite.entity.goal.FoodTargetGoal;
import net.minecraft.block.GrassBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity implements GrassEater {
    protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public float flapProgress;
    @Shadow
    public float maxWingDeviation;
    @Shadow
    public float prevMaxWingDeviation;
    @Shadow
    public float prevFlapProgress;
    @Shadow
    public float flapSpeed = 1.0f;
    @Shadow
    private float field_28639 = 1.0f;
    @Shadow
    public int eggLayTime;
    @Shadow
    public boolean jockey;

    private int eatGrassTimer;
    private EatGrassGoal eatGrassGoal;

    @Shadow
    public boolean hasJockey() {
        return false;
    }

    @Inject(at=@At("HEAD"), method="initGoals")
    public void initGoals(CallbackInfo info) {
        this.eatGrassGoal = new EatGrassGoal(this);
        ((CanEatGrass)eatGrassGoal).setCanEatGrass(false);
        this.goalSelector.add(5, this.eatGrassGoal);

        FoodTargetGoal foodTargetGoal = new FoodTargetGoal(this, true);
        foodTargetGoal.onlyEatsPlants = true;
        this.targetSelector.add(4, foodTargetGoal);
    }

    public void mobTick() {
        super.mobTick();
        this.eatGrassTimer = this.eatGrassGoal.getTimer();
    }
    public void tickMovement() {
        super.tickMovement();
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation = (float)((double)this.maxWingDeviation + (double)(this.onGround ? -1 : 4) * 0.3);
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0, 1);
        if (!this.onGround && this.flapSpeed < 1 || this.getTarget() != null || this.getAttacker() != null) {
            this.flapSpeed = 1;
        }

        this.flapSpeed = (float)((double)this.flapSpeed * 0.9);
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0) {
            this.setVelocity(vec3d.multiply(1, 0.6, 1));
        }

        this.flapProgress += this.flapSpeed * 2.0f;
        if (!this.world.isClient && this.isAlive() && !this.isBaby() && !this.hasJockey() && --this.eggLayTime <= 0) {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            this.dropItem(Items.EGG);
            this.eggLayTime = this.random.nextInt(6000) + 24000;
        }
        if (this.world.isClient) {
            this.eatGrassTimer = Math.max(0, this.eatGrassTimer - 1);
        }

    }

    public void handleStatus(byte status) {
        if (status == 10) {
            this.eatGrassTimer = 40;
        } else {
            super.handleStatus(status);
        }

    }

    public float getNeckAngle(float delta) {
        if (this.eatGrassTimer <= 0) {
            return 0.0F;
        } else if (this.eatGrassTimer >= 4 && this.eatGrassTimer <= 36) {
            return 1.0F;
        } else {
            return this.eatGrassTimer < 4 ? ((float)this.eatGrassTimer - delta) / 4.0F : -((float)(this.eatGrassTimer - 40) - delta) / 4.0F;
        }
    }

    public float getHeadAngle(float delta) {
        if (this.eatGrassTimer > 4 && this.eatGrassTimer <= 36) {
            float f = ((float)(this.eatGrassTimer - 4) - delta) / 32.0F;
            return 0.62831855F + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.eatGrassTimer > 0 ? 0.62831855F : this.getPitch() * 0.017453292F;
        }
    }

    public void onEatingGrass() {
        super.onEatingGrass();
        ((HungryAnimal)this).eat();
    }
}
