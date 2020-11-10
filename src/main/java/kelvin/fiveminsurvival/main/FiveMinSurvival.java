package kelvin.fiveminsurvival.main;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kelvin.fiveminsurvival.commands.CustomTimeCommand;
import kelvin.fiveminsurvival.entity.model.ModelRegistry;
import kelvin.fiveminsurvival.game.OverlayEvents;
import kelvin.fiveminsurvival.game.crops.CropTypes;
import kelvin.fiveminsurvival.game.food.FoodNutrients;
import kelvin.fiveminsurvival.game.world.CampfireState;
import kelvin.fiveminsurvival.game.world.PlantState;
import kelvin.fiveminsurvival.game.world.WorldStateHolder;
import kelvin.fiveminsurvival.init.BlockRegistry;
import kelvin.fiveminsurvival.init.ContainerRegistry;
import kelvin.fiveminsurvival.init.EntityRegistry;
import kelvin.fiveminsurvival.init.ItemRegistry;
import kelvin.fiveminsurvival.init.VanillaOverrides;
import kelvin.fiveminsurvival.main.crafting.CraftingIngredients;
import kelvin.fiveminsurvival.main.loot.BlockLootTables;
import kelvin.fiveminsurvival.main.loot.LootTable;
import kelvin.fiveminsurvival.main.network.NetworkHandler;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GrassColors;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FiveMinSurvival.MODID)
public class FiveMinSurvival
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    
    public static boolean DEBUG = true;

    public static final String MODID = "fiveminsurvival";
    
    public FiveMinSurvival() {
    	try {
    		Field ITEM = Registry.class.getDeclaredField(FiveMinSurvival.DEBUG ? "ITEM" : "field_212630_s");
    	} catch (Exception e) {
    		DEBUG = false;
    	}
    	
    	IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
    	
    	EntityRegistry.ENTITIES.register(modBus);
    	BlockRegistry.BLOCKS.register(modBus);
    	ItemRegistry.ITEMS.register(modBus);
    	VanillaOverrides.BLOCKS.register(modBus);
    	VanillaOverrides.ITEMS.register(modBus);
    	VanillaOverrides.FEATURES.register(modBus);
    	ContainerRegistry.CONTAINERS.register(modBus);
    	
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        
    	//BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    	//ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		//VanillaOverrides.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		//VanillaOverrides.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		//ContainerRegistry.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ContainerRegistry.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
		//EntityRegistry.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
		
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
//        MinecraftForge.EVENT_BUS.register(GameEvents.class);
        
//        MinecraftForge.EVENT_BUS.register(WorldFeatures.class);

        MinecraftForge.EVENT_BUS.addListener(FiveMinSurvival::onWorldTick);
        MinecraftForge.EVENT_BUS.addListener(FiveMinSurvival::onBlockUpdate);
        MinecraftForge.EVENT_BUS.addListener(FiveMinSurvival::onCropGrowth);

        MinecraftForge.EVENT_BUS.addListener(FiveMinSurvival::blockBreak);
        //MinecraftForge.EVENT_BUS.addListener(FiveMinSurvival::harvestDrops);

        NetworkHandler.register();
    }
    
    public static void onWorldTick(WorldTickEvent event)
	{
    	World world = event.world;
    	if (!world.isRemote) {
    		WorldStateHolder holder = WorldStateHolder.get(world);
    		holder.tick();
    	}
	}
    
    @SubscribeEvent
	public static void blockBreak(BreakEvent e) {
    	e.setExpToDrop(0);

    	LootTable loot_table = BlockLootTables.LOOT_TABLES.get(e.getState().getBlock());
    	
    	if (e.getPlayer() != null)
    	if (loot_table != null && !e.getPlayer().isCreative()) {
    		
    		boolean silk_touch = false;
    		ItemStack stack = e.getPlayer().getHeldItemMainhand();
    		if (stack != null) {
    			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
    			for(Enchantment enchantment : enchantments.keySet()) {
    				if (enchantment == Enchantments.SILK_TOUCH) {
    					silk_touch = true;
    					break;
    				}
    			}
    		}
    		
			ItemEntity item = new ItemEntity((World)e.getWorld(), e.getPos().getX() + 0.5f, e.getPos().getY() + 0.5f, e.getPos().getZ() + 0.5f, loot_table.GetDrop(e.getPos(), silk_touch, e.getWorld().getRandom()));
			e.getWorld().addEntity(item);
    					
			if (loot_table.overridesVanillaLoot) {
				e.getWorld().setBlockState(e.getPos(), Blocks.AIR.getDefaultState(), 0);
			}
    		
    	}
   	}
	
    
    @SubscribeEvent
    public static void onCropGrowth(BlockEvent.CropGrowEvent e) {
    	BlockPos pos = e.getPos();
		BlockState state = e.getState();
    	if (state.getBlock() instanceof CropsBlock) {
			WorldStateHolder stateHolder = WorldStateHolder.get(e.getWorld());
			long dayPlanted = -1;
			PlantState p = null;
			for (int i = 0; i < stateHolder.crops.size(); i++) {
				p = stateHolder.crops.get(i);
				if (p.pos.equals(pos)) {
					dayPlanted = p.dayPlanted;
					break;
				}
				p = null;
			}
			if (dayPlanted != -1) {
				long day = ((World)e.getWorld()).getDayTime() / 24000L;
				if (p != null) {
					CropTypes.tickForCrop(((World)e.getWorld()), pos, state, day, p);
				}
			} else {
				p = new PlantState();
				p.dayPlanted = ((World)e.getWorld()).getDayTime() / 24000L;
				p.pos = pos;
				stateHolder.crops.add(p);
			}
		}
    }
    
    @SubscribeEvent
	public static void onBlockUpdate(NeighborNotifyEvent e) {
		BlockPos pos = e.getPos();
		BlockState state = e.getState();
		for (Direction d : e.getNotifiedSides()) {
			BlockPos pos2 = pos.offset(d);
			BlockState state2 = e.getWorld().getBlockState(pos2);
			if (state2.getBlock() == Blocks.DIRT) {
				if (!e.getWorld().getBlockState(pos2.down()).getMaterial().blocksMovement()) {
					FallingBlockEntity fallingblockentity = new FallingBlockEntity(((World)e.getWorld()), (double)pos2.getX() + 0.5D, pos2.getY(), (double)pos2.getZ() + 0.5D, state2);
		            e.getWorld().addEntity(fallingblockentity);
				}
			}
		}
		if (state.getBlock() == Blocks.DIRT) {
			if (!e.getWorld().getBlockState(pos.down()).getMaterial().blocksMovement()) {
				FallingBlockEntity fallingblockentity = new FallingBlockEntity(((World)e.getWorld()), (double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, state);
	            e.getWorld().addEntity(fallingblockentity);
			}
		}
//		if (state.getBlock() instanceof CampfireBlock)
//		if (!e.getWorld().isRemote()) {
//			WorldStateHolder stateHolder = WorldStateHolder.get(e.getWorld());
//			int fuel = 0;
//			for (int i = 0; i < stateHolder.campfires.size(); i++) {
//				CampfireState s = stateHolder.campfires.get(i);
//				if (s.pos.equals(pos)) {
//					fuel = s.fuel;
//					break;
//				}
//			}
//			if (fuel <= 0)
//				((World)e.getWorld()).setBlockState(pos, state.with(CampfireBlock.LIT, Boolean.FALSE));
//		}
		
		if (state.getBlock() instanceof CropsBlock) {
			WorldStateHolder stateHolder = WorldStateHolder.get(e.getWorld());
			long dayPlanted = -1;
			PlantState p = null;
			for (int i = 0; i < stateHolder.crops.size(); i++) {
				p = stateHolder.crops.get(i);
				if (p.pos.equals(pos)) {
					dayPlanted = p.dayPlanted;
					break;
				}
				p = null;
			}
			if (dayPlanted != -1) {
				long day = ((World)e.getWorld()).getDayTime() / 24000L;
				if (p != null) {
					CropTypes.tickForCrop(((World)e.getWorld()), pos, state, day, p);
				}
			} else {
				p = new PlantState();
				p.dayPlanted = ((World)e.getWorld()).getDayTime() / 24000L;
				p.pos = pos;
				stateHolder.crops.add(p);
			}
		}
		if (state.getBlock() instanceof AirBlock) {
			WorldStateHolder stateHolder = WorldStateHolder.get(e.getWorld());
			for (PlantState p : stateHolder.crops) {
				if (p.pos.equals(pos)) {
					stateHolder.crops.remove(p);
					break;
				}
			}
		}
	}

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
		FoodNutrients.init();
		CraftingIngredients.init();
		CropTypes.registerCropTypes();
		
		
		event.enqueueWork(
				() -> {
					EntityRegistry.RegisterEntityAttributes();
					VanillaTweaks.blocks();
					VanillaTweaks.setToolTiers();
					VanillaTweaks.fixStackSizes();
					BlockLootTables.RegisterLootTables();
				}
				);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    	
    	MinecraftForge.EVENT_BUS.register(ModelRegistry.class);
        MinecraftForge.EVENT_BUS.register(OverlayEvents.class);
        
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
		event.getMinecraftSupplier().get().getBlockColors().register(
        		(p_210225_0_, p_210225_1_, p_210225_2_, p_210225_3_) ->
						p_210225_1_ != null && p_210225_2_ != null
								? BiomeColors.getGrassColor(p_210225_1_, p_210225_2_)
								: GrassColors.get(0.5D, 1.0D),
				BlockRegistry.FLAX.get());
        
		RenderTypeLookup.setRenderLayer(BlockRegistry.FLAX.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockRegistry.CAMPFIRE_LOW.get(), RenderType.getCutout());
		
    	Minecraft.getInstance().gameSettings.fovScaleEffect = 0;
    	
    	Minecraft.getInstance().gameSettings.autoJump = false;
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
    	CustomTimeCommand.register(event.getServer().getCommandManager().getDispatcher());
    }

}

