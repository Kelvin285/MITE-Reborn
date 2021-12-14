package kelvin.mite.mixin.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EggItem.class)
public class EggItemMixin {
    @Inject(at=@At("HEAD"), method="use", cancellable = true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> info) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!user.isSneaking()) {
            info.setReturnValue(TypedActionResult.success(itemStack, world.isClient()));
        }
    }
}
