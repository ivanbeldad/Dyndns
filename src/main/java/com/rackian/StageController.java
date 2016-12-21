package com.rackian;

import com.rackian.controller.SetupController;
import com.rackian.controller.StatusController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.rackian.model.dnschanger.DnsChangerService;

public class StageController {

    private DnsChangerService dnsChangerService;
    private Stage stage;
    private StatusController statusController;
    private SetupController setupController;
    private Scene sceneStatus;
    private Scene sceneSetup;
    
    public void init() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loaderStatus = new FXMLLoader(StageController.class.getClassLoader().getResource("status.fxml"));
                FXMLLoader loaderSetup = new FXMLLoader(StageController.class.getClassLoader().getResource("setup.fxml"));
                loaderStatus.setController(this.statusController);
                loaderSetup.setController(this.setupController);
                Pane rootPaneStatus = loaderStatus.load();
                Pane rootPaneSetup = loaderSetup.load();
                Scene sceneStatus = new Scene(rootPaneStatus);
                Scene sceneSetup = new Scene(rootPaneSetup);
                this.sceneStatus = sceneStatus;
                this.sceneSetup = sceneSetup;
            } catch (Exception e) {
            }
        });
    }
    
    public void launchStatus() {
        Platform.runLater(() -> {
            stage.setScene(sceneStatus);
            fitStage();
        });
    }

    public void launchSetup() {
        Platform.runLater(() -> {
            stage.setScene(sceneSetup);
            fitStage();
        });
    }
    
    private void fitStage() {
        stage.centerOnScreen();
        stage.sizeToScene();
        stage.show();
    }
    
    public Stage getStage() {
        return stage;
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
        Platform.runLater(() -> {
            if (statusController != null) {
                this.stage.setOnShown((e) -> dnsChangerService.addObserver(statusController));
            }
            
            this.stage.setOnHidden((e) -> dnsChangerService.removeObserver(statusController));
        });
    }
    
    public StatusController getStatusController() {
        return statusController;
    }
    
    public void setStatusController(StatusController statusController) {
        this.statusController = statusController;
    }
    
    public SetupController getSetupController() {
        return setupController;
    }
    
    public void setSetupController(SetupController setupController) {
        this.setupController = setupController;
        if (this.dnsChangerService != null)
            this.setupController.addObserver(this.dnsChangerService);
    }
    
    public void setDnsChangerService(DnsChangerService dnsChangerService) {
        this.dnsChangerService = dnsChangerService;
        if (this.setupController != null)
            this.setupController.addObserver(this.dnsChangerService);
    }
    
}
