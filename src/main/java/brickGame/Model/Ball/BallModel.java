package brickGame.Model.Ball;

import brickGame.Main;
import brickGame.Model.Interface.BallPosition;
import brickGame.Model.ScoreManager;
import javafx.scene.shape.Circle;

/**
 * The BallModel class is responsible or the representation of the model for the ball.
 * It contains the ball's position, velocity, radius, and the status of the ball.
 * It extends the circle class because it inherits the properties of a circle.
 *
 * @author Lua Chong En
 *
 */

public class BallModel extends Circle implements BallPosition {
    Main main;

    public double ballXCoordinate;
    public double ballYCoordinate;
    public double ballHorizontalSpeed = 1.250;
    public double ballVerticalSpeed = 1.250;
    public int ballRadius = 10;
    public boolean goDownBall;
    public boolean goRightBall;
    public boolean collideToPaddle = false;
    public boolean collideToPaddleAndMoveToRight = true;
    public boolean collideToRightWall = false;
    public boolean collideToLeftWall = false;
    public boolean collideToRightBlock = false;
    public boolean collideToBottomBlock = false;
    public boolean collideToLeftBlock = false;
    public boolean collideToTopBlock = false;
    public long goldTime = 0;
    public boolean goldBall = false;

    /**
     * Constructor for BallModel class.
     * @param main The Main class instance to access the components of the game
     */

    public BallModel(Main main){
        this.main = main;
    }

    /**
     * The setPhysicsToBall method sets the behavior of the ball.
     * It is inclusive of movement and collision detection.
     */

    public void setPhysicsToBall() {
        if (goDownBall) {
            ballYCoordinate += ballVerticalSpeed;
        } else {
            ballYCoordinate -= ballVerticalSpeed;
        }

        if (goRightBall) {
            ballXCoordinate += ballHorizontalSpeed;
        } else {
            ballXCoordinate -= ballHorizontalSpeed;
        }

        if (ballYCoordinate < 0 + ballRadius*1.5) {
            resetCollideFlags();
            goDownBall = true;
            return;
        }

        if (ballYCoordinate > main.getGameScreen().windowHeight - ballRadius*1.5) {
            goDownBall = false;
            if (!goldBall) {
                main.getPlayer().numberOfHearts--;
                new ScoreManager().show(main.getGameScreen().windowWidth / 2, main.getGameScreen().windowHeight / 2, -1, main);
                main.getEngine().checkGameOver();
            }
        }

        if (ballYCoordinate > main.getPlayer().paddleYPosition - main.getBall().ballRadius*1.5) {
            if (ballXCoordinate >= main.getPlayer().paddleXPosition && ballXCoordinate <= main.getPlayer().paddleXPosition + main.getPlayer().paddleWidth) {
                main.getPlayer().hitTime = main.getPlayer().currentTime;
                resetCollideFlags();
                collideToPaddle = true;
                goDownBall = false;

                double relation = (ballXCoordinate - main.getPlayer().paddleCenter) / (main.getPlayer().paddleWidth / 2);

                if (Math.abs(relation) <= 0.3) {
                    ballHorizontalSpeed = Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    ballHorizontalSpeed = (Math.abs(relation) * 1.5) + (main.currentLevel / 3.500);
                } else {
                    ballHorizontalSpeed = (Math.abs(relation) * 2) + (main.currentLevel / 3.500);
                }

                collideToPaddleAndMoveToRight = ballXCoordinate - main.getPlayer().paddleCenter > 0;
            }
        }

        if (ballXCoordinate > main.getGameScreen().windowWidth - ballRadius*1.5) {
            resetCollideFlags();
            collideToRightWall = true;
        }

        if (ballXCoordinate < 0 + ballRadius*1.5) {
            resetCollideFlags();
            collideToLeftWall = true;
        }

        if (collideToPaddle) {
            goRightBall = collideToPaddleAndMoveToRight;
        }

        if (collideToRightWall) {
            goRightBall = false;
        }
        if (collideToLeftWall) {
            goRightBall = true;
        }

        if (collideToRightBlock) {
            goRightBall = true;
        }
        if (collideToLeftBlock) {
            goRightBall = true;
        }
        if (collideToTopBlock) {
            goDownBall = false;
        }
        if (collideToBottomBlock) {
            goDownBall = true;
        }
    }

    /**
     * The resetCollideFlags method resets all collision flags to FALSE.
     * This is so the game can identify new collision events in the next game loop.
     */

    public void resetCollideFlags() {
        collideToPaddle = false;
        collideToPaddleAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }

    /**
     * The setBallXCoordinate method sets the x-coordinate of the ball.
     * @param ballXCoordinate The x-coordinate of the ball
     */

    public void setBallXCoordinate(double ballXCoordinate) {
        this.ballXCoordinate = ballXCoordinate;
    }

    /**
     * The getBallXCoordinate method returns the x-coordinate of the ball.
     * @return The x-coordinate of the ball
     */

    public double getBallXCoordinate() {
        return ballXCoordinate;
    }

    /**
     * The setBallYCoordinate method sets the y-coordinate of the ball.
     * @param ballYCoordinate The y-coordinate of the ball
     */

    public void setBallYCoordinate(double ballYCoordinate) {
        this.ballYCoordinate = ballYCoordinate;
    }

    /**
     * The getBallYCoordinate method returns the y-coordinate of the ball.
     * @return The y-coordinate of the ball
     */

    public double getBallYCoordinate() {
        return ballYCoordinate;
    }
}
