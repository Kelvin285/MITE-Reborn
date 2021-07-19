package kelvin.trewrite.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	@Shadow
	public boolean canMoveVoluntarily() {
		return false;
	}
	@Shadow
	public boolean method_29920() {
		return false;
	}
	@Shadow
	public boolean canWalkOnFluid(Fluid fluid) {
		return false;
	}
	@Shadow
	public boolean isFallFlying() {
		return false;
	}
	@Shadow
	public Vec3d method_26318(Vec3d vec3d, float f) {
		return null;
	}
	
	@Shadow
	public boolean hasStatusEffect(StatusEffect levitation) {
		return false;
	}

	@Shadow
	public StatusEffectInstance getStatusEffect(StatusEffect levitation) {
		return null;
	}
	
	@Inject(at = @At("HEAD"), method = "getJumpVelocity", cancellable = true)
	protected void getJumpVelocity(CallbackInfoReturnable<Float> info) {
		info.setReturnValue(0.42F * this.getJumpVelocityMultiplier() * 2.75f);
	}
	@Inject(at = @At("HEAD"), method = "computeFallDamage", cancellable = true)
	protected void computeFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> info) {
		StatusEffectInstance statusEffectInstance = this.getStatusEffect(StatusEffects.JUMP_BOOST);
		float f = statusEffectInstance == null ? 0.0F : (float) (statusEffectInstance.getAmplifier() + 1) * 2;
		f += 25;
		info.setReturnValue(MathHelper.ceil((fallDistance - 3.0F - f) * damageMultiplier));
	}
	
	@Inject(at = @At("HEAD"), method = "tick")
	public void tick(CallbackInfo info) {
		this.stepHeight = 1;
	}
}
