module brickGame {
    requires javafx.fxml;
    requires javafx.controls;

    opens brickGame to javafx.fxml;
    exports brickGame;
    exports brickGame.Model;
    opens brickGame.Model to javafx.fxml;
    exports brickGame.Model.Serializables;
    opens brickGame.Model.Serializables to javafx.fxml;
    exports brickGame.View;
    opens brickGame.View to javafx.fxml;
    exports brickGame.Model.Blocks;
    opens brickGame.Model.Blocks to javafx.fxml;
    exports brickGame.Model.Player;
    opens brickGame.Model.Player to javafx.fxml;
    exports brickGame.Model.Ball;
    opens brickGame.Model.Ball to javafx.fxml;
    exports brickGame.Controller;
    opens brickGame.Controller to javafx.fxml;
}