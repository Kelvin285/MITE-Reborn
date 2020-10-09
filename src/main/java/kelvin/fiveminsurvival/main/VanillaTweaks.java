package kelvin.fiveminsurvival.main;

import java.lang.reflect.Field;

import kelvin.fiveminsurvival.blocks.MITECraftingTableBlock;
import kelvin.fiveminsurvival.items.SurvivalItemTier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.SoupItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class VanillaTweaks {

    public static void blocks() {
        try {
            changeHardnessAndResistance(Blocks.GRASS, 0.08f);
            changeHardnessAndResistance(Blocks.TALL_GRASS, 0.08f);
            changeHardnessAndResistance(Blocks.DEAD_BUSH, 0.08f);
            changeHardnessAndResistance(Blocks.SUGAR_CANE, 0.25f);
            changeHardnessAndResistance(Blocks.SEAGRASS, 0.08f);
            changeHardnessAndResistance(Blocks.TALL_SEAGRASS, 0.08f);
            changeHardnessAndResistance(Blocks.KELP_PLANT, 0.08f);
            changeHardnessAndResistance(Blocks.KELP, 0.08f);
            changeHardnessAndResistance(Blocks.SWEET_BERRY_BUSH, 0.3f);
            changeHardnessAndResistance(Blocks.OBSIDIAN, 0.5f);
            changeMaterial(Blocks.OBSIDIAN, Material.EARTH);
            changeMaterial(Blocks.FURNACE, Material.EARTH);
            changeHardnessAndResistance(Blocks.FURNACE, 0.2f);
            changeHardnessAndResistance(Blocks.CRAFTING_TABLE, 0.2f);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void changeHardnessAndResistance(Block block, float i) throws Exception {
		Field PROPERTIES = ObfuscationReflectionHelper.findField(AbstractBlock.class, "field_235684_aB_"); //blockHardness
		Properties properties = (Properties) PROPERTIES.get(block);
		properties.hardnessAndResistance(i, i);
    }

    private static void changeMaterial(Block block, Material mat) throws Exception {
        Field MATERIAL = ObfuscationReflectionHelper.findField(AbstractBlock.Properties.class, "field_200953_a"); //material
        Field PROPERTIES = ObfuscationReflectionHelper.findField(AbstractBlock.class, "field_235684_aB_"); //blockHardness
		Properties properties = (Properties) PROPERTIES.get(block);
        MATERIAL.set(properties, mat);
    }

    public static void setToolTiers() {
        setItemTiers(new Item[] {Items.WOODEN_AXE, Items.WOODEN_PICKAXE, Items.WOODEN_SHOVEL, Items.WOODEN_HOE, Items.WOODEN_SWORD}, ItemTier.WOOD, SurvivalItemTier.WOOD);
        setItemTiers(new Item[] {Items.IRON_AXE, Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_HOE, Items.IRON_SWORD}, ItemTier.IRON, SurvivalItemTier.IRON);
        setItemTiers(new Item[] {Items.STONE_AXE, Items.STONE_PICKAXE, Items.STONE_SHOVEL, Items.STONE_HOE, Items.STONE_SWORD}, ItemTier.STONE, SurvivalItemTier.STONE);
        setItemTiers(new Item[] {Items.GOLDEN_AXE, Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_HOE, Items.GOLDEN_SWORD}, ItemTier.GOLD, SurvivalItemTier.GOLD);
        setItemTiers(new Item[] {Items.DIAMOND_AXE, Items.DIAMOND_PICKAXE, Items.DIAMOND_SHOVEL, Items.DIAMOND_HOE, Items.DIAMOND_SWORD}, ItemTier.DIAMOND, SurvivalItemTier.DIAMOND);

    }

    private static void setItemTiers(Item[] item, ItemTier original, SurvivalItemTier[] tiers) {
        for (int i = 0; i < item.length; i++) {
            setItemTier(item[i], original, tiers[i]);
        }
    }

    private static void setItemTier(Item item,ItemTier original, SurvivalItemTier tier) {
        Class<ToolItem> c = ToolItem.class;
        if (!(item instanceof ToolItem)) return;
        try {
            if (!(item instanceof HoeItem) && !(item instanceof SwordItem)) {
                Field f = ObfuscationReflectionHelper.findField(c, "field_77864_a"); //efficiency
                f.set(item, tier.getEfficiency());

                Field f2 = ObfuscationReflectionHelper.findField(c, "field_77865_bY"); //attackDamage
                f2.set(item, (float)f2.get(item) - original.getAttackDamage() + tier.getAttackDamage());
            }

            Class<Item> cl = Item.class;
            Field f = ObfuscationReflectionHelper.findField(cl, "field_77699_b"); //maxDamage
            f.set(item, tier.getMaxUses());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void fixStackSizes() {

        try {

//    		makeFoodsEdible();

            Field f = ObfuscationReflectionHelper.findField(Item.class, "field_77699_b");

            f.set(Items.SHIELD, 75);
            f.set(Items.TRIDENT, 100);

            Field maxStack = ObfuscationReflectionHelper.findField(Item.class, "field_77777_bU"); //maxStackSize

            for (Item item : ForgeRegistries.ITEMS) {
                if (item == null) {
                    System.out.println("null");
                    continue;
                }
                maxStack.set(item, 16);
                if (item instanceof SoupItem) {
                    maxStack.set(item, 4);
                }
                if (item.isFood()) {
                    maxStack.set(item, 16);
                }

                if (item instanceof BlockItem) {
                    maxStack.set(item, 4);
                    Material mat = ((BlockItem)item).getBlock().getDefaultState().getMaterial();
                    if (mat == Material.WOOD || mat == Material.CLAY || mat == Material.CAKE || mat == Material.CARPET || mat == Material.CORAL || mat == Material.DRAGON_EGG || mat == Material.PLANTS || mat == Material.ORGANIC || mat == Material.SNOW || mat == Material.TNT || mat == Material.WEB) {
                        maxStack.set(item, 8);
                    }
                }
                if (item instanceof BlockNamedItem) {
                    maxStack.set(item, 64);
                }
                if (item == Items.TORCH) {
                    maxStack.set(item, 16);
                }
                if (item == Items.REDSTONE_TORCH) {
                    maxStack.set(item, 16);
                }
                if (item == Items.REDSTONE) {
                    maxStack.set(item, 16);
                }
                if (item == Items.POWERED_RAIL || item == Items.DETECTOR_RAIL) {
                    maxStack.set(item, 8);
                }
                if (item == Items.TALL_GRASS || item == Items.GRASS || item == Items.FERN || item == Items.DEAD_BUSH)
                    maxStack.set(item, 32);
                if (item instanceof BlockItem) {
                    BlockItem block = (BlockItem)item;
                    if (block.getBlock().getDefaultState().getMaterial() == Material.WOOL) {
                        maxStack.set(item, 8);
                    }
                    if (block.getBlock().getDefaultState().getMaterial() == Material.PLANTS) {
                        maxStack.set(item, 32);
                    }
                    if (block.getBlock() instanceof SlabBlock) {
                        maxStack.set(item, 8);
                    }
                    if (block.getBlock() instanceof MITECraftingTableBlock) {
                        maxStack.set(item, 1);
                    }
                    if (block.getBlock() instanceof PressurePlateBlock) {
                        maxStack.set(item, 8);
                    }
                    if (block.getBlock() instanceof WeightedPressurePlateBlock) {
                        maxStack.set(item, 8);
                    }
                    if (block.getBlock() == Blocks.SNOW) {
                        maxStack.set(item, 32);
                    }
                    if (block.getBlock() instanceof FenceBlock) {
                        maxStack.set(item, 8);
                    }
                    if (block.getBlock() instanceof PaneBlock) {
                        maxStack.set(item, 16);
                    }
                    if (block.getBlock() instanceof WallBlock) {
                        maxStack.set(item, 8);
                    }
                    if (block.getBlock() instanceof AnvilBlock) {
                        maxStack.set(item, 1);
                    }
                    if (block.getBlock() instanceof CarpetBlock) {
                        maxStack.set(item, 8);
                    }
                    if (block.getBlock() instanceof DoorBlock) {
                        maxStack.set(item, 1);
                    }
                    if (block.getBlock() instanceof StandingSignBlock) {
                        maxStack.set(item, 16);
                    }
                    if (block.getBlock() instanceof WallSignBlock) {
                        maxStack.set(item, 16);
                    }

                }
                if (item == Items.FURNACE || item == Items.BLAST_FURNACE || item == Items.SMOKER
                        || item == Items.CAMPFIRE) {
                    maxStack.set(item, 1);
                }
                if (item == Items.LADDER || item == Items.RAIL
                        || item == Items.ACTIVATOR_RAIL) {
                    maxStack.set(item, 8);
                }
                if (item == Items.PUMPKIN) maxStack.set(item, 8);
                if (item == Items.MELON) maxStack.set(item, 8);
                if (item == Items.CARVED_PUMPKIN) maxStack.set(item, 8);
                if (item == Items.JACK_O_LANTERN) maxStack.set(item, 8);
                if (item == Items.VINE) maxStack.set(item, 8);
                if (item == Items.LILY_PAD) maxStack.set(item, 32);
                if (item == Items.SWEET_BERRIES) maxStack.set(item, 8);
                if (item == Items.APPLE) maxStack.set(item, 16);
                if (item == Items.COAL) maxStack.set(item, 16);
                if (item == Items.CHARCOAL) maxStack.set(item, 16);
                if (item == Items.DIAMOND) maxStack.set(item, 32);
                if (item == Items.IRON_INGOT) maxStack.set(item, 8);
                if (item == Items.GOLD_INGOT) maxStack.set(item, 8);
                if (item == Items.STICK) maxStack.set(item, 32);
                if (item == Items.BOWL) maxStack.set(item, 16);
                if (item == Items.MUSHROOM_STEW) maxStack.set(item, 4);
                if (item == Items.STRING) maxStack.set(item, 16);
                if (item == Items.GUNPOWDER) maxStack.set(item, 16);
                if (item == Items.WHEAT) maxStack.set(item, 16);
                if (item == Items.BREAD) maxStack.set(item, 16);
                if (item == Items.FLINT) maxStack.set(item, 16);
                if (item == Items.PORKCHOP) maxStack.set(item, 16);
                if (item == Items.COOKED_PORKCHOP) maxStack.set(item, 16);
                if (item == Items.PAINTING) maxStack.set(item, 16);
                if (item == Items.GOLDEN_APPLE) maxStack.set(item, 16);
                if (item == Items.ENCHANTED_GOLDEN_APPLE) maxStack.set(item, 16);
                if (item == Items.BUCKET) maxStack.set(item, 8);
                if (item == Items.WATER_BUCKET) maxStack.set(item, 1);
                if (item == Items.LAVA_BUCKET) maxStack.set(item, 1);
                if (item == Items.MILK_BUCKET) maxStack.set(item, 1);
                if (item == Items.MINECART) maxStack.set(item, 1);
                if (item == Items.SADDLE) maxStack.set(item, 1);
                if (item == Items.REDSTONE) maxStack.set(item, 16);
                if (item == Items.SNOWBALL) maxStack.set(item, 16);
                if (item == Items.CLAY_BALL) maxStack.set(item, 16);
                if (item == Items.SUGAR_CANE) maxStack.set(item, 16);
                if (item == Items.PAPER) maxStack.set(item, 64);
                if (item == Items.BOOK) maxStack.set(item, 16);
                if (item == Items.EGG) maxStack.set(item, 16);
                if (item == Items.COMPASS) maxStack.set(item, 16);
                if (item == Items.CLOCK) maxStack.set(item, 16);
                if (item == Items.COD) maxStack.set(item, 16);
                if (item == Items.COOKED_COD) maxStack.set(item, 16);
                if (item == Items.CAKE) maxStack.set(item, 8);
                if (item == Items.REPEATER) maxStack.set(item, 16);
                if (item == Items.MAP) maxStack.set(item, 16);
                if (item == Items.ENDER_PEARL) maxStack.set(item, 16);
                if (item == Items.BLAZE_ROD) maxStack.set(item, 16);
                if (item == Items.GHAST_TEAR) maxStack.set(item, 16);
                if (item == Items.GOLD_NUGGET) maxStack.set(item, 64);
                if (item == Items.IRON_NUGGET) maxStack.set(item, 64);
                if (item == Items.NETHER_WART) maxStack.set(item, 64);
                if (item == Items.GLASS_BOTTLE) maxStack.set(item, 8);
                if (item == Items.EXPERIENCE_BOTTLE) maxStack.set(item, 32);
                if (item == Items.WRITABLE_BOOK) maxStack.set(item, 1);
                if (item == Items.MAP) maxStack.set(item, 16);
                if (item == Items.PUMPKIN_PIE) maxStack.set(item, 8);
                if (item == Items.BRICK) maxStack.set(item, 8);
                if (item == Items.NETHER_BRICK) maxStack.set(item, 8);
                if (item == Items.IRON_INGOT) maxStack.set(item, 8);
                if (item == Items.GOLD_INGOT) maxStack.set(item, 8);
                if (item == Items.ARROW) maxStack.set(item, 32);

            }
        }catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
