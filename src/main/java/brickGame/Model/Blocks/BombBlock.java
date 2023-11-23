package brickGame.Model.Blocks;

import brickGame.Main;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

public class BombBlock extends Block{
    Main main;

    public BombBlock(int row, int column, Main main) {
        super(row, column, Color.TRANSPARENT, BLOCK_BOMB);
        this.main = main;
    }

    public void blockType(){
        main.getBall().setVisible(false);

        // Set up a timer to make the ball visible again after 3 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Make the ball visible again after 3 seconds
                main.getBall().setVisible(true);
            }
        }, 1000);
    }
}
