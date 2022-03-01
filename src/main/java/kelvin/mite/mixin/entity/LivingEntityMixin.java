package kelvin.mite.mixin.entity;

import kelvin.mite.items.ShieldTier;
import kelvin.mite.main.resources.DelayedDamage;
import kelvin.mite.main.resources.MiteHungerManager;
import kelvin.mite.entity.PlayerInterface;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

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
	private float current_health = 0;
	private float last_health = 0;
	@Inject(at = @At("HEAD"), method = "tick")
	public void tick(CallbackInfo info) {

		last_health = current_health;
		current_health = ((LivingEntity)(Object)this).getHealth();

		damaged = -(current_health - last_health);

		if (damaged > 0)
		{
			if (damaged >= 2) {

				lastAttackedTicks = 0;
			}
			damaged = 0;
		}


		healing_ticks++;
		if ((Object)this instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)(Object)this;
			MiteHungerManager hungerManager = (MiteHungerManager)player.getHungerManager();
			float fruits = hungerManager.getSaturation(MiteHungerManager.HungerCategory.FRUITS) / hungerManager.getMaxSaturation();


			float mult = MathHelper.lerp(fruits, 5, 2.5f);
			if (healing_ticks > 20 * 60 * mult) {
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

	@Shadow
	public boolean isDead() {
		return false;
	}

	@Shadow
	public boolean hasStatusEffect(StatusEffect effect) {
		return false;
	}

	@Shadow
	public boolean blockedByShield(DamageSource source) {
		return false;
	}


	@Shadow
	protected int lastAttackedTicks;

	private float damaged = 0;
	@Inject(at=@At("HEAD"), method="damage", cancellable = true)
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {

		if (this.isInvulnerableTo(source)) {
			return;
		} else if (this.world.isClient) {
			return;
		} else if (this.isDead()) {
			return;
		} else if (source.isFire() && this.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
			return;
		} else {

			float f = amount;
			boolean bl = false;
			float g = 0.0F;
			float shield_damage = amount;
			if (amount > 0.0F && this.blockedByShield(source)) {
				this.CustomShieldDamage(shield_damage);
				g = amount;
				amount = 0.0F;
				if (!source.isProjectile()) {
					Entity entity = source.getSource();
					if (entity instanceof LivingEntity) {
						float dmg = this.takeShieldHit((LivingEntity)entity, shield_damage);

						info.setReturnValue(true);
					}
				}

				bl = true;
			} else {
				if (amount > 0.0F) {
					if (this.getActiveItem() != null) {
						if (this.getActiveItem().getItem() instanceof ShieldItem) {
							this.disableShield(true, 20);
						}
					}
				}
			}
		}
		damaged += amount;
	}

	@Shadow
	public ItemStack getActiveItem() {
		return null;
	}
	@Shadow
	public Hand getActiveHand() {return null;}

	@Shadow
	protected ItemStack activeItemStack;


	public void CustomShieldDamage(float amount) {
		if ((Object)this instanceof PlayerEntity) {
			if (this.getActiveItem() != null)
			if (this.getActiveItem().getItem() instanceof ShieldItem) {
				if (!this.world.isClient) {
					((PlayerEntity)(Object)this).incrementStat(Stats.USED.getOrCreateStat(this.getActiveItem().getItem()));
				}

				if (amount >= 3.0F) {
					int i = 1 + MathHelper.floor(amount);
					Hand hand = this.getActiveHand();
					this.getActiveItem().damage(i, (LivingEntity)(Object)this, (player) -> {
						player.sendToolBreakStatus(hand);
					});
					if (this.getActiveItem().isEmpty()) {
						if (hand == Hand.MAIN_HAND) {
							this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
						} else {
							this.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
						}
						activeItemStack = ItemStack.EMPTY;
						this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.random.nextFloat() * 0.4F);
					}
				}

			}
		}
	}

	public void damageShield(float amount) {

	}

	public void takeKnockback(double strength, double x, double z) {
		strength *= 1.0D - this.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
		if (!(strength <= 0.0D)) {
			this.velocityDirty = true;
			Vec3d vec3d = this.getVelocity();
			Vec3d vec3d2 = (new Vec3d(x, 0.0D, z)).normalize().multiply(strength);
			this.setVelocity(vec3d.x / 2.0D - vec3d2.x, this.onGround ? Math.min(0.4D, vec3d.y / 2.0D + strength) : vec3d.y, vec3d.z / 2.0D - vec3d2.z);
		}
	}

	long last_damaged = 0;
	float damage_accum = 0;
	public float takeShieldHit(LivingEntity attacker, float damage) {
		boolean hit = false;
		int shield_disable = 0;
		double knockback = 0.5D;
		boolean sprint = attacker.isSprinting();

		double damage_threshold = 4;

		if (!(attacker instanceof PlayerEntity)) {
			damage_threshold = ((ShieldTier)this.getActiveItem().getItem()).getProtectionLevel();
		}

		long now_damaged = System.currentTimeMillis();
		if (now_damaged > last_damaged + 1000L) {
			last_damaged = now_damaged;
			damage_accum = 0;
		}
		damage_accum += damage;

		Item item = attacker.getMainHandStack().getItem();


		if (item instanceof AxeItem) {
			knockback *= 1.0F;
		} else if (item instanceof TridentItem) {
			knockback *= 2.0F;
		} else if (item instanceof SwordItem) {
			knockback *= 1.25F;
		} else if (item instanceof ShovelItem) {
			knockback *= 1.75F;
		}

		shield_disable = (int)(60 * (damage_accum / 12.0F));

		if (attacker.hasStatusEffect(StatusEffects.SPEED)) {
			int speed = attacker.getStatusEffect(StatusEffects.SPEED).getAmplifier();
			if (attacker.isSprinting()) {
				knockback += 0.1 * speed;
			}
		}

		if (attacker.hasStatusEffect(StatusEffects.STRENGTH)) {
			int strength = attacker.getStatusEffect(StatusEffects.STRENGTH).getAmplifier();
			knockback += 0.05 * strength;
		}

		if (shield_disable > 0 && damage_accum > damage_threshold) {
			this.disableShield(attacker.isSprinting(), shield_disable);
		}

		takeKnockback(knockback, getX() - attacker.getX(), getZ() - attacker.getZ());


		return (float)(damage_accum - damage_threshold);
	}


	public void takeShieldHit(LivingEntity attacker) {

	}

	public void disableShield(boolean sprinting) {
		disableShield(sprinting, 60);
	}

	public void disableShield(boolean sprinting, int time) {

		float f = 0.25F + (float) EnchantmentHelper.getEfficiency((LivingEntity)(Object)this) * 0.05F;
		if (sprinting) {
			f += 0.75F;
		}

		if (this.random.nextFloat() < f) {

			((PlayerEntity)(Object)this).getItemCooldownManager().set(this.getActiveItem().getItem(), time);
			this.clearActiveItem();
			this.world.sendEntityStatus(this, (byte)30);
		}
	}

	@Shadow
	public boolean isUsingItem() {
		return false;
	}


	@Shadow
	public int itemUseTimeLeft;

	public boolean isBlocking() {
		if (this.isUsingItem() && !this.activeItemStack.isEmpty()) {
			Item item = this.activeItemStack.getItem();
			if (item.getUseAction(this.activeItemStack) != UseAction.BLOCK) {
				return false;
			} else {
				return item.getMaxUseTime(this.activeItemStack) - this.itemUseTimeLeft >= 2;
			}
		} else {
			return false;
		}
	}

	@Shadow
	public void clearActiveItem() {

	}
}
