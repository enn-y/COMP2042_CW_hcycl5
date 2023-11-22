package brickGame;

import brickGame.Controller.ButtonControls;
import brickGame.Controller.KeyboardControls;
import brickGame.Model.*;
import brickGame.Model.Blocks.Block;
import brickGame.Model.Interface.OnAction;
import brickGame.View.GameScreen;
import brickGame.View.State;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application implements OnAction { //Application: JavaFX GUI, EventHandler: JavaFX Event, GameEngine.OnAction: GameEngine Event
    Ball ball; //Ball object
    Paddle paddle; //Paddle object
    KeyboardControls keyboardControls; //KeyboardControls object
    ButtonControls buttonControls; //ButtonControls object
    GameObjectInitializer gameObjectInitializer; //GameObjectInitializer object
    LevelManager levelManager; //LevelManager object
    GameScreen gameScreen; //GameScreen object
    State state; //State object
    Score score; //Score object
    GameEngine engine; //GameEngine object
    public int currentLevel = 0;
    public boolean existHeartBlock = false; //Status of heart block
    public int destroyedBlockCount = 0; //Number of destroyed blocks
    public int numberOfHearts = 3; //Number of hearts, initialized at 3
    public int currentScore = 0; //Score, initialized at 0, increases by 1 when a block is destroyed
    public long currentTime = 0; //Time, initialized at 0
    public long hitTime  = 0; //Time of hit, initialized at 0, used to check if ball is still on paddle
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

    @Override
    public void start(Stage primaryStage) throws Exception { //Start method
        ball = new Ball(this);
        paddle = new Paddle(this);
        keyboardControls = new KeyboardControls(this);
        gameObjectInitializer = new GameObjectInitializer(this); //Initialize the initializer
        gameScreen = new GameScreen(this); //Initialize the game screen
        state = new State(this); //Initialize the state
        buttonControls = new ButtonControls(this); //Initialize the button controls
        engine = new GameEngine(); //Initialize the game engine
        score = new Score(); //Initialize the score
        levelManager = new LevelManager(this); //Initialize the level manager

        getGameScreen().primaryStage = primaryStage; //Set primaryStage which is the game window

        if (!getState().loadFromSavedFile) { //If NOT loading from saved file
            currentLevel++; //Increment the level
            if (currentLevel >1){
                getScore().showMessage("Level Up :)", this); //If the level is greater than 1, then display "Level Up :)", inherited from Score.java
            }
            if (currentLevel == 18) {
                getScore().showWin(this); //If level is 18, then display "You Win :)", inherited from Score.java
                return;
            }

            gameObjectInitializer.initializeBall(); //Initialize the ball
            gameObjectInitializer.initializePaddle(); //Initialize the paddle
            gameObjectInitializer.initializeBlocks(); //Initialize the blocks
            gameScreen.AddButtons();
        }

        gameScreen.AddLabels(); //Add the labels
        gameScreen.AddElements(); //Add the elements
        gameScreen.CreateScene(); //Create the scene

        state.checkLoadFromSavedFile();
    }

    public static void main(String[] args) { //Main method HERE
        launch(args); //LAUNCH GAME
    }

    @Override
    public void onInit() {
    }

    public void onPhysicsUpdate() { //Updates game physics and logic during each frame of the game
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getLevelManager().blockDestroyedCount();
                getBall().setPhysicsToBall();

                if (currentTime - getBall().goldTime > 5000) { //Gold Ball
                    getBall().setFill(new ImagePattern(new Image("ball.png")));
                    getGameScreen().root.getStyleClass().remove("goldRoot");
                    getBall().goldBall = false;
                }

                for (Bonus choco : bonusItems) { //Bonus Items
                    if (choco.y > getGameScreen().windowHeight || choco.taken) {
                        continue;
                    }
                    if (choco.y >= getPaddle().paddleYPosition && choco.y <= getPaddle().paddleYPosition + getPaddle().paddleHeight && choco.x >= getPaddle().paddleXPosition && choco.x <= getPaddle().paddleXPosition + getPaddle().paddleWidth) {
                        System.out.println("You Got it and +3 score for you");
                        choco.chocolateBlock.setVisible(false);
                        choco.taken = true;
                        currentScore += 3;
                        getScore().show(choco.x, choco.y, 3, Main.this);
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
                getGameScreen().scoreLabel.setText("Score: " + currentScore);
                getGameScreen().heartLabel.setText("Heart : " + numberOfHearts);

                getPaddle().setX(getPaddle().paddleXPosition);
                getPaddle().setY(getPaddle().paddleYPosition);
                getBall().setCenterX(getBall().ballXCoordinate);
                getBall().setCenterY(getBall().ballYCoordinate);

                for (Bonus choco : bonusItems) { //Bonus Items
                    choco.chocolateBlock.setY(choco.y); //Set the y-coordinate of the bonus item
                }
            }
        });

        if (getBall().ballYCoordinate >= Block.getPaddingTop() && getBall().ballYCoordinate <= (Block.getHeight() * (currentLevel + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(getBall().ballXCoordinate, getBall().ballYCoordinate);
                if (hitCode != Block.NO_HIT) {
                    currentScore += 1;

                    getScore().show(block.blockXCoordinate, block.blockYCoordinate, 1, this);

                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    //System.out.println("size is " + blocks.size());
                    getBall().resetCollideFlags();

                    if (block.type == Block.BLOCK_CHOCOLATE) {
                        final Bonus choco = new Bonus(block.row, block.column);
                        choco.timeCreated = currentTime;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                getGameScreen().root.getChildren().add(choco.chocolateBlock);
                            }
                        });
                        bonusItems.add(choco);
                    }

                    if (block.type == Block.BLOCK_STAR && !getBall().goldBall) {
                        getBall().goldTime = currentTime;
                        getBall().setFill(new ImagePattern(new Image("goldball.png")));
                        System.out.println("gold ball");
                        getGameScreen().root.getStyleClass().add("goldRoot");
                        getBall().goldBall = true;
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        numberOfHearts++;
                        getScore().showMessage("You got a heart!", this);
                    }

                    if (block.type == Block.BLOCK_SLIME) {
                        ball.ballHorizontalSpeed *= 0.3;
                        PauseTransition pause = new PauseTransition(Duration.seconds(3));
                        pause.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                ball.ballHorizontalSpeed *= 2;
                            }
                        });
                    }

                    if(block.type == Block.BLOCK_QUESTION){
                        Random random = new Random();
                        int r = random.nextInt(100);
                        if (r % 2 == 0){
                            numberOfHearts++;
                            getScore().showMessage("You got a heart!", this);
                        } else {
                            numberOfHearts--;
                            getScore().showMessage("You lost a heart!", this);
                        }
                        ball.checkGameOver();
                    }

                    if(block.type == Block.BLOCK_BOMB){
                        ball.setVisible(false);

                        // Set up a timer to make the ball visible again after 3 seconds
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                // Make the ball visible again after 3 seconds
                                ball.setVisible(true);
                            }
                        }, 1000);
                    }

                    if (hitCode == Block.HIT_RIGHT) {
                        getBall().collideToRightBlock = true;
                    } else if (hitCode == Block.HIT_BOTTOM) {
                        getBall().collideToBottomBlock = true;
                    } else if (hitCode == Block.HIT_LEFT) {
                        getBall().collideToLeftBlock = true;
                    } else if (hitCode == Block.HIT_TOP) {
                        getBall().collideToTopBlock = true;
                    }
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

    //Get Methods

    public Ball getBall() {
        return ball;
    }
    public Paddle getPaddle() {
        return paddle;
    }
    public KeyboardControls getKeyboardControls() {
        return keyboardControls;
    }
    public ButtonControls getButtonControls(){
        return buttonControls;
    }
    public GameObjectInitializer getGameObjectInitializer(){
        return gameObjectInitializer;
    }
    public LevelManager getLevelManager(){
        return levelManager;
    }
    public GameScreen getGameScreen(){
        return gameScreen;
    }
    public State getState(){
        return state;
    }
    public GameEngine getEngine(){
        return engine;
    }
    public Score getScore(){
        return new Score();
    }
}