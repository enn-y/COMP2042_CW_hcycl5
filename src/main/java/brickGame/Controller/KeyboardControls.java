package brickGame.Controller;

import brickGame.Main;
import brickGame.Model.Paddle;
import brickGame.View.State;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyboardControls implements EventHandler<KeyEvent> {

    Main main;
    Paddle paddle;
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
            case S: //If the S key is pressed
                state.saveGame(); //Save the game
                break;
        }
    }
}
