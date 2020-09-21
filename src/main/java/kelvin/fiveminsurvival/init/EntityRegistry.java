package kelvin.fiveminsurvival.init;

import kelvin.fiveminsurvival.entity.AnimalWatcherEntity;
<<<<<<< HEAD
import kelvin.fiveminsurvival.entity.BowlItemEntity;
import kelvin.fiveminsurvival.entity.CustomCreeperEntity;
import kelvin.fiveminsurvival.entity.EntityAttackSquid;
import kelvin.fiveminsurvival.entity.FeatherItemEntity;
=======
import kelvin.fiveminsurvival.entity.EntityAttackSquid;
>>>>>>> 2eba99c0d696ed96f3b32c8e0a922901b317922d
import kelvin.fiveminsurvival.entity.NewSkeletonEntity;
import kelvin.fiveminsurvival.entity.SpearEntity;
import kelvin.fiveminsurvival.main.FiveMinSurvival;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
<<<<<<< HEAD
=======
import net.minecraft.entity.monster.CreeperEntity;
>>>>>>> 2eba99c0d696ed96f3b32c8e0a922901b317922d
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, FiveMinSurvival.MODID);

    public static final RegistryObject<EntityType<SpearEntity>> SPEAR_ENTITY = makeEntity("spear", EntityType.Builder.<SpearEntity>create(SpearEntity::new, EntityClassification.MISC).size(1.0f, 1.0f).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(world)));
    public static final RegistryObject<EntityType<AnimalWatcherEntity>> ZOMBIE_ENTITY = makeEntity("zombie", EntityType.Builder.<AnimalWatcherEntity>create(AnimalWatcherEntity::new, EntityClassification.MONSTER).size(0.6F, 1.95F).setCustomClientFactory((spawnEntity, world) -> new AnimalWatcherEntity(world)).setTrackingRange(32));
    public static final RegistryObject<EntityType<NewSkeletonEntity>> SKELETON_ENTITY = makeEntity("skeleton", EntityType.Builder.create(NewSkeletonEntity::new, EntityClassification.MONSTER).size(0.6F, 1.95F).setTrackingRange(32));
    public static final RegistryObject<EntityType<EntityAttackSquid>> ATTACK_SQUID = makeEntity("squid", EntityType.Builder.<EntityAttackSquid>create(EntityAttackSquid::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new EntityAttackSquid(world)));
<<<<<<< HEAD
    public static final RegistryObject<EntityType<CustomCreeperEntity>> CREEPER = makeEntity("creeper", EntityType.Builder.create(CustomCreeperEntity::new, EntityClassification.CREATURE).size(0.6F, 1.7F));
    public static final RegistryObject<EntityType<FeatherItemEntity>> FEATHER = makeEntity("feather", EntityType.Builder.create(FeatherItemEntity::new, EntityClassification.MISC).size(0.25F, 0.25F));
    public static final RegistryObject<EntityType<BowlItemEntity>> BOWL = makeEntity("bowl", EntityType.Builder.create(BowlItemEntity::new, EntityClassification.MISC).size(0.25F, 0.25F));
=======
    public static final RegistryObject<EntityType<CreeperEntity>> CREEPER = makeEntity("creeper", EntityType.Builder.create(CreeperEntity::new, EntityClassification.CREATURE).size(0.6F, 1.7F));
>>>>>>> 2eba99c0d696ed96f3b32c8e0a922901b317922d

    private static <T extends Entity> RegistryObject<EntityType<T>> makeEntity(String name, EntityType.Builder<T> builder) {
		return ENTITIES.register(name, () -> builder.build(new ResourceLocation(FiveMinSurvival.MODID, name).toString()));
	}
}
