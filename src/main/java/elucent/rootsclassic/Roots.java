package elucent.rootsclassic;

import com.mojang.logging.LogUtils;
import elucent.rootsclassic.component.ComponentRegistry;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.ParticleRegistry;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

public class Roots implements ModInitializer {

	public static final CreativeModeTab tab = FabricItemGroupBuilder.build(new ResourceLocation(Const.MODID, Const.MODID), () -> new ItemStack(Items.BIRCH_SAPLING));
	public static final Logger LOGGER = LogUtils.getLogger();

	@Override
	public void onInitialize() {
		ModLoadingContext.registerConfig(Const.MODID, ModConfig.Type.CLIENT, RootsConfig.clientSpec);
		ModLoadingContext.registerConfig(Const.MODID, ModConfig.Type.COMMON, RootsConfig.commonSpec);
		RootsConfig.registerEvents();
		setup();
		RootsRegistry.BLOCKS.register();
		RootsRegistry.ITEMS.register();
		RootsRegistry.BLOCK_ENTITY_TYPES.register();
		RootsEntities.ENTITY_TYPES.register();
		ComponentRegistry.COMPONENTS.register();
		ParticleRegistry.PARTICLE_TYPES.register();
	}

	private void setup() {
		RootsRegistry.registerCompostables();
		RootsEntities.registerSpawnPlacement();
	}
}
