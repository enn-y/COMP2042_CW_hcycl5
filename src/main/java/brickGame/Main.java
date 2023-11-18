package brickGame;

import brickGame.Controller.ButtonControls;
import brickGame.Controller.KeyboardControls;
import brickGame.Model.*;
import brickGame.Model.Serializables.BlockSerializable;
import brickGame.View.GameScreen;
import brickGame.View.State;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application implements GameEngine.OnAction { //Application: JavaFX GUI, EventHandler: JavaFX Event, GameEngine.OnAction: GameEngine Event
    Ball ball; //Ball object
    Paddle paddle; //Paddle object
    KeyboardControls keyboardControls; //KeyboardControls object
    ButtonControls buttonControls; //ButtonControls object
    GameObjectInitializer gameObjectInitializer; //GameObjectInitializer object
    LevelManager levelManager; //LevelManager object
    GameScreen gameScreen; //GameScreen object
    public int currentLevel = 0;
    public int windowWidth = 500; //Game window width
    public int windowHeight = 700; //Game window height
    public boolean goldBall = false; //Status of gold ball
    public boolean existHeartBlock = false; //Status of heart block
    public int destroyedBlockCount = 0; //Number of destroyed blocks
    private double ballVelocity = 1.000; //Velocity of ball
    public int numberOfHearts = 3; //Number of hearts, initialized at 3
    public int currentScore = 0; //Score, initialized at 0, increases by 1 when a block is destroyed
    public long currentTime = 0; //Time, initialized at 0
    public long hitTime  = 0; //Time of hit, initialized at 0, used to check if ball is still on paddle
    public long goldTime = 0; //Time of gold ball, initialized at 0, used to check if gold ball is still active
    public GameEngine engine; //GameEngine object
    public static String savePath    = "D:/save/save.mdds"; //Path to save file
    public static String savePathDir = "D:/save/"; //Path to save directory
    public ArrayList<Block> blocks = new ArrayList<Block>(); //ArrayList to store the blocks
    public ArrayList<Bonus> bonusItems = new ArrayList<Bonus>(); //ArrayList to store the bonus items
    public Color[] blockColors = new Color[]{ //Array of colors for the blocks
            Color.MAGENTA,
            Color.RED,
            Color.GOLD,
            Color.CORAL,
            Color.AQUA,
            Color.VIOLET,
            Color.GREENYELLOW,
            Color.ORANGE,
            Color.PINK,
            Color.SLATEGREY,
            Color.YELLOW,
            Color.TOMATO,
            Color.TAN,
    };
    public  Pane root;
    public Label scoreLabel; //Label to display score
    public Label heartLabel; //Label to display heart
    public Label levelLabel; //Label to display level
    public boolean loadFromSavedFile = false; //Status of loading from saved file
    public Stage primaryStage; //Stage is the top level JavaFX container, the window
    public Button loadButton = null; //Button to load game
    public Button newGameButton = null; //Button to start new game

    @Override
    public void start(Stage primaryStage) throws Exception { //Start method
        this.primaryStage = primaryStage; //Set primaryStage which is the game window

        ball = new Ball(this);
        paddle = new Paddle(this);
        keyboardControls = new KeyboardControls(this, paddle);
        gameObjectInitializer = new GameObjectInitializer(this, ball, paddle, keyboardControls); //Initialize the initializer
        gameScreen = new GameScreen(this); //Initialize the game screen

        if (!loadFromSavedFile) { //If NOT loading from saved file
            currentLevel++; //Increment the level
            if (currentLevel >1){
                new Score().showMessage("Level Up :)", this); //If the level is greater than 1, then display "Level Up :)", inherited from Score.java
            }
            if (currentLevel == 18) {
                new Score().showWin(this); //If level is 18, then display "You Win :)", inherited from Score.java
                return;
            }

            gameObjectInitializer.initializeBall(); //Initialize the ball
            gameObjectInitializer.initializePaddle(); //Initialize the paddle
            gameObjectInitializer.initializeBlocks(); //Initialize the blocks
            gameObjectInitializer.initializeKeyboardController(); //Initialize the controller
            gameScreen.AddButtons();
        }

        gameScreen.AddLabels(); //Add the labels
        gameScreen.AddElements(); //Add the elements
        gameScreen.CreateScene(); //Create the scene

        if (!loadFromSavedFile) { //If NOT loading from saved file
            if (currentLevel > 1 && currentLevel < 18) { //If the level is greater than 1 and less than 18
                loadButton.setVisible(false); //Hide the load button
                newGameButton.setVisible(false); //Hide the new game button
                engine = new GameEngine(); //Initialize the game engine, to start the game
                engine.setOnAction(this); //Listen for events
                engine.setFps(120); //Set FPS
                engine.start(); //Start the game engine
            }

            buttonControls = new ButtonControls(this);

            loadButton.setOnAction(buttonControls.createLoadButtonHandler());
            newGameButton.setOnAction(buttonControls.createNewGameButtonHandler());

        } else { //But if IT IS loading from saved file
            engine = new GameEngine(); //Initialize the game engine, to start the game
            engine.setOnAction(this); //Listen for events
            engine.setFps(120); //Set FPS
            engine.start(); //Start the game engine
            loadFromSavedFile = false; //Set loadFromSave to false, INDICATES that it is a new game
        }
    }

    public static void main(String[] args) { //Main method HERE
        launch(args); //LAUNCH GAME
    }

    //float oldXBreak; //Variable for the old x-coordinate of the paddle, NO USAGES - CHECK IF CAN DELETE

    @Override
    public void onInit() {
    }

    public void onPhysicsUpdate() { //Updates game physics and logic during each frame of the game
        levelManager = new LevelManager(this, ball);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                levelManager.blockDestroyedCount();
                ball.setPhysicsToBall();

                if (currentTime - goldTime > 5000) { //Gold Ball
                    ball.setFill(new ImagePattern(new Image("ball.png")));
                    root.getStyleClass().remove("goldRoot");
                    goldBall = false;
                }

                for (Bonus choco : bonusItems) { //Bonus Items
                    if (choco.y > windowHeight || choco.taken) {
                        continue;
                    }
                    if (choco.y >= paddle.paddleYPosition && choco.y <= paddle.paddleYPosition + paddle.paddleHeight && choco.x >= paddle.paddleXPosition && choco.x <= paddle.paddleXPosition + paddle.paddleWidth) {
                        System.out.println("You Got it and +3 score for you");
                        choco.chocolateBlock.setVisible(false);
                        choco.taken = true;
                        currentScore += 3;
                        new Score().show(choco.x, choco.y, 3, Main.this);
                    }
                    choco.y += ((currentTime - choco.timeCreated) / 1000.000) + 1.000;
                }
                //System.out.println("time is:" + time + " goldTime is " + goldTime);
            }
        });
    }

    public void onUpdate() { //Update the game
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                scoreLabel.setText("Score: " + currentScore);
                heartLabel.setText("Heart : " + numberOfHearts);

                paddle.setX(paddle.paddleXPosition);
                paddle.setY(paddle.paddleYPosition);
                ball.setCenterX(ball.ballXCoordinate);
                ball.setCenterY(ball.ballYCoordinate);

                for (Bonus choco : bonusItems) { //Bonus Items
                    choco.chocolateBlock.setY(choco.y); //Set the y-coordinate of the bonus item
                }

            }
        });

        if (ball.ballYCoordinate >= Block.getPaddingTop() && ball.ballYCoordinate <= (Block.getHeight() * (currentLevel + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(ball.ballXCoordinate, ball.ballYCoordinate);
                if (hitCode != Block.NO_HIT) {
                    currentScore += 1;

                    new Score().show(block.blockXCoordinate, block.blockYCoordinate, 1, this);

                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    //System.out.println("size is " + blocks.size());
                    ball.resetCollideFlags();

                    if (block.type == Block.BLOCK_CHOCOLATE) {
                        final Bonus choco = new Bonus(block.row, block.column);
                        choco.timeCreated = currentTime;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                root.getChildren().add(choco.chocolateBlock);
                            }
                        });
                        bonusItems.add(choco);
                    }

                    if (block.type == Block.BLOCK_STAR && !goldBall) {
                        goldTime = currentTime;
                        ball.setFill(new ImagePattern(new Image("goldball.png")));
                        System.out.println("gold ball");
                        root.getStyleClass().add("goldRoot");
                        goldBall = true;
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        numberOfHearts++;
                    }

                    if (hitCode == Block.HIT_RIGHT) {
                        ball.collideToRightBlock = true;
                    } else if (hitCode == Block.HIT_BOTTOM) {
                        ball.collideToBottomBlock = true;
                    } else if (hitCode == Block.HIT_LEFT) {
                        ball.collideToLeftBlock = true;
                    } else if (hitCode == Block.HIT_TOP) {
                        ball.collideToTopBlock = true;
                    }
                    System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
                }
                //TODO hit to break and some work here....
                //System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
            }
        }
    }

    @Override
    public void onTime(long time) { //Update the time
        this.currentTime = time;
    }

    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }
    public KeyboardControls getKeyboardControls() {
        return keyboardControls;
    }
}