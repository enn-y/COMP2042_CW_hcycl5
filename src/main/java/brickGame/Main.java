package brickGame;

import brickGame.Controller.ButtonControls;
import brickGame.Controller.KeyboardControls;
import brickGame.Model.*;
import brickGame.Model.Blocks.*;
import brickGame.Model.Interface.OnAction;
import brickGame.View.GameScreen;
import brickGame.View.State;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application implements OnAction { //Application: JavaFX GUI, EventHandler: JavaFX Event, GameEngine.OnAction: GameEngine Event
    Ball ball; //Ball object
    Player player; //Paddle object
    KeyboardControls keyboardControls; //KeyboardControls object
    ButtonControls buttonControls; //ButtonControls object
    GameObjectInitializer gameObjectInitializer; //GameObjectInitializer object
    LevelManager levelManager; //LevelManager object
    GameScreen gameScreen; //GameScreen object
    State state; //State object
    Score score; //Score object
    GameEngine engine; //GameEngine object
    public int currentLevel = 0; //Level, initialized at 0, increases by 1 when a level is completed
    public int currentScore = 0; //Score, initialized at 0, increases by 1 when a block is destroyed

    @Override
    public void start(Stage primaryStage) throws Exception { //Start method
        ball = new Ball(this);
        player = new Player(this);
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

                if (getPlayer().currentTime - getBall().goldTime > 5000) { //Gold Ball
                    getBall().setFill(new ImagePattern(new Image("ball.png")));
                    getGameScreen().root.getStyleClass().remove("goldRoot");
                    getBall().goldBall = false;
                }

                for (Bonus choco : getEngine().bonusItems) { //Bonus Items
                    if (choco.y > getGameScreen().windowHeight || choco.taken) {
                        continue;
                    }
                    if (choco.y >= getPlayer().paddleYPosition && choco.y <= getPlayer().paddleYPosition + getPlayer().paddleHeight && choco.x >= getPlayer().paddleXPosition && choco.x <= getPlayer().paddleXPosition + getPlayer().paddleWidth) {
                        System.out.println("You Got it and +3 score for you");
                        choco.chocolateBlock.setVisible(false);
                        choco.taken = true;
                        currentScore += 3;
                        getScore().show(choco.x, choco.y, 3, Main.this);
                    }
                    choco.y += ((getPlayer().currentTime - choco.timeCreated) / 1000.000) + 1.000;
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
                getGameScreen().heartLabel.setText("Heart : " + getPlayer().numberOfHearts);

                getPlayer().setX(getPlayer().paddleXPosition);
                getPlayer().setY(getPlayer().paddleYPosition);
                getBall().setCenterX(getBall().ballXCoordinate);
                getBall().setCenterY(getBall().ballYCoordinate);

                for (Bonus choco : getEngine().bonusItems) { //Bonus Items
                    choco.chocolateBlock.setY(choco.y); //Set the y-coordinate of the bonus item
                }
            }
        });

        if (getBall().ballYCoordinate >= Block.getPaddingTop() && getBall().ballYCoordinate <= (Block.getHeight() * (currentLevel + 1)) + Block.getPaddingTop()) {
            for (final Block block : getEngine().blocks) {
                int hitCode = block.checkHitToBlock(getBall().ballXCoordinate, getBall().ballYCoordinate);
                if (hitCode != Block.NO_HIT) {
                    currentScore += 1;

                    getScore().show(block.blockXCoordinate, block.blockYCoordinate, 1, this);

                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    getPlayer().destroyedBlockCount++;
                    //System.out.println("size is " + blocks.size());
                    getBall().resetCollideFlags();

                    if (block.type == Block.BLOCK_CHOCOLATE) {
                        ChocolateBlock chocolateBlock = new ChocolateBlock(block.row, block.column, this);
                        chocolateBlock.blockType();
                    }

                    if (block.type == Block.BLOCK_STAR && !getBall().goldBall) {
                        StarBlock starBlock = new StarBlock(block.row, block.column, this);
                        starBlock.blockType();
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        HeartBlock heartBlock = new HeartBlock(block.row, block.column, this);
                        heartBlock.blockType();
                    }

                    if (block.type == Block.BLOCK_SLIME) {
                        SlimeBlock slimeBlock = new SlimeBlock(block.row, block.column, this);
                        slimeBlock.blockType();
                    }

                    if(block.type == Block.BLOCK_QUESTION){
                        QuestionBlock questionBlock = new QuestionBlock(block.row, block.column, this);
                        questionBlock.blockType();
                    }

                    if(block.type == Block.BLOCK_BOMB){
                        BombBlock bombBlock = new BombBlock(block.row, block.column, this);
                        bombBlock.blockType();
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
        this.getPlayer().currentTime = time;
    }

    //Get Methods

    public Ball getBall() {
        return ball;
    }
    public Player getPlayer() {
        return player;
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