package rackian.com;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import rackian.com.controller.SetupController;
import rackian.com.controller.StatusController;
import rackian.com.model.configuration.ConfigurationSetup;
import rackian.com.model.dnschanger.DnsChanger;
import rackian.com.model.dnschanger.DnsChangerService;
import rackian.com.model.dnschanger.GoogleDnsChanger;
import rackian.com.model.json.JacksonParser;
import rackian.com.model.json.JsonParser;
import rackian.com.model.persistence.BasicFiler;
import rackian.com.model.persistence.Filer;
import java.io.File;

public class App extends Application {
    
    private static StageController stageController = createStageController();
    
    public static void main(String[] args) throws Exception {
        DnsChangerService dnsChangerService = createDnsChangerService();
        stageController.setDnsChangerService(dnsChangerService);
        Thread dnsChangerThread = new Thread(dnsChangerService);
        Thread fxThread = new Thread(App::launch);
        fxThread.start();
        dnsChangerThread.start();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        stageController.setStage(primaryStage);
        stageController.init();
        SystemTrayController stc = new SystemTrayController(stageController);
        stc.init();
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
        File file = new File("src/config/setup.json");
        if (!file.exists()) {
            stageController.launchSetup();
        }
    }
    
    private static StageController createStageController() {
        StageController stageController = new StageController();
        SetupController setupController = new SetupController(stageController);
        StatusController statusController = new StatusController(stageController);
        stageController.setSetupController(setupController);
        stageController.setStatusController(statusController);
        return stageController;
    }
    
    private static DnsChangerService createDnsChangerService() {
        ConfigurationSetup configurationSetup = createSetup();
        DnsChanger dnsChanger = new GoogleDnsChanger();
        DnsChangerService dnsChangerService = new DnsChangerService(dnsChanger, configurationSetup);
        return dnsChangerService;
    }
    
    private static ConfigurationSetup createSetup() {
        ConfigurationSetup configurationSetup;
        Filer filer = new BasicFiler(ConfigurationSetup.file);
        JsonParser<ConfigurationSetup> parser = new JacksonParser<>();
        if (!ConfigurationSetup.file.exists()) {
            configurationSetup = new ConfigurationSetup();
        } else {
            configurationSetup = parser.jsonToObject(filer.getContent(), ConfigurationSetup.class);
        }
        return configurationSetup;
    }
    
}
