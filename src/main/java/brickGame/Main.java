package brickGame;

import brickGame.Controller.ButtonController;
import brickGame.Controller.KeyboardController;
import brickGame.Model.*;
import brickGame.Model.Ball.BallFactory;
import brickGame.Model.Ball.BallModel;
import brickGame.Model.Blocks.*;
import brickGame.Model.Player.PlayerInitializer;
import brickGame.Model.Player.PlayerModel;
import brickGame.View.GameScreen;
import brickGame.View.State;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application { //Application: JavaFX GUI, EventHandler: JavaFX Event, GameEngine.OnAction: GameEngine Event
    BallModel ballModel; //Ball object
    PlayerModel playerModel; //Paddle object
    KeyboardController keyboardController; //KeyboardControls object
    ButtonController buttonController; //ButtonControls object
    LevelManager levelManager; //LevelManager object
    GameScreen gameScreen; //GameScreen object
    State state; //State object
    Score score; //Score object
    GameEngine engine; //GameEngine object
    BallFactory ballFactory; //BallFactory object
    PlayerInitializer playerInitializer; //PlayerFactory object
    BlockFactory blockFactory; //BlockFactory object
    public int currentLevel = 0; //Level, initialized at 0, increases by 1 when a level is completed
    public int currentScore = 0; //Score, initialized at 0, increases by 1 when a block is destroyed

    @Override
    public void start(Stage primaryStage) throws Exception { //Start method
        ballModel = new BallModel(this); //Initialize the ball
        playerModel = PlayerModel.getInstance(this); //Initialize the paddle
        keyboardController = new KeyboardController(this);
        gameScreen = new GameScreen(this); //Initialize the game screen
        state = new State(this); //Initialize the state
        buttonController = new ButtonController(this); //Initialize the button controls
        engine = new GameEngine(this); //Initialize the game engine
        score = new Score(); //Initialize the score
        levelManager = new LevelManager(this); //Initialize the level manager
        ballFactory = new BallFactory(this); //Initialize the ball factory
        playerInitializer = new PlayerInitializer(this); //Initialize the player factory
        blockFactory = new BlockFactory(this); //Initialize the block factory

        getGameScreen().primaryStage = primaryStage; //Set primaryStage which is the game window

        if (!getState().loadFromSavedFile) { //If NOT loading from saved file
            currentLevel++; //Increment the level
            /*if (currentLevel >1){
                getScore().showMessage("Level Up :)", this); //If the level is greater than 1, then display "Level Up :)", inherited from Score.java
            }
            if (currentLevel == 10) {
                getScore().showWin(this); //If level is 18, then display "You Win :)", inherited from Score.java
                return;
            }
             */

            getEngine().checkWin(); //Check if the player has won

            ballFactory.initializeBall(); //Initialize the ball
            playerInitializer.initializePaddle(); //Initialize the paddle
            blockFactory.initializeBlocks(); //Initialize the blocks
            getGameScreen().AddButtons(); //Add the buttons
        }

        getGameScreen().AddLabels(); //Add the labels
        getGameScreen().AddElements(); //Add the elements
        getGameScreen().CreateScene(); //Create the scene

        getState().checkLoadFromSavedFile(); //Check if loading from saved file
    }

    public static void main(String[] args) { //Main method HERE
        launch(args); //LAUNCH GAME
    }

    public BallModel getBall() { //Getter method for ball
        return ballModel;
    }
    public PlayerModel getPlayer() { //Getter method for paddle
        return playerModel;
    }
    public KeyboardController getKeyboardControls() { //Getter method for keyboard controls
        return keyboardController;
    }
    public ButtonController getButtonControls(){ //Getter method for button controls
        return buttonController;
    }
    public LevelManager getLevelManager(){ //Getter method for level manager
        return levelManager;
    }
    public GameScreen getGameScreen(){ //Getter method for game screen
        return gameScreen;
    }
    public State getState(){ //Getter method for state
        return state;
    }
    public GameEngine getEngine(){ //Getter method for game engine
        return engine;
    }
    public Score getScore(){ //Getter method for score
        return new Score();
    }
}