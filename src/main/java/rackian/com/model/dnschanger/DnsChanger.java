package rackian.com.model.dnschanger;

import rackian.com.model.configuration.ConfigurationSetup;

public interface DnsChanger {
    
    boolean changeDns(ConfigurationSetup cs);
    
}
