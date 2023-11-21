package brickGame.Model;

import brickGame.Main;
import brickGame.View.State;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
//import sun.plugin2.message.Message;

public class Score { //Methods include: show, showMessage, showGameOver, and showWin
    public void show(final double x, final double y, int score, final Main main) {

        String sign;
        if (score >= 0) { // if score is positive, add the "+" sign
            sign = "+";
        } else { // if score is negative, add the "-" sign
            sign = "";
        }
        final Label label = new Label(sign + score); //Create the label and assign the sign and score
        label.setTranslateX(x); //Position of label on x-axis
        label.setTranslateY(y); //Position of label on y-axis

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                main.root.getChildren().add(label);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 21; i++) { //Loop to animate the label, condition is i < 21 because we want to animate the label for 20 times
                    try {
                        double scale = 1.0 + i * 0.1; //Make the label smaller
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
                Platform.runLater(new Runnable() { //To prevent the label from remaining on the screen
                    @Override
                    public void run() {
                        main.root.getChildren().remove(label);
                    }
                });
            }
        }).start();
    }

    public void showMessage(String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                main.root.getChildren().add(label);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 21; i++) {
                    try {
                        double scale = Math.abs(i - 10) * 0.1 + 1.0; // Smaller scaling effect
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
                Platform.runLater(new Runnable() { //To prevent the label from remaining on the screen
                    @Override
                    public void run() {
                        main.root.getChildren().remove(label);
                    }
                });
            }
        }).start();
    }

    public void showGameOver(final Main main) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Pane gameOverLayout = new Pane();

                // Large font title "Game Over"
                Label gameOverLabel = new Label("Game Over");
                gameOverLabel.setStyle("-fx-font-size: 36;"); // Customize font size
                gameOverLabel.setLayoutX(160);
                gameOverLabel.setLayoutY(140);

                // Score, level, and hearts information
                Label scoreLabel = new Label("Score: " + main.currentScore);
                Label levelLabel = new Label("Level: " + main.currentLevel);
                Label heartsLabel = new Label("Hearts: " + main.numberOfHearts);
                scoreLabel.setStyle("-fx-font-size: 24;"); // Customize font size
                scoreLabel.setLayoutX(200); // Center horizontally
                scoreLabel.setLayoutY(240);
                levelLabel.setStyle("-fx-font-size: 24;"); // Customize font size
                levelLabel.setLayoutX(200); // Center horizontally
                levelLabel.setLayoutY(270);
                heartsLabel.setStyle("-fx-font-size: 24;"); // Customize font size
                heartsLabel.setLayoutX(200); // Center horizontally
                heartsLabel.setLayoutY(300);

                Button restart = new Button("Restart");
                restart.setTranslateX(150);
                restart.setTranslateY(400);
                restart.setPrefWidth(200); // Set a larger width
                restart.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        main.getState().restartGame();
                    }
                });

                Button exit = new Button("Exit");
                exit.setTranslateX(150);
                exit.setTranslateY(450);
                exit.setPrefWidth(200); // Set a larger width
                exit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // Exit the game (close the application)
                        main.primaryStage.close();
                    }
                });

                gameOverLayout.getChildren().addAll(gameOverLabel, scoreLabel, levelLabel, heartsLabel, restart, exit);
                Scene gameOverScene = new Scene(gameOverLayout, 500, 700);

                main.primaryStage.setScene(gameOverScene);
                main.primaryStage.show();
            }
        });
    }

    public void showWin(final Main main) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label label = new Label("You Win :)");
                label.setTranslateX(200);
                label.setTranslateY(250);
                label.setScaleX(2);
                label.setScaleY(2);

                main.root.getChildren().addAll(label);
            }
        });
    }
}
