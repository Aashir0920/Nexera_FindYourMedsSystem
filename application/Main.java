package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Correct FXML loading
        	//Parent root = FXMLLoader.load(getClass().getResource("/application/view/MainPage.fxml"));

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml")); 
            
            // Create the scene
            Scene scene = new Scene(root);
            
            // Set the stage
            primaryStage.setTitle("Find Your Meds System");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
