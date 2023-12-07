package brickGame.View;

import brickGame.Main;
import brickGame.Model.*;
import brickGame.Model.Blocks.BlockModel;
import brickGame.Model.Serializables.BlockSerializable;
import javafx.application.Platform;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The State class is used to save and load the game.
 *
 * @author Lua Chong En
 *
 */

public class State { //Methods include: read

    Main main; //Main instance to access the components of the game
    public boolean isExistHeartBlock; //Status of heart block
    public boolean isGoldStatus; //Status of gold ball
    public boolean goDownBall; //Status of ball going down
    public boolean goRightBall; //Status of ball going right
    public boolean collideToPaddle; //Status of ball colliding to paddle
    public boolean collideToPaddleAndMoveToRight; //Status of ball colliding to paddle and moving to right
    public boolean collideToRightWall; //Status of ball colliding to right wall
    public boolean collideToLeftWall; //Status of ball colliding to left wall
    public boolean collideToRightBlock; //Status of ball colliding to right block
    public boolean collideToBottomBlock; //Status of ball colliding to bottom block
    public boolean collideToLeftBlock; //Status of ball colliding to left block
    public boolean collideToTopBlock; //Status of ball colliding to top block
    public int level; //Level of the game
    public int score; //Score of the game
    public int heart; //Heart of the game
    public int destroyedBlockCount; //Number of destroyed blocks
    public double xBall; //x-coordinate of ball
    public double yBall; //y-coordinate of ball
    public double xBreak; //x-coordinate of paddle
    public double yBreak; //y-coordinate of paddle
    public double centerBreakX; //Center of paddle
    public long time; //Time of the game
    public long goldTime; //Time of gold ball
    public double vX; //Horizontal speed of ball
    public ArrayList<BlockSerializable> blocks = new ArrayList<BlockSerializable>(); //Array list of blocks
    public boolean loadFromSavedFile = false; //Status of loading from saved file
    public static String savePath    = "D:/save/save.mdds"; //Path to save file
    public static String savePathDir = "D:/save/"; //Path to save directory

    /**
     * Constructor used to save and load the game.
     * @param main The Main instance to access the components of the game.
     */

    public State(Main main) {
        this.main = main;
    }

    /**
     * The read method is used to read the saved file.
     * It assigns the variables to the saved variables.
     * It also reads the saved file and assigns the variables to the saved variables.
     */

