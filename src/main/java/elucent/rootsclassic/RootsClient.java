package elucent.rootsclassic;

import elucent.rootsclassic.client.ClientHandler;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.registry.ParticleRegistry;
import elucent.rootsclassic.research.ResearchManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class RootsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientHandler.registerParticleFactories();
        ResearchManager.onRecipesUpdated();
    }
}
