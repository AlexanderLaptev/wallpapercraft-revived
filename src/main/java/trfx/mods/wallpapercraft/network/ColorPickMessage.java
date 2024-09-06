package trfx.mods.wallpapercraft.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import trfx.mods.wallpapercraft.WallpaperCraft;
import trfx.mods.wallpapercraft.block.WallpaperBlock;

import java.util.Optional;
import java.util.function.Supplier;

public class ColorPickMessage {
    private final ResourceLocation targetLocation;

    public ColorPickMessage(ResourceLocation targetLocation) {
        this.targetLocation = targetLocation;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(targetLocation);
    }

    public static ColorPickMessage decode(FriendlyByteBuf buffer) {
        return new ColorPickMessage(buffer.readResourceLocation());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender == null) { return; }

            WallpaperBlock targetBlock;
            try {
                targetBlock = (WallpaperBlock) ForgeRegistries.BLOCKS.getValue(targetLocation);
            } catch (ClassCastException e) {
                WallpaperCraft.LOGGER.error("Error during color pick", e);
                return;
            }

            ItemStack heldStack = sender.getMainHandItem();
            Optional<WallpaperBlock> optional = WallpaperBlock.tryConvertStack(heldStack);
            if (optional.isEmpty()) { return; }
            WallpaperBlock sourceBlock = optional.get();

            if (sourceBlock.canBeScrolledTo(targetBlock)) {
                sender.getInventory().setItem(
                        sender.getInventory().selected,
                        new ItemStack(targetBlock, heldStack.getCount())
                );
            }
        });
    }
}
