package brickGame;

import brickGame.Controller.ButtonControls;
import brickGame.Controller.KeyboardControls;
import brickGame.Model.*;
import brickGame.Model.Blocks.*;
import brickGame.Model.Interface.OnAction;
import brickGame.View.GameScreen;
import brickGame.View.State;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

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
        ball = new Ball(this); //Initialize the ball
        player = new Player(this); //Initialize the paddle
        keyboardControls = new KeyboardControls(this);
        gameObjectInitializer = new GameObjectInitializer(this); //Initialize the initializer
        gameScreen = new GameScreen(this); //Initialize the game screen
        state = new State(this); //Initialize the state
        buttonControls = new ButtonControls(this); //Initialize the button controls
        engine = new GameEngine(this); //Initialize the game engine
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

            getGameObjectInitializer().initializeBall(); //Initialize the ball
            getGameObjectInitializer().initializePaddle(); //Initialize the paddle
            getGameObjectInitializer().initializeBlocks(); //Initialize the blocks
            getGameScreen().AddButtons();
        }

        getGameScreen().AddLabels(); //Add the labels
        getGameScreen().AddElements(); //Add the elements
        getGameScreen().CreateScene(); //Create the scene

        getState().checkLoadFromSavedFile();
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
            getEngine().createBlock(); //Create the blocks
        }
    }

    @Override
    public void onTime(long time) { //Update the time
        this.getPlayer().currentTime = time;
    }

    public Ball getBall() { //Getter method for ball
        return ball;
    }
    public Player getPlayer() { //Getter method for paddle
        return player;
    }
    public KeyboardControls getKeyboardControls() { //Getter method for keyboard controls
        return keyboardControls;
    }
    public ButtonControls getButtonControls(){ //Getter method for button controls
        return buttonControls;
    }
    public GameObjectInitializer getGameObjectInitializer(){ //Getter method for game object initializer
        return gameObjectInitializer;
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