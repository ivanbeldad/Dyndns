package rackian.com.controller;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import rackian.com.model.configuration.ConfigurationSetup;
import rackian.com.model.json.JacksonParser;
import rackian.com.model.json.JsonParser;
import rackian.com.model.persistence.BasicFiler;
import rackian.com.model.persistence.Filer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SetupController implements Initializable {
    
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField domain;
    @FXML
    private TextField refreshTimeInSeconds;
    @FXML
    private Button saveButton;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfigurationSetup cs;
        if ((cs = getConfigurationFromFile(new File("src/config/setup.json"))) != null) {
            fillFields(cs);
        }
    }
    
    @FXML
    private boolean save() {
        if (!checkFields()) return false;
        cleanFields();
        toFile();
        return false;
    }
    
    private boolean checkFields() {
        if (username.getText().trim().equals("")) return false;
        if (password.getText().trim().equals("")) return false;
        if (domain.getText().trim().equals("")) return false;
        if (!refreshTimeInSeconds.getText().trim().matches("^[0-9]+$")) return false;
        return true;
    }
    
    private void cleanFields() {
        username.setText(username.getText().trim());
        password.setText(password.getText().trim());
        domain.setText(domain.getText().trim());
        refreshTimeInSeconds.setText(refreshTimeInSeconds.getText().trim());
    }
    
    private boolean toFile() {
        Filer filer = new BasicFiler(new File("src/config/setup.json"));
        ConfigurationSetup cs = createConfiguration();
        JsonParser<ConfigurationSetup> parser = new JacksonParser<>();
        if (filer.save(parser.objectToJson(cs))) {
            return true;
        } else {
            return false;
        }
    }
    
    private ConfigurationSetup createConfiguration() {
        ConfigurationSetup cs = new ConfigurationSetup();
        cs.setDomain(domain.getText());
        cs.setUsername(username.getText());
        cs.setPassword(password.getText());
        cs.setResfreshTimeInSeconds(Integer.parseInt(refreshTimeInSeconds.getText()));
        return cs;
    }
    
    private ConfigurationSetup getConfigurationFromFile(File file) {
        if (!file.exists()) return null;
        Filer filer = new BasicFiler(file);
        JsonParser<ConfigurationSetup> parser = new JacksonParser<>();
        return parser.jsonToObject(filer.getContent(), ConfigurationSetup.class);
    }
    
    private void fillFields(ConfigurationSetup cs) {
        username.setText(cs.getUsername());
        password.setText(cs.getPassword());
        domain.setText(cs.getDomain());
        refreshTimeInSeconds.setText(Integer.toString(cs.getResfreshTimeInSeconds()));
    }
    
}