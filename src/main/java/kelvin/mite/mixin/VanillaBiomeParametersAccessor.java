package kelvin.mite.mixin;

import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VanillaBiomeParameters.class)
public interface VanillaBiomeParametersAccessor {
    @Mutable
    @Accessor("MUSHROOM_FIELDS_CONTINENTALNESS")
    public void SetMushroomFieldsContinentalness(MultiNoiseUtil.ParameterRange MUSHROOM_FIELDS_CONTINENTALNESS);
    @Mutable
    @Accessor("DEEP_OCEAN_CONTINENTALNESS")
    public void SetDeepOceanContinentalness(MultiNoiseUtil.ParameterRange DEEP_OCEAN_CONTINENTALNESS);
    @Mutable
    @Accessor("OCEAN_CONTINENTALNESS")
    public void SetOceanContinentalness(MultiNoiseUtil.ParameterRange OCEAN_CONTINENTALNESS);
    @Mutable
    @Accessor("SHORE_CONTINENTALNESS")
    public void SetShoreContinentalness(MultiNoiseUtil.ParameterRange SHORE_CONTINENTALNESS);
    @Mutable
    @Accessor("RIVER_CONTINENTALNESS")
    public void SetRiverContinentalness(MultiNoiseUtil.ParameterRange RIVER_CONTINENTALNESS);
    @Mutable
    @Accessor("NEAR_INLAND_CONTINENTALNESS")
    public void SetNearInlandContinentalness(MultiNoiseUtil.ParameterRange NEAR_INLAND_CONTINENTALNESS);
    @Mutable
    @Accessor("MID_INLAND_CONTINENTALNESS")
    public void SetMidInlandContinentalness(MultiNoiseUtil.ParameterRange MID_INLAND_CONTINENTALNESS);
    @Mutable
    @Accessor("FAR_INLAND_CONTINENTALNESS")
    public void SetFarInlandContinentalness(MultiNoiseUtil.ParameterRange FAR_INLAND_CONTINENTALNESS);
}
