package brickGame.Model;

import brickGame.Main;
import javafx.application.Platform;

/**
 * The LevelManager class represents the level manager.
 * It is used to manage the levels.
 *
 * @author Lua Chong En
 *
 */

public class LevelManager {

    Main main;

    /**
     * Constructor initializes the level manager.
     * @param main The Main instance to access the components of the game.
     */

    public LevelManager(Main main) {
        this.main = main;
    }

    /**
     * The nextLevel method is used to go to the next level.
     * It resets the horizontal speed of the ball, and the collide flags of the ball.
     * Also sets the goDownBall, goldBall, and existHeartBlock flags to true, false, and false respectively.
     * And resets the hitTime, currentTime, and goldTime to 0.
     * It also clears the blocks and bonus items.
     */

    public void nextLevel() {
        Platform.runLater(() -> {
            try {
                main.getBall().ballHorizontalSpeed = 1.000;

                main.getBall().resetCollideFlags();
                main.getBall().goDownBall = true;

                main.getBall().goldBall = false;
                main.getPlayer().existHeartBlock = false;
                main.getEngine().stop();
                main.getPlayer().hitTime = 0;
                main.getPlayer().currentTime = 0;
                main.getBall().goldTime = 0;

                main.getEngine().blocks.clear();
                main.getEngine().bonusItems.clear();
                main.getPlayer().destroyedBlockCount = 0;
                main.start(main.getGameScreen().primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}