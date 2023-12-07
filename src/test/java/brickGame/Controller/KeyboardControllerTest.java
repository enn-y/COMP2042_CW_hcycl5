package brickGame.Controller;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeyboardControllerTest {
    private KeyboardController keyboardController;

    @Test
    public void testHandleLeftArrowKey() {
        runTest(KeyCode.LEFT);
    }

    @Test
    public void testHandleRightArrowKey() {
        runTest(KeyCode.RIGHT);
    }

    @Test
    public void testHandleSpaceKey() {
        runTest(KeyCode.SPACE);
    }

    @Test
    public void testHandleRKey() {
        runTest(KeyCode.R);
    }

    @Test
    public void testHandleQKey() {
        runTest(KeyCode.Q);
    }

    @Test
    public void testHandleSKey() {
        runTest(KeyCode.S);
    }

    private void runTest(KeyCode keyCode) {
        new Thread(() -> {
            Platform.runLater(() -> {
                KeyEvent event = new KeyEvent(
                        KeyEvent.KEY_PRESSED, "", "", keyCode, false, false, false, false
                );
                keyboardController.handle(event);
            });
        });
    }
}