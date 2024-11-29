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

public class StoreScreen extends JPanel implements Screen {

    private MainController mainController;

    private int productList = 9;
    public StoreScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }
    public void goGameScreen(){
        JButton GoGame = new JButton("게임창으로 돌아가기");
        GoGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Game");
            }
        });
        add(GoGame,BorderLayout.PAGE_START);
    }
    public void readItem(Player player){
    }
    JLabel playerMoneyLabel = new JLabel();
    public void itemListPanel(){
        Player player = Player.getInstance();
        //|  이름   |가격|구매|
        //Save
        JPanel productPanel = new JPanel(new GridLayout(productList,1,0,20));
        playerMoneyLabel.setText("돈 : "+player.getMoney());
        playerMoneyLabel.setVerticalAlignment(JLabel.CENTER);
        playerMoneyLabel.setHorizontalAlignment(JLabel.CENTER);
        playerMoneyLabel.setFont(playerMoneyLabel.getFont().deriveFont(34.0f));
        JPanel moneyPanel = new JPanel();
        moneyPanel.add(playerMoneyLabel);
        moneyPanel.setBackground(Color.yellow);
        productPanel.add(moneyPanel);
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Save",0,1),player));
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Push",0,1),player));
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Upgrade",10,1),player));
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Upgrade",12,1),player));
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Upgrade",14,1),player));
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Upgrade",16,1),player));
        productPanel.add(ItemPanelCreate(Ticket.ticketType("Upgrade",18,1),player));
        add(productPanel, BorderLayout.CENTER);
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

        //폰트크기,중앙정렬
        productName.setVerticalAlignment(JLabel.CENTER);
        productName.setHorizontalAlignment(JLabel.CENTER);
        productPrice.setFont(productPrice.getFont().deriveFont(30.0f));
        productName.setFont(productName.getFont().deriveFont(30.0f));
        buyButton.setFont(buyButton.getFont().deriveFont(30.0f));

        return productPanel;
    }

    private JButton getBuyButton(Item i, Player player) {
        JButton buyButton = new JButton("구매");
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player.getMoney()- i.getPrice() > 0){
                    player.setMoney(player.getMoney()- i.getPrice());
                    if (player.getInventory().contains(i)){
                        i.addCount();
                    }else{
                        player.getInventory().add(i);
                    }
                    JOptionPane.showMessageDialog(null, i.getName() + " 아이템을 구매하셨습니다");
                }
                else{
                    JOptionPane.showMessageDialog(null, "돈이부족합니다");
                }
                mainController.updateInventoryScreen();
                mainController.updateGameScreenUI();
                playerMoneyLabel.setText("돈 : "+player.getMoney());
            }
        });
        return buyButton;
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout(0,60));
        goGameScreen();
        itemListPanel();
    }

    @Override
    public void showScreen() {
        setVisible(true);
    }

    @Override
    public void hideScreen() {
        setVisible(false);
    }

    public void refreshInventory() {
        removeAll();
        initialize();
        revalidate();
        repaint();
    }
}
