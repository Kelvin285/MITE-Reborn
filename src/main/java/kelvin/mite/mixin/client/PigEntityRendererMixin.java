package kelvin.mite.mixin.client;

import kelvin.mite.entity.HungryAnimal;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PigEntityRenderer.class)
public class PigEntityRendererMixin {
    private static final Identifier TEXTURE = new Identifier("textures/entity/pig/pig.png");
    private static final Identifier SICK_TEXTURE = new Identifier("mite:textures/entity/pig/sick.png");

    public Identifier getTexture(PigEntity pigEntity) {
        return ((HungryAnimal)pigEntity).isSick() ? SICK_TEXTURE : TEXTURE;
    }
}
