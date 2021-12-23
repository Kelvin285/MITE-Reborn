package kelvin.mite.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.world.World;

public class BearEntity extends PolarBearEntity {
    public BearEntity(EntityType<? extends PolarBearEntity> entityType, World world) {
        super(entityType, world);
    }
}
