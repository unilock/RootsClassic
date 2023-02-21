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
import elucent.rootsclassic.ritual.RitualBaseRegistry;
import elucent.rootsclassic.ritual.RitualRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

public class Roots implements ModInitializer {

	public static final CreativeModeTab tab = FabricItemGroupBuilder.build(new ResourceLocation(Const.MODID, Const.MODID), () -> new ItemStack(RootsRegistry.SPELL_POWDER.get()));
	public static final Logger LOGGER = LogUtils.getLogger();

	@Override
	public void onInitialize() {
		ModLoadingContext.registerConfig(Const.MODID, ModConfig.Type.CLIENT, RootsConfig.clientSpec);
		ModLoadingContext.registerConfig(Const.MODID, ModConfig.Type.COMMON, RootsConfig.commonSpec);
		RootsConfig.registerEvents();
		RootsRegistry.BLOCKS.register();
		RootsRegistry.ITEMS.register();
		RootsRegistry.BLOCK_ENTITY_TYPES.register();
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
