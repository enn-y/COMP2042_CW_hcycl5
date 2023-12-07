package brickGame.Model;

import brickGame.Main;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreManagerTest {
    Main main = new Main();
    ScoreManager score = new ScoreManager();

    @Test
    public void show() {
        new Thread(() -> {
            score.show(3, 30, 30, main);
            int childrenCount = main.getGameScreen().root.getChildren().size();
            assertEquals(1, childrenCount);
        });
    }

    @Test
    public void showMessage() {
        new Thread(() -> {
            score.showMessage("showMessageTest", main);
            int childrenCount = main.getGameScreen().root.getChildren().size();
            assertEquals(1, childrenCount);
        });
    }

    @Test
    public void showGameOver() {
        new Thread(() -> {
            score.showGameOver(main);
            int childrenCount = main.getGameScreen().root.getChildren().size();
            assertEquals(1, childrenCount);
        });
    }

    @Test
    public void showWin() {
        new Thread(() -> {
            score.showGameWin(main);
            int childrenCount = main.getGameScreen().root.getChildren().size();
            assertEquals(1, childrenCount);
        });
    }
}