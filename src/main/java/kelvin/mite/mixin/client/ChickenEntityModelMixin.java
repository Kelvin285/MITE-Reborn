package kelvin.mite.mixin.client;

import kelvin.mite.entity.GrassEater;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChickenEntityModel.class)
public abstract class ChickenEntityModelMixin<T extends Entity> extends AnimalModel<T> {

    @Shadow
    private ModelPart head;
    @Shadow
    private ModelPart body;
    @Shadow
    private ModelPart rightLeg;
    @Shadow
    private ModelPart leftLeg;
    @Shadow
    private ModelPart rightWing;
    @Shadow
    private ModelPart leftWing;
    @Shadow
    private ModelPart beak;
    @Shadow
    private ModelPart wattle;
    private float headPitchModifier;


    public void animateModel(T entity, float f, float g, float h) {
        super.animateModel(entity, f, g, h);
        //this.head.pivotY = 4.0F + ((GrassEater)entity).getNeckAngle(h) * 9.0F;
        this.headPitchModifier = ((GrassEater)entity).getHeadAngle(h);
    }

    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = this.headPitchModifier + headPitch * 0.017453292F;
        this.head.yaw = headYaw * 0.017453292F;
        this.beak.pitch = this.head.pitch;
        this.beak.yaw = this.head.yaw;
        this.wattle.pitch = this.head.pitch;
        this.wattle.yaw = this.head.yaw;
        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.rightWing.roll = animationProgress;
        this.leftWing.roll = -animationProgress;
    }
}
