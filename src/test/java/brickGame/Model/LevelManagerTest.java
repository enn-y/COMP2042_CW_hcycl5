package brickGame.Model;

import brickGame.Main;
import brickGame.Model.Blocks.BlockModel;
import brickGame.Model.Blocks.Bonus;
import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class LevelManagerTest {
    Main main = new Main();
    LevelManager levelManager = new LevelManager(main);

    @Test
    public void testNextLevel() {
        new Thread(() -> {
            main.getEngine().blocks.add(new BlockModel(0, 0, Color.GREEN, BlockModel.BLOCK_NORMAL)); // Add a block to the list
            main.getEngine().blocks.add(new BlockModel(1, 1, Color.BLUE, BlockModel.BLOCK_NORMAL));
            main.getEngine().bonusItems.add(new Bonus(2, 2)); // Add a bonus item to the list

            main.getBall().ballHorizontalSpeed = 1.000; // Set the ball speed to a non-default value
            main.getBall().goldBall = true; // Set the gold ball flag to true
            main.getPlayer().existHeartBlock = true; // Set the heart block flag to true
            main.getPlayer().hitTime = 10; // Set the hit time to a non-default value
            main.getPlayer().currentTime = 20; // Set the current time to a non-default value
            main.getBall().goldTime = 30; // Set the gold time to a non-default value
            main.getEngine().stop(); // Stop the game engine

            levelManager.nextLevel(); // Call the method to be tested

            assertEquals(1.000, main.getBall().ballHorizontalSpeed, 0.001); // Assert that the ball speed is reset to the default value
            assertFalse(main.getBall().goldBall); // Assert that the gold ball flag is reset to false
            assertFalse(main.getPlayer().existHeartBlock); // Assert that the heart block flag is reset to false
            assertEquals(0, main.getPlayer().hitTime); // Assert that the hit time is reset to the default value
            assertEquals(0, main.getPlayer().currentTime); // Assert that the current time is reset to the default value
            assertEquals(0, main.getBall().goldTime); // Assert that the gold time is reset to the default value
            assertTrue(main.getEngine().blocks.isEmpty()); // Assert that the block list is empty
            assertTrue(main.getEngine().bonusItems.isEmpty()); // Assert that the bonus item list is empty
            assertEquals(0, main.getPlayer().destroyedBlockCount); // Assert that the destroyed block count is reset to the default value
            assertNull(main.getGameScreen().primaryStage); // Assert that the primary stage is null
        });
    }
}