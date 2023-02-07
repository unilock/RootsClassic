package elucent.rootsclassic.data;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.lootmodifiers.DropModifier;
import io.github.fabricators_of_create.porting_lib.loot.GlobalLootModifierProvider;
import me.alphamode.forgetags.Tags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

public class RootsDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(GLMProvider::new);
    }

    private static class GLMProvider extends GlobalLootModifierProvider {
        public GLMProvider(DataGenerator gen) {
            super(gen, Const.MODID);
        }

        @Override
        protected void start() {
            add("rootsclassic_drops", new DropModifier.BlockDropModifier(
                    new LootItemCondition[]{
                            InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))).build()
                    }
            ));
        }
    }
}
