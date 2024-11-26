package controller;
import sql_handler.DBStorage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PharmacyMedicinesController {

    @FXML
    private Label pharmacyNameLabel;

    @FXML
    private ListView<String> medicineListView;

    // This method sets the pharmacy name and loads the medicines for that pharmacy
    public void setPharmacyName(String pharmacyName) {
        pharmacyNameLabel.setText("Pharmacy: " + pharmacyName);
        loadMedicines(pharmacyName);
    }

    private void loadMedicines(String pharmacyName) {
        medicineListView.getItems().clear(); // Clear existing items
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Get database connection
            connection = DBStorage.getInstance().getConnection();

            // SQL query to fetch medicines for the given pharmacy name
            String query = "SELECT m.name1 AS medicineName, m.formula, m.quantity " +
                           "FROM Medicine m " +
                           "INNER JOIN Pharmacy p ON m.pharmacyID = p.pharmacyID " +
                           "WHERE p.name1 = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, pharmacyName);
            resultSet = preparedStatement.executeQuery();

            // Add the fetched medicines to the ListView
            while (resultSet.next()) {
                String medicineName = resultSet.getString("medicineName");
                String formula = resultSet.getString("formula");
                int quantity = resultSet.getInt("quantity");

                // Format the output for each medicine
                String medicineDetails = String.format("Name: %s | Formula: %s | Quantity: %d",
                        medicineName, formula, quantity);
                medicineListView.getItems().add(medicineDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close resources
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void goBackToPharmacyManagement(ActionEvent event) throws Exception {
        // Redirect to Pharmacy Management Page
        Parent pharmacyManagementPage = FXMLLoader.load(getClass().getResource("/view/PharmacyManagementScene.fxml"));
        Scene scene = new Scene(pharmacyManagementPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
    }
}
