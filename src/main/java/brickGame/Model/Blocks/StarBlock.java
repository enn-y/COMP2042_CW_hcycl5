package brickGame.Model.Blocks;

import brickGame.Main;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class StarBlock extends Block{
    Main main;
    public StarBlock(int row, int column, Main main) {
        super(row, column, Color.TRANSPARENT, BLOCK_STAR);
        this.main = main;
    }

    public void blockType(){
        main.getBall().goldTime = main.getPlayer().currentTime;
        main.getBall().setFill(new ImagePattern(new Image("goldball.png")));
        System.out.println("gold ball");
        main.getGameScreen().root.getStyleClass().add("goldRoot");
        main.getBall().goldBall = true;
    }
}
