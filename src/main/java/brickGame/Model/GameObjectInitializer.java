package brickGame.Model;

import brickGame.Controller.KeyboardControls;
import brickGame.Main;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.Random;

public class GameObjectInitializer {
    Main main;
    Ball ball;
    Paddle paddle;
    KeyboardControls keyboardControls;

    public GameObjectInitializer(Main main, Ball ball, Paddle paddle, KeyboardControls keyboardControls) {
        this.main = main;
        this.ball = ball;
        this.paddle = paddle;
        this.keyboardControls = keyboardControls;
    }

    public void initializeBall() { //Initialize the ball
        Random random = new Random(); //Random number generator
        ball.setBallXCoordinate(random.nextInt(main.windowWidth) + 1); //Random x-coordinate of the ball
        ball.setBallYCoordinate(random.nextInt(main.windowHeight - 200) + ((main.currentLevel + 1) * Block.getHeight()) + 15); //Random y-coordinate of the ball
        ball.setCenterX(ball.getBallXCoordinate()); //Set the center x-coordinate of the ball
        ball.setCenterY(ball.getBallYCoordinate());
        ball.setRadius(ball.ballRadius); //Set the radius of the ball
        ball.setFill(new ImagePattern(new Image("ball.png"))); //Using ball.png as the image of the ball
    }

    public void initializePaddle() { //Initialize the paddle
        paddle.setX(paddle.paddleXPosition); //Set the x-coordinate of the paddle
        paddle.setY(paddle.paddleYPosition); //Set the y-coordinate of the paddle
        paddle.setFill(new ImagePattern(new Image("block.jpg"))); //Using ball.png as the image of the ball
    }

    public void initializeKeyboardController() {
        keyboardControls = new KeyboardControls(main, paddle);
    }

    public void initializeBlocks() { //Initialize the blocks
        for (int row = 0; row < 4; row++) { //For each row
            for (int column = 0; column < main.currentLevel+1; column++) { //For each column where the condition is the level + 1 which means that the number of columns increases by 1 every level
                int r = new Random().nextInt(500); //Random number generator
                if (r % 5 == 0) { //If the remainder is 0
                    continue; //Block will not be created
                }
                int type;
                if (r % 10 == 1) { //If the remainder is 1
                    type = Block.BLOCK_CHOCOLATE; //Create a choco block
                } else if (r % 10 == 2) { //BUT IF the remainder is 2
                    if (!main.existHeartBlock) { //AND IF there is NO heart block
                        type = Block.BLOCK_HEART; //Create a heart block
                        main.existHeartBlock = true; //Set isExistHeartBlock to true, INDICATES that there is a heart block
                    } else { //BUT IF there IS a heart block
                        type = Block.BLOCK_NORMAL; //Create a normal block
                    }
                } else if (r % 10 == 3) { //BUT IF the remainder is 3
                    type = Block.BLOCK_STAR; //Create a star block
                } else if (r % 10 == 4) { //BUT IF the remainder is 4
                    type = Block.BLOCK_SLIME;
                } else if (r % 10 == 6) { //BUT IF the remainder is 5
                    type = Block.BLOCK_QUESTION;
                } else if (r % 10 == 5) { //BUT IF the remainder is 6
                    type = Block.BLOCK_EXPLOSION;
                } else { //BUT IF the remainder is NOT 1, 2, or 3 THEN create a normal block
                    type = Block.BLOCK_NORMAL; //Create a normal block
                }
                main.blocks.add(new Block(column, row, main.blockColors[r % (main.blockColors.length)], type)); //Add the block to the ArrayList
                //System.out.println("colors " + r % (colors.length));
            }
        }
    }
}
