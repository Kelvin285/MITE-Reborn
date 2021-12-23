package kelvin.mite.mixin.client;

import kelvin.mite.entity.HorseKicking;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseEntityModel.class)
public abstract class HorseEntityModelMixin<T extends HorseBaseEntity> extends AnimalModel<T> {

    @Shadow
    private static  float EATING_GRASS_ANIMATION_HEAD_BASE_PITCH = 2.1816616F;
    @Shadow
    private static  float ANGRY_ANIMATION_FRONT_LEG_PITCH_MULTIPLIER = 1.0471976F;
    @Shadow
    private static  float ANGRY_ANIMATION_BODY_PITCH_MULTIPLIER = 0.7853982F;
    @Shadow
    private static  float HEAD_TAIL_BASE_PITCH = 0.5235988F;
    @Shadow
    private static  float ANGRY_ANIMATION_HIND_LEG_PITCH_MULTIPLIER = 0.2617994F;
    @Shadow
    protected static  String HEAD_PARTS = "head_parts";
    @Shadow
    private static  String LEFT_HIND_BABY_LEG = "left_hind_baby_leg";
    @Shadow
    private static  String RIGHT_HIND_BABY_LEG = "right_hind_baby_leg";
    @Shadow
    private static  String LEFT_FRONT_BABY_LEG = "left_front_baby_leg";
    @Shadow
    private static  String RIGHT_FRONT_BABY_LEG = "right_front_baby_leg";
    @Shadow
    private static  String SADDLE = "saddle";
    @Shadow
    private static  String LEFT_SADDLE_MOUTH = "left_saddle_mouth";
    @Shadow
    private static  String LEFT_SADDLE_LINE = "left_saddle_line";
    @Shadow
    private static  String RIGHT_SADDLE_MOUTH = "right_saddle_mouth";
    @Shadow
    private static  String RIGHT_SADDLE_LINE = "right_saddle_line";
    @Shadow
    private static  String HEAD_SADDLE = "head_saddle";
    @Shadow
    private static  String MOUTH_SADDLE_WRAP = "mouth_saddle_wrap";
    @Shadow
    protected  ModelPart body;
    @Shadow
    protected  ModelPart head;
    @Shadow
    private  ModelPart rightHindLeg;
    @Shadow
    private  ModelPart leftHindLeg;
    @Shadow
    private  ModelPart rightFrontLeg;
    @Shadow
    private  ModelPart leftFrontLeg;
    @Shadow
    private  ModelPart rightHindBabyLeg;
    @Shadow
    private  ModelPart leftHindBabyLeg;
    @Shadow
    private  ModelPart rightFrontBabyLeg;
    @Shadow
    private  ModelPart leftFrontBabyLeg;
    @Shadow
    private  ModelPart tail;
    @Shadow
    private  ModelPart[] saddle;
    @Shadow
    private  ModelPart[] straps;

