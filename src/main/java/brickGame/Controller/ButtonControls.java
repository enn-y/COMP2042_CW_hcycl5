package brickGame.Controller;

import brickGame.Model.GameEngine;
import brickGame.Main;
import brickGame.View.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonControls {
    Main main;

    public Button loadButton = null; //Button to load game
    public Button newGameButton = null; //Button to start new game

    public ButtonControls(Main main) {
        this.main = main;
    }

    public EventHandler<ActionEvent> createLoadButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.getState().loadGame();
                loadButton.setVisible(false);
                newGameButton.setVisible(false);
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
                loadButton.setVisible(false);
                newGameButton.setVisible(false);
            }
        };
    }
}
