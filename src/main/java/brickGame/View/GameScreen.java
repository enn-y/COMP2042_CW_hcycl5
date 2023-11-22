package brickGame.View;

import brickGame.Main;
import brickGame.Model.Blocks.Block;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GameScreen {
    Main main;

    public int windowWidth = 500; //Game window width
    public int windowHeight = 700; //Game window height

    public GameScreen(Main main) {
        this.main = main;
    }

    public void AddButtons(){
        main.loadButton = new Button("Load Game"); //Initialize the load button
        main.loadButton.setTranslateX(220); //Set the size of load button, x-coordinate (220)
        main.loadButton.setTranslateY(310); //Set the size of the load button, y-coordinate (300)

        main.newGameButton = new Button("Start New Game"); //Initialize the new game button
        main.newGameButton.setTranslateX(220); //Set the size of the new game button, x-coordinate (220)
        main.newGameButton.setTranslateY(340); //Set the size of the new game button, y-coordinate (340)
    }

    public void AddLabels(){
        main.root = new Pane(); //Initialize the root pane, root is the instance of the JavaFX Pane class where Pane is a container to hold JavaFX elements
        main.scoreLabel = new Label("Score: " + main.currentScore); //Initialize the score label
        main.levelLabel = new Label("Level: " + main.currentLevel); //Initialize the level label
        main.levelLabel.setTranslateY(20); //Set the size of the level label, y-coordinate (20)
        main.heartLabel = new Label("Heart : " + main.numberOfHearts); //Initialize the heart label
        main.heartLabel.setTranslateX(windowWidth - 70); //Set the size of the heart label, x-coordinate (sceneWidth - 70)
    }

    public void AddElements(){
        if (!main.loadFromSavedFile) { //If NOT loading from saved file
            main.root.getChildren().addAll(main.getPaddle(), main.getBall(), main.scoreLabel, main.heartLabel, main.levelLabel, main.newGameButton, main.loadButton); //Add the paddle, ball, score label, heart label, level label, and new game button to the root pane
        } else { //But if IT IS loading from saved file
            main.root.getChildren().addAll(main.getPaddle(), main.getBall(), main.scoreLabel, main.heartLabel, main.levelLabel); //Add the paddle, ball, score label, heart label, and level label to the root pane, but NOT the new game button in this else block
        }
        for (Block block : main.blocks) { //For each block in the blocks ArrayList
            main.root.getChildren().add(block.rect); //Add the block to the root pane
        }
    }

    public void CreateScene(){
        Scene scene = new Scene(main.root, windowWidth, windowHeight); //Initialize the scene, scene is the instance of the JavaFX Scene class where Scene is the container for all content in a scene graph
        scene.getStylesheets().add("style.css"); //Add the style.css file to the scene
        scene.setOnKeyPressed(main.getKeyboardControls()); //Listen for key presses
        main.primaryStage.setTitle("Game"); //Set the title of the game window
        main.primaryStage.setScene(scene); //Set the scene of the game window
        main.primaryStage.show(); //Show the game window
    }
}
