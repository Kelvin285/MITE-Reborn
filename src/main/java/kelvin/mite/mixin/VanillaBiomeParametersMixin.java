package kelvin.mite.mixin;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VanillaBiomeParameters.class)
public class VanillaBiomeParametersMixin {

    @Shadow
    private MultiNoiseUtil.ParameterRange FROZEN_TEMPERATURE;
    @Shadow
    private MultiNoiseUtil.ParameterRange NON_FROZEN_TEMPERATURE_PARAMETERS;
    @Shadow
    private MultiNoiseUtil.ParameterRange MUSHROOM_FIELDS_CONTINENTALNESS;
    @Shadow
    private MultiNoiseUtil.ParameterRange DEEP_OCEAN_CONTINENTALNESS;
    @Shadow
    private MultiNoiseUtil.ParameterRange OCEAN_CONTINENTALNESS;
    @Shadow
    private MultiNoiseUtil.ParameterRange SHORE_CONTINENTALNESS;
    @Shadow
    private MultiNoiseUtil.ParameterRange RIVER_CONTINENTALNESS;
    @Shadow
    private MultiNoiseUtil.ParameterRange NEAR_INLAND_CONTINENTALNESS;
    @Shadow
    private MultiNoiseUtil.ParameterRange MID_INLAND_CONTINENTALNESS;
    @Shadow
    private MultiNoiseUtil.ParameterRange FAR_INLAND_CONTINENTALNESS;
    @Shadow
    private RegistryKey<Biome>[][] OCEAN_BIOMES;
    @Shadow
    private RegistryKey<Biome>[][] COMMON_BIOMES;
    @Shadow
    private RegistryKey<Biome>[][] UNCOMMON_BIOMES;
    @Shadow
    private RegistryKey<Biome>[][] NEAR_MOUNTAIN_BIOMES;
    @Shadow
    private RegistryKey<Biome>[][] SPECIAL_NEAR_MOUNTAIN_BIOMES;
    @Shadow
    private RegistryKey<Biome>[][] HILL_BIOMES;

    /*
    {
        MUSHROOM_FIELDS_CONTINENTALNESS = MultiNoiseUtil.ParameterRange.of(-1.2F, -1.05F);
        DEEP_OCEAN_CONTINENTALNESS = MultiNoiseUtil.ParameterRange.of(-1.05F, -0.65F);
        OCEAN_CONTINENTALNESS = MultiNoiseUtil.ParameterRange.of(-0.65F, -0.375F);
        SHORE_CONTINENTALNESS = MultiNoiseUtil.ParameterRange.of(-0.375F, -0.025F);
        NEAR_INLAND_CONTINENTALNESS = MultiNoiseUtil.ParameterRange.of(-0.025F, 0.375F);
        MID_INLAND_CONTINENTALNESS = MultiNoiseUtil.ParameterRange.of(0.375F, 0.6F);
        FAR_INLAND_CONTINENTALNESS = MultiNoiseUtil.ParameterRange.of(0.6F, 1.0F);
    }
     */

    @Inject(at=@At("RETURN"), method="<init>", cancellable = false)
    public void Constructor(CallbackInfo info) {

        VanillaBiomeParametersAccessor accessor = (VanillaBiomeParametersAccessor) (Object)this;

        accessor.SetMushroomFieldsContinentalness(MultiNoiseUtil.ParameterRange.of(-1.2F, -1.05F)); // 0.15
        //deep ocean = 0.4
        //everything else = 0.275
        accessor.SetDeepOceanContinentalness(MultiNoiseUtil.ParameterRange.of(-1.05F, -0.65F)); // 0.4
        accessor.SetOceanContinentalness(MultiNoiseUtil.ParameterRange.of(-0.65F, -0.375F)); // 0.275
        accessor.SetShoreContinentalness(MultiNoiseUtil.ParameterRange.of(-0.375F, -0.025F)); // 0.35
        accessor.SetNearInlandContinentalness(MultiNoiseUtil.ParameterRange.of(-0.025F, 0.375F)); // 0.4
        accessor.SetMidInlandContinentalness(MultiNoiseUtil.ParameterRange.of(0.375F, 0.6F));
        accessor.SetFarInlandContinentalness(MultiNoiseUtil.ParameterRange.of(0.6F, 1.0F));

    }
}
