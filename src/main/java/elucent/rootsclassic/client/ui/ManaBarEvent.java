package elucent.rootsclassic.client.ui;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.capability.IManaCapability;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.client.ClientInfo;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.item.IManaRelatedItem;
import io.github.fabricators_of_create.porting_lib.event.client.OverlayRenderCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;

public class ManaBarEvent {
    private static void drawQuad(BufferBuilder vertexConsumer, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int minU, int minV, int maxU, int maxV) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        vertexConsumer.vertex(x1 + 0.0F, y1 + 0.0F, 0).uv((minU) * f, (minV + maxV) * f1).endVertex();
        vertexConsumer.vertex(x2 + 0.0F, y2 + 0.0F, 0).uv((minU + maxU) * f, (minV + maxV) * f1).endVertex();
        vertexConsumer.vertex(x3 + 0.0F, y3 + 0.0F, 0).uv((minU + maxU) * f, (minV) * f1).endVertex();
        vertexConsumer.vertex(x4 + 0.0F, y4 + 0.0F, 0).uv((minU) * f, (minV) * f1).endVertex();
    }

    public static void onGameOverlayRender() {
        OverlayRenderCallback.EVENT.register((poseStack, v, window, types) -> {
            Player player = Minecraft.getInstance().player;
            boolean showBar = player.getMainHandItem().getItem() instanceof IManaRelatedItem || player.getOffhandItem().getItem() instanceof IManaRelatedItem;
            IManaCapability capability = RootsCapabilityManager.MANA_CAPABILITY.maybeGet(player).orElse(null);
            if(!showBar || capability == null) {
                return true;
            }
            if(capability.getMaxMana() > 0) {
                drawManaBar(player, capability, poseStack, window);
            }
            return true;
        });
    }

    private static void drawManaBar(Player player, IManaCapability capability, PoseStack poseStack, Window window) {
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();
        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, Const.manaBar);
        Tesselator tess = Tesselator.getInstance();
        BufferBuilder b = tess.getBuilder();
        int w = window.getGuiScaledWidth();
        int h = window.getGuiScaledHeight();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int manaNumber = Math.round(capability.getMana());
        int maxManaNumber = Math.round(capability.getMaxMana());
        int offsetX = 0;
        b.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        int manaBarOffset = RootsConfig.CLIENT.manaBarOffset.get();
        while (maxManaNumber > 0) {
            drawQuad(b, w / 2 + 10 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - manaBarOffset, w / 2 + 10 + offsetX, h - manaBarOffset, 0, 0, 9, 9);
            if (maxManaNumber > 4) {
                maxManaNumber -= 4;
                offsetX += 8;
            } else {
                maxManaNumber = 0;
            }
        }
        offsetX = 0;
        while (manaNumber > 0) {
            if (manaNumber > 4) {
                drawQuad(b, w / 2 + 10 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - manaBarOffset, w / 2 + 10 + offsetX, h - manaBarOffset, 0, 16, 9, 9);
                manaNumber -= 4;
                offsetX += 8;
            } else {
                if (manaNumber == 4) {
                    drawQuad(b, w / 2 + 10 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - manaBarOffset, w / 2 + 10 + offsetX, h - manaBarOffset, 0, 16, 9, 9);
                }
                if (manaNumber == 3) {
                    drawQuad(b, w / 2 + 10 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - manaBarOffset, w / 2 + 10 + offsetX, h - manaBarOffset, 16, 16, 9, 9);
                }
                if (manaNumber == 2) {
                    drawQuad(b, w / 2 + 10 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - manaBarOffset, w / 2 + 10 + offsetX, h - manaBarOffset, 32, 16, 9, 9);
                }
                if (manaNumber == 1) {
                    drawQuad(b, w / 2 + 10 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - (manaBarOffset - 9), w / 2 + 19 + offsetX, h - manaBarOffset, w / 2 + 10 + offsetX, h - manaBarOffset, 48, 16, 9, 9);
                }
                manaNumber = 0;
            }
        }
        tess.end();
        RenderSystem.disableBlend();
        poseStack.popPose();
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
    }

    public static void clientEndTick() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientInfo.ticksInGame++;
        });
    }
}
