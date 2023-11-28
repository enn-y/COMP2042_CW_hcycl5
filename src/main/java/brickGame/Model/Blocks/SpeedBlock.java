package brickGame.Model.Blocks;

import brickGame.Main;
import brickGame.Model.Interface.BlockType;
import javafx.scene.paint.Color;

/**
 * The SpeedBlock class is used to create a speed block.
 * This block boosts the speed of the ball by a factor of 2.
 *
 * @author Lua Chong En
 *
 */

public class SpeedBlock extends BlockModel implements BlockType {
        Main main;

    /**
     * Constructor is used to create a speed block.
     * @param row The row of the block.
     * @param column The column of the block.
     * @param main The Main instance to access the components of the game.
     */

    public SpeedBlock(int row, int column, Main main) {
            super(row, column, Color.TRANSPARENT, BLOCK_SPEED);
            this.main = main;
        }

    /**
     * The blockType method is used to boost the speed of the ball by a factor of 2.
     */

    public void blockType() {
        main.getBall().ballHorizontalSpeed *= 2; // Boosting the speed by a factor of 2
    }
}
