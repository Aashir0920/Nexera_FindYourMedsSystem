package controller;

import sql_handler.DBStorage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class AdminLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void login(ActionEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate admin login credentials using DBStorage
        if (DBStorage.getInstance().validateAdmin(username, password)) {
            // Redirect to admin dashboard if login is successful
            Parent adminDashboardPage = FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"));
            Scene adminDashboardScene = new Scene(adminDashboardPage);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(adminDashboardScene);
        } else {
            // Show invalid login message
            showAlert("Login Error", "Invalid username or password. Please try again.", AlertType.ERROR);
        }
    }

    @FXML
    public void goBackToMainPage(ActionEvent event) throws Exception {
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
        Scene mainScene = new Scene(mainPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(mainScene);
    }

    // Utility method to show alerts
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
