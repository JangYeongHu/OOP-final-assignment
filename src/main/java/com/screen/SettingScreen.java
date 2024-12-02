package com.screen;

import com.app.MainController;
import com.app.BgmController;
import com.screen.interfaces.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class SettingScreen extends JPanel implements Screen {

    private MainController mainController;
    private BgmController bgmController = BgmController.getInstance();
    private boolean isBgmOn = true;  // 초기값: BGM 켜짐
    private static final String BGM_FILE_PATH = "src/main/resources/bgm.wav"; // BGM 파일 경로

    public SettingScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    @Override
    public void initialize() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // 색상 지정
        Color backgroundColor = new Color(0x40534C);  // 배경색
        Color buttonColor = new Color(0x677D6A);       // 버튼 색상
        Color labelColor = new Color(0x1A3636);        // 텍스트 색상
        Color textColor = new Color(0xD6BD98);         // 텍스트 색상
        setBackground(backgroundColor);

        Font customFont = new Font("DungGeunMo", Font.PLAIN, 20);  //  폰트
        Font titleFont = new Font("DungGeunMo", Font.PLAIN, 40);  // 제목 폰트

        // 제목 레이블
        JLabel titleLabel = new JLabel("설정");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(labelColor);  // 텍스트 색상
        titleLabel.setFont(titleFont);  // 제목 텍스트 크기
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // BGM 토글 버튼
        JButton bgmToggleButton = new JButton("BGM 켜짐");
        bgmToggleButton.setBackground(Color.GREEN);  // BGM 켜짐 상태에서 형광색으로 표시
        bgmToggleButton.setOpaque(true);
        bgmToggleButton.setBorderPainted(false);
        bgmToggleButton.setForeground(textColor);  // 텍스트 색상
        bgmToggleButton.setFont(customFont);  // 폰트 설정
        bgmToggleButton.addActionListener(e -> {
            isBgmOn = !isBgmOn;
            if (isBgmOn) {
                bgmController.play(BGM_FILE_PATH);  // BGM 재생
                bgmToggleButton.setText("BGM 켜짐");
                bgmToggleButton.setBackground(Color.GREEN);  // BGM 켜짐 상태 색상
            } else {
                bgmController.stop();  // BGM 중지
                bgmToggleButton.setText("BGM 꺼짐");
                bgmToggleButton.setBackground(Color.GRAY);  // BGM 꺼짐 상태 색상
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(bgmToggleButton, gbc);

        // 볼륨 조절 슬라이더
        JPanel volumePanel = new JPanel(new BorderLayout());
        volumePanel.setBackground(buttonColor);  // 버튼 색상으로 볼륨 패널 색상 설정
        JLabel volumeLabel = new JLabel("볼륨 조절: ");
        volumeLabel.setForeground(textColor);  // 텍스트 색상 변경
        volumeLabel.setFont(customFont);  // 폰트 설정
        volumePanel.add(volumeLabel, BorderLayout.WEST);

        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setSnapToTicks(true);
        volumeSlider.setBackground(new Color(0xB5B5B5));  // 배경색 설정
        volumeSlider.setForeground(new Color(0x76B041));  // 진행된 부분(초록색) 설정

        // 0, 50, 100만 표시
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("0"));
        labelTable.put(50, new JLabel("50"));
        labelTable.put(100, new JLabel("100"));
        volumeSlider.setLabelTable(labelTable);

        volumeSlider.addChangeListener(e -> {
            int volume = volumeSlider.getValue();
            // 0일 경우 소리를 완전히 끄기 위해 -80.0f로 설정
            float volumeValue = volume == 0 ? -80.0f : (float) (volume - 100) / 10.0f;
            bgmController.setVolume(volumeValue);
        });
        volumePanel.add(volumeSlider, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(volumePanel, gbc);

        // 뒤로가기 버튼
        JButton backButton = new JButton("뒤로가기");
        backButton.setBackground(buttonColor);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setForeground(textColor);  // 텍스트 색상
        backButton.setFont(customFont);  // 폰트 설정
        backButton.addActionListener(e -> mainController.switchTo("Start"));
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
////