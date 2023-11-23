package brickGame.Model.Blocks;

import brickGame.Main;
import javafx.scene.paint.Color;

public class HeartBlock extends Block{
    Main main;
    public HeartBlock(int row, int column, Main main) {
        super(row, column, Color.TRANSPARENT, BLOCK_HEART);
        this.main = main;
    }

    public void blockType(){
        main.getPlayer().numberOfHearts++;
        main.getScore().showMessage("You got a heart!", main);
    }
}
