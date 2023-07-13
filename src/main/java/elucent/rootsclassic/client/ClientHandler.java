package elucent.rootsclassic.client;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.model.SylvanArmorModel;
import elucent.rootsclassic.client.model.WildwoodArmorModel;
import elucent.rootsclassic.client.particles.*;
import elucent.rootsclassic.client.renderer.block.AltarBER;
import elucent.rootsclassic.client.renderer.block.BrazierBER;
import elucent.rootsclassic.client.renderer.block.ImbuerBER;
import elucent.rootsclassic.client.renderer.block.MortarBER;
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
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.InventoryMenu;

public class ClientHandler {
    public static final ModelLayerLocation SYLVAN_ARMOR = new ModelLayerLocation(new ResourceLocation(Const.MODID, "main"), "sylvan_armor");
    public static final ModelLayerLocation WILDWOOD_ARMOR = new ModelLayerLocation(new ResourceLocation(Const.MODID, "main"), "wildwood_armor");
    private static SylvanArmorModel sylvanHeadModel;
    private static SylvanArmorModel sylvanChestModel;
    private static SylvanArmorModel sylvanLegModel;
    private static SylvanArmorModel sylvanBootModel;
    private static WildwoodArmorModel wildHeadModel;
    private static WildwoodArmorModel wildChestModel;
    private static WildwoodArmorModel wildLegModel;
    private static WildwoodArmorModel wildBootModel;
    private static final ResourceLocation sylvanTexture = new ResourceLocation(Const.MODID, "textures/models/armor/sylvan.png");
    private static final ResourceLocation woodTexture = new ResourceLocation(Const.MODID, "textures/models/armor/wildwood.png");

    public static void onClientSetup() {
        ItemProperties.register(RootsRegistry.STAFF.get(), new ResourceLocation("imbued"), ((itemStack, clientLevel, livingEntity, i) ->
                itemStack.getTag() != null && itemStack.getTag().contains(Const.NBT_EFFECT) ? 1.0f : 0.0f)
        );
    }

    public static void registerEntityRenders() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), RootsRegistry.FLARE_ORCHID.get(), RootsRegistry.MIDNIGHT_BLOOM.get(), RootsRegistry.RADIANT_DAISY.get(), RootsRegistry.ALTAR.get());

        BlockEntityRenderers.register(RootsRegistry.MORTAR_TILE.get(), MortarBER::new);
        BlockEntityRenderers.register(RootsRegistry.IMBUER_TILE.get(), ImbuerBER::new);
        BlockEntityRenderers.register(RootsRegistry.ALTAR_TILE.get(), AltarBER::new);
        BlockEntityRenderers.register(RootsRegistry.BRAZIER_TILE.get(), BrazierBER::new);

        EntityRendererRegistry.register(RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonRenderer::new);
        EntityRendererRegistry.register(RootsEntities.ENTITY_ACCELERATOR.get(), AcceleratorRenderer::new);
        EntityRendererRegistry.register(RootsEntities.TILE_ACCELERATOR.get(), AcceleratorRenderer::new);
    }

    public static void registerLayerDefinitions() {
        EntityModelLayerRegistry.registerModelLayer(SYLVAN_ARMOR, SylvanArmorModel::createArmorDefinition);
        EntityModelLayerRegistry.registerModelLayer(WILDWOOD_ARMOR, WildwoodArmorModel::createArmorDefinition);

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            if(sylvanHeadModel == null) {
                sylvanHeadModel = new SylvanArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(SYLVAN_ARMOR), slot);
            }

            contextModel.copyPropertiesTo(sylvanHeadModel);
            sylvanHeadModel.setAllVisible(false);
            sylvanHeadModel.head.visible = slot == EquipmentSlot.HEAD;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, sylvanHeadModel, sylvanTexture);
        }, RootsRegistry.SYLVAN_HOOD.get());

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            if(sylvanChestModel == null) {
                sylvanChestModel = new SylvanArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(SYLVAN_ARMOR), slot);
            }

            contextModel.copyPropertiesTo(sylvanChestModel);
            sylvanChestModel.setAllVisible(false);
            sylvanChestModel.body.visible = slot == EquipmentSlot.CHEST;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, sylvanChestModel, sylvanTexture);
        }, RootsRegistry.SYLVAN_ROBE.get());

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            if(sylvanLegModel == null) {
                sylvanLegModel = new SylvanArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(SYLVAN_ARMOR), slot);
            }

            contextModel.copyPropertiesTo(sylvanLegModel);
            sylvanLegModel.setAllVisible(false);
            sylvanLegModel.leftLeg.visible = slot == EquipmentSlot.LEGS;
            sylvanLegModel.rightLeg.visible = slot == EquipmentSlot.LEGS;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, sylvanLegModel, sylvanTexture);
        }, RootsRegistry.SYLVAN_TUNIC.get());

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            if(sylvanBootModel == null) {
                sylvanBootModel = new SylvanArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(SYLVAN_ARMOR), slot);
            }

            contextModel.copyPropertiesTo(sylvanBootModel);
            sylvanBootModel.setAllVisible(false);
            sylvanBootModel.leftFoot.visible = slot == EquipmentSlot.FEET;
            sylvanBootModel.rightFoot.visible = slot == EquipmentSlot.FEET;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, sylvanBootModel, sylvanTexture);
        }, RootsRegistry.SYLVAN_BOOTS.get());

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            if(wildHeadModel == null) {
                wildHeadModel = new WildwoodArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(WILDWOOD_ARMOR), slot);
            }

            contextModel.copyPropertiesTo(wildHeadModel);
            wildHeadModel.setAllVisible(false);
            wildHeadModel.head.visible = slot == EquipmentSlot.HEAD;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, wildHeadModel, woodTexture);
        }, RootsRegistry.WILDWOOD_MASK.get());

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            if(wildChestModel == null) {
                wildChestModel = new WildwoodArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(WILDWOOD_ARMOR), slot);
            }
            contextModel.copyPropertiesTo(wildChestModel);
            wildChestModel.setAllVisible(false);
            wildChestModel.body.visible = slot == EquipmentSlot.CHEST;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, wildChestModel, woodTexture);
        }, RootsRegistry.WILDWOOD_PLATE.get());

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            if(wildLegModel == null) {
                wildLegModel = new WildwoodArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(WILDWOOD_ARMOR), slot);
            }
            contextModel.copyPropertiesTo(wildLegModel);
            wildLegModel.setAllVisible(false);
            wildLegModel.leftLeg.visible = slot == EquipmentSlot.LEGS;
            wildLegModel.rightLeg.visible = slot == EquipmentSlot.LEGS;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, wildLegModel, woodTexture);
        }, RootsRegistry.WILDWOOD_LEGGINGS.get());

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            if(wildBootModel == null) {
                wildBootModel = new WildwoodArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(WILDWOOD_ARMOR), slot);
            }
            contextModel.copyPropertiesTo(wildBootModel);
            wildBootModel.setAllVisible(false);
            wildBootModel.leftFoot.visible = slot == EquipmentSlot.FEET;
            wildBootModel.rightFoot.visible = slot == EquipmentSlot.FEET;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, wildBootModel, woodTexture);
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
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_TYPE.get(), MagicParticleData::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_AURA_TYPE.get(), MagicAuraParticleData::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_ALTAR_TYPE.get(), MagicAltarParticleData::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_ALTAR_LINE_TYPE.get(), MagicAltarLineParticleData::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_LINE_TYPE.get(), MagicLineParticleData::new);
    }
}
