package kelvin.mite.items;

import java.util.function.Supplier;

import javax.swing.UIDefaults.LazyValue;

import kelvin.mite.registry.ItemRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;

public enum SurvivalItemTier implements ToolMaterial {
    WOOD_PICKAXE(0, 10, 2.0F, 0.0F, 15, () -> {
        return Ingredient.fromTag(ItemTags.PLANKS);
    }, PickaxeItem.class),
    STONE_PICKAXE(1, 3, 1.0F, 1.0F, 5, () -> {
        return Ingredient.ofItems(Blocks.COBBLESTONE);
    }, PickaxeItem.class),
    IRON_PICKAXE(2, 50, 3.0F, 2.0F, 14, () -> {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }, PickaxeItem.class),
    COPPER_PICKAXE(2, 15, 0.5F, 1.5F, 14, () -> {
        return Ingredient.ofItems(Items.COPPER_INGOT);
    }, PickaxeItem.class),
    DIAMOND_PICKAXE(3, 150, 4.0F, 3.0F, 10, () -> {
        return Ingredient.ofItems(Items.DIAMOND);
    }, PickaxeItem.class),
    GOLD_PICKAXE(0, 15, 3.0F, 0.0F, 22, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, PickaxeItem.class),
    FLINT_PICKAXE(0, 8, 1.0F, 0.0F, 15, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, PickaxeItem.class),

    WOOD_AXE(0, 10, 2.0F, 0.0F, 15, () -> {
        return Ingredient.fromTag(ItemTags.PLANKS);
    }, AxeItem.class),
    STONE_AXE(1, 3, 1.0F, 1.0F, 5, () -> {
        return Ingredient.ofItems(Blocks.COBBLESTONE);
    }, AxeItem.class),
    COPPER_AXE(2, 15, 1.5F, 1.5F, 14, () -> {
        return Ingredient.ofItems(Items.COPPER_INGOT);
    }, AxeItem.class),
    IRON_AXE(2, 50, 3.0F, 2.0F, 14, () -> {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }, AxeItem.class),
    DIAMOND_AXE(3, 150, 4.0F, 3.0F, 10, () -> {
        return Ingredient.ofItems(Items.DIAMOND);
    }, AxeItem.class),
    GOLD_AXE(0, 15, 3.0F, 0.0F, 22, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, AxeItem.class),
    FLINT_AXE(0, 8, 1.0F, 0.0F, 15, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, AxeItem.class),

    WOOD_HOE(0, 10, 2.0F, 0.0F, 15, () -> {
        return Ingredient.fromTag(ItemTags.PLANKS);
    }, HoeItem.class),
    STONE_HOE(1, 3, 1.0F, 1.0F, 5, () -> {
        return Ingredient.ofItems(Blocks.COBBLESTONE);
    }, HoeItem.class),
    COPPER_HOE(2, 15, 1.5F, 1.5F, 14, () -> {
        return Ingredient.ofItems(Items.COPPER_INGOT);
    }, HoeItem.class),
    IRON_HOE(2, 50, 3.0F, 2.0F, 14, () -> {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }, HoeItem.class),
    DIAMOND_HOE(3, 150, 4.0F, 3.0F, 10, () -> {
        return Ingredient.ofItems(Items.DIAMOND);
    }, HoeItem.class),
    GOLD_HOE(0, 15, 3.0F, 0.0F, 22, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, HoeItem.class),
    FLINT_HOE(0, 8, 1.0F, 0.0F, 15, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, HoeItem.class),

