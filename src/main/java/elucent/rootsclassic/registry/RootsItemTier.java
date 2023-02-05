package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import io.github.fabricators_of_create.porting_lib.util.LazyTier;
import io.github.fabricators_of_create.porting_lib.util.TierSortingRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

import java.util.List;

public class RootsItemTier {
    public static final Tier LIVING = TierSortingRegistry.registerTier(
            new LazyTier(2, 192, 6.0f, 2.0f, 18,
                    RootsTags.NEEDS_LIVING_TOOL,
                    () -> null),
            new ResourceLocation(Const.MODID, "living"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
    public static final Tier ENGRAVED = TierSortingRegistry.registerTier(
            new LazyTier(2, 1050, 5F, 8.0F, 5,
                    RootsTags.NEEDS_ENGRAVED_TOOL,
                    () -> null),
            new ResourceLocation(Const.MODID, "engraved"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
}
