package kelvin.mite.mixin.client;

import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WolfEntityRenderer.class)
public class WolfEntityRendererMixin {
    private static final Identifier WILD_TEXTURE = new Identifier("textures/entity/wolf/wolf.png");
    private static final Identifier TAMED_TEXTURE = new Identifier("textures/entity/wolf/wolf_tame.png");
    private static final Identifier ANGRY_TEXTURE = new Identifier("textures/entity/wolf/wolf_angry.png");
    private static final Identifier DIRE_NEUTRAL = new Identifier("mite", "textures/entity/dire_wolf/neutral.png");
    private static final Identifier DIRE_TAME = new Identifier("mite", "textures/entity/dire_wolf/tame.png");
    private static final Identifier DIRE_ANGRY = new Identifier("mite", "textures/entity/dire_wolf/angry.png");


    public Identifier getTexture(WolfEntity wolfEntity) {
        boolean dire = false;
        try {
            dire = (boolean)WolfEntity.class.getMethod("isDire").invoke(wolfEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (wolfEntity.isTamed()) {
            return dire ? DIRE_TAME : TAMED_TEXTURE;
        } else {
            return wolfEntity.hasAngerTime() ? (dire ? DIRE_ANGRY : ANGRY_TEXTURE) : (dire ? DIRE_NEUTRAL : WILD_TEXTURE);
        }
    }
}
