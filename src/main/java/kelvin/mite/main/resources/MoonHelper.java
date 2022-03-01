package kelvin.mite.main.resources;

import kelvin.mite.main.Mite;

public class MoonHelper {
    public static boolean IsHarvestMoon(long time) {
        return !IsBloodMoon(time) && !IsBlueMoon(time) && (time / Mite.ticks_per_day) % 56 == 0 && time / Mite.ticks_per_day > 0;
    }

    public static boolean IsBloodMoon(long time) {
        return !IsBlueMoon(time) && (time / Mite.ticks_per_day) % 24 == 0 && time / Mite.ticks_per_day > 0;
    }

    public static boolean IsBlueMoon(long time) {

        return (time / Mite.ticks_per_day) % 128 == 0 && time / Mite.ticks_per_day > 0;
    }
}
