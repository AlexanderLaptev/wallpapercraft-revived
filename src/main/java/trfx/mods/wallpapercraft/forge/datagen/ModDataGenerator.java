package trfx.mods.wallpapercraft.forge.datagen;

import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import trfx.mods.wallpapercraft.forge.WallpaperCraft;
import trfx.mods.wallpapercraft.forge.datagen.loot.ModLootTableProvider;

public class ModDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
                event.includeServer(),
                new ModBlockTagsProvider(event.getGenerator(), WallpaperCraft.MOD_ID, event.getExistingFileHelper())
        );
        event.getGenerator().addProvider(
                event.includeServer(),
                new ModLootTableProvider(event.getGenerator())
        );
        event.getGenerator().addProvider(
                event.includeClient(),
                new ModBlockStateProvider(event.getGenerator(), WallpaperCraft.MOD_ID, event.getExistingFileHelper())
        );
//        event.getGenerator().addProvider(
//                event.includeClient() || event.includeServer(),
//                new ModLanguageProvider(event.getGenerator(), WallpaperCraft.MOD_ID, "en_us")
//        );
    }
}
