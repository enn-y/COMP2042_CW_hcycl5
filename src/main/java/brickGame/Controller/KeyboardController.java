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

    Main main;

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

    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT ->
                    main.getPlayer().move(PlayerModel.paddleLEFT);
            case RIGHT ->
                    main.getPlayer().move(PlayerModel.paddleRIGHT);
            case SPACE -> {
                main.getScore().showMessage("Game Paused", main);
                main.getEngine().pause();
            }
            case R -> {
                main.getScore().showMessage("Game Restarted", main);
                main.getEngine().stop();
                main.getState().restartGame();
            }
            case Q -> {
                main.getScore().showMessage("Game Quit?", main);
                main.getEngine().pause();
                if (main.getButtonControls().showConfirmationDialog("Exit Confirmation", "Are you sure you want to exit?")) {
                    main.getGameScreen().primaryStage.close();
                } else {
                    main.getScore().showMessage("Game Resumed", main);
                    main.getEngine().pause();
                }
            }
            case S ->
                    main.getState().saveGame();
        }
    }
}