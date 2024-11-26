package controller;

import sql_handler.DBStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;

import java.sql.SQLException;

public class DrugstoreManagePharmacyController {

    @FXML
    private TableView<ObservableList<String>> pharmacyTable;

    private ObservableList<ObservableList<String>> pharmacies;

    private String loggedInOwnerId;

    @FXML
    public void initialize() {
        if (pharmacyTable.getColumns().isEmpty()) {
            String[] columnNames = {"Pharmacy Name", "Location", "Contact Info"};
            for (int i = 0; i < columnNames.length; i++) {
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
                int columnIndex = i;
                column.setCellValueFactory(param ->
                        new javafx.beans.property.SimpleStringProperty(param.getValue().get(columnIndex))
                );
                pharmacyTable.getColumns().add(column);
            }

            // Add Open button column
            TableColumn<ObservableList<String>, Void> actionColumn = new TableColumn<>("Action");
            actionColumn.setCellFactory(param -> new TableCell<>() {
                private final Button openButton = new Button("Open");

                {
                    openButton.setOnAction(event -> {
                        ObservableList<String> selectedPharmacy = getTableView().getItems().get(getIndex());
                        openManageMedicines(selectedPharmacy);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : openButton);
                }
            });

            pharmacyTable.getColumns().add(actionColumn);
        }

        setPharmacies(FXCollections.observableArrayList());
    }

    public ObservableList<ObservableList<String>> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(ObservableList<ObservableList<String>> pharmacies) {
        this.pharmacies = pharmacies;
    }
    public void loadPharmacies() {
        try {
            pharmacies = DBStorage.getInstance().getPharmaciesByOwner(loggedInOwnerId);
            pharmacyTable.setItems(pharmacies);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load pharmacies.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    public void addPharmacy(ActionEvent event) throws Exception {
        Parent addPharmacyPage = FXMLLoader.load(getClass().getResource("/view/DrugstoreAddPharmacy.fxml"));
        Scene addPharmacyScene = new Scene(addPharmacyPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(addPharmacyScene);
    }

    @FXML
    public void deletePharmacy() {
        ObservableList<String> selectedPharmacy = pharmacyTable.getSelectionModel().getSelectedItem();
        if (selectedPharmacy != null) {
            String pharmacyName = selectedPharmacy.get(0);
            try {
                boolean deleted = DBStorage.getInstance().deletePharmacyAndMedicines(pharmacyName, loggedInOwnerId);
                if (deleted) {
                    pharmacies.remove(selectedPharmacy);
                    showAlert("Success", "Pharmacy deleted successfully.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Failure", "Failed to delete pharmacy.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while deleting the pharmacy.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Warning", "Please select a pharmacy to delete.", Alert.AlertType.WARNING);
        }
    }

    private void openManageMedicines(ObservableList<String> pharmacy) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DrugstoreManageMedicines.fxml"));
            Parent manageMedicinesPage = loader.load();

            DrugstoreManageMedicinesController controller = loader.getController();
            int pharmacyID = DBStorage.getInstance().getPharmacyId(pharmacy.get(0), loggedInOwnerId);
            if (pharmacyID != -1) {
                controller.setPharmacyID(pharmacyID);
            }

            Stage currentStage = (Stage) pharmacyTable.getScene().getWindow();
            currentStage.setScene(new Scene(manageMedicinesPage));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLoggedInOwner(String ownerId) {
        this.loggedInOwnerId = ownerId;
        loadPharmacies();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        Parent dashboardPage = FXMLLoader.load(getClass().getResource("/view/DrugstoreOwnerDashboard.fxml"));
        Scene dashboardScene = new Scene(dashboardPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(dashboardScene);
    }
    
}
