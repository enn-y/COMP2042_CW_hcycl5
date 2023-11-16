package brickGame;

import brickGame.Controller.ButtonControls;
import brickGame.Controller.KeyboardControls;
import brickGame.Model.*;
import brickGame.Model.Serializables.BlockSerializable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application implements GameEngine.OnAction { //Application: JavaFX GUI, EventHandler: JavaFX Event, GameEngine.OnAction: GameEngine Event
    Ball ball; //Ball object
    Paddle paddle; //Paddle object
    KeyboardControls keyboardControls; //KeyboardControls object
    ButtonControls buttonControls; //ButtonControls object
    public int currentLevel = 0;
    public int windowWidth = 500; //Game window width
    public int windowHeight = 700; //Game window height
    public boolean goldBall = false; //Status of gold ball
    public boolean existHeartBlock = false; //Status of heart block
    public int destroyedBlockCount = 0; //Number of destroyed blocks
    private double ballVelocity = 1.000; //Velocity of ball
    public int numberOfHearts = 3; //Number of hearts, initialized at 3
    int currentScore = 0; //Score, initialized at 0, increases by 1 when a block is destroyed
    public long currentTime = 0; //Time, initialized at 0
    public long hitTime  = 0; //Time of hit, initialized at 0, used to check if ball is still on paddle
    public long goldTime = 0; //Time of gold ball, initialized at 0, used to check if gold ball is still active
    public GameEngine engine; //GameEngine object
    public static String savePath    = "D:/save/save.mdds"; //Path to save file
    public static String savePathDir = "D:/save/"; //Path to save directory
    public ArrayList<Block> blocks = new ArrayList<Block>(); //ArrayList to store the blocks
    public ArrayList<Bonus> bonusItems = new ArrayList<Bonus>(); //ArrayList to store the bonus items
    private Color[] blockColors = new Color[]{ //Array of colors for the blocks
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
    Label scoreLabel; //Label to display score
    Label heartLabel; //Label to display heart
    private Label levelLabel; //Label to display level
    private boolean loadFromSavedFile = false; //Status of loading from saved file
    public Stage primaryStage; //Stage is the top level JavaFX container, the window
    public Button loadButton = null; //Button to load game
    public Button newGameButton = null; //Button to start new game

    @Override
    public void start(Stage primaryStage) throws Exception { //Start method
        this.primaryStage = primaryStage; //Set primaryStage which is the game window

        if (!loadFromSavedFile) { //If NOT loading from saved file
            currentLevel++; //Increment the level
            if (currentLevel >1){
                new Score().showMessage("Level Up :)", this); //If the level is greater than 1, then display "Level Up :)", inherited from Score.java
            }
            if (currentLevel == 18) {
                new Score().showWin(this); //If level is 18, then display "You Win :)", inherited from Score.java
                return;
            }

            initializeBall(); //Initialize the ball
            initializePaddle(); //Initialize the paddle
            initializeBlocks(); //Initialize the blocks
            initializeKeyboardController(); //Initialize the controller

            loadButton = new Button("Load Game"); //Initialize the load button
            loadButton.setTranslateX(220); //Set the size of load button, x-coordinate (220)
            loadButton.setTranslateY(300); //Set the size of the load button, y-coordinate (300)

            newGameButton = new Button("Start New Game"); //Initialize the new game button
            newGameButton.setTranslateX(220); //Set the size of the new game button, x-coordinate (220)
            newGameButton.setTranslateY(340); //Set the size of the new game button, y-coordinate (340)
        }

        root = new Pane(); //Initialize the root pane, root is the instance of the JavaFX Pane class where Pane is a container to hold JavaFX elements
        scoreLabel = new Label("Score: " + currentScore); //Initialize the score label
        levelLabel = new Label("Level: " + currentLevel); //Initialize the level label
        levelLabel.setTranslateY(20); //Set the size of the level label, y-coordinate (20)
        heartLabel = new Label("Heart : " + numberOfHearts); //Initialize the heart label
        heartLabel.setTranslateX(windowWidth - 70); //Set the size of the heart label, x-coordinate (sceneWidth - 70)
        if (!loadFromSavedFile) { //If NOT loading from saved file
            root.getChildren().addAll(paddle, ball, scoreLabel, heartLabel, levelLabel, newGameButton); //Add the paddle, ball, score label, heart label, level label, and new game button to the root pane
        } else { //But if IT IS loading from saved file
            root.getChildren().addAll(paddle, ball, scoreLabel, heartLabel, levelLabel); //Add the paddle, ball, score label, heart label, and level label to the root pane, but NOT the new game button in this else block
        }
        for (Block block : blocks) { //For each block in the blocks ArrayList
            root.getChildren().add(block.rect); //Add the block to the root pane
        }
        Scene scene = new Scene(root, windowWidth, windowHeight); //Initialize the scene, scene is the instance of the JavaFX Scene class where Scene is the container for all content in a scene graph
        scene.getStylesheets().add("style.css"); //Add the style.css file to the scene
        scene.setOnKeyPressed(keyboardControls); //Listen for key presses

        primaryStage.setTitle("Game"); //Set the title of the game window
        primaryStage.setScene(scene); //Set the scene of the game window
        primaryStage.show(); //Show the game window

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

    private void initializeBlocks() { //Initialize the blocks
        for (int row = 0; row < 4; row++) { //For each row
            for (int column = 0; column < currentLevel + 1; column++) { //For each column where the condition is the level + 1 which means that the number of columns increases by 1 every level
                int r = new Random().nextInt(500); //Random number generator
                if (r % 5 == 0) { //If the remainder is 0
                    continue; //Block will not be created
                }
                int type;
                if (r % 10 == 1) { //If the remainder is 1
                    type = Block.BLOCK_CHOCOLATE; //Create a choco block
                } else if (r % 10 == 2) { //BUT IF the remainder is 2
                    if (!existHeartBlock) { //AND IF there is NO heart block
                        type = Block.BLOCK_HEART; //Create a heart block
                        existHeartBlock = true; //Set isExistHeartBlock to true, INDICATES that there is a heart block
                    } else { //BUT IF there IS a heart block
                        type = Block.BLOCK_NORMAL; //Create a normal block
                    }
                } else if (r % 10 == 3) { //BUT IF the remainder is 3
                    type = Block.BLOCK_STAR; //Create a star block
                } else { //BUT IF the remainder is NOT 1, 2, or 3 THEN create a normal block
                    type = Block.BLOCK_NORMAL; //Create a normal block
                }
                blocks.add(new Block(column, row, blockColors[r % (blockColors.length)], type)); //Add the block to the ArrayList
                //System.out.println("colors " + r % (colors.length));
            }
        }
    }

    public static void main(String[] args) { //Main method HERE
        launch(args); //LAUNCH GAME
    }

    //float oldXBreak; //Variable for the old x-coordinate of the paddle, NO USAGES - CHECK IF CAN DELETE

    private void initializeBall() { //Initialize the ball
        ball = new Ball(this);
        Random random = new Random(); //Random number generator
        ball.setBallXCoordinate(random.nextInt(windowWidth) + 1); //Random x-coordinate of the ball
        ball.setBallYCoordinate(random.nextInt(windowHeight - 200) + ((currentLevel + 1) * Block.getHeight()) + 15); //Random y-coordinate of the ball
        ball.setCenterX(ball.getBallXCoordinate()); //Set the center x-coordinate of the ball
        ball.setCenterY(ball.getBallYCoordinate());
        ball.setRadius(ball.ballRadius); //Set the radius of the ball
        ball.setFill(new ImagePattern(new Image("ball.png"))); //Using ball.png as the image of the ball
    }

    private void initializePaddle() { //Initialize the paddle
        paddle = new Paddle(this);
        paddle.setX(paddle.paddleXPosition); //Set the x-coordinate of the paddle
        paddle.setY(paddle.paddleYPosition); //Set the y-coordinate of the paddle
        paddle.setFill(new ImagePattern(new Image("block.jpg"))); //Using ball.png as the image of the ball
    }

    private void initializeKeyboardController() {
        keyboardControls = new KeyboardControls(this, paddle);
    }

    public boolean collideToPaddle = false; //Status for ball colliding with the paddle, set to FALSE
    public boolean collideToPaddleAndMoveToRight = true; //Status for ball colliding with the paddle and moving to the right
    public boolean collideToRightWall = false; //Status for ball colliding with the right wall, set to FALSE
    public boolean collideToLeftWall = false; //Status for ball colliding with the left wall, set to FALSE
    public boolean collideToRightBlock = false; //Status for ball colliding to the right side of the block, set to FALSE
    public boolean collideToBottomBlock = false; //Status for ball colliding to the bottom side of the block, set to FALSE
    public boolean collideToLeftBlock = false; //Status for ball colliding to the left side of the block, set to FALSE
    public boolean collideToTopBlock = false; //Status for ball colliding to the top side of the block, set to FALSE

    public void resetCollideFlags() { //Reset all collision flags to FALSE so game can identify new collision events in the next game loop
        collideToPaddle = false;
        collideToPaddleAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }

    void blockDestroyedCount() { //Check the number of destroyed blocks
        if (destroyedBlockCount == blocks.size()) { //If the number of destroyed blocks is equal to the number of blocks
            //TODO win level todo...
            LevelManager levelManager = new LevelManager(this, ball);
            //System.out.println("You Win");
            levelManager.nextLevel();
        }
    }

    public void saveGame() { //Save the game
        new Thread(new Runnable() {
            @Override
            public void run() {
                new File(savePathDir).mkdirs();
                File file = new File(savePath);
                ObjectOutputStream outputStream = null;
                try { //Try to save the game, save all the variables
                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    outputStream.writeInt(currentLevel);
                    outputStream.writeInt(currentScore);
                    outputStream.writeInt(numberOfHearts);
                    outputStream.writeInt(destroyedBlockCount);

                    outputStream.writeDouble(ball.ballXCoordinate);
                    outputStream.writeDouble(ball.ballYCoordinate);
                    outputStream.writeDouble(paddle.paddleXPosition);
                    outputStream.writeDouble(paddle.paddleYPosition);
                    outputStream.writeDouble(paddle.paddleCenter);
                    outputStream.writeLong(currentTime);
                    outputStream.writeLong(goldTime);
                    outputStream.writeDouble(ball.ballHorizontalSpeed);

                    outputStream.writeBoolean(existHeartBlock);
                    outputStream.writeBoolean(goldBall);
                    outputStream.writeBoolean(ball.goDownBall);
                    outputStream.writeBoolean(ball.goRightBall);
                    outputStream.writeBoolean(collideToPaddle);
                    outputStream.writeBoolean(collideToPaddleAndMoveToRight);
                    outputStream.writeBoolean(collideToRightWall);
                    outputStream.writeBoolean(collideToLeftWall);
                    outputStream.writeBoolean(collideToRightBlock);
                    outputStream.writeBoolean(collideToBottomBlock);
                    outputStream.writeBoolean(collideToLeftBlock);
                    outputStream.writeBoolean(collideToTopBlock);

                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
                    for (Block block : blocks) {
                        if (block.isDestroyed) {
                            continue;
                        }
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                    }

                    outputStream.writeObject(blockSerializables);

                    Platform.runLater(() -> {
                        new Score().showMessage("Game Saved", Main.this); //Display "Game Saved" when the game is saved
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void loadGame() { //Load the game
        LoadSave loadSave = new LoadSave();
        loadSave.read(); //Read the saved file, assign the variables to the saved variables

        existHeartBlock = loadSave.isExistHeartBlock;
        goldBall = loadSave.isGoldStatus;
        ball.goDownBall = loadSave.goDownBall;
        ball.goRightBall = loadSave.goRightBall;
        collideToPaddle = loadSave.collideToPaddle;
        collideToPaddleAndMoveToRight = loadSave.collideToPaddleAndMoveToRight;
        collideToRightWall = loadSave.collideToRightWall;
        collideToLeftWall = loadSave.collideToLeftWall;
        collideToRightBlock = loadSave.collideToRightBlock;
        collideToBottomBlock = loadSave.collideToBottomBlock;
        collideToLeftBlock = loadSave.collideToLeftBlock;
        collideToTopBlock = loadSave.collideToTopBlock;
        currentLevel = loadSave.level;
        currentScore = loadSave.score;
        numberOfHearts = loadSave.heart;
        destroyedBlockCount = loadSave.destroyedBlockCount;
        ball.ballXCoordinate = loadSave.xBall;
        ball.ballYCoordinate = loadSave.yBall;
        paddle.paddleXPosition = loadSave.xBreak;
        paddle.paddleYPosition = loadSave.yBreak;
        paddle.paddleCenter = loadSave.centerBreakX;
        currentTime = loadSave.time;
        goldTime = loadSave.goldTime;
        ball.ballHorizontalSpeed = loadSave.vX;

        blocks.clear();
        bonusItems.clear();

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            blocks.add(new Block(ser.row, ser.column, blockColors[r % blockColors.length], ser.type));
        }

        try {
            loadFromSavedFile = true;
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restartGame() { //Restart the game
        try {
            currentLevel = 0;
            numberOfHearts = 3;
            currentScore = 0;
            ball.ballHorizontalSpeed = 1.000;
            destroyedBlockCount = 0;
            resetCollideFlags();
            ball.goDownBall = true;

            goldBall = false;
            existHeartBlock = false;
            hitTime = 0;
            hitTime = 0;
            currentTime = 0;
            goldTime = 0;

            blocks.clear();
            bonusItems.clear();

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInit() {
    }

    public void onPhysicsUpdate() { //Updates game physics and logic during each frame of the game
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                blockDestroyedCount();
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
                    resetCollideFlags();

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
                        collideToRightBlock = true;
                    } else if (hitCode == Block.HIT_BOTTOM) {
                        collideToBottomBlock = true;
                    } else if (hitCode == Block.HIT_LEFT) {
                        collideToLeftBlock = true;
                    } else if (hitCode == Block.HIT_TOP) {
                        collideToTopBlock = true;
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

    public void checkGameOver() {
        if (!goldBall && numberOfHearts == 0) {
            new Score().showGameOver(this);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            engine.stop();
        }
    }

    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }
}