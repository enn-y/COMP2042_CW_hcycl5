package brickGame.Model.Ball;

import brickGame.Main;
import brickGame.Model.Blocks.BlockModel;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.Random;

public class BallFactory {
    Main main;

    public BallFactory(Main main) {
        this.main = main;
    }
    public void initializeBall() { //Initialize the ball
        Random random = new Random(); //Random number generator
        main.getBall().setBallXCoordinate(random.nextInt(main.getGameScreen().windowWidth) + 1); //Random x-coordinate of the ball
        main.getBall().setBallYCoordinate(random.nextInt(main.getGameScreen().windowHeight - 200) + ((main.currentLevel + 1) * BlockModel.getHeight()) + 15); //Random y-coordinate of the ball
        main.getBall().setCenterX(main.getBall().getBallXCoordinate()); //Set the center x-coordinate of the ball
        main.getBall().setCenterY(main.getBall().getBallYCoordinate());
        main.getBall().setRadius(main.getBall().ballRadius); //Set the radius of the ball
        main.getBall().setFill(new ImagePattern(new Image("ball.png"))); //Using ball.png as the image of the ball
    }
}
