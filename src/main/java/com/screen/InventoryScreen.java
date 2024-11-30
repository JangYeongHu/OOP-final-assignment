package com.screen;

import com.app.MainController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.item.Ticket;
import com.item.interfaces.Item;
import com.player.Player;
import com.screen.interfaces.Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.ArrayList;

public class InventoryScreen extends JPanel implements Screen {
    Player player = Player.getInstance();
    int MRows = 3;
    int MCols = 3;

    private MainController mainController;
    public InventoryScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }
    public void CenterPanelCreate(){
        JPanel panel = new JPanel(new GridLayout(MRows,MCols,10,5));
        ArrayList<Item> inventory = player.getInventory();
        add(mainPanelCreate(inventory, panel));
    }

    private JPanel mainPanelCreate(ArrayList<Item> inventory, JPanel panel){
        int a = 0;
        for (Item item : inventory) {
            if (item.getCount() > 0) {
                JPanel MiniPanel = new JPanel(new GridLayout(2, 1));
                MiniPanel = ItemPanelCreate(MiniPanel, item);
                panel.add(MiniPanel);
            } else a++;
        }
        for (int i = 0; i < MRows*MCols-inventory.size()+a; i++) {
            JPanel miniPanel = new JPanel(new GridLayout(2, 1));
            panel.add(nullItemPanel(miniPanel));
        }

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(64, 83, 76));
        panel.setOpaque(false);
        return panel;
    }
    private JPanel nullItemPanel(JPanel j){
        return ItemPanelCreate(j, new Ticket());
    }
    public JPanel ItemPanelCreate(JPanel c,Item i){
        JPanel NamePanel = new JPanel(new BorderLayout());
        JLabel itemName = new JLabel(i.getName());//1번패널
        labelCenterSort(itemName);
        NamePanel.add(itemName);
        replaceFont(itemName, 50);
        c.add(NamePanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        JLabel countLabel =new JLabel(i.getCount()+" 개");
        JButton ActivateButton = AButton(new JButton("사용"), i,countLabel);
        labelCenterSort(countLabel);
        buttonPanel.add(countLabel);
        buttonPanel.add(ActivateButton);
        replaceFont(countLabel, 40);

        NamePanel.setOpaque(false);
        buttonPanel.setOpaque(false);
        c.setOpaque(false);
        c.add(buttonPanel);

        Color color = new Color(214, 189, 152);
        c.setBorder(BorderFactory.createLineBorder(color, 4));
        Border topBorder = new MatteBorder(4, 0, 0, 2, color);
        countLabel.setBorder(new CompoundBorder(topBorder, null));
        return c;
    }

    private void labelCenterSort(JLabel label){
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(25.0f));
    }
    private JButton AButton(JButton Button, Item i, JLabel countLabel) {
        Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (i.getCount() > 0) {
                    i.useItem();
                    countLabel.setText(i.getCount() + " 개");
                    if (i.getCount() == 0) {
                        JOptionPane.showMessageDialog(null, i.getName() + " 아이템이 모두 사용되었습니다!");
                        refreshInventory();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "아이템이 없습니다!");
                }
                mainController.updateGameScreenUI();
            }

        });
        setButtonSize(Button);
        return Button;
    }
    private void setButtonSize(JButton c) {
        c.setBackground(new Color(26, 54, 54));
        Color color = new Color(214, 189, 152);
        c.setForeground(color);
        replaceFont(c,30);
        c.setFocusPainted(false);
        c.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                c.setBackground(new Color(103, 125, 106));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                c.setBackground(new Color(26, 54, 54));
            }
        });
        Border topBorder = new MatteBorder(4, 2, 0, 0, color);
        c.setBorder(new CompoundBorder(topBorder, null));
    }
    private void replaceFont(Container c, int size){
        try{
            InputStream fontStream = getClass().getResourceAsStream("/fonts/DungGeunMo.ttf");
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            Font newFont = baseFont.deriveFont((float) size);
            c.setForeground(Color.white);
            c.setFont(newFont);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("not find font2");
        }
    }
    public void goGameScreen(){
        JButton GoGame = new JButton("게임창으로 돌아가기");
        GoGame.setPreferredSize(new Dimension(0,100));
        setButtonSize(GoGame);
        GoGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Game");
            }
        });
        JPanel goScreen = new JPanel(new BorderLayout());
        goScreen.add(GoGame);
        Color color = new Color(214, 189, 152);
        Border topBorder = new MatteBorder(4, 4, 4, 4, color);
        GoGame.setBorder(new CompoundBorder(topBorder, null));
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