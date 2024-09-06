package trfx.mods.wallpapercraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import trfx.mods.wallpapercraft.block.WallpaperBlock;
import trfx.mods.wallpapercraft.network.ColorPickMessage;
import trfx.mods.wallpapercraft.network.ModNetworkHandler;

import java.util.Optional;

public class ColorPickingHandler {
    @SubscribeEvent
    public static void handleEvent(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isPickBlock()) { return; }
        if (Minecraft.getInstance().player == null) { return; }
        if (Minecraft.getInstance().level == null) { return; }
        if (!ModKeyMappings.SCROLL_KEY_MAPPING.isDown()) { return; }

        HitResult hitResult = Minecraft.getInstance().hitResult;
        if (hitResult == null || hitResult.getType() != HitResult.Type.BLOCK) { return; }
        BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
        BlockState blockState = Minecraft.getInstance().level.getBlockState(blockPos);
        if (blockState.isAir()) { return; }
        Block block = blockState.getBlock();
        if (!(block instanceof WallpaperBlock targetBlock)) { return; }

        ItemStack heldStack = Minecraft.getInstance().player.getMainHandItem();
        Optional<WallpaperBlock> optional = WallpaperBlock.tryConvertStack(heldStack);
        if (optional.isEmpty()) { return; }
        WallpaperBlock heldBlock = optional.get();
        if (!heldBlock.canBeScrolledTo(targetBlock)) { return; }

        ResourceLocation targetLocation = ForgeRegistries.BLOCKS.getKey(targetBlock);
        ModNetworkHandler.MAIN_CHANNEL.sendToServer(new ColorPickMessage(targetLocation));
        event.setSwingHand(false);
        event.setCanceled(true);
    }
}
