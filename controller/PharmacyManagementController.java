package controller;
import sql_handler.DBStorage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PharmacyManagementController {

    @FXML
    private ListView<String> pharmacyListView; // ListView to display pharmacy details

    private ObservableList<String> allPharmacies; // List of all pharmacies with details

    @FXML
    public void initialize() {
        // Load the initial list of pharmacies with their details
        loadPharmacies();

        // Set the ListView to display the loaded pharmacies
        pharmacyListView.setItems(allPharmacies);
    }

    private void loadPharmacies() {
        allPharmacies = FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Connect to the database
            connection = DBStorage.getInstance().getConnection();
            statement = connection.createStatement();

            // SQL query to fetch pharmacy details along with owner name
            String query = "SELECT p.name1 AS pharmacyName, p.location1, d.name1 AS ownerName " +
                           "FROM Pharmacy p " +
                           "INNER JOIN DrugstoreOwner d ON p.ownerID = d.ownerID";

            resultSet = statement.executeQuery(query);

            // Add results to the ObservableList
            while (resultSet.next()) {
                String pharmacyName = resultSet.getString("pharmacyName");
                String location = resultSet.getString("location1");
                String ownerName = resultSet.getString("ownerName");

                // Format the output for each pharmacy
                String pharmacyDetails = String.format("Pharmacy: %s | Location: %s | Owner: %s",
                        pharmacyName, location, ownerName);

                allPharmacies.add(pharmacyDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close resources
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handlePharmacyClick(MouseEvent event) throws Exception {
        if (event.getClickCount() == 2) { // Detect double-click
            String selectedPharmacy = pharmacyListView.getSelectionModel().getSelectedItem();
            if (selectedPharmacy != null) {
                // Extract the pharmacy name from the selected item
                String pharmacyName = selectedPharmacy.split(" \\| ")[0].replace("Pharmacy: ", "");

                // Open the Pharmacy Medicines Page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PharmacyMedicinesScene.fxml"));
                Parent page = loader.load();

                // Pass the selected pharmacy name to the PharmacyMedicinesController
                PharmacyMedicinesController controller = loader.getController();
                controller.setPharmacyName(pharmacyName);

                Scene scene = new Scene(page);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(scene);
            }
        }
    }

    @FXML
    public void goBackToDashboard(ActionEvent event) throws Exception {
        // Redirect to Admin Dashboard
        Parent dashboardPage = FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"));
        Scene scene = new Scene(dashboardPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
    }
}
