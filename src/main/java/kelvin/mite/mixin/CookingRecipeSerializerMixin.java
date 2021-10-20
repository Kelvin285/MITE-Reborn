package kelvin.mite.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.tools.obfuscation.service.ObfuscationServices;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Mixin(CookingRecipeSerializer.class)
public class CookingRecipeSerializerMixin<T extends AbstractCookingRecipe> implements RecipeSerializer<T> {

    @Shadow
    private int cookingTime;

    @Override
    public T read(Identifier identifier, JsonObject jsonObject) {

        String string = JsonHelper.getString(jsonObject, "group", "");
        JsonElement jsonElement = JsonHelper.hasArray(jsonObject, "ingredient") ? JsonHelper.getArray(jsonObject, "ingredient") : JsonHelper.getObject(jsonObject, "ingredient");
        Ingredient ingredient = Ingredient.fromJson((JsonElement)jsonElement);
        String string2 = JsonHelper.getString(jsonObject, "result");
        Identifier identifier2 = new Identifier(string2);
        ItemStack itemStack = new ItemStack((ItemConvertible)Registry.ITEM.getOrEmpty(identifier2).orElseThrow(() -> {
            return new IllegalStateException("Item: " + string2 + " does not exist");
        }));
        float f = JsonHelper.getFloat(jsonObject, "experience", 0.0F);
        int i = JsonHelper.getInt(jsonObject, "cookingtime", this.cookingTime);

        System.out.println("Test 1");
        Field recipeFactory = CookingRecipeSerializer.class.getDeclaredFields()[1];
        System.out.println("Test 2: " + recipeFactory);
        T recipe = null;
        try {
            recipeFactory.setAccessible(true);
            System.out.println("Test 3: " + this);
            var factory = recipeFactory.get(this);
            System.out.println("Factory class type : " + factory);
            var result = factory.getClass().getDeclaredMethods()[0].invoke(factory, identifier, string, ingredient, itemStack, f, i);
            System.out.println("Result class type : " + result);
            recipe = (T) result;
            AbstractCookingRecipe.class.getDeclaredFields()[7].set(recipe, JsonHelper.getInt(jsonObject, "inputcount", 1)); //inputCount
            AbstractCookingRecipe.class.getDeclaredFields()[8].set(recipe, JsonHelper.getInt(jsonObject, "outputcount", 1)); //outputCount
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return recipe;
    }

    @Override
    public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
        String string = packetByteBuf.readString();
        Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
        ItemStack itemStack = packetByteBuf.readItemStack();
        float f = packetByteBuf.readFloat();
        int i = packetByteBuf.readVarInt();

        Field recipeFactory = CookingRecipeSerializer.class.getDeclaredFields()[1];
        T recipe = null;
        try {
            recipeFactory.setAccessible(true);
            System.out.println("Test 3: " + this);
            var factory = recipeFactory.get(this);
            System.out.println("Factory class type : " + factory);
            var result = factory.getClass().getDeclaredMethods()[0].invoke(factory, identifier, string, ingredient, itemStack, f, i);
            System.out.println("Result class type : " + result);
            recipe = (T) result;
            AbstractCookingRecipe.class.getDeclaredFields()[7].set(recipe, packetByteBuf.readVarInt()); //inputCount
            AbstractCookingRecipe.class.getDeclaredFields()[8].set(recipe, packetByteBuf.readVarInt()); //outputCount
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return recipe;
    }

    /*
    @Shadow
    protected RecipeType<?> type;
    @Shadow
    protected Identifier id;
    @Shadow
    protected String group;
    @Shadow
    protected Ingredient input;
    @Shadow
    protected ItemStack output;
    @Shadow
    protected float experience;
    @Shadow
    protected int cookTime;
     */

    @Override
    public void write(PacketByteBuf packetByteBuf, T recipe) {
        try {
            packetByteBuf.writeString((String)recipe.getClass().getDeclaredFields()[2].get(recipe)); //group
            ((Ingredient)recipe.getClass().getDeclaredFields()[3].get(recipe)).write(packetByteBuf); //input
            packetByteBuf.writeItemStack((ItemStack)recipe.getClass().getDeclaredFields()[4].get(recipe)); //output
            packetByteBuf.writeFloat((float)recipe.getClass().getDeclaredFields()[5].get(recipe)); //experience
            packetByteBuf.writeVarInt((int)recipe.getClass().getDeclaredFields()[6].get(recipe)); //cookTime
            packetByteBuf.writeVarInt((int)recipe.getClass().getDeclaredFields()[7].get(recipe)); //inputCount
            packetByteBuf.writeVarInt((int)recipe.getClass().getDeclaredFields()[8].get(recipe)); //outputCount
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
