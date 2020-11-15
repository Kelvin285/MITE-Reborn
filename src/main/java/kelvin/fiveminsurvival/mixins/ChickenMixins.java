package kelvin.fiveminsurvival.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@Mixin(ChickenEntity.class)
public class ChickenMixins extends AnimalEntity {
	protected ChickenMixins(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}
	@Shadow
	public int timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
	public boolean first = false;
	@Inject(method = "livingTick", at = @At("HEAD"))
	public void livingTick(CallbackInfo info) {
		if (!first) {
			timeUntilNextEgg = this.rand.nextInt(12000) + 12000;
		}
		if (!this.world.isRemote && this.isAlive() && !this.isChild() && !this.isChickenJockey() && --this.timeUntilNextEgg <= 0) {
	         this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
	         this.entityDropItem(Items.EGG);
			 this.timeUntilNextEgg = this.rand.nextInt(12000) + 12000;
	      }
	}
	
	@Shadow
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return null;
	}
	
	@Shadow
	public boolean isChickenJockey() {
		return false;
	}
}
