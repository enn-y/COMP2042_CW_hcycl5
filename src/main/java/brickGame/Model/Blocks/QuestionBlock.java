package brickGame.Model.Blocks;

import brickGame.Main;
import javafx.scene.paint.Color;

import java.util.Random;

public class QuestionBlock extends Block{
    Main main;
    public QuestionBlock(int row, int column, Main main) {
        super(row, column, Color.TRANSPARENT, BLOCK_QUESTION);
        this.main = main;
    }

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
