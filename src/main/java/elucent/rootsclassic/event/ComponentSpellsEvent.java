package elucent.rootsclassic.event;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.RootsUtil;
import io.github.fabricators_of_create.porting_lib.event.common.LivingEntityEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ComponentSpellsEvent {
    public static int TICKS_PER_MANA_REGEN = 5;

    public static void registerEvents() {
        onLivingTick();
    }

    private static void onLivingTick() {
        LivingEntityEvents.TICK.register(livingEntity -> {
            if(livingEntity instanceof Player) {
                // armor regen if full set
                wildwoodArmorRegenFullset((Player)livingEntity);
                tickManaRegen(livingEntity);
            }
            tickSkipMovementCurse(livingEntity);
        });
    }

    private static void tickSkipMovementCurse(LivingEntity entity) {
        CompoundTag persistentData = entity.getExtraCustomData();
        if (persistentData.contains(Const.NBT_TRACK_TICKS) && persistentData.contains(Const.NBT_SKIP_TICKS)) {
            int skipTicks = persistentData.getInt(Const.NBT_SKIP_TICKS);
            if (skipTicks > 0) {
                persistentData.putInt(Const.NBT_SKIP_TICKS, skipTicks - 1);
                if (skipTicks <= 0) {
                    persistentData.remove(Const.NBT_SKIP_TICKS);
                    RootsUtil.decrementTickTracking(entity);
                }
            }
        }
    }

    private static void tickManaRegen(LivingEntity entity) {
        if (entity.tickCount % TICKS_PER_MANA_REGEN == 0) {
            RootsCapabilityManager.MANA_CAPABILITY.maybeGet(entity).ifPresent(cap -> cap.setMana(cap.getMana() + 1.0f));
        }
    }

    private static void wildwoodArmorRegenFullset(Player entity) {
        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);
        if (head.getItem() == RootsRegistry.WILDWOOD_MASK.get() &&
                chest.getItem() == RootsRegistry.WILDWOOD_PLATE.get() &&
                legs.getItem() == RootsRegistry.WILDWOOD_LEGGINGS.get() &&
                feet.getItem() == RootsRegistry.WILDWOOD_BOOTS.get()) {
            //
            if (entity.level.random.nextDouble() < 0.02 && entity.getHealth() < entity.getMaxHealth()) {
                entity.heal(1);//1 half heart
            }
        }
    }
}
