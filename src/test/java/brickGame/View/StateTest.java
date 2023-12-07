package brickGame.View;

import brickGame.Main;
import org.junit.Test;

import static org.junit.Assert.*;

public class StateTest {
    Main main = new Main();
    State state = new State(main);

    @Test
    public void testSaveAndLoadGame() {
        new Thread(() -> {
            main.currentLevel = 2;
            main.currentScore = 30;
            main.getPlayer().numberOfHearts = 3;

            state.saveGame();

            state.loadGame();

            assertEquals(2, main.currentLevel);
            assertEquals(30, main.currentScore);
            assertEquals(3, main.getPlayer().numberOfHearts);
        });
    }

    @Test
    public void testRestartGame() {
        new Thread(() -> {
            main.currentLevel = 2; // Modify game state
            main.currentScore = 30;
            main.getPlayer().numberOfHearts = 2;

            state.restartGame(); // Restart the game

            assertEquals(0, main.currentLevel); // Check if the game state is reset
            assertEquals(0, main.currentScore);
            assertEquals(3, main.getPlayer().numberOfHearts);
        });
    }
}