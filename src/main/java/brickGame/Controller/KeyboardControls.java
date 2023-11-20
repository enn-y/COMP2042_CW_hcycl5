package brickGame.Controller;

import brickGame.Main;
import brickGame.Model.Ball;
import brickGame.Model.Paddle;
import brickGame.View.State;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyboardControls implements EventHandler<KeyEvent> {

    Main main;
    Paddle paddle;
    Ball ball;
    State state;

    public KeyboardControls(Main main, Paddle paddle) {
        this.main = main;
        this.paddle = paddle;
    }

    public void handle(KeyEvent event) { //Handle key presses, ARROW CONTROLS
        switch (event.getCode()) { //Switch statement for key presses
            case LEFT: //If the left arrow key is pressed
                paddle.move(paddle.paddleLEFT); //Move the paddle to the left
                break;
            case RIGHT: //If the right arrow key is pressed
                paddle.move(paddle.paddleRIGHT); //Move the paddle to the right
                break;
            case DOWN:
                //setPhysicsToBall();
                break;
            case SPACE:
                // If the spacebar is pressed, pause or resume the game
                main.getScore().showMessage("Game Paused", main);
                main.getEngine().pause();
                break;
            case R:
                // If the R key is pressed, restart the game
                main.getScore().showMessage("Game Restarted", main);
                main.getEngine().stop();
                main.getState().restartGame();
                break;
            case S: //If the S key is pressed
                state = new State(main, ball, paddle);
                state.saveGame(); //Save the game
                break;
        }
    }
}
