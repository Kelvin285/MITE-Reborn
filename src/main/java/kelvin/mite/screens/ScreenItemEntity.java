package kelvin.mite.screens;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ScreenItemEntity extends ItemEntity {
    public ScreenItemEntity(World world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);
    }

    @Override
    public void tick() {

    }

    @Override
    public int getItemAge() {
        return 0;
    }

    @Override
    public float getRotation(float tickDelta) {
        return 0;
    }
}
