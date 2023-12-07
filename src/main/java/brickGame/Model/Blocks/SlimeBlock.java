package brickGame.Model.Blocks;

import brickGame.Main;
import brickGame.Model.Interface.Blocks;
import javafx.scene.paint.Color;

/**
 * The SlimeBlock class represents the SlimeBlock object.
 * When the ball hits this block, the ball's horizontal speed is reduced by 70% for 3 seconds.
 */

public class SlimeBlock extends BlockModel implements Blocks {
    Main main;

    /**
     * Constructor initializes the SlimeBlock object.
     * @param row The row of the block.
     * @param column The column of the block.
     * @param main The Main class instance to access the components of the game.
     */
    public SlimeBlock(int row, int column, Main main) {
        super(row, column, Color.TRANSPARENT, BLOCK_SLIME);
        this.main = main;
    }

    /**
     * The blockType method is called when the ball hits the SlimeBlock.
     * The ball's horizontal speed is reduced by 70% for 3 seconds.
     */

    public void blockType(){
        main.getBall().ballHorizontalSpeed *= 0.3;
    }
}
