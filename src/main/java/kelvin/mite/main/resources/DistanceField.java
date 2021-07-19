package kelvin.mite.main.resources;

import java.awt.Point;

import net.minecraft.util.math.BlockPos;

public class DistanceField {
	public static boolean cone(BlockPos pos, BlockPos cone, float height, float radius) {
		float rad = Resources.lerp(radius, 0, (float)Math.min(1.0f, (float)Math.max(0, pos.getY() - cone.getY()) / height));
		return (float)Point.distance(pos.getX(), pos.getZ(), cone.getX(), cone.getZ()) - rad < 0;
	}
	public static boolean sphere(BlockPos pos, BlockPos sphere, float radius) {
		return pos.isWithinDistance(sphere, radius);
	}
	public static boolean Cylinder(BlockPos pos, BlockPos cylinder, float height, float radius) {
		boolean circle = Point.distance(pos.getX(), pos.getZ(), cylinder.getX(), cylinder.getZ()) <= radius;
		return circle && pos.getY() >= cylinder.getY() && pos.getY() <= cylinder.getY() + height;
	}
	
	public static boolean Union(boolean a, boolean b) {
		return a | b;
	}
	
	public static boolean Subtraction(boolean a, boolean b) {
		return a & !b;
	}
	
	public static boolean Intersection(boolean a, boolean b) {
		return a && b;
	}
}
