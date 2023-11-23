package brickGame.Controller;

import brickGame.Main;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyboardControls implements EventHandler<KeyEvent> {

    Main main;

    public KeyboardControls(Main main) {
        this.main = main;
    }

    public void handle(KeyEvent event) { //Handle key presses, ARROW CONTROLS
        switch (event.getCode()) { //Switch statement for key presses
            case LEFT: //If the left arrow key is pressed
                main.getPlayer().move(main.getPlayer().paddleLEFT); //Move the paddle to the left
                break;
            case RIGHT: //If the right arrow key is pressed
                main.getPlayer().move(main.getPlayer().paddleRIGHT); //Move the paddle to the right
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
                main.getState().saveGame(); //Save the game
                break;
        }
    }
}
