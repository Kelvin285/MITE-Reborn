package kelvin.mite.blocks;

import kelvin.mite.main.Mite;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class MiteTallPlantBlock extends net.minecraft.block.TallPlantBlock {

	public MiteTallPlantBlock(Settings properties) {
		super(properties);
		if (Mite.client) BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
	}

}
