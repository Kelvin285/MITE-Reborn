package kelvin.mite.mixin.client;

import kelvin.mite.entity.GrassEater;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PigEntityModel.class)
public abstract class PigEntityModelMixin<T extends Entity> extends QuadrupedEntityModel<T> {
    private float headPitchModifier;

    protected PigEntityModelMixin(ModelPart root, boolean headScaled, float childHeadYOffset, float childHeadZOffset, float invertedChildHeadScale, float invertedChildBodyScale, int childBodyYOffset) {
        super(root, headScaled, childHeadYOffset, childHeadZOffset, invertedChildHeadScale, invertedChildBodyScale, childBodyYOffset);
    }

    public void animateModel(T cowEntity, float f, float g, float h) {
        super.animateModel(cowEntity, f, g, h);
        this.head.pivotY = 12.0F + ((GrassEater)cowEntity).getNeckAngle(h) * 2.0F;
        this.headPitchModifier = ((GrassEater)cowEntity).getHeadAngle(h);
    }

    public void setAngles(T sheepEntity, float f, float g, float h, float i, float j) {
        super.setAngles(sheepEntity, f, g, h, i, j);
        this.head.pitch = this.headPitchModifier;
    }
}
