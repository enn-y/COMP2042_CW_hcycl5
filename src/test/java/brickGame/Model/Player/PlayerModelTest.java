package brickGame.Model.Player;

import brickGame.Main;
import brickGame.Model.Blocks.BlockModel;
import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerModelTest {
    Main main = new Main();
    PlayerModel playerModel = PlayerModel.getInstance(main);

    @Test
    public void move() {
        new Thread(() -> {
            playerModel.move(PlayerModel.paddleLEFT);
            assertEquals(playerModel.paddleYPosition, playerModel.getY(), 0);
        });
    }

    @Test
    public void blockDestroyedCount() {
        new Thread(() -> {
            main.getEngine().blocks.add(new BlockModel(0, 0, Color.YELLOW, BlockModel.BLOCK_NORMAL));
            main.getEngine().blocks.add(new BlockModel(1, 1, Color.RED, BlockModel.BLOCK_NORMAL));
            main.getEngine().blocks.add(new BlockModel(2, 2, Color.BLUE, BlockModel.BLOCK_NORMAL));

            playerModel.destroyedBlockCount = 3;
            playerModel.blockDestroyedCount();
            assertEquals(1, main.currentLevel);
        });
    }
}