package controller;
import sql_handler.DBStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OrderTypeController {

    @FXML
    private Label orderIDLabel;

    @FXML
    private Button pickupButton;

    @FXML
    private Button codButton;

    private String orderID;

    public void setOrderDetails(String orderID) {
        this.orderID = orderID;
        orderIDLabel.setText("Order ID: " + orderID);
    }

    @FXML
    public void selectPickup(ActionEvent event) {
        updateOrderTypeInDatabase(orderID, "Pickup");
        showAlertAndGoBack("Pickup selected for Order ID: " + orderID, event);
    }

    @FXML
    public void selectCashOnDelivery(ActionEvent event) {
        updateOrderTypeInDatabase(orderID, "Delivery");
        showAlertAndGoBack("Delivery selected for Order ID: " + orderID, event);
    }

    private void updateOrderTypeInDatabase(String orderID, String orderType) {
        try (Connection connection = DBStorage.getInstance().getConnection()) {
            String query = "UPDATE Orders SET orderType = ? WHERE orderID = ?";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, orderType);
                pst.setInt(2, Integer.parseInt(orderID));
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlertAndGoBack(String message, ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Order Type Confirmation");
        alert.setHeaderText("Your Order Type has been Selected");
        alert.setContentText(message);
        alert.showAndWait();

        // Navigate back to Place Order page
        try {
            Parent placeOrderPage = FXMLLoader.load(getClass().getResource("/view/PlaceOrderScene.fxml"));
            Scene placeOrderScene = new Scene(placeOrderPage);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(placeOrderScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
