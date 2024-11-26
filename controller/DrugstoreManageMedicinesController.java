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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class DrugstoreManageMedicinesController {

    @FXML
    private TableView<ObservableList<String>> medicineTable;

    private ObservableList<ObservableList<String>> medicines;

    private int pharmacyID; // Holds the ID of the opened pharmacy
    
    public void setMedicines(ObservableList<ObservableList<String>> medicines) {
        this.medicines = medicines;
    }


    @FXML
    public void initialize() {
        if (medicineTable.getColumns().isEmpty()) {
            String[] columnNames = {"Medicine Name", "Formula", "Price", "Quantity", "Manufacturer"};
            for (int i = 0; i < columnNames.length; i++) {
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
                int columnIndex = i;
                column.setCellValueFactory(param ->
                        new javafx.beans.property.SimpleStringProperty(param.getValue().get(columnIndex))
                );
                medicineTable.getColumns().add(column);
            }

            setMedicines(FXCollections.observableArrayList());
        }
    }

    public void loadMedicines() {
        if (pharmacyID <= 0) {
            showAlert("Error", "Pharmacy not identified. Please try again.", Alert.AlertType.ERROR);
            return;
        }

        try {
            List<List<String>> fetchedMedicines = DBStorage.getInstance().getMedicinesByPharmacyID(pharmacyID);

            medicines.clear(); // Clear existing data
            for (List<String> row : fetchedMedicines) {
                medicines.add(FXCollections.observableArrayList(row));
            }

            medicineTable.setItems(medicines);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load medicines from the database.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void addMedicine(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DrugstoreAddMedicine.fxml"));
            Parent addMedicinePage = loader.load();

            // Pass the pharmacy ID and controller to the Add Medicine page
            DrugstoreAddMedicineController controller = loader.getController();
            controller.setParentController(this);

            Scene addMedicineScene = new Scene(addMedicinePage);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(addMedicineScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteMedicine() {
        ObservableList<String> selectedMedicine = medicineTable.getSelectionModel().getSelectedItem();
        if (selectedMedicine != null) {
            String medicineName = selectedMedicine.get(0);

            try {
                boolean isDeleted = DBStorage.getInstance().deleteMedicine(pharmacyID, medicineName);
                if (isDeleted) {
                    medicines.remove(selectedMedicine);
                    medicineTable.refresh();
                    showAlert("Success", "The selected medicine has been deleted.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to delete the selected medicine.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while deleting the medicine.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Warning", "Please select a medicine to delete.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void updateStock() {
        ObservableList<String> selectedMedicine = medicineTable.getSelectionModel().getSelectedItem();
        if (selectedMedicine != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Update Stock");
            dialog.setHeaderText("Set New Stock Quantity");
            dialog.setContentText("Enter quantity:");

            dialog.showAndWait().ifPresent(quantity -> {
                try {
                    int qty = Integer.parseInt(quantity);
                    String medicineName = selectedMedicine.get(0);

                    boolean isUpdated = DBStorage.getInstance().updateMedicineStock(pharmacyID, medicineName, qty);
                    if (isUpdated) {
                        selectedMedicine.set(3, String.valueOf(qty));
                        medicineTable.refresh();
                        showAlert("Success", "Stock quantity updated successfully.", Alert.AlertType.INFORMATION);
                    } else {
                        showAlert("Error", "Failed to update stock quantity.", Alert.AlertType.ERROR);
                    }
                } catch (NumberFormatException e) {
                    showAlert("Error", "Please enter a valid number.", Alert.AlertType.ERROR);
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Error", "An error occurred while updating stock.", Alert.AlertType.ERROR);
                }
            });
        } else {
            showAlert("Warning", "Please select a medicine to update.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DrugstoreManagePharmacy.fxml"));
        Parent managePharmacyPage = loader.load();
        Scene managePharmacyScene = new Scene(managePharmacyPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(managePharmacyScene);
    }

    public void setPharmacyID(int pharmacyID) {
        this.pharmacyID = pharmacyID;
        loadMedicines(); // Load medicines specific to this pharmacy
    }

    public TableView<ObservableList<String>> getMedicineTable() {
        return medicineTable;
    }
    public int getPharmacyID() {
        return pharmacyID;
    }

    public ObservableList<ObservableList<String>> getMedicines() {
        return medicines;
    }

    
    
    
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
