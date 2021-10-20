package kelvin.mite.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractCookingRecipe.class)
public class AbstractCookingRecipeMixin {

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
}
