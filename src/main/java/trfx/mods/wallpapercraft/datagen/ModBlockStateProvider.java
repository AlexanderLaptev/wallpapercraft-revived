package trfx.mods.wallpapercraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.StringUtils;
import trfx.mods.wallpapercraft.WallpaperCraft;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.block.WallpaperBlock;
import trfx.mods.wallpapercraft.init.ModBlocks;

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
        for (RegistryObject<Block> regObject : ModBlocks.BLOCKS.getEntries()) {
            if (!(regObject.get() instanceof WallpaperBlock modBlock)) { continue; }
            WallpaperCraft.LOGGER.debug("Adding model for '{}'", regObject.getId());

            BlockModelBuilder model;
            if (modBlock.getModelType() == Pattern.ModelType.CARPET) {
                model = models().carpet(
                        regObject.getId().getPath(),
                        new ResourceLocation(
                                WallpaperCraft.MOD_ID,
                                ModelProvider.BLOCK_FOLDER + "/" + StringUtils.removeEnd(
                                        regObject.getId().getPath(),
                                        "_" + Pattern.ModelType.CARPET.getSuffix()
                                )
                        )
                );
            } else {
                model = models().cubeAll(regObject.getId().getPath(), blockTexture(modBlock));
            }

            if (modBlock.getPattern().getMaterial() == Pattern.Material.GLASS) {
                model.renderType("minecraft:translucent");
            }

            simpleBlock(modBlock, model);
            simpleBlockItem(modBlock, model);
        }
    }
}
