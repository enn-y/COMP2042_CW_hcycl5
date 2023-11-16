module brickGame {
    requires javafx.fxml;
    requires javafx.controls;

    opens brickGame to javafx.fxml;
    exports brickGame;
    exports brickGame.Model;
    opens brickGame.Model to javafx.fxml;
    exports brickGame.Model.Serializables;
    opens brickGame.Model.Serializables to javafx.fxml;
}