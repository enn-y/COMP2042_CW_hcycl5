package brickGame.Model.Interface;

/**
 * The Ball interface is used to set and get the ball's coordinates.
 * Classes implementing this interface will need to implement the setBallXCoordinate, getBallXCoordinate, setBallYCoordinate, and getBallYCoordinate methods.
 */

public interface BallPosition {
    /**
     * The setBallXCoordinate method is used to set the ball's x-coordinate.
     * @param ballXCoordinate The ball's x-coordinate.
     */
    void setBallXCoordinate(double ballXCoordinate);

    /**
     * The getBallXCoordinate method is used to get the ball's x-coordinate.
     * @return The ball's x-coordinate.
     */
    double getBallXCoordinate();

    /**
     * The setBallYCoordinate method is used to set the ball's y-coordinate.
     * @param ballYCoordinate The ball's y-coordinate.
     */
    void setBallYCoordinate(double ballYCoordinate);

    /**
     * The getBallYCoordinate method is used to get the ball's y-coordinate.
     * @return The ball's y-coordinate.
     */
    double getBallYCoordinate();
}
