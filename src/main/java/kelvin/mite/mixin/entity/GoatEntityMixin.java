package kelvin.mite.mixin.entity;

import kelvin.mite.entity.GrassEater;
import kelvin.mite.entity.HungryAnimal;
import kelvin.mite.entity.goal.CanEatGrass;
import kelvin.mite.entity.goal.FoodTargetGoal;
import kelvin.mite.registry.ItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GoatBrain;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.security.auth.callback.Callback;

@Mixin(GoatEntity.class)
public abstract class GoatEntityMixin extends AnimalEntity implements GrassEater {

    protected GoatEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    private int poop_timer = 6000;
    private int eatGrassTimer;
    private EatGrassGoal eatGrassGoal;


    public void initGoals() {
        super.initGoals();
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.add(5, this.eatGrassGoal);
        FoodTargetGoal foodTargetGoal = new FoodTargetGoal(this, true);
        foodTargetGoal.onlyEatsPlants = true;
        this.targetSelector.add(4, foodTargetGoal);
    }

    @Inject(at=@At("HEAD"), method="mobTick")
    protected void mobTick(CallbackInfo info) {
        this.eatGrassTimer = this.eatGrassGoal.getTimer();

        if (poop_timer > 0) {
            poop_timer--;
        } else {
            poop_timer = random.nextInt(2000) + 4000;
            this.dropItem(new ItemConvertible() {
                @Override
                public Item asItem() {
                    return ItemRegistry.MANURE;
                }
            });
        }
    }

    @Inject(at=@At("HEAD"), method="tickMovement")
    public void tickMovement(CallbackInfo info) {
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
