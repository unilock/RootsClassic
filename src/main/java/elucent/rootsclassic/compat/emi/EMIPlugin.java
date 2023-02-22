package elucent.rootsclassic.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import elucent.rootsclassic.compat.emi.recipe.MortarRecipe;
import elucent.rootsclassic.compat.emi.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

public class EMIPlugin implements EmiPlugin {
	private static final EmiStack MORTAR_WORKSTATION = EmiStack.of(RootsRegistry.MORTAR.get());
	public static final EmiRecipeCategory MORTAR = new EmiRecipeCategory(
			new ResourceLocation("roots", "mortar"),
			MORTAR_WORKSTATION
	);
	
	private static final EmiStack RITUAL_WORKSTATION = EmiStack.of(RootsRegistry.ALTAR.get());
	public static final EmiRecipeCategory RITUAL = new EmiRecipeCategory(
			new ResourceLocation("roots", "ritual"),
			RITUAL_WORKSTATION
	);
	
	@Override
	public void register(EmiRegistry registry) {
		RecipeManager manager = registry.getRecipeManager();
		
		// Mortar Recipe Type
		registry.addCategory(MORTAR);
		registry.addWorkstation(MORTAR, MORTAR_WORKSTATION);
		manager.getAllRecipesFor(RootsRecipes.COMPONENT_RECIPE_TYPE.get()).forEach((r) -> registry.addRecipe(new MortarRecipe(r)));
		
		// Ritual Recipe Type
		registry.addCategory(RITUAL);
		registry.addWorkstation(RITUAL, RITUAL_WORKSTATION);
		RitualRegistry.RITUALS.getEntries().forEach((ritual) -> registry.addRecipe(new RitualRecipe(ritual.getId(), ritual.get())));
		
		// Information pages
		RootsRegistry.ITEMS.getEntries().forEach((registryObject) -> {
			Item item = registryObject.get();
			if (item != null) {
				List<EmiIngredient> stacks = List.of(EmiIngredient.of(Ingredient.of(new ItemStack(item))));
				List<Component> text = List.of(Component.translatable(item.getDescriptionId() + ".guide"));
				registry.addRecipe(new EmiInfoRecipe(stacks, text, null));
			}
		});
	}
}
