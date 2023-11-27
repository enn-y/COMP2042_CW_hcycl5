package brickGame.Model.Player;

import brickGame.Main;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class PlayerInitializer {
    Main main;

    public PlayerInitializer(Main main) {
        this.main = main;
    }

    public void initializePaddle() { //Initialize the paddle
        main.getPlayer().setX(main.getPlayer().paddleXPosition); //Set the x-coordinate of the paddle
        main.getPlayer().setY(main.getPlayer().paddleYPosition); //Set the y-coordinate of the paddle
        main.getPlayer().setFill(new ImagePattern(new Image("block.jpg"))); //Using ball.png as the image of the ball
    }
}
