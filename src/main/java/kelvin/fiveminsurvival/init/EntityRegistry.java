package kelvin.fiveminsurvival.init;

import java.util.HashMap;

import kelvin.fiveminsurvival.entity.AnimalWatcherEntity;
import kelvin.fiveminsurvival.entity.BowlItemEntity;
import kelvin.fiveminsurvival.entity.CustomCreeperEntity;
import kelvin.fiveminsurvival.entity.EntityAttackSquid;
import kelvin.fiveminsurvival.entity.FeatherItemEntity;
import kelvin.fiveminsurvival.entity.NewSkeletonEntity;
import kelvin.fiveminsurvival.entity.SpearEntity;
import kelvin.fiveminsurvival.main.FiveMinSurvival;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, FiveMinSurvival.MODID);

    private static HashMap<Class<? extends Entity>, EntityType<? extends LivingEntity>> entity_map = new HashMap<Class<? extends Entity>, EntityType<? extends LivingEntity>>();
    
    public static final RegistryObject<EntityType<SpearEntity>> SPEAR_ENTITY = makeEntity("spear", EntityType.Builder.<SpearEntity>create(SpearEntity::new, EntityClassification.MISC).size(1.0f, 1.0f).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(world)));
    public static final RegistryObject<EntityType<AnimalWatcherEntity>> ZOMBIE_ENTITY = makeLivingEntity(AnimalWatcherEntity.class, "zombie", EntityType.Builder.<AnimalWatcherEntity>create(AnimalWatcherEntity::new, EntityClassification.MONSTER).size(0.6F, 1.95F).setCustomClientFactory((spawnEntity, world) -> new AnimalWatcherEntity(world)).setTrackingRange(32));
    public static final RegistryObject<EntityType<NewSkeletonEntity>> SKELETON_ENTITY = makeLivingEntity(NewSkeletonEntity.class, "skeleton", EntityType.Builder.create(NewSkeletonEntity::new, EntityClassification.MONSTER).size(0.6F, 1.95F).setTrackingRange(32));
    public static final RegistryObject<EntityType<EntityAttackSquid>> ATTACK_SQUID = makeLivingEntity(EntityAttackSquid.class, "squid", EntityType.Builder.<EntityAttackSquid>create(EntityAttackSquid::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new EntityAttackSquid(world)));
    public static final RegistryObject<EntityType<CustomCreeperEntity>> CREEPER = makeLivingEntity(CustomCreeperEntity.class, "creeper", EntityType.Builder.create(CustomCreeperEntity::new, EntityClassification.CREATURE).size(0.6F, 1.7F));
    public static final RegistryObject<EntityType<FeatherItemEntity>> FEATHER = makeEntity("feather", EntityType.Builder.create(FeatherItemEntity::new, EntityClassification.MISC).size(0.25F, 0.25F));
    public static final RegistryObject<EntityType<BowlItemEntity>> BOWL = makeEntity("bowl", EntityType.Builder.create(BowlItemEntity::new, EntityClassification.MISC).size(0.25F, 0.25F));

    
    private static <T extends LivingEntity> RegistryObject<EntityType<T>> makeLivingEntity(Class<? extends LivingEntity> c, String name, EntityType.Builder<T> builder) {
    	EntityType<T> TYPE = builder.build(new ResourceLocation(FiveMinSurvival.MODID, name).toString());
    	RegistryObject<EntityType<T>> type = ENTITIES.register(name, () -> TYPE);
    	entity_map.put(c, TYPE);
		return type;
	}
    
    private static <T extends Entity> RegistryObject<EntityType<T>> makeEntity(String name, EntityType.Builder<T> builder) {
		return ENTITIES.register(name, () -> builder.build(new ResourceLocation(FiveMinSurvival.MODID, name).toString()));
	}
    
    public static void RegisterEntityAttributes() {
		for (Class<? extends Entity> c : entity_map.keySet()) {
			EntityType<? extends LivingEntity> type = entity_map.get(c);
			GlobalEntityTypeAttributes.put(type, LivingEntity.registerAttributes().createMutableAttribute(Attributes.FOLLOW_RANGE).createMutableAttribute(Attributes.ATTACK_DAMAGE).createMutableAttribute(Attributes.ATTACK_KNOCKBACK).createMutableAttribute(Attributes.ARMOR).createMutableAttribute(Attributes.ARMOR_TOUGHNESS).createMutableAttribute(Attributes.ATTACK_SPEED).createMutableAttribute(Attributes.FLYING_SPEED).createMutableAttribute(Attributes.HORSE_JUMP_STRENGTH).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE).createMutableAttribute(Attributes.LUCK).createMutableAttribute(Attributes.ZOMBIE_SPAWN_REINFORCEMENTS).createMutableAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).createMutableAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).create());
			System.out.println("Registered entity type " + type + "!");
		}
    }
}
