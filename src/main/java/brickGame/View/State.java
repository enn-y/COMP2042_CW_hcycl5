package brickGame.View;

import brickGame.Controller.ButtonControls;
import brickGame.Main;
import brickGame.Model.*;
import brickGame.Model.Serializables.BlockSerializable;
import javafx.application.Platform;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class State { //Methods include: read

    Main main;
    Ball ball;
    Paddle paddle;
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
    public State(Main main, Ball ball, Paddle paddle) {
        this.main = main;
        this.ball = ball;
        this.paddle = paddle;
    }

    public void read() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(Main.savePath)));

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
                new File(main.savePathDir).mkdirs();
                File file = new File(main.savePath);
                ObjectOutputStream outputStream = null;
                try { //Try to save the game, save all the variables
                    ball = new Ball(main);
                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    outputStream.writeInt(main.currentLevel);
                    outputStream.writeInt(main.currentScore);
                    outputStream.writeInt(main.numberOfHearts);
                    outputStream.writeInt(destroyedBlockCount);

                    outputStream.writeDouble(ball.ballXCoordinate);
                    outputStream.writeDouble(ball.ballYCoordinate);
                    outputStream.writeDouble(paddle.paddleXPosition);
                    outputStream.writeDouble(paddle.paddleYPosition);
                    outputStream.writeDouble(paddle.paddleCenter);
                    outputStream.writeLong(main.currentTime);
                    outputStream.writeLong(goldTime);
                    outputStream.writeDouble(ball.ballHorizontalSpeed);

                    outputStream.writeBoolean(main.existHeartBlock);
                    outputStream.writeBoolean(main.goldBall);
                    outputStream.writeBoolean(ball.goDownBall);
                    outputStream.writeBoolean(ball.goRightBall);
                    outputStream.writeBoolean(ball.collideToPaddle);
                    outputStream.writeBoolean(ball.collideToPaddleAndMoveToRight);
                    outputStream.writeBoolean(ball.collideToRightWall);
                    outputStream.writeBoolean(ball.collideToLeftWall);
                    outputStream.writeBoolean(ball.collideToRightBlock);
                    outputStream.writeBoolean(ball.collideToBottomBlock);
                    outputStream.writeBoolean(ball.collideToLeftBlock);
                    outputStream.writeBoolean(ball.collideToTopBlock);

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
        main.goldBall = isGoldStatus;
        ball.goDownBall = goDownBall;
        ball.goRightBall = goRightBall;
        ball.collideToPaddle = collideToPaddle;
        ball.collideToPaddleAndMoveToRight = collideToPaddleAndMoveToRight;
        ball.collideToRightWall = collideToRightWall;
        ball.collideToLeftWall = collideToLeftWall;
        ball.collideToRightBlock = collideToRightBlock;
        ball.collideToBottomBlock = collideToBottomBlock;
        ball.collideToLeftBlock = collideToLeftBlock;
        ball.collideToTopBlock = collideToTopBlock;
        main.currentLevel = level;
        main.currentScore = score;
        main.numberOfHearts = heart;
        destroyedBlockCount = destroyedBlockCount;
        ball.ballXCoordinate = xBall;
        ball.ballYCoordinate = yBall;
        paddle.paddleXPosition = xBreak;
        paddle.paddleYPosition = yBreak;
        paddle.paddleCenter = centerBreakX;
        main.currentTime = time;
        goldTime = goldTime;
        ball.ballHorizontalSpeed = vX;

        blocks.clear();
        main.bonusItems.clear();

        for (BlockSerializable ser : blocks) {
            int r = new Random().nextInt(200);
            main.blocks.add(new Block(ser.row, ser.column, main.blockColors[r % main.blockColors.length], ser.type));
        }

        try {
            main.loadFromSavedFile = true;
            main.start(main.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restartGame() { //Restart the game
        ball = new Ball(main);
        try {
            main.currentLevel = 0;
            main.numberOfHearts = 3;
            main.currentScore = 0;
            ball.ballHorizontalSpeed = 1.000;
            destroyedBlockCount = 0;
            ball.resetCollideFlags();
            ball.goDownBall = true;

            main.goldBall = false;
            main.existHeartBlock = false;
            main.hitTime = 0;
            main.currentTime = 0;
            goldTime = 0;

            blocks.clear();
            main.bonusItems.clear();

            main.start(main.primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkLoadFromSavedFile(){
        if (!main.loadFromSavedFile) { //If NOT loading from saved file
            if (main.currentLevel > 1 && main.currentLevel < 18) { //If the level is greater than 1 and less than 18
                main.loadButton.setVisible(false); //Hide the load button
                main.newGameButton.setVisible(false); //Hide the new game button
                main.engine = new GameEngine(); //Initialize the game engine, to start the game
                main.getEngine().setOnAction(main); //Listen for events
                main.getEngine().setFps(120); //Set FPS
                main.getEngine().start(); //Start the game engine
            }

            main.buttonControls = new ButtonControls(main);

            main.loadButton.setOnAction(main.getButtonControls().createLoadButtonHandler());
            main.newGameButton.setOnAction(main.getButtonControls().createNewGameButtonHandler());

        } else { //But if IT IS loading from saved file
            main.engine = new GameEngine(); //Initialize the game engine, to start the game
            main.getEngine().setOnAction(main); //Listen for events
            main.getEngine().setFps(120); //Set FPS
            main.getEngine().start(); //Start the game engine
            main.loadFromSavedFile = false; //Set loadFromSave to false, INDICATES that it is a new game
        }
    }
}
