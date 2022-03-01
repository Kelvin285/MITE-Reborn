package kelvin.mite.mixin.structures;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.feature.RuinedPortalFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(RuinedPortalFeature.class)
public class RuinedPortalFeatureMixin {
    /*
    if (airPocket) {
                j = MathHelper.nextBetween(random, 32, 100);
            } else if (random.nextFloat() < 0.5F) {
                j = MathHelper.nextBetween(random, 27, 29);
            } else {
                j = MathHelper.nextBetween(random, 29, 100);
            }
     */

    @Inject(at=@At("RETURN"), method="choosePlacementHeight", cancellable = true)
    private static void choosePlacementHeight(Random random, int min, int max, CallbackInfoReturnable<Integer> info) {
        if (min == 32 && max == 100 || min == 27 && max == 29 || min == 29 && max == 100) {
            //this is in the nether
            info.setReturnValue(min < max ? MathHelper.nextBetween(random, min, max) : max);
        } else {
            min = -50;
            max = -10;
            info.setReturnValue(min < max ? MathHelper.nextBetween(random, min, max) : max);
        }

    }
}
