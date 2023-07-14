package elucent.rootsclassic.compat.rei.display;

import elucent.rootsclassic.compat.rei.REIPlugin;
import elucent.rootsclassic.recipe.ComponentRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public class MortarDisplay implements Display {
    private final List<EntryIngredient> input, output;

    public MortarDisplay(ComponentRecipe recipe) {
        this.input = EntryIngredients.ofIngredients(recipe.getIngredients());
        this.output = List.of(EntryIngredients.of(recipe.assemble(null)));
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return this.input;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return this.output;
    }

    @Override
    public CategoryIdentifier<MortarDisplay> getCategoryIdentifier() {
        return REIPlugin.MORTAR_TYPE;
    }
}