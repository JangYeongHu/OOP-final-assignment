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
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("start.png")).getImage();
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

        // 색상 및 폰트 설정
        Color textColor = new Color(0xFF1D4040, true);
        Color buttonTextColor = new Color(0xFFD6BD98, true);
        Color buttonColor = new Color(0x677D6A);
        Font customFont = new Font("DungGeunMo", Font.PLAIN, 20);  // 폰트 설정
        Font titleFont = new Font("DungGeunMo", Font.PLAIN, 40); // 제목 폰트
        // 제목 레이블
        JLabel titleLabel = new JLabel("검 강화하기");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(textColor);  // 텍스트 색상
        titleLabel.setFont(titleFont);  // 제목 폰트 크기 조정
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // 게임 시작 버튼
        JButton startButton = new JButton("게임 시작");
        startButton.setBackground(buttonColor);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setForeground(buttonTextColor); // 버튼 텍스트 색상 변경
        startButton.setFont(customFont); // 버튼 폰트 설정
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
        settingButton.setBackground(buttonColor);
        settingButton.setOpaque(true);
        settingButton.setBorderPainted(false);
        settingButton.setForeground(buttonTextColor); // 버튼 텍스트 색상 변경
        settingButton.setFont(customFont); // 버튼 폰트 설정
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
        statsButton.setBackground(buttonColor);
        statsButton.setOpaque(true);
        statsButton.setBorderPainted(false);
        statsButton.setForeground(buttonTextColor); // 버튼 텍스트 색상 변경
        statsButton.setFont(customFont); // 버튼 폰트 설정
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
        exitButton.setBackground(buttonColor);
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        exitButton.setForeground(buttonTextColor); // 버튼 텍스트 색상 변경
        exitButton.setFont(customFont); // 버튼 폰트 설정
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
/////