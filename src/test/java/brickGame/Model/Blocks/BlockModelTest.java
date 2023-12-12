package brickGame.Model.Blocks;

import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockModelTest {
    @Test
    public void testCheckHitToBlock() {
        BlockModel blockModel = new BlockModel(1, 2, Color.BLUE, BlockModel.BLOCK_NORMAL);

        double xBall = blockModel.blockXCoordinate + blockModel.getWidth() / 2; // Assume ball coordinates are within block boundaries
        double yBall = blockModel.blockYCoordinate + blockModel.getHeight() / 2; // Assume ball coordinates are within block boundaries

        int hitDirection = blockModel.checkHitToBlock(xBall, yBall); // Call the method to be tested

        assertEquals(BlockModel.HIT_BOTTOM, hitDirection); // Assert that the block is hit from the bottom
    }

    @Test
    public void testGetPaddingTop() {
        assertEquals(BlockModel.getPaddingTop(), BlockModel.block.paddingTop);
    }

    @Test
    public void testGetPaddingH() {
        assertEquals(BlockModel.getPaddingH(), BlockModel.block.paddingH);
    }

    @Test
    public void testGetHeight() {
        assertEquals(BlockModel.getHeight(), BlockModel.block.height);
    }

    @Test
    public void testGetWidth() {
        assertEquals(BlockModel.getWidth(), BlockModel.block.width);
    }
}