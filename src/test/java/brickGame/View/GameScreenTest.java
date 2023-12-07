package brickGame.View;

import brickGame.Main;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameScreenTest {
    Main main = new Main();
    GameScreen gameScreen = new GameScreen(main);

    @Test
    public void testAddButtons() {
        new Thread(() -> {
            assertNotNull(main.getButtonControls().newGameButton);
            assertNotNull(main.getButtonControls().loadButton);
            assertNotNull(main.getButtonControls().exitButton);
            assertNotNull(main.getButtonControls().instructionsButton);
        });
    }

    @Test
    public void testAddLabels() {
        new Thread(() -> {
            assertNotNull(gameScreen.scoreLabel);
            assertNotNull(gameScreen.levelLabel);
            assertNotNull(gameScreen.heartLabel);
        });
    }

    @Test
    public void testAddElements() {
        new Thread(() -> {
            assertNotNull(gameScreen.scoreLabel);
            assertNotNull(gameScreen.levelLabel);
            assertNotNull(gameScreen.heartLabel);
            assertNotNull(main.getButtonControls().newGameButton);
            assertNotNull(main.getButtonControls().loadButton);
            assertNotNull(main.getButtonControls().exitButton);
            assertNotNull(main.getButtonControls().instructionsButton);
        });
    }
}