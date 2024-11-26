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
import javafx.stage.Stage;
import javafx.scene.Node;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MedicineInventoryManagementController {

    @FXML
    private ListView<String> medicineListView; // ListView to display medicines

    private ObservableList<String> allMedicines; // List of all medicines with details

    @FXML
    public void initialize() {
        // Initialize and load medicines from the database
        loadMedicines();
        // Set the ListView to display the loaded medicines
        medicineListView.setItems(allMedicines);
    }

    private void loadMedicines() {
        allMedicines = FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Connect to the database
            connection = DBStorage.getInstance().getConnection();
            statement = connection.createStatement();

            // Execute SQL query to fetch medicines with pharmacy names
            String query = "SELECT m.name1 AS medicineName, m.formula, m.quantity, p.name1 AS pharmacyName " +
                           "FROM Medicine m " +
                           "INNER JOIN Pharmacy p ON m.pharmacyID = p.pharmacyID";

            resultSet = statement.executeQuery(query);

            // Add results to the ObservableList
            while (resultSet.next()) {
                String medicineName = resultSet.getString("medicineName");
                String formula = resultSet.getString("formula");
                int quantity = resultSet.getInt("quantity");
                String pharmacyName = resultSet.getString("pharmacyName");

                // Format the output for each medicine
                String medicineDetails = String.format("Name: %s | Formula: %s | Quantity: %d | Pharmacy: %s",
                        medicineName, formula, quantity, pharmacyName);

                allMedicines.add(medicineDetails);
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
    public void goBackToDashboard(ActionEvent event) throws Exception {
        // Redirect to Admin Dashboard
        Parent dashboardPage = FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"));
        Scene scene = new Scene(dashboardPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
    }
}
