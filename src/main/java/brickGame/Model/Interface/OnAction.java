package brickGame.Model.Interface;

public interface OnAction {
    void onUpdate();

    void onInit();

    void onPhysicsUpdate();

    void onTime(long time);
}
