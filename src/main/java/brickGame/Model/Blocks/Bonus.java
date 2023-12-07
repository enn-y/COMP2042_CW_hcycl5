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

public class Bonus { //Methods include: Bonus and draw, indicates that instances of this class can be serialized, making them suitable for saving or sending data between Java applications.
    public Rectangle chocolateBlock; //chocolate block, only chocolate block can create the bonus item

    public double x; //x coordinate of bonus item
    public double y; //y coordinate of bonus item
    public long timeCreated; //time created which means time when bonus item is created
    public boolean taken = false; //bonus item is taken by the paddle or not

    /**
     * Constructor initializes the bonus item.
     *
     * @param row The row of the block
     * @param column The column of the block
     */

    public Bonus(int row, int column) { //constructor
        x = (column * (BlockModel.getWidth())) + BlockModel.getPaddingH() + (BlockModel.getWidth() / 2) - 15; //set x coordinate of bonus item
        y = (row * (BlockModel.getHeight())) + BlockModel.getPaddingTop() + (BlockModel.getHeight() / 2) - 15; //set y coordinate of bonus item

        create();
    }

    /**
     * The create method is responsible for creating the bonus item.
     * The bonus item is created when a block is destroyed.
     * Sets the properties and image of the bonus item.
     * The bonus item is randomly chosen from bonus1.png and bonus2.png.
     */

    private void create() {
        chocolateBlock = new Rectangle(); //create chocolate block
        chocolateBlock.setWidth(30); //set width of chocolate block
        chocolateBlock.setHeight(30); //set height of chocolate block
        chocolateBlock.setX(x); //set x coordinate of chocolate block
        chocolateBlock.setY(y); //set y coordinate of chocolate block

        String imageFilePath; //image file path
        int random = new Random().nextInt(2);
        imageFilePath = (random == 0) ? "bonus1.png" : "bonus2.png"; //randomly choose bonus item, condition if random is 0 then choose bonus1.png, else choose bonus2.png

        chocolateBlock.setFill(new ImagePattern(new Image(imageFilePath))); //set image of chocolate block
    }
}
