package se.lu.ics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            String path = "/fxml/EmployeesView.fxml";

            // Load root layout from fxml file (on the classpath).
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            BorderPane root = loader.load();

            // Create a scene and set it on the stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            // Set the stage title and show it
            primaryStage.setTitle("Employees");
            primaryStage.show();

        } catch (Exception e) {
            // TODO: Error handling
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}