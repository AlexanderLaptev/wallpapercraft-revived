package trfx.mods.wallpapercraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.Validate;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.autogen.variant.VariantList;
import trfx.mods.wallpapercraft.autogen.variant.VariantListCache;

import java.util.List;
import java.util.function.ToIntFunction;

public class WallpaperBlock extends Block {
    private static final ToIntFunction<BlockState> DARK_BLOCK_FUNCTION = (state) -> 0;
    private static final ToIntFunction<BlockState> LIGHT_BLOCK_FUNCTION = (state) -> 15;

    private final Pattern pattern;
    private final VariantList.Variant variant;
    private final Pattern.ModelType modelType;
    private final List<VariantList.Variant> variants;

    public final int flammability;
    public final int fireSpreadSpeed;

    public WallpaperBlock(
            Pattern pattern,
            VariantList.Variant variant,
            Pattern.ModelType modelType,
            Block template,
            boolean emitsLight
    ) {
        super(Properties.copy(template).lightLevel(emitsLight ? LIGHT_BLOCK_FUNCTION : DARK_BLOCK_FUNCTION));
        this.pattern = pattern;
        this.variant = variant;
        this.modelType = modelType;
        this.variants = VariantListCache.getVariantList(pattern.getVariantListName())
                .getVariants()
                .values()
                .stream()
                .toList();

        flammability = getFlammabilityFromFire(template);
        fireSpreadSpeed = getFireSpreadSpeedFromFire(template);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return flammability;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return fireSpreadSpeed;
    }

    public VariantList.Variant scrollVariant(boolean direction) {
        int index = variants.indexOf(variant);
        Validate.isTrue(index >= 0);
        return variants.get(Math.floorMod(index + (direction ? 1 : -1), variants.size()));
    }

    public static String getRegistryName(Pattern pattern, VariantList.Variant variant, Pattern.ModelType modelType) {
        StringBuilder sb = new StringBuilder(pattern.getName());
        String variantName = variant.getInternalName();
        if (!variantName.isBlank()) {
            sb.append("_");
            sb.append(variantName);
        }
        String modelSuffix = modelType.getSuffix();
        if (!modelSuffix.isBlank()) {
            sb.append("_");
            sb.append(modelSuffix);
        }
        return sb.toString();
    }

    public Pattern getPattern() {
        return pattern;
    }

    public VariantList.Variant getVariant() {
        return variant;
    }

    public Pattern.ModelType getModelType() {
        return modelType;
    }

    @SuppressWarnings("deprecation")
    private static int getFlammabilityFromFire(Block block) {
        return ((FireBlock) Blocks.FIRE).getBurnOdds(block.defaultBlockState());
    }

    @SuppressWarnings("deprecation")
    private static int getFireSpreadSpeedFromFire(Block block) {
        return ((FireBlock) Blocks.FIRE).getIgniteOdds(block.defaultBlockState());
    }
}
