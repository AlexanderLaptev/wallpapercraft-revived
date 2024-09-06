package trfx.mods.wallpapercraft.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import trfx.mods.wallpapercraft.WallpaperCraft;
import trfx.mods.wallpapercraft.autogen.variant.VariantList;
import trfx.mods.wallpapercraft.block.WallpaperBlock;

import java.util.Optional;
import java.util.function.Supplier;

public class ScrollingMessage {
    private final boolean direction;

    public ScrollingMessage(boolean direction) {
        this.direction = direction;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(direction);
    }

    public static ScrollingMessage decode(FriendlyByteBuf buffer) {
        return new ScrollingMessage(buffer.readBoolean());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender == null) { return; }

            Inventory inventory = sender.getInventory();
            ItemStack heldStack = inventory.getSelected();
            Optional<WallpaperBlock> optional = ScrollingMessage.tryConvertStackToModBlock(heldStack);

            if (optional.isEmpty()) { return; }
            WallpaperBlock modBlock = optional.get();
            VariantList.Variant newVariant = modBlock.scrollVariant(direction);
            String newName = WallpaperBlock.getRegistryName(modBlock.getPattern(), newVariant, modBlock.getModelType());
            Item newItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(WallpaperCraft.MOD_ID, newName));
            inventory.setItem(inventory.selected, new ItemStack(newItem));
        });
        ctx.setPacketHandled(true);
    }

    public static Optional<WallpaperBlock> tryConvertStackToModBlock(ItemStack stack) {
        if (stack.isEmpty()) { return Optional.empty(); }

        Item item = stack.getItem();
        if (!(item instanceof BlockItem blockItem)) { return Optional.empty(); }

        Block block = blockItem.getBlock();
        if (!(block instanceof WallpaperBlock modBlock)) { return Optional.empty(); }

        return Optional.of(modBlock);
    }
}
