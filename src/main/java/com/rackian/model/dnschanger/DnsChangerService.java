package com.rackian.model.dnschanger;

import com.rackian.model.Observer;
import com.rackian.model.Subject;
import com.rackian.model.configuration.ConfigurationSetup;
import com.rackian.model.configuration.ConfigurationStatus;
import com.rackian.model.configuration.Status;
import com.rackian.model.json.JacksonParser;
import com.rackian.model.json.JsonParser;
import com.rackian.model.persistence.BasicFiler;
import com.rackian.model.persistence.Filer;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DnsChangerService implements Runnable, Subject, Observer {
    
    private List<Observer> observers;
    private DnsChanger dnsChanger;
    private ConfigurationSetup configurationSetup;
    private final Object lock = new Object();
    
    public DnsChangerService(DnsChanger dnsChanger, ConfigurationSetup configurationSetup) {
        this.observers = new ArrayList<>();
        this.dnsChanger = dnsChanger;
        this.configurationSetup = configurationSetup;
    }
    
    @Override
    public void run() {
        ConfigurationStatus configurationStatus = loadConfigurationStatus();
        ConfigurationStatus responseStatus;
        while (true) {
            try {
                responseStatus = dnsChanger.changeDns(configurationSetup);
                configurationStatus.setStatus(responseStatus.getStatus());
                configurationStatus.setCurrentIp(responseStatus.getCurrentIp());
                if (responseStatus.getStatus() == Status.OK || responseStatus.getStatus() == Status.NO_CHANGES) {
                    Calendar date = new GregorianCalendar();
                    configurationStatus.setLastUpdate(date.getTimeInMillis());
                }
                saveConfigurationStatus(configurationStatus);
                Platform.runLater(() -> notifyObservers());
                synchronized (lock) { lock.wait(configurationSetup.getResfreshTimeInSeconds()); }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private boolean saveConfigurationStatus(ConfigurationStatus configurationStatus) {
        Filer filer = new BasicFiler(ConfigurationStatus.file);
        JsonParser<ConfigurationStatus> parser = new JacksonParser<>();
        return filer.save(parser.objectToJson(configurationStatus));
    }
    
    private ConfigurationStatus loadConfigurationStatus() {
        initConfigurationStatus();
        JsonParser<ConfigurationStatus> parser = new JacksonParser<>();
        Filer filer = new BasicFiler(ConfigurationStatus.file);
        return parser.jsonToObject(filer.getContent(), ConfigurationStatus.class);
    }
    
    private void initConfigurationStatus() {
        if (!ConfigurationStatus.file.exists()) {
            JsonParser<ConfigurationStatus> parser = new JacksonParser<>();
            Filer filer = new BasicFiler(ConfigurationStatus.file);
            filer.save(parser.objectToJson(new ConfigurationStatus()));
        }
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
        this.observers.forEach((o) -> o.notified(null));
    }
    
    @Override
    public void notified(Object args) {
        if (args instanceof ConfigurationSetup) {
            this.configurationSetup = (ConfigurationSetup) args;
            synchronized (lock) { lock.notify(); }
        }
    }
    
}
