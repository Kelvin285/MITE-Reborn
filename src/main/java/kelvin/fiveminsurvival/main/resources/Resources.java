package kelvin.fiveminsurvival.main.resources;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import kelvin.fiveminsurvival.entity.RaycastCollision;
import kelvin.fiveminsurvival.survival.food.Nutrients;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class Resources {
	public static final int dayTicks = 24000; //one day is 24000 ticks
	public static Nutrients clientNutrients = new Nutrients(null);
	public static int currentTable = 0;
	
	public static boolean malnourished = false;
	
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
	
	public static RaycastCollision getBlockCollisionForPhysicalReach(Vec3d start, Vec3d end, World world) {
		Vec3d slope = new Vec3d(end.x - start.x, end.y - start.y, end.z - start.z);
		RaycastCollision rc = new RaycastCollision();
		for (int i = 0; i < start.distanceTo(end); i++) {
			BlockPos pos = new BlockPos(start.x + slope.x * i, start.y + slope.y * i, start.z * slope.z * i);
			if (world.isAreaLoaded(pos, 1))
			if (world.getBlockState(pos).getMaterial().isSolid()) {
				rc.block_hit_x = pos.getX();
				rc.block_hit_y = pos.getY();
				rc.block_hit_z = pos.getZ();
				rc.blockHit = world.getBlockState(pos).getBlock();
			}
		}
		return rc;
	}
}
