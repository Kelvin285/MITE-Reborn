package kelvin.mite.mixin.entity;

import kelvin.mite.main.resources.FallingBlockHelper;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    public FallingBlockEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private boolean moving = false;

    private float speed = (9.81f / 20.0f) * 0.5f;
    private float speed_multiplier = 2;

    private boolean initialized = false;

    @Shadow
    private BlockState block;

    @Shadow
    private int timeFalling;
    @Shadow
    private long discardTime;

    private Vec3d move_dir = new Vec3d(0, 0, 0);


    public boolean canPlaceBlock() {
        if (this.isOnGround() && (!moving || getVelocity().length() <= 0.04f)) {
            return true;
        }
        return false;
    }

    public boolean canFallThrough(BlockPos pos) {
        return FallingBlock.canFallThrough(world.getBlockState(pos));
    }

    private boolean fallen = false;

    private boolean last_on_ground;

    private BlockPos move_pos = new BlockPos(0, 0, 0);

    private final int FALL = 0, SET_POSITION = 1, MOVE = 2;
    private int state = FALL;

    @Inject(at=@At("HEAD"), method = "tick", cancellable = true)
    public void tick(CallbackInfo info) {

        if (!initialized) {
            initialized = true;
            if (timeFalling == 0) {
                world.removeBlock(getBlockPos(), false);
            }
        }



        if (fallen) {
            moving = false;
            return;
        }
        if (this.block.isAir()) {
            moving = false;
            this.discard();
            return;
        }

        if (this.world.isClient && this.discardTime > 0L) {
            if (System.currentTimeMillis() >= this.discardTime) {
                super.setRemoved(RemovalReason.DISCARDED);
            }

        }

        this.move(MovementType.SELF, this.getVelocity());

        if (state == FALL) {
            moving = false;
            if (this.isOnGround()) {
                this.setVelocity(0, 0, 0);
                this.setPos(getBlockX() + 0.5f, getBlockY(), getBlockZ() + 0.5f);
                state = SET_POSITION;
            } else {
                if (!this.hasNoGravity()) {
                    this.setVelocity(this.getVelocity().add(0.0D, -0.04D * speed_multiplier, 0.0D));
                }
            }
        } else if (state == SET_POSITION) {
            if (block.getBlock() instanceof AnvilBlock) {
                fallen = true;
                moving = false;
                return;
            } else {
                ArrayList<Direction> directions = new ArrayList<Direction>();

                Random random = new Random();
                random.setSeed((int)(getX() + getY() + getZ()));

                if (canFallThrough(getBlockPos().add(1, 0, 0)) &&
                        canFallThrough(getBlockPos().add(1, -1, 0))) {
                    directions.add(Direction.EAST);
                }
                if (canFallThrough(getBlockPos().add(-1, 0, 0)) &&
                        canFallThrough(getBlockPos().add(-1, -1, 0))) {
                    directions.add(Direction.WEST);
                }
                if (canFallThrough(getBlockPos().add(0, 0, 1)) &&
                        canFallThrough(getBlockPos().add(0, -1, 1))) {
                    directions.add(Direction.NORTH);
                }
                if (canFallThrough(getBlockPos().add(0, 0, -1)) &&
                        canFallThrough(getBlockPos().add(0, -1, -1))) {
                    directions.add(Direction.SOUTH);
                }
                if (directions.size() > 0) {
                    Direction direction = directions.get(random.nextInt(directions.size()));
                    if (direction == Direction.EAST) {
                        moving = true;
                        move_pos = getBlockPos().add(1, 0, 0);
                        move_dir = new Vec3d(1, 0, 0);
                    } else if (direction == Direction.WEST) {
                        moving = true;
                        move_pos = getBlockPos().add(-1, 0, 0);
                        move_dir = new Vec3d(-1, 0, 0);
                    } else if (direction == Direction.NORTH) {
                        moving = true;
                        move_pos = getBlockPos().add(0, 0, 1);
                        move_dir = new Vec3d(0, 0, 1);
                    } else if (direction == Direction.SOUTH) {
                        moving = true;
                        move_pos = getBlockPos().add(0, 0, -1);
                        move_dir = new Vec3d(0, 0, -1);
                    }
                }


                world.createAndScheduleBlockTick(getBlockPos().north(), world.getBlockState(getBlockPos().north()).getBlock(), random.nextInt(3) + 1);
                world.createAndScheduleBlockTick(getBlockPos().east(), world.getBlockState(getBlockPos().east()).getBlock(), random.nextInt(3) + 1);
                world.createAndScheduleBlockTick(getBlockPos().south(), world.getBlockState(getBlockPos().south()).getBlock(), random.nextInt(3) + 1);
                world.createAndScheduleBlockTick(getBlockPos().west(), world.getBlockState(getBlockPos().west()).getBlock(), random.nextInt(3) + 1);

                if (moving) {
                    state = MOVE;
                    if (!world.isClient()) {
                        BlockPos left = getBlockPos().add(-1, 0, 0);
                        BlockPos right = getBlockPos().add(1, 0, 0);
                        BlockPos front = getBlockPos().add(0, 0, 1);
                        BlockPos back = getBlockPos().add(0, 0, -1);
                        BlockPos down = getBlockPos().add(0, -1, 0);
                        BlockPos[] plist = {left, right, front, back, down};
                        for (int i = 0; i < plist.length; i++) {
                            if (random.nextInt(10) == 0) {
                                BlockPos pos = plist[i];
                                if (FallingBlockHelper.isFallingBlock(world, pos)) {
                                    FallingBlockHelper.tryToFall(world, pos);
                                }
                            }
                        }
                    }
                } else {
                    if (fallDistance > 0.04F) {
                        state = FALL;
                    } else {
                        fallen = true;
                        moving = false;
                        return;
                    }
                }
            }
        } else if (state == MOVE) {
            List<Entity> entities = world.getOtherEntities(this, this.getBoundingBox(), (entity) -> {
                return entity instanceof LivingEntity;
            });

            this.setVelocity(move_dir.x * speed, 0, move_dir.z * speed);
            this.setVelocity(getVelocity().multiply(0.9F));

            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).setVelocity(entities.get(i).getVelocity().lerp(new Vec3d(getVelocity().x, entities.get(i).getVelocity().y, getVelocity().z), 0.75f));
                if (this.getPos().y > entities.get(i).getEyePos().y - 0.25F) {
                    entities.get(i).damage(DamageSource.FALLING_BLOCK, 2);
                }
            }

            if (Math.abs((getPos().x - 0.5f) - move_pos.getX()) <= 0.1f &&
            Math.abs((getPos().z - 0.5f) - move_pos.getZ()) <= 0.1f) {
                this.setVelocity(0, 0, 0);
                this.setPos(move_pos.getX() + 0.5f, getY(), move_pos.getZ() + 0.5f);
                state = FALL;
            }
        }

        this.setVelocity(this.getVelocity().multiply(0.98D));


        timeFalling++;

        /*
        if (this.canPlaceBlock()) {
            moving = false;
            fallen = true;
        }
         */

        info.cancel();
        last_on_ground = isOnGround();
    }

    @Shadow
    public void setRemoved(RemovalReason reason) {

    }
}
