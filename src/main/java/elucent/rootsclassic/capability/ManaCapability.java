package elucent.rootsclassic.capability;

import net.minecraft.nbt.CompoundTag;

public class ManaCapability implements IManaCapability {
    private float maxMana = 40;
    private float mana = 40;

    public ManaCapability() {
        this.maxMana = 40;
        this.mana = 40;
    }

    @Override
    public float getMana() {
        return mana;
    }

    @Override
    public float getMaxMana() {
        return maxMana;
    }

    @Override
    public void setMana(float mana) {
        this.mana = mana;
        if (mana < 0) {
            this.mana = 0;
        }
        if (mana > getMaxMana()) {
            this.mana = getMaxMana();
        }
    }

    @Override
    public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putFloat("mana", getMana());
        tag.putFloat("maxMana", getMaxMana());
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        setMana(tag.getFloat("mana"));
        setMaxMana(tag.getFloat("maxMana"));
    }
}