    public void read() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(savePath)));

            level = inputStream.readInt();
            score = inputStream.readInt();
            heart = inputStream.readInt();
            destroyedBlockCount = inputStream.readInt();

            xBall = inputStream.readDouble();
            yBall = inputStream.readDouble();
            xBreak = inputStream.readDouble();
            yBreak = inputStream.readDouble();
            centerBreakX = inputStream.readDouble();
            time = inputStream.readLong();
            goldTime = inputStream.readLong();
            vX = inputStream.readDouble();

            isExistHeartBlock = inputStream.readBoolean();
            isGoldStatus = inputStream.readBoolean();
            goDownBall = inputStream.readBoolean();
            goRightBall = inputStream.readBoolean();
            collideToPaddle = inputStream.readBoolean();
            collideToPaddleAndMoveToRight = inputStream.readBoolean();
            collideToRightWall = inputStream.readBoolean();
            collideToLeftWall = inputStream.readBoolean();
            collideToRightBlock = inputStream.readBoolean();
            collideToBottomBlock = inputStream.readBoolean();
            collideToLeftBlock = inputStream.readBoolean();
            collideToTopBlock = inputStream.readBoolean();

            try {
                blocks = (ArrayList<BlockSerializable>) inputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The saveGame method is used to save the game.
     * It saves all the variables.
     * It also saves the variables to the saved file.
     */

    public void saveGame() { //Save the game
        new Thread(new Runnable() {
            @Override
            public void run() {
                new File(savePathDir).mkdirs();
                File file = new File(savePath);
                ObjectOutputStream outputStream = null;
                try { //Try to save the game, save all the variables
                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    outputStream.writeInt(main.currentLevel);
                    outputStream.writeInt(main.currentScore);
                    outputStream.writeInt(main.getPlayer().numberOfHearts);
                    outputStream.writeInt(main.getPlayer().destroyedBlockCount);

                    outputStream.writeDouble(main.getBall().ballXCoordinate);
                    outputStream.writeDouble(main.getBall().ballYCoordinate);
                    outputStream.writeDouble(main.getPlayer().paddleXPosition);
                    outputStream.writeDouble(main.getPlayer().paddleYPosition);
                    outputStream.writeDouble(main.getPlayer().paddleCenter);
                    outputStream.writeLong(main.getPlayer().currentTime);
                    outputStream.writeLong(main.getBall().goldTime);
                    outputStream.writeDouble(main.getBall().ballHorizontalSpeed);

                    outputStream.writeBoolean(main.getPlayer().existHeartBlock);
                    outputStream.writeBoolean(main.getBall().goldBall);
                    outputStream.writeBoolean(main.getBall().goDownBall);
                    outputStream.writeBoolean(main.getBall().goRightBall);
                    outputStream.writeBoolean(main.getBall().collideToPaddle);
                    outputStream.writeBoolean(main.getBall().collideToPaddleAndMoveToRight);
                    outputStream.writeBoolean(main.getBall().collideToRightWall);
                    outputStream.writeBoolean(main.getBall().collideToLeftWall);
                    outputStream.writeBoolean(main.getBall().collideToRightBlock);
                    outputStream.writeBoolean(main.getBall().collideToBottomBlock);
                    outputStream.writeBoolean(main.getBall().collideToLeftBlock);
                    outputStream.writeBoolean(main.getBall().collideToTopBlock);

                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
                    for (BlockModel block : main.getEngine().blocks) {
                        if (block.isDestroyed) {
                            continue;
                        }
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                    }

                    outputStream.writeObject(blockSerializables);

                    Platform.runLater(() -> {
                        new ScoreManager().showMessage("Game Saved", main); //Display "Game Saved" when the game is saved
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

    /**
     * The loadGame method is used to load the game.
     * It loads all the variables.
     * It also loads the variables to the saved file.
     * It also clears the blocks ArrayList.
     * It also clears the bonusItems ArrayList.
     * It also sets the loadFromSavedFile to true, INDICATES that it is loading from saved file.
     */

    public void loadGame() { //Load the game
        read(); //Read the saved file, assign the variables to the saved variables

        main.getPlayer().existHeartBlock = isExistHeartBlock;
        main.getBall().goldBall = isGoldStatus;
        main.currentLevel = level-1;
        main.currentScore = score;
        main.getPlayer().numberOfHearts = heart;
        main.getPlayer().destroyedBlockCount = destroyedBlockCount;

        main.getBall().goDownBall = goDownBall;
        main.getBall().goRightBall = goRightBall;
        main.getBall().collideToPaddle = collideToPaddle;
        main.getBall().collideToPaddleAndMoveToRight = collideToPaddleAndMoveToRight;
        main.getBall().collideToRightWall = collideToRightWall;
        main.getBall().collideToLeftWall = collideToLeftWall;
        main.getBall().collideToRightBlock = collideToRightBlock;
        main.getBall().collideToBottomBlock = collideToBottomBlock;
        main.getBall().collideToLeftBlock = collideToLeftBlock;
        main.getBall().collideToTopBlock = collideToTopBlock;
        main.getBall().ballXCoordinate = xBall;
        main.getBall().ballYCoordinate = yBall;
        main.getBall().ballHorizontalSpeed = vX;

        main.getPlayer().paddleXPosition = xBreak;
        main.getPlayer().paddleYPosition = yBreak;
        main.getPlayer().paddleCenter = centerBreakX;
        main.getPlayer().currentTime = time;
        main.getBall().goldTime = goldTime;

        blocks.clear();
        main.getEngine().bonusItems.clear();

        for (BlockSerializable ser : blocks) {
            int r = new Random().nextInt(200);
            main.getEngine().blocks.add(new BlockModel(ser.row, ser.column, main.getEngine().blockColors[r % main.getEngine().blockColors.length], ser.type));
        }

        try {
            loadFromSavedFile = true;
            main.start(main.getGameScreen().primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The restartGame method is used to restart the game.
     * It resets all the variables.
     * It also clears the blocks ArrayList.
     * It also clears the bonusItems ArrayList.
     * It also sets the loadFromSavedFile to false, INDICATES that it is a new game.
     */

    public void restartGame() { //Restart the game
        try {
            main.getEngine().stop();
            level = 0;
            main.currentLevel = level;
            main.getPlayer().numberOfHearts = 3;
            main.currentScore = 0;
            main.getBall().ballHorizontalSpeed = 1.000;
            main.getPlayer().destroyedBlockCount = 0; //Changes made here
            main.getBall().resetCollideFlags();
            main.getBall().goDownBall = true;

            main.getBall().goldBall = false;
            main.getPlayer().existHeartBlock = false;
            main.getPlayer().hitTime = 0;
            main.getPlayer().currentTime = 0;
            main.getBall().goldTime = 0; //Changes made here

            blocks.clear();
            main.getEngine().bonusItems.clear();

            main.start(main.getGameScreen().primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The checkLoadFromSavedFile method is used to check if the game is loading from saved file.
     * It also checks if the game is a new game.
     */

    public void checkLoadFromSavedFile(){
        if (!loadFromSavedFile) { //If NOT loading from saved file
            if (main.currentLevel > 1 && main.currentLevel < 10) { //If the level is greater than 1 and less than 18
                main.getButtonControls().hideButtons(); //Hide the buttons
                main.getEngine().setOnAction(new GameEngine(main)); //Listen for events
                main.getEngine().setFps(120); //Set FPS
                main.getEngine().start(); //Start the game engine
            }

            main.getButtonControls().loadButton.setOnAction(main.getButtonControls().createLoadButtonHandler());
            main.getButtonControls().newGameButton.setOnAction(main.getButtonControls().createNewGameButtonHandler());
            main.getButtonControls().exitButton.setOnAction(main.getButtonControls().createExitButtonHandler());
            main.getButtonControls().instructionsButton.setOnAction(main.getButtonControls().createInstructionsButtonHandler());

        } else { //But if IT IS loading from saved file
            main.getEngine().setOnAction(new GameEngine(main)); //Listen for events
            main.getEngine().setFps(120); //Set FPS
            main.getEngine().start(); //Start the game engine
            loadFromSavedFile = false; //Set loadFromSave to false, INDICATES that it is a new game
        }
    }
}
