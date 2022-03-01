package kelvin.mite.main.resources;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.command.TimeCommand;

public class CropNutrients {

    public int potassium, nitrogen, phosphorus;
    public CropNutrients(int potassium, int nitrogen, int phosphorus) {
        this.potassium = potassium;
        this.nitrogen = nitrogen;
        this.phosphorus = phosphorus;
    }
    public static CropNutrients DEFAULT = new CropNutrients(1, 1, 1);

    public static CropNutrients WHEAT = new CropNutrients(1, 3, 2);
    public static CropNutrients POTATO = new CropNutrients(2, 2, 1);

    public static CropNutrients CARROT = new CropNutrients(2, 1, 2);
    public static CropNutrients PUMPKIN = new CropNutrients(2, 1, 2);

    public static CropNutrients BEETROOT = new CropNutrients(2, 2, 2);
    public static CropNutrients WATERMELON = new CropNutrients(1, 1, 1);

    public static CropNutrients GetNutrients(Block block) {
        if (block == Blocks.WHEAT) {
            return WHEAT;
        }
        else if (block == Blocks.POTATOES) {
            return POTATO;
        } else if (block == Blocks.PUMPKIN_STEM) {
            return PUMPKIN;
        } else if (block == Blocks.MELON_STEM) {
            return WATERMELON;
        } else if (block == Blocks.CARROTS) {
            return CARROT;
        } else if (block == Blocks.BEETROOTS) {
            return BEETROOT;
        }
        return CropNutrients.DEFAULT;
    }

}
