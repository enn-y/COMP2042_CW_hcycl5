package brickGame.Model;

import brickGame.Main;
import brickGame.View.State;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    Main main;

    public double ballXCoordinate; //x-coordinate of ball
    public double ballYCoordinate; //y-coordinate of ball
    public double ballHorizontalSpeed = 1.000; //Horizontal velocity of ball
    public double ballVerticalSpeed = 1.000; //Vertical velocity of ball
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

    public Ball(Main main){
        this.main = main;
    }

    public void setPhysicsToBall() { //The behavior of the ball
        //v = ((time - hitTime) / 1000.000) + 1.000;

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
            //vX = 1.000;
            resetCollideFlags();
            goDownBall = true; //Ball moves downwards
            return;
        }
        if (ballYCoordinate > main.windowHeight - ballRadius*1.5) { //If the ball collides with bottom wall
            goDownBall = false; //Ball moves upwards
            if (!main.goldBall) { //If the ball is NOT gold
                //TODO gameover
                main.numberOfHearts--; //Decrement the heart
                new Score().show(main.windowWidth / 2, main.windowHeight / 2, -1, main);
                checkGameOver();
            }
            //return;
        }

        Paddle paddle = main.getPaddle();
        if (ballYCoordinate > paddle.paddleYPosition - main.getBall().ballRadius*1.5) {
            //System.out.println("Collide1");
            if (ballXCoordinate >= paddle.paddleXPosition && ballXCoordinate <= paddle.paddleXPosition + paddle.paddleWidth) {
                main.hitTime = main.currentTime;
                resetCollideFlags();
                collideToPaddle = true;
                goDownBall = false;

                double relation = (ballXCoordinate - paddle.paddleCenter) / (paddle.paddleWidth / 2);

                if (Math.abs(relation) <= 0.3) {
                    //vX = 0;
                    ballHorizontalSpeed = Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    ballHorizontalSpeed = (Math.abs(relation) * 1.5) + (main.currentLevel / 3.500);
                    //System.out.println("vX " + vX);
                } else {
                    ballHorizontalSpeed = (Math.abs(relation) * 2) + (main.currentLevel / 3.500);
                    //System.out.println("vX " + vX);
                }

                if (ballXCoordinate - paddle.paddleCenter > 0) {
                    collideToPaddleAndMoveToRight = true;
                } else {
                    collideToPaddleAndMoveToRight = false;
                }
                //System.out.println("Collide2");
            }
        }

        if (ballXCoordinate > main.windowWidth - ballRadius*1.5) {
            resetCollideFlags();
            //vX = 1.000;
            collideToRightWall = true;
        }

        if (ballXCoordinate < 0 + ballRadius*1.5) {
            resetCollideFlags();
            //vX = 1.000;
            collideToLeftWall = true;
        }

        if (collideToPaddle) {
            if (collideToPaddleAndMoveToRight) {
                goRightBall = true;
            } else {
                goRightBall = false;
            }
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

    public void checkGameOver() {
        if (!main.goldBall && main.numberOfHearts == 0) {
            new Score().showGameOver(main);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            main.engine.stop();
        }
    }

    public void resetCollideFlags() { //Reset all collision flags to FALSE so game can identify new collision events in the next game loop
        collideToPaddle = false;
        collideToPaddleAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }

    public void setBallXCoordinate(double ballXCoordinate) {
        this.ballXCoordinate = ballXCoordinate;
    }

    public double getBallXCoordinate() {
        return ballXCoordinate;
    }

    public void setBallYCoordinate(double ballYCoordinate) {
        this.ballYCoordinate = ballYCoordinate;
    }

    public double getBallYCoordinate() {
        return ballYCoordinate;
    }

    public void setBallRadius(int ballRadius) {
        this.ballRadius = ballRadius;
    }

    public int getBallRadius() {
        return ballRadius;
    }
}
