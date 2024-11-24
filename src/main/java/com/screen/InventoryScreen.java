package com.screen;

import com.app.MainController;

import javax.swing.*;

import com.item.Ticket;
import com.item.interfaces.Item;
import com.player.Player;
import com.screen.interfaces.Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InventoryScreen extends JPanel implements Screen {
    Player player = Player.getInstance();

    private MainController mainController;
    public InventoryScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }
    public void CenterPanelCreate(){
        int MRows = 5;
        int MCols = 5;
        JPanel panel = new JPanel(new GridLayout(MRows,MCols,10,5));
        ArrayList<Item> inventory = player.getInventory();

        for (int i = 0; i < MRows*MCols; i++) {
            if(inventory.size() > i){
                JPanel MiniPanel = new JPanel(new GridLayout(2,1));
                MiniPanel = ItemPanelCreate(MiniPanel,inventory.get(i));
                panel.add(MiniPanel);
            }
            else{
                Item nullItem = new Ticket();
                JPanel MiniPanel = new JPanel(new GridLayout(2,1));
                MiniPanel = ItemPanelCreate(MiniPanel,nullItem);
                panel.add(MiniPanel);
            }
        }
        add(panel);
    }
    public JPanel ItemPanelCreate(JPanel c,Item i){

        JPanel NamePanel = new JPanel();
        JLabel itemName = new JLabel(i.getName());//1번패널
        itemName.setHorizontalAlignment(JLabel.CENTER);
        itemName.setVerticalAlignment(JLabel.CENTER);
        itemName.setFont(itemName.getFont().deriveFont(20.0f));
        NamePanel.add(itemName);
        c.add(NamePanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        JLabel countLabel =new JLabel(i.getCount()+" 개");
        JButton ActivateButton = AButton(new JButton(), i,countLabel);
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);
        countLabel.setVerticalAlignment(SwingConstants.CENTER);
        countLabel.setFont(countLabel.getFont().deriveFont(32.0f));
        buttonPanel.add(countLabel);
        buttonPanel.add(ActivateButton);

        buttonPanel.setBackground(Color.LIGHT_GRAY);
        NamePanel.setBackground(Color.gray);
        c.add(buttonPanel);

        return c;
    }

    private JButton AButton(JButton Button, Item i, JLabel countLabel) {
        JLabel Label = new JLabel("사용하기");
        Label.setHorizontalAlignment(JLabel.CENTER);
        Label.setFont(Label.getFont().deriveFont(20.0f));
        Button.add(Label);
        Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                i.useItem();
                countLabel.setText(i.getCount()+" 개");
                mainController.updateGameScreenUI();
            }
        });
        return Button;
    }

    public void goGameScreen(){
        JButton GoGame = new JButton("게임창으로 돌아가기");
        GoGame.setPreferredSize(new Dimension(0,70));
        GoGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Game");
            }
        });
        JPanel goScreen = new JPanel(new BorderLayout());
        goScreen.add(GoGame);
        add(goScreen, BorderLayout.PAGE_START);
    }
    public void refreshInventory() {
        removeAll();
        initialize();
        revalidate();
        repaint();
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        goGameScreen();
        CenterPanelCreate();
    }
    @Override
    public void showScreen() {
        setVisible(true);
    }

    @Override
    public void hideScreen() {
        setVisible(false);
    }
}