package com.app;

import com.item.Sword;
import com.player.Player;
import com.screen.*;
import com.screen.interfaces.Screen;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class MainController extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, Screen> screens;

    private static Player player;

    public static Sword[] swordList = new Sword[20];

    public MainController() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        screens = new HashMap<>();
        createSword();
        JsonController.getInstance();

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
        switchTo("Load");
    }

    public static Sword findSwordById(int swordId) {
        for(Sword sword: swordList) {
            if(sword.getId() == swordId) return sword;
        }
        return null;
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

    static void createSword(){
        for (int i = 1; i < 21; i++){
            swordList[i-1] = new Sword("src/main/resources/"+i+".png",i);
            swordList[i-1].setSwordDescription(i+"번째 검의 설명");
            swordList[i-1].setSwordName(i+"번째 검");
        }
    }
}