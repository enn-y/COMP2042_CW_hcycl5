package brickGame.Model.Blocks;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.junit.Test;

import static org.junit.Assert.*;

public class BonusTest {
    @Test
    public void testCreate() {
        new Thread(() -> {
            Bonus bonus = new Bonus(1, 1);

            Rectangle chocolateBlock = bonus.chocolateBlock;

            assertNotNull(chocolateBlock);
            assertEquals(10, chocolateBlock.getWidth(), 0);
            assertEquals(10, chocolateBlock.getHeight(), 0);
            assertEquals(100, chocolateBlock.getX(), 0);
            assertEquals(50, chocolateBlock.getY(), 0);
            assertTrue(chocolateBlock.getFill() instanceof ImagePattern);
        });
    }
}