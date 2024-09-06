package trfx.mods.wallpapercraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.autogen.variant.VariantList;

public class GlassWallpaperBlock extends WallpaperBlock {
    public GlassWallpaperBlock(
            Pattern pattern,
            VariantList.Variant variant,
            Pattern.ModelType modelType,
            boolean emitsLight
    ) {
        super(pattern, variant, modelType, Blocks.GLASS, emitsLight);
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @SuppressWarnings("deprecation")
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true;
    }

    @SuppressWarnings("deprecation")
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.is(this) || super.skipRendering(pState, pAdjacentBlockState, pSide);
    }
}
