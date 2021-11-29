package kelvin.mite.entity;

import kelvin.mite.entity.rendering.CustomAnimationController;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TurkeyEntity extends ChickenEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    private AnimationBuilder anim_idle, anim_walk, anim_run;

    public TurkeyEntity(EntityType<? extends TurkeyEntity> entityType, World world) {
        super(entityType, world);
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        CustomAnimationController controller = (CustomAnimationController)event.getController();

        controller.speed = 1;

        if (this.anim_idle == null) {
            anim_idle = new AnimationBuilder().addAnimation("idle", true);
            anim_walk = new AnimationBuilder().addAnimation("walk", true);
            anim_run = new AnimationBuilder().addAnimation("run", true);
        }

        double mov_x = Math.abs(getVelocity().x);
        double mov_z = Math.abs(getVelocity().z);

        if (this.onGround) {
            if ((mov_x > 0.05 || mov_z > 0.05)) {
                controller.speed = 2.5f;
                if (isSprinting()) {
                    controller.setAnimation(anim_run);
                } else {
                    controller.setAnimation(anim_walk);
                }
            } else {
                controller.speed = 1.0f;
                controller.setAnimation(anim_idle);
            }
        } else {
            controller.speed = 2.5f;
            controller.setAnimation(anim_run);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new CustomAnimationController(this, "controller", 5, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
