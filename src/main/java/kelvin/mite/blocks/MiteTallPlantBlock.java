package kelvin.mite.blocks;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class MiteTallPlantBlock extends net.minecraft.block.TallPlantBlock {

	public MiteTallPlantBlock(Settings properties) {
		super(properties);
		BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
	}

}
