package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ComponentWhiteTulip extends ComponentBase {

    public ComponentWhiteTulip() {
        super(Blocks.WHITE_TULIP, 10);
    }

    @Override
    public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL && casterEntity instanceof LivingEntity caster) {
            //   int damageDealt = 0;
            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(x - size, y - size, z - size, x + size, y + size, z + size));
            targets.removeIf(target -> target.getUUID() == casterEntity.getUUID());
            for (LivingEntity target : targets) {
                if (target instanceof Player && RootsConfig.COMMON.disablePVP.get()) {
                    //no pvp allowed
                } else {
                    target.hurt(spellAttack(caster), (int) (5 + 3 * potency));
                    //     damageDealt += (int) (5 + 3 * potency);
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200 + 100 * (int) potency, (int) potency));
                    target.setLastHurtMob(caster);
                    target.setLastHurtByMob(caster);
                }
            }
        }
    }

    public static DamageSource spellAttack(LivingEntity attacker) {
        return attacker.damageSources().source(RootsDamageTypes.GENERIC, attacker);
    }
}
