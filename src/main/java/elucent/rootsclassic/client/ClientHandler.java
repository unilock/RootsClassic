package elucent.rootsclassic.client;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.registry.ParticleRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class ClientHandler {
    public static final ModelLayerLocation SYLVAN_ARMOR = new ModelLayerLocation(new ResourceLocation(Const.MODID, "main"), "sylvan_armor");
    public static final ModelLayerLocation WILDWOOD_ARMOR = new ModelLayerLocation(new ResourceLocation(Const.MODID, "main"), "wildwood_armor");

    public static void registerParticleFactories() {
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((atlasTexture, registry) -> {
            registry.register(new ResourceLocation(Const.MODID, "particle/magic"));
        });

        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_TYPE.get(), MagicParticleData::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_AURA_TYPE.get(), MagicAuraParticleData::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_LINE_TYPE.get(), MagicLineParticleData::new);
    }
}
