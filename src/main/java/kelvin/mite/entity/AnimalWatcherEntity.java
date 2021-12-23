package kelvin.mite.entity;

import net.minecraft.block.Block;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AttackGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public interface AnimalWatcherEntity {
    boolean isHoldingItemThatPreventsDigging();

    boolean isDiggingEnabled();

    int recentlyHit();

    int getDestroyBlockZ();
    int getDestroyBlockX();
    int getDestroyBlockY();

    boolean canDestroyBlock(int destroyBlockX, int destroyBlockY, int destroyBlockZ, boolean b);

    boolean isDestroyingBlock();

    boolean setBlockToDig(int vec3_pool, int can_attacker_see_target, int path, boolean b);

    void setDestroyingBlock(boolean b);

    Vec3d getEyePosForBlockDestroying();

    boolean isFrenzied();

    boolean hasLineOfStrikeAndTargetIsWithinStrikingDistance(LivingEntity target);

    Vec3d getTargetEntityCenterPosForBlockDestroying(LivingEntity target);

    boolean canSeeTarget(boolean b);

    boolean isHoldingAnEffectiveTool(Block blockHit);

    Vec3d getAttackerLegPosForBlockDestroying();

    boolean blockWillFall(int block_hit_x, int i, int block_hit_z);

    int getDestroyPauseTicks();

    int getTicksExistedWithOffset();

    GoalSelector getGoalSelector();

    void decrementDestroyPauseTicks();

    int getDestroyBlockCooloff();

    int getCooloffForBlock();

    void setDestroyBlockCooloff(int cooloffForBlock);

    void partiallyDestroyBlock();

    void decrementDestroyBlockCooloff();

    void cancelBlockDestruction();

    int getDestroyBlockProgress();
}
