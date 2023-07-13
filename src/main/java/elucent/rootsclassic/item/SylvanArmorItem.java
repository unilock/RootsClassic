package elucent.rootsclassic.item;

import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.util.RootsUtil;
import io.github.fabricators_of_create.porting_lib.item.ArmorTickListeningItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SylvanArmorItem extends ArmorItem implements ArmorTickListeningItem {

    public SylvanArmorItem(ArmorMaterial materialIn, ArmorItem.Type slot, Item.Properties builderIn) {
        super(materialIn, slot, builderIn);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level levelAccessor, Player player) {
        RootsUtil.randomlyRepair(levelAccessor.random, stack);
        if (levelAccessor.random.nextInt(40) == 0) {
            RootsCapabilityManager.MANA_CAPABILITY.maybeGet(player).ifPresent(cap -> {
                cap.setMana(cap.getMana() + 1.0f);
            });
        }
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
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("rootsclassic.attribute.fullset").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(" +1 ").append(Component.translatable("rootsclassic.attribute.potency")).withStyle(ChatFormatting.BLUE));
    }
}
