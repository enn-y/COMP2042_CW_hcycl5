package brickGame.Controller;

import brickGame.Model.GameEngine;
import brickGame.Main;
import brickGame.View.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ButtonControls {
    Main main;

    public Button loadButton = null; //Button to load game
    public Button newGameButton = null; //Button to start new game
    public Button exitButton = null; //Button to exit game
    public Button instructionsButton = null; //Button to display instructions

    public ButtonControls(Main main) {
        this.main = main;
    }

    public EventHandler<ActionEvent> createLoadButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.getState().loadGame();
                hideButtons();
            }
        };
    }

    public EventHandler<ActionEvent> createNewGameButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.getEngine().setOnAction(main);
                main.getEngine().setFps(120);
                main.getEngine().start();
                hideButtons();
            }
        };
    }

    public EventHandler<ActionEvent> createExitButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.getGameScreen().primaryStage.close();
                hideButtons();
            }
        };
    }

    public EventHandler<ActionEvent> createInstructionsButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showInstructionsDialog();
            }
        };
    }

    public void showInstructionsDialog() {
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

    public void hideButtons(){
        loadButton.setVisible(false);
        newGameButton.setVisible(false);
        exitButton.setVisible(false);
        instructionsButton.setVisible(false);
    }
}
