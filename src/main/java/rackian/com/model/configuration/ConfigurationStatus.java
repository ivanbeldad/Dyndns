package rackian.com.model.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ConfigurationStatus {

    private String currentIp;
    private String realIp;
    private Status status;
    private long lastUpdate;
    
    public ConfigurationStatus() {
    }
    
    public ConfigurationStatus(String currentIp, String realIp, Status status, long lastUpdate) {
        this.currentIp = currentIp;
        this.realIp = realIp;
        this.status = status;
        this.lastUpdate = lastUpdate;
    }
    
    public ConfigurationStatus(String currentIp, String realIp, Status status, MyDate lastUpdate) {
        this.currentIp = currentIp;
        this.realIp = realIp;
        this.status = status;
        this.lastUpdate = lastUpdate.getTimeInMillis();
    }
    
    public String getCurrentIp() {
        return currentIp;
    }
    
    public void setCurrentIp(String currentIp) {
        this.currentIp = currentIp;
    }
    
    public String getRealIp() {
        return realIp;
    }
    
    public void setRealIp(String realIp) {
        this.realIp = realIp;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public long getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    @JsonIgnore
    public MyDate getLastUpdateDate() {
        return new MyDate(lastUpdate);
    }
    
    public void setLastUpdateDate(MyDate lastUpdate) {
        this.lastUpdate = lastUpdate.getTimeInMillis();
    }
    
    @Override
    public String toString() {
        return "ConfigurationStatus{" +
                "\n\tcurrentIp = " + currentIp +
                "\n\trealIp = " + realIp +
                "\n\tstatus = " + status +
                "\n\tlastUpdate = " + lastUpdate +
                "\n}";
    }
    
}
