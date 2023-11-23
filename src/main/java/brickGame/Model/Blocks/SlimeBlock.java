package brickGame.Model.Blocks;

import brickGame.Main;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SlimeBlock extends Block{
    Main main;
    public SlimeBlock(int row, int column, Main main) {
        super(row, column, Color.TRANSPARENT, BLOCK_SLIME);
        this.main = main;
    }

    public void blockType(){
        main.getBall().ballHorizontalSpeed *= 0.3;
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.getBall().ballHorizontalSpeed *= 2;
            }
        });
    }
}
