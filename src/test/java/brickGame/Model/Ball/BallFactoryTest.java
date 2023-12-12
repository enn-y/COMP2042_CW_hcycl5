package brickGame.Model.Ball;

import brickGame.Main;
import javafx.scene.paint.ImagePattern;
import org.junit.Test;

import static org.junit.Assert.*;

public class BallFactoryTest {
    Main main = new Main();
    BallFactory ballFactory = new BallFactory(main);

    @Test
    public void testInitializeBall() {
        new Thread(() -> {
            ballFactory.initializeBall();

            BallModel ball = main.getBall();
            assertEquals(0, ball.getBallXCoordinate(), 0.1);
            assertEquals(0, ball.getBallYCoordinate(), 0.1);
            assertEquals(0, ball.getCenterX(), 0.1);
            assertEquals(0, ball.getCenterY(), 0.1);
            assertEquals(ball.ballRadius, ball.getRadius(), 0.1);
            assertEquals(new ImagePattern(null), ball.getFill());
        });
    }
}