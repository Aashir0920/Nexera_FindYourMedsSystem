package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CustomerDashboardController {

    @FXML
    public void goToSearchMedicines(ActionEvent event) throws Exception {
        Parent searchMedicinesPage = FXMLLoader.load(getClass().getResource("/view/SearchMedicinesScene.fxml"));
        Scene searchMedicinesScene = new Scene(searchMedicinesPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(searchMedicinesScene);
    }

    @FXML
    public void goToWishlist(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WishlistScene.fxml"));
        Parent wishlistPage = loader.load();

        Scene wishlistScene = new Scene(wishlistPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(wishlistScene);
    }


    @FXML
    public void goToPlaceOrder(ActionEvent event) throws Exception {
        Parent placeOrderPage = FXMLLoader.load(getClass().getResource("/view/PlaceOrderScene.fxml"));
        Scene placeOrderScene = new Scene(placeOrderPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(placeOrderScene);
    }

    @FXML
    public void goToFeedback(ActionEvent event) throws Exception {
        Parent feedbackPage = FXMLLoader.load(getClass().getResource("/view/FeedbackScene.fxml"));
        Scene feedbackScene = new Scene(feedbackPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(feedbackScene);
    }

    @FXML
    public void logout(ActionEvent event) throws Exception {
        // Load the Login page (LoginScene.fxml)
        Parent MainPage = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
        Scene MainScene = new Scene(MainPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(MainScene);
    }
}
