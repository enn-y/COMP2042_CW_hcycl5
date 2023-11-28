package brickGame.Model.Interface;

/**
 * The OnAction interface defines methods for handling events in the game.
 * Classes implementing this interface will need to implement the onUpdate, onInit, onPhysicsUpdate, and onTime methods.
 *
 * @author Lua Chong En
 *
 */

public interface OnAction {
    /**
     * The onUpdate method is called every frame.
     */
    void onUpdate();

    /**
     * The onInit method is called when the game is initialized.
     */

    void onInit();
    /**
     * The onPhysicsUpdate method is called when the physics is updated.
     */

    void onPhysicsUpdate();

    /**
     * The onPhysicsUpdate method is called when the time is updated.
     * @param time The time in milliseconds.
     */

    void onTime(long time);
}
