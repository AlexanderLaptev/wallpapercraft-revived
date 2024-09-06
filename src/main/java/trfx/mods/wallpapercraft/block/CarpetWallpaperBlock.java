package trfx.mods.wallpapercraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;
import trfx.mods.wallpapercraft.autogen.variant.VariantList;

public class CarpetWallpaperBlock extends WallpaperBlock {
    public CarpetWallpaperBlock(
            Pattern pattern,
            VariantList.Variant variant,
            Block template,
            boolean emitsLight
    ) {
        super(pattern, variant, Pattern.ModelType.CARPET, template, emitsLight);
    }

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    @SuppressWarnings("deprecation")
    public VoxelShape getShape(
            BlockState pState,
            BlockGetter pLevel,
            BlockPos pPos,
            CollisionContext pContext
    ) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    public BlockState updateShape(
            BlockState pState,
            Direction pDirection,
            BlockState pNeighborState,
            LevelAccessor pLevel,
            BlockPos pCurrentPos,
            BlockPos pNeighborPos
    ) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(
                pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos
        );
    }

    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return !pLevel.isEmptyBlock(pPos.below());
    }
}
