package brickGame.Model.Blocks;

import brickGame.Main;
import brickGame.Model.Interface.BlockType;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * The QuestionBlock class represents the question block.
 * When destroyed, it will either give the player a heart or take away a heart.
 * It will also display a message to the player as well as check if the game is over.
 *
 * @author Lua Chong En
 *
 */

public class QuestionBlock extends BlockModel implements BlockType {
    Main main;

    /**
     * Constructor for QuestionBlock class.
     * @param row The row of the block.
     * @param column The column of the block.
     * @param main The Main class instance to access the components of the game.
     */

    public QuestionBlock(int row, int column, Main main) {
        super(row, column, Color.TRANSPARENT, BLOCK_QUESTION);
        this.main = main;
    }

    /**
     * The blockType method is called when the block is destroyed.
     * When destroyed, it either gives the player a heart or takes away a heart, then displays a corresponding message to the player.
     */

    public void blockType(){
        Random random = new Random();
        int r = random.nextInt(100);
        if (r % 2 == 0){
            main.getPlayer().numberOfHearts++;
            main.getScore().showMessage("You got a heart!", main);
        } else {
            main.getPlayer().numberOfHearts--;
            main.getScore().showMessage("You lost a heart!", main);
        }
        main.getBall().checkGameOver();
    }
}
