package brickGame.Model.Blocks;

import brickGame.Main;
import brickGame.Model.Interface.BlockType;
import javafx.application.Platform;
import javafx.scene.paint.Color;

/**
 * The ChocolateBlock class represents the chocolate block.
 * When the ball hits this block, a bonus item will be created.
 *
 * @author Lua Chong En
 *
 */

public class ChocolateBlock extends BlockModel implements BlockType {
    Main main;

    /**
     * Constructor for the ChocolateBlock class.
     * @param row the row of the block.
     * @param column the column of the block.
     * @param main The Main class instance to access the components of the game.
     */
    public ChocolateBlock(int row, int column, Main main) {
        super(row, column, Color.TRANSPARENT, BLOCK_CHOCOLATE);
        this.main = main;
    }

    /**
     * The blockType method manages the behavior of the chocolate block.
     * It creates a bonus item when the ball hits the chocolate block.
     */

    public void blockType(){
        final Bonus choco = new Bonus(row, column);
        choco.timeCreated = main.getPlayer().currentTime;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                main.getGameScreen().root.getChildren().add(choco.chocolateBlock);
            }
        });
        main.getEngine().bonusItems.add(choco);
    }
}
