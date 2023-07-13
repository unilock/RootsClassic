package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.recipe.ComponentRecipe;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RootsRecipes {
    public static final LazyRegistrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = LazyRegistrar.create(BuiltInRegistries.RECIPE_SERIALIZER, Const.MODID);
    public static final LazyRegistrar<RecipeType<?>> RECIPE_TYPES = LazyRegistrar.create(Registries.RECIPE_TYPE, Const.MODID);


    public static final RegistryObject<RecipeType<ComponentRecipe>> COMPONENT_RECIPE_TYPE = RECIPE_TYPES.register("component", () -> new RecipeType<>() {
    });
    public static final RegistryObject<ComponentRecipe.SerializeComponentRecipe> COMPONENT_SERIALIZER = RECIPE_SERIALIZERS.register("component", ComponentRecipe.SerializeComponentRecipe::new);
}
