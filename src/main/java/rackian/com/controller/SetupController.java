package rackian.com.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import rackian.com.StageController;
import rackian.com.model.Observer;
import rackian.com.model.Subject;
import rackian.com.model.configuration.ConfigurationSetup;
import rackian.com.model.json.JacksonParser;
import rackian.com.model.json.JsonParser;
import rackian.com.model.persistence.BasicFiler;
import rackian.com.model.persistence.Filer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SetupController implements Initializable, Subject {
    
    @JsonIgnore
    private List<Observer> observers;
    @JsonIgnore
    private StageController stageController;
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
    @FXML
    private Hyperlink statusLink;
    
    public SetupController(StageController stageController) {
        this.stageController = stageController;
        this.observers = new ArrayList<>();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> username.getParent().requestFocus());
        ConfigurationSetup cs;
        if ((cs = getConfigurationFromFile(new File("src/config/setup.json"))) != null) {
            fillFields(cs);
        }

        refreshTimeInMinutes.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField field = (TextField) event.getSource();
                if (field.getText().length() > 5) {
                    event.consume();
                }
                if (!event.getCharacter().matches("[0-9]")) {
                    event.consume();
                }
            }
        });
    }
    
    @FXML
    private boolean save() {
        if (!checkFields()) return false;
        trimFields();
        if (!toFile()) return false;
        notifyObservers();
        return true;
    }
    
    @FXML
    private void openStatus() throws IOException {
        stageController.launchStatus();
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
    
    private void trimFields() {
        username.setText(username.getText().trim());
        password.setText(password.getText().trim());
        domain.setText(domain.getText().trim());
        refreshTimeInMinutes.setText(refreshTimeInMinutes.getText().trim());
    }
    
    private boolean toFile() {
        Filer filer = new BasicFiler(ConfigurationSetup.file);
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
        refreshTimeInMinutes.setText(Integer.toString(cs.getResfreshTimeInSeconds()/60));
    }
    
    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }
    
    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        this.observers.forEach((o) -> o.notified(createConfiguration()));
    }
    
}
