package brickGame.Model.Player;

import brickGame.Main;
import brickGame.Model.Interface.Playable;
import javafx.scene.shape.Rectangle;

/**
 * The PlayerModel class represents the player model.
 * It is used to create the paddle.
 * It also contains the methods to move the paddle.
 * The class includes a thread-safe singleton to ensure a single instance.
 *
 * @author Lua Chong En
 *
 */

public class PlayerModel extends Rectangle implements Playable {
    Main main;
    public int paddleWidth = 130;
    public int paddleHeight = 20;
    public double paddleXPosition = 0.0f;
    public double paddleCenter;
    public double paddleYPosition = 670.0f;
    public int paddleWidthHalf = paddleWidth / 2;
    public static int paddleLEFT = 1;
    public static int paddleRIGHT = 2;
    public long hitTime  = 0;
    public boolean existHeartBlock = false;
    public int destroyedBlockCount = 0;
    public int numberOfHearts = 3;
    public long currentTime = 0;

    private static PlayerModel instance;

    /**
     * Constructor used to create the paddle.
     * @param main The Main instance to access the components of the game.
     */

    private PlayerModel(Main main) {
        super(0, 0, 130, 20);
        this.main = main;
    }

    /**
     * This method is used to create a thread-safe singleton.
     * @param main The Main instance to access the components of the game.
     * @return The instance of the player model.
     */

    public static synchronized PlayerModel getInstance(Main main) {
        if (instance == null) {
            instance = new PlayerModel(main);
        }
        return instance;
    }

    /**
     * The move method is used to move the paddle.
     * @param direction The direction of the paddle. Either left or right.
     */

    public void move(final int direction) {
        new Thread(() -> {
            int sleepTime = 4;
            for (int i = 0; i < 30; i++) {
                if (paddleXPosition == (main.getGameScreen().windowWidth - paddleWidth) && direction == paddleRIGHT) {
                    return;
                }
                if (paddleXPosition == 0 && direction == paddleLEFT) {
                    return;
                }
                if (direction == paddleRIGHT) {
                    paddleXPosition++;
                } else {
                    paddleXPosition--;
                }
                paddleCenter = paddleXPosition + paddleWidthHalf;
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i >= 20) {
                    sleepTime = i;
                }
            }
        }).start();
    }

    /**
     * The blockDestroyedCount method is used to check the number of destroyed blocks.
     * If the number of destroyed blocks is equal to the number of blocks, the player wins the level, and moves to the next level.
     */

    public void blockDestroyedCount() {
        if (main.getPlayer().destroyedBlockCount == main.getEngine().blocks.size()) {
            main.getLevelManager().nextLevel();
        }
    }
}
