package brickGame;


import javafx.application.Platform;

public class GameEngine { //Methods include: setOnAction, setFps, Update, Initialize, PhysicsCalculation, start, stop, TimeStart, and OnAction

    private OnAction onAction;
    private int fps = 15;
    private Thread updateThread;
    private Thread physicsThread;
    public boolean isStopped = true;

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

    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }
}
