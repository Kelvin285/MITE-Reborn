package kelvin.mite.entity.rendering;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PolarBearEntityRenderer;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.Identifier;

public class BearRenderer extends PolarBearEntityRenderer {
    private static final Identifier TEXTURE = new Identifier("mite:textures/entity/bear/bear.png");

    public BearRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
    public Identifier getTexture(PolarBearEntity polarBearEntity) {

        return TEXTURE;
    }

}
