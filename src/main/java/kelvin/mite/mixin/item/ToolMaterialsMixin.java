package kelvin.mite.mixin.item;

import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ToolMaterials.class)
public class ToolMaterialsMixin {

    @Shadow
    float miningSpeed;

    @Overwrite
    public float getMiningSpeedMultiplier() {
        if (((ToolMaterials)(Object)this) == ToolMaterials.STONE) {
            return 1.1F;
        }
        return this.miningSpeed;
    }
}
