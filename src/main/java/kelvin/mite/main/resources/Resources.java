package kelvin.mite.main.resources;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import kelvin.mite.registry.WeightRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.Resource;
import net.minecraft.util.math.MathHelper;

public class Resources {
	
	public static int MAX_CARRY = 400;

	
	public static float getDistanceFromDeltas(double dx, double dy, double dz)
    {
        return MathHelper.sqrt((float)(dx * dx + dy * dy + dz * dz));
    }
	
	public static float lerp(float a, float b, float lerp)
	{
	    return a + lerp * (b - a);
	}

	public static int GetInventoryWeight(PlayerInventory inventory) {
		int weight = 0;
		for (ItemStack stack : inventory.armor) { 
			if (stack != null) {
				weight += WeightRegistry.weight.get(stack.getItem()) * stack.getCount();
			}
		}
		for (ItemStack stack : inventory.main) { 
			if (stack != null) {
				weight += WeightRegistry.weight.get(stack.getItem()) * stack.getCount();
			}
		}
		for (ItemStack stack : inventory.offHand) { 
			if (stack != null) {
				weight += WeightRegistry.weight.get(stack.getItem()) * stack.getCount();
			}
		}
		return weight;
	}
}
