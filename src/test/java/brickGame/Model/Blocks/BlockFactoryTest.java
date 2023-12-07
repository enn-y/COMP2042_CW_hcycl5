package brickGame.Model.Blocks;

import brickGame.Main;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockFactoryTest {
    Main main = new Main();

    @Test
    public void testInitializeBlocks() {
        new Thread(() -> {
            BlockFactory blockFactory = new BlockFactory(main);
            blockFactory.initializeBlocks();
            assertFalse(main.getEngine().blocks.isEmpty());
        });
    }
}