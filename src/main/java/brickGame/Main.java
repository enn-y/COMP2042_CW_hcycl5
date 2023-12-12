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

public class Main extends Application {
    BallModel ballModel;
    PlayerModel playerModel;
    KeyboardController keyboardController;
    ButtonController buttonController;
    LevelManager levelManager;
    GameScreen gameScreen;
    State state;
    ScoreManager score;
    GameEngine engine;
    BallFactory ballFactory;
    PlayerInitializer playerInitializer;
    BlockFactory blockFactory;
    public int currentLevel = 0;
    public int currentScore = 0;

    /**
     * The start method starts the game.
     * It initializes the ball, paddle, game screen, state, button controls, game engine, score, level manager, ball factory, player factory, and block factory.
     * It also checks if the game is loading from a saved file.
     * @param primaryStage The game window.
     */

    @Override
    public void start(Stage primaryStage) {
        ballModel = new BallModel(this);
        playerModel = PlayerModel.getInstance(this);
        keyboardController = new KeyboardController(this);
        gameScreen = new GameScreen(this);
        state = new State(this);
        buttonController = new ButtonController(this);
        engine = new GameEngine(this);
        score = new ScoreManager();
        levelManager = new LevelManager(this);
        ballFactory = new BallFactory(this);
        playerInitializer = new PlayerInitializer(this);
        blockFactory = new BlockFactory(this);

        getGameScreen().primaryStage = primaryStage;

        if (!getState().loadFromSavedFile) {
            currentLevel++;
            getEngine().checkWin();
            ballFactory.initializeBall();
            playerInitializer.initializePaddle();
            blockFactory.initializeBlocks();
            getGameScreen().AddButtons();
        }
        getGameScreen().AddLabels();
        getGameScreen().AddElements();
        getGameScreen().CreateScene();
        getState().checkLoadFromSavedFile();
    }

    /**
     * The main method launches the game.
     * @param args represents command-line arguments.
     */

    public static void main(String[] args) {
        launch(args);
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