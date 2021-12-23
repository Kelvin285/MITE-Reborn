package kelvin.mite.mixin.client;

import kelvin.mite.entity.GrassEater;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.GoatEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.passive.GoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinInner;

@Mixin(GoatEntityModel.class)
public abstract class GoatEntityModelMixin<T extends GoatEntity> extends QuadrupedEntityModel<T> {
    private float headPitchModifier;

    protected GoatEntityModelMixin(ModelPart root, boolean headScaled, float childHeadYOffset, float childHeadZOffset, float invertedChildHeadScale, float invertedChildBodyScale, int childBodyYOffset) {
        super(root, headScaled, childHeadYOffset, childHeadZOffset, invertedChildHeadScale, invertedChildBodyScale, childBodyYOffset);
    }

    public void animateModel(T goatEntity, float f, float g, float h) {
        super.animateModel(goatEntity, f, g, h);
        this.head.pivotY = 12.0F + ((GrassEater)goatEntity).getNeckAngle(h) * 2.0F;
        this.headPitchModifier = ((GrassEater)goatEntity).getHeadAngle(h);
    }

    @Inject(at=@At("HEAD"),method="setAngles",cancellable = true)
    public void setAngles(T goatEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
        this.head.getChild("left_horn").visible = !goatEntity.isBaby();
        this.head.getChild("right_horn").visible = !goatEntity.isBaby();
        super.setAngles(goatEntity, f, g, h, i, j);
        float k = goatEntity.getHeadPitch();
        float pitch = headPitchModifier;
        if (k != 0.0F) {
            pitch += k;
        }
        this.head.pitch = pitch;
        info.cancel();
    }
}
