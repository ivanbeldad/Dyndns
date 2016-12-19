package rackian.com;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class StageController {
    
    public static void launchStatus(Stage stage) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(StageController.class.getClassLoader().getResource("status.fxml"));
                Pane rootPane = loader.load();
                stage.setScene(new Scene(rootPane));
                stage.centerOnScreen();
                stage.sizeToScene();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    public static void launchSetup(Stage stage) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(StageController.class.getClassLoader().getResource("setup.fxml"));
                Pane rootPane = loader.load();
                stage.setScene(new Scene(rootPane));
                stage.centerOnScreen();
                stage.sizeToScene();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
}
