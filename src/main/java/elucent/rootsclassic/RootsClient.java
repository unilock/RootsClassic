package elucent.rootsclassic;

import elucent.rootsclassic.client.ClientHandler;
import elucent.rootsclassic.client.ui.ManaBarEvent;
import elucent.rootsclassic.research.ResearchManager;
import net.fabricmc.api.ClientModInitializer;

public class RootsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ManaBarEvent.onGameOverlayRender();
        ManaBarEvent.clientEndTick();
        ClientHandler.onClientSetup();
        ClientHandler.registerEntityRenders();
        ClientHandler.registerLayerDefinitions();
        ClientHandler.registerItemColors();
        ClientHandler.registerParticleFactories();
        ResearchManager.onRecipesUpdated();
    }
}
