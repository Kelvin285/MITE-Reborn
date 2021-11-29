package kelvin.mite.entity.rendering.turkey;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TurkeyModel<T extends LivingEntity & IAnimatable> extends AnimatedGeoModel<T> {

    @Override
    public Identifier getModelLocation(T object) {
        return new Identifier("mite", "geo/turkey/turkey.geo.json");
    }

    @Override
    public Identifier getTextureLocation(T object) {
        return new Identifier("mite", "textures/entity/turkey.png");
    }

    @Override
    public Identifier getAnimationFileLocation(T animatable) {
        return new Identifier("mite", "animations/turkey/animations.json");
    }

    @Override
    public Animation getAnimation(String name, IAnimatable animatable) {
        return super.getAnimation(name, animatable);
    }
}

