package brickGame.Model;

import brickGame.Main;
import javafx.application.Platform;

public class LevelManager {

    Main main;
    Ball ball;

    public LevelManager(Main main, Ball ball) {
        this.main = main;
        this.ball = ball;
    }

    public void nextLevel() { //Go to the next level
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ball.ballHorizontalSpeed = 1.000;

                    //engine.stop();
                    ball.resetCollideFlags();
                    ball.goDownBall = true;

                    main.goldBall = false;
                    main.existHeartBlock = false;

                    main.hitTime = 0;
                    main.currentTime = 0;
                    main.goldTime = 0;

                    main.engine.stop();
                    main.blocks.clear();
                    main.bonusItems.clear();
                    main.destroyedBlockCount = 0;
                    main.start(main.primaryStage);

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
