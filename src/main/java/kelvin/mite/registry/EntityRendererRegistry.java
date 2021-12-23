package kelvin.mite.registry;

import kelvin.mite.entity.TurkeyEntity;
import kelvin.mite.entity.rendering.BearRenderer;
import kelvin.mite.entity.rendering.turkey.TurkeyRenderer;
import kelvin.mite.main.Mite;

public class EntityRendererRegistry {
    public static void Register() {
        if (Mite.client) {
            net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry.INSTANCE.register(EntityRegistry.TURKEY,
                    (context) -> {
                        return new TurkeyRenderer<TurkeyEntity>(context);
                    });

            net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry.INSTANCE.register(EntityRegistry.BEAR,
                    (context) -> {
                        return new BearRenderer(context);
                    });
        }
//		EntityRendererRegistry.INSTANCE.register(BASIC_TREE,
//				(context) -> {
//					return new BasicTreeRenderer(context);
//				});
    }

}
