package kelvin.mite.mixin;

import kelvin.mite.crafting.MiteCookingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractCookingRecipe.class)
public class AbstractCookingRecipeMixin implements MiteCookingRecipe {

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

    public int inputcount = 0;
    public int outputcount = 0;

    @Override
    public int getInputCount() {
        return inputcount;
    }

    @Override
    public int getOutputCount() {
        return outputcount;
    }

    @Override
    public void setInputCount(int inputCount) {
        this.inputcount = inputCount;
    }

    @Override
    public void setOutputCount(int outputCount) {
        this.outputcount = outputCount;
    }
}
