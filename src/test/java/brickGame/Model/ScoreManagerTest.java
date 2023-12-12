package brickGame.Model;

import brickGame.Main;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreManagerTest {
    Main main = new Main();
    ScoreManager score = new ScoreManager();

    @Test
    public void testShow() {
        new Thread(() -> {
            score.show(3, 30, 30, main);
            int childrenCount = main.getGameScreen().root.getChildren().size();
            assertEquals(1, childrenCount);
        });
    }

    @Test
    public void testShowMessage() {
        new Thread(() -> {
            score.showMessage("showMessageTest", main);
            int childrenCount = main.getGameScreen().root.getChildren().size();
            assertEquals(1, childrenCount);
        });
    }

    @Test
    public void testShowGameOver() {
        new Thread(() -> {
            score.showGameOver(main);
            int childrenCount = main.getGameScreen().root.getChildren().size();
            assertEquals(1, childrenCount);
        });
    }

    @Test
    public void testShowWin() {
        new Thread(() -> {
            score.showGameWin(main);
            int childrenCount = main.getGameScreen().root.getChildren().size();
            assertEquals(1, childrenCount);
        });
    }
}