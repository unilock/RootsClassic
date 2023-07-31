package elucent.rootsclassic.datagen;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.lootmodifiers.DropModifier;
import elucent.rootsclassic.registry.RootsDamageTypes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import io.github.fabricators_of_create.porting_lib.data.DatapackBuiltinEntriesProvider;
import io.github.fabricators_of_create.porting_lib.loot.GlobalLootModifierProvider;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class RootsDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(BlockLootTables::new);
        pack.addProvider(Recipes::new);
        pack.addProvider(GLMProvider::new);
        pack.addProvider(RootsBlockTags::new);
        pack.addProvider(RootsItemTags::new);
        pack.addProvider((output, registriesFuture) -> new DatapackBuiltinEntriesProvider(output, CompletableFuture.supplyAsync(RootsDataGen::getProvider), Set.of(Const.MODID)));
    }

    private static HolderLookup.Provider getProvider() {
        final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
        registryBuilder.add(Registries.DAMAGE_TYPE, context -> {
            context.register(RootsDamageTypes.GENERIC, new DamageType(Const.MODID + ".generic", 0.0F));
            context.register(RootsDamageTypes.FIRE, new DamageType(Const.MODID + ".fire", 0.1F, DamageEffects.BURNING));
            context.register(RootsDamageTypes.WITHER, new DamageType(Const.MODID + ".wither", 0.0F));
            context.register(RootsDamageTypes.CACTUS, new DamageType(Const.MODID + ".cactus", 0.1F));
        });
        RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup());
    }

    private static class BlockLootTables extends FabricBlockLootTableProvider {

        public BlockLootTables(FabricDataOutput dataGenerator) {
            super(dataGenerator);
        }

        @Override
        public void generate() {
            dropSelf(RootsRegistry.MORTAR.get());
            dropSelf(RootsRegistry.ALTAR.get());
            dropSelf(RootsRegistry.BRAZIER.get());
            dropSelf(RootsRegistry.IMBUER.get());
            dropSelf(RootsRegistry.MUNDANE_STANDING_STONE.get());
            dropSelf(RootsRegistry.MIDNIGHT_BLOOM.get());
            dropSelf(RootsRegistry.FLARE_ORCHID.get());
            dropSelf(RootsRegistry.RADIANT_DAISY.get());
            createDoorTable(RootsRegistry.ATTUNED_STANDING_STONE.get());
            createDoorTable(RootsRegistry.VACUUM_STANDING_STONE.get());
            createDoorTable(RootsRegistry.REPULSOR_STANDING_STONE.get());
            createDoorTable(RootsRegistry.ACCELERATOR_STANDING_STONE.get());
            createDoorTable(RootsRegistry.AESTHETIC_STANDING_STONE.get());
            createDoorTable(RootsRegistry.ENTANGLER_STANDING_STONE.get());
            createDoorTable(RootsRegistry.IGNITER_STANDING_STONE.get());
            createDoorTable(RootsRegistry.GROWER_STANDING_STONE.get());
            createDoorTable(RootsRegistry.HEALER_STANDING_STONE.get());
        }
    }

    private static class GLMProvider extends GlobalLootModifierProvider {

        public GLMProvider(FabricDataOutput packOutput) {
            super(packOutput, Const.MODID);
        }

        @Override
        protected void start() {
            add("rootsclassic_drops", new DropModifier.BlockDropModifier(
                    new LootItemCondition[]{
                            InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))).build()
                    }));
        }
    }

    private static class Recipes extends FabricRecipeProvider {
        public Recipes(FabricDataOutput dataGenerator) {
            super(dataGenerator);
        }

        @Override
        public void buildRecipes(Consumer<FinishedRecipe> consumer) {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RootsRegistry.PESTLE.get()).define('X', Blocks.DIORITE).group("pestle")
                    .pattern("X  ").pattern(" XX").pattern(" XX").unlockedBy("has_diorite", has(Blocks.DIORITE)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RootsRegistry.PESTLE.get()).define('X', Blocks.DIORITE).group("pestle")
                    .pattern("  X").pattern("XX ").pattern("XX ").unlockedBy("has_diorite", has(Blocks.DIORITE))
                    .save(consumer, BuiltInRegistries.ITEM.getKey(RootsRegistry.PESTLE.get()) + "2");
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC,RootsRegistry.MORTAR.get()).define('X', Tags.Items.STONE)
                    .pattern("X X").pattern("X X").pattern(" X ").unlockedBy("has_stone", has(Tags.Items.STONE)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RootsRegistry.IMBUER.get()).define('X', Tags.Items.RODS_WOODEN).define('L', ItemTags.LOGS).define('S', Blocks.CHISELED_STONE_BRICKS)
                    .pattern("X X").pattern("LSL").unlockedBy("has_chiseled_stone_bricks", has(Blocks.CHISELED_STONE_BRICKS)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RootsRegistry.MUNDANE_STANDING_STONE.get()).define('S', Tags.Items.STONE).define('B', Blocks.STONE_BRICKS).define('L', Tags.Items.STORAGE_BLOCKS_LAPIS)
                    .pattern("SBS").pattern("BLB").pattern("SBS").unlockedBy("has_lapis_block", has(Tags.Items.STORAGE_BLOCKS_LAPIS)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RootsRegistry.ATTUNED_STANDING_STONE.get()).define('S', Tags.Items.STONE).define('N', Tags.Items.INGOTS_NETHER_BRICK).define('D', Tags.Items.GEMS_DIAMOND)
                    .pattern("SNS").pattern("NDN").pattern("SNS").unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC,RootsRegistry.BRAZIER.get())
                    .define('I', Tags.Items.INGOTS_IRON).define('S', Tags.Items.STRING).define('C', Items.CAULDRON).define('X', Tags.Items.RODS_WOODEN)
                    .pattern("ISI").pattern("ICI").pattern("IXI").unlockedBy("has_cauldron", has(Items.CAULDRON)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RootsRegistry.ALTAR.get())
                    .define('S', Tags.Items.STONE).define('F', Items.POPPY).define('B', RootsRegistry.VERDANT_SPRIG.get())
                    .define('G', Tags.Items.STORAGE_BLOCKS_GOLD).define('C', Blocks.CHISELED_STONE_BRICKS)
                    .pattern("BFB").pattern("SGS").pattern(" C ").unlockedBy("has_gold_block", has(Tags.Items.STORAGE_BLOCKS_GOLD)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, RootsRegistry.BARK_KNIFE.get())
                    .define('S', Tags.Items.RODS_WOODEN).define('V', ItemTags.SAPLINGS).define('P', ItemTags.PLANKS)
                    .pattern(" VV").pattern("VPV").pattern("SV ").unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(consumer);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RootsRegistry.RUNIC_TABLET.get())
                    .define('S', Tags.Items.SEEDS_WHEAT).define('B', Tags.Items.STONE).define('R', RootsRegistry.OLD_ROOT.get())
                    .pattern(" R ").pattern("SBS").pattern(" S ").unlockedBy("has_old_root", has(RootsRegistry.OLD_ROOT.get())).save(consumer);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RootsRegistry.GROWTH_POWDER.get(), 4)
                    .requires(Tags.Items.SEEDS_WHEAT).requires(Items.GRASS).requires(Tags.Items.DUSTS_REDSTONE).requires(RootsRegistry.PESTLE.get())
                    .unlockedBy("has_pestle", has(RootsRegistry.PESTLE.get())).save(consumer);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RootsRegistry.MUTATING_POWDER.get())
                    .requires(RootsRegistry.GROWTH_POWDER.get()).requires(RootsRegistry.GROWTH_POWDER.get()).requires(RootsRegistry.GROWTH_POWDER.get()).requires(RootsRegistry.GROWTH_POWDER.get())
                    .requires(Tags.Items.NETHER_STARS).requires(Tags.Items.CROPS_NETHER_WART).requires(RootsRegistry.PESTLE.get())
                    .unlockedBy("has_pestle", has(RootsRegistry.PESTLE.get())).save(consumer);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, RootsRegistry.ROOTY_STEW.get())
                    .requires(Tags.Items.CROPS_WHEAT).requires(Items.BOWL).requires(RootsRegistry.OLD_ROOT.get())
                    .unlockedBy("has_bowl", has(Items.BOWL)).save(consumer);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, RootsRegistry.FRUIT_SALAD.get())
                    .requires(Items.MELON).requires(Items.MELON).requires(Items.MELON)
                    .requires(Items.APPLE).requires(Items.BOWL).requires(RootsRegistry.ELDERBERRY.get())
                    .requires(RootsRegistry.WHITECURRANT.get()).requires(RootsRegistry.BLACKCURRANT.get()).requires(RootsRegistry.REDCURRANT.get())
                    .unlockedBy("has_bowl", has(Items.BOWL)).save(consumer);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RootsRegistry.HEALING_POULTICE.get(), 2)
                    .requires(RootsRegistry.REDCURRANT.get()).requires(Items.PAPER).requires(RootsRegistry.PESTLE.get()).requires(RootsRegistry.VERDANT_SPRIG.get())
                    .unlockedBy("has_pestle", has(RootsRegistry.PESTLE.get())).save(consumer);
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(RootsRegistry.DRAGONS_EYE.get()), RecipeCategory.MISC,
                            Items.ENDER_PEARL, 1F, 200).unlockedBy("has_dragons_eye", has(RootsRegistry.DRAGONS_EYE.get()))
                    .save(consumer, "rootsclassic:ender_pearl");
        }
    }

    private static class RootsBlockTags extends FabricTagProvider.BlockTagProvider {
        public RootsBlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            this.tag(RootsTags.NEEDS_LIVING_TOOL);
            this.tag(RootsTags.NEEDS_ENGRAVED_TOOL);
        }
    }

    private static class RootsItemTags extends FabricTagProvider.ItemTagProvider {
        public RootsItemTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(RootsTags.BERRIES).add(RootsRegistry.NIGHTSHADE.get(), RootsRegistry.BLACKCURRANT.get(), RootsRegistry.REDCURRANT.get(), RootsRegistry.WHITECURRANT.get(), RootsRegistry.ELDERBERRY.get());

            this.addBark(RootsRegistry.ACACIA_BARK.get(), "acacia");
            this.addBark(RootsRegistry.BIRCH_BARK.get(), "birch");
            this.addBark(RootsRegistry.DARK_OAK_BARK.get(), "dark_oak");
            this.addBark(RootsRegistry.JUNGLE_BARK.get(), "jungle");
            this.addBark(RootsRegistry.OAK_BARK.get(), "oak");
            this.addBark(RootsRegistry.SPRUCE_BARK.get(), "spruce");
        }

        private void addBark(Item item, String treeType) {
            TagKey<Item> barkTypeTag = TagKey.create(Registries.ITEM, new ResourceLocation(Const.MODID, "barks/" + treeType));
            this.tag(RootsTags.BARKS).addTag(barkTypeTag);
            getOrCreateTagBuilder(barkTypeTag).add(item);
        }
    }
}
