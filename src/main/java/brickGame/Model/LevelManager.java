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
                    main.resetCollideFlags();
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
}
