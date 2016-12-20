package rackian.com.model.dnschanger;

import rackian.com.model.configuration.ConfigurationSetup;
import rackian.com.model.configuration.ConfigurationStatus;

public interface DnsChanger {
    
    ConfigurationStatus changeDns(ConfigurationSetup cs);
    
}
