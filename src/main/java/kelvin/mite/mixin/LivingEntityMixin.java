package kelvin.mite.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.Tag;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	public void swimUpward(Tag<Fluid> fluid) {
		LivingEntity entity = (LivingEntity)(Object)this;
		if (!entity.hasStatusEffect(StatusEffects.SLOWNESS))
			entity.setVelocity(entity.getVelocity().add(0.0D, 0.03999999910593033D, 0.0D));
	}
}
