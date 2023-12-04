package brickGame.Controller;

import brickGame.Main;
import brickGame.Model.Player.PlayerModel;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * The KeyboardControls class handles the keyboard controls for the game.
 * It implements the EventHandler interface to listen for key presses.
 *
 * @author Lua Chong En
 *
 */

public class KeyboardController implements EventHandler<KeyEvent> {

    Main main; //Main class instance

    /**
     * Constructor for the KeyboardControls class.
     * @param main The Main class instance to access components of the game.
     */

    public KeyboardController(Main main) {
        this.main = main;
    }

    /**
     * The handle method handles the key presses for the game.
     * Uses an enhanced switch statement to handle the key presses.
     * @param event The KeyEvent object contains the key-press information.
     */

    public void handle(KeyEvent event) { //Handle key presses, ARROW CONTROLS
        switch (event.getCode()) { //Switch statement for key presses
            case LEFT -> //If the left arrow key is pressed
                    main.getPlayer().move(PlayerModel.paddleLEFT); //Move the paddle to the left
            case RIGHT -> //If the right arrow key is pressed
                    main.getPlayer().move(PlayerModel.paddleRIGHT); //Move the paddle to the right
            case SPACE -> {
                // If the space-bar is pressed, pause or resume the game
                main.getScore().showMessage("Game Paused", main);
                main.getEngine().pause();
            }
            case R -> {
                // If the R key is pressed, restart the game
                main.getScore().showMessage("Game Restarted", main);
                main.getEngine().stop();
                main.getState().restartGame();
            }
            case Q -> {
                // If the Q key is pressed, quit the game
                main.getScore().showMessage("Game Quit?", main);
                main.getEngine().pause(); //Pause the game
                if (main.getButtonControls().showConfirmationDialog("Exit Confirmation", "Are you sure you want to exit?")) {
                    main.getGameScreen().primaryStage.close(); //Close the game window
                } else {
                    main.getScore().showMessage("Game Resumed", main);
                    main.getEngine().pause(); //Resume the game
                }
            }
            case S -> //If the S key is pressed
                    main.getState().saveGame(); //Save the game
        }
    }
}