package trfx.mods.wallpapercraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;
import trfx.mods.wallpapercraft.WallpaperCraft;
import trfx.mods.wallpapercraft.init.ModBlocks;
import trfx.mods.wallpapercraft.init.ModItems;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        WallpaperCraft.LOGGER.debug("Generating template localization");
        add(ModItems.BLOCKS_TAB.getDisplayName().getString(), "");
        for (RegistryObject<Block> regObject : ModBlocks.BLOCKS.getEntries()) {
            add(regObject.get(), "");
        }
    }
}
