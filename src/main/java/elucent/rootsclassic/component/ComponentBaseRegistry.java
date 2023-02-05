package elucent.rootsclassic.component;

import elucent.rootsclassic.Const;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceLocation;

public class ComponentBaseRegistry {
    public static final ResourceLocation registryLocation = new ResourceLocation(Const.MODID, "component");

    public static final MappedRegistry<ComponentBase> COMPONENTS = FabricRegistryBuilder.createSimple(ComponentBase.class, registryLocation).buildAndRegister();
}
