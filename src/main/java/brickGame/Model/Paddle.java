package brickGame.Model;

import brickGame.Main;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
    Main main;
    public int paddleWidth = 130; //Width of paddle
    public int paddleHeight = 30; //Height of paddle
    public double paddleXPosition = 0.0f; //Variable for the paddle that the user controls
    public double paddleCenter; //Center of paddle
    public double paddleYPosition = 640.0f; //y-coordinate of the top position of paddle
    public int paddleWidthHalf = paddleWidth / 2; //Half of the width of paddle
    public static int paddleLEFT = 1; //Direction of paddle
    public static int paddleRIGHT = 2; //Direction of paddle

    public Paddle(Main main){
        super(0, 0, 130, 30);
        this.main = main;
    }

    public void move(final int direction) { //Move paddle method
        new Thread(new Runnable() { //Thread runs in parallel with main thread, using the runnable interface
            @Override
            public void run() {
                int sleepTime = 4; //Delays the movement of the paddle because if not, the paddle will move too fast
                for (int i = 0; i < 30; i++) { //For loop to move the paddle, condition is 30 because the paddle will move 30 pixels
                    if (paddleXPosition == (main.windowWidth - paddleWidth) && direction == paddleRIGHT) { //If the paddle is at the right wall and the direction is right, prevents paddle from moving out of bounds
                        return; //Stop Moving
                    }
                    if (paddleXPosition == 0 && direction == paddleLEFT) { //If the paddle is at the left wall and the direction is left, prevents paddle from moving out of bounds
                        return; //Stop Moving
                    }
                    if (direction == paddleRIGHT) { //Updates the x-coordinate of the paddle to move it to the right
                        paddleXPosition++;
                    } else {
                        paddleXPosition--;
                    }
                    paddleCenter = paddleXPosition + paddleWidthHalf; //Position the paddle accurately
                    try { //Control the speed of the paddle, sleep allows the paddle to move smoothly
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();
    }
}
