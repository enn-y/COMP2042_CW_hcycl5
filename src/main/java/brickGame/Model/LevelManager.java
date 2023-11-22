package brickGame.Model;

import brickGame.Main;
import javafx.application.Platform;

public class LevelManager {

    Main main;

    public LevelManager(Main main) {
        this.main = main;
    }

    public void nextLevel() { //Go to the next level
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    main.getBall().ballHorizontalSpeed = 1.000;

                    //engine.stop();
                    main.getBall().resetCollideFlags();
                    main.getBall().goDownBall = true;

                    main.getBall().goldBall = false;
                    main.existHeartBlock = false;

                    main.hitTime = 0;
                    main.currentTime = 0;
                    main.getBall().goldTime = 0;

                    main.getEngine().stop();
                    main.blocks.clear();
                    main.bonusItems.clear();
                    main.destroyedBlockCount = 0;
                    main.start(main.getGameScreen().primaryStage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void blockDestroyedCount() { //Check the number of destroyed blocks
        if (main.destroyedBlockCount == main.blocks.size()) { //If the number of destroyed blocks is equal to the number of blocks
            //TODO win level todo...
            //System.out.println("You Win");
            nextLevel();
        }
    }
}
