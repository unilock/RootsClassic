package elucent.rootsclassic.component;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.components.ComponentRose;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ComponentRegistry {
    public static final LazyRegistrar<ComponentBase> COMPONENTS = LazyRegistrar.create(ComponentBaseRegistry.registryLocation, Const.MODID);

    public static final RegistryObject<ComponentBase> ROSE_BUSH = COMPONENTS.register("rose_bush", () ->
            new ComponentRose().setPrimaryColor(192, 0, 72).setSecondaryColor(0, 200, 48).setTextColor(ChatFormatting.GREEN));
}
