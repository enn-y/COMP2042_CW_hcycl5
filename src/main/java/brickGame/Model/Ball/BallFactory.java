package brickGame.Model.Ball;

import brickGame.Main;
import brickGame.Model.Blocks.BlockModel;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.Random;

/**
 * The BallFactory class is responsible for initializing the ball.
 * It sets the position, center, radius, and the image of the ball.
 *
 * @author Lua Chong En
 *
 */

public class BallFactory {
    Main main;

    /**
     * Constructor for BallFactory class.
     * @param main The Main class instance to access the components of the game.
     */

    public BallFactory(Main main) {
        this.main = main;
    }

    /**
     * The initializeBall method initializes the ball.
     * It sets the position, center, radius, and the image of the ball.
     * The ball position is a random x and y-coordinate within the game window.
     * The center of the ball is the same as the position of the ball.
     */

    public void initializeBall() {
        Random random = new Random();
        main.getBall().setBallXCoordinate(random.nextInt(main.getGameScreen().windowWidth) + 1);
        main.getBall().setBallYCoordinate(random.nextInt(main.getGameScreen().windowHeight - 200) + ((main.currentLevel + 1) * BlockModel.getHeight()) + 15);
        main.getBall().setCenterX(main.getBall().getBallXCoordinate());
        main.getBall().setCenterY(main.getBall().getBallYCoordinate());
        main.getBall().setRadius(main.getBall().ballRadius);
        main.getBall().setFill(new ImagePattern(new Image("ball.png")));
    }
}
