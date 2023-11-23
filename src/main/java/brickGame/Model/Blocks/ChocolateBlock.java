package brickGame.Model.Blocks;

import brickGame.Main;
import brickGame.Model.Bonus;
import javafx.application.Platform;
import javafx.scene.paint.Color;

public class ChocolateBlock extends Block{
    Main main;
    public ChocolateBlock(int row, int column, Main main) {
        super(row, column, Color.TRANSPARENT, BLOCK_CHOCOLATE);
        this.main = main;
    }

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
