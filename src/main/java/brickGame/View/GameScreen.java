package brickGame.View;

import brickGame.Main;
import brickGame.Model.Blocks.Block;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameScreen {
    Main main;

    public int windowWidth = 500; //Game window width
    public int windowHeight = 700; //Game window height
    public  Pane root;
    public Label scoreLabel; //Label to display score
    public Label heartLabel; //Label to display heart
    public Label levelLabel; //Label to display level
    public Stage primaryStage; //Stage is the top level JavaFX container, the window

    public GameScreen(Main main) {
        this.main = main;
    }

    public void AddButtons(){
        main.getButtonControls().newGameButton = new Button("Start New Game"); //Initialize the new game button
        main.getButtonControls().newGameButton.setTranslateX(160); //Set the size of the new game button, x-coordinate (220)
        main.getButtonControls().newGameButton.setTranslateY(310); //Set the size of the new game button, y-coordinate (340)
        main.getButtonControls().newGameButton.setPrefWidth(180); //Set a larger width
        main.getButtonControls().newGameButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        main.getButtonControls().newGameButton.setTooltip(new javafx.scene.control.Tooltip("Start a new game")); //Set a tooltip for the new game button

        main.getButtonControls().loadButton = new Button("Load Game"); //Initialize the load button
        main.getButtonControls().loadButton.setTranslateX(160); //Set the size of load button, x-coordinate (220)
        main.getButtonControls().loadButton.setTranslateY(340); //Set the size of the load button, y-coordinate (300)
        main.getButtonControls().loadButton.setPrefWidth(180); //Set a larger width
        main.getButtonControls().loadButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        main.getButtonControls().loadButton.setTooltip(new javafx.scene.control.Tooltip("Load a saved game")); //Set a tooltip for the load button

        main.getButtonControls().exitButton = new Button("Exit"); //Initialize the exit button
        main.getButtonControls().exitButton.setTranslateX(160); //Set the size of the exit button, x-coordinate (220)
        main.getButtonControls().exitButton.setTranslateY(370); //Set the size of the exit button, y-coordinate (370)
        main.getButtonControls().exitButton.setPrefWidth(180); //Set a larger width
        main.getButtonControls().exitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        main.getButtonControls().exitButton.setTooltip(new javafx.scene.control.Tooltip("Exit the game")); //Set a tooltip for the exit button

        main.getButtonControls().instructionsButton = new Button("Instructions"); //Initialize the instructions button
        main.getButtonControls().instructionsButton.setTranslateX(160); //Set the size of the instructions button, x-coordinate (220)
        main.getButtonControls().instructionsButton.setTranslateY(400); //Set the size of the instructions button, y-coordinate (400)
        main.getButtonControls().instructionsButton.setPrefWidth(180); //Set a larger width
        main.getButtonControls().instructionsButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        main.getButtonControls().instructionsButton.setTooltip(new javafx.scene.control.Tooltip("Display instructions")); //Set a tooltip for the instructions button
    }

    public void AddLabels(){
        root = new Pane(); //Initialize the root pane, root is the instance of the JavaFX Pane class where Pane is a container to hold JavaFX elements
        scoreLabel = new Label("Score: " + main.currentScore); //Initialize the score label
        levelLabel = new Label("Level: " + main.currentLevel); //Initialize the level label
        levelLabel.setTranslateY(20); //Set the size of the level label, y-coordinate (20)
        heartLabel = new Label("Heart : " + main.getPlayer().numberOfHearts); //Initialize the heart label
        heartLabel.setTranslateX(windowWidth - 70); //Set the size of the heart label, x-coordinate (sceneWidth - 70)
    }

    public void AddElements(){
        if (!main.getState().loadFromSavedFile) { //If NOT loading from saved file
            root.getChildren().addAll(main.getPlayer(), main.getBall(), scoreLabel, heartLabel, levelLabel, main.getButtonControls().newGameButton, main.getButtonControls().loadButton,  main.getButtonControls().exitButton, main.getButtonControls().instructionsButton); //Add the paddle, ball, score label, heart label, level label, and new game button to the root pane
        } else { //But if IT IS loading from saved file
            root.getChildren().addAll(main.getPlayer(), main.getBall(), scoreLabel, heartLabel, levelLabel); //Add the paddle, ball, score label, heart label, and level label to the root pane, but NOT the new game button in this else block
        }
        for (Block block : main.getEngine().blocks) { //For each block in the blocks ArrayList
            root.getChildren().add(block.rect); //Add the block to the root pane
        }
    }

    public void CreateScene(){
        Scene scene = new Scene(root, windowWidth, windowHeight); //Initialize the scene, scene is the instance of the JavaFX Scene class where Scene is the container for all content in a scene graph
        scene.getStylesheets().add("style.css"); //Add the style.css file to the scene
        scene.setOnKeyPressed(main.getKeyboardControls()); //Listen for key presses
        primaryStage.setTitle("Game"); //Set the title of the game window
        primaryStage.setScene(scene); //Set the scene of the game window
        primaryStage.show(); //Show the game window
    }
}
