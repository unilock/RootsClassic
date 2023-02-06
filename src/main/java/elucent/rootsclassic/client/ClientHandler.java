package elucent.rootsclassic.client;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.particles.*;
import elucent.rootsclassic.client.renderer.entity.AcceleratorRenderer;
import elucent.rootsclassic.client.renderer.entity.PhantomSkeletonRenderer;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.entity.skeleton.PhantomSkeletonEntity;
import elucent.rootsclassic.item.CrystalStaffItem;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.registry.ParticleRegistry;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.RootsUtil;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class ClientHandler {
    public static final ModelLayerLocation SYLVAN_ARMOR = new ModelLayerLocation(new ResourceLocation(Const.MODID, "main"), "sylvan_armor");
    public static final ModelLayerLocation WILDWOOD_ARMOR = new ModelLayerLocation(new ResourceLocation(Const.MODID, "main"), "wildwood_armor");

    public static void onClientSetup() {
        ItemProperties.register(RootsRegistry.STAFF.get(), new ResourceLocation("imbued"), ((itemStack, clientLevel, livingEntity, i) ->
                itemStack.getTag() != null && itemStack.getTag().contains(Const.NBT_EFFECT) ? 1.0f : 0.0f)
        );
    }

    public static void registerEntityRenders() {
        EntityRendererRegistry.register(RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonRenderer::new);
        EntityRendererRegistry.register(RootsEntities.ENTITY_ACCELERATOR.get(), AcceleratorRenderer::new);
        EntityRendererRegistry.register(RootsEntities.TILE_ACCELERATOR.get(), AcceleratorRenderer::new);
    }

    public static void registerLayerDefinitions() {

    }

    public static void registerItemColors() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (stack.hasTag() && stack.getItem() instanceof StaffItem) {
                CompoundTag tag = stack.getTag();
                ResourceLocation compName = ResourceLocation.tryParse(tag.getString(Const.NBT_EFFECT));
                if (compName != null) {
                    ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get(compName);
                    if (comp != null) {
                        if (tintIndex == 2) {
                            return RootsUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
                        }
                        if (tintIndex == 1) {
                            return RootsUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
                        }
                    }
                }
            }
            return RootsUtil.intColor(255, 255, 255);
        }, RootsRegistry.STAFF.get());
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (stack.getItem() instanceof CrystalStaffItem && stack.hasTag()) {
                String effect = CrystalStaffItem.getEffect(stack);
                if (effect != null) {
                    ResourceLocation compName = ResourceLocation.tryParse(effect);
                    if (compName != null) {
                        ComponentBase comp = ComponentBaseRegistry.COMPONENTS.get(compName);
                        if (comp != null) {
                            if (tintIndex == 2) {
                                return RootsUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
                            }
                            if (tintIndex == 1) {
                                return RootsUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
                            }
                        }
                    }
                }
            }
            return RootsUtil.intColor(255, 255, 255);
        }, RootsRegistry.CRYSTAL_STAFF.get());
    }

    public static void registerParticleFactories() {
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((atlasTexture, registry) -> {
            registry.register(new ResourceLocation(Const.MODID, "particle/magic"));
        });

        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_TYPE.get(), MagicParticleData::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_AURA_TYPE.get(), MagicAuraParticleData::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_ALTAR_TYPE.get(), MagicAltarParticleData::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_ALTAR_LINE_TYPE.get(), MagicAltarLineParticleData::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_LINE_TYPE.get(), MagicLineParticleData::new);
    }
}
