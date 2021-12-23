package kelvin.mite.main.resources;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import kelvin.mite.registry.WeightRegistry;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.Resource;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.SimpleVoxelShape;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

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

	public static RaycastCollision getBlockCollisionForPhysicalReach(Vec3d start, Vec3d end, World world, Entity entity) {
		//   public RayTraceContext(Vector3d startVecIn, Vector3d endVecIn, RayTraceContext.BlockMode blockModeIn, RayTraceContext.FluidMode fluidModeIn, @javax.annotation.Nullable Entity entityIn) {


		RaycastCollision rc = new RaycastCollision();
		BlockHitResult result = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));

		if (result != null) {

			rc.block_hit_x = result.getBlockPos().getX();
			rc.block_hit_y = result.getBlockPos().getY();
			rc.block_hit_z = result.getBlockPos().getZ();
			rc.blockHit = world.getBlockState(result.getBlockPos()).getBlock();
		}

		return rc;
	}
}
