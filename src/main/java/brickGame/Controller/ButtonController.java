package brickGame.Controller;

import brickGame.Main;
import brickGame.Model.GameEngine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * The ButtonControls class is used to create the button handlers for the main menu buttons.
 * It contains the methods to display the instructions and confirmation dialogs.
 * It also contains the method to hide the buttons.
 *
 * @author Lua Chong En
 *
 */

public class ButtonController {
    Main main; //Main class instance

    public Button loadButton = null; //Button to load game
    public Button newGameButton = null; //Button to start new game
    public Button exitButton = null; //Button to exit game
    public Button instructionsButton = null; //Button to display instructions

    /**
     * Constructor for the ButtonControls class.
     * @param main The Main class instance to access components of the game.
     */

    public ButtonController(Main main) {
        this.main = main;
    }

    /**
     * The createLoadButtonHandler method creates the event handler for the load button.
     * It calls the loadGame() method in the state class.
     * @return The event handler for the load button.
     */

    public EventHandler<ActionEvent> createLoadButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.getState().loadGame();
                hideButtons();
            }
        };
    }

    /**
     * The createNewGameButtonHandler creates the event handler for the new game button.
     * It sets the game engine to the main class.
     * @return The event handler for the new game button.
     */

    public EventHandler<ActionEvent> createNewGameButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.getEngine().setOnAction(new GameEngine(main));
                main.getEngine().setFps(120);
                main.getEngine().start();
                hideButtons();
            }
        };
    }

    /**
     * The createExitButtonHandler method creates the event handler for the exit button.
     * Creates the event handler for the exit button.
     * It displays the confirmation dialog with the title "Exit Game" and content "Are you sure you want to exit the game?".
     * User can click the OK button to exit the game.
     * @return The event handler for the exit button.
     */

    public EventHandler<ActionEvent> createExitButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (showConfirmationDialog("Exit Confirmation", "Are you sure you want to exit?")) {
                    main.getGameScreen().primaryStage.close();
                    hideButtons();
                }
            }
        };
    }

    /**
     * The createInstructionsButtonHandler method creates the event handler for the instructions button.
     * Creates the event handler for the instructions button.
     * It calls the showInstructionsDialog() method.
     * @return The event handler for the instructions button.
     */

    public EventHandler<ActionEvent> createInstructionsButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showInstructionsDialog();
            }
        };
    }

    /**
     * The showInstructionsDialog method displays the instructions dialog with game instructions.
     * Displays the instructions dialog with game instructions.
     * It is an alert dialog with the title "Game Instructions".
     */

    private void showInstructionsDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Instructions");
        alert.setHeaderText(null);
        alert.setContentText("WELCOME TO THE BRICK BREAKER GAME\n\n" +
                "1. Use the left and right arrow keys to move the paddle.\n" +
                "2. Press the 'SPACE BAR' key to pause the game.\n" +
                "3. Press the 'S' key to save the game.\n" +
                "4. Press the 'R' key to restart the game.\n" +
                "5. Press the 'Q' key to quit the game.\n" +
                "\nGOOD LUCK!");
        alert.showAndWait();
    }

    /**
     * The showConfirmationDialog method displays the confirmation dialog with the title and content.
     * It is an alert dialog with the title and content.
     * @param title The title of the confirmation dialog.
     * @param content The message of the confirmation dialog.
     * @return True if the user clicks the OK button, false otherwise.
     */

    public boolean showConfirmationDialog(String title, String content) {
        Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmExit.setTitle(title);
        confirmExit.setHeaderText(null);
        confirmExit.setContentText(content);

        Optional<ButtonType> result = confirmExit.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * The hideButtons method hides the main menu buttons.
     * The buttons are loadButton, newGameButton, exitButton, and instructionsButton.
     */

    public void hideButtons(){
        loadButton.setVisible(false);
        newGameButton.setVisible(false);
        exitButton.setVisible(false);
        instructionsButton.setVisible(false);
    }
}
