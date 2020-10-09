package kelvin.fiveminsurvival.entity;

import javax.annotation.Nullable;

import kelvin.fiveminsurvival.init.ItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class NewSkeletonEntity extends AbstractSkeletonEntity {

	public NewSkeletonEntity(EntityType<? extends AbstractSkeletonEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_SKELETON_STEP;
	}
	
	/**
	    * Gives armor or weapon for entity based on given DifficultyInstance
	    */
	   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		   ItemStack club = new ItemStack(ItemRegistry.WOODEN_CLUB.get());
		   club.setDamage(rand.nextInt(4) + 2);
	      this.setItemStackToSlot(EquipmentSlotType.MAINHAND, club);
	   }

	   /**
	    * Enchants Entity's current equipments based on given DifficultyInstance
	    */
	   protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficulty) {
	   }
	   // field_233820_c_ = movement speed
	   // field_233818_a_ = health
	   // field_233823_f_ = attack damage
	   // field_233819_b_ = follow range
	   // field_233826_i_ = armor
	   @Nullable
	   public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
	      ILivingEntityData ilivingentitydata = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	      this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
	      this.setCombatTask();
	      return ilivingentitydata;
	   }

}
