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
            if(inventory.size() > i&& inventory.get(i).getCount() > 0){
                JPanel MiniPanel = new JPanel(new GridLayout(2,1));
                MiniPanel = ItemPanelCreate(MiniPanel,inventory.get(i));
                panel.add(MiniPanel);
            }
            else{
                JPanel miniPanel = new JPanel(new GridLayout(2,1));
                panel.add(nullItemPanel(miniPanel));
            }
        }
        add(panel);
    }
    private JPanel nullItemPanel(JPanel j){
        return ItemPanelCreate(j, new Ticket());
    }
    public JPanel ItemPanelCreate(JPanel c,Item i){
        JPanel NamePanel = new JPanel();
        JLabel itemName = new JLabel(i.getName());//1번패널
        labelCenterSort(itemName);
        NamePanel.add(itemName);
        c.add(NamePanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        JLabel countLabel =new JLabel(i.getCount()+" 개");
        JButton ActivateButton = AButton(new JButton(), i,countLabel);
        labelCenterSort(countLabel);
        buttonPanel.add(countLabel);
        buttonPanel.add(ActivateButton);

        buttonPanel.setBackground(Color.LIGHT_GRAY);
        NamePanel.setBackground(Color.gray);
        c.add(buttonPanel);

        return c;
    }

    private void labelCenterSort(JLabel label){
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(25.0f));
    }
    private JButton AButton(JButton Button, Item i, JLabel countLabel) {
        JLabel Label = new JLabel("사용하기");
        Label.setHorizontalAlignment(JLabel.CENTER);
        Label.setFont(Label.getFont().deriveFont(20.0f));
        Button.add(Label);
        Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                i.useItem();
//                countLabel.setText(i.getCount()+" 개");
                if (i.getCount() > 0) {
                    i.useItem();
                    countLabel.setText(i.getCount() + " 개");

                    // count가 0이 되었는지 확인
                    if (i.getCount() == 0) {
                        JOptionPane.showMessageDialog(null, i.getName() + " 아이템이 모두 사용되었습니다!");
                        refreshInventory(); // 인벤토리 화면 갱신
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "아이템이 없습니다!");
                }
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