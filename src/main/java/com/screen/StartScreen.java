package com.screen;

import com.app.MainController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.screen.interfaces.Screen;

public class StartScreen extends JPanel implements Screen {
    private MainController mainController;
    private Image backgroundImage;

    public StartScreen(MainController mainController) {
        this.mainController = mainController;
        // 배경 이미지 초기화
        backgroundImage = getScaledImageIcon("start.png", 1024, 1024).getImage(); // 배경 이미지 경로 설정
        initialize();
    }

    // 이미지 스케일링 메서드
    private ImageIcon getScaledImageIcon(String imagePath, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    @Override
    public void initialize() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 패딩 설정

        // 제목 레이블
        JLabel titleLabel = new JLabel("검 강화하기");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE); // 글자 흰색
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // 게임 시작 버튼
        JButton startButton = new JButton("게임 시작");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Game");
                mainController.updateGameScreenUI();
            // 검 강화 화면으로 전환
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridwidth = 2;
        add(startButton, gbc);

        // 설정 버튼
        JButton settingButton = new JButton("설정");
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Setting");
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(settingButton, gbc);

        // 통계 화면으로 이동 버튼
        JButton statsButton = new JButton("통계 화면으로 이동");
        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Statistics"); // 통계 화면으로 전환
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(statsButton, gbc);

        // 게임 종료 버튼
        JButton exitButton = new JButton("게임 종료");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // 게임 종료
            }
        });
        gbc.gridx = 0;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridwidth = 1;
        add(exitButton, gbc);

        // 버튼 스타일 설정
        for (Component component : getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.setForeground(Color.WHITE); // 버튼 글자 색상
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경 이미지 그리기
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // 배경 이미지 그리기
        }

        // 글자가 잘 보이도록 어두운 반투명 배경 추가
        g.setColor(new Color(0, 0, 0, 150)); // 검은색 반투명 배경 (150의 값으로 어둡게 조정)
        g.fillRect(0, 0, getWidth(), getHeight()); // 전체 패널에 덧칠
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
