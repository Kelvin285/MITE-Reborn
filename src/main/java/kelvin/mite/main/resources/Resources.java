package kelvin.mite.main.resources;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import kelvin.mite.registry.WeightRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class Resources {
	
	public static int MAX_CARRY = 400;
	
	public static void makeFieldAccessible(Field field) throws Exception {
		Field modifiers = Field.class.getDeclaredField("modifiers");
		modifiers.setAccessible(true);
		try {
			modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			modifiers.setInt(field, field.getModifiers() & ~Modifier.PROTECTED);
			modifiers.setInt(field, field.getModifiers() | Modifier.PUBLIC);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
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
