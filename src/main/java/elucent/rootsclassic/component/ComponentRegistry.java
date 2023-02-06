package elucent.rootsclassic.component;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.components.*;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;

public class ComponentRegistry {
    public static final LazyRegistrar<ComponentBase> COMPONENTS = LazyRegistrar.create(ComponentBaseRegistry.registryLocation, Const.MODID);

    public static final RegistryObject<ComponentBase> ROSE_BUSH = COMPONENTS.register("rose_bush", () ->
            new ComponentRose().setPrimaryColor(192, 0, 72).setSecondaryColor(0, 200, 48).setTextColor(ChatFormatting.GREEN));

    public static final RegistryObject<ComponentBase> DANDELION = COMPONENTS.register("dandelion", () ->
            new ComponentDandelion().setPrimaryColor(255, 217, 102).setSecondaryColor(240, 159, 10).setTextColor(ChatFormatting.YELLOW));

    public static final RegistryObject<ComponentBase> CHORUS = COMPONENTS.register("chorus", () ->
            new ComponentChorus().setPrimaryColor(95, 57, 95).setSecondaryColor(225, 215, 225).setTextColor(ChatFormatting.DARK_PURPLE));

    public static final RegistryObject<ComponentBase> NETHER_WART = COMPONENTS.register("nether_wart", () ->
            new ComponentNetherWart().setPrimaryColor(255, 76, 36).setSecondaryColor(255, 174, 0).setTextColor(ChatFormatting.GOLD));

    public static final RegistryObject<ComponentBase> PEONY = COMPONENTS.register("peony", () ->
            new ComponentPeony().setPrimaryColor(255, 102, 178).setSecondaryColor(255, 51, 153).setTextColor(ChatFormatting.LIGHT_PURPLE));

    public static final RegistryObject<ComponentBase> SUNFLOWER = COMPONENTS.register("sunflower", () ->
            new ComponentSunflower().setPrimaryColor(255, 255, 128).setSecondaryColor(255, 255, 255).setTextColor(ChatFormatting.WHITE));

    public static final RegistryObject<ComponentBase> LILAC = COMPONENTS.register("lilac", () ->
            new ComponentLilac().setPrimaryColor(112, 80, 112).setSecondaryColor(0, 112, 24).setTextColor(ChatFormatting.GREEN));

    public static final RegistryObject<ComponentBase> AZURE_BLUET = COMPONENTS.register("azure_bluet", () ->
            new ComponentAzureBluet().setPrimaryColor(240, 240, 255).setSecondaryColor(86, 86, 96).setTextColor(ChatFormatting.GRAY));

    public static final RegistryObject<ComponentBase> ALLIUM = COMPONENTS.register("allium", () ->
            new ComponentAllium().setPrimaryColor(255, 202, 255).setSecondaryColor(51, 30, 50).setTextColor(ChatFormatting.DARK_PURPLE));

    public static final RegistryObject<ComponentBase> WHITE_TULIP = COMPONENTS.register("white_tulip", () ->
            new ComponentWhiteTulip().setPrimaryColor(255, 255, 255).setSecondaryColor(136, 252, 255).setTextColor(ChatFormatting.AQUA));

    public static final RegistryObject<ComponentBase> RED_TULIP = COMPONENTS.register("red_tulip", () ->
            new ComponentRedTulip().setPrimaryColor(128, 16, 16).setSecondaryColor(128, 16, 64).setTextColor(ChatFormatting.DARK_RED));

    public static final RegistryObject<ComponentBase> POPPY = COMPONENTS.register("poppy", () ->
            new ComponentPoppy().setPrimaryColor(255, 0, 0).setSecondaryColor(50, 50, 50).setTextColor(ChatFormatting.RED));

    public static final RegistryObject<ComponentBase> BLUE_ORCHID = COMPONENTS.register("blue_orchid", () ->
            new ComponentBlueOrchid().setPrimaryColor(68, 39, 26).setSecondaryColor(162, 153, 150).setTextColor(ChatFormatting.GRAY));

    public static final RegistryObject<ComponentBase> POISONOUS_POTATO = COMPONENTS.register("poisonous_potato", () ->
            new ComponentPoisonousPotato().setPrimaryColor(172, 255, 81).setSecondaryColor(81, 181, 255).setTextColor(ChatFormatting.YELLOW));

    public static final RegistryObject<ComponentBase> ORANGE_TULIP = COMPONENTS.register("orange_tulip", () ->
            new ComponentOrangeTulip().setPrimaryColor(255, 181, 70).setSecondaryColor(255, 255, 0).setTextColor(ChatFormatting.GOLD));

    public static final RegistryObject<ComponentBase> PINK_TULIP = COMPONENTS.register("pink_tulip", () ->
            new ComponentPinkTulip().setPrimaryColor(255, 0, 51).setSecondaryColor(255, 0, 249).setTextColor(ChatFormatting.LIGHT_PURPLE));

    public static final RegistryObject<ComponentBase> OXEYE_DAISY = COMPONENTS.register("oxeye_daisy", () ->
            new ComponentOxeyeDaisy().setPrimaryColor(255, 254, 206).setSecondaryColor(52, 0, 74).setTextColor(ChatFormatting.WHITE));

    public static final RegistryObject<ComponentBase> LILY_PAD = COMPONENTS.register("lily_pad", () ->
            new ComponentLilyPad().setPrimaryColor(36, 255, 167).setSecondaryColor(8, 0, 255).setTextColor(ChatFormatting.BLUE));

    public static final RegistryObject<ComponentBase> APPLE = COMPONENTS.register("apple", () ->
            new ComponentApple().setPrimaryColor(255, 43, 89).setSecondaryColor(255, 43, 43).setTextColor(ChatFormatting.DARK_RED));

    public static final RegistryObject<ComponentBase> MIDNIGHT_BLOOM = COMPONENTS.register("midnight_bloom", () ->
            new ComponentMidnightBloom().setPrimaryColor(12, 6, 36).setSecondaryColor(18, 18, 18).setTextColor(ChatFormatting.DARK_PURPLE));

    public static final RegistryObject<ComponentBase> FLARE_ORCHID = COMPONENTS.register("flare_orchid", () ->
            new ComponentFlareOrchid().setPrimaryColor(255, 60, 18).setSecondaryColor(255, 60, 18).setTextColor(ChatFormatting.RED));

    public static final RegistryObject<ComponentBase> RADIANT_DAISY = COMPONENTS.register("radiant_daisy", () ->
            new ComponentRadiantDaisy().setPrimaryColor(255, 255, 255).setSecondaryColor(255, 255, 255).setTextColor(ChatFormatting.WHITE));

}
