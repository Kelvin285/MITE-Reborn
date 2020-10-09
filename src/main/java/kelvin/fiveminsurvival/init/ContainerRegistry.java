package kelvin.fiveminsurvival.init;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import kelvin.fiveminsurvival.init.BlockRegistry;
import kelvin.fiveminsurvival.main.FiveMinSurvival;
import kelvin.fiveminsurvival.main.gui.MITEFurnaceContainer;
import kelvin.fiveminsurvival.main.gui.MITEWorkbenchContainer;
import kelvin.fiveminsurvival.tileentity.MITEFurnaceTileEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ContainerRegistry {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, FiveMinSurvival.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, FiveMinSurvival.MODID);

    public static final RegistryObject<ContainerType<MITEWorkbenchContainer>> WORKBENCH = CONTAINERS.register("workbench", () -> new ContainerType<>(MITEWorkbenchContainer::new));
    public static final RegistryObject<ContainerType<MITEFurnaceContainer>> FURNACE = CONTAINERS.register("furnace", () -> new ContainerType<>(MITEFurnaceContainer::new));
    public static final RegistryObject<TileEntityType<TileEntity>> FURNACE_TILE_ENTITY = register("furnace", () -> TileEntityType.Builder.create(MITEFurnaceTileEntity::new, BlockRegistry.CLAY_OVEN.get(), BlockRegistry.SANDSTONE_OVEN.get(), BlockRegistry.COBBLESTONE_FURNACE.get(), BlockRegistry.OBSIDIAN_FURNACE.get(), BlockRegistry.NETHERRACK_FURNACE.get()));

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<TileEntityType.Builder<T>> builder) {
        Type<?> type = null;

        try {
            type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getVersion().getWorldVersion())).getChoiceType(TypeReferences.BLOCK_ENTITY, name);
        } catch (IllegalArgumentException illegalstateexception) {
            if (SharedConstants.developmentMode) {
                throw illegalstateexception;
            }

        }
		Type<?> finalType = type;
		return TILE_ENTITIES.register(name, () -> builder.get().build(finalType));
    }

}
