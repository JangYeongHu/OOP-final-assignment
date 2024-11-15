package com.screen;

import com.app.MainController;
import com.screen.interfaces.Screen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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
        JPanel panel1 = new JPanel();

        //패딩 설정
        panel1.setBorder(new EmptyBorder(20, 30, 20, 30));
        JPanel panel2 = new JPanel();

        // 배경색 설정 - 구분용
        panel1.setBackground(Color.WHITE);
        panel2.setBackground(Color.CYAN);

        // panel1은 상단에 배치, 패널 높이를 더 크게 설정
        panel1.setPreferredSize(new Dimension(getWidth(), 120));
        add(panel1, BorderLayout.NORTH);

        // panel2는 중앙에 배치
        add(panel2, BorderLayout.CENTER);



        panel1.setLayout(new BorderLayout());
        panel1.add(topPanel(isLoadRequest));
//        loadData();
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
        lbl.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        tp.add(lbl, BorderLayout.CENTER);

        return tp;
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

