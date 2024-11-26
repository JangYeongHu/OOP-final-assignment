package com.screen;

import com.app.MainController;
import com.item.Sword;
import com.item.Ticket;
import com.item.interfaces.Item;
import com.player.Player;
import com.screen.interfaces.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameScreen extends JPanel implements Screen {

    static Player player;
    private JLabel money;
    private MainController mainController;
    private static boolean isSaveTicketActive = false;

    public static boolean getIsSaveTicketActive() {
        return isSaveTicketActive;
    }

    public void updateSaveTicketButton() {
        saveTicketButton.setBackground(isSaveTicketActive ? Color.GREEN : Color.GRAY);
        saveTicketButton.setText(isSaveTicketActive ? "파괴방지 활성화" : "파괴방지 비활성화");
    }

    public GameScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    private void topPanel() {
        JPanel Panel = new JPanel(null);
        Panel.setBackground(Color.GREEN);
        Panel.setBounds(20,20,300,80);
        money.setBounds(50,10,280,60);
        money.setFont(money.getFont().deriveFont(34.0f));
        Panel.add(money);
        add(Panel);
    }
    public void gamePanelUpdate(){
        removeAll();
        initialize();
        revalidate();
        repaint();
    }
    public void moneyPanelUpdate(){
        money.setText("돈 : "+player.getMoney());//돈갱신
        money.setVisible(true);
        revalidate();
        repaint();
}


    private void midPanel() {
        JPanel mid = new JPanel(new BorderLayout());
        setMidbutton(mid);
        add(mid, BorderLayout.CENTER);
    }

    private JButton swordUpgradeButton;
    private JButton swordSellButton;
    private static JButton saveTicketButton;
    JLabel swordNameLabel;
    private void setMidbutton(Container c) {
        JPanel imagePanel = new JPanel(new BorderLayout());//검이미지
        Sword sword = player.getNowSword();
        JLabel swordImage = new JLabel(sword.imageIcon());

        JPanel swordName = new JPanel(new BorderLayout());//검이름
        swordNameLabel = new JLabel(player.getNowSword().getName());
        swordNameLabel.setFont(swordNameLabel.getFont().deriveFont(50.0f));
        swordNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        swordNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        swordName.add(swordNameLabel,BorderLayout.CENTER);
        swordNameLabel.setForeground(Color.cyan);

        imagePanel.add(swordImage, BorderLayout.PAGE_START);//검이미지,검이름추가
        imagePanel.add(swordName);

        JPanel buttonList = new JPanel();//파괴방지권,판매하기,강화하기을 넣을곳
        buttonList.setLayout(new GridLayout(1, 3, 150, 130));
        saveTicketButton = new JButton("파괴방지 비활성화");
        saveTicketClickedEvent();
        buttonList.add(saveTicketButton);

        swordUpgradeButton = new JButton("강화하기");
        buttonList.add(upgradeButtonEvent(swordImage));

        swordSellButton = new JButton(MainController.swordList[player.getNowSword().getpossibility()-1].getsellPrice() + "원 판매하기");
        buttonList.add(sellButton(swordSellButton, swordImage));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonList,BorderLayout.PAGE_END);
        c.add(mainPanel);
    }

    private void saveTicketClickedEvent() {
        setButtonSize(saveTicketButton);
        saveTicketButton.setBackground(isSaveTicketActive ? Color.GREEN : Color.GRAY);
        saveTicketButton.setText(isSaveTicketActive ? "파괴방지 활성화" : "파괴방지 비활성화");

        saveTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count = findSaveTicket();
                if(player.getInventory().isEmpty()){
                    JOptionPane.showMessageDialog(null, "파괴방지권이 부족합니다");
                }
                else if(player.getInventory().get(count).getCount() > 0){
                    if (!isSaveTicketActive) {
                        player.getInventory().get(count).minCount();
                        saveTicketButton.setBackground(Color.GREEN);
                        saveTicketButton.setText("파괴방지 활성화");
                        isSaveTicketActive = true;
                    } else {
                        player.getInventory().get(count).addCount();
                        saveTicketButton.setBackground(Color.gray);
                        saveTicketButton.setText("파괴방지 비활성화");
                        isSaveTicketActive = false;
                    }
                }
                mainController.updateInventoryScreen();
                mainController.updateStoreScreen();
            }
        });
    }

    private int findSaveTicket(){
        int count = 0;
        for (int i = 0; i < player.getInventory().size(); i++){
            Item saveTicket = player.getInventory().get(i);
            if(saveTicket.match("파괴방지권")){
                count = i;
                break;
            }
        }
        return count;
    }

    public static void setIsSaveTicketActive(boolean Active){
        isSaveTicketActive = Active;
    }
    public static void pushTicketActive(){
        int pSword = player.getNowSword().getpossibility();
        if(MainController.swordList.length > pSword){
            player.setNowSword(MainController.swordList[pSword]);
        }else{
            System.out.println("maxPossibility");
        }
    }
    public static void upgradeTicketActive(int i){
        player.setNowSword(MainController.swordList[i]);
    }
    private JButton upgradeButtonEvent(JLabel image) {
        swordUpgradeButton.setBackground(Color.yellow);
        setButtonSize(swordUpgradeButton);
        swordUpgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sword nowSword = player.getNowSword();
                player.doUpgradeSword();//돈소모 현재쓰는방식은 임시방편
                if (player.getMoney() > nowSword.getUpgradeFee()){//player의 돈이 강화비용보닫 많을경우에만
                    if (nowSword.upgradeProbability()) {
                        success(image,nowSword.getpossibility());
                    } else {
                        fall(image);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "돈이부족합니다");//세팅스크린에서빌려왔음
                }
                moneyPanelUpdate();
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


    private void fall(JLabel Image) {
        if (saveTicketButton.getBackground() != Color.GREEN) {
            Sword nextSword = MainController.swordList[0];
            player.setNowSword(MainController.swordList[0]);
            swordNameLabel.setText(nextSword.getName());
            swordSellButton.setText((MainController.swordList[0].getsellPrice() + "원 \n판매하기"));
            Image.setIcon(nextSword.imageIcon());
        } else {
            saveTicketButton.setBackground(Color.gray);
            saveTicketButton.setText("파괴방지 비활성화");
            isSaveTicketActive = false;
            JOptionPane.showMessageDialog(null, "파괴방어");

        }
    }

    private JButton sellButton(JButton button, JLabel Image) {
        setButtonSize(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//player에 돈넣어주는것추가하기
                player.soldSword(MainController.swordList[0]);//n번째검 판매 > 0번째검으로 초기화
                swordUpgradeButton.setText("강화하기");
                swordSellButton.setText(("판매하기"));
                swordNameLabel.setText(player.getNowSword().getName());
                Image.setIcon(player.getNowSword().imageIcon());
                money.setText("돈 : " + player.getMoney());
                moneyPanelUpdate();
            }
        });
        return button;
    }


    private void setButtonSize(Container c) {
        c.setPreferredSize(new Dimension(300, 100));
        c.setFont(c.getFont().deriveFont(24.0f));
    }

    private void bottomPanel() {
        JPanel bottom = new JPanel(new GridLayout(1, 4));
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
                LoadScreen.getInstance().setLoadRequest(false);
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
        player = Player.getInstance();
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
//class GameClearScreen extends JPanel implements Screen{//클리어하면 나오는 화면
//    public void setting(){
//        setLayout(new BorderLayout());
//    }
//    @Override
//    public void initialize() {
//    }
//
//
//    @Override
//    public void showScreen() {
//        setVisible(true);
//    }
//
//    @Override
//    public void hideScreen() {
//        setVisible(false);
//    }
//}