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
    Main main; //Main class instance
    private int level; //Level of the game

    public double ballXCoordinate; //x-coordinate of ball
    public double ballYCoordinate; //y-coordinate of ball
    public double ballHorizontalSpeed = 1.250; //Horizontal velocity of ball
    public double ballVerticalSpeed = 1.250; //Vertical velocity of ball
    public int ballRadius = 10; //Radius of ball/Size of ball
    public boolean goDownBall; //Status for ball moving downwards
    public boolean goRightBall; //Status for ball moving to the right
    public boolean collideToPaddle = false; //Status for ball colliding with the paddle, set to FALSE
    public boolean collideToPaddleAndMoveToRight = true; //Status for ball colliding with the paddle and moving to the right
    public boolean collideToRightWall = false; //Status for ball colliding with the right wall, set to FALSE
    public boolean collideToLeftWall = false; //Status for ball colliding with the left wall, set to FALSE
    public boolean collideToRightBlock = false; //Status for ball colliding to the right side of the block, set to FALSE
    public boolean collideToBottomBlock = false; //Status for ball colliding to the bottom side of the block, set to FALSE
    public boolean collideToLeftBlock = false; //Status for ball colliding to the left side of the block, set to FALSE
    public boolean collideToTopBlock = false; //Status for ball colliding to the top side of the block, set to FALSE
    public long goldTime = 0; //Time of gold ball, initialized at 0, used to check if gold ball is still active
    public boolean goldBall = false; //Status of gold ball

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

    public void setPhysicsToBall() { //The behavior of the ball
        if (goDownBall) { //If the ball is moving downwards
            ballYCoordinate += ballVerticalSpeed; //Increment the vertical velocity of the ball
        } else {
            ballYCoordinate -= ballVerticalSpeed; //Decrement the vertical velocity of the ball
        }

        if (goRightBall) { //If the ball is moving to the right
            ballXCoordinate += ballHorizontalSpeed; //Increment the horizontal velocity of the ball
        } else {
            ballXCoordinate -= ballHorizontalSpeed; //Decrement the horizontal velocity of the ball
        }

        if (ballYCoordinate < 0 + ballRadius*1.5) { //If the ball collides with top wall
            resetCollideFlags(); //Reset all collision flags to FALSE
            goDownBall = true; //Ball moves downwards
            return; //Exit the method
        }

        if (ballYCoordinate > main.getGameScreen().windowHeight - ballRadius*1.5) { //If the ball collides with bottom wall
            goDownBall = false; //Ball moves upwards
            if (!goldBall) { //If the ball is NOT gold
                main.getPlayer().numberOfHearts--; //Decrement the heart
                new ScoreManager().show(main.getGameScreen().windowWidth / 2, main.getGameScreen().windowHeight / 2, -1, main);
                main.getEngine().checkGameOver();
            }
        }

        level = main.currentLevel;

        if (ballYCoordinate > main.getPlayer().paddleYPosition - main.getBall().ballRadius*1.5) { //If the ball collides with the paddle
            if (ballXCoordinate >= main.getPlayer().paddleXPosition && ballXCoordinate <= main.getPlayer().paddleXPosition + main.getPlayer().paddleWidth) {
                main.getPlayer().hitTime = main.getPlayer().currentTime; //Set the hit time to the current time
                resetCollideFlags(); //Reset all collision flags to FALSE
                collideToPaddle = true; //Set the collideToPaddle flag to TRUE
                goDownBall = false; //Ball moves upwards

                double relation = (ballXCoordinate - main.getPlayer().paddleCenter) / (main.getPlayer().paddleWidth / 2); //Relation of the ball to the paddle

                if (Math.abs(relation) <= 0.3) { //If the relation is less than or equal to 0.3
                    ballHorizontalSpeed = Math.abs(relation); //Set the horizontal velocity of the ball to the absolute value of the relation
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) { //If the relation is greater than 0.3 and less than or equal to 0.7
                    ballHorizontalSpeed = (Math.abs(relation) * 1.5) + (level / 3.500); //Set the horizontal velocity of the ball to the absolute value of the relation multiplied by 1.5 and added to the current level divided by 3.500
                } else { //If the relation is greater than 0.7
                    ballHorizontalSpeed = (Math.abs(relation) * 2) + (level / 3.500); // Set the horizontal velocity of the ball to the absolute value of the relation multiplied by 2 and added to the current level divided by 3.500
                }

                collideToPaddleAndMoveToRight = ballXCoordinate - main.getPlayer().paddleCenter > 0; //Set the collideToPaddleAndMoveToRight flag to TRUE if the ball is moving to the right
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

        //Wall Collide
        if (collideToRightWall) {
            goRightBall = false;
        }
        if (collideToLeftWall) {
            goRightBall = true;
        }

        //Block Collide
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

    public void resetCollideFlags() { //Reset all collision flags to FALSE so game can identify new collision events in the next game loop
        collideToPaddle = false; //Set to FALSE
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
