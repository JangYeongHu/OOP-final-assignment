package com.screen;


import com.app.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.screen.interfaces.Screen;

public class SettingScreen extends JPanel implements Screen {

    private MainController mainController;
    private boolean isBgmOn = true;

    public SettingScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    @Override
    public void initialize() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 패딩 설정

        // 제목 레이블
        JLabel titleLabel = new JLabel("설정");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // BGM 켜기 버튼
        JButton bgmOnButton = new JButton("BGM 켜기");
        bgmOnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isBgmOn = true;
                JOptionPane.showMessageDialog(null, "BGM이 켜졌습니다.");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(bgmOnButton, gbc);

        // BGM 끄기 버튼
        JButton bgmOffButton = new JButton("BGM 끄기");
        bgmOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isBgmOn = false;
                JOptionPane.showMessageDialog(null, "BGM이 꺼졌습니다.");
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(bgmOffButton, gbc);

        // 볼륨 조절 패널
        JPanel volumePanel = new JPanel(new BorderLayout());
        JLabel volumeLabel = new JLabel("볼륨 조절");
        volumePanel.add(volumeLabel, BorderLayout.WEST);

        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.addChangeListener(e -> {
            int volume = volumeSlider.getValue();
            // 볼륨 값에 따른 행동 추가
        });
        volumePanel.add(volumeSlider, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(volumePanel, gbc);


        // 뒤로가기 버튼
        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Start");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(backButton, gbc);
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
