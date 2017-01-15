package com.rackian.model.configuration;

import com.rackian.App;

import java.util.ResourceBundle;

public enum Status {
    
    OK ("ok", "OK", "okMsg"),
    NO_CHANGES ("noChanges", "OK", "noChangesMsg"),
    NO_HOST ("noHost", "ERROR", "noHostMsg"),
    NOT_FQDN ("notFQDN", "ERROR", "notFQDNMsg"),
    BAD_AUTH ("badAuth", "ERROR", "badAuthMsg"),
    ABUSE ("abuse", "WARNING", "abuseMsg"),
    BAD_AGENT ("badAgent", "ERROR", "badAgentMsg"),
    SERVICE_TMP_UNAVAILABLE ("serviceTmpUnavailable", "WARNING", "serviceTmpUnavailableMsg"),
    UNKNOWN ("unknown", "ERROR", "unknownMsg");
    
    private final String name;
    private final String message;
    private final String type;
    
    Status(String name, String type, String message) {
        ResourceBundle bundle = ResourceBundle.getBundle("view.bundles.statusMessages", App.locale);
        this.name = bundle.getString(name);
        this.type = type;
        this.message = bundle.getString(message);
    }
    
    public String getName() {
        return name;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getType() {
        return type;
    }
    
}
