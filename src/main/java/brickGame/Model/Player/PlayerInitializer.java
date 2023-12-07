package brickGame.Model.Player;

import brickGame.Main;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * The PlayerInitializer class represents the player initializer.
 * It is used to initialize the paddle.
 *
 * @author Lua Chong En
 *
 */

public class PlayerInitializer {
    Main main;

    /**
     * Constructor initializes the player initializer.
     * @param main The Main instance to access the components of the game.
     */

    public PlayerInitializer(Main main) {
        this.main = main;
    }

    /**
     * The initializePaddle method is used to initialize the paddle.
     * It sets the x-coordinate and y-coordinate of the paddle.
     * It also sets the image of the paddle.
     */

    public void initializePaddle() {
        main.getPlayer().setX(main.getPlayer().paddleXPosition);
        main.getPlayer().setY(main.getPlayer().paddleYPosition);
        main.getPlayer().setFill(new ImagePattern(new Image("block.jpg")));
    }
}
