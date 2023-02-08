package elucent.rootsclassic.ritual;

import elucent.rootsclassic.Const;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceLocation;

public class RitualBaseRegistry {
    public static final ResourceLocation registryLocation = new ResourceLocation(Const.MODID, "ritual");
    public static final MappedRegistry<RitualBase> RITUALS = FabricRegistryBuilder.createSimple(RitualBase.class, registryLocation).buildAndRegister();
}
