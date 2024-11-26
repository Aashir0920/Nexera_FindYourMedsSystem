package controller;

import sql_handler.DBStorage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DrugstoreAddMedicineController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField formulaField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField manufacturerField;

    private DrugstoreManageMedicinesController parentController;

    public void setParentController(DrugstoreManageMedicinesController parentController) {
        this.parentController = parentController;
    }

    @FXML
    public void addMedicine(ActionEvent event) {
        String name = nameField.getText();
        String formula = formulaField.getText();
        String price = priceField.getText();
        String quantity = quantityField.getText();
        String manufacturer = manufacturerField.getText();

        if (name.isEmpty() || formula.isEmpty() || price.isEmpty() || quantity.isEmpty() || manufacturer.isEmpty()) {
            showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
        } else {
            try {
                boolean success = DBStorage.getInstance().addMedicine(
                        name,
                        formula,
                        Double.parseDouble(price),
                        Integer.parseInt(quantity),
                        manufacturer,
                        parentController.getPharmacyID()
                );

                if (success) {
                    showAlert("Success", "The new medicine has been added to the database.", Alert.AlertType.INFORMATION);

                    // Update the parent controller's table view
                    ObservableList<String> newMedicine = FXCollections.observableArrayList(
                            name, formula, price, quantity, manufacturer
                    );
                    parentController.getMedicines().add(newMedicine);
                    parentController.getMedicineTable().refresh();

                    // Clear fields for the next entry
                    nameField.clear();
                    formulaField.clear();
                    priceField.clear();
                    quantityField.clear();
                    manufacturerField.clear();

                    // Navigate back to the manage medicines page
                    goBack(event);
                } else {
                    showAlert("Error", "Failed to add the new medicine.", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter valid numeric values for price and quantity.", Alert.AlertType.ERROR);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while adding the medicine.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DrugstoreManageMedicines.fxml"));
        Parent manageMedicinesPage = loader.load();

        // Ensure that the parent controller is re-initialized
        DrugstoreManageMedicinesController controller = loader.getController();
        controller.setPharmacyID(parentController.getPharmacyID());
        controller.loadMedicines();

        Scene manageMedicinesScene = new Scene(manageMedicinesPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(manageMedicinesScene);
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
