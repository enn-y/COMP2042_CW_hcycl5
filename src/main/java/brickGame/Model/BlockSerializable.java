package brickGame.Model;

import java.io.Serializable;

public class BlockSerializable implements Serializable { //Allows for transfer of data between two different Java Virtual Machines
    public final int row; //Indicates the row that the block is in
    public final int column; //Indicates the column that the block is in
    public final int type; //Indicates the type of block

    public BlockSerializable(int row , int column , int type) { //Constructor
        this.row = row;
        this.column = column;
        this.type = type;
    }
}
