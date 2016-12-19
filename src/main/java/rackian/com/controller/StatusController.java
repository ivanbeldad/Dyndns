package rackian.com.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import rackian.com.StageController;
import rackian.com.model.configuration.ConfigurationStatus;
import rackian.com.model.ipchecker.IpFounder;
import rackian.com.model.ipchecker.Ipify;
import rackian.com.model.json.JacksonParser;
import rackian.com.model.json.JsonParser;
import rackian.com.model.persistence.BasicFiler;
import rackian.com.model.persistence.Filer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatusController implements Initializable {
    
    @FXML
    private Label currentIp;
    @FXML
    private Label realIp;
    @FXML
    private Label lastUpdate;
    @FXML
    private Label status;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfigurationStatus cs;
        if ((cs = getConfigurationFromFile(new File("src/config/status.json"))) != null) {
            fillFields(cs);
        }
    }
    
    @FXML
    private void openConfiguration() throws IOException {
        StageController.launchSetup((Stage)currentIp.getScene().getWindow());
    }
    
    private ConfigurationStatus getConfigurationFromFile(File file) {
        if (!file.exists()) return null;
        Filer filer = new BasicFiler(file);
        JsonParser<ConfigurationStatus> parser = new JacksonParser<>();
        return parser.jsonToObject(filer.getContent(), ConfigurationStatus.class);
    }
    
    private void fillFields(ConfigurationStatus cs) {
        currentIp.setText(cs.getCurrentIp());
        realIp.setText(getRealIp());
        lastUpdate.setText(cs.getLastUpdateDate().toString());
        status.setText(cs.getStatus().toString());
        switch (cs.getStatus()) {
            case OK:
                status.setTextFill(Color.color(0.4, 0.8, 0.4));
                break;
            case FATAL:
                status.setTextFill(Color.color(0.95, 0.25, 0.3));
                break;
            case WARNING:
                status.setTextFill(Color.color(1, 0.8, 0.1));
                break;
        }
    }
    
    private String getRealIp() {
        IpFounder ipFounder = new Ipify();
        return ipFounder.getHostIp();
    }
    
}
