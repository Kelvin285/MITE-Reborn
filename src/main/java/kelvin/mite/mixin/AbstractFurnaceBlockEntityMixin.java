package kelvin.mite.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Maps;

import kelvin.mite.registry.ItemRegistry;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
	
	@Inject(at = @At("RETURN"), method = "createFuelTimeMap", cancellable = true)
	private static void createFuelTimeMap(CallbackInfoReturnable<Map<Item, Integer>> info) {
		Map<Item, Integer> map = info.getReturnValue();
		map.put(ItemRegistry.THIN_OAK_LOG, 250);
		map.put(ItemRegistry.THIN_BIRCH_LOG, 250);
		map.put(ItemRegistry.THIN_SPRUCE_LOG, 250);
		info.setReturnValue(map);
	}
}
