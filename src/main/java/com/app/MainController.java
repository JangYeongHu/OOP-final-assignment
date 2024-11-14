package com.app;


import org.json.JSONObject;
import com.screen.*;
import com.screen.interfaces.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
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

        add(mainPanel);
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        switchTo("Statistics");
    }

    private void addScreen(String name, Screen screen) {
        screens.put(name, screen); // Ma
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

    public JSONObject loadJson(String path) {



        // 지정된 경로에서 JSON 파일을 로드
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            StringBuilder jsonText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonText.append(line);
            }

            // JSON 텍스트를 JSONObject로 변환
            return new JSONObject(jsonText.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void loadSettingData() {
    }

    public void saveData() {
    }
}