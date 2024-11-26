package controller;
import sql_handler.DBStorage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class WishlistController {

    @FXML
    private ListView<String> wishlistListView;

    @FXML
    private Button orderButton;

    private String selectedMedicine = null;
    private int selectedMedicineID = -1;
    private int selectedPharmacyID = -1;

    @FXML
    public void initialize() {
        loadWishlistItems();
    }

    private void loadWishlistItems() {
        try {
            wishlistListView.getItems().clear();
            int userID = UserSession.getInstance().getUserID();
            DBStorage dbStorage = DBStorage.getInstance();

            // Corrected query
            String query = "SELECT m.name1, w.medid, w.pharmacyid, p.location1 " +
                           "FROM Wishlist w " +
                           "JOIN Medicine m ON w.medid = m.medID " +
                           "JOIN Pharmacy p ON w.pharmacyid = p.pharmacyID " +
                           "WHERE w.userid = ?";
            ResultSet rs = dbStorage.executeQuery(query, String.valueOf(userID));

            // Load wishlist items
            while (rs.next()) {
                String medicineName = rs.getString("name1");
                int medicineID = rs.getInt("medid");
                int pharmacyID = rs.getInt("pharmacyid");
                String pharmacyLocation = rs.getString("location1");

                wishlistListView.getItems().add(
                    medicineName + " (Medicine ID: " + medicineID + 
                    ", Pharmacy ID: " + pharmacyID + 
                    ", Location: " + pharmacyLocation + ")"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database Error", "Could not load wishlist items.");
        }
    }

    @FXML
    public void onWishlistItemSelected() {
        String selectedItem = wishlistListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String[] parts = selectedItem.split(",");
            if (parts.length >= 2) {
                try {
                    selectedMedicine = parts[0].split("\\(")[0].trim();
                    selectedMedicineID = Integer.parseInt(parts[0].split(":")[1].trim());
                    selectedPharmacyID = Integer.parseInt(parts[1].split(":")[1].trim());

                    orderButton.setVisible(true); // Show the Order button
                } catch (NumberFormatException e) {
                    showError("Error", "Invalid format in wishlist item.");
                    orderButton.setVisible(false);
                }
            }
        } else {
            orderButton.setVisible(false); // Hide the button if no item is selected
        }
    }

    @FXML
    public void placeOrder(ActionEvent event) {
        if (selectedMedicine != null && selectedMedicineID != -1 && selectedPharmacyID != -1) {
            TextInputDialog quantityDialog = new TextInputDialog();
            quantityDialog.setTitle("Order Quantity");
            quantityDialog.setHeaderText("Enter quantity for " + selectedMedicine);
            quantityDialog.setContentText("Quantity:");

            Optional<String> result = quantityDialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int quantity = Integer.parseInt(result.get());
                    if (quantity <= 0) {
                        showError("Invalid Quantity", "Quantity must be greater than 0.");
                        return;
                    }

                    int userID = UserSession.getInstance().getUserID();
                    DBStorage dbStorage = DBStorage.getInstance();

                    String medicineQuery = "SELECT price FROM Medicine WHERE medID = ?";
                    ResultSet rs = dbStorage.executeQuery(medicineQuery, String.valueOf(selectedMedicineID));

                    if (!rs.next()) {
                        showError("Medicine Not Found", "Could not find the selected medicine in the database.");
                        return;
                    }

                    double price = rs.getDouble("price");
                    double total = price * quantity;

                    String query = "INSERT INTO Orders (orderDate, total, quantity, paymentStatus, approvalStatus, userID, pharmacyID, medicineID) " +
                                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                    dbStorage.executeUpdate(query,
                            LocalDate.now().toString(),
                            total,
                            quantity,
                            "Pending",
                            "Pending",
                            userID,
                            selectedPharmacyID,
                            selectedMedicineID);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Order Placed");
                    successAlert.setHeaderText("Order Sent for Approval");
                    successAlert.setContentText("Your order for " + selectedMedicine + " has been sent for approval.");
                    successAlert.showAndWait();
                } catch (NumberFormatException e) {
                    showError("Invalid Input", "Please enter a valid quantity.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    showError("Database Error", "There was a problem placing your order.");
                }
            }
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        Parent dashboardPage = FXMLLoader.load(getClass().getResource("/view/CustomerDashboard.fxml"));
        Scene dashboardScene = new Scene(dashboardPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(dashboardScene);
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
