package rackian.com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    
    public static void main(String[] args) throws Exception {
        App.launch();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(getClass().getClassLoader().getResource("status.fxml"));
        Pane rootPane = fxml.load();
        Scene scene = new Scene(rootPane);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
