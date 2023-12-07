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

public class BlockModel implements Serializable {
    public static BlockModel block = new BlockModel(-1, -1, Color.TRANSPARENT, 99);
    public int row;
    public int column;
    public boolean isDestroyed = false;
    private Color color;
    public int type;
    public int blockXCoordinate;
    public int blockYCoordinate;
    public int width = 100;
    public int height = 30;
    public int paddingTop = height*2;
    public int paddingH = 50;
    public Rectangle rect;
    public static int NO_HIT = -1;
    public static int HIT_RIGHT = 0;
    public static int HIT_BOTTOM = 1;
    public static int HIT_LEFT = 2;
    public static int HIT_TOP = 3;
    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCOLATE = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;
    public static int BLOCK_SLIME = 103;
    public static int BLOCK_BOMB = 104;
    public static int BLOCK_QUESTION = 105;
    public static int BLOCK_TELEPORT = 106;
    public static int BLOCK_SPEED = 107;

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
     * It also sets the image of the blocks.
     */

    private void create() {
        int padding = 2;
        blockXCoordinate = (column * (width+padding)) + paddingH;
        blockYCoordinate = (row * (height+padding)) + paddingTop;

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

        if (xBall >= blockXCoordinate - 15 && xBall <= blockXCoordinate + width + 15 && yBall >= blockYCoordinate - 15 && yBall <= blockYCoordinate + height + 15) {
            return HIT_BOTTOM;
        }

        if (xBall >= blockXCoordinate - 15 && xBall <= blockXCoordinate + width + 15 && yBall >= blockYCoordinate - 15 && yBall <= blockYCoordinate + height + 15) {
            return HIT_TOP;
        }

        if (yBall >= blockYCoordinate - 15 && yBall <= blockYCoordinate + height + 15 && xBall >= blockXCoordinate - 15 && xBall <= blockXCoordinate + width + 15) {
            return HIT_RIGHT;
        }

        if (yBall >= blockYCoordinate - 15 && yBall <= blockYCoordinate + height + 15 && xBall >= blockXCoordinate - 15 && xBall <= blockXCoordinate + width + 15) {
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
