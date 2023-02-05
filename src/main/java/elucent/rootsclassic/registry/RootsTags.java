package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RootsTags {
    public static final TagKey<Block> NEEDS_LIVING_TOOL = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Const.MODID, "needs_living_tool"));
    public static final TagKey<Block> NEEDS_ENGRAVED_TOOL = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Const.MODID, "needs_engraved_tool"));

    public static final TagKey<Item> BARKS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Const.MODID, "barks"));
    public static final TagKey<Item> BERRIES = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Const.MODID, "berries"));
}
