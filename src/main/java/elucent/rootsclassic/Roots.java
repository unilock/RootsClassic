package elucent.rootsclassic;

import com.mojang.logging.LogUtils;
import elucent.rootsclassic.component.ComponentRegistry;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.event.ComponentSpellsEvent;
import elucent.rootsclassic.lootmodifiers.DropModifier;
import elucent.rootsclassic.recipe.RootsReloadManager;
import elucent.rootsclassic.registry.ParticleRegistry;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

public class Roots implements ModInitializer {

	public static final Logger LOGGER = LogUtils.getLogger();

	@Override
	public void onInitialize() {
		ForgeConfigRegistry.INSTANCE.register(Const.MODID, ModConfig.Type.CLIENT, RootsConfig.clientSpec);
		ForgeConfigRegistry.INSTANCE.register(Const.MODID, ModConfig.Type.COMMON, RootsConfig.commonSpec);
		RootsConfig.registerEvents();
		RootsRegistry.BLOCKS.register();
		RootsRegistry.ITEMS.register();
		RootsRegistry.BLOCK_ENTITY_TYPES.register();
		RootsRegistry.CREATIVE_MODE_TABS.register();
		RootsEntities.ENTITY_TYPES.register();
		RitualRegistry.RITUALS.register();
		ComponentRegistry.COMPONENTS.register();
		RootsRecipes.RECIPE_SERIALIZERS.register();
		RootsRecipes.RECIPE_TYPES.register();
		DropModifier.GLM.register();
		ParticleRegistry.PARTICLE_TYPES.register();
		RootsReloadManager.onAddReloadListeners();
		ComponentSpellsEvent.registerEvents();
		RootsEntities.registerEntityAttributes();
		setup();
	}

	private void setup() {
		RootsRegistry.registerStats();
		RootsEntities.registerSpawnPlacement();
	}
}
