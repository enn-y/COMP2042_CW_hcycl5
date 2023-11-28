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
    public int paddleWidth = 130; //Width of paddle
    public int paddleHeight = 30; //Height of paddle
    public double paddleXPosition = 0.0f; //Variable for the paddle that the user controls
    public double paddleCenter; //Center of paddle
    public double paddleYPosition = 640.0f; //y-coordinate of the top position of paddle
    public int paddleWidthHalf = paddleWidth / 2; //Half of the width of paddle
    public static int paddleLEFT = 1; //Direction of paddle
    public static int paddleRIGHT = 2; //Direction of paddle
    public long hitTime  = 0; //Time of hit, initialized at 0, used to check if ball is still on paddle
    public boolean existHeartBlock = false; //Status of heart block
    public int destroyedBlockCount = 0; //Number of destroyed blocks
    public int numberOfHearts = 3; //Number of hearts, initialized at 3
    public long currentTime = 0; //Time, initialized at 0, increases by 1 every second

    private static PlayerModel instance;

    /**
     * Constructor used to create the paddle.
     * @param main The Main instance to access the components of the game.
     */

    private PlayerModel(Main main) {
        super(0, 0, 130, 30);
        this.main = main;
    }

    /**
     * This method is used to create a thread-safe singleton.
     * @param main The Main instance to access the components of the game.
     * @return The instance of the player model.
     */

    public static synchronized PlayerModel getInstance(Main main) { //Using thread safe singleton
        if (instance == null) {
            instance = new PlayerModel(main);
        }
        return instance;
    }

    /**
     * The move method is used to move the paddle.
     * @param direction The direction of the paddle. Either left or right.
     */

    public void move(final int direction) { //Move paddle method
        new Thread(new Runnable() { //Thread runs in parallel with main thread, using the runnable interface
            @Override
            public void run() {
                int sleepTime = 4; //Delays the movement of the paddle because if not, the paddle will move too fast
                for (int i = 0; i < 30; i++) { //For loop to move the paddle, condition is 30 because the paddle will move 30 pixels
                    if (paddleXPosition == (main.getGameScreen().windowWidth - paddleWidth) && direction == paddleRIGHT) { //If the paddle is at the right wall and the direction is right, prevents paddle from moving out of bounds
                        return; //Stop Moving
                    }
                    if (paddleXPosition == 0 && direction == paddleLEFT) { //If the paddle is at the left wall and the direction is left, prevents paddle from moving out of bounds
                        return; //Stop Moving
                    }
                    if (direction == paddleRIGHT) { //Updates the x-coordinate of the paddle to move it to the right
                        paddleXPosition++;
                    } else {
                        paddleXPosition--;
                    }
                    paddleCenter = paddleXPosition + paddleWidthHalf; //Position the paddle accurately
                    try { //Control the speed of the paddle, sleep allows the paddle to move smoothly
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();
    }

    /**
     * The blockDestroyedCount method is used to check the number of destroyed blocks.
     * If the number of destroyed blocks is equal to the number of blocks, the player wins the level, and moves to the next level.
     */

    public void blockDestroyedCount() { //Check the number of destroyed blocks
        if (main.getPlayer().destroyedBlockCount == main.getEngine().blocks.size()) { //If the number of destroyed blocks is equal to the number of blocks
            //TODO win level todo...
            //System.out.println("You Win");
            main.getLevelManager().nextLevel();
        }
    }
}
