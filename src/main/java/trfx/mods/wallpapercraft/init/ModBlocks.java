package trfx.mods.wallpapercraft.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import trfx.mods.wallpapercraft.BlockFactory;
import trfx.mods.wallpapercraft.PatternIterator;
import trfx.mods.wallpapercraft.WallpaperCraft;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.autogen.variant.VariantList;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {
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


    public static final Map<RegistryObject<Block>, BlockInfo> BLOCK_INFO = new HashMap<>();

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        PatternIterator.iteratePatterns((pattern, variant, modelType) -> {
            String blockName = makeBlockName(
                    pattern.getName(),
                    variant.getInternalName(),
                    modelType.getSuffix()
            );
            RegistryObject<Block> block = BLOCKS.register(
                    blockName,
                    () -> BlockFactory.makeBlock(pattern, modelType)
            );
            ModItems.registerBlockItem(blockName, block);
            BLOCK_INFO.put(block, new BlockInfo(pattern, variant, modelType));
            WallpaperCraft.LOGGER.debug("Registered block '{}'", block.getId());
        });
    }

    public static String makeBlockName(String pattern, String variant, String modelSuffix) {
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
}
