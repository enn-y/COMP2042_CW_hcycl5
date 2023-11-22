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
        main.getButtonControls().loadButton = new Button("Load Game"); //Initialize the load button
        main.getButtonControls().loadButton.setTranslateX(220); //Set the size of load button, x-coordinate (220)
        main.getButtonControls().loadButton.setTranslateY(310); //Set the size of the load button, y-coordinate (300)

        main.getButtonControls().newGameButton = new Button("Start New Game"); //Initialize the new game button
        main.getButtonControls().newGameButton.setTranslateX(220); //Set the size of the new game button, x-coordinate (220)
        main.getButtonControls().newGameButton.setTranslateY(340); //Set the size of the new game button, y-coordinate (340)
    }

    public void AddLabels(){
        root = new Pane(); //Initialize the root pane, root is the instance of the JavaFX Pane class where Pane is a container to hold JavaFX elements
        scoreLabel = new Label("Score: " + main.currentScore); //Initialize the score label
        levelLabel = new Label("Level: " + main.currentLevel); //Initialize the level label
        levelLabel.setTranslateY(20); //Set the size of the level label, y-coordinate (20)
        heartLabel = new Label("Heart : " + main.numberOfHearts); //Initialize the heart label
        heartLabel.setTranslateX(windowWidth - 70); //Set the size of the heart label, x-coordinate (sceneWidth - 70)
    }

    public void AddElements(){
        if (!main.getState().loadFromSavedFile) { //If NOT loading from saved file
            root.getChildren().addAll(main.getPaddle(), main.getBall(), scoreLabel, heartLabel, levelLabel, main.getButtonControls().newGameButton, main.getButtonControls().loadButton); //Add the paddle, ball, score label, heart label, level label, and new game button to the root pane
        } else { //But if IT IS loading from saved file
            root.getChildren().addAll(main.getPaddle(), main.getBall(), scoreLabel, heartLabel, levelLabel); //Add the paddle, ball, score label, heart label, and level label to the root pane, but NOT the new game button in this else block
        }
        for (Block block : main.blocks) { //For each block in the blocks ArrayList
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
