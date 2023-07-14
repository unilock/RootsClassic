package elucent.rootsclassic.compat.rei.display;

import elucent.rootsclassic.compat.rei.REIPlugin;
import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.ritual.rituals.RitualCrafting;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class RitualDisplay implements Display {
    private final List<EntryIngredient> inputs, output;
    private final List<ItemStack> incenses;
    private final List<BlockPos> positionsRelative;
    private final List<Block> blocks;
    public RitualDisplay(RitualBase ritual) {
        this.inputs = List.of(EntryIngredients.ofItemStacks(ritual.getIngredients()));
        if (ritual instanceof RitualCrafting ritualCrafting)
            output = List.of(EntryIngredients.of(ritualCrafting.result));
        else
            output = List.of(EntryIngredients.of(ItemStack.EMPTY));
        this.incenses = ritual.getIncenses();
        this.positionsRelative = ritual.getPositionsRelative();
        this.blocks = ritual.getBlocks();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return this.inputs;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return this.output;
    }

    @Override
    public CategoryIdentifier<RitualDisplay> getCategoryIdentifier() {
        return REIPlugin.RITUAL_TYPE;
    }

    public List<ItemStack> getIncenses() {
        return this.incenses;
    }

    public List<BlockPos> getPositionsRelative() {
        return this.positionsRelative;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
