package brickGame.Model.Blocks;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

/**
 * The Bonus class is responsible for creating the bonus item.
 * The bonus item is created when a block is destroyed.
 *
 * @author Lua Chong En
 *
 */

public class Bonus {
    public Rectangle chocolateBlock;

    public double x;
    public double y;
    public long timeCreated;
    public boolean taken = false;

    /**
     * Constructor initializes the bonus item.
     *
     * @param row The row of the block
     * @param column The column of the block
     */

    public Bonus(int row, int column) {
        x = (column * (BlockModel.getWidth())) + BlockModel.getPaddingH() + (BlockModel.getWidth() / 2) - 15;
        y = (row * (BlockModel.getHeight())) + BlockModel.getPaddingTop() + (BlockModel.getHeight() / 2) - 15;

        create();
    }

    /**
     * The create method is responsible for creating the bonus item.
     * The bonus item is created when a block is destroyed.
     * Sets the properties and image of the bonus item.
     * The bonus item is randomly chosen from bonus1.png and bonus2.png.
     */

    private void create() {
        chocolateBlock = new Rectangle();
        chocolateBlock.setWidth(30);
        chocolateBlock.setHeight(30);
        chocolateBlock.setX(x);
        chocolateBlock.setY(y);

        String imageFilePath;
        int random = new Random().nextInt(2);
        imageFilePath = (random == 0) ? "bonus1.png" : "bonus2.png";

        chocolateBlock.setFill(new ImagePattern(new Image(imageFilePath)));
    }
}
