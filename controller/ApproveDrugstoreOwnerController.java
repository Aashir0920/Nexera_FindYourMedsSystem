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

public class ApproveDrugstoreOwnerController {

    @FXML
    private ListView<String> ownerListView;

    @FXML
    public void initialize() {
        // Load the unapproved owners into the ListView
        loadUnapprovedOwners();
    }

    private void loadUnapprovedOwners() {
        try {
            // Fetch unapproved owners using DBStorage
            List<String> unapprovedOwners = DBStorage.getInstance().getUnapprovedOwners();

            // Clear the ListView and populate it with unapproved owners
            ownerListView.getItems().clear();
            ownerListView.getItems().addAll(unapprovedOwners);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void approveOwner(ActionEvent event) {
        String selectedOwner = ownerListView.getSelectionModel().getSelectedItem();
        if (selectedOwner != null) {
            // Extract the owner ID from the selected item
            int ownerID = Integer.parseInt(selectedOwner.split(" - ")[0].split(": ")[1]);

            try {
                // Approve the owner using DBStorage
                boolean success = DBStorage.getInstance().approveOwner(ownerID);

                if (success) {
                    System.out.println("Owner approved successfully.");

                    // Refresh the list after approval
                    loadUnapprovedOwners();
                } else {
                    System.out.println("Failed to approve owner.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select an owner to approve.");
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
