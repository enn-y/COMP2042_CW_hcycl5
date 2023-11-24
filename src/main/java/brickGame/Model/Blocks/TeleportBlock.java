package brickGame.Model.Blocks;

import brickGame.Main;
import brickGame.Model.Interface.BlockType;
import javafx.scene.paint.Color;

import java.util.Random;

public class TeleportBlock extends Block implements BlockType {
        Main main;

        public TeleportBlock(int row, int column, Main main) {
            super(row, column, Color.TRANSPARENT, BLOCK_TELEPORT);
            this.main = main;
        }

        public void blockType() {
            Random random = new Random();
            double randomX = random.nextDouble() * main.getGameScreen().windowWidth;
            double randomY = random.nextDouble() * main.getGameScreen().windowHeight;

            // Teleport the ball to the random coordinates
            main.getBall().ballXCoordinate = randomX;
            main.getBall().ballYCoordinate = randomY;
        }

}
