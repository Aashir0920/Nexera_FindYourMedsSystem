package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class DrugstoreOwnerLoginOrRegisterController {

    @FXML
    public void goToLogin(ActionEvent event) throws Exception {
        Parent loginPage = FXMLLoader.load(getClass().getResource("/view/DrugstoreOwnerLoginPage.fxml"));
        Scene loginScene = new Scene(loginPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(loginScene);
    }

    @FXML
    public void goToRegister(ActionEvent event) throws Exception {
        Parent registerPage = FXMLLoader.load(getClass().getResource("/view/DrugstoreOwnerRegistrationPage.fxml"));
        Scene registerScene = new Scene(registerPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(registerScene);
    }

    @FXML
    public void goBackToMainPage(ActionEvent event) throws Exception {
        Parent mainPage = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));  // Replace with the correct main page FXML
        Scene mainScene = new Scene(mainPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(mainScene);
    }
}
