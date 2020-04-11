package kelvin.fiveminsurvival.init;

import kelvin.fiveminsurvival.entity.AnimalWatcherEntity;
import kelvin.fiveminsurvival.entity.EntityAttackSquid;
import kelvin.fiveminsurvival.entity.NewSkeletonEntity;
import kelvin.fiveminsurvival.entity.SpearEntity;
import kelvin.fiveminsurvival.main.FiveMinSurvival;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, FiveMinSurvival.MODID);

    public static final RegistryObject<EntityType<SpearEntity>> SPEAR_ENTITY = makeEntity("spear", EntityType.Builder.<SpearEntity>create(SpearEntity::new, EntityClassification.MISC).size(1.0f, 1.0f).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(world)));
    public static final RegistryObject<EntityType<AnimalWatcherEntity>> ZOMBIE_ENTITY = makeEntity("zombie", EntityType.Builder.<AnimalWatcherEntity>create(AnimalWatcherEntity::new, EntityClassification.MONSTER).size(0.6F, 1.95F).setCustomClientFactory((spawnEntity, world) -> new AnimalWatcherEntity(world)).setTrackingRange(32));
    public static final RegistryObject<EntityType<NewSkeletonEntity>> SKELETON_ENTITY = makeEntity("skeleton", EntityType.Builder.create(NewSkeletonEntity::new, EntityClassification.MONSTER).size(0.6F, 1.95F).setTrackingRange(32));
    public static final RegistryObject<EntityType<EntityAttackSquid>> ATTACK_SQUID = makeEntity("squid", EntityType.Builder.<EntityAttackSquid>create(EntityAttackSquid::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new EntityAttackSquid(world)));
    public static final RegistryObject<EntityType<CreeperEntity>> CREEPER = makeEntity("creeper", EntityType.Builder.create(CreeperEntity::new, EntityClassification.CREATURE).size(0.6F, 1.7F));

    private static <T extends Entity> RegistryObject<EntityType<T>> makeEntity(String name, EntityType.Builder<T> builder) {
		return ENTITIES.register(name, () -> builder.build(new ResourceLocation(FiveMinSurvival.MODID, name).toString()));
	}
}
