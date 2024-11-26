package controller;

import sql_handler.DBStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SearchMedicinesController {

    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> medicinesListView;
    @FXML
    private javafx.scene.control.Button placeOrderButton;
    @FXML
    private javafx.scene.control.Button addToWishlistButton;

    private String selectedMedicine = null;
    private int selectedPharmacyID = -1;

    @FXML
    public void searchMedicines(ActionEvent event) {
        String searchQuery = searchField.getText();
        medicinesListView.getItems().clear();

        try {
            DBStorage dbStorage = DBStorage.getInstance();
            // Fetch medicines from DBStorage
            medicinesListView.getItems().addAll(dbStorage.searchMedicines(searchQuery));
        } catch (SQLException e) {
            showError("Database Error", "There was a problem fetching medicines from the database.");
            e.printStackTrace();
        }
    }

    @FXML
    public void onMedicineSelected() {
        String selectedItem = medicinesListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String[] parts = selectedItem.split("\\(Pharmacy ID = ");
            if (parts.length == 2) {
                selectedMedicine = parts[0].split(" - Available at: ")[0].trim();
                try {
                    selectedPharmacyID = Integer.parseInt(parts[1].replace(")", "").trim());
                    placeOrderButton.setVisible(true);
                    addToWishlistButton.setVisible(true);
                } catch (NumberFormatException e) {
                    showError("Invalid Format", "Could not parse Pharmacy ID.");
                }
            }
        }
    }

    @FXML
    public void placeOrder(ActionEvent event) {
        if (selectedMedicine != null && selectedPharmacyID != -1) {
            TextInputDialog quantityDialog = new TextInputDialog();
            quantityDialog.setTitle("Order Quantity");
            quantityDialog.setHeaderText("Enter the quantity for " + selectedMedicine);
            quantityDialog.setContentText("Quantity:");

            Optional<String> result = quantityDialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int quantity = Integer.parseInt(result.get());
                    if (quantity <= 0) {
                        showError("Invalid Quantity", "Quantity must be greater than 0.");
                        return;
                    }

                    DBStorage dbStorage = DBStorage.getInstance();
                    ResultSet rs = dbStorage.getMedicineDetails(selectedMedicine);

                    if (rs.next()) {
                        int medicineID = rs.getInt("medID");
                        double price = rs.getDouble("price");
                        double total = price * quantity;

                        dbStorage.placeOrder(UserSession.getInstance().getUserID(), selectedPharmacyID, medicineID, quantity, total);

                        showSuccess("Order Placed", "Your order for " + selectedMedicine + " has been sent for approval.");
                    } else {
                        showError("Medicine Not Found", "The selected medicine could not be found.");
                    }
                } catch (NumberFormatException e) {
                    showError("Invalid Input", "Please enter a valid quantity.");
                } catch (SQLException e) {
                    showError("Database Error", "There was a problem placing your order.");
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void addToWishlist(ActionEvent event) {
        if (selectedMedicine != null && selectedPharmacyID != -1) {
            try {
                DBStorage dbStorage = DBStorage.getInstance();
                ResultSet rs = dbStorage.getMedicineDetails(selectedMedicine);

                if (rs.next()) {
                    int medicineID = rs.getInt("medID");
                    dbStorage.addToWishlist(UserSession.getInstance().getUserID(), medicineID, selectedPharmacyID);

                    showSuccess("Added to Wishlist", selectedMedicine + " has been added to your wishlist.");
                } else {
                    showError("Medicine Not Found", "The selected medicine could not be found.");
                }
            } catch (SQLException e) {
                showError("Database Error", "There was a problem adding the medicine to the wishlist.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        Parent homePage = FXMLLoader.load(getClass().getResource("/view/CustomerDashboard.fxml"));
        Scene homeScene = new Scene(homePage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(homeScene);
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccess(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
