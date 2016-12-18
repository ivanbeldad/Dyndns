package rackian.com.model.ipchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Ipify implements IpFounder {
    
    private static final String IPIFY_URL = "https://api.ipify.org";
    
    public String getHostIp() {
        try {
            URL url = new URL(IPIFY_URL);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStreamReader isr = new InputStreamReader(http.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;
            String content = "";
            while ((line = br.readLine()) != null) {
                content += line;
            }
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
