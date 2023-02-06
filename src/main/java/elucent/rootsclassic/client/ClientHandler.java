package elucent.rootsclassic.client;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.model.SylvanArmorModel;
import elucent.rootsclassic.client.model.WildwoodArmorModel;
import elucent.rootsclassic.client.particles.*;
import elucent.rootsclassic.client.renderer.entity.AcceleratorRenderer;
import elucent.rootsclassic.client.renderer.entity.PhantomSkeletonRenderer;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.item.CrystalStaffItem;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.registry.ParticleRegistry;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.RootsUtil;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.InventoryMenu;

public class ClientHandler {
    public static final ModelLayerLocation SYLVAN_ARMOR = new ModelLayerLocation(new ResourceLocation(Const.MODID, "main"), "sylvan_armor");
    public static final ModelLayerLocation WILDWOOD_ARMOR = new ModelLayerLocation(new ResourceLocation(Const.MODID, "main"), "wildwood_armor");
    private static WildwoodArmorModel wildHeadModel;
    private static WildwoodArmorModel wildChestModel;
    private static WildwoodArmorModel wildLegModel;
    private static WildwoodArmorModel wildBootModel;
    private static final ResourceLocation texture = new ResourceLocation(Const.MODID, "textures/models/armor/wildwood.png");

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
        EntityModelLayerRegistry.registerModelLayer(SYLVAN_ARMOR, SylvanArmorModel::createArmorDefinition);
        EntityModelLayerRegistry.registerModelLayer(WILDWOOD_ARMOR, WildwoodArmorModel::createArmorDefinition);

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            if(wildHeadModel == null) {
                wildHeadModel = new WildwoodArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(WILDWOOD_ARMOR), slot);
            }

            contextModel.copyPropertiesTo(wildHeadModel);
            wildHeadModel.setAllVisible(false);
            wildHeadModel.head.visible = slot == EquipmentSlot.HEAD;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, wildHeadModel, texture);
        }, RootsRegistry.WILDWOOD_MASK.get());

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            if(wildChestModel == null) {
                wildChestModel = new WildwoodArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(WILDWOOD_ARMOR), slot);
            }
            contextModel.copyPropertiesTo(wildChestModel);
            wildChestModel.setAllVisible(false);
            wildChestModel.body.visible = slot == EquipmentSlot.CHEST;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, wildChestModel, texture);
        }, RootsRegistry.WILDWOOD_PLATE.get());

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            if(wildLegModel == null) {
                wildLegModel = new WildwoodArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(WILDWOOD_ARMOR), slot);
            }
            contextModel.copyPropertiesTo(wildLegModel);
            wildLegModel.setAllVisible(false);
            wildLegModel.leftLeg.visible = slot == EquipmentSlot.LEGS;
            wildLegModel.rightLeg.visible = slot == EquipmentSlot.LEGS;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, wildLegModel, texture);
        }, RootsRegistry.WILDWOOD_LEGGINGS.get());

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            if(wildBootModel == null) {
                wildBootModel = new WildwoodArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(WILDWOOD_ARMOR), slot);
            }
            contextModel.copyPropertiesTo(wildBootModel);
            wildBootModel.setAllVisible(false);
            wildBootModel.leftFoot.visible = slot == EquipmentSlot.FEET;
            wildBootModel.rightFoot.visible = slot == EquipmentSlot.FEET;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, wildBootModel, texture);
        }, RootsRegistry.WILDWOOD_BOOTS.get());
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
