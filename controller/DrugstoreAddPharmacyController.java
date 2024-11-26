package controller;

import sql_handler.DBStorage;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class DrugstoreAddPharmacyController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField contactField;

    @FXML
    public void addPharmacy(ActionEvent event) {
        String name = nameField.getText();
        String location = locationField.getText();
        String contact = contactField.getText();
        String ownerId = DrugstoreOwnerLoginController.getLoggedInOwnerId(); // Get logged-in owner ID

        if (name.isEmpty() || location.isEmpty() || contact.isEmpty()) {
            showAlert("Error", "Please fill in all fields.", AlertType.ERROR);
            return;
        }

        try {
            // Use DBStorage method to add pharmacy
            boolean isInserted = DBStorage.getInstance().addPharmacy(name, location, contact, ownerId);

            if (isInserted) {
                showAlert("Pharmacy Added", "Pharmacy successfully added!", AlertType.INFORMATION);

                // Redirect back to Manage Pharmacy
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DrugstoreManagePharmacy.fxml"));
                Parent managePharmacyPage = loader.load();

                // Pass updated data to the Manage Pharmacy controller
                DrugstoreManagePharmacyController controller = loader.getController();
                controller.setLoggedInOwner(ownerId);

                Scene managePharmacyScene = new Scene(managePharmacyPage);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(managePharmacyScene);
            } else {
                showAlert("Error", "Failed to add the pharmacy. Please try again.", AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while adding the pharmacy.", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
