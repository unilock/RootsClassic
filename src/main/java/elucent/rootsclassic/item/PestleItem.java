package elucent.rootsclassic.item;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class PestleItem extends Item {

    public PestleItem(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public Item getCraftingRemainingItem() {
        return this;
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }
}
