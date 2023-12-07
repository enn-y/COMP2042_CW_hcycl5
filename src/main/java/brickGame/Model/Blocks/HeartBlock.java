package brickGame.Model.Blocks;

import brickGame.Main;
import brickGame.Model.Interface.Blocks;
import javafx.scene.paint.Color;

/**
 * The HeartBlock class represents the heart block.
 * When destroyed it increases the players heart by 1 and displays on the screen "You got a heart!"
 *
 * @author Lua Chong En
 *
 */

public class HeartBlock extends BlockModel implements Blocks {
    Main main;

    /**
     * Constructor for the heart block.
     * @param row The row of the block.
     * @param column The column of the block.
     * @param main The Main class instance to access the components of the game.
     */

    public HeartBlock(int row, int column, Main main) {
        super(row, column, Color.TRANSPARENT, BLOCK_HEART);
        this.main = main;
    }

    /**
     * The blockType method is called when the heart block is destroyed.
     * It increases the players heart by 1 and displays on the screen "You got a heart!"
     */

    public void blockType(){
        main.getPlayer().numberOfHearts++;
        main.getScore().showMessage("You got a heart!", main);
    }
}
