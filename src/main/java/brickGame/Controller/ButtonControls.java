package brickGame.Controller;

import brickGame.Model.GameEngine;
import brickGame.Main;
import brickGame.View.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ButtonControls {
    Main main;
    State state;

    public ButtonControls(Main main) {
        this.main = main;
    }

    public EventHandler<ActionEvent> createLoadButtonHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                state = new State(main);
                state.loadGame();
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
