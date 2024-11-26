package controller;

import sql_handler.DBStorage;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FeedbackController {

    @FXML
    private TextArea feedbackArea;

    @FXML
    private TextField pharmacyNameField;  // TextField for pharmacy name

    @FXML
    public void submitFeedback(ActionEvent event) throws SQLException {
        String feedbackText = feedbackArea.getText();
        String pharmacyName = pharmacyNameField.getText();
        int userID = UserSession.getInstance().getUserID(); // Get user ID from session

        // Validate input
        if (pharmacyName.isEmpty() || feedbackText.isEmpty()) {
            showError("Missing Information", "Please provide both pharmacy name and feedback.");
            return;
        }

        // Get the pharmacy ID from the pharmacy name
        int pharmacyID = DBStorage.getInstance().getPharmacyIDByName(pharmacyName);

        if (pharmacyID == -1) {
            showError("Pharmacy Not Found", "The pharmacy you entered does not exist.");
            return;
        }

        // Insert feedback into the database
        boolean success = DBStorage.getInstance().insertFeedback(userID, pharmacyID, feedbackText);

        if (success) {
            showSuccess("Feedback Submitted", "Your feedback has been successfully submitted.");
        } else {
            showError("Submission Error", "There was an error submitting your feedback.");
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        Parent homePage = FXMLLoader.load(getClass().getResource("/view/CustomerDashboard.fxml"));
        Scene homeScene = new Scene(homePage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);
    }

    private void showError(String title, String message) {
        // Implement a method to show error messages to the user (e.g., an alert or dialog)
    }

    private void showSuccess(String title, String message) {
        // Implement a method to show success messages to the user (e.g., an alert or dialog)
    }
}
