package com.screen;

import com.app.MainController;
import com.item.Sword;
import com.player.Player;
import com.screen.interfaces.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class GameScreen extends JPanel implements Screen {
    //testss
    static Sword[] Slist = new Sword[20];
    static Player player = new Player();
    JLabel money = new JLabel("돈 : "+player.getMoney());

    static void CreateSword(){
        for (int i = 1; i < 21; i++){
            Slist[i-1] =  new Sword("src/main/resources/"+i+".png",i);
            Slist[i-1].setSwordDescription(i+"번째 검의 설명");
            Slist[i-1].setSwordName(i+"번째 검");
        }
        if(player.getNowSword() == null){
            player.setNowSword(Slist[0]);//초기 플레이어 검생성
        }
    }

    private MainController mainController;
    public GameScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    private void TopPanel(){
        JPanel top = new JPanel(new GridLayout(1, 3));
        top.setPreferredSize(new Dimension(1200, 50));

        top.setBackground(Color.green);
        Topbutton(top);
        add(top, BorderLayout.PAGE_START);
    }

    private void Topbutton(Container j){
        JButton MainButton = new JButton("메인으로 돌아가기");
        JButton SaveButton = new JButton("저장하기");
        j.add(MainButton, BorderLayout.LINE_START);
        j.add(money, BorderLayout.CENTER);
        money.setFont(money.getFont().deriveFont(24.0f));
        j.add(SaveButton, BorderLayout.PAGE_END);
        SaveButton(SaveButton);
    }
    private void SaveButton(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//저장하는법 알아내고 작성
                mainController.switchTo("Load");
            }
        });
    }
    private void MainButton(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Start");
            }
        });
    }

    private void MidPanel(){
        JPanel mid = new JPanel();
        Midbutton(mid);
        add(mid, BorderLayout.CENTER);
    }

    JButton SwordUp;
    JButton SwordSell;
    JButton SwordSave;

    private void Midbutton(Container c){//파괴방지권,판매하기,강화하기
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Sword sword = player.getNowSword();//player에서 현재검 강화도를 가져올예정
        JLabel SwordImage = new JLabel(sword.Imageicon());
        imagePanel.add(SwordImage, BorderLayout.CENTER);

        JPanel buttonList = new JPanel();
        buttonList.setLayout(new GridLayout(1,3,150,130)); // 간격 조정

        SwordSave = Ticket(new JButton("파괴방지 비활성화"));
        buttonList.add(SwordSave);

        SwordUp = new JButton("강화하기");
        buttonList.add(UpButton(SwordImage, SwordSave));

        SwordSell = new JButton("판매하기");
        buttonList.add(SellButton(SwordSell,SwordImage));


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonList, BorderLayout.PAGE_END);
        c.add(mainPanel);
    }
    private JButton Ticket(JButton button){
        ButtonSetSize(button);
        button.setBackground(Color.gray);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//누르면 색깔변경 초록색
                TicketActivate(button);
            }
        });
        return button;
    }
    private void TicketActivate(JButton button){
        if(button.getBackground() != Color.GREEN){
            button.setBackground(Color.GREEN);
            button.setText("파괴방지 활성화");
        }
        else{
            button.setBackground(Color.gray);
            button.setText("파괴방지 비활성화");
        }
    }


    private JButton UpButton(JLabel Image, JButton button){
        SwordUp.setBackground(Color.yellow);
        ButtonSetSize(SwordUp);
        SwordUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //number = 19;
                int number = player.getNowSword().getpossibility();
                if (number >= 19) {//게임클리어 화면을만들거나 강화를 못하게 하거나 할것임\
                    //GameClearScreen.Clear();
                    System.out.println("게임클리어");
                    player.setNowSword(Slist[0]);//다음검을 플레이어 객체에넣기
                }
                if(Slist[number].UpgradeProbability()){
                    Sword Upsword = Slist[number];//다음검뽑아오기
                    player.doUpgradeSword(Upsword);//다음검을 플레이어 객체에넣기
                    SwordUp.setText(Upsword.getName()+" 강화하기");//버튼텍스트변경
                    SwordSell.setText(Upsword.getsellPrice()+"원 \n판매하기");//버튼텍스트변경
                    Image.setIcon(Upsword.Imageicon());//이미지변경
                }else{
                    Fall(Image,button);
                }
            }
        });
        return SwordUp;
    }
    private void Fall(JLabel Image,JButton button){
        if(button.getBackground() != Color.GREEN){//초록색이 아니면 = 비활성화
            int number = 0;
            Sword Upsword = Slist[number];
            player.setNowSword(Slist[0]);
            SwordUp.setText("강화하기");
            SwordSell.setText((Slist[number].getsellPrice()+"원 \n판매하기"));
            Image.setIcon(Upsword.Imageicon());
        }
        else{//나중에 인벤토리안에 있는 티켓에서 -1 하고 색깔을 다시 그레이색으로 바꾼다
            button.setBackground(Color.gray);
            button.setText("파괴방지 비활성화");
            System.out.println("파괴방어!!");
        }
    }


    private JButton SellButton(JButton button, JLabel Image){
        ButtonSetSize(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//player에 돈넣어주는것추가하기
                player.soldSword(Slist[0]);//n번째검 판매 > 0번째검으로 초기화
                money.setText("돈 : "+player.getMoney());
                SwordUp.setText("강화하기");
                SwordSell.setText((player.getNowSword().getsellPrice()+"원 \n판매하기"));
                Image.setIcon(player.getNowSword().Imageicon());
            }
        });
        return button;
    }



    private void ButtonSetSize(Container c){
        c.setPreferredSize(new Dimension(300,150));
    }

    private void BottomPanel() {
        JPanel bottom = new JPanel(new GridLayout(1,2));
        bottom.setBackground(Color.cyan);
        bottom.setPreferredSize(new Dimension(1200,100));


        JButton GoStore = new JButton("상점으로");
        bottom.add(GoStore);
        GoStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Store");
            }
        });
        JButton GoInventory = new JButton("인벤토리");
        GoInventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Inventory");
            }
        });
        bottom.add(GoInventory);
        add(bottom, BorderLayout.PAGE_END);
    }


    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        CreateSword();
        TopPanel();
        MidPanel();
        BottomPanel();
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
//class GameClearScreen extends JPanel{
//    public static void Clear() {
//
//    }
//    public void setting(){
//        setLayout(new BorderLayout());
//
//    }
//}클리어화면 만들고싶다