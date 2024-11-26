package controller;
import sql_handler.DBStorage;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;

public class CustomerLoginSceneController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void login(ActionEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            // Validate customer login and fetch userID from DBStorage
            DBStorage dbStorage = DBStorage.getInstance();
            int userID = dbStorage.getCustomerUserID(username, password);

            if (userID != -1) {
                // Set session details
                UserSession session = UserSession.getInstance();
                session.setUsername(username);
                session.setUserID(userID);

                // Transition to Customer Dashboard
                Parent customerDashboard = FXMLLoader.load(getClass().getResource("/view/CustomerDashboard.fxml"));
                Scene customerScene = new Scene(customerDashboard);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(customerScene);
            } else {
                // Invalid credentials
                showAlert("Login Error", "Invalid username or password!");
            }
        } catch (SQLException e) {
            // Handle database errors
            e.printStackTrace();
            showAlert("Database Error", "There was a problem connecting to the database. Please try again later.");
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        Parent homePage = FXMLLoader.load(getClass().getResource("/view/LoginScene.fxml"));
        Scene homeScene = new Scene(homePage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);
    }

    // Utility method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
