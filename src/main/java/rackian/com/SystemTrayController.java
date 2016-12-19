package rackian.com;

import javafx.stage.Stage;
import java.awt.*;
import java.awt.event.ActionListener;

public class SystemTrayController {
    
    private Stage stage;
    
    public SystemTrayController(Stage stage) {
        this.stage = stage;
    }
    
    public void init() {
        TrayIcon trayIcon;
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("src/main/resources/logo16.png");
            PopupMenu popup = new PopupMenu();
            addItemToMenu(popup, "Current Status", (e) -> StageController.launchStatus(stage));
            addItemToMenu(popup, "Configuration", (e) -> StageController.launchSetup(stage));
            addItemToMenu(popup, "Exit", (e) -> System.exit(0));
            trayIcon = new TrayIcon(image, "DynDns", popup);
            trayIcon.addActionListener((e) -> StageController.launchSetup(stage));
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
        } else {
            System.exit(1);
        }
    }
    
    private void addItemToMenu(PopupMenu menu, String text, ActionListener listener) {
        MenuItem menuItem = new MenuItem(text);
        menuItem.addActionListener(listener);
        menu.add(menuItem);
    }
    
}
