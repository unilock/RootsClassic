package elucent.rootsclassic.util;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.minecraft.world.item.ItemStack;

public class InventoryUtil {
    public static int getFirstEmptyStack(ItemStackHandler itemHandler) {
        if (itemHandler == null) return -1;
        for (int i = 0; i < itemHandler.getSlots().size(); i++) {
            if (itemHandler.getStackInSlot(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isFull(ItemStackHandler itemHandler) {
        if (itemHandler == null) return true;
        for (int i = 0; i < itemHandler.getSlots().size(); i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (stack.isEmpty() || (stack.getCount() < stack.getMaxStackSize())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(ItemStackHandler itemHandler) {
        if (itemHandler == null) return true;
        for (int i = 0; i < itemHandler.getSlots().size(); i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static void clearInventory(ItemStackHandler itemHandler) {
        if (itemHandler == null) return;
        for (int i = 0; i < itemHandler.getSlots().size(); i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                stack.shrink(1);
            }
        }
    }

    public static ItemStack getLastStack(ItemStackHandler itemHandler) {
        if (itemHandler == null) return ItemStack.EMPTY;
        for (int i = itemHandler.getSlots().size() - 1; i >= 0; i--) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public static CustomInventory createIInventory(ItemStackHandler itemHandler) {
        if (itemHandler == null) return null;
        CustomInventory inventory = new CustomInventory(itemHandler.getSlots().size());
        for (int i = 0; i < itemHandler.getSlots().size(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return inventory;
    }
}
