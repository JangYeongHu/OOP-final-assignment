package com.screen;

import com.app.MainController;
import com.screen.interfaces.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndingScreen extends JPanel implements Screen {
    private MainController mainController;
    private Image backgroundImage;
    public EndingScreen(MainController mainController) {
        this.mainController = mainController;
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("start.png")).getImage();
        initialize();
    }



    @Override
    public void initialize() {

        //1~20까지의 검이미지를 넣기?
        //엔딩 화면만 크게 보여주기
        setLayout(null);

        // 필요한 UI 요소 추가 (예: 버튼, 텍스트 등)
        JPanel ENDPanel = new JPanel(new GridLayout(6,1));
        ENDPanel.setBounds(250, 200, 700, 500); // 위치와 크기 설정
        JPanel ENDPanel2 = new JPanel(new BorderLayout());
        ENDPanel2.setBounds(400, 50, 400, 100); // 위치와 크기 설정
        JLabel END = new JLabel("THE END");
        END.setFont(new Font("DungGeunMo",Font.PLAIN,90));;
        END.setForeground(Color.BLACK);
        END.setHorizontalAlignment(JLabel.CENTER);
        END.setVerticalAlignment(JLabel.CENTER);
        ENDPanel2.add(END);

        ENDPanel.add(ENDINGCOMMENT("검 강화하기를 플레이 해주서 감사합니다!!!"));
        ENDPanel.add(ENDINGCOMMENT("조장 장영후 : 혹시 버그 쓰신거 아니죠?"));
        ENDPanel.add(ENDINGCOMMENT("조원 주호 : 이 운으로 로또를 샀으면.."));
        ENDPanel.add(ENDINGCOMMENT("조원 이경재 : 이걸 깨네"));
        ENDPanel.add(ENDINGCOMMENT("조원 박지연 : ..."));
        JLabel start = ENDINGCOMMENT("게임시작 화면으로 돌아가기");
        JButton reStart = new JButton();
        reStart.add(start);
        reStart.setBackground(new Color(64, 83, 76));
        reStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("start");
            }
        });
        ENDPanel.add(reStart);
        ENDPanel.setBackground(Color.white);

        add(ENDPanel2);
        add(ENDPanel);

    }
    private JLabel ENDINGCOMMENT(String a){
        JLabel EndingComment= new JLabel(a);
        EndingComment.setHorizontalAlignment(JLabel.CENTER);
        EndingComment.setVerticalAlignment(JLabel.CENTER);
        EndingComment.setFont(new Font("DungGeunMo",Font.PLAIN,30));
        return EndingComment;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(backgroundImage!=null){
            g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),this);
        }
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
