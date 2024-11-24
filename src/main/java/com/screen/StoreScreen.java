package com.screen;



import com.app.MainController;

import javax.swing.*;

import com.player.Player;
import com.screen.interfaces.Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreScreen extends JPanel implements Screen {

    private MainController mainController;

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
        add(GoGame);
    }
    public void readItem(Player player){
    }
    public void buyItem(){

    }
    public void itemListPanel(){
        Player player = Player.getInstance();
        JPanel itemList = new JPanel(new BorderLayout());
        JPanel item = new JPanel(new BorderLayout());
        JLabel name = new JLabel("아이템이름");
        //|이름   |구매|

    }

    @Override
    public void initialize() {
        setLayout(new GridLayout(8,1));
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
}
