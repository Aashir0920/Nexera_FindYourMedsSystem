package controller;

import sql_handler.DBStorage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.SQLException;

public class DrugstoreOwnerRegisterController {

    @FXML
    private TextField nameField; // Maps to `name1` in the table

    @FXML
    private TextField emailField; // Maps to `email` in the table

    @FXML
    private TextField contactInfoField; // Maps to `contactInfo` in the table

    @FXML
    private TextField businessLicenseField; // Maps to `businessLicense` in the table

    @FXML
    private PasswordField passwordField; // Maps to `password1` in the table

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private Label confirmationMessageLabel;

    @FXML
    public void register(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String contactInfo = contactInfoField.getText();
        String businessLicense = businessLicenseField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate input
        if (name.isEmpty() || email.isEmpty() || contactInfo.isEmpty() || businessLicense.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "All fields are required.", AlertType.ERROR);
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.", AlertType.ERROR);
            return;
        }

        // Insert data into the database using DBStorage
        try {
            int rowsAffected = DBStorage.getInstance().registerDrugstoreOwner(
                name,               // name1
                email,              // email
                contactInfo,        // contactInfo
                businessLicense,    // businessLicense
                password            // password1
            );

            if (rowsAffected > 0) {
                showAlert("Registration Successful",
                          "Your request has been sent to the admin for approval. Please wait before logging in.",
                          AlertType.INFORMATION);

                registerButton.setText("Request Sent");
                registerButton.setDisable(true);
            } else {
                showAlert("Error", "Registration failed. Please try again.", AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while connecting to the database.", AlertType.ERROR);
        }
    }

    @FXML
    public void goBackToLoginOrRegister(ActionEvent event) throws Exception {
        Parent loginOrRegisterPage = FXMLLoader.load(getClass().getResource("/view/DrugstoreOwnerLoginOrRegisterPage.fxml"));
        Scene loginOrRegisterScene = new Scene(loginOrRegisterPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(loginOrRegisterScene);
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
