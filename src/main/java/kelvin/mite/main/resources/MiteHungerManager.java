package kelvin.mite.main.resources;

import net.minecraft.item.Item;

public interface MiteHungerManager {



    enum HungerCategory {
        FRUITS, VEGETABLES, DAIRY, PROTEIN, GRAIN
    }

    void eatFood(Item food);

    void addSaturation(HungerCategory category, int value);

    float getSaturation(HungerCategory category);
    void setSaturation(HungerCategory fruits, float fruit);
    float getMaxSaturation();

    void addExhaustion(HungerCategory category, float value);
}
