package src.screen;

import src.item.Sword;
import src.player.Player;
import src.screen.interfaces.Screen;
import src.main.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameScreen extends JPanel implements Screen {
    //test
    static Sword[] Slist = new Sword[20];
    static int number = 0;//현재강화도

    static void CreateSword(){
        for (int i = 1; i < 21; i++){
            Slist[i-1] =  new Sword("res/"+i+".png",i);
            Slist[i-1].SetswordDescription(i+"번째 검의 설명");
            Slist[i-1].SetswordName(i+"번째 검");
        }
    }

    Player player = new Player();
    JLabel money = new JLabel("돈 : "+player.getMoney());
    //player.getnowSword()으로 지금 검 상태를 갖고와야하는데 시작검 상태가 없다 생성자로 하나를 넣는다?
    private MainController mainController;
    public GameScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    private void topPanel(){
        JPanel top = new JPanel(new GridLayout(1, 3));
        top.setPreferredSize(new Dimension(1200, 50));

        top.setBackground(Color.green);
        topbutton(top);
        add(top, BorderLayout.PAGE_START);
    }

    private void topbutton(Container j){
        JButton MainButton = new JButton("메인으로 돌아가기");
        JButton SaveButton = new JButton("저장하기");
        j.add(MainButton, BorderLayout.LINE_START);
        j.add(money, BorderLayout.CENTER);
        money.setFont(money.getFont().deriveFont(24.0f));
        j.add(SaveButton, BorderLayout.PAGE_END);
    }
    private void SaveButton(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//저장하는법 알아내고 작성
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

    private void midPanel(){
        JPanel mid = new JPanel();
        midbutton(mid);
        add(mid, BorderLayout.CENTER);
    }

    JButton SwordUp;
    JButton SwordSell;
    JButton SwordSave;

    private void midbutton(Container c){//파괴방지권,판매하기,강화하기
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Sword sword = Slist[number];//player에서 현재검 강화도를 가져올예정
        JLabel SwordImage = new JLabel(sword.imageicon());
        imagePanel.add(SwordImage, BorderLayout.CENTER);

        JPanel buttonList = new JPanel();
        buttonList.setLayout(new GridLayout(1,3,150,130)); // 간격 조정

        SwordSave = ticket(new JButton("파괴방지 비활성화"));
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
    private JButton ticket(JButton button){
        buttonSetSize(button);
        button.setBackground(Color.gray);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//누르면 색깔변경 초록색
                if(ticketActivate(button)){
                    button.setBackground(Color.GREEN);
                    button.setText("파괴방지 활성화");
                }
                else{
                    button.setBackground(Color.gray);
                    button.setText("파괴방지 비활성화");
                }
            }
        });
        return button;
    }
    private boolean ticketActivate(JButton button){
        return button.getBackground() != Color.GREEN;
    }


    private JButton UpButton(JLabel Image, JButton button){
        SwordUp.setBackground(Color.yellow);
        buttonSetSize(SwordUp);
        SwordUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //number = 19;
                if (number <= 19) {//게임클리어 화면을만들거나 강화를 못하게 하거나 할것임\
                    //GameClearScreen.Clear();
                    System.out.println("게임클리어");
                }
                if(Slist[number].upgrade_probability()){
                    Sword Upsword = Slist[++number];
                    player.setNowSword(Upsword);
                    SwordUp.setText(Slist[number].getName()+" 강화하기");
                    SwordSell.setText((Slist[number].getsellPrice()+"원 \n판매하기"));
                    Image.setIcon(Upsword.imageicon());
                }else{
                    FallS(Image,button);
                }
            }
        });
        return SwordUp;
    }
    private void FallS(JLabel Image,JButton button){
        if(ticketActivate(button)){//초록색이 아니면 = 비활성화
            number = 0;
            SwordUp.setText("강화하기");
            Sword Upsword = Slist[number];
            Image.setIcon(Upsword.imageicon());
        }
        else{//나중에 인벤토리안에 있는 티켓에서 -1 하고 색깔을 다시 그레이색으로 바꾼다
            button.setBackground(Color.gray);
            button.setText("파괴방지 비활성화");
            System.out.println("파괴방어!!");
        }
    }


    private JButton SellButton(JButton button, JLabel Image){
        buttonSetSize(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//player에 돈넣어주는것추가하기
                player.setMoney(Slist[number].getsellPrice());
                number = 0;
                money.setText("돈 : "+player.getMoney());
                SwordUp.setText("강화하기");
                SwordSell.setText((Slist[number].getsellPrice()+"원 \n판매하기"));
                Image.setIcon(Slist[number].imageicon());
            }
        });
        return button;
    }



    private void buttonSetSize(Container c){
        c.setPreferredSize(new Dimension(300,150));
    }

    private void bottomPanel() {
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
        topPanel();
        midPanel();
        bottomPanel();
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