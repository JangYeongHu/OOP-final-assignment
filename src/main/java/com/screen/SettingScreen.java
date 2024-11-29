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
    private boolean isBgmOn = true;
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

        // 제목 레이블
        JLabel titleLabel = new JLabel("설정");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // BGM 토글 버튼
        JButton bgmToggleButton = new JButton("BGM 켜짐");
        bgmToggleButton.setBackground(Color.GREEN);
        bgmToggleButton.setOpaque(true);
        bgmToggleButton.setBorderPainted(false);
        bgmToggleButton.addActionListener(e -> {
            isBgmOn = !isBgmOn;
            if (isBgmOn) {
                bgmController.play(BGM_FILE_PATH);  // BGM 재생
                bgmToggleButton.setText("BGM 켜짐");
                bgmToggleButton.setBackground(Color.GREEN);
            } else {
                bgmController.stop();  // BGM 중지
                bgmToggleButton.setText("BGM 꺼짐");
                bgmToggleButton.setBackground(Color.GRAY);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(bgmToggleButton, gbc);

        // 볼륨 조절 슬라이더
        JPanel volumePanel = new JPanel(new BorderLayout());
        JLabel volumeLabel = new JLabel("볼륨 조절: ");
        volumePanel.add(volumeLabel, BorderLayout.WEST);

        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("0"));
        labelTable.put(50, new JLabel("50"));
        labelTable.put(100, new JLabel("100"));
        volumeSlider.setLabelTable(labelTable);
        volumeSlider.addChangeListener(e -> {
            int volume = volumeSlider.getValue();
            System.out.println("현재 볼륨: " + volume);
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
