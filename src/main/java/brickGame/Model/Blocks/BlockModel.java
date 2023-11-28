package brickGame.Model.Blocks;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

/**
 * The BlockModel class is responsible for creating the blocks and their properties.
 * It contains methods for handling the block attributes and behavior.
 * It also implements a serializable interface to allow the object to be saved to a file.
 *
 * @author Lua Chong En
 *
 */

public class BlockModel implements Serializable { //Methods include: Block, draw, checkHitToBlock, getPaddingTop, getPaddingH, getHeight, and getWidth
    private static BlockModel block = new BlockModel(-1, -1, Color.TRANSPARENT, 99);   //block is a static variable of type BlockModel

    public int row; //row of block
    public int column; //column of block

    public boolean isDestroyed = false; //isDestroyed is a boolean variable that is initialized to false

    private Color color; //color of block
    public int type; //type of block

    public int blockXCoordinate; //x coordinate of block
    public int blockYCoordinate; //y coordinate of block

    private int width = 100; //width of block
    private int height = 30; //height of block
    private int paddingTop = height*2; //padding top of block
    private int paddingH = 50; //padding horizontal of block
    public Rectangle rect; //rectangle object


    public static int NO_HIT = -1; //NO_HIT is a static variable of type int that is initialized to -1
    public static int HIT_RIGHT = 0; //HIT_RIGHT is a static variable of type int that is initialized to 0
    public static int HIT_BOTTOM = 1; //HIT_BOTTOM is a static variable of type int that is initialized to 1
    public static int HIT_LEFT = 2; //HIT_LEFT is a static variable of type int that is initialized to 2
    public static int HIT_TOP = 3; //HIT_TOP is a static variable of type int that is initialized to 3

    public static int BLOCK_NORMAL = 99; //BLOCK_NORMAL is a static variable of type int that is initialized to 99
    public static int BLOCK_CHOCOLATE = 100; //BLOCK_CHOCOLATE is a static variable of type int that is initialized to 100
    public static int BLOCK_STAR = 101; //BLOCK_STAR is a static variable of type int that is initialized to 101
    public static int BLOCK_HEART = 102; //BLOCK_HEART is a static variable of type int that is initialized to 102
    public static int BLOCK_SLIME = 103; //slime block
    public static int BLOCK_BOMB = 104; //explosion block
    public static int BLOCK_QUESTION = 105; //question block
    public static int BLOCK_TELEPORT = 106; //teleport block
    public static int BLOCK_SPEED = 107; //mirror block

    /**
     * The BlockModel method is the constructor for the BlockModel class.
     * @param row The row index of the block
     * @param column The column index of the block
     * @param color The color of the block
     * @param type The type of the block
     */

    public BlockModel(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        create();
    }

    /**
     * The create() method is responsible for creating the blocks.
     * It sets the position of the blocks, color, and type.
     */

    private void create() {
        blockXCoordinate = (column * width) + paddingH;
        blockYCoordinate = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(blockXCoordinate);
        rect.setY(blockYCoordinate);

        if (type == BLOCK_CHOCOLATE) {
            setImage("choco.jpg");
        } else if (type == BLOCK_HEART) {
            setImage("heart.jpg");
        } else if (type == BLOCK_STAR) {
            setImage("star.jpg");
        }  else if (type == BLOCK_SLIME) {
            setImage("slime.jpg");
        } else if (type == BLOCK_BOMB) {
            setImage("bomb.jpg");
        } else if (type == BLOCK_QUESTION) {
            setImage("question.png");
        } else if (type == BLOCK_TELEPORT){
            setImage("teleport.jpg");
        } else if (type == BLOCK_SPEED){
            setImage("speed.jpg");
        } else {
            rect.setFill(color);
        }
    }

    /**
     * The checkHitToBlock method is responsible for drawing the blocks.
     * @param xBall The x-coordinate of the ball.
     * @param yBall The y-coordinate of the ball.
     * @return The hit direction and NO_HIT if the block is not hit
     */

    public int checkHitToBlock(double xBall, double yBall) {
        if (isDestroyed) {
            return NO_HIT;
        }

        // Increase the sensitivity by adjusting the conditions
        if (xBall >= blockXCoordinate - 10 && xBall <= blockXCoordinate + width + 10 && yBall >= blockYCoordinate - 10 && yBall <= blockYCoordinate + height + 10) {
            return HIT_BOTTOM;
        }

        if (xBall >= blockXCoordinate - 10 && xBall <= blockXCoordinate + width + 10 && yBall >= blockYCoordinate - 10 && yBall <= blockYCoordinate + height + 10) {
            return HIT_TOP;
        }

        if (yBall >= blockYCoordinate - 10 && yBall <= blockYCoordinate + height + 10 && xBall >= blockXCoordinate - 10 && xBall <= blockXCoordinate + width + 10) {
            return HIT_RIGHT;
        }

        if (yBall >= blockYCoordinate - 10 && yBall <= blockYCoordinate + height + 10 && xBall >= blockXCoordinate - 10 && xBall <= blockXCoordinate + width + 10) {
            return HIT_LEFT;
        }

        return NO_HIT;
    }

    /**
     * The setImage method is responsible for setting the image of the blocks.
     * @param imagePath The path to the file of the image
     */

    public void setImage(String imagePath) {
        Image image = new Image(imagePath);
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);
    }

    /**
     * This getPaddingTop method is responsible for getting the padding top of the blocks.
     * @return The top padding value.
     */

    public static int getPaddingTop() {
        return block.paddingTop;
    }

    /**
     * The getPaddingH method is responsible for getting the padding horizontal of the blocks.
     * @return The horizontal padding value.
     */

    public static int getPaddingH() {
        return block.paddingH;
    }

    /**
     * The getHeight method is responsible for getting the height of the blocks.
     * @return The height value.
     */

    public static int getHeight() {
        return block.height;
    }

    /**
     * The getWidth method is responsible for getting the width of the blocks.
     * @return The width value.
     */

    public static int getWidth() {
        return block.width;
    }
}
