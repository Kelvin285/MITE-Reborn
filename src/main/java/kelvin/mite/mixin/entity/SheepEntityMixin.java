package kelvin.mite.mixin.entity;

import kelvin.mite.entity.goal.FoodTargetGoal;
import kelvin.mite.registry.ItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity implements Shearable {
    private int poop_timer = 6000;

    @Shadow
    private int eatGrassTimer = 0;
    @Shadow
    private EatGrassGoal eatGrassGoal;

    protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"), method="initGoals")
    public void initGoals(CallbackInfo info) {
        FoodTargetGoal foodTargetGoal = new FoodTargetGoal(this, true);
        foodTargetGoal.onlyEatsPlants = true;
        this.targetSelector.add(4, foodTargetGoal);
    }

    public void mobTick() {
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

        super.mobTick();
    }
}
