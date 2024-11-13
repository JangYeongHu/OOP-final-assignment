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
        JPanel mid = new JPanel(new GridLayout(3,1));

        midbutton(mid);
        add(mid, BorderLayout.SOUTH);
    }

    JButton SwordUp = new JButton("강화하기");

    private void midbutton(Container c){//파괴방지권,판매하기,강화하기

        JPanel buttonList = new JPanel();
        buttonList.setLayout(new FlowLayout(FlowLayout.CENTER,200,20));

        Sword sword = Slist[number];//첫번째검
        JLabel SwordImage = new JLabel(sword.imageicon());
        c.add(SwordImage, BorderLayout.CENTER);

        JButton SwordSave = new JButton("파괴방지권 사용");
        buttonSetSize(SwordSave);
        buttonList.add(SwordSave);

        buttonList.add(UpButton(SwordImage));

        JButton SwordSell = SellButton(new JButton("판매하기"),SwordImage);
        buttonList.add(SwordSell);

        c.add(buttonList);
    }
    private void ticket(){

    }
    private JButton UpButton(JLabel Image){

        SwordUp.setBackground(Color.yellow);
        buttonSetSize(SwordUp);

        SwordUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Slist[number].upgrade_probability()){
                    Sword Upsword = Slist[++number];
                    SwordUp.setText(Slist[number].getName()+" 강화하기");
                    Image.setIcon(Upsword.imageicon());
                }else{
                    number = 0;
                    Sword Upsword = Slist[number];
                    SwordUp.setText("강화하기");
                    Image.setIcon(Upsword.imageicon());
                }
            }
        });

        return SwordUp;
    }

    private JButton SellButton(JButton button, JLabel Image){
        buttonSetSize(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//player에 돈넣어주는것추가하기
                player.setMoney(Slist[number].getSellPrice());
                number = 0;
                money.setText("돈 : "+player.getMoney());
                SwordUp.setText("강화하기");
                Image.setIcon(Slist[number].imageicon());
            }
        });
        return button;
    }



    private void buttonSetSize(Container c){
        c.setPreferredSize(new Dimension(200,50));
    }


    @Override
    public void initialize() {
        CreateSword();
        topPanel();
        midPanel();
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
class Forges {
    //미리 검을 만드는 방법 vs 검을 강화를 성공을 하면 만드는것 임시로 검만드는 위치를 여기로했습니다
    //검이름과 설명을 읽는 작업이 필요함
    //검이름, 검의 이미지 임시로 이름을 1,2,---,20으로 정함 !!임시!!
    //대장간안에 검이 있다는 느낌으로 여기있는 검을 움직일것
    static ArrayList<Sword> Slist = new ArrayList<>();

    String imageSword(int i){
        return "";
    }
}
