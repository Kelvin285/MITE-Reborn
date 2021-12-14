package kelvin.mite.mixin.entity;

import kelvin.mite.main.resources.MiteHungerManager;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
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
	public double getAttributeValue(EntityAttribute attribute) {
		return 0;
	}

	public final float getMaxHealth() {
		if ((LivingEntity)(Object)this instanceof PlayerEntity) {
			PlayerEntity player = ((PlayerEntity)(Object)this);
			return (int)Math.floor(player.experienceLevel / 5) * 2 + 6;
		}
		return (float)this.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
	}

	@Shadow
	public void heal(float amount) {

	}

	private int healing_ticks = 0;
	@Inject(at = @At("HEAD"), method = "tick")
	public void tick(CallbackInfo info) {
		healing_ticks++;
		if ((Object)this instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)(Object)this;
			MiteHungerManager hungerManager = (MiteHungerManager)player.getHungerManager();
			float fruits = hungerManager.getSaturation(MiteHungerManager.HungerCategory.FRUITS) / hungerManager.getMaxSaturation();



			if (healing_ticks > 20 * 60 * (5 - (1.0f - fruits) * 2.5f)) {
				healing_ticks = 0;
				this.heal(1);
			}
		} else {
			if (healing_ticks > 20 * 60 * 5) {
				healing_ticks = 0;
				this.heal(1);
			}
		}

	}
}
