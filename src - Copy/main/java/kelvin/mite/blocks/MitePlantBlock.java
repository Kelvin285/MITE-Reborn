package kelvin.mite.blocks;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.PlantBlock;
import net.minecraft.client.render.RenderLayer;

public class MitePlantBlock extends PlantBlock {

	public MitePlantBlock(Settings settings) {
		super(settings);
		BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
	}

}
