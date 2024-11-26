package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class MainSceneController {
    @FXML
    private TextField tfTitle; // TextField defined in your FXML

    // Event Listener for the Button's onAction event
    @FXML
    public void clicked(ActionEvent event) {
        // Get the current window (Stage)
        Stage currentStage = (Stage) tfTitle.getScene().getWindow();

        // Get the text entered in the TextField
        String title = tfTitle.getText();

        // Set the window's title to the text entered
        currentStage.setTitle(title);
    }
}
