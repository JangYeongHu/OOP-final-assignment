package com.screen;



import com.app.MainController;

import javax.swing.*;

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
        GoGame.setPreferredSize(new Dimension(0,70));
        GoGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Game");
            }
        });
        JPanel goScreen  =new JPanel(new BorderLayout());
        goScreen.add(GoGame);
        add(goScreen, BorderLayout.PAGE_START);
    }
    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        goGameScreen();
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
