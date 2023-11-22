package brickGame.Model;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Block implements Serializable { //Methods include: Block, draw, checkHitToBlock, getPaddingTop, getPaddingH, getHeight, and getWidth
    private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);

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
    public static int BLOCK_EXPLOSION = 104; //explosion block
    public static int BLOCK_QUESTION = 105; //question block

    public Block(int row, int column, Color color, int type) {
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
            Image image = new Image("choco.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_HEART) {
            Image image = new Image("heart.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_STAR) {
            Image image = new Image("star.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        }  else if (type == BLOCK_SLIME) {
            Image image = new Image("slime.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_EXPLOSION) {
            Image image = new Image("explosion.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_QUESTION) {
            Image image = new Image("images.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        }
        else {
            rect.setFill(color);
        }
    }

    public int checkHitToBlock(double xBall, double yBall) {
        if (isDestroyed) {
            return NO_HIT;
        }

        // Increase the sensitivity by adjusting the conditions
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
