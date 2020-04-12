package kelvin.fiveminsurvival.survival;

import kelvin.fiveminsurvival.init.BlockRegistry;
import kelvin.fiveminsurvival.entity.AnimalWatcherEntity;
import kelvin.fiveminsurvival.entity.EntityAttackSquid;
import kelvin.fiveminsurvival.init.EntityRegistry;
import kelvin.fiveminsurvival.entity.NewSkeletonEntity;
import kelvin.fiveminsurvival.entity.goal.EnhancedPanicGoal;
import kelvin.fiveminsurvival.entity.goal.RunFromPlayerGoal;
import kelvin.fiveminsurvival.items.HatchetItem;
import kelvin.fiveminsurvival.init.ItemRegistry;
import kelvin.fiveminsurvival.items.ShortswordItem;
import kelvin.fiveminsurvival.main.resources.Resources;
import kelvin.fiveminsurvival.survival.food.CustomFoodStats;
import kelvin.fiveminsurvival.survival.food.Nutrients;
import kelvin.fiveminsurvival.survival.world.CampfireState;
import kelvin.fiveminsurvival.survival.world.WorldStateHolder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker.MusicType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombiePigmanEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TieredItem;
import net.minecraft.item.ToolItem;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.IRegistryDelegate;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class SurvivalEvents {
	
	@SubscribeEvent
	public static void handleClickBlock(RightClickBlock event) {
		if (event.getItemStack() != null) {
			if (event.getItemStack().getItem() instanceof DyeItem) {
				DyeItem dye = (DyeItem)event.getItemStack().getItem();
				DyeColor color = dye.getDyeColor();
				BlockState state = event.getWorld().getBlockState(event.getPos());
				Block block = state.getBlock();
				Block red = BlockRegistry.RED_CAKE;
				Block green = BlockRegistry.GREEN_CAKE;
				Block blue = BlockRegistry.BLUE_CAKE;
				Block cyan = BlockRegistry.CYAN_CAKE;
				Block brown = BlockRegistry.BROWN_CAKE;
				Block magenta = BlockRegistry.MAGENTA_CAKE;
				Block orange = BlockRegistry.ORANGE_CAKE;
				Block gray = BlockRegistry.GRAY_CAKE;
				Block light_blue = BlockRegistry.LIGHT_BLUE_CAKE;
				Block light_gray = BlockRegistry.LIGHT_GRAY_CAKE;
				Block black = BlockRegistry.BLACK_CAKE;
				Block lime = BlockRegistry.LIME_CAKE;
				Block yellow = BlockRegistry.YELLOW_CAKE;
				Block white = Blocks.CAKE;
				Block purple = BlockRegistry.PURPLE_CAKE;
				Block pink = BlockRegistry.PINK_CAKE;
				
				Block set = white;
				if (block == Blocks.CAKE || block == BlockRegistry.RED_CAKE || block == BlockRegistry.GREEN_CAKE || block == BlockRegistry.BLUE_CAKE || block == BlockRegistry.YELLOW_CAKE ||
						block == BlockRegistry.BROWN_CAKE || block == BlockRegistry.CYAN_CAKE || block == BlockRegistry.LIGHT_BLUE_CAKE || block == BlockRegistry.PURPLE_CAKE ||
						block == BlockRegistry.MAGENTA_CAKE || block == BlockRegistry.BLACK_CAKE || block == BlockRegistry.PINK_CAKE ||
						block == BlockRegistry.ORANGE_CAKE || block == BlockRegistry.GRAY_CAKE || block == BlockRegistry.LIGHT_GRAY_CAKE || block == BlockRegistry.LIME_CAKE) {
					if (color == DyeColor.RED) set = red;
					if (color == DyeColor.GREEN) set = green;
					if (color == DyeColor.BLUE) set = blue;
					if (color == DyeColor.CYAN) set = cyan;
					if (color == DyeColor.BROWN) set = brown;
					if (color == DyeColor.MAGENTA) set = magenta;
					if (color == DyeColor.ORANGE) set = orange;
					if (color == DyeColor.GRAY) set = gray;
					if (color == DyeColor.LIGHT_BLUE) set = light_blue;
					if (color == DyeColor.LIGHT_GRAY) set = light_gray;
					if (color == DyeColor.BLACK) set = black;
					if (color == DyeColor.LIME) set = lime;
					if (color == DyeColor.YELLOW) set = yellow;
					if (color == DyeColor.PURPLE) set = purple;
					if (color == DyeColor.PINK) set = pink;
				}
				event.getWorld().setBlockState(event.getPos(), set.getDefaultState().with(CakeBlock.BITES, state.get(CakeBlock.BITES).intValue()));
			}
		}
	}
	
	@SubscribeEvent
	public static void handleItemEvent(ItemEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof ItemEntity) {
			ItemEntity item = (ItemEntity)entity;
			if (item.getItem().getItem() == Items.FEATHER) {
				
			}
		}
	}
	
	@SubscribeEvent
	public static void handleExperienceDrop(LivingExperienceDropEvent event) {
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntity();
			if (player.experienceLevel <= 0) {
				Nutrients nutrients = WorldStateHolder.get(player.world).nutrients.get(player.getUniqueID().toString());
				
				if (nutrients != null) {
					System.out.println("noot: " + nutrients.negativeLevel);
					
					if (nutrients.negativeLevel < 30) {
						
						nutrients.negativeLevel++;
						if (player.getFoodStats() instanceof CustomFoodStats) {
							((CustomFoodStats)player.getFoodStats()).nutrients.negativeLevel = nutrients.negativeLevel;
						}
					}
				}
				WorldStateHolder.get(player.world).markDirty();
			}
			return;
		}
		event.setDroppedExperience(5);
		if (event.getEntity() instanceof SlimeEntity) event.setDroppedExperience(4);
		if (event.getEntity() instanceof GhastEntity) event.setDroppedExperience(10);
		if (event.getEntity() instanceof ZombiePigmanEntity) event.setDroppedExperience(15);
		if (event.getEntity() instanceof CaveSpiderEntity) event.setDroppedExperience(10);
		if (event.getEntity() instanceof BlazeEntity) event.setDroppedExperience(20);
		if (event.getEntity() instanceof MagmaCubeEntity) event.setDroppedExperience(12);
		if (event.getEntity() instanceof EnderDragonEntity) event.setDroppedExperience(100);
		if (event.getEntity() instanceof WitherEntity) event.setDroppedExperience(50);
		if (event.getEntity() instanceof WitchEntity) event.setDroppedExperience(20);
		if (event.getEntity() instanceof IronGolemEntity) event.setDroppedExperience(20);
