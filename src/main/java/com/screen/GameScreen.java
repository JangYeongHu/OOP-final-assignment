package com.screen;

import com.app.Main;
import com.app.MainController;
import com.item.Sword;
import com.player.Player;
import com.screen.interfaces.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameScreen extends JPanel implements Screen {

    static Player player;
    static JLabel money;

    private MainController mainController;

    public GameScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    private void topPanel() {
        JPanel top = new JPanel(new GridLayout(1, 3));
        top.setPreferredSize(new Dimension(1200, 50));

        top.setBackground(Color.green);
        topbutton(top);
        add(top, BorderLayout.PAGE_START);
    }

    private void topbutton(Container j) {
        JButton MainButton = new JButton("메인으로 돌아가기");
        JButton SaveButton = new JButton("저장하기");
        j.add(MainButton, BorderLayout.LINE_START);
        j.add(money, BorderLayout.CENTER);
        money.setFont(money.getFont().deriveFont(24.0f));
        j.add(SaveButton, BorderLayout.PAGE_END);
        saveButton_E(SaveButton);
        mainButton_E(MainButton);
    }

    private void saveButton_E(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//저장하는법 알아내고 작성
                LoadScreen.getInstance().setLoadRequest(false);
                mainController.switchTo("Load");
            }
        });
    }

    private void mainButton_E(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Start");
            }
        });
    }

    private void MidPanel() {
        JPanel mid = new JPanel();
        Midbutton(mid);
        add(mid, BorderLayout.CENTER);
    }

    private JButton swordUpgradeButton;
    private JButton swordSellButton;
    private JButton saveTicketButton;

    private void Midbutton(Container c) {//파괴방지권,판매하기,강화하기
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Sword sword = player.getNowSword();//player에서 현재검 강화도를 가져올예정
        JLabel SwordImage = new JLabel(sword.imageIcon());
        imagePanel.add(SwordImage, BorderLayout.CENTER);

        JPanel buttonList = new JPanel();
        buttonList.setLayout(new GridLayout(1, 3, 150, 130)); // 간격 조정

        saveTicketButton = Ticket(new JButton("파괴방지 비활성화"));
        buttonList.add(saveTicketButton);

        swordUpgradeButton = new JButton("강화하기");
        buttonList.add(UpButton(SwordImage, saveTicketButton));

        swordSellButton = new JButton("판매하기");
        buttonList.add(sellButton(swordSellButton, SwordImage));


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonList, BorderLayout.PAGE_END);
        c.add(mainPanel);
    }

    private JButton Ticket(JButton button) {
        setButtonSize(button);
        button.setBackground(Color.gray);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//누르면 색깔변경 초록색
                TicketActivate(button);
            }
        });
        return button;
    }

    private void TicketActivate(JButton button) {
        if (button.getBackground() != Color.GREEN) {
            button.setBackground(Color.GREEN);
            button.setText("파괴방지 활성화");
        } else {
            button.setBackground(Color.gray);
            button.setText("파괴방지 비활성화");
        }
    }


    private JButton UpButton(JLabel image, JButton button) {
        swordUpgradeButton.setBackground(Color.yellow);
        setButtonSize(swordUpgradeButton);
        swordUpgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //number = 19;
                int number = player.getNowSword().getpossibility();
                if (number >= 19) {//게임클리어 화면을만들거나 강화를 못하게 하거나 할것임\
                    //GameClearScreen.Clear();
                    System.out.println("게임클리어");
                    player.setNowSword(MainController.swordList[0]);//다음검을 플레이어 객체에넣기
                }
                if (MainController.swordList[number].UpgradeProbability()) {
                    Sword Upsword = MainController.swordList[number];//다음검뽑아오기
                    player.doUpgradeSword();//다음검을 플레이어 객체에넣기
                    swordUpgradeButton.setText(Upsword.getName() + " 강화하기");//버튼텍스트변경
                    swordSellButton.setText(Upsword.getsellPrice() + "원 \n판매하기");//버튼텍스트변경
                    image.setIcon(Upsword.imageIcon());//이미지변경
                } else {
                    fall(image, button);
                }
            }
        });
        return swordUpgradeButton;
    }

    private void fall(JLabel Image, JButton button) {
        if (button.getBackground() != Color.GREEN) {//초록색이 아니면 = 비활성화
            int number = 0;
            Sword Upsword = MainController.swordList[number];
            player.setNowSword(MainController.swordList[0]);
            swordUpgradeButton.setText("강화하기");
            swordSellButton.setText((MainController.swordList[number].getsellPrice() + "원 \n판매하기"));
            Image.setIcon(Upsword.imageIcon());
        } else {//나중에 인벤토리안에 있는 티켓에서 -1 하고 색깔을 다시 그레이색으로 바꾼다
            button.setBackground(Color.gray);
            button.setText("파괴방지 비활성화");
            System.out.println("파괴방어!!");
        }
    }


    private JButton sellButton(JButton button, JLabel Image) {
        setButtonSize(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//player에 돈넣어주는것추가하기
                player.soldSword(MainController.swordList[0]);//n번째검 판매 > 0번째검으로 초기화
                money.setText("돈 : " + player.getMoney());
                swordUpgradeButton.setText("강화하기");
                swordSellButton.setText((player.getNowSword().getsellPrice() + "원 \n판매하기"));
                Image.setIcon(player.getNowSword().imageIcon());
            }
        });
        return button;
    }


    private void setButtonSize(Container c) {
        c.setPreferredSize(new Dimension(300, 150));
    }

    private void BottomPanel() {
        JPanel bottom = new JPanel(new GridLayout(1, 2));
        bottom.setBackground(Color.cyan);
        bottom.setPreferredSize(new Dimension(1200, 100));


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

    private void updateSword() {

    };


    @Override
    public void initialize() {
        player = Player.getInstance();
        System.out.println(player.getNowSword().getName());
        money = new JLabel("money : " + player.getMoney());
        setLayout(new BorderLayout());
        topPanel();
        MidPanel();
        BottomPanel();
    }


    @Override
    public void showScreen() {
        setVisible(true);
        updateSword();
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