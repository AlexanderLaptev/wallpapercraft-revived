package trfx.mods.wallpapercraft.forge;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.slf4j.Logger;
import trfx.mods.wallpapercraft.core.pattern.Pattern;
import trfx.mods.wallpapercraft.core.pattern.PatternLoader;

import java.util.List;

@Mod(WallpaperCraft.MOD_ID)
@Mod.EventBusSubscriber(modid = WallpaperCraft.MOD_ID, bus = Bus.MOD)
public class WallpaperCraft {
    public static final String MOD_ID = "wallpapercraft";

    private static final Logger LOGGER = LogUtils.getLogger();

    public WallpaperCraft() {
        LOGGER.debug("Loading patterns");
        List<Pattern> patterns = PatternLoader.load();
    }
}
