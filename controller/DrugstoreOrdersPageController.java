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

import java.sql.SQLException;
import java.util.ArrayList;

public class DrugstoreOrdersPageController {

    @FXML
    private ListView<String> ordersListView; // Holds order summaries

    private int loggedInOwnerID; // To store the logged-in owner ID

    // Set the logged-in owner ID
    public void setLoggedInOwner(String ownerId) throws SQLException {
        this.loggedInOwnerID = Integer.parseInt(ownerId);
        loadOrders(); // Load orders when the owner ID is set
    }

    @FXML
    public void initialize() {
        // Initialize is intentionally left blank. Orders are loaded after setting the owner ID.
    }

    private void loadOrders() throws SQLException {
        // Fetch orders from the DBStorage class
        ArrayList<String> orders = DBStorage.getInstance().fetchOrdersForOwner(loggedInOwnerID);
        ordersListView.getItems().addAll(orders); // Display the orders in the ListView
    }

    @FXML
    public void approveOrder(ActionEvent event) {
        String selectedOrder = ordersListView.getSelectionModel().getSelectedItem();

        if (selectedOrder != null) {
            try {
                int orderId = Integer.parseInt(selectedOrder.split(" ")[1].replace("#", ""));

                // Approve the order using DBStorage class
                boolean isApproved = DBStorage.getInstance().approveOrder(orderId);

                if (isApproved) {
                    // Update the status in the ListView
                    ordersListView.getItems().set(
                        ordersListView.getSelectionModel().getSelectedIndex(),
                        selectedOrder.replace("Pending", "Approved")
                    );
                    System.out.println("Order approved successfully.");
                } else {
                    System.out.println("Failed to approve the order.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void goBackToDashboard(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DrugstoreOwnerDashboard.fxml"));
        Parent dashboardPage = loader.load();

        DrugstoreOwnerDashboardController dashboardController = loader.getController();
        dashboardController.setLoggedInOwner(loggedInOwnerID);

        Scene scene = new Scene(dashboardPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
    }
}
