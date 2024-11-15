package com.app;

import com.screen.*;
import com.screen.interfaces.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class MainController extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, Screen> screens;

    public MainController() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        screens = new HashMap<>();

        addScreen("Load", new LoadScreen(this));
        addScreen("Start", new StartScreen(this));
        addScreen("Game", new GameScreen(this));
        addScreen("Store", new StoreScreen(this));
        addScreen("Setting", new SettingScreen(this));
        addScreen("Reset", new ResetScreen(this));
        addScreen("Statistics", new StatisticsScreen(this));
        addScreen("Inventory", new InventoryScreen(this));

        add(mainPanel);
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        switchTo("Game");
    }

    private void addScreen(String name, Screen screen) {
        screens.put(name, screen); // Map에 화면 추가
        mainPanel.add((Component) screen, name); // CardLayout에 화면 추가
    }

    public void switchTo(String screenName) {

        screens.forEach((name, screen) -> {
            if (name.equals(screenName)) {
                screen.showScreen();
            } else {
                screen.hideScreen();
            }
        });
        cardLayout.show(mainPanel, screenName);
    }

    public void loadSettingData() {

    }

    public void saveData() {

    }
}