//		if (event.getEntity() instanceof GhoulEntity) event.setDroppedExperience(10);
//		if (event.getEntity() instanceof WightEntity) event.setDroppedExperience(10);
//		if (event.getEntity() instanceof InvisibleStalkerEntity) event.setDroppedExperience(10);
//		if (event.getEntity() instanceof DemonSpiderEntity) event.setDroppedExperience(15);
//		if (event.getEntity() instanceof HellhoundEntity) event.setDroppedExperience(15);
//		if (event.getEntity() instanceof DireWolfEntity) event.setDroppedExperience(10);
//		if (event.getEntity() instanceof InfernalCreeperEntity) event.setDroppedExperience(15);
//		if (event.getEntity() instanceof ShadowEntity) event.setDroppedExperience(10);
//		if (event.getEntity() instanceof FireElementalEntity) event.setDroppedExperience(15);
//		if (event.getEntity() instanceof BlackWidowEntity) event.setDroppedExperience(8);
//		if (event.getEntity() instanceof RevenantEntity) event.setDroppedExperience(15);
//		if (event.getEntity() instanceof EarthElementalEntity) event.setDroppedExperience(15);
//		if (event.getEntity() instanceof JellyEntity) event.setDroppedExperience(8);
//		if (event.getEntity() instanceof BlobEntity) event.setDroppedExperience(12);
//		if (event.getEntity() instanceof OozeEntity) event.setDroppedExperience(8);
//		if (event.getEntity() instanceof PuddingEntity) event.setDroppedExperience(20);
//		if (event.getEntity() instanceof SeaSludgeEntity) event.setDroppedExperience(20);
//		if (event.getEntity() instanceof GiantVampireBatEntity) event.setDroppedExperience(10);
//		if (event.getEntity() instanceof LongdeadEntity) event.setDroppedExperience(15);
//		if (event.getEntity() instanceof LongdeadGuardianEntity) event.setDroppedExperience(25);
//		if (event.getEntity() instanceof NightwingEntity) event.setDroppedExperience(10);
//		if (event.getEntity() instanceof NetherspawnEntity) event.setDroppedExperience(10);
//		if (event.getEntity() instanceof CopperspineEntity) event.setDroppedExperience(10);
//		if (event.getEntity() instanceof HoarySilverfishEntity) event.setDroppedExperience(10);
//		if (event.getEntity() instanceof EarthElementalEntity) event.setDroppedExperience(15);
//		if (event.getEntity() instanceof BoneLordEntity) event.setDroppedExperience(15);
//		if (event.getEntity() instanceof AncientBoneLordEntity) event.setDroppedExperience(30);
//		if (event.getEntity() instanceof PhaseSpiderEntity) event.setDroppedExperience(10);
//		if (event.getEntity() instanceof BoneFishEntity) event.setDroppedExperience(10);
	}
	
	@SubscribeEvent
	public static void handleDeathEvent(LivingDeathEvent event) {
		
	}
	
	@SubscribeEvent
	public static void handleDamageEvent(LivingDamageEvent event) {
		if (event.getEntity() instanceof ZombieEntity || event.getEntity() instanceof WolfEntity ||
				event.getEntity() instanceof AbstractSkeletonEntity || event.getEntity() instanceof PhantomEntity ||
				event.getEntity() instanceof ZombieHorseEntity || event.getEntity() instanceof SkeletonHorseEntity) {
			if (event.getSource().getImmediateSource() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)event.getSource().getImmediateSource();
				ItemStack stack = player.getHeldItemMainhand();
				if (stack != null) {
					Item item = stack.getItem();
					if (item == ItemRegistry.SILVER_AXE.get() || //item == ItemRegistry.SILVER_BATTLE_AXE.get() ||
							item == ItemRegistry.SILVER_HATCHET.get() || item == ItemRegistry.SILVER_HOE.get() ||
							item == ItemRegistry.SILVER_KNIFE.get() || //item == ItemRegistry.SILVER_MATTOCK.get() ||
							item == ItemRegistry.SILVER_PICKAXE.get() || //item == ItemRegistry.SILVER_SHEARS.get() ||
							item == ItemRegistry.SILVER_SWORD.get() /*|| item == ItemRegistry.SILVER_WAR_HAMMER.get()*/) {
						event.setAmount(event.getAmount() * 2.0f);
					}
				}
			}
		}
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntity();
			Nutrients nutrients = WorldStateHolder.get(event.getEntity().getEntityWorld()).nutrients.get(player.getUniqueID().toString());
			if (nutrients != null) {
				double damageResistance = 1.0 - nutrients.getWeaknessResistance();
				event.setAmount(event.getAmount() + event.getAmount() * (float)damageResistance);
				nutrients.happiness -= event.getAmount() * 5;
			}
		}
	}
	
	@SubscribeEvent
	public static void livingHealEvent(LivingHealEvent event) {
		if (event.getAmount() == 1) {
			event.setCanceled(true);
		}
	}
	
	public static int tick = 0;
	@SubscribeEvent
	public static void interactEvent(PlayerInteractEvent.RightClickBlock event) {
		if (event.getItemStack() == null) return;
		
		BlockPos pos = event.getPos();
		BlockState state = event.getWorld().getBlockState(pos);
		World world = event.getWorld();
		
		WorldStateHolder stateHolder = WorldStateHolder.get(world);
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntity();
			if (event.getPlayer().getHeldItem(Hand.MAIN_HAND) != null)
				if (event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() != null)
		if (player.getCooldownTracker().getCooldown(player.getHeldItem(Hand.MAIN_HAND).getItem(), 0) <= 0)
			if (state.getBlock() instanceof CampfireBlock && !world.isRemote()) {
				
				ItemStack stack = event.getPlayer().getHeldItem(Hand.MAIN_HAND);
				if (stack != null) {
					Item item = stack.getItem();
					if (item != null) {
						Map<IRegistryDelegate<Item>, Integer> BURNS = null;
						try {
							Field VANILLA_BURNS = ObfuscationReflectionHelper.findField(ForgeHooks.class, "VANILLA_BURNS");
							Resources.makeFieldAccessible(VANILLA_BURNS);
							BURNS = (Map<IRegistryDelegate<Item>, Integer>) VANILLA_BURNS.get(null);
						} catch (Exception e) {
							e.printStackTrace();
						}
						int burnTime = stack.getBurnTime();
						if (BURNS != null) {
							burnTime = BURNS.getOrDefault(item.delegate, 0);
						}
						boolean canDestroy = true;
	//					if (item == Items.FLINT_AND_STEEL) {
	//						burnTime = 6000;
	//						canDestroy = false;
	//					}
						if (item == Items.CHARCOAL) {
							burnTime = 6000;
						}
						if (item == Items.COAL || item == Items.LAVA_BUCKET || item == Items.BLAZE_ROD) {
							burnTime = 0;
						}
						if (burnTime > 0) {
							CampfireState fire = null;
							for (int i = 0; i < stateHolder.campfires.size(); i++) {
								CampfireState s = stateHolder.campfires.get(i);
								if (s.pos.equals(pos)) {
									fire = s;
									stateHolder.campfires.remove(i);
									break;
								}
							}
							if (fire == null) {
								fire = new CampfireState();
								fire.pos = pos;
							}
							fire.fuel += burnTime * 10;
							if (fire.fuel > 60 * 20 * 10) {
								fire.fuel = 60 * 20 * 10;
							}
							stateHolder.campfires.add(fire);
							world.setBlockState(pos, state.with(CampfireBlock.LIT, Boolean.TRUE));
							
								player.getCooldownTracker().setCooldown(stack.getItem(), 10);
							
							if (canDestroy);
							stack.setCount(stack.getCount() - 1);
							
						}
					}
				}
			}
		}
		stateHolder.placeTick++;
		if (stateHolder.placeTick > 3) stateHolder.placeTick = 0;
		
		if (!(event.getItemStack().getItem() instanceof AxeItem)) return;
		
		if (pos != null) {
			boolean cutLog = false;
			if (world.getBlockState(pos).getBlock().getDefaultState() == Blocks.ACACIA_LOG.getDefaultState()) {
				world.setBlockState(pos, Blocks.STRIPPED_ACACIA_LOG.getDefaultState().with(LogBlock.AXIS, world.getBlockState(pos).get(LogBlock.AXIS)));
				cutLog = true;
			}
			if (world.getBlockState(pos).getBlock().getDefaultState() == Blocks.BIRCH_LOG.getDefaultState()) {
				world.setBlockState(pos, Blocks.STRIPPED_BIRCH_LOG.getDefaultState().with(LogBlock.AXIS, world.getBlockState(pos).get(LogBlock.AXIS)));
				cutLog = true;
			}
			if (world.getBlockState(pos).getBlock().getDefaultState() == Blocks.DARK_OAK_LOG.getDefaultState()) {
				world.setBlockState(pos, Blocks.STRIPPED_DARK_OAK_LOG.getDefaultState().with(LogBlock.AXIS, world.getBlockState(pos).get(LogBlock.AXIS)));
				cutLog = true;
			}
			if (world.getBlockState(pos).getBlock().getDefaultState() == Blocks.JUNGLE_LOG.getDefaultState()) {
				world.setBlockState(pos, Blocks.STRIPPED_JUNGLE_LOG.getDefaultState().with(LogBlock.AXIS, world.getBlockState(pos).get(LogBlock.AXIS)));
				cutLog = true;
			}
			if (world.getBlockState(pos).getBlock().getDefaultState() == Blocks.OAK_LOG.getDefaultState()) {
				world.setBlockState(pos, Blocks.STRIPPED_OAK_LOG.getDefaultState().with(LogBlock.AXIS, world.getBlockState(pos).get(LogBlock.AXIS)));
				cutLog = true;
			}
			if (world.getBlockState(pos).getBlock().getDefaultState() == Blocks.SPRUCE_LOG.getDefaultState()) {
				world.setBlockState(pos, Blocks.STRIPPED_SPRUCE_LOG.getDefaultState().with(LogBlock.AXIS, world.getBlockState(pos).get(LogBlock.AXIS)));
				cutLog = true;
			}
			if (cutLog) {
				world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemRegistry.STRIPPED_BARK.get(), new Random().nextInt(2) + 1)));
