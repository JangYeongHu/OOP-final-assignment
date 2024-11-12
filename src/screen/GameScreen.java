package src.screen;

import src.item.Sword;
import src.player.Player;
import src.screen.interfaces.Screen;
import src.main.MainController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreen extends JPanel implements Screen {
    Player player = new Player();
    JLabel money = new JLabel("돈 : "+player.getMoney());
    private MainController mainController;
    public GameScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    void topPanel(){
        JPanel top = new JPanel(new GridLayout(1, 3));
        top.setPreferredSize(new Dimension(1200, 50));

        top.setBackground(Color.green);
        button(top);
        add(top, BorderLayout.PAGE_START);
    }

    void button(Container j){
        JButton MainButton = new JButton("메인으로 돌아가기");
        JButton SaveButton = new JButton("저장하기");
        j.add(MainButton, BorderLayout.LINE_START);
        j.add(money, BorderLayout.CENTER);
        money.setFont(money.getFont().deriveFont(24.0f));
        j.add(SaveButton, BorderLayout.PAGE_END);
    }
    void midPanel(){
        JPanel mid = new JPanel(new GridLayout(3,3));

        midbutton(mid);
        add(mid, BorderLayout.SOUTH);
    }
    void midbutton(Container c){//파괴방지권,판매하기,강화하기
        JButton SwordSave = new JButton("파괴방지권 사용");
        SwordSave.setPreferredSize(new Dimension(140,50));
//
//        ImageIcon imageicon = new ImageIcon("res/1.png");//따로 소드에서 구현한걸 쓸예정
//        Image image = imageicon.getImage();
//        int newWidth = 300;
//        int newHeight = 300;
//        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
//        ImageIcon resizedIcon = new ImageIcon(resizedImage);
//        JLabel ja =new JLabel(resizedIcon);

        c.add(new JButton("강화하기"));
        c.add(SwordSave);
        c.add(new JButton("판매하기"));
    }

    void bottomPanel(){//인벤토리,상점

    }
    void bottombutton(Container c){
    }

    @Override
    public void initialize() {
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
    static void CreateSword(){
        for (int i = 1; i < 21; i++){
            Slist.add(new Sword(i+"",i+"번째 검이다","res/"+i+".png"));
        }
    }

    String imageSword(int i){
        return "";
    }
}
