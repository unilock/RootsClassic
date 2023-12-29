package elucent.rootsclassic.event;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.RootsUtil;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ComponentSpellsEvent {
    public static int TICKS_PER_MANA_REGEN = 5;

    public static void registerEvents() {
        onLivingTick();
        onLivingDamage();
    }

    private static void onLivingTick() {
        LivingEntityEvents.TICK.register(livingEntity -> {
            if(livingEntity instanceof Player) {
                // armor regen if full set
                wildwoodArmorRegenFullset((Player)livingEntity);
                tickManaRegen(livingEntity);
            }
        });
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
            if (entity.level().random.nextDouble() < 0.02 && entity.getHealth() < entity.getMaxHealth()) {
                entity.heal(1);//1 half heart
            }
        }
    }

    /* TODO: Not porting yet because it's not actually used other then for the Phantom Skeleton which doesn't have the data added
    /* And no other mob uses this data
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        CompoundTag persistentData = event.getEntity().getPersistentData();
        if (persistentData.contains(Const.NBT_DONT_DROP)) {
            if (!persistentData.getBoolean(Const.NBT_DONT_DROP)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onLivingXP(LivingExperienceDropEvent event) {
        CompoundTag persistentData = event.getEntity().getPersistentData();
        if (persistentData.contains(Const.NBT_DONT_DROP)) {
            if (!persistentData.getBoolean(Const.NBT_DONT_DROP)) {
                event.setCanceled(true);
            }
        }
    }
     */

    private static void onLivingDamage() {
        LivingEntityEvents.HURT.register((source, damaged, amount) -> {
            CompoundTag persistentData = damaged.getCustomData();
            if (persistentData.contains(Const.NBT_VULN)) {
                amount *= (float) (1.0 + persistentData.getDouble(Const.NBT_VULN));
                persistentData.remove(Const.NBT_VULN);
            }
            if (persistentData.contains(Const.NBT_THORNS) && source.getEntity() instanceof LivingEntity) {
                source.getEntity().hurt(damaged.damageSources().cactus(), persistentData.getFloat(Const.NBT_THORNS));
                persistentData.remove(Const.NBT_THORNS);
                RootsUtil.decrementTickTracking(damaged);
            }
            if (damaged instanceof Player player) {
                if (!player.getInventory().getSelected().isEmpty() && player.getInventory().getSelected().getItem() == RootsRegistry.ENGRAVED_BLADE.get()) {
                    ItemStack sword = player.getInventory().getSelected();
                    if (sword.hasTag() && sword.getTag().contains("shadowstep")) {
                        int stepLvl = sword.getTag().getInt("shadowstep");
                        double chance = stepLvl * 12.5;
                        if (player.getCommandSenderWorld().random.nextInt(100) < chance) {
                            return 0;
                        }
                    }
                }
            }
            if (source.getEntity() instanceof Player) {
                if (!damaged.getCommandSenderWorld().isClientSide) {
                    Player player = (Player) source.getEntity();
                    if (!player.getInventory().getSelected().isEmpty() && player.getInventory().getSelected().getItem() == RootsRegistry.ENGRAVED_BLADE.get()) {
                        ItemStack sword = player.getInventory().getSelected();
                        if (sword.hasTag()) {
                            CompoundTag tag = sword.getTag();
                            if (tag.contains("aquatic")) {
                                int aquaLvl = tag.getInt("aquatic");
                                float amount2 = aquaLvl * 0.5f;
                                damaged.hurt(damaged.damageSources().drown(), amount2);
                            }
                            if ((tag.contains("holy")) && damaged.getMobType() == MobType.UNDEAD) {
                                int holyLvl = tag.getInt("holy");
                                amount += holyLvl * 1.5f;
                            }
                            if (tag.contains("spikes")) {
                                int spikeLvl = tag.getInt("spikes");
                                amount += spikeLvl;
                            }
                        }
                    }
                }
            }
            return amount;
        });
    }
}
