package trfx.mods.wallpapercraft;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.slf4j.Logger;

@Mod(WallpaperCraft.MOD_ID)
@Mod.EventBusSubscriber(modid = WallpaperCraft.MOD_ID, bus = Bus.MOD)
public class WallpaperCraft {
    public static final String MOD_ID = "wallpapercraft";

    private static final Logger LOGGER = LogUtils.getLogger();

    public WallpaperCraft() {
    }
}
