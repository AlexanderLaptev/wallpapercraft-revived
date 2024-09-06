package trfx.mods.wallpapercraft.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import trfx.mods.wallpapercraft.WallpaperCraft;

public class ModKeyMappings {
    public static final KeyMapping SCROLL_KEY_MAPPING = new KeyMapping(
            makeKeyBindingName("scroll"),
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            Minecraft.getInstance().options.keyShift.getKey().getValue(), // Use the sneak key by default
            KeyMapping.CATEGORY_MISC
    );

    @SuppressWarnings("SameParameterValue")
    private static String makeKeyBindingName(String name) {
        return "key." + WallpaperCraft.MOD_ID + "." + name;
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(SCROLL_KEY_MAPPING);
    }
}
