package trfx.mods.wallpapercraft;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;

import java.util.*;

public class ModInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS,
            WallpaperCraft.MOD_ID
    );

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS,
            WallpaperCraft.MOD_ID
    );

    public static final CreativeModeTab BLOCKS_TAB = new CreativeModeTab(makeTabName("blocks")) {
        private static final ItemStack ICON_STACK = new ItemStack(Items.STONE);

        @Override
        public void fillItemList(NonNullList<ItemStack> pItems) {
            super.fillItemList(pItems);
        }

        @Override
        public ItemStack makeIcon() {
            return ICON_STACK;
        }
    };

    public static final Map<Pattern.Type, Collection<RegistryObject<Block>>> MOD_BLOCKS_BY_TYPE = new HashMap<>();

    static {
        for (Pattern.Type type : Pattern.Type.values()) {
            MOD_BLOCKS_BY_TYPE.put(type, new ArrayList<>());
        }
    }

    public static void register(IEventBus bus, List<Pattern> patterns) {
        BLOCKS.register(bus);
        ITEMS.register(bus);

        for (Pattern pattern : patterns) {
            Set<String> internalNames = pattern.getVariant().getInternalNames();
            for (String variantName : internalNames) {
                String registryName = makeBlockName(pattern.getName(), variantName);
                RegistryObject<Block> block = BLOCKS.register(
                        registryName,
                        () -> BlockFactory.makeBlockForType(pattern.getType())
                );
                ITEMS.register(registryName, () -> new BlockItem(block.get(), new Item.Properties().tab(BLOCKS_TAB)));
                MOD_BLOCKS_BY_TYPE.get(pattern.getType()).add(block);
            }
        }
    }

    private static String makeBlockName(String pattern, String variant) {
        return variant.isBlank() ? pattern : variant + "_" + pattern;
    }

    private static String makeTabName(String name) {
        return WallpaperCraft.MOD_ID + "." + name;
    }
}
