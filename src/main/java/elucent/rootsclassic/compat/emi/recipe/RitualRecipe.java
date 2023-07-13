package elucent.rootsclassic.compat.emi.recipe;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TextureWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.compat.emi.EMIPlugin;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.ritual.rituals.RitualCrafting;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class RitualRecipe implements EmiRecipe {
	private final static ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(Const.MODID, "textures/gui/jei/compat.png");
	private final static ResourceLocation ALTAR_LOCATION = new ResourceLocation(Const.MODID, "textures/gui/tabletaltar.png");
	
	private final ResourceLocation id;
	private final RitualBase ritual;
	private final List<EmiIngredient> inputs, incenses, allIngredients;
	
	public RitualRecipe(ResourceLocation id, RitualBase ritual) {
		// Mutate the recipe id to be formatted like "rootsclassic:/ritual/<id>" since it is technically a synthetic recipe and will prevent recipe id collisions
		this.id = new ResourceLocation(id.getNamespace(), "/ritual/" + id.getPath());
		this.ritual = ritual;
		
		this.inputs = ritual.getIngredients().stream().map((i) -> EmiIngredient.of(Ingredient.of(i))).toList();
		this.incenses = ritual.getIncenses().stream().map((i) -> EmiIngredient.of(Ingredient.of(i))).toList();
		
		this.allIngredients = Lists.newArrayList();
		this.allIngredients.addAll(this.inputs);
		this.allIngredients.addAll(this.incenses);
	}
	
	@Override
	public EmiRecipeCategory getCategory() {
		return EMIPlugin.RITUAL;
	}
	
	@Override
	public ResourceLocation getId() {
		return id;
	}
	
	@Override
	public List<EmiIngredient> getInputs() {
		return allIngredients;
	}
	
	@Override
	public List<EmiStack> getOutputs() {
		if (ritual instanceof RitualCrafting crafting) {
			return List.of(EmiStack.of(crafting.result));
		}
		return List.of(EmiStack.EMPTY);
	}
	
	@Override
	public int getDisplayWidth() {
		return 94;
	}
	
	@Override
	public int getDisplayHeight() {
		return 100;
	}
	
	@Override
	public void addWidgets(WidgetHolder widgets) {
		widgets.addTexture(BACKGROUND_LOCATION, 0, 0, 94, 100, 0, 0);
		
		widgets.addTexture(ALTAR_LOCATION, 12, 0, 70, 22, 61, 53); // ingredient background
		widgets.addTexture(ALTAR_LOCATION, 0, 24, 94, 22, 49, 85); // incense background
		widgets.addTexture(ALTAR_LOCATION, 64, 64, 22, 22, 61, 53); // result background
		
		widgets.addDrawable(0, 0, 93, 93, (graphics, mx, my, delta) -> {
			PoseStack stack = graphics.pose();
			stack.pushPose();
			stack.scale(0.5f, 0.5f, 1f);
			
			new TextureWidget(ALTAR_LOCATION, 20, 100, 93, 93, 50, 118).render(graphics, mx, my, delta); // grid
			
			final int basePosX = 63;
			final int basePosY = 135;
			
			new TextureWidget(ALTAR_LOCATION, basePosX, basePosY, 16, 16, 192, 32).render(graphics, mx, my, delta); // stone
			
			final List<Block> blocks = ritual.getBlocks();
			final List<BlockPos> relativePosition = ritual.getPositionsRelative();
			for (int i = 0; i < blocks.size(); i++) {
				int xShift, yShift;
				Block block = blocks.get(i);
				BlockPos pos = relativePosition.get(i);
				if (block.equals(RootsRegistry.MUNDANE_STANDING_STONE.get())) {
					xShift = 8 * pos.getX();
					yShift = 8 * pos.getZ();
					new TextureWidget(ALTAR_LOCATION, basePosX + xShift, basePosY + yShift, 16, 16, 192, 48).render(graphics, mx, my, delta); // mundane stone
				}
				if (block.equals(RootsRegistry.ATTUNED_STANDING_STONE.get())) {
					xShift = 8 * pos.getX();
					yShift = 8 * pos.getZ();
					new TextureWidget(ALTAR_LOCATION, basePosX + xShift, basePosY + yShift, 16, 16, 192, 64).render(graphics, mx, my, delta); // attuned stone
				}
			}
			
			stack.popPose();
		});
		
		for (int i = 0; i < inputs.size(); i++) {
			widgets.addSlot(inputs.get(i), 14 + (i * 24), 2).drawBack(false);
		}
		for (int i = 0; i < incenses.size(); i++) {
			widgets.addSlot(incenses.get(i), 27 + (i * 16), 26).drawBack(false);
		}
		EmiStack output = this.getOutputs().get(0);
		if (!output.isEmpty()) {
			widgets.addSlot(output, 66, 66).drawBack(false).recipeContext(this);
		}
	}
}
