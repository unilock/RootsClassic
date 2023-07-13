package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.particles.factory.MagicParticleType;
import elucent.rootsclassic.client.particles.factory.MagicParticleTypeData;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ParticleRegistry {
    public static final LazyRegistrar<ParticleType<?>> PARTICLE_TYPES = LazyRegistrar.create(BuiltInRegistries.PARTICLE_TYPE, Const.MODID);
    public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_TYPE = PARTICLE_TYPES.register("magic", MagicParticleType::new);
    public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_AURA_TYPE = PARTICLE_TYPES.register("magic_aura", MagicParticleType::new);
    public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_ALTAR_TYPE = PARTICLE_TYPES.register("magic_altar", MagicParticleType::new);
    public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_ALTAR_LINE_TYPE = PARTICLE_TYPES.register("magic_altar_line", MagicParticleType::new);
    public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_LINE_TYPE = PARTICLE_TYPES.register("magic_line", MagicParticleType::new);
}
