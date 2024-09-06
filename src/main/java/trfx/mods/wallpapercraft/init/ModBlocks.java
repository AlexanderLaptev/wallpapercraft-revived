package trfx.mods.wallpapercraft.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import trfx.mods.wallpapercraft.PatternIterator;
import trfx.mods.wallpapercraft.WallpaperCraft;
import trfx.mods.wallpapercraft.block.BlockFactory;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS,
            WallpaperCraft.MOD_ID
    );

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        PatternIterator.iteratePatterns((pattern, variant, modelType) -> {
            String blockName = makeBlockName(
                    pattern.getName(),
                    variant,
                    modelType.getSuffix()
            );
            RegistryObject<Block> block = BLOCKS.register(
                    blockName,
                    () -> BlockFactory.makeBlock(pattern, variant, modelType)
            );
            ModItems.registerBlockItem(blockName, block);
            WallpaperCraft.LOGGER.debug("Registered block '{}'", block.getId());
        });
    }

    public static String makeBlockName(String pattern, String variant, String modelSuffix) {
        StringBuilder sb = new StringBuilder(pattern);
        if (!variant.isBlank()) {
            sb.append("_");
            sb.append(variant);
        }
        if (!modelSuffix.isBlank()) {
            sb.append("_");
            sb.append(modelSuffix);
        }
        return sb.toString();
    }
}
