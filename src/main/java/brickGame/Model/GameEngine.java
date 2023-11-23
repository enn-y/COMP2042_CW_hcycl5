package brickGame.Model;


import brickGame.Model.Blocks.Block;
import brickGame.Model.Interface.OnAction;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameEngine { //Methods include: setOnAction, setFps, Update, Initialize, PhysicsCalculation, start, stop, TimeStart, and OnAction

    private OnAction onAction;
    private int fps = 15;
    private Thread updateThread;
    private Thread physicsThread;
    public boolean isStopped = true;
    public ArrayList<Block> blocks = new ArrayList<Block>(); //ArrayList to store the blocks
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

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * @param fps set fps and we convert it to millisecond
     */
    public void setFps(int fps) {
        this.fps = (int) 1000 / fps;
    }

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

    private void Initialize() {
        onAction.onInit();
    }

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

    public void start() {
        time = 0;
        Initialize();
        Update();
        PhysicsCalculation();
        TimeStart();
        isStopped = false;
    }

    public void stop() {
        if (!isStopped) {
            isStopped = true;
            updateThread.stop();
            physicsThread.stop();
            timeThread.stop();
        }
    }

    private long time = 0;

    private Thread timeThread;

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
}
