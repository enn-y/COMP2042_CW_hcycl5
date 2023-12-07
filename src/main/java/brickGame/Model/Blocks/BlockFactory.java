package brickGame.Model.Blocks;

import brickGame.Main;

import java.util.Random;

/**
 * The BlockFactory class is responsible for initializing the blocks.
 * It also determines the type and properties of each block.
 *
 * @author Lua Chong En
 *
 */

public class BlockFactory {
    Main main;

    /**
     * Constructor for BlockFactory class.
     * @param main The Main class instance to access the components of the game.
     */

    public BlockFactory(Main main){
        this.main = main;
    }

    /**
     * The initializeBlocks method initializes the blocks.
     * It also determines the type and properties of each block based on random conditions.
     */

    public void initializeBlocks() {
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < main.currentLevel+1; column++) {
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
                }
                int type;
                if (r % 10 == 1) {
                    type = BlockModel.BLOCK_CHOCOLATE;
                } else if (r % 10 == 2) {
                    if (!main.getPlayer().existHeartBlock) {
                        type = BlockModel.BLOCK_HEART;
                        main.getPlayer().existHeartBlock = true;
                    } else {
                        type = BlockModel.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = BlockModel.BLOCK_STAR;
                } else if (r % 10 == 4) {
                    type = BlockModel.BLOCK_SLIME;
                } else if (r % 10 == 6) {
                    type = BlockModel.BLOCK_QUESTION;
                } else if (r % 10 == 7) {
                    type = BlockModel.BLOCK_BOMB;
                } else if (r % 10 == 8) {
                    type = BlockModel.BLOCK_TELEPORT;
                } else if (r % 3 == 0) {
                    type = BlockModel.BLOCK_SPEED;
                } else {
                    type = BlockModel.BLOCK_NORMAL;
                }
                main.getEngine().blocks.add(new BlockModel(column, row, main.getEngine().blockColors[r % (main.getEngine().blockColors.length)], type));
            }
        }
    }
}