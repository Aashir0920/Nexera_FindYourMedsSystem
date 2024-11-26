package controller;

import sql_handler.DBStorage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class AdminFeedbackController {

    @FXML
    private ListView<String> feedbackListView;

    @FXML
    public void initialize() {
        // Load feedback data from the database
        loadFeedback();
    }

    private void loadFeedback() {
        try {
            // Get feedback data using DBStorage
            List<String> feedbacks = DBStorage.getInstance().getFeedbackDetails();

            // Clear the ListView and populate it with the fetched data
            feedbackListView.getItems().clear();
            feedbackListView.getItems().addAll(feedbacks);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goBackToDashboard(ActionEvent event) throws Exception {
        // Redirect to Admin Dashboard
        Parent dashboardPage = FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"));
        Scene dashboardScene = new Scene(dashboardPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(dashboardScene);
    }
}
