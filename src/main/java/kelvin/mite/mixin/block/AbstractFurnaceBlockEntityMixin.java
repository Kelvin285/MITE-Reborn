package kelvin.mite.mixin.block;

import java.lang.reflect.Method;
import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import kelvin.mite.registry.ItemRegistry;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider {

	@Shadow
	protected DefaultedList<ItemStack> inventory;
	@Shadow
	int burnTime;
	@Shadow
	int fuelTime;
	@Shadow
	int cookTime;
	@Shadow
	int cookTimeTotal;
	@Shadow
	private Object2IntOpenHashMap<Identifier> recipesUsed;
	@Shadow
	private RecipeType<? extends AbstractCookingRecipe> recipeType;

	public int input_count;
	public int output_count;


	protected AbstractFurnaceBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	@Inject(at = @At("RETURN"), method = "createFuelTimeMap", cancellable = true)
	private static void createFuelTimeMap(CallbackInfoReturnable<Map<Item, Integer>> info) {
		Map<Item, Integer> map = info.getReturnValue();
		map.put(ItemRegistry.THIN_OAK_LOG, 250);
		map.put(ItemRegistry.THIN_BIRCH_LOG, 250);
		map.put(ItemRegistry.THIN_SPRUCE_LOG, 250);
		info.setReturnValue(map);
	}

	@Shadow
	private int getFuelTime(ItemStack stack) {
		return 0;
	}

	@Inject(at = @At("HEAD"), method = "readNbt")
	public void readNbt(NbtCompound nbt, CallbackInfo info) {
		this.input_count = nbt.getInt("InputCount");
		this.output_count = nbt.getInt("OutputCount");
		if (input_count == 0) input_count = 1;
		if (output_count == 0) output_count = 1;
	}

	@Overwrite
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putShort("BurnTime", (short)this.burnTime);
		nbt.putShort("CookTime", (short)this.cookTime);
		nbt.putShort("CookTimeTotal", (short)this.cookTimeTotal);
		Inventories.writeNbt(nbt, this.inventory);
		NbtCompound nbtCompound = new NbtCompound();
		this.recipesUsed.forEach((identifier, count) -> {
			nbtCompound.putInt(identifier.toString(), count);
		});
		nbt.put("RecipesUsed", nbtCompound);
		nbt.putInt("InputCount", this.input_count);
		nbt.putInt("OutputCount", this.output_count);
	}

	@Shadow
	private static boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
		return false;
	}

	@Shadow
	private boolean isBurning() {
		return false;
	}

	@Shadow
	private static int getCookTime(World world, RecipeType<? extends AbstractCookingRecipe> recipeType, Inventory inventory) {
		return 0;
	}


	private static Method craftRecipe = null;
	@Inject(at = @At("HEAD"), method = "tick", cancellable = true)
	private static void tick(World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo info) {

		AbstractFurnaceBlockEntityMixin mixin = (AbstractFurnaceBlockEntityMixin)(Object)blockEntity;

		if (mixin.input_count > 0 || mixin.output_count > 0)
		{
			boolean bl = mixin.isBurning();
			boolean bl2 = false;
			ItemStack itemStack = (ItemStack)mixin.inventory.get(1);
			if (mixin.isBurning() || !itemStack.isEmpty() && !((ItemStack)mixin.inventory.get(0)).isEmpty()) {
				Recipe<?> recipe = (Recipe) world.getRecipeManager().getFirstMatch(mixin.recipeType, blockEntity, world).orElse(null);
				if (mixin.inventory.get(0) != null) {
					if (recipe != null) {
						try {
							if (mixin.inventory.get(0).getCount() < AbstractCookingRecipe.class.getDeclaredField("inputcount").getInt(recipe)) {
								info.cancel();
								return;
							}
						} catch (Exception e) {
							System.out.println("AbstractFurnaceBlockEntityMixin Error!");
							e.printStackTrace();
						}
					}

				}
			}
		}

	}
	@Inject(at = @At("HEAD"), method = "canAcceptRecipeOutput", cancellable = true)
	private static void canAcceptRecipeOutput(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count, CallbackInfoReturnable<Boolean> info) {
		int inputCount = 1;
		try {
			inputCount = AbstractCookingRecipe.class.getDeclaredField("inputcount").getInt(recipe);
		} catch (Exception e)  {

		}
		if (slots.get(0).getCount() < inputCount) {
			info.setReturnValue(false);
		}
	}

	@Inject(at = @At("HEAD"), method = "craftRecipe", cancellable = true)
	private static void craftRecipe(@Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count, CallbackInfoReturnable<Boolean> info) {
		if (recipe != null && canAcceptRecipeOutput(recipe, slots, count)) {
			int inputCount = 1;
			try {
				recipe.getOutput().setCount(AbstractCookingRecipe.class.getDeclaredField("outputcount").getInt(recipe));
				inputCount = AbstractCookingRecipe.class.getDeclaredField("inputcount").getInt(recipe);
			} catch (Exception e)  {

			}
			if (inputCount <= 0) {
				inputCount = 1;
			}
			ItemStack itemStack = (ItemStack)slots.get(0);
			ItemStack itemStack2 = recipe.getOutput();
			ItemStack itemStack3 = (ItemStack)slots.get(2);
			if (itemStack3.isEmpty()) {
				slots.set(2, itemStack2.copy());
			} else if (itemStack3.isOf(itemStack2.getItem())) {
				itemStack3.increment(itemStack2.getCount());
			}
			itemStack.decrement(inputCount);
		}
	}
}
