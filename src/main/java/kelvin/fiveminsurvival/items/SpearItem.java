package kelvin.fiveminsurvival.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import com.google.common.collect.Multimap;

import kelvin.fiveminsurvival.entity.SpearEntity;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpearItem extends Item {
	private double attackDamage;
	
	private Enchantment[] enchantments;
	
	   public SpearItem(Item.Properties builder, double attackDamage) {
		      super(builder);
		      //this.addPropertyOverride(new ResourceLocation("throwing"), (p_210315_0_, p_210315_1_, p_210315_2_) -> p_210315_2_ != null && p_210315_2_.isHandActive() && p_210315_2_.getActiveItemStack() == p_210315_0_ ? 1.0F : 0.0F);
		      this.attackDamage = attackDamage;
		      enchantments = new Enchantment[] {
		    		  Enchantments.LOYALTY, Enchantments.LOOTING, Enchantments.SHARPNESS, Enchantments.KNOCKBACK,
		    		  Enchantments.FLAME, Enchantments.INFINITY, Enchantments.IMPALING, Enchantments.MENDING,
		    		  Enchantments.MULTISHOT, Enchantments.PIERCING, Enchantments.UNBREAKING
		      };
		   }
	   		
	  	   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
			   int impaling = EnchantmentHelper.getEnchantmentLevel(Enchantments.IMPALING, stack);
			   int piercing = EnchantmentHelper.getEnchantmentLevel(Enchantments.PIERCING, stack);
			   int infinity = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack);
			   int mending = EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, stack);
			   int sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);

			   if (impaling > 0 && enchantment == Enchantments.PIERCING ||
					   piercing > 0 && enchantment == Enchantments.IMPALING ||
					   infinity > 0 && enchantment == Enchantments.MENDING ||
					   mending > 0 && enchantment == Enchantments.INFINITY) {
				   return false;
			   }
			   
			   if (impaling > 0 && enchantment == Enchantments.SHARPNESS || sharpness > 0 && enchantment == Enchantments.IMPALING) {
				   return false;
			   }
			   
	  		   for (int i = 0; i < enchantments.length; i++) {
	  			   if (enchantment == enchantments[i]) return true;
	  		   }
	  		   return false;
	  	   }

		   public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		      return !player.isCreative();
		   }

		   
		   
		   public UseAction getUseAction(ItemStack stack) {
		      return UseAction.SPEAR;
		   }

		   
		   
		   public int getUseDuration(ItemStack stack) {
		      return 72000;
		   }
		   
		   
		   public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
			   
			   float max_charge = 15;
			   
			   int piercing = EnchantmentHelper.getEnchantmentLevel(Enchantments.PIERCING, stack);
			   int infinity = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack);
			   int impaling = EnchantmentHelper.getEnchantmentLevel(Enchantments.IMPALING, stack);
			   int multishot = EnchantmentHelper.getEnchantmentLevel(Enchantments.MULTISHOT, stack);

		      if (entityLiving instanceof PlayerEntity) {
		         PlayerEntity playerentity = (PlayerEntity)entityLiving;
		         int i = this.getUseDuration(stack) - timeLeft;
		         if (i >= 5) {
	               if (!worldIn.isRemote) {
	                    int shots = multishot * 2 + 1;

	                    stack.damageItem(shots, playerentity, (player) -> player.sendBreakAnimation(entityLiving.getActiveHand()));
	                    for (int shot = 0; shot < shots; shot++) {
	                    	double deg = 15 * (shot - (shots / 2.0)) + 15 * 0.5f;
	                    	SpearEntity tridententity = new SpearEntity(worldIn, playerentity, stack);
		                     tridententity.setEnchantmentEffectsFromEntity(playerentity, 1.0f);
		                     tridententity.setPierceLevel((byte)piercing);
		                     tridententity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw + (float)deg, 0.0F, 2.5F * 0.7F * Math.min(1, i / max_charge), 5.0F);
		                     tridententity.setDamage((this.attackDamage + impaling * 2.5f) * Math.min(1.0f, i / max_charge));

		                     if (playerentity.abilities.isCreativeMode) {
		                        tridententity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
		                     } else {
		                    	 tridententity.pickupStatus = shot == (shots / 2) ? AbstractArrowEntity.PickupStatus.ALLOWED : AbstractArrowEntity.PickupStatus.DISALLOWED;
		                     }
		                     
		                     worldIn.addEntity(tridententity);
		                     if (shot == 0)
		                     worldIn.playMovingSound(playerentity, tridententity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
		                     if (!playerentity.abilities.isCreativeMode && infinity <= 0) {
		                        playerentity.inventory.deleteStack(stack);
		                     }
	                    }
	                    
	                    
	                     
	               }

	               playerentity.addStat(Stats.ITEM_USED.get(this));

		         }
		      }
		   }

		   
		   
		   public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		      ItemStack itemstack = playerIn.getHeldItem(handIn);
		      if (itemstack.getDamage() >= itemstack.getMaxDamage()) {
		         return new ActionResult<>(ActionResultType.FAIL, itemstack);
		      } else if (EnchantmentHelper.getRiptideModifier(itemstack) > 0 && !playerIn.isWet()) {
		         return new ActionResult<>(ActionResultType.FAIL, itemstack);
		      } else {
		         playerIn.setActiveHand(handIn);
		         return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
		      }
		   }

		   
		   public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		      stack.damageItem(1, attacker, (p_220048_0_) -> p_220048_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND));
		      return true;
		   }

		  
		   public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		      if ((double)state.getBlockHardness(worldIn, pos) != 0.0D) {
		         stack.damageItem(2, entityLiving, (p_220046_0_) -> p_220046_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND));
		      }

		      return true;
		   }

		   
		   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
//		      Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);
		      
		      Builder<Attribute, AttributeModifier> lvt_2_1_ = ImmutableMultimap.builder();
				lvt_2_1_.put(Attributes.ATTACK_DAMAGE,
						new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", attackDamage, Operation.ADDITION));
				lvt_2_1_.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier",
						-2.9F, Operation.ADDITION));

		      return lvt_2_1_.build();
		   }

		   
		   public int getItemEnchantability() {
		      return 1;
		   }
		}