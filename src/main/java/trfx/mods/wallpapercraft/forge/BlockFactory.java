package trfx.mods.wallpapercraft.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import trfx.mods.wallpapercraft.core.pattern.Pattern;

import java.util.function.ToIntFunction;

public class BlockFactory {
    private static final ToIntFunction<BlockState> DARK_BLOCK_FUNCTION = (state) -> 0;

    private static final ToIntFunction<BlockState> LIGHT_BLOCK_FUNCTION = (state) -> 15;

    public static Block makeBlockForType(Pattern.Type type) {
        return switch (type) {
            case STONE -> makeBasicBlock(Blocks.STONE, false);
            case GLASS -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS));
            case WOOL -> makeBasicBlock(Blocks.WHITE_WOOL, false);
            case LAMP -> makeBasicBlock(Blocks.STONE, true);
            case PLANKS -> makeBasicBlock(Blocks.OAK_PLANKS, false);

            case CARPET -> new CarpetBlock(
                    BlockBehaviour.Properties
                            .copy(Blocks.WHITE_CARPET)
            ) {
                public final int flammability = getFlammabilityFromFire(Blocks.WHITE_CARPET);

                public final int fireSpreadSpeed = getFireSpreadSpeedFromFire(Blocks.WHITE_CARPET);

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return flammability;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return fireSpreadSpeed;
                }
            };
        };
    }

    private static Block makeBasicBlock(Block template, boolean isEmittingLight) {
        return new Block(
                BlockBehaviour.Properties
                        .copy(template)
                        .lightLevel(isEmittingLight ? LIGHT_BLOCK_FUNCTION : DARK_BLOCK_FUNCTION)
        ) {
            public final int flammability = getFlammabilityFromFire(template);

            public final int fireSpreadSpeed = getFireSpreadSpeedFromFire(template);

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return fireSpreadSpeed;
            }
        };
    }

    private static int getFlammabilityFromFire(Block block) {
        return ((FireBlock) Blocks.FIRE).getBurnOdds(block.defaultBlockState());
    }

    private static int getFireSpreadSpeedFromFire(Block block) {
        return ((FireBlock) Blocks.FIRE).getIgniteOdds(block.defaultBlockState());
    }
}