//				world.playSound(event.getPlayer(), pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}
	
	@SubscribeEvent
	public static void joinWorldEvent(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof AnimalEntity) {
			AnimalEntity animal = (AnimalEntity)event.getEntity();
			animal.goalSelector.addGoal(0, new EnhancedPanicGoal(animal, 2.0D));
			if (animal instanceof ChickenEntity) {
				animal.goalSelector.addGoal(0, new RunFromPlayerGoal(animal, 2.0D));
			}
		}
	}
	
	@SubscribeEvent
	public static void livingSpawnEvent(LivingSpawnEvent event) {
		
		if (!(event.getEntity() instanceof DrownedEntity))
		if (event.getEntity() instanceof ZombieEntity ||
				event.getEntity() instanceof SkeletonEntity) {
			if (event.getWorld().canBlockSeeSky(event.getEntity().getPosition()) ||
					event.getWorld().getBlockState(event.getEntity().getPosition().down()).getBlock() != Blocks.STONE) {
				event.getEntity().remove();
				event.setResult(Result.DENY);
			}
				
		}
		
		if (event instanceof LivingSpawnEvent.CheckSpawn) {
			if (event.getEntity().isAlive())
			{
				if (event.getEntity() instanceof CreeperEntity) {
					if (event.getWorld().getRandom().nextInt() <= 7) {
						event.setResult(Result.DENY);
					}
				}
				if (!event.getWorld().getWorld().isDaytime()) {
					if (event.getEntity().getType() == EntityRegistry.CREEPER.get()) {
						if (event.getWorld().getRandom().nextInt() <= 7) {
							event.setResult(Result.DENY);
						}
					}
				}
			}
		} else {
			if (event.getEntity().isAlive()) {
				if (event.getEntity() instanceof CreeperEntity) {
					if (event instanceof LivingSpawnEvent.SpecialSpawn) 
					if (event.getWorld().getRandom().nextInt() <= 7) {
						event.setResult(Result.DENY);
					}
				}
				if (!event.getWorld().getWorld().isDaytime()) {
					if (event.getEntity().getType() == EntityRegistry.CREEPER.get()) {
						if (event instanceof LivingSpawnEvent.SpecialSpawn) 
						if (event.getWorld().getRandom().nextInt() <= 7) {
							event.setResult(Result.DENY);
						}
					}
				}
			}
		}
		
		if (event.getEntity() instanceof PhantomEntity) {
			if (event.getWorld().getRandom().nextInt() <= 7) {
				event.getEntity().remove();
				event.setResult(Result.DENY);
			}
			else {
				PhantomEntity p = (PhantomEntity)event.getEntity();
				p.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2 + p.getPhantomSize());
			}
		}
		
		if (event.getEntity() instanceof ZombieEntity && !(event.getEntity() instanceof AnimalWatcherEntity)) {
			BlockPos pos = event.getEntity().getPosition();
			event.getEntity().remove();
			
			AnimalWatcherEntity zombie = EntityRegistry.ZOMBIE_ENTITY.get().create(event.getWorld().getWorld());
			zombie.setPosition(pos.getX(), pos.getY(), pos.getZ());
			event.getWorld().addEntity(zombie);
			event.setResult(Result.DENY);
		}
		
		if (event.getEntity() instanceof SquidEntity && !(event.getEntity() instanceof EntityAttackSquid)) {
			BlockPos pos = event.getEntity().getPosition();
			event.getEntity().remove();
			
			EntityAttackSquid entity = EntityRegistry.ATTACK_SQUID.get().create(event.getWorld().getWorld());
			entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
			event.getWorld().addEntity(entity);
			event.setResult(Result.DENY);
		}
		
		if (event.getEntity() instanceof ItemEntity) {
			ItemEntity i = (ItemEntity)event.getEntity();
			i.lifespan = 20 * 60 * 15;//15 minutes until items despawn.
		}
		
		
		if (event.getEntity() instanceof SkeletonEntity) {
			SkeletonEntity s = (SkeletonEntity)event.getEntity();
			
			if (s.ticksExisted < 100) {

				if (new Random().nextInt(25) == 0) {
					
					BlockPos pos = s.getPosition();
					s.remove();
					NewSkeletonEntity skeleton = EntityRegistry.SKELETON_ENTITY.get().create(event.getWorld().getWorld());
					skeleton.setPosition(pos.getX(), pos.getY(), pos.getZ());
					ItemStack club = new ItemStack(ItemRegistry.WOODEN_CLUB.get());
					club.setDamage(new Random().nextInt(5));
					skeleton.setItemStackToSlot(EquipmentSlotType.MAINHAND, club);
					skeleton.setDropChance(EquipmentSlotType.MAINHAND, 0.5f);
					skeleton.setCombatTask();
					
					skeleton.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0);
					skeleton.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0);
					skeleton.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
					
					event.getWorld().addEntity(skeleton);
				}
			}
			event.setResult(Result.DENY);
		}
		
		
		
	}
	
	
	@SubscribeEvent
    public static void livingUpdateEvent(LivingEvent.LivingJumpEvent event) {
		
		

		Entity entity = event.getEntity();
		
		if (entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntity();
			
			player.getFoodStats().addExhaustion(0.3F);
		}
		
		if (entity instanceof CowEntity || entity instanceof PigEntity || entity instanceof SheepEntity || entity instanceof HorseEntity) {
			Random rand = new Random();
			AnimalEntity animal = (AnimalEntity)entity;
			if (!entity.world.isRemote && entity.isAlive() && !animal.isChild() && rand.nextInt(6000) <= 10) {
				animal.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
		        animal.entityDropItem(ItemRegistry.MANURE.get());
		    }
		}
		
	}
	
    @SubscribeEvent
    public static void livingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
		Entity entity = event.getEntity();
		
		if (entity != null) {
			if (!entity.isSpectator())
			if (entity.world.getBlockState(entity.getPosition().down()).getBlock() instanceof FallingBlock) {
				if (entity.world instanceof ServerWorld)
				entity.world.getBlockState(entity.getPosition().down()).randomTick((ServerWorld)entity.world, entity.getPosition().down(), entity.world.rand);
			}
		}
		
		
		if (entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntity();
			
			if (player.world.isRemote) {
	    		if (player.world.getRandom().nextDouble() <= 0.001f)
				if (Minecraft.getInstance() != null) {
					Biome biome = player.world.getBiome(player.getPosition());
		    		if (biome == Biomes.OCEAN || biome == Biomes.BEACH || biome == Biomes.SNOWY_BEACH ||
		    				biome == Biomes.COLD_OCEAN || biome == Biomes.DEEP_COLD_OCEAN
		    				|| biome == Biomes.DEEP_FROZEN_OCEAN || biome == Biomes.DEEP_LUKEWARM_OCEAN ||
		    				biome == Biomes.DEEP_OCEAN || biome == Biomes.DEEP_WARM_OCEAN || biome == Biomes.FROZEN_OCEAN ||
		    				biome == Biomes.LUKEWARM_OCEAN || biome == Biomes.WARM_OCEAN) {
		    			if (!Minecraft.getInstance().getMusicTicker().isPlaying(MusicType.GAME) && !Minecraft.getInstance().getMusicTicker().isPlaying(MusicType.CREATIVE)) {
		    				if (!Minecraft.getInstance().getMusicTicker().isPlaying(MusicType.UNDER_WATER)) {
		    					Minecraft.getInstance().getMusicTicker().play(MusicType.UNDER_WATER);
		    				}
						}
					}
				}
	    		
			}
			
			if (player.experienceLevel > 0) {
				Nutrients nutrients = WorldStateHolder.get(player.world).nutrients.get(player.getUniqueID().toString());
				if (nutrients != null) {
					if (nutrients.negativeLevel > 0) {
						nutrients.negativeLevel--;
						player.addExperienceLevel(-1);
					}
				}
			}
    		if (!player.isAlive()) {
    			WorldStateHolder.get(player.getEntityWorld()).nutrients.get(player.getUniqueID().toString()).reset();
    		}
    		if (!(player.getFoodStats() instanceof CustomFoodStats))
        		try {
        			FoodStats stats = player.getFoodStats();
        			
        			CustomFoodStats customStats = new CustomFoodStats(player);
        			
        			
        			
        			
        			customStats.foodLevel = stats.getFoodLevel();
        			customStats.foodSaturationLevel = stats.getSaturationLevel();
        			Field foodTimer = ObfuscationReflectionHelper.findField(FoodStats.class, "field_75123_d"); //foodTimer
        			Resources.makeFieldAccessible(foodTimer);
        			customStats.foodTimer = (int)foodTimer.get(stats);
        			System.out.println(stats.getFoodLevel());
        			Field foodStats = ObfuscationReflectionHelper.findField(PlayerEntity.class, "field_71100_bB"); //foodStats
        			Resources.makeFieldAccessible(foodStats);
        			foodStats.set(player, customStats);
        			
        			
        		}catch (Exception e) {
        			e.printStackTrace();
        			System.exit(1);
        		}
    		else
    		try {
    			CustomFoodStats customStats = (CustomFoodStats)player.getFoodStats();
    			
    			if (customStats.nutrients == null) {
    				Nutrients n = new Nutrients(customStats);

        			String UUID = player.getUniqueID().toString();
        			System.out.println(UUID);
        			if (WorldStateHolder.get(entity.getEntityWorld()).nutrients.containsKey(UUID)) {
        				n = WorldStateHolder.get(entity.getEntityWorld()).nutrients.get(UUID);
        				n.foodStats = customStats;
        			} else {
        				WorldStateHolder.get(entity.world).nutrients.put(UUID, n);
        			}
        			
        			customStats.nutrients = n;
    			}
    			
    		}catch (Exception e) {
    			e.printStackTrace();
    		}
        		
    	}
    	
    	
    	if (entity instanceof PlayerEntity) {
//    		event.getEntity().stepHeight = 1.0f;
    		
    		PlayerEntity player = (PlayerEntity)event.getEntity();
    		
    		if (player.getActivePotionEffect(Effects.SLOWNESS) != null) {
    			if (player.getActivePotionEffect(Effects.SLOWNESS).getAmplifier() >= 2)
    			if (player.isInWaterOrBubbleColumn()) {
    				player.setMotion(player.getMotion().add(0, -0.1f, 0));
    			}
    		}
    		
    		double baseReach = 3.0;
    		
    		if (player.isCreative()) {
    			baseReach = 8.0;
    		}
    		
    		Nutrients nutrients = null;
    		if (player.getEntityWorld().isRemote())
    		if (player.getFoodStats() instanceof CustomFoodStats) {
    			
    			CustomFoodStats foodStats = (CustomFoodStats)player.getFoodStats();
    			
    			if (foodStats.nutrients != null) {
    				if (!player.isAlive()) {
        				foodStats.nutrients = new Nutrients(foodStats);
        				String UUID = player.getUniqueID().toString();
        				WorldStateHolder.get(entity.getEntityWorld()).nutrients.put(UUID, foodStats.nutrients);
        			}
    				nutrients = foodStats.nutrients;
    				nutrients.tick(player, foodStats);
    				
    				double speed = Math.max(0.05, nutrients.getSpeedModifier());
    				speed = 0;
					player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.11f + speed);
    				
    			}
    		}
    		
    		player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach);
    		if (player.getHeldItemMainhand() != null) {
    			Item item = player.getHeldItemMainhand().getItem();
    			if (item == Items.STICK || item == Items.BONE) {
    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.5);
    			}
    			if (item instanceof SwordItem) {
    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.75);
    			}
    			if (item instanceof ShortswordItem) {
    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.25);
    			}
    			if (item instanceof HatchetItem) {
    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.5);
    			}
    			if (item instanceof AxeItem) {
    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.75);
    			}
    			if (item instanceof PickaxeItem) {
    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.75);
    			}
    			if (item instanceof HoeItem) {
    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.75);
    			}
    			if (item instanceof ShearsItem) {
    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.5);
    			}
