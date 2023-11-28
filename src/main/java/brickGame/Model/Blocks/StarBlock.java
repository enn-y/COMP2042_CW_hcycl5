package brickGame.Model.Blocks;

import brickGame.Main;
import brickGame.Model.Interface.BlockType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * The StarBlock class is used to create a star block.
 * This block turns the ball into a gold ball for 10 seconds.
 *
 * @author Lua Chong En
 *
 */

public class StarBlock extends BlockModel implements BlockType {
    Main main;

    /**
     * Constructor is used to create a star block.
     * @param row The row of the block.
     * @param column The column of the block.
     * @param main The Main instance to access the components of the game.
     */

    public StarBlock(int row, int column, Main main) {
        super(row, column, Color.TRANSPARENT, BLOCK_STAR);
        this.main = main;
    }

    /**
     * The blockType method is used to turn the ball into a gold ball for 10 seconds.
     */

    public void blockType(){
        main.getBall().goldTime = main.getPlayer().currentTime;
        main.getBall().setFill(new ImagePattern(new Image("goldball.png")));
        System.out.println("gold ball");
        main.getGameScreen().root.getStyleClass().add("goldRoot");
        main.getBall().goldBall = true;
    }
}
