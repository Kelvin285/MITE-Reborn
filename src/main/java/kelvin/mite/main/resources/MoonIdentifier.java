package kelvin.mite.main.resources;

import kelvin.mite.main.Mite;
import net.minecraft.util.Identifier;

public class MoonIdentifier extends Identifier {
    private Identifier MOON_PHASES = new Identifier("textures/environment/moon_phases.png");
    private Identifier HARVEST_MOON_PHASES = new Identifier("textures/environment/harvest_moon_phases.png");
    private Identifier BLOOD_MOON_PHASES = new Identifier("textures/environment/blood_moon_phases.png");
    private Identifier BLUE_MOON_PHASES = new Identifier("textures/environment/blue_moon_phases.png");
    public MoonIdentifier(String id) {
        super(id);
    }

    public Identifier getIdentifier() {
        long day_time = Mite.day_time;
        return MoonHelper.IsBloodMoon(day_time) ? BLOOD_MOON_PHASES :
                MoonHelper.IsBlueMoon(day_time) ? BLUE_MOON_PHASES :
                MoonHelper.IsHarvestMoon(day_time) ? HARVEST_MOON_PHASES :
                MOON_PHASES;
    }

    public String getPath() {
        return getIdentifier().getPath();
    }

    public String getNamespace() {
        return getIdentifier().getNamespace();
    }

    public String toString() {
        return this.namespace + ":" + this.path;
    }

    public boolean equals(Object o) {
        return getIdentifier().equals(o);
    }

    public int hashCode() {
        return getIdentifier().hashCode();
    }

    public int compareTo(Identifier identifier) {
        return getIdentifier().compareTo(identifier);
    }

    public String toUnderscoreSeparatedString() {

        return getIdentifier().toUnderscoreSeparatedString();
    }

}