    @Inject(at=@At("HEAD"),method="animateModel",cancellable = true)
    public void animateModel(T horseBaseEntity, float limbAngle, float limbDistance, float tickDelta, CallbackInfo info) {
        //super.animateModel(horseBaseEntity, limbAngle, limbDistance, tickDelta);
        boolean kicking = ((HorseKicking)horseBaseEntity).isKicking();
        float current_body_yaw = MathHelper.lerpAngle(horseBaseEntity.prevBodyYaw, horseBaseEntity.bodyYaw, tickDelta);
        float current_head_yaw = MathHelper.lerpAngle(horseBaseEntity.prevHeadYaw, horseBaseEntity.headYaw, tickDelta);
        float current_pitch = MathHelper.lerp(tickDelta, horseBaseEntity.prevPitch, horseBaseEntity.getPitch());
        float yaw_delta = current_head_yaw - current_body_yaw;
        float pitch_multiplied = current_pitch * 0.017453292F;
        if (yaw_delta > 20.0F) {
            yaw_delta = 20.0F;
        }

        if (yaw_delta < -20.0F) {
            yaw_delta = -20.0F;
        }

        if (limbDistance > 0.2F) {
            pitch_multiplied += MathHelper.cos(limbAngle * 0.4F) * 0.15F * limbDistance;
        }

        if (!kicking) {
            float eating_grass_animation_progress = horseBaseEntity.getEatingGrassAnimationProgress(tickDelta);
            float angry_animation_progress = horseBaseEntity.getAngryAnimationProgress(tickDelta);
            float angry_animation = 1.0F - angry_animation_progress;
            float eating_animation_progress = horseBaseEntity.getEatingAnimationProgress(tickDelta);
            boolean tail_wagging = horseBaseEntity.tailWagTicks != 0;
            float age = (float)horseBaseEntity.age + tickDelta;
            this.head.pivotY = 4.0F;
            this.head.pivotZ = -12.0F;
            this.body.pitch = 0.0F;
            this.head.pitch = 0.5235988F + pitch_multiplied;
            this.head.yaw = yaw_delta * 0.017453292F;
            float water_offset = horseBaseEntity.isTouchingWater() ? 0.2F : 1.0F;
            float cos_water_offset = MathHelper.cos(water_offset * limbAngle * 0.6662F + 3.1415927F);
            float water_leg_pitch = cos_water_offset * 0.8F * limbDistance;
            float animation_head_pitch = (1.0F - Math.max(angry_animation_progress, eating_grass_animation_progress)) * (0.5235988F + pitch_multiplied + eating_animation_progress * MathHelper.sin(age) * 0.05F);
            this.head.pitch = angry_animation_progress * (0.2617994F + pitch_multiplied) + eating_grass_animation_progress * (2.1816616F + MathHelper.sin(age) * 0.05F) + animation_head_pitch;
            this.head.yaw = angry_animation_progress * yaw_delta * 0.017453292F + (1.0F - Math.max(angry_animation_progress, eating_grass_animation_progress)) * this.head.yaw;
            this.head.pivotY = angry_animation_progress * -4.0F + eating_grass_animation_progress * 11.0F + (1.0F - Math.max(angry_animation_progress, eating_grass_animation_progress)) * this.head.pivotY;
            this.head.pivotZ = angry_animation_progress * -4.0F + eating_grass_animation_progress * -12.0F + (1.0F - Math.max(angry_animation_progress, eating_grass_animation_progress)) * this.head.pivotZ;
            this.body.pitch = angry_animation_progress * -0.7853982F + angry_animation * this.body.pitch;
            float back_leg_pitch = 0.2617994F * angry_animation_progress;
            float angry_leg_cosine = MathHelper.cos(age * 0.6F + 3.1415927F);
            this.leftFrontLeg.pivotY = 2.0F * angry_animation_progress + 14.0F * angry_animation;
            this.leftFrontLeg.pivotZ = -6.0F * angry_animation_progress - 10.0F * angry_animation;
            this.rightFrontLeg.pivotY = this.leftFrontLeg.pivotY;
            this.rightFrontLeg.pivotZ = this.leftFrontLeg.pivotZ;
            float angry_left_leg_pitch = (-1.0471976F + angry_leg_cosine) * angry_animation_progress + water_leg_pitch * angry_animation;
            float angry_right_leg_pitch = (-1.0471976F - angry_leg_cosine) * angry_animation_progress - water_leg_pitch * angry_animation;
            this.leftHindLeg.pitch = back_leg_pitch - cos_water_offset * 0.5F * limbDistance * angry_animation;
            this.rightHindLeg.pitch = back_leg_pitch + cos_water_offset * 0.5F * limbDistance * angry_animation;
            this.leftFrontLeg.pitch = angry_left_leg_pitch;
            this.rightFrontLeg.pitch = angry_right_leg_pitch;
            this.tail.pitch = 0.5235988F + limbDistance * 0.75F;
            this.tail.pivotY = -5.0F + limbDistance;
            this.tail.pivotZ = 2.0F + limbDistance * 2.0F;
            if (tail_wagging) {
                this.tail.yaw = MathHelper.cos(age * 0.7F);
            } else {
                this.tail.yaw = 0.0F;
            }
            this.rightHindLeg.pivotY = 14.0F;
            this.leftHindLeg.pivotY = 14.0F;
            this.rightHindLeg.pivotZ = 7.0F;
            this.leftHindLeg.pivotZ = 7.0F;
        } else {
            this.body.pitch = -(float)Math.toRadians(-15);
            this.head.pitch = -(float)Math.toRadians(-15);
            this.rightHindLeg.pitch = -(float)Math.toRadians(-90);
            this.leftHindLeg.pitch = -(float)Math.toRadians(-90);
            this.rightHindLeg.pivotY = 14.0F - 5;
            this.leftHindLeg.pivotY = 14.0F - 5;
            this.rightHindLeg.pivotZ = 7.0F + 3;
            this.leftHindLeg.pivotZ = 7.0F + 3;
        }

        this.rightHindBabyLeg.pivotY = this.rightHindLeg.pivotY;
        this.rightHindBabyLeg.pivotZ = this.rightHindLeg.pivotZ;
        this.rightHindBabyLeg.pitch = this.rightHindLeg.pitch;
        this.leftHindBabyLeg.pivotY = this.leftHindLeg.pivotY;
        this.leftHindBabyLeg.pivotZ = this.leftHindLeg.pivotZ;
        this.leftHindBabyLeg.pitch = this.leftHindLeg.pitch;
        this.rightFrontBabyLeg.pivotY = this.rightFrontLeg.pivotY;
        this.rightFrontBabyLeg.pivotZ = this.rightFrontLeg.pivotZ;
        this.rightFrontBabyLeg.pitch = this.rightFrontLeg.pitch;
        this.leftFrontBabyLeg.pivotY = this.leftFrontLeg.pivotY;
        this.leftFrontBabyLeg.pivotZ = this.leftFrontLeg.pivotZ;
        this.leftFrontBabyLeg.pitch = this.leftFrontLeg.pitch;
        boolean is_baby = horseBaseEntity.isBaby();
        this.rightHindLeg.visible = !is_baby;
        this.leftHindLeg.visible = !is_baby;
        this.rightFrontLeg.visible = !is_baby;
        this.leftFrontLeg.visible = !is_baby;
        this.rightHindBabyLeg.visible = is_baby;
        this.leftHindBabyLeg.visible = is_baby;
        this.rightFrontBabyLeg.visible = is_baby;
        this.leftFrontBabyLeg.visible = is_baby;
        this.body.pivotY = is_baby ? 10.8F : 0.0F;
        info.cancel();
    }
}
