package com.screen;

import com.app.MainController;
import com.item.Sword;
import com.item.interfaces.Item;
import com.player.Player;
import com.screen.interfaces.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

public class GameScreen extends JPanel implements Screen {

    static Player player;
    private JLabel money;
    private JLabel finalDestination;
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
        Panel.setBackground(Color.yellow);
        Panel.setBounds(20,20,300,80);
        money.setBounds(30,10,280,60);
        replaceFont(money,40);
        Panel.add(money);
        add(Panel);
        menuPanel();
    }

    public void gamePanelUpdate(){
        removeAll();
        initialize();
        revalidate();
        repaint();
    }
    public void moneyPanelUpdate(){
        money.setVisible(true);
        revalidate();
        repaint();
        textReplace();
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
        replaceFont(swordNameLabel,60);
        swordNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        swordNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        swordName.add(swordNameLabel);
        swordNameLabel.setForeground(Color.white);

        imagePanel.add(swordImage);//검이미지,검이름추가
        imagePanel.add(swordName, BorderLayout.PAGE_END);

        JPanel buttonList = new JPanel();//파괴방지권,판매하기,강화하기을 넣을곳
        buttonList.setLayout(new GridLayout(1, 3, 150, 0));
        saveTicketButton = new JButton("파괴방지 비활성화");
        saveTicketClickedEvent();
        buttonList.add(saveTicketButton);

        swordUpgradeButton = new JButton("강화하기");
        buttonList.add(upgradeButtonEvent(swordImage));

        swordSellButton = new JButton(MainController.swordList[player.getNowSword().getpossibility()-1].getsellPrice() + "원 판매하기");
        buttonList.add(sellButton(swordSellButton, swordImage));

        JPanel mainPanel = new JPanel(new BorderLayout(50,30));
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonList,BorderLayout.PAGE_END);

        PanelBackGroundColor(mainPanel);
        PanelBackGroundColor(imagePanel);
        PanelBackGroundColor(swordImage);
        PanelBackGroundColor(buttonList);
        PanelBackGroundColor(swordName);

        c.add(mainPanel);
    }

    private void PanelBackGroundColor(Container c){
        c.setBackground(new Color(64, 83, 76));
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
        image.setIcon(nextSword.imageIcon());//이미지변경
    }

/*
*
* */
    private void fall(JLabel Image) {
        if (saveTicketButton.getBackground() != Color.GREEN) {
            Sword nextSword = MainController.swordList[0];
            player.setNowSword(MainController.swordList[0]);
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
                Image.setIcon(player.getNowSword().imageIcon());
                moneyPanelUpdate();
            }
        });
        return button;
    }

    private void menuPanel() {
        JButton openInventoryButton = new JButton("인벤토리");
        openInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Inventory");
            }
        });
        JButton openStoreButton = new JButton("상점으로");
        openStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Store");
            }
        });
        openStoreButton.setBounds(20,140,300,100);
        openInventoryButton.setBounds(20,260,300,100);
        setButtonSize(openStoreButton);
        setButtonSize(openInventoryButton);
        add(openStoreButton);
        add(openInventoryButton);



        //JButton endSwordCount = new JButton("남은 강화 : "+(20-player.getNowSword().getpossibility()));
        JPanel endSwordCount = new JPanel();
        finalDestination = new JLabel("목표까지 "+(20-player.getNowSword().getpossibility())+"강");
        endSwordCount.setBounds(900,20,300,50);
        endSwordCount.setBackground(Color.white);
        replaceFont(finalDestination,30);
        endSwordCount.add(finalDestination);
        add(endSwordCount);
    }

    @Override
    public void initialize() {
        popup();
        player = Player.getInstance();
        money = new JLabel("돈 : " + player.getMoney());
        finalDestination = new JLabel("목표까지 "+(20-player.getNowSword().getpossibility())+"강");
        setLayout(new BorderLayout());
        topPanel();
        midPanel();

    }


    private void setButtonSize(JButton c) {
        c.setPreferredSize(new Dimension(300, 100));
        c.setBackground(new Color(103, 125, 106));
        Color color = new Color(214, 189, 152);
        c.setForeground(color);
        replaceFont(c,30);
        c.setFocusPainted(false);
        c.setBorder(BorderFactory.createLineBorder(color, 8));
    }

    private void replaceFont(Container c, int size){
        try{
            InputStream fontStream = getClass().getResourceAsStream("/DungGeunMo.ttf");
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            Font newFont = baseFont.deriveFont((float) size);
            c.setFont(newFont);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("not find font2");
        }
    }
    private void textReplace(){
        money.setText("돈 : " + player.getMoney());
        swordNameLabel.setText(player.getNowSword().getName());
        swordSellButton.setText(player.getNowSword().getsellPrice() + "원 \n판매하기");
        finalDestination.setText("목표까지 "+(20-player.getNowSword().getpossibility())+"강");
        if(player.getNowSword().getpossibility() >= 20){
            money.setText("엔딩도착");
            swordNameLabel.setText("엔딩도착");
            swordSellButton.setText("리셋하기");
            finalDestination.setText("목표도달 축하드립니다.");
        }
    }
    private void popup() {
        // 팝업 패널
        JPanel popupPanel = new JPanel();
        popupPanel.setBackground(new Color(112, 145, 133)); // 반투명 배경
        popupPanel.setBounds(20, 380, 300, 200); // 위치와 크기 설정
        popupPanel.setLayout(new GridLayout(4,1));


        // 팝업 내용
        JButton openStartButton = new JButton("메인");
        openStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Start");
            }
        });
        JButton openSaveButton = new JButton("저장");
        openSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadScreen.getInstance().setLoadRequest(false);
                mainController.switchTo("Load");
            }
        });

        JButton openLoadButton = new JButton("불러오기");
        openLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadScreen.getInstance().setLoadRequest(true);
                mainController.switchTo("Load");
            }
        });
        popupPanel.add(openStartButton);
        popupPanel.add(openSaveButton);
        popupPanel.add(openLoadButton);
        JButton closeButton = new JButton("닫기");
        closeButton.setBounds(100, 140, 100, 30);

        // 닫기 버튼 클릭 시 팝업 숨기기
        JButton openPopupButton = new JButton("팝업 열기");
        setButtonSize(openPopupButton);
        openPopupButton.setBounds(20, 380, 300, 100);

        closeButton.addActionListener(e -> popupPanel.setVisible(false));
        closeButton.addActionListener(e -> openPopupButton.setVisible(true));
        popupPanel.add(closeButton);
        popupPanel.setVisible(false); // 초기에는 숨김

        // 버튼으로 팝업 열기
        openPopupButton.addActionListener(e -> popupPanel.setVisible(true));
        openPopupButton.addActionListener(e -> openPopupButton.setVisible(false));

        // 게임 패널에 추가
        add(openPopupButton);
        add(popupPanel);

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