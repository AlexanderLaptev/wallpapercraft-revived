package trfx.mods.wallpapercraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import trfx.mods.wallpapercraft.block.WallpaperBlock;
import trfx.mods.wallpapercraft.network.ModNetworkHandler;
import trfx.mods.wallpapercraft.network.ScrollingMessage;

import java.util.Optional;

public class MouseScrollingHandler {
    @SubscribeEvent
    public static void mouseScrolled(InputEvent.MouseScrollingEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) { return; }
        if (!ModKeyMappings.SCROLL_KEY_MAPPING.isDown()) { return; }

        ItemStack heldStack = Minecraft.getInstance().player.getInventory().getSelected();
        Optional<WallpaperBlock> optional = WallpaperBlock.tryConvertStack(heldStack);
        if (optional.isEmpty()) { return; }
        ModNetworkHandler.MAIN_CHANNEL.sendToServer(new ScrollingMessage(event.getScrollDelta() < 0.0f));
        event.setCanceled(true);
    }
}
