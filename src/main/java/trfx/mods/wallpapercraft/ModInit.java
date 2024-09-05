package trfx.mods.wallpapercraft;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.autogen.variant.VariantList;
import trfx.mods.wallpapercraft.autogen.variant.VariantListCache;

import java.util.HashMap;
import java.util.Map;

public class ModInit {
    public static final class BlockInfo {
        public final Pattern pattern;
        public final VariantList.Variant variant;
        public final Pattern.ModelType modelType;

        public BlockInfo(Pattern pattern, VariantList.Variant variant, Pattern.ModelType modelType) {
            this.pattern = pattern;
            this.variant = variant;
            this.modelType = modelType;
        }
    }

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS,
            WallpaperCraft.MOD_ID
    );

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS,
            WallpaperCraft.MOD_ID
    );

    // TODO: sort
    // TODO: localize
    public static final CreativeModeTab BLOCKS_TAB = new CreativeModeTab(WallpaperCraft.MOD_ID + ".blocks") {
        private static final ItemStack ICON_STACK = new ItemStack(Items.STONE);

        @Override
        public void fillItemList(NonNullList<ItemStack> pItems) {
            super.fillItemList(pItems);
        }

        @Override
        public ItemStack makeIcon() {
            return ICON_STACK;
        }
    };

    public static final Map<RegistryObject<Block>, BlockInfo> BLOCK_INFO = new HashMap<>();

    public static void register(IEventBus bus, Map<String, Pattern> patterns) {
        BLOCKS.register(bus);
        ITEMS.register(bus);

        for (var pattern : patterns.values()) {
            VariantList variantList = VariantListCache.getVariantList(pattern.getVariantListName());
            for (VariantList.Variant variant : variantList.getVariants().values()) {
                for (Pattern.ModelType modelType : pattern.getModelTypes()) {
                    String blockName = makeBlockName(
                            pattern.getName(),
                            variant.getInternalName(),
                            modelType.getSuffix()
                    );
                    RegistryObject<Block> block = BLOCKS.register(
                            blockName,
                            () -> BlockFactory.makeBlock(pattern, modelType)
                    );
                    ITEMS.register(blockName, () -> makeBlockItemForBlock(block.get()));
                    BLOCK_INFO.put(block, new BlockInfo(pattern, variant, modelType));
                    WallpaperCraft.LOGGER.debug("Registered block '{}'", block.getId());
                }
            }
        }
    }

    private static String makeBlockName(String pattern, String variant, String modelSuffix) {
        StringBuilder sb = new StringBuilder();
        if (!variant.isBlank()) {
            sb.append(variant);
            sb.append("_");
        }
        sb.append(pattern);
        if (!modelSuffix.isBlank()) {
            sb.append("_");
            sb.append(modelSuffix);
        }
        return sb.toString();
    }

    private static BlockItem makeBlockItemForBlock(Block block) {
        return new BlockItem(block, new Item.Properties().tab(BLOCKS_TAB));
    }
}
