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

public class State {

    Main main;
    public boolean isExistHeartBlock;
    public boolean isGoldStatus;
    public boolean goDownBall;
    public boolean goRightBall;
    public boolean collideToPaddle;
    public boolean collideToPaddleAndMoveToRight;
    public boolean collideToRightWall;
    public boolean collideToLeftWall;
    public boolean collideToRightBlock;
    public boolean collideToBottomBlock;
    public boolean collideToLeftBlock;
    public boolean collideToTopBlock;
    public int level;
    public int score;
    public int heart;
    public int destroyedBlockCount;
    public double xBall;
    public double yBall;
    public double xBreak;
    public double yBreak;
    public double centerBreakX;
    public long time;
    public long goldTime;
    public double vX;
    public ArrayList<BlockSerializable> blocks = new ArrayList<BlockSerializable>();
    public boolean loadFromSavedFile = false;
    public static String savePath    = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

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

    public void saveGame() {
        new Thread(() -> {
            new File(savePathDir).mkdirs();
            File file = new File(savePath);
            ObjectOutputStream outputStream = null;
            try {
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

                ArrayList<BlockSerializable> blockSerializables = new ArrayList<>();
                for (BlockModel block : main.getEngine().blocks) {
                    if (block.isDestroyed) {
                        continue;
                    }
                    blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                }

                outputStream.writeObject(blockSerializables);

                Platform.runLater(() -> {
                    new ScoreManager().showMessage("Game Saved", main);
                });

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert outputStream != null;
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
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

    public void loadGame() {
        read();

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

    public void restartGame() {
        try {
            main.getEngine().stop();
            level = 0;
            main.currentLevel = level;
            main.getPlayer().numberOfHearts = 3;
            main.currentScore = 0;
            main.getBall().ballHorizontalSpeed = 1.000;
            main.getPlayer().destroyedBlockCount = 0;
            main.getBall().resetCollideFlags();
            main.getBall().goDownBall = true;

            main.getBall().goldBall = false;
            main.getPlayer().existHeartBlock = false;
            main.getPlayer().hitTime = 0;
            main.getPlayer().currentTime = 0;
            main.getBall().goldTime = 0;

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
        if (!loadFromSavedFile) {
            if (main.currentLevel > 1 && main.currentLevel < 10) {
                main.getButtonControls().hideButtons();
                main.getEngine().setOnAction(new GameEngine(main));
                main.getEngine().setFps(120);
                main.getEngine().start();
            }

            main.getButtonControls().loadButton.setOnAction(main.getButtonControls().createLoadButtonHandler());
            main.getButtonControls().newGameButton.setOnAction(main.getButtonControls().createNewGameButtonHandler());
            main.getButtonControls().exitButton.setOnAction(main.getButtonControls().createExitButtonHandler());
            main.getButtonControls().instructionsButton.setOnAction(main.getButtonControls().createInstructionsButtonHandler());

        } else {
            main.getEngine().setOnAction(new GameEngine(main));
            main.getEngine().setFps(120);
            main.getEngine().start();
            loadFromSavedFile = false;
        }
    }
}
