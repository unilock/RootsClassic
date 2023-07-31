package elucent.rootsclassic.item;

import elucent.rootsclassic.util.RootsUtil;
import io.github.fabricators_of_create.porting_lib.item.ArmorTickListeningItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WildwoodArmorItem extends ArmorItem implements ArmorTickListeningItem {

    public WildwoodArmorItem(ArmorMaterial materialIn, ArmorItem.Type slot, Item.Properties builderIn) {
        super(materialIn, slot, builderIn);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level levelAccessor, Player player) {
        RootsUtil.randomlyRepair(levelAccessor.random, stack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level levelAccessor, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, levelAccessor, tooltip, flagIn);
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("rootsclassic.attribute.equipped").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(" ").append(Component.translatable("rootsclassic.attribute.increasedmanaregen")).withStyle(ChatFormatting.BLUE));
    }
}
