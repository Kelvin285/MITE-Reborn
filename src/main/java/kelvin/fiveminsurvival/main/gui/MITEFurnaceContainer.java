package kelvin.fiveminsurvival.main.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

public class MITEFurnaceContainer extends FurnaceContainer {

    public static final int CLAY = 0, HARDENED_CLAY = 1, SANDSTONE = 1, STONE = 2, OBSIDIAN = 3, NETHERRACK = 4;

    public int HEAT_LEVEL = MITEFurnaceContainer.CLAY;
    public int CURRENT_HEAT_LEVEL = 0;

    public MITEFurnaceContainer(int p_i50082_1_, PlayerInventory p_i50082_2_) {
        super(p_i50082_1_, p_i50082_2_);
        System.out.println("3");
    }

    public MITEFurnaceContainer(int p_i50082_1_, PlayerInventory p_i50082_2_, int HEAT_LEVEL) {
        super(p_i50082_1_, p_i50082_2_);
        this.HEAT_LEVEL = HEAT_LEVEL;
        System.out.println(HEAT_LEVEL);
    }

    public MITEFurnaceContainer(int p_i50083_1_, PlayerInventory p_i50083_2_, IInventory p_i50083_3_,
                                IIntArray p_i50083_4_, int HEAT_LEVEL) {
        super(p_i50083_1_, p_i50083_2_, p_i50083_3_, p_i50083_4_);
        this.HEAT_LEVEL = HEAT_LEVEL;

    }

    protected int getHeatForItem(Item item) {
        if (item instanceof BlockItem) {
            return 1;
        }
        if (item == Items.COAL || item == Items.COAL_BLOCK) {
            return 2;
        }
        if (item == Items.LAVA_BUCKET) {
            return 3;
        }
        if (item == Items.BLAZE_ROD) {
            return 4;
        }
        if (item == Items.COAL_ORE || item == Items.DIAMOND_ORE || item == Items.EMERALD_ORE ||
                item == Items.GOLD_ORE || item == Items.IRON_ORE || item == Items.LAPIS_ORE ||
                item == Items.NETHER_QUARTZ_ORE || item == Items.REDSTONE_ORE) {
            return 2;
        }
        return 0;
    }

    protected boolean func_217057_a(ItemStack p_217057_1_) {

        if (p_217057_1_.getItem() != null) {
            this.CURRENT_HEAT_LEVEL = getHeatForItem(p_217057_1_.getItem());
            if (HEAT_LEVEL < CURRENT_HEAT_LEVEL) {
                return false;
            }
        }
        Field recipeType;
        try {
            recipeType = ObfuscationReflectionHelper.findField(AbstractFurnaceContainer.class, "field_214014_c");
            return this.world.getRecipeManager().getRecipe((IRecipeType) recipeType.get(this), new Inventory(p_217057_1_), this.world).isPresent();

        } catch (SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected boolean isFuel(ItemStack p_217058_1_) {
        if (HEAT_LEVEL < CURRENT_HEAT_LEVEL) {
            return false;
        }

        boolean canSupport = true;

        if (p_217058_1_.getItem() != null) {
            canSupport = getHeatForItem(p_217058_1_.getItem()) <= this.HEAT_LEVEL;
        }
        if (!canSupport) return false;
        return AbstractFurnaceTileEntity.isFuel(p_217058_1_);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookProgressionScaled() {
        Field field_217064_e = ObfuscationReflectionHelper.findField(AbstractFurnaceContainer.class, "field_217064_e");
		int i = 0;
		int j = 0;
		try {
			i = ((IIntArray) field_217064_e.get(this)).get(2);
			j = ((IIntArray) field_217064_e.get(this)).get(3);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled() {
        try {
            Field field_217064_e = ObfuscationReflectionHelper.findField(AbstractFurnaceContainer.class, "field_217064_e");
            int i = ((IIntArray) field_217064_e.get(this)).get(1);
            if (i == 0) {
                i = 200;
            }
            return ((IIntArray) field_217064_e.get(this)).get(0) * 13 / i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean func_217061_l() {

        try {
            Field field_217064_e = ObfuscationReflectionHelper.findField(AbstractFurnaceContainer.class, "field_217064_e");
            return ((IIntArray) field_217064_e.get(this)).get(0) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
