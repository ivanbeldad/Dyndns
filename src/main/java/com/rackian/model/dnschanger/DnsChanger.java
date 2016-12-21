package com.rackian.model.dnschanger;

import com.rackian.model.configuration.ConfigurationSetup;
import com.rackian.model.configuration.ConfigurationStatus;

public interface DnsChanger {
    
    ConfigurationStatus changeDns(ConfigurationSetup cs);
    
}
