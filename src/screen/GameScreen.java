package src.screen;

import src.item.Sword;
import src.player.Player;
import src.screen.interfaces.Screen;
import src.main.MainController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreen extends JPanel implements Screen {
    ArrayList<Sword> Slist = new ArrayList<>();
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
        j.add(SaveButton, BorderLayout.LINE_END);
    }
    void midPanel(){
        JPanel mid = new JPanel(new GridLayout(1,2));


        JButton SwordSave = new JButton("파괴방지권 사용");
        SwordSave.setPreferredSize(new Dimension(100,100));
        add(SwordSave);

        add(mid, BorderLayout.CENTER);
    }
    void midbutton(Container c){//색깔로 사용유무확인
    }

    void bottomPanel(){

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
