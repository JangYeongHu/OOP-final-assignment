package com.screen;


import com.app.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.screen.interfaces.Screen;

public class SettingScreen extends JPanel implements Screen {

    private MainController mainController;

    public SettingScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Setting Screen");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Start Screen으로 이동");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Start");
            }
        });
        add(backButton, BorderLayout.SOUTH);
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
