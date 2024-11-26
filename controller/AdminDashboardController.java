package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class AdminDashboardController {

    @FXML
    public void goToMedicineManagement(ActionEvent event) throws Exception {
        Parent medicinePage = FXMLLoader.load(getClass().getResource("/view/MedicineInventoryManagementScene.fxml"));
        Scene medicineScene = new Scene(medicinePage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(medicineScene);
    }

    @FXML
    public void goToPharmacyManagement(ActionEvent event) throws Exception {
        Parent pharmacyPage = FXMLLoader.load(getClass().getResource("/view/PharmacyManagementScene.fxml"));
        Scene pharmacyScene = new Scene(pharmacyPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(pharmacyScene);
    }

    @FXML
    public void goToOrderManagement(ActionEvent event) throws Exception {
        Parent orderPage = FXMLLoader.load(getClass().getResource("/view/OrderHistoryScene.fxml"));
        Scene orderScene = new Scene(orderPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(orderScene);
    }

    @FXML
    public void goToFeedbackView(ActionEvent event) throws Exception {
        Parent feedbackPage = FXMLLoader.load(getClass().getResource("/view/AdminFeedbackScene.fxml"));
        Scene feedbackScene = new Scene(feedbackPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(feedbackScene);
    }

    @FXML
    public void goToApproveDrugstoreOwner(ActionEvent event) throws Exception {
        Parent approvePage = FXMLLoader.load(getClass().getResource("/view/ApproveDrugstoreOwnerScene.fxml"));
        Scene approveScene = new Scene(approvePage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(approveScene);
    }

    @FXML
    public void logout(ActionEvent event) throws Exception {
        // Redirect to the main page
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
        Scene mainScene = new Scene(mainPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(mainScene);
    }
}
