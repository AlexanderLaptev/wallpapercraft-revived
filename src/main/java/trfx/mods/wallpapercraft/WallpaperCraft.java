package trfx.mods.wallpapercraft;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.autogen.pattern.PatternLoader;
import trfx.mods.wallpapercraft.datagen.ModDataGenerator;

import java.util.Map;

@Mod(WallpaperCraft.MOD_ID)
@Mod.EventBusSubscriber(modid = WallpaperCraft.MOD_ID, bus = Bus.MOD)
public class WallpaperCraft {
    public static final String MOD_ID = "wallpapercraft";

    public static final Logger LOGGER = LogUtils.getLogger();

    public WallpaperCraft() {
        Map<String, Pattern> patterns = PatternLoader.load();
        Validate.notEmpty(patterns, "No patterns have been loaded. Please report to the developers of WallpaperCraft.");

        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.register(ModDataGenerator.class);
        ModInit.register(modBus, patterns);
    }
}
