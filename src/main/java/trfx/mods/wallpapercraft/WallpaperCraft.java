package trfx.mods.wallpapercraft;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.autogen.pattern.PatternLoader;
import trfx.mods.wallpapercraft.client.ModKeyMappings;
import trfx.mods.wallpapercraft.client.MouseScrollingHandler;
import trfx.mods.wallpapercraft.datagen.ModDataGenerator;
import trfx.mods.wallpapercraft.init.ModBlocks;
import trfx.mods.wallpapercraft.init.ModItems;
import trfx.mods.wallpapercraft.network.ModNetworkHandler;

import java.util.Map;

@Mod(WallpaperCraft.MOD_ID)
@Mod.EventBusSubscriber(modid = WallpaperCraft.MOD_ID, bus = Bus.MOD)
public class WallpaperCraft {
    public static final String MOD_ID = "wallpapercraft";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Map<String, Pattern> patterns = PatternLoader.load();

    public WallpaperCraft() {
        Validate.notEmpty(patterns, "No patterns have been loaded. Please report to the developers of WallpaperCraft.");

        ModNetworkHandler.register();
        MinecraftForge.EVENT_BUS.register(MouseScrollingHandler.class);

        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.register(ModDataGenerator.class);
        modBus.register(ModKeyMappings.class);
        ModItems.register(modBus);
        ModBlocks.register(modBus);
    }
}
