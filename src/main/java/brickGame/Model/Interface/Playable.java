package brickGame.Model.Interface;

/**
 * The Playable interface is used to move the paddle.
 * Classes implementing this interface will need to implement the move method.
 */

public interface Playable {
    /**
     * The move method is used to move the paddle.
     * @param direction integer representing the direction in which the paddle should move.
     */
    void move(int direction);
}
