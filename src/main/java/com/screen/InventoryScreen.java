package com.screen;

import com.app.MainController;

import javax.swing.*;

import com.item.interfaces.Item;
import com.player.Player;
import com.screen.interfaces.Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class InventoryScreen extends JPanel implements Screen {
    Player player = Player.getInstance();

    private MainController mainController;
    public InventoryScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }
    //상 : 게임스크린으로 돌아가는 버튼추가
    //중 : 5*5 로 패널들을 배치시키기
    //중패널: 2:1 로 배치해서 위에는 이름만갖는
    //하 : 따로필요없다.
    public void openInventory(){

    }
    public void CenterPanelCreate(){
        int MRows = 5;
        int MCols = 5;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(MRows,MCols,40,40));
        for (int i = 0; i < MRows; i++) {
            for (int j = 0; j < MCols; j++) {
                JPanel MiniPanel = new JPanel(new GridLayout(2,1));
                MiniPanel = ItemPanelCreate(player, MiniPanel);
                panel.add(MiniPanel);
            }
        }
        add(panel);
    }
    public JPanel ItemPanelCreate(Player p,JPanel c){
        //getItemName()
        //getItemCount()각각 인벤토리 인터페이스에 추가예정
        //파괴방지권은 인벤토리에 따로넣지않기
        Random r = new Random();
        ArrayList<Item> ItemList = p.getInventory();

        JPanel NamePanel = new JPanel();
        JLabel IName = new JLabel("아이템 이름");//1번패널
        NamePanel.add(IName);
        c.add(NamePanel);
        NamePanel.setBackground(new Color(232, 113, 64));
        for (Item i : ItemList) {
            i.Getitem();
        }



        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        JButton ActivateButton = new JButton("사용하기");//사용버튼
        //int count = p.getInventory().getItemCount();//아이템갯수
        int count = r.nextInt(200);
        JLabel CountLabel= new JLabel(count+"개");//2번패널
        CountLabel.setHorizontalAlignment(JLabel.CENTER);
        buttonPanel.setBackground(Color.green);
        buttonPanel.add(ActivateButton);
        buttonPanel.add(CountLabel);

        c.add(buttonPanel);
        return c;
    }
    public void goGameScreen(){
        JButton GoGame = new JButton("게임창으로 돌아가기");
        GoGame.setPreferredSize(new Dimension(0,70));
        GoGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Game");
            }
        });
        JPanel goScreen  =new JPanel(new BorderLayout());
        goScreen.add(GoGame);
        add(goScreen, BorderLayout.PAGE_START);
    }

    public void AddInventory(Item I){
    }
    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        goGameScreen();
        CenterPanelCreate();
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