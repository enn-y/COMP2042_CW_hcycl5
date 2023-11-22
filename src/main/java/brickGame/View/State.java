package brickGame.View;

import brickGame.Main;
import brickGame.Model.*;
import brickGame.Model.Blocks.Block;
import brickGame.Model.Serializables.BlockSerializable;
import javafx.application.Platform;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class State { //Methods include: read

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
    public boolean loadFromSavedFile = false; //Status of loading from saved file
    public static String savePath    = "D:/save/save.mdds"; //Path to save file
    public static String savePathDir = "D:/save/"; //Path to save directory
    public State(Main main) {
        this.main = main;
    }

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
                    outputStream.writeInt(main.numberOfHearts);
                    outputStream.writeInt(destroyedBlockCount);

                    outputStream.writeDouble(main.getBall().ballXCoordinate);
                    outputStream.writeDouble(main.getBall().ballYCoordinate);
                    outputStream.writeDouble(main.getPaddle().paddleXPosition);
                    outputStream.writeDouble(main.getPaddle().paddleYPosition);
                    outputStream.writeDouble(main.getPaddle().paddleCenter);
                    outputStream.writeLong(main.currentTime);
                    outputStream.writeLong(goldTime);
                    outputStream.writeDouble(main.getBall().ballHorizontalSpeed);

                    outputStream.writeBoolean(main.existHeartBlock);
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
                    for (Block block : main.blocks) {
                        if (block.isDestroyed) {
                            continue;
                        }
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                    }

                    outputStream.writeObject(blockSerializables);

                    Platform.runLater(() -> {
                        new Score().showMessage("Game Saved", main); //Display "Game Saved" when the game is saved
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
        //State loadSave = new State();
        read(); //Read the saved file, assign the variables to the saved variables

        main.existHeartBlock = isExistHeartBlock;
        main.getBall().goldBall = isGoldStatus;
        main.currentLevel = level;
        main.currentScore = score;
        main.numberOfHearts = heart;
        destroyedBlockCount = destroyedBlockCount;

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

        main.getPaddle().paddleXPosition = xBreak;
        main.getPaddle().paddleYPosition = yBreak;
        main.getPaddle().paddleCenter = centerBreakX;
        main.currentTime = time;
        goldTime = goldTime;

        blocks.clear();
        main.bonusItems.clear();

        for (BlockSerializable ser : blocks) {
            int r = new Random().nextInt(200);
            main.blocks.add(new Block(ser.row, ser.column, main.blockColors[r % main.blockColors.length], ser.type));
        }

        try {
            loadFromSavedFile = true;
            main.start(main.getGameScreen().primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restartGame() { //Restart the game
        try {
            main.currentLevel = 0;
            main.numberOfHearts = 3;
            main.currentScore = 0;
            main.getBall().ballHorizontalSpeed = 1.000;
            destroyedBlockCount = 0;
            main.getBall().resetCollideFlags();
            main.getBall().goDownBall = true;

            main.getBall().goldBall = false;
            main.existHeartBlock = false;
            main.hitTime = 0;
            main.currentTime = 0;
            goldTime = 0;

            blocks.clear();
            main.bonusItems.clear();

            main.start(main.getGameScreen().primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkLoadFromSavedFile(){
        if (!loadFromSavedFile) { //If NOT loading from saved file
            if (main.currentLevel > 1 && main.currentLevel < 18) { //If the level is greater than 1 and less than 18
                main.getButtonControls().loadButton.setVisible(false); //Hide the load button
                main.getButtonControls().newGameButton.setVisible(false); //Hide the new game button
                //main.engine = new GameEngine(); //Initialize the game engine, to start the game
                main.getEngine().setOnAction(main); //Listen for events
                main.getEngine().setFps(120); //Set FPS
                main.getEngine().start(); //Start the game engine
            }

            main.getButtonControls().loadButton.setOnAction(main.getButtonControls().createLoadButtonHandler());
            main.getButtonControls().newGameButton.setOnAction(main.getButtonControls().createNewGameButtonHandler());

        } else { //But if IT IS loading from saved file
            //main.engine = new GameEngine(); //Initialize the game engine, to start the game
            main.getEngine().setOnAction(main); //Listen for events
            main.getEngine().setFps(120); //Set FPS
            main.getEngine().start(); //Start the game engine
            loadFromSavedFile = false; //Set loadFromSave to false, INDICATES that it is a new game
        }
    }
}
