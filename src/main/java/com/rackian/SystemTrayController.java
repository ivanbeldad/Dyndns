package com.rackian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class SystemTrayController {
    
    private StageController stageController;
    
    public SystemTrayController(StageController stageController) {
        this.stageController = stageController;
    }
    
    public void init() {
        TrayIcon trayIcon;
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("img/logo16.png"));
//            Image image = Toolkit.getDefaultToolkit().getImage("src/main/resources/logo16.png");
            ResourceBundle bundle = ResourceBundle.getBundle("view.bundles.tray", App.locale);
            PopupMenu popup = new PopupMenu();
            addItemToMenu(popup, bundle.getString("status"), (e) -> stageController.launchStatus());
            addItemToMenu(popup, bundle.getString("configuration"), (e) -> stageController.launchSetup());
            addItemToMenu(popup, bundle.getString("exit"), (e) -> System.exit(0));
            trayIcon = new TrayIcon(image.getImage(), "DynDns", popup);
            trayIcon.addActionListener((e) -> stageController.launchStatus());
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
