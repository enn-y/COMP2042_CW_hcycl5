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

/**
 * The main class for the brickGame application.
 * It extends the JavaFX Application class.
 * It contains the main method to launch the game.
 * It also contains the getter methods to access the components of the game.
 * It also contains the start method to start the game.
 */

public class Main extends Application { //Application: JavaFX GUI, EventHandler: JavaFX Event, GameEngine.OnAction: GameEngine Event
    BallModel ballModel; //Ball object
    PlayerModel playerModel; //Paddle object
    KeyboardController keyboardController; //KeyboardControls object
    ButtonController buttonController; //ButtonControls object
    LevelManager levelManager; //LevelManager object
    GameScreen gameScreen; //GameScreen object
    State state; //State object
    ScoreManager score; //Score object
    GameEngine engine; //GameEngine object
    BallFactory ballFactory; //BallFactory object
    PlayerInitializer playerInitializer; //PlayerFactory object
    BlockFactory blockFactory; //BlockFactory object
    public int currentLevel = 0; //Level, initialized at 0, increases by 1 when a level is completed
    public int currentScore = 0; //Score, initialized at 0, increases by 1 when a block is destroyed

    /**
     * The start method starts the game.
     * It initializes the ball, paddle, game screen, state, button controls, game engine, score, level manager, ball factory, player factory, and block factory.
     * It also checks if the game is loading from a saved file.
     * @param primaryStage The game window.
     * @throws Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception { //Start method
        ballModel = new BallModel(this); //Initialize the ball
        playerModel = PlayerModel.getInstance(this); //Initialize the paddle, singleton design pattern
        keyboardController = new KeyboardController(this);
        gameScreen = new GameScreen(this); //Initialize the game screen
        state = new State(this); //Initialize the state
        buttonController = new ButtonController(this); //Initialize the button controls
        engine = new GameEngine(this); //Initialize the game engine
        score = new ScoreManager(); //Initialize the score
        levelManager = new LevelManager(this); //Initialize the level manager
        ballFactory = new BallFactory(this); //Initialize the ball factory, factory design pattern
        playerInitializer = new PlayerInitializer(this); //Initialize the player factory
        blockFactory = new BlockFactory(this); //Initialize the block factory, factory design pattern

        getGameScreen().primaryStage = primaryStage; //Set primaryStage which is the game window

        if (!getState().loadFromSavedFile) { //If NOT loading from saved file
            currentLevel++; //Set the level to 1
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

    /**
     * The main method launches the game.
     * @param args
     */

    public static void main(String[] args) { //Main method HERE
        launch(args); //LAUNCH GAME
    }

    /**
     * The getBall method returns the ball object.
     * @return The ballModel object.
     */

    public BallModel getBall() { //Getter method for ball
        return ballModel;
    }

    /**
     * The getPlayer method returns the paddle object.
     * @return The playerModel object.
     */

    public PlayerModel getPlayer() { //Getter method for paddle
        return playerModel;
    }

    /**
     * The getKeyboardControls method returns the keyboard controls object.
     * @return The keyboardController object.
     */

    public KeyboardController getKeyboardControls() { //Getter method for keyboard controls
        return keyboardController;
    }

    /**
     * The getButtonControls method returns the button controls object.
     * @return The buttonController object.
     */

    public ButtonController getButtonControls(){ //Getter method for button controls
        return buttonController;
    }

    /**
     * The getLevelManager method returns the level manager object.
     * @return The levelManager object.
     */

    public LevelManager getLevelManager(){ //Getter method for level manager
        return levelManager;
    }

    /**
     * The getGameScreen method returns the game screen object.
     * @return The gameScreen object.
     */

    public GameScreen getGameScreen(){ //Getter method for game screen
        return gameScreen;
    }

    /**
     * The getState method returns the state object.
     * @return The state object.
     */

    public State getState(){ //Getter method for state
        return state;
    }

    /**
     * The getEngine method returns the game engine object.
     * @return The engine object.
     */

    public GameEngine getEngine(){ //Getter method for game engine
        return engine;
    }

    /**
     * The getScore method returns the score object.
     * @return The score object.
     */

    public ScoreManager getScore(){ //Getter method for score
        return new ScoreManager();
    }
}