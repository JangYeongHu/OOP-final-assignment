package com.screen;

import com.app.MainController;
import com.screen.interfaces.Screen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class LoadScreen extends JPanel implements Screen {
    private MainController mainController;
    private boolean isLoadRequest = false;

    public LoadScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    private void loadData() {

    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());

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

        panel2.add(filepanel());
        panel2.add(filepanel());
        panel2.add(filepanel());


    }

    @Override
    public void showScreen() {
        setVisible(true);
    }

    @Override
    public void hideScreen() {
        setVisible(false);
    }



    private JPanel topPanel(boolean isLoadRequest) {

        JPanel tp = new JPanel();
        tp.setLayout(new BorderLayout());
        String str = isLoadRequest ? "어떤 파일을 로드하시겠습니까?" : "어떤 파일에 저장하시겠습니까?";
        JLabel lbl = new JLabel(str, JLabel.CENTER);


        lbl.setAlignmentX(JLabel.CENTER_ALIGNMENT); // X축 가운데 정렬
        lbl.setAlignmentY(JLabel.CENTER_ALIGNMENT); // Y축 가운데 정렬
        lbl.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        tp.add(returnBtn(), BorderLayout.WEST);
        tp.add(lbl, BorderLayout.CENTER);

        return tp;
    }




    private JPanel filepanel() {
        // 데이터가 없으면 빈화면으로 뜨게 할 생각
        // 나중에 메소드 분리해서 검 객체로부터 받아오도록 할게요
        JPanel fp = new JPanel(new BorderLayout());


        Border padding = new EmptyBorder(10, 20, 10, 20); // 내부 여백
        Border line = BorderFactory.createLineBorder(Color.DARK_GRAY); // 외곽선
        fp.setBorder(new CompoundBorder(line, padding)); // CompoundBorder로 결합


        // 검 이미지 패널
        JPanel leftPanel = new JPanel(new BorderLayout());

        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/1.png"));
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
        JLabel nameLabel = new JLabel("검 이름");
        JLabel strengthLabel = new JLabel("강화도 : 3");
        JLabel moneyLabel = new JLabel("돈 :");
        JLabel dateLabel = new JLabel("저장일");

        // 오른쪽 패널에 라벨 추가
        rightPanel.add(nameLabel);
        rightPanel.add(strengthLabel);
        rightPanel.add(moneyLabel);
        rightPanel.add(dateLabel);

        fp.add(rightPanel, BorderLayout.CENTER);

        fp.revalidate();
        fp.repaint();

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



//    public static void main(String[] args) {
//        //테스트를 위한 메인
//        MainController mainController = null; // 실제 구현에 따라 초기화
//
//        // JFrame 생성
//        JFrame frame = new JFrame("Load Screen Example");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1200, 800);
//
//        // LoadScreen 패널 추가
//        LoadScreen loadScreen = new LoadScreen(mainController);
//        frame.add(loadScreen);
//
//        // 화면 표시
//        frame.setVisible(true);
//    }



}

