package rackian.com;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.File;

public class App extends Application {
    
    public static void main(String[] args) throws Exception {
        App.launch();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest((e) -> {
            e.consume();
            primaryStage.hide();
        });
        SystemTrayController stc = new SystemTrayController(primaryStage);
        stc.init();
        File file = new File("src/config/setup.json");
        if (!file.exists()) {
            StageController.launchSetup(primaryStage);
        }
    }

}
