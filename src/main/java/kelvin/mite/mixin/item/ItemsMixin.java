package kelvin.mite.mixin.item;

import com.mojang.serialization.Lifecycle;
import kelvin.mite.items.SurvivalItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import kelvin.mite.main.resources.ItemChanger;

import java.util.OptionalInt;

@Mixin(Items.class)
public class ItemsMixin implements ItemChanger {
    @Mutable
    @Shadow
    public static Item STICK;

    @Mutable
    @Shadow
    public static Item BONE;

    @Shadow
    private static Item register(String id, Item item) {
        return null;
    }

    @Override
    public void ChangeItems() {
        //ToolItem stick_item = new ToolItem(SurvivalItemTier.STICK, new Item.Settings().group(ItemGroup.MATERIALS));
        //STICK = Registry.ITEM.set(Registry.ITEM.getRawId(STICK), Registry.ITEM.getKey(STICK).get(), stick_item, Lifecycle.stable());

        //ToolItem bone_item = new ToolItem(SurvivalItemTier.STICK, new Item.Settings().group(ItemGroup.MATERIALS));
        //BONE = Registry.ITEM.set(Registry.ITEM.getRawId(BONE), Registry.ITEM.getKey(BONE).get(), stick_item, Lifecycle.stable());
    }
}
