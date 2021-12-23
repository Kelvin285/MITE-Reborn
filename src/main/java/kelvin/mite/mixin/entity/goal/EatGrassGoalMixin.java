package kelvin.mite.mixin.entity.goal;

import kelvin.mite.entity.HungryAnimal;
import kelvin.mite.entity.goal.CanEatGrass;
import kelvin.mite.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Predicate;

@Mixin(EatGrassGoal.class)
public abstract class EatGrassGoalMixin extends Goal implements CanEatGrass {

    @Shadow
    private static int MAX_TIMER = 40;
    @Shadow
    private static Predicate<BlockState> GRASS_PREDICATE;
    @Shadow
    private MobEntity mob;
    @Shadow
    private World world;
    @Shadow
    private int timer;

    private boolean can_eat_grass = true;

    public boolean canEatGrass() {
        return can_eat_grass;
    }

    public void setCanEatGrass(boolean b) {
        can_eat_grass = b;
    }

    public boolean canStart() {
        if (this.mob.getRandom().nextInt(3000) != 0 || ((HungryAnimal)this.mob).isHungry()) {
            return false;
        } else {
            BlockPos blockPos = this.mob.getBlockPos();
            if (GRASS_PREDICATE.test(this.world.getBlockState(blockPos))) {
                return true;
            } else {
                return BlockRegistry.grass_variants.values().contains(world.getBlockState(blockPos.down()).getBlock());
            }
        }
    }

    public void tick() {
        this.timer = Math.max(0, this.timer - 1);
        if (this.timer == this.getTickCount(4)) {
            BlockPos blockPos = this.mob.getBlockPos();
            if (GRASS_PREDICATE.test(this.world.getBlockState(blockPos)) ||
                    this.world.getBlockState(blockPos).getBlock() instanceof CropBlock) {
                if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && can_eat_grass) {
                    this.world.breakBlock(blockPos, false);
                }

                this.mob.onEatingGrass();
                this.mob.emitGameEvent(GameEvent.EAT, this.mob.getCameraBlockPos());
            } else {
                BlockPos blockPos2 = blockPos.down();
                if (BlockRegistry.grass_variants.values().contains(world.getBlockState(blockPos2).getBlock())) {
                    if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                        this.world.syncWorldEvent(2001, blockPos2, Block.getRawIdFromState(Blocks.GRASS_BLOCK.getDefaultState()));
                        if (this.world.random.nextInt(5) == 0 && can_eat_grass) {
                            this.world.setBlockState(blockPos2, BlockRegistry.TrySwapFromGrass(world.getBlockState(blockPos.down()).getBlock()).getDefaultState(), 2);
                        }
                    }

                    this.mob.onEatingGrass();
                    this.mob.emitGameEvent(GameEvent.EAT, this.mob.getCameraBlockPos());
                }
            }

        }
    }
}
