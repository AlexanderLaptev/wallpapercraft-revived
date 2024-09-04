package trfx.mods.wallpapercraft.datagen.loot;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import trfx.mods.wallpapercraft.ModInit;

public class ModBlockLoot extends BlockLoot {
    @Override
    protected void addTables() {
        for (RegistryObject<Block> regObject : ModInit.BLOCKS.getEntries()) {
            ModInit.BlockInfo info = ModInit.BLOCK_INFO.get(regObject);
            switch (info.pattern.getMaterial()) {
                case GLASS, LAMP -> dropWhenSilkTouch(regObject.get());
                default -> dropSelf(regObject.get());
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModInit.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
    }
}
