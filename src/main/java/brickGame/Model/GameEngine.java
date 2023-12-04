package brickGame.Model;


import brickGame.Main;
import brickGame.Model.Blocks.*;
import brickGame.Model.Interface.OnAction;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

/**
 * The GameEngine class represents the game engine.
 * It is used to manage the game. It includes the methods to set the fps, update, initialize, and calculate physics.
 * It also includes the methods to start, stop, and pause the game.
 *
 * @author Lua Chong En
 *
 */

public class GameEngine implements OnAction{ //Methods include: setOnAction, setFps, Update, Initialize, PhysicsCalculation, start, stop, TimeStart, and OnAction
    Main main; //Main class instance

    private OnAction onAction; //Interface to handle the actions
    private int fps = 15; //Frames per second
    private Thread updateThread; //Thread to update the game
    private Thread physicsThread; //Thread to update the physics
    public boolean isStopped = true; //Boolean to check if the game is stopped
    public ArrayList<BlockModel> blocks = new ArrayList<BlockModel>(); //ArrayList to store the blocks
    public ArrayList<Bonus> bonusItems = new ArrayList<Bonus>(); //ArrayList to store the bonus items
    public Color[] blockColors = new Color[]{ //Array of colors for the blocks
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
    private long time = 0; //Time

    private Thread timeThread; //Thread to update the time

    /**
     * Constructor initializes the game engine.
     * @param main The Main instance to access the components of the game.
     */

    public GameEngine(Main main){
        this.main = main;
    }

    /**
     * The setOnAction method is used to set the action.
     * @param onAction The action to be set.
     */

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * The setFps method is used to set the fps.
     * @param fps set fps and we convert it to millisecond
     */
    public void setFps(int fps) {
        this.fps = (int) 1000 / fps;
    }

    /**
     * The Update method is used to update the game.
     * It is synchronized to prevent multiple threads from accessing the same object at the same time.
     */

    private synchronized void Update() {
        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!updateThread.isInterrupted()) {
                    try {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                onAction.onUpdate();
                            }
                        });
                        Thread.sleep(fps);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updateThread.start();
    }

    /**
     * The Initialize method is used to initialize the game.
     * It calls the onInit method when the game is initialized.
     */

    private void Initialize() {
        onAction.onInit();
    }

    /**
     * The PhysicsCalculation method is used to calculate the physics.
     * It is synchronized to prevent multiple threads from accessing the same object at the same time.
     */

    private synchronized void PhysicsCalculation() {
        physicsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!physicsThread.isInterrupted()) {
                    try {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                onAction.onPhysicsUpdate();
                            }
                        });
                        Thread.sleep(fps);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        physicsThread.start();
    }

    /**
     * The start method is used to start the game.
     * It calls the Initialize, Update, and PhysicsCalculation methods.
     * And sets the time to 0 and isStopped parameter to false.
     */

    public void start() {
        time = 0;
        Initialize();
        Update();
        PhysicsCalculation();
        TimeStart();
        isStopped = false;
    }

    /**
     * The stop method is used to stop the game.
     * It stops the updateThread, physicsThread, and timeThread.
     * And sets the isStopped parameter to true.
     */

    public void stop() {
        if (!isStopped) {
            isStopped = true;
            updateThread.stop();
            physicsThread.stop();
            timeThread.stop();
        }
    }

    /**
     * The TimeStart method is used to start the time.
     * It is synchronized to prevent multiple threads from accessing the same object at the same time.
     * It calls the onTime method when the time is updated.
     */

    private void TimeStart() {
        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        time++;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                onAction.onTime(time);
                            }
                        });
                        Thread.sleep(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timeThread.start();
    }

    /**
     * The pause method is used to pause the game.
     * It is synchronized to prevent multiple threads from accessing the same object at the same time.
     * It calls the onTime method when the time is updated.
     */

    public void pause(){
        Platform.runLater(() -> {
            if (!isStopped) {
                isStopped = true;
                updateThread.suspend();
                physicsThread.suspend();
                timeThread.suspend();
            } else {
                isStopped = false;
                updateThread.resume();
                physicsThread.resume();
                timeThread.resume();
            }
        });
    }

    /**
     * The createBlock method is used to create the blocks.
     * It is synchronized to prevent multiple threads from accessing the same object at the same time.
     * It sets the visibility of the blocks to false and isDestroyed parameter to true.
     */

    public void createBlock() {
        for (final BlockModel block : main.getEngine().blocks) {
            int hitCode = block.checkHitToBlock(main.getBall().ballXCoordinate, main.getBall().ballYCoordinate);
            if (hitCode != BlockModel.NO_HIT) {
                main.currentScore += 1;

                main.getScore().show(block.blockXCoordinate, block.blockYCoordinate, 1, main);

                block.rect.setVisible(false);
                block.isDestroyed = true;
                main.getPlayer().destroyedBlockCount++;
                //System.out.println("size is " + blocks.size());
                main.getBall().resetCollideFlags();

                if (block.type == BlockModel.BLOCK_CHOCOLATE) {
                    ChocolateBlock chocolateBlock = new ChocolateBlock(block.row, block.column, main);
                    chocolateBlock.blockType();
                }

                if (block.type == BlockModel.BLOCK_STAR && !main.getBall().goldBall) {
                    StarBlock starBlock = new StarBlock(block.row, block.column, main);
                    starBlock.blockType();
                }

                if (block.type == BlockModel.BLOCK_HEART) {
                    HeartBlock heartBlock = new HeartBlock(block.row, block.column, main);
                    heartBlock.blockType();
                }

                if (block.type == BlockModel.BLOCK_SLIME) {
                    SlimeBlock slimeBlock = new SlimeBlock(block.row, block.column, main);
                    slimeBlock.blockType();
                }

                if(block.type == BlockModel.BLOCK_QUESTION){
                    QuestionBlock questionBlock = new QuestionBlock(block.row, block.column, main);
                    questionBlock.blockType();
                }

                if(block.type == BlockModel.BLOCK_BOMB){
                    BombBlock bombBlock = new BombBlock(block.row, block.column, main);
                    bombBlock.blockType();
                }

                if(block.type == BlockModel.BLOCK_TELEPORT){
                    TeleportBlock teleportBlock = new TeleportBlock(block.row, block.column, main);
                    teleportBlock.blockType();
                }

                if(block.type == BlockModel.BLOCK_SPEED){
                    SpeedBlock speedBlock = new SpeedBlock(block.row, block.column, main);
                    speedBlock.blockType();
                }

                if (hitCode == BlockModel.HIT_RIGHT) {
                    main.getBall().collideToRightBlock = true;
                } else if (hitCode == BlockModel.HIT_BOTTOM) {
                    main.getBall().collideToBottomBlock = true;
                } else if (hitCode == BlockModel.HIT_LEFT) {
                    main.getBall().collideToLeftBlock = true;
                } else if (hitCode == BlockModel.HIT_TOP) {
                    main.getBall().collideToTopBlock = true;
                }
            }
            //TODO hit to break and some work here....
            //System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
        }
    }

    @Override
    public void onInit() {
    }

    public void onPhysicsUpdate() { //Updates game physics and logic during each frame of the game
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                main.getPlayer().blockDestroyedCount();
                main.getBall().setPhysicsToBall();

                if (main.getPlayer().currentTime - main.getBall().goldTime > 5000) { //Gold Ball
                    main.getBall().setFill(new ImagePattern(new Image("ball.png")));
                    main.getGameScreen().root.getStyleClass().remove("goldRoot");
                    main.getBall().goldBall = false;
                }

                for (Bonus choco : main.getEngine().bonusItems) { //Bonus Items
                    if (choco.y > main.getGameScreen().windowHeight || choco.taken) {
                        continue;
                    }
                    if (choco.y >= main.getPlayer().paddleYPosition && choco.y <= main.getPlayer().paddleYPosition + main.getPlayer().paddleHeight && choco.x >= main.getPlayer().paddleXPosition && choco.x <= main.getPlayer().paddleXPosition + main.getPlayer().paddleWidth) {
                        System.out.println("You Got it and +3 score for you");
                        choco.chocolateBlock.setVisible(false);
                        choco.taken = true;
                        main.currentScore += 3;
                        main.getScore().show(choco.x, choco.y, 3, main);
                    }
                    choco.y += ((main.getPlayer().currentTime - choco.timeCreated) / 1000.000) + 1.000;
                }
            }
        });
    }

    public void onUpdate() { //Update the game
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                main.getGameScreen().scoreLabel.setText("Score: " + main.currentScore);
                main.getGameScreen().heartLabel.setText("Heart : " + main.getPlayer().numberOfHearts);

                main.getPlayer().setX(main.getPlayer().paddleXPosition);
                main.getPlayer().setY(main.getPlayer().paddleYPosition);
                main.getBall().setCenterX(main.getBall().ballXCoordinate);
                main.getBall().setCenterY(main.getBall().ballYCoordinate);

                for (Bonus choco : main.getEngine().bonusItems) { //Bonus Items
                    choco.chocolateBlock.setY(choco.y); //Set the y-coordinate of the bonus item
                }
            }
        });

        if (main.getBall().ballYCoordinate >= BlockModel.getPaddingTop() && main.getBall().ballYCoordinate <= (BlockModel.getHeight() * (main.currentLevel + 1)) + BlockModel.getPaddingTop()) {
            main.getEngine().createBlock(); //Create the blocks
        }
    }

    @Override
    public void onTime(long time) { //Update the time
        main.getPlayer().currentTime = time;
    }

    public void checkWin(){
        if(main.currentLevel > 1){
            main.getScore().showMessage("Level Up :)", main); //If the level is greater than 1, then display "Level Up :)", inherited from Score.java
        }
        if (main.currentLevel == 10) {
            main.getScore().showGameWin(main); //If level is 10, then display "You Win :)", inherited from Score.java
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop();
        }
    }

    /**
     * The checkGameOver method checks if the game is over.
     * If the game is over, the game over screen is displayed and game is stopped.
     */

    public void checkGameOver() {
        if (!main.getBall().goldBall && main.getPlayer().numberOfHearts == 0) {
            main.getScore().showGameOver(main);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            main.getEngine().stop();
        }
    }
}
