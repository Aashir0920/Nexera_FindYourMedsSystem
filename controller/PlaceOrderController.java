package controller;

import sql_handler.DBStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PlaceOrderController {

    @FXML
    private TableView<ObservableList<String>> ordersTable;

    @FXML
    private TableColumn<ObservableList<String>, String> paymentStatusColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> orderIDColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> medicineIDColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> approvalStatusColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> orderTypeColumn;

    @FXML
    private Button proceedButton;

    @FXML
    public void initialize() throws SQLException {
        // Initialize table columns
        orderIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        medicineIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        paymentStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        approvalStatusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        orderTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));

        // Load orders into the table
        loadOrders();

        // Make proceed button hidden initially
        proceedButton.setVisible(false);

        // Add listener for row selection
        ordersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                proceedButton.setVisible(true);
            } else {
                proceedButton.setVisible(false);
            }
        });
    }

    private void loadOrders() throws SQLException {
        // Fetch orders using DBStorage and populate the table
        int userID = UserSession.getInstance().getUserID();
        ObservableList<ObservableList<String>> orders = DBStorage.getInstance().fetchOrders(userID);
        ordersTable.setItems(orders);
    }

    @FXML
    public void proceed(ActionEvent event) throws IOException {
        ObservableList<String> selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            String selectedOrderID = selectedOrder.get(0);

            // Load the OrderTypeSelection.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderTypeSelection.fxml"));
            Parent orderTypePage = loader.load();

            // Pass the Order ID to OrderTypeController
            OrderTypeController orderTypeController = loader.getController();
            orderTypeController.setOrderDetails(selectedOrderID);

            // Switch the scene
            Scene orderTypeScene = new Scene(orderTypePage);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(orderTypeScene);
        } else {
            // Show an alert if no order is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Order Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an order to proceed.");
            alert.showAndWait();
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        Parent homePage = FXMLLoader.load(getClass().getResource("/view/CustomerDashboard.fxml"));
        Scene homeScene = new Scene(homePage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);
    }
}
