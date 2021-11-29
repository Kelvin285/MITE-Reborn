package kelvin.mite.mixin;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RecipeBook.class)
public class RecipeBookMixin {

    /*
    @Shadow
    protected void add(Identifier id) {

    }

    public void add(Recipe<?> recipe) {
        this.add(recipe.getId());
    }

    public boolean shouldDisplay(Recipe<?> recipe) {
        return true;
    }
     */
}
