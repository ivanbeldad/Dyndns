package rackian.com;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class App extends Application {
    
    public static void main(String[] args) throws Exception {
        App.launch();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
        primaryStage.setTitle("DynDns");
        primaryStage.getIcons().addAll(
                new Image("file:src/main/resources/logo16.png"),
                new Image("file:src/main/resources/logo30.png"),
                new Image("file:src/main/resources/logo32.png"),
                new Image("file:src/main/resources/logo40.png"),
                new Image("file:src/main/resources/logo48.png"),
                new Image("file:src/main/resources/logo64.png")
        );
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
