package rackian.com.model.dnschanger;

import rackian.com.model.configuration.ConfigurationSetup;
import rackian.com.model.configuration.ConfigurationStatus;
import rackian.com.model.configuration.Status;
import sun.misc.BASE64Encoder;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class GoogleDnsChanger implements DnsChanger {
    
    @Override
    public ConfigurationStatus changeDns(ConfigurationSetup cs) {
        ConfigurationStatus configurationStatus = new ConfigurationStatus();
        try {
            URL url = new URL("https://domains.google.com/nic/update?hostname=" + cs.getDomain());
            String auth = new BASE64Encoder().encode((cs.getUsername() + ":" + cs.getPassword()).getBytes());
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + auth);
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String l;
            String line = "";
            while ((l = br.readLine()) != null) {
                line += l;
            }
            String[] status_ip = line.split(" ");
            if (status_ip.length >= 1) {
                String status = line.split(" ")[0];
                configurationStatus.setStatus(getStatus(status));
            }
            if (status_ip.length >= 2) {
                String newIp = line.split(" ")[1];
                configurationStatus.setCurrentIp(newIp);
            }
        } catch (Exception ex) {}
        return configurationStatus;
    }
    
    private Status getStatus(String googleStatus) {
        switch (googleStatus) {
            case "good":
                return Status.OK;
            case "nochg":
                return Status.NO_CHANGES;
            case "nohost":
                return Status.NO_HOST;
            case "badauth":
                return Status.BAD_AUTH;
            case "notfqdn":
                return Status.NOT_FQDN;
            case "badagent":
                return Status.BAD_AGENT;
            case "abuse":
                return Status.ABUSE;
            case "911":
                return Status.UNKNOWN;
            default:
                return Status.UNKNOWN;
        }
    }

}
