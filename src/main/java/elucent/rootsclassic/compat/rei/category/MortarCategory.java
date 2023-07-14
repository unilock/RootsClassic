package elucent.rootsclassic.compat.rei.category;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.compat.rei.REIPlugin;
import elucent.rootsclassic.compat.rei.display.MortarDisplay;
import elucent.rootsclassic.registry.RootsRegistry;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class MortarCategory implements DisplayCategory<MortarDisplay> {

    private final static ResourceLocation backgroundLocation = new ResourceLocation(Const.MODID, "textures/gui/tabletmortar.png");
    private final Renderer icon;
    private final Component localizedName;

    public MortarCategory() {
        this.icon = EntryStacks.of(RootsRegistry.MORTAR.get());
        this.localizedName = Component.translatable("rootsclassic.gui.jei.category.mortar");
    }

    @Override
    public CategoryIdentifier<MortarDisplay> getCategoryIdentifier() {
        return REIPlugin.MORTAR_TYPE;
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
    public int getDisplayHeight() {
        return 53;
    }

    @Override
    public List<Widget> setupDisplay(MortarDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(backgroundLocation, bounds.getX() + 4, bounds.getY() + 4, 21, 30, 142, 45));
        for (int i = 0; i < display.getInputEntries().size(); i++) {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 7 + (i * 16), bounds.getY() + 30)).markInput().disableBackground().entry(display.getInputEntries().get(i).get(0)));
        }
        widgets.add(Widgets.createSlot(new Point(bounds.getX() + 127, bounds.getY() + 30)).markOutput().disableBackground().entries(display.getOutputEntries().get(0)));
        return widgets;
    }
}
