package kelvin.mite.mixin.item;

import kelvin.mite.items.ShieldTier;
import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShieldItem.class)
public class ShieldItemMixin implements ShieldTier  {

    public int protectionLevel = 0;

    @Override
    public int getProtectionLevel() {
        return protectionLevel;
    }

    @Override
    public void setProtectionLevel(int protectionLevel) {
        this.protectionLevel = protectionLevel;
    }
}
