package com.player;

import com.app.MainController;
import com.app.JsonController;
import com.item.interfaces.Item;
import com.item.Sword;
import org.json.JSONObject;

import java.util.ArrayList;

public class Player {

    private static Player singletonPlayer;


    private int money = 0;
    private Sword nowSword = null;
    ArrayList<Item> inventory = new ArrayList<>();

    public static Player getInstance() {
        if(singletonPlayer == null)
            singletonPlayer = new Player();
        return singletonPlayer;
    }


    public Player() {
        loadPlayerData();
    }


    public void loadPlayerData() {

    }

    public void addItem(Item item) {
    }

    public Item useItem(Item item) {
        return null;
    }

    public void doUpgradeSword(Sword upGradeSword) {
        nowSword = upGradeSword;
//        money -= nowSword.getUpgradeFee(); - 강화 비용을 돌려주는 메소드 필요
   }

    public void soldSword(Sword initSword) {
        //SList[0] 을 넣어서 호출
        money += nowSword.getsellPrice();
        nowSword = initSword;
    }

    //Getter
    public int getMoney() {
        return money;
    }

    public Sword getNowSword() {
        return nowSword;
    }

    public ArrayList<Item> getInventory() {

        return inventory;
    }

    //Setter
    public void setMoney(int money) {
        this.money = money;
    }

    public void setNowSword(Sword nowSword) {
        this.nowSword = nowSword;
    }

    public void setInventory(ArrayList<Item> inventory) {}



}