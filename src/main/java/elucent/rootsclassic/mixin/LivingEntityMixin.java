package elucent.rootsclassic.mixin;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void rootsclassic_tick(CallbackInfo ci) {
        LivingEntity entity = ((LivingEntity)(Object)this);
        CompoundTag persistentData = entity.getCustomData();
        if (persistentData.contains(Const.NBT_TRACK_TICKS) && persistentData.contains(Const.NBT_SKIP_TICKS)) {
            int skipTicks = persistentData.getInt(Const.NBT_SKIP_TICKS);
            if (skipTicks > 0) {
                persistentData.putInt(Const.NBT_SKIP_TICKS, skipTicks - 1);
                if (skipTicks <= 0) {
                    persistentData.remove(Const.NBT_SKIP_TICKS);
                    RootsUtil.decrementTickTracking(entity);
                }
                ci.cancel();
            }
        }
    }
}
