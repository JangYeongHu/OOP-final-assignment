package com.screen;

import com.app.MainController;
import com.player.Player;
import com.screen.interfaces.Screen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoadScreen extends JPanel implements Screen {
    private MainController mainController;
    private JPanel topPanel;
    public boolean isLoadRequest = true;

    private static LoadScreen loadScreen;

    // 색상 팔레트 정의
    private final Color COLOR_PRIMARY = new Color(0x1A3636); // #1a3636
    private final Color COLOR_SECONDARY = new Color(0x40534C); // #40534c
    private final Color COLOR_HIGHLIGHT = new Color(0x677D6A); // #677d6a
    private final Color COLOR_BACKGROUND = new Color(0xD6BD98); // #d6bd98

    public static LoadScreen getInstance() {
        return loadScreen;
    }

    public void setLoadRequest(boolean loadRequest) {
        isLoadRequest = loadRequest;
        initialize();
    }

    public LoadScreen(MainController mainController) {
        this.mainController = mainController;
        loadScreen = this;
        initialize();
    }

    @Override
    public void initialize() {
        removeAll();
        setLayout(new BorderLayout());
        run();
        revalidate();
        repaint();
    }

    @Override
    public void showScreen() {
        setVisible(true);
    }

    @Override
    public void hideScreen() {
        setVisible(false);
    }

    private void run() {
        // 상단 패널
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(topPanel(isLoadRequest));
        panel1.setBorder(new EmptyBorder(20, 30, 20, 30)); // 패딩 설정
        panel1.setBackground(COLOR_PRIMARY); // 상단 배경색 설정

        JPanel panel2 = new JPanel();

        // 중앙 패널 배경색 설정
        panel2.setBackground(COLOR_BACKGROUND);

        // 패널 높이를 더 크게 설정
        panel1.setPreferredSize(new Dimension(getWidth(), 120));
        add(panel1, BorderLayout.NORTH);

        // panel2는 중앙에 배치
        add(panel2, BorderLayout.CENTER);
        panel2.setLayout(new GridLayout(3, 1, 15, 15));
        panel2.setBorder(new EmptyBorder(30, 30, 30, 30));

        for (int i = 0; i < 3; i++) {
            createFilePans(panel2, i);
        }
    }

    private JPanel topPanel(boolean isLoadRequest) {
        JPanel tp = new JPanel();
        tp.setLayout(new BorderLayout());
        tp.setBackground(COLOR_PRIMARY); // 상단 패널 배경색

        String str = isLoadRequest ? "어떤 파일을 로드하시겠습니까?" : "어떤 파일에 저장하시겠습니까?";
        JLabel lbl = new JLabel(str, JLabel.CENTER);
        lbl.setFont(new Font("DungGeunMo", Font.BOLD, 30));
        lbl.setForeground(COLOR_BACKGROUND); // 텍스트 색상 설정
        tp.add(lbl, BorderLayout.CENTER);

        if (!isLoadRequest) {
            tp.add(returnBtn(), BorderLayout.WEST);
        }
        return tp;
    }

    public void createFilePans(JPanel panel, int index) {
        JPanel fp = filePanel(index);

        fp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isLoadRequest) {
                    Player.setSelectedIdx(index);
                    mainController.switchTo("Start");
                } else {
                    Player.getInstance();
                    mainController.savePlayerData(index);
                    mainController.switchTo("Game");
                }
            }
        });

        panel.add(fp);
    }

    private JPanel filePanel(int index) {
        Player player = Player.getInstance(index);
        JPanel fp = new JPanel(new BorderLayout());

        Border padding = new EmptyBorder(10, 20, 10, 20); // 내부 여백
        Border line = BorderFactory.createLineBorder(COLOR_SECONDARY, 3); // 외곽선 색상
        fp.setBorder(new CompoundBorder(line, padding));

        // 검 이미지 패널
        JPanel leftPanel = new JPanel(new BorderLayout());
        ImageIcon backgroundIcon = player.getNowSword().imageIcon();
        Image scaledImage = backgroundIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel j = new JLabel(scaledIcon);

        leftPanel.setPreferredSize(new Dimension(200, 200));
        leftPanel.add(j);
        fp.add(leftPanel, BorderLayout.WEST);

        // 정보 패널
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2, 2, 0, 10)); // 세로로 배치, 행 간격 10px

        // 폰트 정의
        Font labelFont = new Font("DungGeunMo", Font.PLAIN, 20); // 글씨 크기 조정

        JLabel nameLabel = new JLabel("검 이름: " + player.getNowSword().getName());
        nameLabel.setFont(labelFont); // 폰트 크기 적용

        JLabel strengthLabel = new JLabel("강화도 : " + player.getNowSword().getpossibility());
        strengthLabel.setFont(labelFont);

        JLabel moneyLabel = new JLabel("돈 : " + player.getMoney());
        moneyLabel.setFont(labelFont);

        JLabel dateLabel = new JLabel("저장일: " + player.getUpdateDate());
        dateLabel.setFont(labelFont);

        // 텍스트 색상 설정
        nameLabel.setForeground(COLOR_SECONDARY);
        strengthLabel.setForeground(COLOR_SECONDARY);
        moneyLabel.setForeground(COLOR_SECONDARY);
        dateLabel.setForeground(COLOR_SECONDARY);

        rightPanel.add(nameLabel);
        rightPanel.add(strengthLabel);
        rightPanel.add(moneyLabel);
        rightPanel.add(dateLabel);

        fp.add(rightPanel, BorderLayout.CENTER);

        // 패널 배경색 초기화
        fp.setBackground(COLOR_BACKGROUND);
        leftPanel.setBackground(COLOR_BACKGROUND);
        rightPanel.setBackground(COLOR_BACKGROUND);

        // 마우스 이벤트에 따른 색상 변화
        fp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                fp.setBackground(COLOR_BACKGROUND);
                leftPanel.setBackground(COLOR_BACKGROUND);
                rightPanel.setBackground(COLOR_BACKGROUND);

                // 텍스트 색상 복원
                nameLabel.setForeground(COLOR_SECONDARY);
                strengthLabel.setForeground(COLOR_SECONDARY);
                moneyLabel.setForeground(COLOR_SECONDARY);
                dateLabel.setForeground(COLOR_SECONDARY);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                fp.setBackground(COLOR_HIGHLIGHT);
                leftPanel.setBackground(COLOR_HIGHLIGHT);
                rightPanel.setBackground(COLOR_HIGHLIGHT);

                // 텍스트 색상 변경
                nameLabel.setForeground(COLOR_BACKGROUND);
                strengthLabel.setForeground(COLOR_BACKGROUND);
                moneyLabel.setForeground(COLOR_BACKGROUND);
                dateLabel.setForeground(COLOR_BACKGROUND);
            }
        });

        return fp;
    }


    private JButton returnBtn() {
        JButton jb = new JButton();
        jb.setText("돌아가기");
        jb.setBackground(COLOR_HIGHLIGHT);
        jb.setForeground(COLOR_PRIMARY);
        jb.addActionListener(e -> mainController.switchTo("Game"));
        return jb;
    }
}
