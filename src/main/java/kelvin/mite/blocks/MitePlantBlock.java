package kelvin.mite.blocks;

import kelvin.mite.main.Mite;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.PlantBlock;
import net.minecraft.client.render.RenderLayer;

public class MitePlantBlock extends PlantBlock {

	public MitePlantBlock(Settings settings) {
		super(settings);
		if (Mite.client) {
			BlockRenderLayerMap.INSTANCE.putBlock(this, RenderLayer.getCutout());
		}
	}

}
