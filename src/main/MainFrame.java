package src.main;

import src.game.GameScreen;
import src.interfaces.Screen;
import src.load.LoadScreen;
import src.start.StartScreen;
import src.store.StoreScreen;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, Screen> screens;

    public MainFrame() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        screens = new HashMap<>();

        addScreen("Load", new LoadScreen(this));
        addScreen("Start", new StartScreen(this));
        addScreen("Game", new GameScreen(this));
        addScreen("Store", new StoreScreen(this));

        add(mainPanel);
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        switchTo("Start");
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
                screen.showScreen();
            }
        });
        cardLayout.show(mainPanel, screenName);
    }
}