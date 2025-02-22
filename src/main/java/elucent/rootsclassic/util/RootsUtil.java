package elucent.rootsclassic.util;

import elucent.rootsclassic.Const;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RootsUtil {
    private static final Random random = new Random();

    public static double randomDouble(double min, double max) {
        double range = max - min;
        double scale = random.nextDouble() * range;
        return scale + min;
    }

    public static BlockPos getRayTrace(Level levelAccessor, LivingEntity livingEntity, int reachDistance) {
        double x = livingEntity.getX();
        double y = livingEntity.getY() + livingEntity.getEyeHeight();
        double z = livingEntity.getZ();
        for (int i = 0; i < reachDistance * 10.0; i++) {
            x += livingEntity.getLookAngle().x * 0.1;
            y += livingEntity.getLookAngle().y * 0.1;
            z += livingEntity.getLookAngle().z * 0.1;
            if (levelAccessor.getBlockState(BlockPos.containing(x, y, z)).getBlock() != Blocks.AIR) {
                return BlockPos.containing(x, y, z);
            }
        }
        return BlockPos.containing(x, y, z);
    }

    public static void addTickTracking(Entity entity) {
        if (entity.getCustomData().contains(Const.NBT_TRACK_TICKS)) {
            entity.getCustomData().putInt(Const.NBT_TRACK_TICKS, entity.getCustomData().getInt(Const.NBT_TRACK_TICKS) + 1);
        } else {
            entity.getCustomData().putInt(Const.NBT_TRACK_TICKS, 1);
        }
    }

    public static void decrementTickTracking(Entity entity) {
        if (entity.getCustomData().contains(Const.NBT_TRACK_TICKS)) {
            entity.getCustomData().putInt(Const.NBT_TRACK_TICKS, entity.getCustomData().getInt(Const.NBT_TRACK_TICKS) - 1);
            if (entity.getCustomData().getInt(Const.NBT_TRACK_TICKS) == 0) {
                entity.removeTag(Const.NBT_TRACK_TICKS);
            }
        }
    }

    public static Entity getRayTraceEntity(Level levelAccessor, LivingEntity livingEntity, int reachDistance) {
        double x = livingEntity.getX();
        double y = livingEntity.getY() + livingEntity.getEyeHeight();
        double z = livingEntity.getZ();
        for (int i = 0; i < reachDistance * 10.0; i++) {
            x += livingEntity.getLookAngle().x * 0.1;
            y += livingEntity.getLookAngle().y * 0.1;
            z += livingEntity.getLookAngle().z * 0.1;
            List<Entity> entities = levelAccessor.getEntitiesOfClass(Entity.class, new AABB(x - 0.1, y - 0.1, z - 0.1, x + 0.1, y + 0.1, z + 0.1));
            if (entities.size() > 0) {
                if (entities.get(0).getUUID() != livingEntity.getUUID()) {
                    return entities.get(0);
                }
            }
        }
        return null;
    }

    public static int intColor(int r, int g, int b) {
        return (r * 65536 + g * 256 + b);
    }

    public static boolean itemListMatchInventoryWithSize(Container i1, List<ItemStack> i2) {
        List<ItemStack> recipe = new ArrayList<>();
        for (int i = 0; i < i1.getContainerSize(); i++) {
            recipe.add(i1.getItem(i));
        }
        List<ItemStack> available = new ArrayList<>(i2);
        for (int i = 0; i < recipe.size(); i++) {
            if (recipe.get(i).isEmpty()) {
                recipe.remove(i);
                i--;
            }
        }
        for (int i = 0; i < available.size(); i++) {
            if (available.get(i).isEmpty()) {
                available.remove(i);
                i--;
            }
        }
        if (available.size() == recipe.size()) {
            for (ItemStack itemStack : available) {
                boolean endIteration = false;
                for (int i = 0; i < recipe.size() && !endIteration; i++) {
                    if (ItemStack.isSameItem(itemStack, recipe.get(i))) {
                        recipe.remove(i);
                        endIteration = true;
                    }
                }
            }
        }
        return recipe.size() == 0;
    }

    public static boolean itemListMatchInventory(Container i1, List<ItemStack> i2) {
        List<ItemStack> recipe = new ArrayList<>();
        for (int i = 0; i < i1.getContainerSize(); i++) {
            recipe.add(i1.getItem(i));
        }
        List<ItemStack> available = new ArrayList<>(i2);
        for (int i = 0; i < recipe.size(); i++) {
            if (recipe.get(i).isEmpty()) {
                recipe.remove(i);
                i--;
            }
        }
        for (int i = 0; i < available.size(); i++) {
            if (available.get(i).isEmpty()) {
                available.remove(i);
                i--;
            }
        }
        if (available.size() >= recipe.size()) {
            for (ItemStack itemStack : available) {
                boolean endIteration = false;
                for (int i = 0; i < recipe.size() && !endIteration; i++) {
                    if (ItemStack.isSameItem(itemStack, recipe.get(i))) {
                        recipe.remove(i);
                        endIteration = true;
                    }
                }
            }
        }
        return recipe.size() == 0;
    }

    public static boolean itemListMatchesIngredients(List<Ingredient> i1, List<ItemStack> i2) {
        List<Ingredient> recipe = new ArrayList<>(i1);
        List<ItemStack> available = new ArrayList<>(i2);
        for (int i = 0; i < recipe.size(); i++) {
            if (recipe.get(i).isEmpty()) {
                recipe.remove(i);
                i--;
            }
        }
        for (int i = 0; i < available.size(); i++) {
            if (available.get(i).isEmpty()) {
                available.remove(i);
                i--;
            }
        }
        if (available.size() >= recipe.size()) {
            for (ItemStack itemStack : available) {
                boolean endIteration = false;
                for (int i = 0; i < recipe.size() && !endIteration; i++) {
                    if (recipe.get(i).test(itemStack)) {
                        recipe.remove(i);
                        endIteration = true;
                    }
                }
            }
        }
        return recipe.size() == 0;
    }

    public static boolean itemListMatchesIngredientsWithSize(List<Ingredient> i1, List<ItemStack> i2) {
        List<Ingredient> recipe = new ArrayList<>(i1);
        List<ItemStack> available = new ArrayList<>(i2);
        for (int i = 0; i < recipe.size(); i++) {
            if (recipe.get(i).isEmpty()) {
                recipe.remove(i);
                i--;
            }
        }
        for (int i = 0; i < available.size(); i++) {
            if (available.get(i).isEmpty()) {
                available.remove(i);
                i--;
            }
        }
        if (available.size() == recipe.size()) {
            for (ItemStack itemStack : available) {
                boolean endIteration = false;
                for (int i = 0; i < recipe.size() && !endIteration; i++) {
                    if (recipe.get(i).test(itemStack)) {
                        recipe.remove(i);
                        endIteration = true;
                    }
                }
            }
        }
        return recipe.size() == 0;
    }

    public static boolean itemListsMatchWithSize(List<ItemStack> i1, List<ItemStack> i2) {
        List<ItemStack> recipe = new ArrayList<>(i1);
        List<ItemStack> available = new ArrayList<>(i2);
        for (int i = 0; i < recipe.size(); i++) {
            if (recipe.get(i).isEmpty()) {
                recipe.remove(i);
                i--;
            }
        }
        for (int i = 0; i < available.size(); i++) {
            if (available.get(i).isEmpty()) {
                available.remove(i);
                i--;
            }
        }
        if (available.size() == recipe.size()) {
            for (ItemStack itemStack : available) {
                boolean endIteration = false;
                for (int i = 0; i < recipe.size() && !endIteration; i++) {
                    if (ItemStack.isSameItem(itemStack, recipe.get(i))) {
                        recipe.remove(i);
                        endIteration = true;
                    }
                }
            }
        }
        return recipe.size() == 0;
    }

    public static boolean itemListsMatch(List<ItemStack> i1, List<ItemStack> i2) {
        List<ItemStack> recipe = new ArrayList<>(i1);
        List<ItemStack> available = new ArrayList<>(i2);
        for (int i = 0; i < recipe.size(); i++) {
            if (recipe.get(i).isEmpty()) {
                recipe.remove(i);
                i--;
            }
        }
        for (int i = 0; i < available.size(); i++) {
            if (available.get(i).isEmpty()) {
                available.remove(i);
                i--;
            }
        }
        if (available.size() >= recipe.size()) {
            for (ItemStack itemStack : available) {
                boolean endIteration = false;
                for (int i = 0; i < recipe.size() && !endIteration; i++) {
                    if (ItemStack.isSameItem(itemStack, recipe.get(i))) {
                        recipe.remove(i);
                        endIteration = true;
                    }
                }
            }
        }
        return recipe.size() == 0;
    }

    public static boolean ingredientListMatch(List<Ingredient> i1, List<Ingredient> i2) {
        List<Ingredient> recipe = new ArrayList<>(i1);
        List<Ingredient> available = new ArrayList<>(i2);
        for (int i = 0; i < recipe.size(); i++) {
            if (recipe.get(i).isEmpty()) {
                recipe.remove(i);
                i--;
            }
        }
        for (int i = 0; i < available.size(); i++) {
            if (available.get(i).isEmpty()) {
                available.remove(i);
                i--;
            }
        }
        if (available.size() >= recipe.size()) {
            for (Ingredient ingredient : available) {
                boolean endIteration = false;
                for (int i = 0; i < recipe.size() && !endIteration; i++) {
                    if (ItemStack.isSameItem(ingredient.getItems()[0], recipe.get(i).getItems()[0])) {
                        recipe.remove(i);
                        endIteration = true;
                    }
                }
            }
        }
        return recipe.size() == 0;
    }

    public static void randomlyRepair(RandomSource rnd, ItemStack stack) {
        if (stack.isDamaged() && rnd.nextInt(80) == 0) {
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }
}
