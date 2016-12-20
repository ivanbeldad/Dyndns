package rackian.com.model.configuration;

public enum Status {
    
    OK ("OK", "OK", "Ip changed"),
    NO_CHANGES ("NO_CHANGES", "OK", "Without changes"),
    NO_HOST ("NO_HOST", "ERROR", "Domain does not exists. Check domain configuration."),
    NOT_FQDN ("NOT_FQDN", "ERROR", "Bad FQDN. Check domain configuration."),
    BAD_AUTH ("BAD_AUTH", "ERROR", "Authentication error. Check username and password configuration."),
    ABUSE ("ABUSE", "WARNING", "Too much tries. Access blocked. Check after some hours if the problem is solved."),
    BAD_AGENT ("BAD_AGENT", "ERROR", "Authentication error. Check username and password configuration."),
    SERVICE_TMP_UNAVAILABLE ("SERVICE_TMP_UNAVAILABLE", "WARNING", "Service temporaly unavailable. Check in a while if the problem was solved."),
    UNKNOWN ("UNKNOWN", "ERROR", "Unknow error. Contact administrator for more information.");
    
    private final String name;
    private final String message;
    private final String type;
    
    Status(String name, String type, String message) {
        this.name = name;
        this.type = type;
        this.message = message;
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
