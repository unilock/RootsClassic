package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.entity.EntityAccelerator;
import elucent.rootsclassic.entity.EntityTileAccelerator;
import elucent.rootsclassic.entity.skeleton.PhantomSkeletonEntity;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;

public class RootsEntities {
    public static final LazyRegistrar<EntityType<?>> ENTITY_TYPES = LazyRegistrar.create(BuiltInRegistries.ENTITY_TYPE, Const.MODID);
    public static final RegistryObject<EntityType<PhantomSkeletonEntity>> PHANTOM_SKELETON = ENTITY_TYPES.register("phantom_skeleton", () -> register("phantom_skeleton", EntityType.Builder.<PhantomSkeletonEntity>of(PhantomSkeletonEntity::new, MobCategory.MONSTER)
            .sized(0.6F, 1.99F).clientTrackingRange(6)));
    public static final RegistryObject<EntityType<EntityAccelerator>> ENTITY_ACCELERATOR = ENTITY_TYPES.register("entity_accelerator", () -> register("entity_accelerator", EntityType.Builder.<EntityAccelerator>of(EntityAccelerator::new, MobCategory.MISC)
            .sized(0.5F, 0.5F).clientTrackingRange(64).updateInterval(20)));
    public static final RegistryObject<EntityType<EntityTileAccelerator>> TILE_ACCELERATOR = ENTITY_TYPES.register("tile_accelerator", () -> register("tile_accelerator", EntityType.Builder.<EntityTileAccelerator>of(EntityTileAccelerator::new, MobCategory.MISC)
            .sized(0.5F, 0.5F).clientTrackingRange(64).updateInterval(20)));

    public static void registerSpawnPlacement() {
        SpawnPlacements.register(RootsEntities.PHANTOM_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
    }

    public static void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonEntity.registerAttributes());
    }

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        return builder.build(id);
    }
}
