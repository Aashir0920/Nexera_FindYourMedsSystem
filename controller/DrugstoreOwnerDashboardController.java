package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.event.ActionEvent;

public class DrugstoreOwnerDashboardController {

	// Event Listener on Button.onAction
	@FXML
	public void managePharmacy(ActionEvent event) throws Exception {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DrugstoreManagePharmacy.fxml"));
	    Parent mainPage = loader.load();

	    // Pass logged-in owner ID to the next controller
	    DrugstoreManagePharmacyController controller = loader.getController();
	    controller.setLoggedInOwner(DrugstoreOwnerLoginController.getLoggedInOwnerId());

	    Scene mainScene = new Scene(mainPage);
	    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    currentStage.setScene(mainScene);
	}
	// Event Listener on Button.onAction
	@FXML
	public void viewOrders(ActionEvent event) throws Exception {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DrugstoreOrdersPage.fxml"));
	    Parent ordersPage = loader.load();

	    DrugstoreOrdersPageController ordersController = loader.getController();
	    ordersController.setLoggedInOwner(DrugstoreOwnerLoginController.getLoggedInOwnerId());

	    Scene mainScene = new Scene(ordersPage);
	    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    currentStage.setScene(mainScene);
	}

	// Event Listener on Button.onAction
	@FXML
	public void viewFeedback(ActionEvent event) throws Exception {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DrugstoreFeedbackPage.fxml"));
	    Parent feedbackPage = loader.load();

	    DrugstoreFeedbackController feedbackController = loader.getController();
	    feedbackController.setLoggedInOwner(DrugstoreOwnerLoginController.getLoggedInOwnerId());

	    Scene mainScene = new Scene(feedbackPage);
	    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    currentStage.setScene(mainScene);
	}

	
	@FXML
    public void logout(ActionEvent event) throws Exception {
        // Redirect to the main page
		Parent mainPage = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
        Scene mainScene = new Scene(mainPage);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(mainScene);
    }
	public void setLoggedInOwner(int loggedInOwnerID) {
		// TODO Auto-generated method stub
		
	}
}
