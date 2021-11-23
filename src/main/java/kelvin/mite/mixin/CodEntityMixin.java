package kelvin.mite.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CodEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CodEntity.class)
public abstract class CodEntityMixin extends SchoolingFishEntity  {
    private boolean hasJockey = false;

    public CodEntityMixin(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean HasJockey() {
        return hasJockey;
    }
    public void setHasJockey(boolean hasJockey) {
        this.hasJockey = true;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.hasJockey = nbt.getBoolean("IsCodJockey");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("IsCodJockey", this.hasJockey);
    }

    @Override
    public boolean canBeRiddenInWater() {
        return true;
    }
}
