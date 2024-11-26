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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderHistoryController {

    @FXML
    private ListView<String> orderListView;

    @FXML
    public void initialize() {
        // Load order data from the database
        loadOrders();
    }

    private void loadOrders() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Get the database connection
            connection = DBStorage.getInstance().getConnection();

            // SQL query to fetch order details
            String query = "SELECT o.orderID, o.orderDate, o.quantity, m.name1 AS medicineName " +
                           "FROM Orders o " +
                           "INNER JOIN Medicine m ON o.medicineID = m.medID";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            // Clear the ListView and populate it with the fetched data
            orderListView.getItems().clear();
            while (resultSet.next()) {
                int orderID = resultSet.getInt("orderID");
                String orderDate = resultSet.getString("orderDate");
                int quantity = resultSet.getInt("quantity");
                String medicineName = resultSet.getString("medicineName");

                // Format the order details for display
                String orderDetails = String.format("Order #%d - %s - Quantity: %d - Date: %s",
                        orderID, medicineName, quantity, orderDate);
                orderListView.getItems().add(orderDetails);
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
    public void goBackToDashboard(ActionEvent event) throws Exception {
        // Redirect to Admin Dashboard
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
        Parent dashboardPage = loader.load();

        Scene scene = new Scene(dashboardPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
    }
}
