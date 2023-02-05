package elucent.rootsclassic.capability;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface IManaCapability extends ComponentV3 {
    float getMana();

    float getMaxMana();

    void setMana(float mana);

    void setMaxMana(float maxMana);
}
