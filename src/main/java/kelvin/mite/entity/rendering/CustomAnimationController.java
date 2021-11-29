package kelvin.mite.entity.rendering;

import com.eliotlash.molang.MolangParser;
import org.apache.commons.lang3.tuple.Pair;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.easing.EasingType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.keyframe.*;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import software.bernie.geckolib3.core.util.Axis;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CustomAnimationController<T extends IAnimatable> extends AnimationController<T> {

    public double speed = 1;
    private double ticks = 0;
    public CustomAnimationController(T animatable, String name, float transitionLengthTicks, IAnimationPredicate animationPredicate) {
        super(animatable, name, transitionLengthTicks, animationPredicate);
    }

    public CustomAnimationController(T animatable, String name, float transitionLengthTicks, EasingType easingtype, IAnimationPredicate animationPredicate) {
        super(animatable, name, transitionLengthTicks, easingtype, animationPredicate);
    }

    public CustomAnimationController(T animatable, String name, float transitionLengthTicks, Function customEasingMethod, IAnimationPredicate animationPredicate) {
        super(animatable, name, transitionLengthTicks, customEasingMethod, animationPredicate);
    }

    public void process(double tick, AnimationEvent<T> event, List<IBone> modelRendererList, HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection, MolangParser parser, boolean crashWhenCantFindBone) {
        ticks += speed;
        super.process(ticks, event, modelRendererList, boneSnapshotCollection, parser, crashWhenCantFindBone);
    }
}