package trfx.mods.wallpapercraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import trfx.mods.wallpapercraft.init.ModBlocks;
import trfx.mods.wallpapercraft.WallpaperCraft;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(
            DataGenerator pGenerator,
            String modId,
            @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(pGenerator, modId, existingFileHelper);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void addTags() {
        var woolTag = tag(BlockTags.WOOL);
        var occludesVibrationsTag = tag(BlockTags.OCCLUDES_VIBRATION_SIGNALS);
        var carpetsTag = tag(BlockTags.WOOL_CARPETS);
        var impermeableTag = tag(BlockTags.IMPERMEABLE);
        var glassTag = tag(ForgeRegistries.BLOCKS.tags().createTagKey(new ResourceLocation("forge:glass")));
        var planksTag = tag(BlockTags.PLANKS);
        var mineableWithAxeTag = tag(BlockTags.MINEABLE_WITH_AXE);
        var mineableWithPickaxeTag = tag(BlockTags.MINEABLE_WITH_PICKAXE);

        for (RegistryObject<Block> regObject : ModBlocks.BLOCKS.getEntries()) {
            WallpaperCraft.LOGGER.debug("Adding tags for '{}'", regObject.getId());
            ModBlocks.BlockInfo info = ModBlocks.BLOCK_INFO.get(regObject);
            Block block = regObject.get();

            if (info.modelType == Pattern.ModelType.CARPET) {
                carpetsTag.add(block);
            } else {
                switch (info.pattern.getMaterial()) {
                    case STONE -> mineableWithPickaxeTag.add(block);
                    case WOOL -> {
                        woolTag.add(block);
                        occludesVibrationsTag.add(block);
                    }
                    case GLASS -> {
                        impermeableTag.add(block);
                        glassTag.add(block);
                    }
                    case PLANKS -> {
                        planksTag.add(block);
                        mineableWithAxeTag.add(block);
                    }
                }
            }

        }
    }
}
