package elucent.rootsclassic.compat.emi.recipe;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.compat.emi.EMIPlugin;
import elucent.rootsclassic.recipe.ComponentRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;

import java.util.List;

public class MortarRecipe implements EmiRecipe {
	private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(Const.MODID, "textures/gui/tabletmortar.png");
	
	private final ComponentRecipe recipe;
	
	private final ResourceLocation id;
	private final List<EmiIngredient> input;
	private final List<EmiStack> output;
	
	public MortarRecipe(ComponentRecipe recipe) {
		this.recipe = recipe;
		this.id = recipe.getId();
		this.input = recipe.getIngredients().stream().map(EmiIngredient::of).toList();
		this.output = List.of(EmiStack.of(recipe.getResultItem(Minecraft.getInstance().player.clientLevel.registryAccess())));
	}
	
	@Override
	public EmiRecipeCategory getCategory() {
		return EMIPlugin.MORTAR;
	}
	
	@Override
	public ResourceLocation getId() {
		return id;
	}
	
	@Override
	public List<EmiIngredient> getInputs() {
		return input;
	}
	
	@Override
	public List<EmiStack> getOutputs() {
		return output;
	}
	
	@Override
	public int getDisplayWidth() {
		return 142;
	}
	
	@Override
	public int getDisplayHeight() {
		return 45;
	}
	
	@Override
	public void addWidgets(WidgetHolder widgets) {
		widgets.addTexture(BACKGROUND_LOCATION, 0, 0, 142, 45, 21, 30);
		
		for (int i = 0; i < input.size(); i++) {
			EmiIngredient ingredient = input.get(i);
			widgets.addSlot(ingredient, 2 + (i * 16), 25).drawBack(false);
		}
		
		widgets.addSlot(EmiStack.of(recipe.assemble(new SimpleContainer(), Minecraft.getInstance().level.registryAccess())), 122, 25).drawBack(false).recipeContext(this);
	}
}
