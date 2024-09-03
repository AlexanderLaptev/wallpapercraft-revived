package trfx.mods.wallpapercraft.forge.datagen.loot;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import trfx.mods.wallpapercraft.core.pattern.Pattern;
import trfx.mods.wallpapercraft.forge.ModInit;

public class ModBlockLoot extends BlockLoot {
    @Override
    protected void addTables() {
        for (var entry : ModInit.MOD_BLOCKS_BY_TYPE.entrySet()) {
            for (RegistryObject<Block> block : entry.getValue()) {
                if (entry.getKey() == Pattern.Type.GLASS) {
                    dropWhenSilkTouch(block.get());
                } else {
                    dropSelf(block.get());
                }
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModInit.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
    }
}
