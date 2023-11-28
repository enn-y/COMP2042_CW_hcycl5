package brickGame.Model.Blocks;

import brickGame.Main;
import brickGame.Model.Interface.BlockType;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * The TeleportBlock class is used to create a teleport block.
 * This block teleports the ball to a random location.
 *
 * @author Lua Chong En
 *
 */

public class TeleportBlock extends BlockModel implements BlockType {
        Main main;

        /**
         * Constructor is used to create a teleport block.
         * @param row The row of the block.
         * @param column The column of the block.
         * @param main The Main instance to access the components of the game.
         */

        public TeleportBlock(int row, int column, Main main) {
            super(row, column, Color.TRANSPARENT, BLOCK_TELEPORT);
            this.main = main;
        }

        /**
         * The blockType method is used to teleport the ball to a random location.
         */

        public void blockType() {
            Random random = new Random();
            double randomX = random.nextDouble() * main.getGameScreen().windowWidth;
            double randomY = random.nextDouble() * main.getGameScreen().windowHeight;

            // Teleport the ball to the random coordinates
            main.getBall().ballXCoordinate = randomX;
            main.getBall().ballYCoordinate = randomY;
        }

}
