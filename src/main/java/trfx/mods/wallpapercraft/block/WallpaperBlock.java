package trfx.mods.wallpapercraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.autogen.variant.VariantsDefinition;

import java.util.List;
import java.util.function.ToIntFunction;

public class WallpaperBlock extends Block {
    private static final ToIntFunction<BlockState> DARK_BLOCK_FUNCTION = (state) -> 0;
    private static final ToIntFunction<BlockState> LIGHT_BLOCK_FUNCTION = (state) -> 15;

    private final Pattern pattern;
    private final String variant;
    private final Pattern.ModelType modelType;

    public final int flammability;
    public final int fireSpreadSpeed;

    public WallpaperBlock(
            Pattern pattern,
            String variant,
            Pattern.ModelType modelType,
            Block template,
            boolean emitsLight
    ) {
        super(Properties.copy(template).lightLevel(emitsLight ? LIGHT_BLOCK_FUNCTION : DARK_BLOCK_FUNCTION));
        this.pattern = pattern;
        this.variant = variant;
        this.modelType = modelType;

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

    public String scrollVariant(boolean direction) {
        VariantsDefinition.Group group = pattern.getVariantsDefinition().getGroupForVariant(variant);
        List<String> variants = group.getVariants();
        int index = variants.indexOf(variant);
        return variants.get(Math.floorMod(index + (direction ? 1 : -1), variants.size()));
    }

    public static String getRegistryName(
            Pattern pattern,
            String variant,
            Pattern.ModelType modelType
    ) {
        StringBuilder sb = new StringBuilder(pattern.getName());
        if (!variant.isBlank()) {
            sb.append("_");
            sb.append(variant);
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

    public String getVariant() {
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
