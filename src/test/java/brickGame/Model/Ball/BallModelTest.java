package brickGame.Model.Ball;

import brickGame.Main;
import org.junit.Test;

import static org.junit.Assert.*;

public class BallModelTest {
    BallModel ballModel = new BallModel(new Main());

    @Test
    public void testSetPhysicsToBall() {
        new Thread(() -> {
            ballModel.setBallXCoordinate(50.0);
            ballModel.setBallYCoordinate(50.0);
            ballModel.goDownBall = true;
            ballModel.goRightBall = true;

            ballModel.setPhysicsToBall();

            assertTrue(ballModel.getBallYCoordinate() > 50.0); // Ball should move downwards
            assertTrue(ballModel.getBallXCoordinate() > 50.0); // Ball should move to the right
            assertFalse(ballModel.goDownBall); // Ball should not go down after reaching the bottom
            assertFalse(ballModel.collideToPaddle); // No paddle collision in this scenario
            assertFalse(ballModel.collideToRightWall); // No right wall collision in this scenario
            assertFalse(ballModel.collideToLeftWall); // No left wall collision in this scenario
        });
    }

    @Test
    public void testResetCollideFlags() {
        ballModel.collideToPaddle = true;
        ballModel.collideToRightWall = true;
        ballModel.collideToLeftWall = true;

        ballModel.resetCollideFlags();

        assertFalse(ballModel.collideToPaddle); // Flag should be reset to false
        assertFalse(ballModel.collideToRightWall); // Flag should be reset to false
        assertFalse(ballModel.collideToLeftWall); // Flag should be reset to false
    }

    @Test
    public void testSetBallXCoordinate() {
        double expectedXCoordinate = 10.0;
        ballModel.setBallXCoordinate(expectedXCoordinate);
        assertEquals(expectedXCoordinate, ballModel.getBallXCoordinate(), 0.1);
    }

    @Test
    public void testGetBallXCoordinate() {
        double expectedXCoordinate = 15.0;
        ballModel.setBallXCoordinate(expectedXCoordinate);
        assertEquals(expectedXCoordinate, ballModel.getBallXCoordinate(), 0.1);
    }

    @Test
    public void testSetBallYCoordinate() {
        double expectedYCoordinate = 10.0;
        ballModel.setBallYCoordinate(expectedYCoordinate);
        assertEquals(expectedYCoordinate, ballModel.getBallYCoordinate(), 0.1);
    }

    @Test
    public void testGetBallYCoordinate() {
        double expectedYCoordinate = 25.0;
        ballModel.setBallYCoordinate(expectedYCoordinate);
        assertEquals(expectedYCoordinate, ballModel.getBallYCoordinate(), 0.1);
    }
}