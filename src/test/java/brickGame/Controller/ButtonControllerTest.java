package brickGame.Controller;

import brickGame.Main;
import org.junit.Test;

import static org.junit.Assert.*;

public class ButtonControllerTest {
    ButtonController buttonController = new ButtonController(new Main()); //Create a new instance of ButtonController

    @Test
    public void testCreateLoadButtonHandler() {
        assertNotNull(new ButtonController(null).createLoadButtonHandler());
    }

    @Test
    public void testCreateNewGameButtonHandler() {
        assertNotNull(new ButtonController(null).createNewGameButtonHandler());
    }

    @Test
    public void testCreateExitButtonHandler() {
        assertNotNull(new ButtonController(null).createExitButtonHandler());
    }

    @Test
    public void testCreateInstructionsButtonHandler() {
        assertNotNull(new ButtonController(null).createInstructionsButtonHandler());
    }

    @Test
    public void testShowConfirmationDialog() {
        new Thread(() -> assertTrue(new ButtonController(null).showConfirmationDialog("Title", "Content")));
    }

    @Test
    public void testHideButtons() {
        new Thread(() -> {
            assertFalse(buttonController.loadButton.isVisible());
            assertFalse(buttonController.newGameButton.isVisible());
            assertFalse(buttonController.exitButton.isVisible());
            assertFalse(buttonController.instructionsButton.isVisible());
        });
    }
}