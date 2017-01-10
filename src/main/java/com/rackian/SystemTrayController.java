package com.rackian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
            PopupMenu popup = new PopupMenu();
            addItemToMenu(popup, "Current Status", (e) -> stageController.launchStatus());
            addItemToMenu(popup, "Configuration", (e) -> stageController.launchSetup());
            addItemToMenu(popup, "Exit", (e) -> System.exit(0));
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
