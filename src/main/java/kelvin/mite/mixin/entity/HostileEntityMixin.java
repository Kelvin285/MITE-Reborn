package kelvin.mite.mixin.entity;

import kelvin.mite.main.Mite;
import kelvin.mite.main.resources.MoonHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(HostileEntity.class)
public abstract class HostileEntityMixin extends PathAwareEntity  {

    protected HostileEntityMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "isSpawnDark", cancellable = true)
    private static void isSpawnDark(ServerWorldAccess world, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> info) {
        if (pos.getY() > 55 && world.getRandom().nextInt(100) <= 95) {
            //info.setReturnValue(false);
        }
    }

    private void disablePlayerShield(PlayerEntity player, ItemStack mobStack, ItemStack playerStack) {
        if (!mobStack.isEmpty() && !playerStack.isEmpty() && mobStack.getItem() instanceof AxeItem && playerStack.isOf(Items.SHIELD)) {
            float f = 0.25F + (float)EnchantmentHelper.getEfficiency(this) * 0.05F;
            if (this.random.nextFloat() < f) {
                player.getItemCooldownManager().set(Items.SHIELD, 100);
                this.world.sendEntityStatus(player, (byte)30);
            }
        }
    }

    public boolean tryAttack(Entity target) {
        float attack_damage = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (MoonHelper.IsBloodMoon(Mite.day_time) && world.isNight()) {
            attack_damage += attack_damage * 0.5f;
        }
        float knockback = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
        if (target instanceof LivingEntity) {
            attack_damage += EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity)target).getGroup());
            knockback += (float)EnchantmentHelper.getKnockback(this);
        }

        int fire_aspect = EnchantmentHelper.getFireAspect(this);
        if (fire_aspect > 0) {
            target.setOnFireFor(fire_aspect * 4);
        }

        boolean damaged = target.damage(DamageSource.mob(this), attack_damage);
        if (damaged) {
            if (knockback > 0 && target instanceof LivingEntity) {
                ((LivingEntity)target).takeKnockback(knockback * 0.5f, MathHelper.sin(this.getYaw() * 0.017453292f), -MathHelper.cos(this.getYaw() * 0.017453292f));
                this.setVelocity(this.getVelocity().multiply(0.6, 1, 0.6));
            }

            if (target instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity)target;
                this.disablePlayerShield(playerEntity, this.getMainHandStack(), playerEntity.isUsingItem() ? playerEntity.getActiveItem() : ItemStack.EMPTY);
            }

            this.applyDamageEffects(this, target);
            this.onAttacking(target);
        }

        return damaged;
    }
}
