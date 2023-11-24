package brickGame.Model.Blocks;

import brickGame.Main;
import brickGame.Model.Interface.BlockType;
import javafx.scene.paint.Color;

public class SpeedBlock extends Block implements BlockType {
        Main main;

        public SpeedBlock(int row, int column, Main main) {
            super(row, column, Color.TRANSPARENT, BLOCK_SPEED);
            this.main = main;
        }

        public void blockType() {
            main.getBall().ballHorizontalSpeed *= 2; // Boosting the speed by a factor of 2
        }
}
