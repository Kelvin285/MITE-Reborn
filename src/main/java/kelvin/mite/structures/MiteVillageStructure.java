package kelvin.mite.structures;

import com.mojang.serialization.Codec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.minecraft.world.gen.feature.VillageFeature;

import java.lang.reflect.Field;
import java.util.List;

public class MiteVillageStructure extends JigsawFeature {
    public static boolean CanGenerateVillage = false;


    public static boolean canGenerate(World world) {
        if (!world.isClient()) {
            List<? extends PlayerEntity> players = world.getPlayers();
            Field iron = null;
            try {
                iron = PlayerEntity.class.getDeclaredField("acquired_iron");
                boolean can_spawn = false;
                for (PlayerEntity p : players) {
                    if (iron.getBoolean(p)) {
                        can_spawn = true;
                        break;
                    }
                }
                if (!can_spawn) {
                    return false;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    public MiteVillageStructure(Codec<StructurePoolFeatureConfig> configCodec) {
        super(configCodec, 0, true, true, (context) -> {
            return CanGenerateVillage;
        });
    }
}
