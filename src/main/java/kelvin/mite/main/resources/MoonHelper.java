package kelvin.mite.main.resources;

public class MoonHelper {
    public static boolean IsHarvestMoon(long time) {
        return !IsBloodMoon(time) && !IsBlueMoon(time) && (time / 24000) % 56 == 0 && time / 24000 > 0;
    }

    public static boolean IsBloodMoon(long time) {
        return !IsBlueMoon(time) && (time / 24000) % 24 == 0 && time / 24000 > 0;
    }

    public static boolean IsBlueMoon(long time) {

        return (time / 24000) % 128 == 0 && time / 24000 > 0;
    }
}
