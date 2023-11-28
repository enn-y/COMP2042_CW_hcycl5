package brickGame.Model.Serializables;

import java.io.Serializable;

/**
 * The BlockSerializable class is used to serialize the block.
 * It is used to transfer data between two different Java Virtual Machines.
 *
 * @author Lua Chong En
 *
 */

public class BlockSerializable implements Serializable { //Allows for transfer of data between two different Java Virtual Machines
    public final int row; //Indicates the row that the block is in
    public final int column; //Indicates the column that the block is in
    public final int type; //Indicates the type of block

    /**
     * Constructor is used to serialize the block.
     * @param row The row of the block.
     * @param column The column of the block.
     * @param type The type of the block.
     */

    public BlockSerializable(int row , int column , int type) { //Constructor
        this.row = row;
        this.column = column;
        this.type = type;
    }
}
