package brickGame.Model.Interface;

/**
 * The Playable interface is used to move the paddle.
 * Classes implementing this interface will need to implement the move method.
 */

public interface Playable {
    /**
     * The move method is used to move the paddle.
     * @param direction
     */
    void move(int direction);
}
