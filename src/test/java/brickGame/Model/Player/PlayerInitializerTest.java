package brickGame.Model.Player;

import brickGame.Main;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerInitializerTest {
    Main main = new Main();
    PlayerInitializer playerInitializer = new PlayerInitializer(main);

    @Test
    public void initializePaddle() {
        new Thread(() -> {
            playerInitializer.initializePaddle();
            assertEquals(main.getPlayer().paddleXPosition, main.getPlayer().getX(), 0);
            assertEquals(main.getPlayer().paddleYPosition, main.getPlayer().getY(), 0);
            assertEquals(new ImagePattern(new Image("block.jpg")), main.getPlayer().getFill());
        });
    }
}