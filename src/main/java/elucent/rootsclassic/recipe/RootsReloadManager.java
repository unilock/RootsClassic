package elucent.rootsclassic.recipe;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.mutation.MutagenManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class RootsReloadManager implements ResourceManagerReloadListener, IdentifiableResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(Const.MODID, "mutagen_reload_listener");
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        MutagenManager.reload();
    }

    public static void onAddReloadListeners() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new RootsReloadManager());
    }
}
