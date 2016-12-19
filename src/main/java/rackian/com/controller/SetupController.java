package rackian.com.controller;

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
    private TextField refreshTimeInMinutes;
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
        if (!toFile()) return false;
        return true;
    }
    
    private boolean checkFields() {
        if (username.getText().trim().equals("")) {
            username.setStyle("-fx-border-color: red;");
            return false;
        } else {
            username.setStyle("-fx-border-color: lawngreen;");
        }
        if (password.getText().trim().equals("")) {
            password.setStyle("-fx-border-color: red;");
            return false;
        } else {
            password.setStyle("-fx-border-color: lawngreen;");
        }
        if (domain.getText().trim().equals("")) {
            domain.setStyle("-fx-border-color: red;");
            return false;
        } else {
            domain.setStyle("-fx-border-color: lawngreen;");
        }
        if (!refreshTimeInMinutes.getText().trim().matches("^[0-9]+$")) {
            refreshTimeInMinutes.setStyle("-fx-border-color: red;");
            return false;
        } else {
            refreshTimeInMinutes.setStyle("-fx-border-color: lawngreen;");
        }
        return true;
    }
    
    private void cleanFields() {
        username.setText(username.getText().trim());
        password.setText(password.getText().trim());
        domain.setText(domain.getText().trim());
        refreshTimeInMinutes.setText(refreshTimeInMinutes.getText().trim());
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
        cs.setResfreshTimeInSeconds(Integer.parseInt(refreshTimeInMinutes.getText()) * 60);
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
        refreshTimeInMinutes.setText(Integer.toString(cs.getResfreshTimeInSeconds()));
    }
    
}
