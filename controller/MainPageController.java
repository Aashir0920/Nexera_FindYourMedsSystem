package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MainPageController {

    @FXML
    public void goToCustomerLogin(ActionEvent event) throws Exception {
        Parent customerLoginPage = FXMLLoader.load(getClass().getResource("/view/LoginScene.fxml"));
        Scene customerLoginScene = new Scene(customerLoginPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(customerLoginScene);
    }

    @FXML
    public void goToDrugstoreOwnerLogin(ActionEvent event) throws Exception {
        Parent drugstoreLoginPage = FXMLLoader.load(getClass().getResource("/view/DrugstoreOwnerLoginOrRegisterPage.fxml"));
        Scene drugstoreLoginScene = new Scene(drugstoreLoginPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(drugstoreLoginScene);
    }

    @FXML
    public void goToAdminLogin(ActionEvent event) throws Exception {
        Parent adminLoginPage = FXMLLoader.load(getClass().getResource("/view/AdminLoginPage.fxml"));
        Scene adminLoginScene = new Scene(adminLoginPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(adminLoginScene);
    }
}
