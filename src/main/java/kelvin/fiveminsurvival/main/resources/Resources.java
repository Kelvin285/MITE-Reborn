package kelvin.fiveminsurvival.main.resources;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import kelvin.fiveminsurvival.entity.RaycastCollision;
import kelvin.fiveminsurvival.game.food.Nutrients;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class Resources {
	public static final int dayTicks = 24000; //one day is 24000 ticks
	public static Nutrients clientNutrients = new Nutrients(null);
	public static int currentTable = 10;
	
	public static boolean malnourished = false;
	
	public static HashMap<Item, int[]> furnaceInOut = new HashMap<Item, int[]>();
	
	public static void SetFurnaceRecipes() {
		furnaceInOut.put(Items.SANDSTONE, new int[]{4, 1});
	}
	
	public static void makeFieldAccessible(Field field) {
		Field modifiers = ObfuscationReflectionHelper.findField(Field.class, "modifiers");
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
        return MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
    }
	
	public static float lerp(float a, float b, float lerp)
	{
	    return a + lerp * (b - a);
	}
	
	public static RaycastCollision getBlockCollisionForPhysicalReach(Vector3d start, Vector3d end, World world) {
		//   public RayTraceContext(Vector3d startVecIn, Vector3d endVecIn, RayTraceContext.BlockMode blockModeIn, RayTraceContext.FluidMode fluidModeIn, @javax.annotation.Nullable Entity entityIn) {

		
		RaycastCollision rc = new RaycastCollision();
		BlockRayTraceResult result = world.rayTraceBlocks(new RayTraceContext(start, end, BlockMode.COLLIDER, FluidMode.NONE, null));
		
		if (result != null) {
			rc.block_hit_x = result.getPos().getX();
			rc.block_hit_y = result.getPos().getY();
			rc.block_hit_z = result.getPos().getZ();
			rc.blockHit = world.getBlockState(result.getPos()).getBlock();
		}
		
		return rc;
	}
}
