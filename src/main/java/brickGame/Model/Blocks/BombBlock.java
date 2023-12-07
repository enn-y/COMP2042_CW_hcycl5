package brickGame.Model.Blocks;

import brickGame.Main;
import brickGame.Model.Interface.Blocks;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The BombBlock class represents the bomb block.
 * When hit, it makes the ball invisible for 3 seconds.
 * It extends the BlockModel class and implements the BlockType interface.
 *
 * @author Lua Chong En
 *
 */

public class BombBlock extends BlockModel implements Blocks {
    Main main;

    /**
     * This constructor initializes the bomb block.
     *
     * @param row The row of the block.
     * @param column The column of the block.
     * @param main The main class.
     */

    public BombBlock(int row, int column, Main main) {
        super(row, column, Color.TRANSPARENT, BLOCK_BOMB);
        this.main = main;
    }

    /**
     * The blockType method is called when the bomb block is hit.
     * It makes the ball invisible for 3 seconds.
     */

    public void blockType(){
        main.getBall().setVisible(false);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                main.getBall().setVisible(true);
            }
        }, 1000);
    }
}