//    			if (item instanceof MattockItem) {
//    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.75);
//    			}
//    			if (item instanceof BattleAxeItem) {
//    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.75);
//    			}
//    			if (item instanceof ScytheItem) {
//    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 1.0);
//    			}
    			if (item == ItemRegistry.WOODEN_CUDGEL.get()) {
    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.25);
    			}
    			if (item == ItemRegistry.WOODEN_CLUB.get()) {
    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.5);
    			}
    			if (item == ItemRegistry.FLINT_KNIFE.get()) {
    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.25);
    			}
    			if (item instanceof HatchetItem) {
    				player.getAttribute(PlayerEntity.REACH_DISTANCE).setBaseValue(baseReach + 0.5);
    			}
    		}
    		if (player.isSwingInProgress) 
    		{
    			player.getFoodStats().addExhaustion(0.025F);
    		}
    		
    		
    		int i = 6 + (player.experienceLevel / 5) * 2;
    		
//    		if (player.getFoodStats() instanceof CustomFoodStats) {
//    			CustomFoodStats stats = (CustomFoodStats)player.getFoodStats();
//    			int negativeLevel = Resources.clientNutrients.negativeLevel;
//    			if (stats != null && stats.nutrients != null)
//    			if (stats.nutrients.negativeLevel > negativeLevel) negativeLevel = stats.nutrients.negativeLevel;
//    			if (negativeLevel >= 10) {
//    				i+= 2;
//    			}
//    			if (negativeLevel >= 20) {
//    				i+= 2;
//    			}
//    			if (negativeLevel >= 30) {
//    				i+= 2;
//    			}
//    		}
    		
    		if (i > 20) i = 20;
    		player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(i);
    		if (player.getHealth() > i) {
    			player.setHealth(i);
    		}
    		if (!player.getEntityWorld().isRemote) {
    			
    		}
    		
    	}
    }
    
    @SubscribeEvent
    public static void handleAttack(AttackEntityEvent event) {
    	if (event.getPlayer() != null) {
    		PlayerEntity player = event.getPlayer();
    		if (player.getHeldItemMainhand() != null) {
    			Item item = player.getHeldItemMainhand().getItem();
    			if (item == Items.STICK || item == Items.BONE) {
    				if (new Random().nextInt(100) == 0) {
    					player.getHeldItemMainhand().setCount(player.getHeldItemMainhand().getCount() - 1);
    				}
    			}
    			if (item == Items.STICK || item == Items.BONE || item instanceof ToolItem || item instanceof TieredItem) {
    				
    			} else {
    				Entity attacked = event.getTarget();
    				
    				
    				
    				if (attacked instanceof ZombieEntity || attacked instanceof CreeperEntity || attacked instanceof WolfEntity) {
    					if (new Random().nextInt(10) == 0) {
    						player.performHurtAnimation();
    						player.attackEntityFrom(DamageSource.causeMobDamage((LivingEntity)event.getTarget()), 1.0f);
    					}
    				}
    				if (attacked instanceof SlimeEntity) {
						player.performHurtAnimation();
						player.attackEntityFrom(DamageSource.causeMobDamage((LivingEntity)event.getTarget()), 2.0f);
    				}
    				if (attacked instanceof MagmaCubeEntity || attacked instanceof BlazeEntity) {
						player.performHurtAnimation();
						player.attackEntityFrom(DamageSource.causeMobDamage((LivingEntity)event.getTarget()), 3.0f);
						player.setFire(5);
    				}
    				if (attacked instanceof ZombiePigmanEntity) {
    					if (new Random().nextInt(10) == 0) {
							player.performHurtAnimation();
							player.attackEntityFrom(DamageSource.causeMobDamage((LivingEntity)event.getTarget()), 4.0f);
    					}
    				}
    			}
    		}
			player.getFoodStats().addExhaustion(0.1F);
			if (player.getFoodStats().getFoodLevel() <= 0 && player.getFoodStats().getSaturationLevel() <= 0) {
				event.setCanceled(true);
			}
    	}
    }
    
    @SubscribeEvent
	public static void handleMining(BreakSpeed event) {
		if (event.getPlayer() != null) {
			PlayerEntity player = event.getPlayer();
			BlockState state = event.getState();
			boolean set = false;
			if (state != null) {
				if (player.getFoodStats().getSaturationLevel() <= 0 && player.getFoodStats().getFoodLevel() <= 0) {
					
					if (state.getMaterial() == Material.PLANTS || state.getMaterial() == Material.TALL_PLANTS || state.getMaterial() == Material.OCEAN_PLANT) {
						event.setNewSpeed(0.5f + event.getPlayer().experienceLevel * 0.01f);
						set = true;
					}
					if (!set) {
						event.setNewSpeed(-1);
						set = true;
					}
				}
				if (!set)
				if (state.getMaterial() == Material.EARTH || state.getMaterial() == Material.SAND || state.getMaterial() == Material.LEAVES
						|| state.getMaterial() == Material.ORGANIC || state.getMaterial() == Material.CLAY || state.getBlock() == BlockRegistry.PEA_GRAVEL.get()) {
					event.setNewSpeed(event.getOriginalSpeed() * 0.05f + event.getPlayer().experienceLevel * 0.01f);
					set = true;
				}
			}
			
			if (event.getState() != null) {
				
				ItemStack held = player.getHeldItemMainhand();
				
				
				if (held != null) {
					if (held.getItem() != null) {
						Item item = held.getItem();
						
						if (state.getMaterial() == Material.ROCK || state.getMaterial() == Material.IRON) {
							if (item instanceof PickaxeItem) {
								event.setNewSpeed(1.5f * event.getOriginalSpeed() * 0.15f + event.getPlayer().experienceLevel * 0.01f);
								return;
							}
						}
						
						if (state.getBlock() instanceof LogBlock) {
							if (item instanceof AxeItem) {
								event.setNewSpeed(1.5f * event.getOriginalSpeed() * 0.15f + event.getPlayer().experienceLevel * 0.01f);
								return;
							}
						}
						
						if (state.getMaterial() == Material.EARTH || state.getMaterial() == Material.SAND || state.getMaterial() == Material.LEAVES
								|| state.getMaterial() == Material.ORGANIC || state.getMaterial() == Material.CLAY || state.getBlock() == BlockRegistry.PEA_GRAVEL.get()) {
							if (item instanceof ShovelItem) {
								event.setNewSpeed(2.0f * event.getOriginalSpeed() * 0.05f + event.getPlayer().experienceLevel * 0.01f);
							}
							return;
						}
						
					}
				}
				if (state.getBlock() == Blocks.CRAFTING_TABLE) {
					return;
				}
				
				
				
				
				if (state.getMaterial() == Material.GLASS || state.getBlock() == Blocks.CAMPFIRE) {
					event.setNewSpeed(1.5f * event.getOriginalSpeed() * 2.0f + event.getPlayer().experienceLevel * 0.01f);
					return;
				}
				if (state.getMaterial() == Material.ROCK || state.getMaterial() == Material.IRON || state.getBlock() instanceof LogBlock) {
					event.setNewSpeed(-1);
					return;
				}
				if (!set)
				event.setNewSpeed(event.getOriginalSpeed() * 0.15f + event.getPlayer().experienceLevel * 0.01f);
			}
			
		}
    }
}
