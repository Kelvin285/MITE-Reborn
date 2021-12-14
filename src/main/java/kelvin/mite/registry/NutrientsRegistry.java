package kelvin.mite.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;

public class NutrientsRegistry {
    public static class Nutrients {
        public int protein, dairy, vegetables, fruits, grains;
        public Nutrients(int fruits, int vegetables, int grains, int dairy, int protein) {
            this.fruits = fruits;
            this.vegetables = vegetables;
            this.grains = grains;
            this.dairy = dairy;
            this.protein = protein;
        }

    }

    public static HashMap<Item, Nutrients> nutrients = new HashMap<Item, Nutrients>();

    public static Nutrients DEFAULT = new Nutrients(0, 0, 0, 0, 0);

    public static Nutrients GetNutrientsFor(Item item) {
        return nutrients.getOrDefault(item, DEFAULT);
    }

    public static void RegisterNutrients() {
        Register(Items.SWEET_BERRIES, 1, 0, 0, 0, 0);
        Register(ItemRegistry.ORANGE, 2, 0, 0, 0, 0);
        Register(ItemRegistry.BANANA, 2, 0, 0, 0, 0);
        Register(ItemRegistry.BANANA_SPLIT, 1, 1, 0, 1, 0);
        Register(ItemRegistry.BACON, 0, 0, 0, 0, 1);
        Register(ItemRegistry.BEEF_STEW, 0, 3, 0, 0, 4);
        Register(ItemRegistry.BLUEBERRIES, 1, 0, 0, 0, 0);
        Register(ItemRegistry.BLUEBERRY_MUFFIN, 1, 0, 1, 0, 0);
        Register(ItemRegistry.BROWNIE, 0, 0, 1, 0, 0);
        Register(ItemRegistry.CEREAL, 0, 0, 1, 1, 0);
        Register(ItemRegistry.CHEESE, 0, 0, 0, 2, 0);
        Register(ItemRegistry.CHERRIES, 1, 0, 0, 0, 0);
        Register(ItemRegistry.CHICKEN_SOUP, 1, 2, 0, 0, 2);
        Register(ItemRegistry.CHOCOLATE_ICE_CREAM, 0, 1, 0, 1, 0);
        Register(ItemRegistry.COOKED_BACON, 0, 0, 0 ,0, 2);
        Register(ItemRegistry.COOKED_EGG, 0 ,0, 0, 0, 2);
        Register(ItemRegistry.COOKED_WORM, 0, 0, 0, 0, 1);
        Register(ItemRegistry.COOKIE_DOUGH, 0, 0, 2, 0, 0);
        Register(ItemRegistry.CREAM_OF_MUSHROOM_SOUP, 0, 2, 0, 2, 2);
        Register(ItemRegistry.CREAM_OF_VEGETABLE_SOUP, 0, 4, 2, 2, 1);
        Register(ItemRegistry.DOUGH, 0, 0, 1, 0, 0);
        Register(ItemRegistry.FLAX_SEEDS, 0, 1, 1, 0, 1);
        Register(ItemRegistry.FLOUR, 0, 0, 1, 0, 0);
        Register(ItemRegistry.ICE_CREAM, 0, 0, 0, 2, 0);
        Register(ItemRegistry.LEMON, 1, 0, 0, 0, 0);
        Register(ItemRegistry.MASHED_POTATOES, 0, 2, 2, 3, 1);
        Register(ItemRegistry.MILK_BOWL, 0, 0, 0, 2, 0);
        Register(ItemRegistry.ONION, 0, 1, 0, 0, 0);
        Register(ItemRegistry.PORRIDGE, 1, 1, 1, 0, 0);
        Register(ItemRegistry.RAW_WORM, 0, 0, 0, 0, 1);
        Register(ItemRegistry.SALAD, 0, 2, 1, 0, 0);
        Register(ItemRegistry.PUMPKIN_SOUP, 2, 2, 0, 0, 1);
        Register(ItemRegistry.SORBET, 3, 0, 0, 0, 0);
        Register(ItemRegistry.TURKEY_COOKED, 0, 0, 0, 0, 3);
        Register(ItemRegistry.TURKEY_RAW, 0, 0, 0, 0, 1);
        Register(ItemRegistry.VEGETABLE_SOUP, 0, 4, 2, 0, 4);
        Register(Items.BAKED_POTATO, 0, 3, 4, 0, 3);
        Register(Items.BEEF, 0, 0, 0, 0, 2);
        Register(Items.APPLE, 2, 0, 0, 0, 0);
        Register(Items.BEETROOT, 0, 2, 0, 0, 0);
        Register(Items.BEETROOT_SOUP, 0, 4, 0, 0, 0);
        Register(Items.BREAD, 0, 1, 4, 0, 0);
        Register(Items.MILK_BUCKET, 0, 0, 0, 4, 0);
        Register(Items.PUMPKIN_PIE, 0, 4, 2, 3, 2);
        Register(Items.GOLDEN_APPLE, 4, 0, 0, 0, 0);
        Register(Items.ENCHANTED_GOLDEN_APPLE, 4, 0, 0, 0, 0);
        Register(Items.COD, 0, 0, 0, 0, 1);
        Register(Items.COOKED_COD, 0, 0, 0, 0, 3);
        Register(Items.CHICKEN, 0, 0, 0 ,0, 1);
        Register(Items.COOKED_CHICKEN, 0, 0, 0, 0, 3);
        Register(Items.COOKED_BEEF, 0, 0, 0, 0, 4);
        Register(Items.MUTTON, 0, 0, 0, 0, 2);
        Register(Items.COOKED_MUTTON, 0, 0, 0, 0, 4);
        Register(Items.PORKCHOP, 0, 0, 0, 0, 2);
        Register(Items.COOKED_PORKCHOP, 0, 0, 0, 0, 4);
        Register(Items.RABBIT, 0, 0, 0, 0, 1);
        Register(Items.COOKED_RABBIT, 0, 0, 0, 0, 3);
        Register(Items.SALMON, 0, 0, 0, 0, 1);
        Register(Items.COOKED_SALMON, 0, 0, 0, 0, 3);
        Register(Items.PUMPKIN_SEEDS, 0, 1, 1, 0, 1);
        Register(Items.MELON_SEEDS, 1, 0, 0, 0, 0);
        Register(Items.MELON, 2, 0, 0, 0, 0);
        Register(Items.GLISTERING_MELON_SLICE, 4, 0, 0, 0, 0);
        Register(Items.HONEY_BOTTLE, 0, 0, 0, 0, 2);
        Register(Items.TROPICAL_FISH, 0, 0, 0, 0, 1);
        Register(Items.CARROT, 0, 1, 0, 0, 0);
        Register(Items.POTATO, 0, 2, 2, 0, 1);
        Register(Items.GOLDEN_CARROT, 0, 4, 0, 0, 1);
        Register(Items.CAKE, 0, 0, 3, 4, 0);
        Register(Items.COOKIE, 0, 0, 3, 0, 2);
        Register(Items.GLOW_BERRIES, 3, 0, 0, 0, 0);
        Register(Items.KELP, 0, 1, 0, 0, 0);
        Register(Items.MUSHROOM_STEW, 0, 2, 0, 0, 2);
        Register(Items.BROWN_MUSHROOM, 0, 1, 0, 0, 1);
        Register(Items.RED_MUSHROOM, 0, 1, 0, 0, 1);
        Register(Items.SPIDER_EYE, 0, 0, 0, 0, 1);
        Register(Items.DRIED_KELP, 0, 1, 0, 0, 0);
        Register(Items.HONEYCOMB, 0 ,0, 0, 0, 2);
        Register(Items.EGG, 0, 0, 0, 0, 1);
        Register(Items.WHEAT_SEEDS, 0, 1, 1, 0, 0);
    }

    public static void Register(Item item, int fruits, int vegetables, int grains, int dairy, int protein) {
        nutrients.put(item, new Nutrients(fruits, vegetables, grains, dairy, protein));
    }

}
