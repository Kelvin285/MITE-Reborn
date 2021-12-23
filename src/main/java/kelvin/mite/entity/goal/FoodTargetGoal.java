package kelvin.mite.entity.goal;

import kelvin.mite.entity.HungryAnimal;
import kelvin.mite.registry.ItemRegistry;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.StewItem;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class FoodTargetGoal extends Goal {
    private static final int DEFAULT_RECIPROCAL_CHANCE = 10;
    @Nullable
    protected ItemEntity targetEntity;
    protected TargetPredicate targetPredicate;
    private int timeWithoutVisibility;

    public boolean onlyEatsMeat;
    public boolean onlyEatsPlants;
    public MobEntity mob;

    public boolean checkVisibility = true;

    private boolean active = false;

    public FoodTargetGoal(MobEntity mob, boolean checkVisibility) {
        this.mob = mob;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        this.findClosestTarget();
        return this.targetEntity != null;
    }

    public int getFollowRange() {
        return 8;
    }

    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, 4.0D, distance);
    }

    protected void findClosestTarget() {
        //    default <T extends Entity> List<T> getEntitiesByClass(Class<T> entityClass, Box box, Predicate<? super T> predicate) {
        var items = this.mob.world.getEntitiesByClass(
                ItemEntity.class,
                this.getSearchBox(this.getFollowRange()),
                (entity) -> {

                    if (onlyEatsPlants) {
                        if (entity instanceof ItemEntity &&
                                ((ItemEntity)entity).getStack() != null) {
                            Item item = ((ItemEntity)entity).getStack().getItem();
                            if (item == Items.WHEAT ||
                            item == Items.GRASS ||
                            item == Items.TALL_GRASS ||
                            item == Items.SEAGRASS ||
                            item == Items.DANDELION ||
                            item == Items.KELP ||
                            item == ItemRegistry.DRY_GRASS) {
                                return true;
                            }
                        }
                    }

                    if (entity instanceof ItemEntity &&
                            ((ItemEntity)entity).getStack() != null &&
                            ((ItemEntity)entity).getStack().getItem().isFood()) {
                        if (!((ItemEntity)entity).getStack().getItem().getFoodComponent().isMeat() &&  onlyEatsMeat ||
                                ((ItemEntity)entity).getStack().getItem().getFoodComponent().isMeat() &&  onlyEatsPlants) {
                            return false;
                        }
                        return true;
                    }
                    return false;
                });
        float distance = Float.MAX_VALUE;
        for (ItemEntity entity : items) {
            float dist = entity.distanceTo(mob);
            if (dist < distance) {
                distance = dist;
                targetEntity = entity;
            }
        }
    }

    public void start() {
        this.mob.setTarget(null);
        this.mob.getNavigation().startMovingTo(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), 1);
        this.active = true;

        super.start();
    }

    public void stop() {
        this.active = false;
    }

    public void tick() {
        super.tick();
        if (targetEntity.distanceTo(mob) <= 1) {
            if (targetEntity.getStack().getItem() instanceof StewItem) {
                targetEntity.setStack(new ItemStack(Items.BOWL));
            } else {
                targetEntity.kill();
            }
            if (mob instanceof HungryAnimal) {
                ((HungryAnimal)mob).eat();
            }
            stop();
        }
    }



    public boolean shouldContinue() {

        return !this.mob.getNavigation().isIdle() || targetEntity == null || (targetEntity != null && !targetEntity.isAlive());
    }
}
