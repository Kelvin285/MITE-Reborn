package kelvin.mite.main.resources;


import net.minecraft.entity.damage.DamageSource;

public class DelayedDamage {
    public DamageSource source;
    public float amount;
    public int delay = 0;
    public DelayedDamage(DamageSource source, float amount, int delay) {
        this.source = source;
        this.amount = amount;
        this.delay = delay;
    }
}
