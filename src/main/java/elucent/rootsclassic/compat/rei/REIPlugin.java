package elucent.rootsclassic.compat.rei;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.compat.rei.category.MortarCategory;
import elucent.rootsclassic.compat.rei.category.RitualCategory;
import elucent.rootsclassic.compat.rei.display.MortarDisplay;
import elucent.rootsclassic.compat.rei.display.RitualDisplay;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualRegistry;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class REIPlugin implements REIClientPlugin {
    public static final ResourceLocation PLUGIN_UID = new ResourceLocation(Const.MODID, "main");

    public static final ResourceLocation MORTAR = new ResourceLocation(Const.MODID, "mortar");
    public static final CategoryIdentifier<MortarDisplay> MORTAR_TYPE = CategoryIdentifier.of(Const.MODID, "mortar");

    public static final ResourceLocation RITUAL = new ResourceLocation(Const.MODID, "ritual");
    public static final CategoryIdentifier<RitualDisplay> RITUAL_TYPE = CategoryIdentifier.of(Const.MODID, "ritual");

    @Nullable
    private DisplayCategory<MortarDisplay> mortarCategory;
    @Nullable
    private DisplayCategory<RitualDisplay> ritualCategory;

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(
                mortarCategory = new MortarCategory(),
                ritualCategory = new RitualCategory()
        );

        registry.addWorkstations(MORTAR_TYPE, EntryIngredients.of(RootsRegistry.MORTAR.get()));
        registry.addWorkstations(RITUAL_TYPE, EntryIngredients.of(RootsRegistry.ALTAR.get()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(ComponentRecipe.class, MortarDisplay::new);
        RitualRegistry.RITUALS.getEntries().forEach(ritual -> registry.add(new RitualDisplay(ritual.get())));
        for (RegistryObject<Item> registryObject : RootsRegistry.ITEMS.getEntries()) {
            Item item = registryObject.get();
            if (item != null) {
                registry.add(DefaultInformationDisplay.createFromEntries(EntryIngredients.of(item), Component.empty()).line(Component.translatable(item.getDescriptionId() + ".guide")));
            }
        }
    }
}
