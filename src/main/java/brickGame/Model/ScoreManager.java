package brickGame.Model;

import brickGame.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * The Score class represents the score.
 * It is used to display the score, game over, and win.
 * It also animates the score. It is used to display the message.
 *
 * @author Lua Chong En
 *
 */

public class ScoreManager {

    /**
     * The show method is used to display the score.
     * It also animates the score.
     * @param x The x-coordinate of the score.
     * @param y The y-coordinate of the score.
     * @param score The score.
     * @param main The Main instance to access the components of the game.
     */

    public void show(final double x, final double y, int score, final Main main) {

        String sign;
        if (score >= 0) {
            sign = "+";
        } else {
            sign = "";
        }
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        Platform.runLater(() -> main.getGameScreen().root.getChildren().add(label));

        new Thread(() -> {
            for (int i = 0; i < 21; i++) {
                try {
                    double scale = 1.0 + i * 0.1;
                    int tempI = i;
                    Platform.runLater(() -> {
                        label.setScaleX(scale);
                        label.setScaleY(scale);
                        label.setOpacity((20 - tempI) / 20.0);
                    });
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> main.getGameScreen().root.getChildren().remove(label));
        }).start();
    }

    /**
     * The showMessage method is used to display the message.
     * It also animates the message.
     * @param message The message to be displayed.
     * @param main The Main instance to access the components of the game.
     */

    public void showMessage(String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(() -> main.getGameScreen().root.getChildren().add(label));

        new Thread(() -> {
            for (int i = 0; i < 21; i++) {
                try {
                    double scale = Math.abs(i - 10) * 0.1 + 1.0;
                    int finalI = i;
                    Platform.runLater(() -> {
                        label.setScaleX(scale);
                        label.setScaleY(scale);
                        label.setOpacity((20 - finalI) / 20.0);
                    });
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> main.getGameScreen().root.getChildren().remove(label));
        }).start();
    }

    /**
     * The showGameOver method is used to display the game over screen.
     * It also displays the score, level, and hearts information.
     * @param main The Main instance to access the components of the game.
     */

    public void showGameOver(final Main main) {
        Platform.runLater(() -> {
            Pane gameOverLayout = new Pane();

            // Large font title "Game Over"
            Label gameOverLabel = new Label("Game Over!");
            gameOverLabel.setStyle("-fx-font-size: 36;");
            gameOverLabel.setLayoutX(160);
            gameOverLabel.setLayoutY(140);

            // Score, level, and hearts information
            Label scoreLabel = new Label("Score: " + main.currentScore);
            Label levelLabel = new Label("Level: " + main.currentLevel);
            Label heartsLabel = new Label("Hearts: " + main.getPlayer().numberOfHearts);
            scoreLabel.setStyle("-fx-font-size: 24;");
            scoreLabel.setLayoutX(200);
            scoreLabel.setLayoutY(240);
            levelLabel.setStyle("-fx-font-size: 24;");
            levelLabel.setLayoutX(200);
            levelLabel.setLayoutY(270);
            heartsLabel.setStyle("-fx-font-size: 24;");
            heartsLabel.setLayoutX(200);
            heartsLabel.setLayoutY(300);

            Button restart = new Button("Restart");
            restart.setTooltip(new javafx.scene.control.Tooltip("Restart game"));
            restart.setTranslateX(150);
            restart.setTranslateY(400);
            restart.setPrefWidth(200);
            restart.setOnAction(event -> main.getState().restartGame());

            Button exit = new Button("Exit");
            exit.setTooltip(new javafx.scene.control.Tooltip("Exit game"));
            exit.setTranslateX(150);
            exit.setTranslateY(450);
            exit.setPrefWidth(200);
            exit.setOnAction(event -> {
                if (main.getButtonControls().showConfirmationDialog("Exit Confirmation", "Are you sure you want to exit?")) {
                    main.getGameScreen().primaryStage.close();
                    main.getButtonControls().hideButtons();
                }
            });

            gameOverLayout.getChildren().addAll(gameOverLabel, scoreLabel, levelLabel, heartsLabel, restart, exit);
            Scene gameOverScene = new Scene(gameOverLayout, 500, 700);

            main.getGameScreen().primaryStage.setScene(gameOverScene);
            main.getGameScreen().primaryStage.show();
        });
    }

    /**
     * The showWin method is used to display the win screen.
     * It also displays the score, level, and hearts information.
     * @param main The Main instance to access the components of the game.
     */

    public void showGameWin(final Main main) {
        Platform.runLater(() -> {
            Pane gameWinLayout = new Pane();

            Label gameWinLabel = new Label("Game Win!");
            gameWinLabel.setStyle("-fx-font-size: 36;");
            gameWinLabel.setLayoutX(160);
            gameWinLabel.setLayoutY(140);

            Label scoreLabel = new Label("Score: " + main.currentScore);
            Label levelLabel = new Label("Level: " + main.currentLevel);
            Label heartsLabel = new Label("Hearts: " + main.getPlayer().numberOfHearts);
            scoreLabel.setStyle("-fx-font-size: 24;");
            scoreLabel.setLayoutX(200);
            scoreLabel.setLayoutY(240);
            levelLabel.setStyle("-fx-font-size: 24;");
            levelLabel.setLayoutX(200);
            levelLabel.setLayoutY(270);
            heartsLabel.setStyle("-fx-font-size: 24;");
            heartsLabel.setLayoutX(200);
            heartsLabel.setLayoutY(300);

            Button exit = new Button("Exit");
            exit.setTooltip(new javafx.scene.control.Tooltip("Exit game"));
            exit.setTranslateX(150);
            exit.setTranslateY(450);
            exit.setPrefWidth(200);
            exit.setOnAction(event -> {
                if (main.getButtonControls().showConfirmationDialog("Exit Confirmation", "Are you sure you want to exit?")) {
                    main.getGameScreen().primaryStage.close();
                }
            });

            gameWinLayout.getChildren().addAll(gameWinLabel, scoreLabel, levelLabel, heartsLabel, exit);
            Scene gameWinScene = new Scene(gameWinLayout, 500, 700);

            main.getGameScreen().primaryStage.setScene(gameWinScene);
            main.getGameScreen().primaryStage.show();
        });
    }
}
