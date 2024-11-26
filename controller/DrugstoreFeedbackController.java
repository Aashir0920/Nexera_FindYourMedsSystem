package controller;

import sql_handler.DBStorage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class DrugstoreFeedbackController {

    @FXML
    private ListView<String> feedbackListView; // List of feedback for the pharmacy

    private int loggedInOwnerID; // Owner ID passed from the previous screen

    /**
     * Sets the logged-in owner's ID and loads feedback for their pharmacies.
     *
     * @param ownerID The logged-in owner's ID.
     */
    public void setLoggedInOwnerID(int ownerID) {
        this.loggedInOwnerID = ownerID;
        loadFeedback();
    }

    @FXML
    public void initialize() {
        // Placeholder for additional initialization logic
    }

    /**
     * Loads feedback for pharmacies owned by the logged-in owner.
     */
    private void loadFeedback() {
        try {
            List<String> feedbackList = DBStorage.getInstance().getFeedbackForOwner(loggedInOwnerID);
            feedbackListView.getItems().clear();
            feedbackListView.getItems().addAll(feedbackList);
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error", "Failed to load feedback from the database.");
        }
    }

    @FXML
    public void goBackToDashboard(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DrugstoreOwnerDashboard.fxml"));
        Parent dashboardPage = loader.load();

        // Pass the logged-in owner ID back to the dashboard
        DrugstoreOwnerDashboardController dashboardController = loader.getController();
        dashboardController.setLoggedInOwner(loggedInOwnerID);

        Scene scene = new Scene(dashboardPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setLoggedInOwner(String loggedInOwnerId) {
        this.loggedInOwnerID = Integer.parseInt(loggedInOwnerId);
        loadFeedback();
    }
}
