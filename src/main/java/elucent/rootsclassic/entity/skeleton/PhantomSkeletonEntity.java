package elucent.rootsclassic.entity.skeleton;

import elucent.rootsclassic.registry.RootsEntities;
import io.github.fabricators_of_create.porting_lib.fake_players.FakePlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class PhantomSkeletonEntity extends Skeleton {

    public static final boolean appliesSlowPotion = true;
    private static final Predicate<LivingEntity> SKELETON_SELECTOR = (livingEntity) -> {
        return !(livingEntity instanceof PhantomSkeletonEntity);
    };

    public PhantomSkeletonEntity(EntityType<? extends PhantomSkeletonEntity> type, Level levelAccessor) {
        super(type, levelAccessor);
    }

    public PhantomSkeletonEntity(Level levelAccessor) {
        super(RootsEntities.PHANTOM_SKELETON.get(), levelAccessor);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return AbstractSkeleton.createAttributes();
    }

    @Override
    protected void registerGoals() {
        //super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        //   this.goalSelector.addGoal(3, new EntityAIAvoidEntity<PhantomSkeletonEntity>(this, PhantomSkeletonEntity.class, 6.0F, 1.0D, 1.2D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        //  this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MobEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Spider.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, EnderMan.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Skeleton.class, 10, true, false, SKELETON_SELECTOR));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource s) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random spawn bonus", random.nextGaussian() * 0.05D, AttributeModifier.Operation.MULTIPLY_BASE));
        float f = difficultyIn.getSpecialMultiplier();
        this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * f);
        setCanPickUpLoot(random.nextFloat() < 0.55F * f);
        return spawnDataIn;
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        if (appliesSlowPotion && entityIn instanceof Player player && !(entityIn instanceof FakePlayer)) {
            if (!player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10 * 20, 0)); // is 10seconds
            }
        }
        return super.doHurtTarget(entityIn);
    }
}
