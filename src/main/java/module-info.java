module brickGame {
    requires javafx.fxml;
    requires javafx.controls;

    opens brickGame to javafx.fxml;
    exports brickGame;
    exports brickGame.Model;
    opens brickGame.Model to javafx.fxml;
}