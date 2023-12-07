package brickGame.View;

import brickGame.Main;
import brickGame.Model.Blocks.BlockModel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The GameScreen class represents the game screen.
 * It is used to display the game screen, buttons, and labels.
 *
 * @author Lua Chong En
 *
 */

public class GameScreen {
    Main main;
    public int windowWidth = 500;
    public int windowHeight = 700;
    public  Pane root;
    public Label scoreLabel;
    public Label heartLabel;
    public Label levelLabel;
    public Stage primaryStage;

    /**
     * Constructor initializes the game screen.
     * @param main The Main instance to access the components of the game.
     */

    public GameScreen(Main main) {
        this.main = main;
    }

    /**
     * The AddButtons method is used to display the game screen.
     * It also displays the buttons and labels.
     * It is used to initialize the new game button, load button, exit button, and instructions button.
     * It is also used to initialize the score label, heart label, and level label.
     */

    public void AddButtons(){
        main.getButtonControls().newGameButton = new Button("Start New Game");
        main.getButtonControls().newGameButton.setTranslateX(160);
        main.getButtonControls().newGameButton.setTranslateY(310);
        main.getButtonControls().newGameButton.setPrefWidth(180);
        main.getButtonControls().newGameButton.setStyle("-fx-background-color: #013220; -fx-text-fill: white;");
        main.getButtonControls().newGameButton.setTooltip(new javafx.scene.control.Tooltip("Start a new game"));

        main.getButtonControls().loadButton = new Button("Load Game");
        main.getButtonControls().loadButton.setTranslateX(160);
        main.getButtonControls().loadButton.setTranslateY(350);
        main.getButtonControls().loadButton.setPrefWidth(180);
        main.getButtonControls().loadButton.setStyle("-fx-background-color: #013220; -fx-text-fill: white;");
        main.getButtonControls().loadButton.setTooltip(new javafx.scene.control.Tooltip("Load a saved game"));

        main.getButtonControls().exitButton = new Button("Exit");
        main.getButtonControls().exitButton.setTranslateX(160);
        main.getButtonControls().exitButton.setTranslateY(390);
        main.getButtonControls().exitButton.setPrefWidth(180);
        main.getButtonControls().exitButton.setStyle("-fx-background-color: #013220; -fx-text-fill: white;");
        main.getButtonControls().exitButton.setTooltip(new javafx.scene.control.Tooltip("Exit the game"));

        main.getButtonControls().instructionsButton = new Button("Instructions");
        main.getButtonControls().instructionsButton.setTranslateX(160);
        main.getButtonControls().instructionsButton.setTranslateY(430);
        main.getButtonControls().instructionsButton.setPrefWidth(180);
        main.getButtonControls().instructionsButton.setStyle("-fx-background-color: #013220; -fx-text-fill: white;");
        main.getButtonControls().instructionsButton.setTooltip(new javafx.scene.control.Tooltip("Display instructions"));
    }

    /**
     * The AddLabels method is used to display the labels.
     * It is used to initialize the score label, heart label, and level label.
     * It is also used to set the size of the score label, heart label, and level label.
     */

    public void AddLabels(){
        root = new Pane();
        scoreLabel = new Label("Score: " + main.currentScore);
        levelLabel = new Label("Level: " + main.currentLevel);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + main.getPlayer().numberOfHearts);
        heartLabel.setTranslateX(windowWidth - 70);
    }

    /**
     * The AddElements method is used to display the elements.
     * It is used to add the paddle, ball, score label, heart label, level label, and new game button to the root pane.
     * It is also used to add the blocks to the root pane.
     */

    public void AddElements(){
        if (!main.getState().loadFromSavedFile) {
            root.getChildren().addAll(main.getPlayer(), main.getBall(), scoreLabel, heartLabel, levelLabel, main.getButtonControls().newGameButton, main.getButtonControls().loadButton,  main.getButtonControls().exitButton, main.getButtonControls().instructionsButton);
        } else {
            root.getChildren().addAll(main.getPlayer(), main.getBall(), scoreLabel, heartLabel, levelLabel);
        }
        for (BlockModel block : main.getEngine().blocks) {
            root.getChildren().add(block.rect);
        }
    }

    /**
     * The CreateScene method is used to create the scene.
     * It is used to initialize the scene, add the style.css file to the scene, listen for key presses, set the title of the game window, set the scene of the game window, and show the game window.
     */

    public void CreateScene(){
        Scene scene = new Scene(root, windowWidth, windowHeight);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(main.getKeyboardControls());
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
