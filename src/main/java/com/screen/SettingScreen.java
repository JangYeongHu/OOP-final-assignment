package com.screen;


import com.app.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

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

        // BGM 토글 버튼 (초록/회색 상태 변경)
        JButton bgmToggleButton = new JButton("BGM 켜짐");
        bgmToggleButton.setBackground(Color.GREEN);
        bgmToggleButton.setOpaque(true);
        bgmToggleButton.setBorderPainted(false);
        bgmToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isBgmOn = !isBgmOn; // BGM 상태 변경
                if (isBgmOn) {
                    bgmToggleButton.setText("BGM 켜짐");
                    bgmToggleButton.setBackground(Color.GREEN);
                    JOptionPane.showMessageDialog(null, "BGM이 켜졌습니다."); // 알림
                } else {
                    bgmToggleButton.setText("BGM 꺼짐");
                    bgmToggleButton.setBackground(Color.GRAY);
                    JOptionPane.showMessageDialog(null, "BGM이 꺼졌습니다."); // 알림
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(bgmToggleButton, gbc);

        // 볼륨 조절 패널
        JPanel volumePanel = new JPanel(new BorderLayout());
        JLabel volumeLabel = new JLabel("볼륨 조절: ");
        volumePanel.add(volumeLabel, BorderLayout.WEST);

        // 볼륨 슬라이더
        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setMajorTickSpacing(10); // 10 단위로 표시
        volumeSlider.setMinorTickSpacing(10); // 10 단위로 조정 가능
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);

        // 숫자 레이블 설정
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("0"));
        labelTable.put(50, new JLabel("50"));
        labelTable.put(100, new JLabel("100"));
        volumeSlider.setLabelTable(labelTable);

        // 스냅 기능 활성화
        volumeSlider.setSnapToTicks(true);

        // 볼륨 변경 이벤트 핸들러
        volumeSlider.addChangeListener(e -> {
            int volume = volumeSlider.getValue();
            // 선택된 볼륨에 대한 추가 동작 (필요시 추가)
            System.out.println("현재 볼륨: " + volume);
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
        gbc.gridy = 3;
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