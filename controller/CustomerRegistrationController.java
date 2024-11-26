package controller;

import sql_handler.DBStorage;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CustomerRegistrationController {

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField passwordField;

    public void registerUser(ActionEvent event) throws Exception {
        // Fetch input values
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String contact = contactField.getText();
        String address = addressField.getText();
        String password = passwordField.getText();

        // Validate inputs
        if (fullName.isEmpty() || email.isEmpty() || contact.isEmpty() || address.isEmpty() || password.isEmpty()) {
            showAlert("Registration Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        try {
            DBStorage db = DBStorage.getInstance();
            boolean isRegistered = db.registerCustomer(fullName, email, contact, address, password);

            if (isRegistered) {
                showAlert("Success", "User registered successfully!", Alert.AlertType.INFORMATION);

                // Redirect to Customer Login Scene
                Parent loginPage = FXMLLoader.load(getClass().getResource("/view/CustomerLoginScene.fxml"));
                Scene loginScene = new Scene(loginPage);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(loginScene);
            } else {
                showAlert("Registration Error", "Registration failed! Please try again.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "There was a problem connecting to the database.", Alert.AlertType.ERROR);
        }
    }

    // Utility method to show alerts
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
