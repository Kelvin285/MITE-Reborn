package kelvin.fiveminsurvival.main;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = FiveMinSurvival.MODID)
public class RecipeRemover {

<<<<<<< HEAD
    public static final List<Item> vanillaRecipesToRemove = Arrays.asList(Items.FURNACE, Items.CRAFTING_TABLE, Items.DIAMOND_AXE, Items.WOODEN_SWORD,
    		Items.WOODEN_PICKAXE, Items.WOODEN_AXE, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_SHOVEL, Items.DIAMOND_SWORD,
    		Items.DIAMOND_HOE, Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS, Items.STONE_PICKAXE, Items.STONE_AXE,
    		Items.STONE_HOE, Items.SANDSTONE, Items.RED_SANDSTONE, Items.COBBLESTONE, Items.CAKE, Items.BREAD, Items.COOKIE, Items.FLINT_AND_STEEL,
    		Items.SADDLE, Items.BRICK, Items.SNOW_BLOCK, Items.FISHING_ROD, Items.BRICKS, Items.SMOOTH_STONE, Items.ARROW, Items.COBBLESTONE_WALL,
    		Items.STONE_BRICKS, Items.TERRACOTTA, Items.NETHER_BRICKS, Items.MUSHROOM_STEW, Items.MELON_SEEDS, Items.COMPASS, Items.CLOCK, Items.GOLDEN_APPLE,
    		Items.SADDLE, Items.CAKE, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.COBBLESTONE_WALL);
=======
    public static final List<Item> vanillaRecipesToRemove = Arrays.asList(Items.FURNACE, Items.CRAFTING_TABLE, Items.DIAMOND_AXE, Items.WOODEN_SWORD);
>>>>>>> 2eba99c0d696ed96f3b32c8e0a922901b317922d

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Field RECIPES = ObfuscationReflectionHelper.findField(RecipeManager.class, "field_199522_d" /* recipes */);

    private static RecipeManager recipeManager;

    /**
     * Removes recipes from the server's recipe manager when it starts up.
     *
     * @param event The server starting event
     */
    @SubscribeEvent
    public static void removeRecipes(final FMLServerStartedEvent event) {
        recipeManager = event.getServer().getRecipeManager();
        vanillaRecipesToRemove.forEach(RecipeRemover::removeVanilla);
    }

    private static void removeVanilla(Item item) {
        removeRecipes(vanillaFor(item));
    }

    private static Predicate<IRecipe<?>> vanillaFor(Item item) {
        return (IRecipe<?> recipe) -> recipe.getId().getNamespace().equals("minecraft") && recipe.getRecipeOutput().getItem().equals(item);
    }

    /**
     * Removes all crafting recipes with an output item contained in the specified tag.
     *
     * @param tag           The tag
     */
    private static void removeRecipes(final Tag<Item> tag) {
        final int recipesRemoved = removeRecipes(recipe -> {
            final ItemStack recipeOutput = recipe.getRecipeOutput();
            return !recipeOutput.isEmpty() && recipeOutput.getItem().isIn(tag);
        });

        LOGGER.info("Removed {} recipe(s) for tag {}", recipesRemoved, tag.getId());
    }

    /**
     * Remove all crafting recipes that are instances of the specified class.
     * <p>
     * Test for this thread:
     * https://www.minecraftforge.net/forum/topic/33420-removing-vanilla-recipes/
     *
     * @param recipeClass   The recipe class
     */
    private static void removeRecipes(final Class<? extends IRecipe<?>> recipeClass) {
        final int recipesRemoved = removeRecipes(recipeClass::isInstance);

        LOGGER.info("Removed {} recipe(s) for class {}", recipesRemoved, recipeClass);
    }

    /**
     * Remove all crafting recipes that match the specified predicate.
     *
     * @param predicate     The predicate
     * @return The number of recipes removed
     */
    private static int removeRecipes(final Predicate<IRecipe<?>> predicate) {
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> existingRecipes;
        try {
            @SuppressWarnings("unchecked")
            final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipesMap = (Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>>) RECIPES.get(recipeManager);
            existingRecipes = recipesMap;
        } catch (final IllegalAccessException e) {
            throw new RuntimeException("Couldn't get recipes map while removing recipes", e);
        }

        final Object2IntMap<IRecipeType<?>> removedCounts = new Object2IntOpenHashMap<>();
        final ImmutableMap.Builder<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> newRecipes = ImmutableMap.builder();

        // For each recipe type, create a new map that doesn't contain the recipes to be removed
        existingRecipes.forEach((recipeType, existingRecipesForType) -> {
            //noinspection UnstableApiUsage
            final ImmutableMap<ResourceLocation, IRecipe<?>> newRecipesForType = existingRecipesForType.entrySet()
                    .stream()
                    .filter(entry -> !predicate.test(entry.getValue()))
                    .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));

            removedCounts.put(recipeType, existingRecipesForType.size() - newRecipesForType.size());
            newRecipes.put(recipeType, newRecipesForType);
        });

        final int removedCount = removedCounts.values().stream().reduce(0, Integer::sum);

        try {
            RECIPES.set(recipeManager, newRecipes.build());
        } catch (final IllegalAccessException e) {
            throw new RuntimeException("Couldn't replace recipes map while removing recipes", e);
        }

        return removedCount;
    }
}