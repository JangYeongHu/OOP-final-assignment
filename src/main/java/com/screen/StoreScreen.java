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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;

public class StoreScreen extends JPanel implements Screen {

    private MainController mainController;

    private int productList = 7;
    public StoreScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }
    public void goGameScreen(){
        JButton GoGame = new JButton("게임창으로 돌아가기");
        setButtonSize(GoGame);
        GoGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Game");
                mainController.updateInventoryScreen();
                mainController.updateGameScreenUI();

            }
        });
        add(GoGame, BorderLayout.PAGE_START);
    }
    JLabel playerMoneyLabel = new JLabel();
    public void itemListPanel(Player player){

        JPanel productPanel2 = new JPanel(new BorderLayout());
        JPanel productPanel = new JPanel(new GridLayout(productList,1,0,20));
        JPanel moneyPanel = new JPanel(new BorderLayout());
        moneyPanel.setPreferredSize(new Dimension(200,70));
        playerMoneyLabel.setText("돈 : "+player.getMoney());
        playerMoneyLabel.setForeground(new Color(214, 189, 152));
        replaceFont(playerMoneyLabel,40);
        playerMoneyLabel.setHorizontalAlignment(JLabel.CENTER);
        playerMoneyLabel.setVerticalAlignment(JLabel.CENTER);
        moneyPanel.add(playerMoneyLabel);

        moneyPanel.setOpaque(false);
        productPanel2.setOpaque(false);
        productPanel2.add(moneyPanel,BorderLayout.PAGE_START);
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Save",0,1),player));
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Push",0,1),player));
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Upgrade",10,1),player));
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Upgrade",12,1),player));
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Upgrade",14,1),player));
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Upgrade",16,1),player));
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Upgrade",18,1),player));

        productPanel.setBackground(new Color(64, 83, 76));
        productPanel2.add(productPanel);

        add(productPanel2, BorderLayout.CENTER);
    }

    public JPanel ItemPanelCreate(Item i,Player player){
        JPanel productPanel = new JPanel(new GridLayout(1,2));
        JLabel productName = new JLabel(i.getName());
        JPanel buyPanel = new JPanel(new GridLayout(1,2));
        JLabel productPrice = new JLabel(i.getPrice()+"");
        JButton buyButton = getBuyButton(i, player);

        buyPanel.add(productPrice);
        buyPanel.add(buyButton);
        productPanel.add(productName);
        productPanel.add(buyPanel);
        productName.setForeground(new Color(214, 189, 152));
        productPrice.setForeground(Color.white);
        productPanel.setBorder(BorderFactory.createLineBorder(new Color(103, 125, 106), 4));
        //폰트크기,중앙정렬
        productName.setVerticalAlignment(JLabel.CENTER);
        productName.setHorizontalAlignment(JLabel.CENTER);
        replaceFont(productName,40);
        replaceFont(productPrice,40);
        //페널들 투명
        buyPanel.setOpaque(false);
        productPanel.setOpaque(false);
        //패널크기증가
        return productPanel;
    }

    private JButton getBuyButton(Item i, Player player) {
        JButton buyButton = new JButton("구매");
        setButtonSize(buyButton);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player.getMoney()- i.getPrice() >= 0){
                    player.setMoney(player.getMoney()- i.getPrice());
                    int check = 0;
                    for (Item item :player.getInventory()) {
                        if (item.getName().equals(i.getName())) {
                            item.addCount();
                            check =1;
                            break;
                        }
                    }
                    if(check == 0){
                        player.getInventory().add(i);
                    }
                    JOptionPane.showMessageDialog(null, i.getName() + " 아이템을 구매하셨습니다");
                }
                else{
                    JOptionPane.showMessageDialog(null, "돈이부족합니다");
                }
                playerMoneyLabel.setText("돈 : "+player.getMoney());
            }
        });
        return buyButton;
    }

    private void setButtonSize(JButton c) {
        c.setPreferredSize(new Dimension(100, 100));
        c.setBackground(new Color(64, 83, 76));
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
                c.setBackground(new Color(64, 83, 76));
            }
        });
        c.setBorder(BorderFactory.createLineBorder(color, 4));
    }

    private void replaceFont(Container c, int size){
        try{
            InputStream fontStream = getClass().getResourceAsStream("/fonts/DungGeunMo.ttf");
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            Font newFont = baseFont.deriveFont((float) size);
            c.setFont(newFont);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("not find font2");
        }
    }

    @Override
    public void initialize() {
        Player player = Player.getInstance();
        setLayout(new BorderLayout());
        setBackground(new Color(64, 83, 76));
        goGameScreen();
        itemListPanel(player);
    }

    @Override
    public void showScreen() {
        setVisible(true);
    }

    @Override
    public void hideScreen() {
        setVisible(false);
    }

    public void refreshStore() {
        playerMoneyLabel.setText("돈 : "+Player.getInstance().getMoney());
    }
}
