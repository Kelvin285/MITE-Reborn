package kelvin.mite.registry;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;
import kelvin.mite.blocks.entity.CropBlockEntity;
import kelvin.mite.blocks.entity.FarmlandBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.impl.registry.sync.FabricRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.util.registry.Registry;

import java.util.Set;

public class BlockEntityRegistry {


    public static BlockEntityType<FarmlandBlockEntity> FARMLAND;
    public static BlockEntityType<CropBlockEntity> CROPS;

    public static <T extends BlockEntity>BlockEntityType<T> Create(String name, BlockEntityType<T> type) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, name, type);
        return type;
    }

    public static void RegisterBlockEntities() {
        FARMLAND = Create("mite:farmland", FabricBlockEntityTypeBuilder.create(FarmlandBlockEntity::new, BlockRegistry.FARMLAND_DIRT, BlockRegistry.FARMLAND_ANDESITE, BlockRegistry.FARMLAND_DIORITE, BlockRegistry.FARMLAND_GRANITE, BlockRegistry.FARMLAND_LIMESTONE, BlockRegistry.FARMLAND_RED_SANDSTONE, BlockRegistry.FARMLAND_SANDSTONE).build());
        CROPS = Create("mite:crops", FabricBlockEntityTypeBuilder.create(CropBlockEntity::new, Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES, Blocks.BEETROOTS).build());

    }
}
