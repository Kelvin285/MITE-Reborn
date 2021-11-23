package kelvin.mite.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	public void swimUpward(Tag<Fluid> fluid) {
		LivingEntity entity = (LivingEntity)(Object)this;
		if (!entity.hasStatusEffect(StatusEffects.SLOWNESS))
			entity.setVelocity(entity.getVelocity().add(0.0D, 0.03999999910593033D, 0.0D));
	}

	@Shadow
	public void heal(float amount) {

	}

	private int healing_ticks = 0;
	@Inject(at = @At("HEAD"), method = "tick")
	public void tick(CallbackInfo info) {
		healing_ticks++;
		if (healing_ticks > 20 * 60 * 5) {
			healing_ticks = 0;
			this.heal(1);
		}
	}
}
