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
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.ModInit;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(
            DataGenerator pGenerator,
            String modId,
            @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(pGenerator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        // Wool
        var woolTag = tag(BlockTags.WOOL);
        var occludesVibrationsTag = tag(BlockTags.OCCLUDES_VIBRATION_SIGNALS);
        for (RegistryObject<Block> woolLike : ModInit.MOD_BLOCKS_BY_TYPE.get(Pattern.Type.WOOL)) {
            woolTag.add(woolLike.get());
            occludesVibrationsTag.add(woolLike.get());
        }

        // Carpet
        var carpetsTag = tag(BlockTags.WOOL_CARPETS);
        for (RegistryObject<Block> carpet : ModInit.MOD_BLOCKS_BY_TYPE.get(Pattern.Type.CARPET)) {
            carpetsTag.add(carpet.get());
        }

        // Glass
        var impermeableTag = tag(BlockTags.IMPERMEABLE);
        var glassTag = tag(ForgeRegistries.BLOCKS.tags().createTagKey(new ResourceLocation("forge:glass")));
        for (RegistryObject<Block> carpet : ModInit.MOD_BLOCKS_BY_TYPE.get(Pattern.Type.GLASS)) {
            impermeableTag.add(carpet.get());
        }

        // Planks
        var planksTag = tag(BlockTags.PLANKS);
        var mineableWithAxeTag = tag(BlockTags.MINEABLE_WITH_AXE);
        for (RegistryObject<Block> planks : ModInit.MOD_BLOCKS_BY_TYPE.get(Pattern.Type.PLANKS)) {
            planksTag.add(planks.get());
            mineableWithAxeTag.add(planks.get());
        }

        // Normal and lamp
        var mineableWithPickaxeTag = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        for (RegistryObject<Block> block : ModInit.MOD_BLOCKS_BY_TYPE.get(Pattern.Type.STONE)) {
            mineableWithPickaxeTag.add(block.get());
        }
        for (RegistryObject<Block> block : ModInit.MOD_BLOCKS_BY_TYPE.get(Pattern.Type.LAMP)) {
            mineableWithPickaxeTag.add(block.get());
        }
    }
}
