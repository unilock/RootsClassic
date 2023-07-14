package elucent.rootsclassic.compat.rei.category;

import com.mojang.blaze3d.systems.RenderSystem;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.compat.rei.REIPlugin;
import elucent.rootsclassic.compat.rei.display.RitualDisplay;
import elucent.rootsclassic.registry.RootsRegistry;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class RitualCategory implements DisplayCategory<RitualDisplay> {

    private final static ResourceLocation backgroundLocation = new ResourceLocation(Const.MODID, "textures/gui/jei/compat.png");
    private final static ResourceLocation location = new ResourceLocation(Const.MODID, "textures/gui/tabletaltar.png");
    private final Renderer icon;
    private final Component localizedName;

    public RitualCategory() {
        this.icon = EntryStacks.of(RootsRegistry.ALTAR.get());
        this.localizedName = Component.translatable("rootsclassic.gui.jei.category.ritual");
    }

    @Override
    public CategoryIdentifier<RitualDisplay> getCategoryIdentifier() {
        return REIPlugin.RITUAL_TYPE;
    }

    @Override
    public Component getTitle() {
        return this.localizedName;
    }

    @Override
    public Renderer getIcon() {
        return this.icon;
    }

    @Override
    public int getDisplayWidth(RitualDisplay display) {
        return 102;
    }

    @Override
    public int getDisplayHeight() {
        return 108;
    }

    @Override
    public List<Widget> setupDisplay(RitualDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(backgroundLocation, bounds.getX() + 4, bounds.getY() + 4, 0, 0, 94, 100));

        for (int i = 0; i < display.getInputEntries().get(0).size(); i++) {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 19 + (i * 24), bounds.getY() + 7)).markInput().entry(display.getInputEntries().get(0).get(i)));
        }
        for (int i = 0; i < display.getIncenses().size(); i++) {
            ItemStack stack = display.getIncenses().get(i);
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 32 + (i * 16), bounds.getY() + 31)).entry(EntryStacks.of(stack)));
        }

        if (!display.getOutputEntries().get(0).get(0).isEmpty()) {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 71, bounds.getY() + 71)).markOutput().entries(display.getOutputEntries().get(0)));
        }

        widgets.add(Widgets.createTexturedWidget(location, bounds.getX() + 16, bounds.getY() + 4, 61, 53, 70, 22));
        widgets.add(Widgets.createTexturedWidget(location, bounds.getX() + 4, bounds.getY() + 28, 49, 85, 94, 22));
        widgets.add(Widgets.createTexturedWidget(location, bounds.getX() + 68, bounds.getY() + 68, 61, 53, 22, 22));


        Widget grid = Widgets.createTexturedWidget(location, 24, 104, 50, 118, 93, 93);
        Widget stone = Widgets.createTexturedWidget(location, 67, 139, 192, 32, 16, 16);

        widgets.add(Widgets.createDrawableWidget((helper, stack, mouseX, mouseY, delta) -> {
            stack.pushPose();
            stack.translate(bounds.getX(), bounds.getY() + 4, 0);
            stack.scale(0.5F, 0.5F, 1);
            grid.render(stack, mouseX, mouseY, delta);

            int basePosX = 63;
            int basePosY = 135;
            final List<Block> blocks = display.getBlocks();
            final List<BlockPos> relativePosition = display.getPositionsRelative();
            for (int i = 0; i < blocks.size(); i++) {
                int xShift = 0;
                int yShift = 0;
                stone.render(stack, mouseX, mouseY, delta);
                if (blocks.get(i).equals(RootsRegistry.MUNDANE_STANDING_STONE.get())) {
                    xShift = 8 * (int) relativePosition.get(i).getX();
                    yShift = 8 * (int) relativePosition.get(i).getZ();
                    RenderSystem.setShaderTexture(0, location);
                    helper.blit(stack, basePosX + xShift + 4, basePosY + yShift + 4, 192, 48, 16, 16);
                }
                if (blocks.get(i).equals(RootsRegistry.ATTUNED_STANDING_STONE.get())) {
                    xShift = 8 * (int) relativePosition.get(i).getX();
                    yShift = 8 * (int) relativePosition.get(i).getZ();
                    RenderSystem.setShaderTexture(0, location);
                    helper.blit(stack, basePosX + xShift + 4, basePosY + yShift + 4, 192, 64, 16, 16);
                }
            }
            stack.popPose();
        }));

        return widgets;
    }
}