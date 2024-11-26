package controller;

import sql_handler.DBStorage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class DrugstoreOwnerLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private static String loggedInOwnerId;

    public static String getLoggedInOwnerId() {
        return loggedInOwnerId;
    }

    private static void setLoggedInOwnerId(String ownerId) {
        loggedInOwnerId = ownerId;
    }

    @FXML
    public void login(ActionEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        // Validate login credentials and fetch approval status and owner ID
        OwnerLoginResult loginResult = DBStorage.getInstance().validateLoginAndGetApprovalStatus(username, password);

        if (loginResult == null) {
            // Login failed due to invalid credentials
            showAlert("Login Failed", "Invalid username or password. Please try again.", Alert.AlertType.ERROR);
        } else if ("Pending".equalsIgnoreCase(loginResult.getApprovalStatus())) {
            // Approval is pending
            showAlert("Approval Pending", "Your account is not approved yet. Please wait for admin approval.", Alert.AlertType.WARNING);
        } else {
            // Login successful, store the logged-in owner's ID
            setLoggedInOwnerId(loginResult.getOwnerId());

            // Go to Drugstore Owner Dashboard
            Parent dashboardPage = FXMLLoader.load(getClass().getResource("/view/DrugstoreOwnerDashboard.fxml"));
            Scene dashboardScene = new Scene(dashboardPage);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(dashboardScene);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goBackToLoginOrRegister(ActionEvent event) throws Exception {
        Parent loginOrRegisterPage = FXMLLoader.load(getClass().getResource("/view/DrugstoreOwnerLoginOrRegisterPage.fxml"));
        Scene loginOrRegisterScene = new Scene(loginOrRegisterPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(loginOrRegisterScene);
    }
}
