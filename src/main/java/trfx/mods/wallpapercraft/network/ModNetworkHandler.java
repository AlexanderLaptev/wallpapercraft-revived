package trfx.mods.wallpapercraft.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import trfx.mods.wallpapercraft.WallpaperCraft;

public class ModNetworkHandler {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel MAIN_CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(WallpaperCraft.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int index = 0;
        MAIN_CHANNEL.registerMessage(
                index++,
                ScrollingMessage.class,
                ScrollingMessage::encode,
                ScrollingMessage::decode,
                ScrollingMessage::handle
        );
        MAIN_CHANNEL.registerMessage(
                index++,
                ColorPickMessage.class,
                ColorPickMessage::encode,
                ColorPickMessage::decode,
                ColorPickMessage::handle
        );
    }
}
