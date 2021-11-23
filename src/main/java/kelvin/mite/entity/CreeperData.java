package kelvin.mite.entity;

import net.minecraft.entity.EntityData;

public class CreeperData implements EntityData {
    public final boolean baby;

    public CreeperData(boolean baby) {
        this.baby = baby;
    }
}