    WOOD_SWORD(0, 10, 2.0F, 0.0F, 15, () -> {
        return Ingredient.fromTag(ItemTags.PLANKS);
    }, SwordItem.class),
    STONE_SWORD(1, 3, 1.0F, 1.0F, 5, () -> {
        return Ingredient.ofItems(Blocks.COBBLESTONE);
    }, SwordItem.class),
    COPPER_SWORD(2, 30, 1.5F, 1.5F, 14, () -> {
        return Ingredient.ofItems(Items.COPPER_INGOT);
    }, SwordItem.class),
    IRON_SWORD(2, 50, 3.0F, 2.0F, 14, () -> {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }, SwordItem.class),
    DIAMOND_SWORD(3, 150, 4.0F, 3.0F, 10, () -> {
        return Ingredient.ofItems(Items.DIAMOND);
    }, SwordItem.class),
    GOLD_SWORD(0, 15, 3.0F, 0.0F, 22, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, SwordItem.class),
    FLINT_SWORD(0, 8, 1.0F, 0.0F, 15, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, SwordItem.class),
    WOOD_HATCHET(0, 10, 2.0F, 0.0F, 15, () -> {
        return Ingredient.fromTag(ItemTags.PLANKS);
    }, HatchetItem.class),
    STONE_HATCHET(1, 3, 1.0F, 1.0F, 5, () -> {
        return Ingredient.ofItems(Blocks.COBBLESTONE);
    }, HatchetItem.class),
    COPPER_HATCHET(2, 15, 1.5F, 1.5F, 14, () -> {
        return Ingredient.ofItems(Items.COPPER_INGOT);
    }, HatchetItem.class),
    IRON_HATCHET(2, 50, 3.0F, 2.0F, 14, () -> {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }, HatchetItem.class),
    DIAMOND_HATCHET(3, 150, 4.0F, 3.0F, 10, () -> {
        return Ingredient.ofItems(Items.DIAMOND);
    }, HatchetItem.class),
    GOLD_HATCHET(0, 15, 3.0F, 0.0F, 22, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, HatchetItem.class),
    FLINT_HATCHET(0, 8, 1.0F, 0.0F, 15, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, HatchetItem.class),
    WOOD_SHORTSWORD(0, 10, 2.0F, 0.0F, 15, () -> {
        return Ingredient.fromTag(ItemTags.PLANKS);
    }, KnifeItem.class),
    STONE_SHORTSWORD(1, 3, 1.0F, 1.0F, 5, () -> {
        return Ingredient.ofItems(Blocks.COBBLESTONE);
    }, KnifeItem.class),
    COPPER_SHORTSWORD(2, 20, 1.5F, 1.5F, 14, () -> {
        return Ingredient.ofItems(Items.COPPER_INGOT);
    }, KnifeItem.class),
    IRON_SHORTSWORD(2, 50, 3.0F, 2.0F, 14, () -> {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }, KnifeItem.class),
    DIAMOND_SHORTSWORD(3, 150, 4.0F, 3.0F, 10, () -> {
        return Ingredient.ofItems(Items.DIAMOND);
    }, KnifeItem.class),
    GOLD_SHORTSWORD(0, 15, 3.0F, 0.0F, 22, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, KnifeItem.class),
    FLINT_SHORTSWORD(0, 8, 1.0F, 0.0F, 15, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, KnifeItem.class),
    WOOD_SHOVEL(0, 8, 2.0F, 0.0F, 15, () -> {
        return Ingredient.fromTag(ItemTags.PLANKS);
    }, ShovelItem.class),
    STONE_SHOVEL(1, 3, 1.0F, 1.0F, 5, () -> {
        return Ingredient.ofItems(Blocks.COBBLESTONE);
    }, ShovelItem.class),
    COPPER_SHOVEL(2, 30, 1.5F, 1.5F, 14, () -> {
        return Ingredient.ofItems(Items.COPPER_INGOT);
    }, ShovelItem.class),
    IRON_SHOVEL(2, 50, 3.0F, 2.0F, 14, () -> {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }, ShovelItem.class),
    DIAMOND_SHOVEL(3, 150, 4.0F, 3.0F, 10, () -> {
        return Ingredient.ofItems(Items.DIAMOND);
    }, ShovelItem.class),
    GOLD_SHOVEL(0, 15, 3.0F, 0.0F, 22, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, ShovelItem.class),
    FLINT_SHOVEL(0, 20, 1.0F, 0.0F, 15, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, ShovelItem.class);

    public static final SurvivalItemTier[] WOOD = {WOOD_AXE, WOOD_PICKAXE, WOOD_SHOVEL, WOOD_HOE, WOOD_SWORD, WOOD_HATCHET, WOOD_SHORTSWORD};
    public static final SurvivalItemTier[] FLINT = {FLINT_AXE, FLINT_PICKAXE, FLINT_SHOVEL, FLINT_HOE, FLINT_SWORD, FLINT_HATCHET, FLINT_SHORTSWORD};
    public static final SurvivalItemTier[] IRON = {IRON_AXE, IRON_PICKAXE, IRON_SHOVEL, IRON_HOE, IRON_SWORD, IRON_HATCHET, IRON_SHORTSWORD};
    public static final SurvivalItemTier[] STONE = {STONE_AXE, STONE_PICKAXE, STONE_SHOVEL, STONE_HOE, STONE_SWORD, STONE_HATCHET, STONE_SHORTSWORD};
    public static final SurvivalItemTier[] GOLD = {GOLD_AXE, GOLD_PICKAXE, GOLD_SHOVEL, GOLD_HOE, GOLD_SWORD, GOLD_HATCHET, GOLD_SHORTSWORD};
    public static final SurvivalItemTier[] DIAMOND = {DIAMOND_AXE, DIAMOND_PICKAXE, DIAMOND_SHOVEL, DIAMOND_HOE, DIAMOND_SWORD, DIAMOND_HATCHET, DIAMOND_SHORTSWORD};
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Ingredient repairMaterial;
    private final Class<? extends Item> itemClass;

    SurvivalItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repair, Class<? extends Item> itemClass) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        if (efficiencyIn <= 2.0) efficiencyIn = 2.0f;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = repair.get();
        this.itemClass = itemClass;
    }

    public int getDurability() {
        int use = 0;
        double mul = 1;
        if (itemClass == HatchetItem.class) {
            mul = 3.0 / 8.0;
        }
        if (itemClass == KnifeItem.class) {
            mul = 4.0 / 8.0;
        }
        if (itemClass == PickaxeItem.class) {
            mul = 7.0 / 8.0;
        }
        if (itemClass == SwordItem.class) {
            mul = 9.0 / 8.0;
        }
        if (itemClass == HoeItem.class) {
            mul = 6.0 / 8.0;
        }
        if (itemClass == ShovelItem.class) {
            mul = 12.0 / 8.0;
        }
        return (int) (this.maxUses * mul) + use;
    }

    public float getMiningSpeedMultiplier() {
        return this.efficiency;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.harvestLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return this.repairMaterial;
    }


}

