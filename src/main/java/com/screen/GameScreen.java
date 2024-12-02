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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameScreen extends JPanel implements Screen {

    static Player player;
    private JLabel money;
    private JLabel upSwordPrice;
    private JLabel finalDestination;
    private MainController mainController;
    private JLabel successProbability;//성공확률 표기
    private JPanel endSwordCount;//엔딩카운트
    private JButton endingButton;//엔딩버튼
    private JButton resetButton;//엔딩버튼
    private static boolean isSaveTicketActive = false;
    private Sword[] swords;

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
        Panel.setBounds(20,20,300,80);
        money.setBounds(30,10,280,60);
        money.setFont(new Font("DungGeunMo",Font.PLAIN,40));
        Panel.add(money);
        Panel.setOpaque(false);
        money.setForeground(new Color(214, 189, 152));
        Panel.setBorder(BorderFactory.createLineBorder(new Color(26, 54, 54), 5));
        add(Panel);

        menuPanel();
        popup();
    }
    private void menuPanel() {
        // 팝업 패널 인벤토리, 상점, 엔딩으로 향하는 버튼
        JPanel popupPanel = new JPanel(null);

        popupPanel.setBorder(BorderFactory.createLineBorder(new Color(214, 189, 152), 4));
        popupPanel.setBackground(new Color(64, 83, 76)); //배경
        popupPanel.setBounds(20, 110, 300, 180); // 위치와 크기 설정

        JButton openInventoryButton = new JButton("인벤토리");
        openInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.updateInventoryScreen();
                mainController.switchTo("Inventory");
            }
        });
        JButton openStoreButton = new JButton("상점");
        openStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.updateStoreScreen();
                mainController.switchTo("Store");
            }
        });
        // 버튼 위치와 크기 설정
        openInventoryButton.setBounds(50, 20, 200, 50);
        openStoreButton.setBounds(50, 80, 200, 50);
        popupPanel.add(openInventoryButton);
        popupPanel.add(openStoreButton);


        // 버튼 위치와 크기 설정
        JButton openPopupButton = new JButton("상점 // 인벤토리");
        openPopupButton.setBounds(20,140,300,100);
        JButton closeButton = new JButton("닫기");
        closeButton.setBounds(100, 140, 100, 30);
        popupPanel.add(closeButton);

        // 버튼으로 팝업 닫혀있음
        closeButton.addActionListener(e -> {
            popupPanel.setVisible(false);
            openPopupButton.setVisible(true);
        });
        // 버튼으로 팝업 열기 팝업열림
        openPopupButton.addActionListener(e -> {
            popupPanel.setVisible(true);
            openPopupButton.setVisible(false);
            setComponentZOrder(popupPanel, 0); // popupPanel을 맨 앞으로 이동
            revalidate();          // 레이아웃 갱신
            repaint();
        });

        setButtonSize(openPopupButton,30);
        popupPanel.setVisible(false); // 초기에는 숨김

        closeButton.setOpaque(false);

        setMenuButtonSetting(openInventoryButton,30);
        setMenuButtonSetting(openStoreButton,30);
        setMenuButtonSetting(closeButton,20);

        // 게임 패널에 추가
        add(openPopupButton);
        add(popupPanel);


        //검강화하기 목표
        endSwordCount = new JPanel(new BorderLayout());
        endSwordCount.setBounds(860,20,300,70);
        endSwordCount.setOpaque(false);
        finalDestination = new JLabel("목표까지 "+(20-player.getNowSword().getId())+"강");
        //폰트조정, 중앙정렬
        finalDestination.setFont(new Font("DungGeunMo",Font.PLAIN,35));
        finalDestination.setHorizontalAlignment(JLabel.CENTER);
        finalDestination.setVerticalAlignment(JLabel.CENTER);
        finalDestination.setForeground(new Color(214, 189, 152));
        endSwordCount.add(finalDestination);
        endSwordCount.setBorder(BorderFactory.createLineBorder(new Color(26, 54, 54), 5));
        add(endSwordCount);

        //검강화비용
        upSwordPrice = new JLabel("강화비용:"+player.getNowSword().getUpgradeFee()+"원");
        //폰트조정, 중앙정렬
        upSwordPrice.setBounds(880,520,280,100);
        upSwordPrice.setFont(new Font("DungGeunMo",Font.PLAIN,25));
        upSwordPrice.setHorizontalAlignment(JLabel.CENTER);
        upSwordPrice.setVerticalAlignment(JLabel.CENTER);
        upSwordPrice.setForeground(new Color(214, 189, 152));
        upSwordPrice.setBorder(BorderFactory.createLineBorder(new Color(214, 189, 152), 5));
        add(upSwordPrice);

        // 성공 메시지 패널 설정
        successProbability = new JLabel("강화 확률 : "+player.getNowSword().getpossibility()+"%");
        successProbability.setBounds(460, 25, 350, 60);
        successProbability.setFont(new Font("DungGeunMo",Font.PLAIN,35));
        successProbability.setForeground(Color.yellow);
        add(successProbability);

        // 엔딩 버튼
        endingButton = new JButton("엔딩으로");
        endingButton.setBounds(440,620,300,140);
        endingButton.setFont(new Font("DungGeunMo",Font.PLAIN,40));
        endingButton.setBackground(new Color(192, 192, 192));
        endingButton.setForeground(Color.red);
        endingButton.setBorder(BorderFactory.createLineBorder(Color.red, 5));
        add(endingButton);
        endingButton.setVisible(false);
        //엔딩스크린이동
        endingButton.addActionListener(e -> {
            player.setMoney(2147483647);
            mainController.switchTo("END");
        });
        // 리셋 버튼
        resetButton = new JButton("모두 초기화");
        resetButton.setBounds(850,620,300,100);
        resetButton.setFont(new Font("DungGeunMo",Font.PLAIN,30));
        resetButton.setBackground(new Color(33, 150, 243));
        resetButton.setForeground(new Color(244, 67, 54));
        add(resetButton);
        resetButton.setVisible(false);
        resetButton.addActionListener(e -> {
            resetButton();
        });

    }

    private void popup() {
        // 팝업 패널
        JPanel popupPanel = new JPanel(null);

        popupPanel.setBorder(BorderFactory.createLineBorder(new Color(214, 189, 152), 4));
        popupPanel.setBackground(new Color(64, 83, 76)); //배경
        popupPanel.setBounds(20, 260, 300, 230); // 위치와 크기 설정

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
        // 버튼 위치와 크기 설정
        openStartButton.setBounds(50, 10, 200, 50);
        openSaveButton.setBounds(50, 70, 200, 50);
        openLoadButton.setBounds(50, 130, 200, 50);
        popupPanel.add(openLoadButton);
        popupPanel.add(openSaveButton);
        popupPanel.add(openStartButton);


        // 버튼 위치와 크기 설정
        JButton openPopupButton = new JButton("메뉴");
        openPopupButton.setBounds(20,300,300,100);
        JButton closeButton = new JButton("닫기");
        closeButton.setBounds(100, 190, 100, 30);
        closeButton.setFont(new Font("DungGeunMo",Font.PLAIN,20));
        // 버튼으로 팝업 닫혀있음
        closeButton.addActionListener(e -> {
            popupPanel.setVisible(false);
            openPopupButton.setVisible(true);
        });
        // 버튼으로 팝업 열기 팝업열림
        openPopupButton.addActionListener(e -> {
            popupPanel.setVisible(true);
            openPopupButton.setVisible(false);
            setComponentZOrder(popupPanel, 0); // popupPanel을 맨 앞으로 이동
            revalidate();          // 레이아웃 갱신
            repaint();
        });
        setButtonSize(openPopupButton,30);
        popupPanel.setVisible(false); // 초기에는 숨김
        popupPanel.add(closeButton, BorderLayout.PAGE_END);

        closeButton.setOpaque(false);

        setMenuButtonSetting(openStartButton,30);
        setMenuButtonSetting(openSaveButton,30);
        setMenuButtonSetting(openLoadButton,30);
        setMenuButtonSetting(closeButton,20);

        // 게임 패널에 추가
        add(openPopupButton);
        add(popupPanel);
    }

    public void gamePanelUpdate(){
        removeAll();
        initialize();
        revalidate();
        repaint();
    }
    public void moneyPanelUpdate(){
        money.setVisible(true);
        textReplace();
    }

    private void midPanel() {
        JPanel mid = new JPanel(new BorderLayout());
        setMidbutton(mid);
        mid.setOpaque(false);
        add(mid, BorderLayout.CENTER);
    }

    private JButton swordUpgradeButton;
    private JButton swordSellButton;
    private static JButton saveTicketButton;
    JLabel swordNameLabel;
    private void setMidbutton(Container c) {
        JPanel imagePanel = new JPanel(new BorderLayout());//검이미지
        Sword sword = player.getNowSword();
//        JLabel swordImage = new JLabel(sword.imageIcon());
        JLabel swordImage = new JLabel(sword.gifIcon());

        JPanel swordName = new JPanel(new BorderLayout());//검이름
        swordNameLabel = new JLabel(player.getNowSword().getName());
        swordNameLabel.setFont(new Font("DungGeunMo",Font.PLAIN,50));
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

        swordSellButton = new JButton("판매비용:"+MainController.swordList[player.getNowSword().getId()-1].getsellPrice() + "원");
        buttonList.add(sellButton(swordSellButton, swordImage));

        JPanel mainPanel = new JPanel(new BorderLayout(50,30));
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonList,BorderLayout.PAGE_END);

        mainPanel.setOpaque(false);
        imagePanel.setOpaque(false);
        buttonList.setOpaque(false);
        swordImage.setOpaque(false);
        swordName.setOpaque(false);

        setBackground(new Color(64, 83, 76));
        c.add(mainPanel);
    }
    private void saveTicketClickedEvent() {
        saveTicketButton.setBackground(isSaveTicketActive ? Color.GREEN : Color.GRAY);
        saveTicketButton.setText(isSaveTicketActive ? "파괴방지 활성화" : "파괴방지 비활성화");
        //색깔크기,폰트조정,배경투명,
        saveTicketButton.setPreferredSize(new Dimension(300, 100));
        saveTicketButton.setBackground(new Color(64, 83, 76));
        Color color = new Color(214, 189, 152);
        saveTicketButton.setForeground(color);
        saveTicketButton.setFont(new Font("DungGeunMo",Font.PLAIN,30));
        saveTicketButton.setFocusPainted(false);
        saveTicketButton.setBorder(BorderFactory.createLineBorder(color, 3));
        saveTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player.getInventory().isEmpty()){
                    JOptionPane.showMessageDialog(null, "파괴방지권이 부족합니다");
                }
                else if(player.getInventory().get(findSaveTicket()).getCount() > 0){
                    if (!isSaveTicketActive) {
                        saveTicketButton.setBackground(Color.GREEN);
                        saveTicketButton.setText("파괴방지 활성화");
                        isSaveTicketActive = true;
                    } else {
                        saveTicketButton.setBackground(Color.gray);
                        saveTicketButton.setText("파괴방지 비활성화");
                        isSaveTicketActive = false;
                    }
                }
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
        int pSword = player.getNowSword().getId();
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
        setButtonSize(swordUpgradeButton,30);
        swordUpgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sword nowSword = player.getNowSword();
                if (player.getMoney() >= nowSword.getUpgradeFee()){//player의 돈이 강화비용보닫 많을경우에만
                    player.doUpgradeSword();//돈소모 현재쓰는방식은 임시방편
                    if (nowSword.upgradeProbability()) {
                        success(image,nowSword.getId());
                    } else {
                        fall(image, nowSword.getId());
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "돈이부족합니다");//세팅스크린에서빌려왔음
                }
            }
        });
        return swordUpgradeButton;
    }

    private void success(JLabel image, int number){
        Sword nextSword = MainController.swordList[number];//다음검뽑아오기
        if(number > player.getBestSword()) player.setBestSword(number);
        player.setNowSword(nextSword);//플레이어검 업그레이드
//        image.setIcon(nextSword.imageIcon());//이미지변경
        image.setIcon(nextSword.gifIcon());//이미지변경
        moneyPanelUpdate();
    }
    private void fall(JLabel Image, int num) {
        moneyPanelUpdate();
        if (saveTicketButton.getBackground() != Color.GREEN) {
//            Image.setIcon(nextSword.imageIcon());
            Image.setIcon(swords[num-1].failedIcon());
            player.setNowSword(swords[0]);
            Image.setIcon(swords[0].gifIcon());
            moneyPanelUpdate();
        } else {
            saveTicketButton.setBackground(Color.gray);
            saveTicketButton.setText("파괴방지 비활성화");
            isSaveTicketActive = false;
            player.getInventory().get(findSaveTicket()).minCount();
            moneyPanelUpdate();
            mainController.updateInventoryScreen();
            JOptionPane.showMessageDialog(null,"파괴방어", "파괴방지권", JOptionPane.WARNING_MESSAGE);
        }
    }


    private JButton sellButton(JButton button, JLabel Image) {
        setButtonSize(button,25);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//player에 돈넣어주는것추가하기
                player.soldSword(MainController.swordList[0]);//n번째검 판매 > 0번째검으로 초기화
//                Image.setIcon(player.getNowSword().imageIcon());
                Image.setIcon(player.getNowSword().gifIcon());
                moneyPanelUpdate();
                mainController.updateStoreScreen();
            }
        });
        return button;
    }
    @Override
    public void initialize() {
        gameSetting();
        topPanel();
        midPanel();
        endingCheck();
    }

    public void gameSetting(){
        swords = MainController.swordList;
        player = Player.getInstance();
        money = new JLabel("돈:" + player.getMoney());
        finalDestination = new JLabel("목표까지 "+(20-player.getNowSword().getId())+"강");
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout());
    }
    private void setButtonSize(JButton c,int size) {
        c.setPreferredSize(new Dimension(300, 100));
        c.setBackground(new Color(64, 83, 76));
        Color color = new Color(214, 189, 152);
        c.setForeground(color);
        c.setFont(new Font("DungGeunMo",Font.PLAIN,size));
        c.setFocusPainted(false);
        c.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                c.setBackground(new Color(103, 125, 106));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                c.setBackground(new Color(64, 83, 76));
            }
        });
        c.setBorder(BorderFactory.createLineBorder(color, 5));
    }
    private void textReplace(){
        money.setText("돈:" + player.getMoney());
        swordNameLabel.setText(player.getNowSword().getName());
        successProbability.setText("강화 확률 : "+player.getNowSword().getpossibility()+"%");
        swordSellButton.setText("판매비용:"+player.getNowSword().getsellPrice() + "원");
        upSwordPrice.setText("강화비용:"+player.getNowSword().getUpgradeFee()+"원");
        finalDestination.setText("목표까지 "+(20-player.getNowSword().getId())+"강");
    }
    private void endingCheck(){
        if(player.getNowSword().getId() >= 20){
            endingButton.setVisible(true);
            resetButton.setVisible(true);//리셋버튼
            //나머지버튼 패널 비활성화
            endSwordCount.setVisible(false);
            swordSellButton.setVisible(false);
            saveTicketButton.setVisible(false);
            swordUpgradeButton.setVisible(false);
            successProbability.setVisible(false);
            upSwordPrice.setVisible(false);
        }
    }
    private void resetButton(){
        endSwordCount.setVisible(true);
        swordSellButton.setVisible(true);
        saveTicketButton.setVisible(true);
        swordUpgradeButton.setVisible(true);
        player.setMoney(0);
        System.out.println(player.getInventory().size());
        if(!player.getInventory().isEmpty()){
            ArrayList<Item> emptyInventory = player.getInventory();
            for (int i = 0; i < player.getInventory().size(); i++){
                emptyInventory.clear();
            }
            player.setInventory(emptyInventory);
        }
        player.setNowSword(MainController.swordList[0]);
        gamePanelUpdate();

    }
    private void setMenuButtonSetting(JButton button, int size){
        button.setBackground(new Color(64, 83, 76));
        Color color = new Color(214, 189, 152);
        button.setForeground(color);
        button.setFont(new Font("DungGeunMo",Font.PLAIN,size));
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(103, 125, 106));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(64, 83, 76));
            }
        });
        button.setBorder(BorderFactory.createLineBorder(color, 3));
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
//게임클리어화면구현
//강화비용 판매비용 위에 구현