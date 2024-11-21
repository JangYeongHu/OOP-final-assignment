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

    static Player player;
    static JLabel money;
    private MainController mainController;

    public GameScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    private void topPanel() {
        JPanel Panel = new JPanel(new BorderLayout());
        Panel.setPreferredSize(new Dimension(0, 70));

        setTopButton(Panel);
        add(Panel, BorderLayout.PAGE_START);
    }

    private void setTopButton(Container j) {
        JLabel tite;
        j.setBackground(Color.ORANGE);
        money.setFont(money.getFont().deriveFont(34.0f));
        j.add(money, BorderLayout.CENTER);
    }

    private void midPanel() {
        JPanel mid = new JPanel();
        setMidbutton(mid);
        add(mid, BorderLayout.CENTER);
    }

    private JButton swordUpgradeButton;
    private JButton swordSellButton;
    JLabel swordNameLabel;
    private void setMidbutton(Container c) {//파괴방지권,판매하기,강화하기
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Sword sword = player.getNowSword();//player에서 현재검 강화도를 가져올예정
        JLabel swordImage = new JLabel(sword.imageIcon());
        imagePanel.add(swordImage, BorderLayout.CENTER);

        JPanel buttonList = new JPanel();
        buttonList.setLayout(new GridLayout(1, 3, 150, 130)); // 간격 조정

        JButton saveTicketButton = saveTicketClickedEvent(new JButton("파괴방지 비활성화"));
        buttonList.add(saveTicketButton);

        swordUpgradeButton = new JButton("강화하기");
        buttonList.add(upgradeButtonEvent(swordImage, saveTicketButton));

        swordSellButton = new JButton("판매하기");
        buttonList.add(sellButton(swordSellButton, swordImage));


        JPanel mainPanel = new JPanel(new BorderLayout(10,8));

        JPanel swordName = new JPanel();
        swordNameLabel = new JLabel(player.getNowSword().getName());
        swordNameLabel.setFont(swordNameLabel.getFont().deriveFont(50.0f));
        swordName.add(swordNameLabel);
        swordName.setBackground(Color.lightGray);
        swordName.setPreferredSize(new Dimension(1000,80));
        mainPanel.add(imagePanel, BorderLayout.PAGE_START);
        mainPanel.add(swordName, BorderLayout.CENTER);
        mainPanel.add(buttonList, BorderLayout.PAGE_END);
        c.add(mainPanel);
    }

    private JButton saveTicketClickedEvent(JButton button) {
        setButtonSize(button);
        button.setBackground(Color.gray);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//누르면 색깔변경 초록색
                if (button.getBackground() != Color.GREEN) {
                    button.setBackground(Color.GREEN);
                    button.setText("파괴방지 활성화");
                } else {
                    button.setBackground(Color.gray);
                    button.setText("파괴방지 비활성화");
                }
            }
        });
        return button;
    }


    private JButton upgradeButtonEvent(JLabel image, JButton button) {
        swordUpgradeButton.setBackground(Color.yellow);
        setButtonSize(swordUpgradeButton);
        swordUpgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//누를때 마다 돈소모하게 하고 돈이 업그레이드 돈보다 적으면 강화가 안되게
                Sword nowSword = player.getNowSword();
                if (player.getMoney() > nowSword.getUpgradeFee()){//player의 돈이 강화비용보닫 많을경우에만
                    player.setMoney(player.getMoney()-player.getNowSword().setupgradeFee());//player.doUpgradeSword();//돈소모 현재쓰는방식은 임시방편
                    money.setText("돈 : "+player.getMoney());
                    if (nowSword.upgradeProbability()) {
                        success(image,nowSword.getpossibility());
                    } else {
                        fall(image, button);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "돈이부족합니다");//세팅스크린에서빌려왔음
                }
            }
        });
        return swordUpgradeButton;
    }

//엔딩스크린아동
//    private void ending(int number){
//        if (number >= 19) {//게임클리어 화면을만들거나 강화를 못하게 하거나 할것임\
//            //GameClearScreen.Clear();
//            System.out.println("게임클리어");
//            player.setNowSword(MainController.swordList[0]);//다음검을 플레이어 객체에넣기
//        }
//    }
    private void success(JLabel image, int number){
        Sword nextSword = MainController.swordList[number];//다음검뽑아오기
        player.setNowSword(nextSword);//플레이어검 업그레이드
        swordNameLabel.setText(nextSword.getName());
        swordSellButton.setText(nextSword.getsellPrice() + "원 \n판매하기");//버튼텍스트변경
        image.setIcon(nextSword.imageIcon());//이미지변경
    }

    private void fall(JLabel Image, JButton button) {
        if (button.getBackground() != Color.GREEN) {//초록색이 아니면 = 비활성화
            Sword nextSword = MainController.swordList[0];
            player.setNowSword(MainController.swordList[0]);
            swordNameLabel.setText(nextSword.getName());
            swordSellButton.setText((MainController.swordList[0].getsellPrice() + "원 \n판매하기"));
            Image.setIcon(nextSword.imageIcon());
        } else {//나중에 인벤토리안에 있는 티켓에서 -1 하고 색깔을 다시 그레이색으로 바꾼다
            button.setBackground(Color.gray);
            button.setText("파괴방지 비활성화");
            JOptionPane.showMessageDialog(null, "파괴방어");//설정창에서 빌려왔습니다

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
        c.setPreferredSize(new Dimension(300, 100));
        c.setFont(c.getFont().deriveFont(24.0f));
    }

    private void bottomPanel() {
        JPanel bottom = new JPanel(new GridLayout(2, 2));
        bottom.setPreferredSize(new Dimension(1200, 80));
        setBottomButton(bottom);

        add(bottom, BorderLayout.PAGE_END);
    }
    private void setBottomButton(Container c){
        JButton openStoreButton = new JButton("상점으로");
        openStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Store");
            }
        });
        JButton openInventoryButton = new JButton("인벤토리");
        openInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Inventory");
            }
        });

        JButton openStartButton = new JButton("메인으로 돌아가기");
        openStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Start");
            }
        });
        JButton openSaveButton = new JButton("저장하기");
        openSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//저장하는법 알아내고 작성
                mainController.switchTo("Load");
            }
        });

        c.add(openStoreButton);
        c.add(openInventoryButton);
        c.add(openSaveButton);
        c.add(openStartButton);
    }





    @Override
    public void initialize() {
        player = Player.getInstance(0);
        money = new JLabel("돈 : " + player.getMoney());
        setLayout(new BorderLayout());
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
class GameClearScreen extends JPanel implements Screen{//클리어하면 나오는 화면
    public void setting(){
        setLayout(new BorderLayout());
    }
    @Override
    public void initialize() {
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