package com.rackian.model.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;

public class ConfigurationSetup {
    
    @JsonIgnore
    public static final File file = new File(System.getProperty("user.home").concat("/Dyndns/setup.json"));
    private String domain;
    private String username;
    private String password;
    private int resfreshTimeInSeconds;
    
    public ConfigurationSetup() {
    }
    
    public ConfigurationSetup(String domain, String username, String password, int resfreshTimeInSeconds) {
        this.domain = domain;
        this.username = username;
        this.password = password;
        this.resfreshTimeInSeconds = resfreshTimeInSeconds;
    }
    
    public String getDomain() {
        return domain;
    }
    
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getResfreshTimeInSeconds() {
        return resfreshTimeInSeconds;
    }
    
    public void setResfreshTimeInSeconds(int resfreshTimeInSeconds) {
        this.resfreshTimeInSeconds = resfreshTimeInSeconds;
    }
    
    @Override
    public String toString() {
        return "ConfigurationSetup{" +
                "\n\tdomain = " + domain +
                "\n\tusername = " + username +
                "\n\tpassword = " + password +
                "\n\tresfreshTimeInSeconds = " + resfreshTimeInSeconds +
                '}';
    }
    
}
