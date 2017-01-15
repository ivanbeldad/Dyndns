package com.rackian.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rackian.StageController;
import com.rackian.model.Observer;
import com.rackian.model.configuration.ConfigurationStatus;
import com.rackian.model.ipchecker.IpFounder;
import com.rackian.model.ipchecker.Ipify;
import com.rackian.model.json.JacksonParser;
import com.rackian.model.json.JsonParser;
import com.rackian.model.persistence.BasicFiler;
import com.rackian.model.persistence.Filer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatusController implements Initializable, Observer {
    
    @JsonIgnore
    private StageController stageController;
    @FXML
    private Label currentIp;
    @FXML
    private Label realIp;
    @FXML
    private Label lastUpdate;
    @FXML
    private Label status;
    @FXML
    private Label statusMessage;
    
    public StatusController(StageController stageController) {
        this.stageController = stageController;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfigurationStatus cs;
        if ((cs = getConfigurationFromFile()) != null) {
            fillFields(cs);
        }
    }
    
    @FXML
    private void openConfiguration() throws IOException {
        stageController.launchSetup();
    }
    
    private ConfigurationStatus getConfigurationFromFile() {
        if (!ConfigurationStatus.file.exists()) return null;
        Filer filer = new BasicFiler(ConfigurationStatus.file);
        JsonParser<ConfigurationStatus> parser = new JacksonParser<>();
        return parser.jsonToObject(filer.getContent(), ConfigurationStatus.class);
    }
    
    private void fillFields(ConfigurationStatus cs) {
        currentIp.setText("Undefined");
        realIp.setText("Undefined");
        lastUpdate.setText("Undefined");
        status.setText("Undefined");
        statusMessage.setText("");
        currentIp.setText(cs.getCurrentIp());
        if (cs.getCurrentIp() == null) {
            currentIp.setText("Undefined");
        }
        realIp.setText(getRealIp());
        lastUpdate.setText(cs.getLastUpdateDate().toString());
        if (cs.getLastUpdate() == 0) {
            lastUpdate.setText("Undefined");
        }
        switch (cs.getStatus().getType()) {
            case "OK":
                status.setTextFill(Color.color(0.4, 0.8, 0.4));
                break;
            case "WARNING":
                status.setTextFill(Color.color(0.95, 0.25, 0.3));
                break;
            case "ERROR":
                status.setTextFill(Color.color(0.95, 0.25, 0.3));
                break;
            default:
                status.setTextFill(Color.color(0.95, 0.25, 0.3));
        }
        status.setText(cs.getStatus().getName());
        statusMessage.setText(cs.getStatus().getMessage());
    }
    
    private String getRealIp() {
        IpFounder ipFounder = new Ipify();
        return ipFounder.getHostIp();
    }
    
    @Override
    public void notified(Object args) {
        fillFields(getConfigurationFromFile());
    }
    
}
