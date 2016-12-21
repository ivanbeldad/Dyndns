package com.rackian;

import com.rackian.model.configuration.ConfigurationSetup;
import com.rackian.model.dnschanger.GoogleDnsChanger;
import com.rackian.model.json.JacksonParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.rackian.controller.SetupController;
import com.rackian.controller.StatusController;
import com.rackian.model.dnschanger.DnsChanger;
import com.rackian.model.dnschanger.DnsChangerService;
import com.rackian.model.json.JsonParser;
import com.rackian.model.persistence.BasicFiler;
import com.rackian.model.persistence.Filer;
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
