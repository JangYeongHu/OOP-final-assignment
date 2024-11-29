package com.screen;

import com.app.MainController;
import com.player.Player;
import com.screen.interfaces.Screen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoadScreen extends JPanel implements Screen {
    private MainController mainController;
    private JPanel topPanel;
    public boolean isLoadRequest = true;

    private static LoadScreen loadScreen;

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

        // 패널 생성
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(topPanel(isLoadRequest));
        panel1.setBorder(new EmptyBorder(20, 30, 20, 30));         //패딩 설정
        JPanel panel2 = new JPanel();

        // 배경색 설정 - 구분용
        panel1.setBackground(Color.WHITE);
        panel2.setBackground(new Color(0, 102, 153));

        // panel1은 상단에 배치, 패널 높이를 더 크게 설정
        panel1.setPreferredSize(new Dimension(getWidth(), 120));
        add(panel1, BorderLayout.NORTH);

        // panel2는 중앙에 배치
        add(panel2, BorderLayout.CENTER);
        panel2.setLayout(new GridLayout(3, 1, 15, 15));
        panel2.setBorder(new EmptyBorder(30, 30, 30, 30));


//        filePanel(panel2);
        for (int i = 0; i < 3; i++) {
            createFilePans(panel2, i);
        }

    }

    private JPanel topPanel(boolean isLoadRequest) {

        JPanel tp = new JPanel();
        tp.setLayout(new BorderLayout());
        // 텍스트를 바로 설정
        String str = isLoadRequest ? "어떤 파일을 로드하시겠습니까?" : "어떤 파일에 저장하시겠습니까?";
        JLabel lbl = new JLabel(str, JLabel.CENTER);
        lbl.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        tp.add(lbl, BorderLayout.CENTER);

        // 조건에 따라 버튼 추가
        if (!isLoadRequest)
            tp.add(returnBtn(), BorderLayout.WEST);

        return tp;
    }



    public void createFilePans(JPanel panel, int index) {
        JPanel fp = filePanel(index);

        fp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //로드 화면일 때
                if(isLoadRequest) {
                    Player.setSelectedIdx(index);
                    mainController.switchTo("Start");
                    super.mouseClicked(e);
                }
                // 세이브 화면일 때
                if(!isLoadRequest) {
                    Player.getInstance();
                    mainController.savePlayerData(index);
                    mainController.switchTo("Game");
                    super.mouseClicked(e);
                }

            }
        });

        panel.add(fp);

    }




    private JPanel filePanel(int index) {
        // 데이터가 없으면 빈화면으로 뜨게 할 생각
        // 나중에 메소드 분리해서 검 객체로부터 받아오도록 할게요
        Player player = Player.getInstance(index);

        JPanel fp = new JPanel(new BorderLayout());


        Border padding = new EmptyBorder(10, 20, 10, 20); // 내부 여백
        Border line = BorderFactory.createLineBorder(Color.DARK_GRAY); // 외곽선
        fp.setBorder(new CompoundBorder(line, padding)); // CompoundBorder로 결합


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
        rightPanel.setLayout(new GridLayout(3, 1, 0, 10)); // 세로로 배치, 행 간격 10px


        // 정보 라벨
        JLabel nameLabel = new JLabel("검 이름: " + player.getNowSword().getName());
        JLabel strengthLabel = new JLabel("강화도 : " + player.getNowSword().getpossibility());
        JLabel moneyLabel = new JLabel("돈 : " + player.getMoney());
        JLabel dateLabel = new JLabel("저장일: " + player.getUpdateDate());

        // 오른쪽 패널에 라벨 추가
        rightPanel.add(nameLabel);
        rightPanel.add(strengthLabel);
        rightPanel.add(moneyLabel);
        rightPanel.add(dateLabel);

        fp.add(rightPanel, BorderLayout.CENTER);

        fp.revalidate();
        fp.repaint();

        fp.setBackground(Color.WHITE);
        leftPanel.setBackground(Color.WHITE);
        rightPanel.setBackground(Color.WHITE);

        fp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                fp.setBackground(Color.WHITE);
                leftPanel.setBackground(Color.WHITE);
                rightPanel.setBackground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                fp.setBackground(new Color(192, 192, 192));
                leftPanel.setBackground(new Color(192, 192, 192));
                rightPanel.setBackground(new Color(192, 192, 192));

            }
        });

        return fp;
    }

    private JButton returnBtn() {
        // 나중에 배경을 이미지 파일로 변경
        JButton jb = new JButton();
        jb.setText("돌아가기");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Game");
            }
        });
        return jb;
    }




}

