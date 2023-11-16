package brickGame.Controller;

import brickGame.GameEngine;
import brickGame.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ButtonControls {
    Main main;

    public ButtonControls(Main main) {
        this.main = main;
    }

    public EventHandler<ActionEvent> createLoadButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.loadGame();
                main.loadButton.setVisible(false);
                main.newGameButton.setVisible(false);
            }
        };
    }

    public EventHandler<ActionEvent> createNewGameButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.engine = new GameEngine();
                main.engine.setOnAction(main);
                main.engine.setFps(120);
                main.engine.start();
                main.loadButton.setVisible(false);
                main.newGameButton.setVisible(false);
            }
        };
    }
}
