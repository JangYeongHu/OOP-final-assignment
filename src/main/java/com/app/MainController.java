package com.app;

import com.item.Sword;
import com.player.Player;
import com.screen.*;
import com.screen.interfaces.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainController extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, Screen> screens;

    private static Player player = Player.getInstance();
    private static JsonController jsonController;

    private static BgmController bgmController;

    public static Sword[] swordList = new Sword[20];

    public MainController() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        screens = new HashMap<>();
        jsonController = JsonController.getInstance();
        bgmController = BgmController.getInstance();
        createSword();
        loadFont();

        addScreen("Load", new LoadScreen(this));
        addScreen("Start", new StartScreen(this));
        addScreen("Game", new GameScreen(this));
        addScreen("Store", new StoreScreen(this));
        addScreen("Setting", new SettingScreen(this));
        addScreen("Reset", new ResetScreen(this));
        addScreen("Statistics", new StatisticsScreen(this));
        addScreen("Inventory", new InventoryScreen(this));
        addScreen("Collection", new CollectionScreen(this));


        add(mainPanel);
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
//        bgmController.play();
        loadSettingData();
        switchTo("Load");
    }

    public static Sword findSwordById(int swordId) {
        for(Sword sword: swordList) {
            if(sword.getId() == swordId) return sword;
        }
        return null;
    }

    public static void updateSwordStatistics() {
        for(int i = 0; i < player.getStatics().size(); i++) {
            Sword sword = swordList[i];
            sword.setSuccessCount(player.getStatics().get(i)[0]);
            sword.setFailureCount(player.getStatics().get(i)[1]);
            System.out.println(sword.getName() + " " + sword.getSuccessCount() + " " + sword.getFailureCount());
        }
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
        if(jsonController.isBgmOn()) bgmController.play();
        int volume = jsonController.getBgmVolume();
        float volumeValue = volume == 0 ? -80.0f : (float) (volume - 100) / 10.0f;
        bgmController.setVolume(volumeValue);
        bgmController.setRoughVolume(volume);
    }


    static void createSword(){
        JSONArray swords = jsonController.getSwords();

        for (int i = 0; i < swords.length(); i++){
            JSONObject sword = swords.getJSONObject(i);
            swordList[i] = new Sword("src/main/resources/"+(i+1)+".png", i+1);
            swordList[i].setSwordName(sword.getString("name"));
            swordList[i].setSellPrice(sword.getInt("sell-price"));
            swordList[i].setUpgradeFee(sword.getInt("update-fee"));
            swordList[i].setPossibility(sword.getInt("possibility"));
            swordList[i].setSwordDescription(sword.getString("description"));

        }

        jsonController.init();
    }

    private void loadFont() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            InputStream fontStream = Main.class.getResourceAsStream("/fonts/DungGeunMo.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            customFont = customFont.deriveFont(30f);
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateGameScreenUI() {
        Screen screen = screens.get("Game");
        if (screen instanceof GameScreen) {
            ((GameScreen) screen).gamePanelUpdate(); // GameScreen의 UI 갱신
            ((GameScreen) screen).updateSaveTicketButton();
        }
    }
    public void updateInventoryScreen() {//게임,상점에서사용예정
        Screen screen = screens.get("Inventory");
        if (screen instanceof InventoryScreen) {
            ((InventoryScreen) screen).refreshInventory(); // InventoryScreen 갱신
        }
    }
    public void updateStoreScreen() {//게임,상점에서사용예정
        Screen screen = screens.get("Store");
        if (screen instanceof StoreScreen) {
            ((StoreScreen) screen).refreshStore(); // InventoryScreen 갱신
        }
    }
    //index 자리에 현재 데이터를 저장
    public void savePlayerData(int index) {
        jsonController.writeJson(index);
        LoadScreen.getInstance().initialize();
    }
}
////