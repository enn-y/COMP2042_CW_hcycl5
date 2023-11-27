package brickGame.Model.Blocks;

import brickGame.Main;

import java.util.Random;

public class BlockFactory {
    Main main;

    public BlockFactory(Main main){
        this.main = main;
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
                    type = BlockModel.BLOCK_CHOCOLATE; //Create a choco block
                } else if (r % 10 == 2) { //BUT IF the remainder is 2
                    if (!main.getPlayer().existHeartBlock) { //AND IF there is NO heart block
                        type = BlockModel.BLOCK_HEART; //Create a heart block
                        main.getPlayer().existHeartBlock = true; //Set isExistHeartBlock to true, INDICATES that there is a heart block
                    } else { //BUT IF there IS a heart block
                        type = BlockModel.BLOCK_NORMAL; //Create a normal block
                    }
                } else if (r % 10 == 3) { //BUT IF the remainder is 3
                    type = BlockModel.BLOCK_STAR; //Create a star block
                } else if (r % 10 == 4) { //BUT IF the remainder is 4
                    type = BlockModel.BLOCK_SLIME;
                } else if (r % 10 == 6) { //BUT IF the remainder is 5
                    type = BlockModel.BLOCK_QUESTION;
                } else if (r % 10 == 7) { //BUT IF the remainder is 6
                    type = BlockModel.BLOCK_BOMB;
                } else if (r % 10 == 8) { //BUT IF the remainder is 7
                    type = BlockModel.BLOCK_TELEPORT;
                } else if (r % 3 == 0) { //BUT IF the remainder is 8
                    type = BlockModel.BLOCK_SPEED;
                } else { //BUT IF the remainder is NOT 1, 2, or 3 THEN create a normal block
                    type = BlockModel.BLOCK_NORMAL; //Create a normal block
                }
                main.getEngine().blocks.add(new BlockModel(column, row, main.getEngine().blockColors[r % (main.getEngine().blockColors.length)], type)); //Add the block to the ArrayList
                //System.out.println("colors " + r % (colors.length));
            }
        }
    }
}
