package trfx.mods.wallpapercraft.init;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import trfx.mods.wallpapercraft.PatternIterator;
import trfx.mods.wallpapercraft.WallpaperCraft;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS,
            WallpaperCraft.MOD_ID
    );

    public static final CreativeModeTab BLOCKS_TAB = new CreativeModeTab(WallpaperCraft.MOD_ID + ".blocks") {
        private static final ResourceLocation ICON_LOCATION = new ResourceLocation(WallpaperCraft.MOD_ID, "red_jewel");

        @Override
        public void fillItemList(NonNullList<ItemStack> items) {
            PatternIterator.iteratePatterns(((pattern, variant, modelType) -> {
                Item blockItem = ForgeRegistries.ITEMS.getValue(
                        new ResourceLocation(
                                WallpaperCraft.MOD_ID,
                                ModBlocks.makeBlockName(
                                        pattern.getName(),
                                        variant.getInternalName(),
                                        modelType.getSuffix()
                                )
                        )
                );
                items.add(new ItemStack(blockItem));
            }));
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ForgeRegistries.ITEMS.getDelegateOrThrow(ICON_LOCATION));
        }
    };

    public static void registerBlockItem(String blockName, RegistryObject<Block> block) {
        ModItems.ITEMS.register(blockName, () -> makeBlockItemForBlock(block.get()));
    }

    private static BlockItem makeBlockItemForBlock(Block block) {
        return new BlockItem(block, new Item.Properties().tab(BLOCKS_TAB));
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
