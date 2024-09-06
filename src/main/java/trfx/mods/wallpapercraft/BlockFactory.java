package trfx.mods.wallpapercraft;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import trfx.mods.wallpapercraft.autogen.pattern.Pattern;

import java.util.function.ToIntFunction;

public class BlockFactory {
    private static final ToIntFunction<BlockState> DARK_BLOCK_FUNCTION = (state) -> 0;

    private static final ToIntFunction<BlockState> LIGHT_BLOCK_FUNCTION = (state) -> 15;

    public static Block makeBlock(Pattern pattern, Pattern.ModelType modelType) {
        if (modelType == Pattern.ModelType.CARPET) {
            return new CarpetBlock(
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
        } else {
            return makeBasicBlock(
                    getTemplateForMaterial(pattern.getMaterial()),
                    pattern.getMaterial() == Pattern.Material.LAMP
            );
        }
    }

    private static Block getTemplateForMaterial(Pattern.Material material) {
        return switch (material) {
            case STONE -> Blocks.STONE;
            case WOOL -> Blocks.WHITE_WOOL;
            case LAMP -> Blocks.GLOWSTONE;
            case GLASS -> Blocks.GLASS;
            case PLANKS -> Blocks.OAK_PLANKS;
            case BRICKS -> Blocks.BRICKS;
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

    @SuppressWarnings("deprecation")
    private static int getFlammabilityFromFire(Block block) {
        return ((FireBlock) Blocks.FIRE).getBurnOdds(block.defaultBlockState());
    }

    @SuppressWarnings("deprecation")
    private static int getFireSpreadSpeedFromFire(Block block) {
        return ((FireBlock) Blocks.FIRE).getIgniteOdds(block.defaultBlockState());
    }
}
