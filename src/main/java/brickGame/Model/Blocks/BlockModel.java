package brickGame.Model.Blocks;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class BlockModel implements Serializable { //Methods include: Block, draw, checkHitToBlock, getPaddingTop, getPaddingH, getHeight, and getWidth
    private static BlockModel block = new BlockModel(-1, -1, Color.TRANSPARENT, 99);

    public int row;
    public int column;

    public boolean isDestroyed = false;

    private Color color;
    public int type;

    public int blockXCoordinate; //x coordinate of block
    public int blockYCoordinate; //y coordinate of block

    private int width = 100;
    private int height = 30;
    private int paddingTop = height*2;
    private int paddingH = 50;
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
    public static int BLOCK_SLIME = 103; //slime block
    public static int BLOCK_BOMB = 104; //explosion block
    public static int BLOCK_QUESTION = 105; //question block
    public static int BLOCK_TELEPORT = 106; //teleport block
    public static int BLOCK_SPEED = 107; //mirror block

    public BlockModel(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        create();
    }

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

    public void setImage(String imagePath) {
        Image image = new Image(imagePath);
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);
    }

    public static int getPaddingTop() {
        return block.paddingTop;
    }

    public static int getPaddingH() {
        return block.paddingH;
    }

    public static int getHeight() {
        return block.height;
    }

    public static int getWidth() {
        return block.width;
    }
}
