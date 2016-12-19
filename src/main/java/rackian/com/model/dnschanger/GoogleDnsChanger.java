package rackian.com.model.dnschanger;

import rackian.com.model.configuration.ConfigurationSetup;
import sun.misc.BASE64Encoder;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class GoogleDnsChanger implements DnsChanger {
    
    @Override
    public boolean changeDns(ConfigurationSetup cs) {
        try {
            URL url = new URL("https://domains.google.com/nic/update?hostname=" + cs.getDomain());
            String auth = new BASE64Encoder().encode((cs.getUsername() + ":" + cs.getPassword()).getBytes());
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + auth);
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            String status = line.split(" ")[0];
            String newIp = line.split(" ")[1];
            switch (status) {
                case "good":
                case "nochg":
                    return true;
                case "nohost":
                case "badauth":
                case "notfqdn":
                case "badagent":
                case "abuse":
                case "911":
                    return false;
                default:
                    return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
    
}
