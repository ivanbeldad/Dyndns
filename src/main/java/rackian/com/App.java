package rackian.com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.misc.BASE64Encoder;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Hello world!
 */
public class App extends Application {
//    public static void main(String[] args) throws IOException {
//        ConfigurationSetup csetup = new ConfigurationSetup("domain.rackian.com", "ivan", "noteladigo", 60);
//        ConfigurationStatus cstatus = new ConfigurationStatus("11.11.11.11", "12.12.12.12", Status.OK, new MyDate());
//        JsonParser<ConfigurationSetup> parserSetup = new JacksonParser<>();
//        JsonParser<ConfigurationStatus> parserStatus = new JacksonParser<>();
//        Filer filerSetup = new BasicFiler("src/config/setup.json");
//        Filer filerStatus = new BasicFiler("src/config/status.json");
//        System.out.println(parserSetup.objectToJson(csetup));
//        System.out.println(parserStatus.objectToJson(cstatus));
//        filerSetup.save(parserSetup.objectToJson(csetup));
//        filerStatus.save(parserStatus.objectToJson(cstatus));
//    }
    
    public static void main(String[] args) throws Exception {
//        App a = new App();
//        FXMLLoader loader = new FXMLLoader(a.getClass().getResource("view/setup.fxml"));
//        Scene scene = new Scene(loader.getRoot());
//        Stage stage = new Stage();
        App.launch();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(getClass().getClassLoader().getResource("setup.fxml"));
        Pane rootPane = fxml.load();
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMaxHeight(primaryStage.getHeight());
    }
    
    public static void changeDns() throws IOException {
        URL url = new URL("https://domains.google.com/nic/update?hostname=dyndns.rackian.com");
        String auth = new BASE64Encoder().encode("JgUVsjq4R6QUtg2g:AsHDotYMLYxeTPZo".getBytes());
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic " + auth);
        InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }
}
