package trfx.mods.wallpapercraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.ModInit;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(
            DataGenerator gen,
            String modId,
            ExistingFileHelper exFileHelper
    ) {
        super(gen, modId, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (var entry : ModInit.MOD_BLOCKS_BY_TYPE.entrySet()) {
            for (RegistryObject<Block> regObject : entry.getValue()) {
                Block block = regObject.get();

                ModelFile model;
                if (entry.getKey() == Pattern.Type.CARPET) {
                    model = models().carpet(regObject.getId().getPath(), blockTexture(block));
                } else if (entry.getKey() == Pattern.Type.GLASS) {
                    model = models().cubeAll(regObject.getId().getPath(), blockTexture(block)).renderType("minecraft:translucent");
                } else {
                    model = cubeAll(block);
                }

                simpleBlock(block);
                simpleBlockItem(block, model);
            }
        }
    }
}
