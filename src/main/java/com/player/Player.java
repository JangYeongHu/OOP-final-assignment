package com.player;

import com.app.MainController;
import com.app.JsonController;
import com.item.interfaces.Item;
import com.item.Sword;
import org.json.JSONObject;

import java.util.ArrayList;

public class Player {

    private static Player[] instances = new Player[3];

    private static int nowPlayer = 0;

    private int money = 0;
    private Sword nowSword;
    ArrayList<Item> inventory = new ArrayList<>();

    private String updatedDate;


    //현재 플레이어가 선택중인 프로필을 반환
    public static Player getInstance() {
        if(instances[0] == null) {
            for(int i = 0; i < 3; i++) instances[i] = new Player();
        }
        return instances[nowPlayer];
    }

    //플레이어가 어떤 프로필을 골랐는지와 별개로 index번째 프로필을 반환
    public static Player getInstance(int index) {
        if(instances[0] == null) {
            for(int i = 0; i < 3; i++) instances[i] = new Player();
        }
        return instances[index];
    }

    //플레이어가 선택한 프로필을 갱신
    public static void setNowPlayer(int changePlayer) {
        nowPlayer = changePlayer;
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

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdateDate() {
        return updatedDate;
    }

    public void setInventory(ArrayList<Item> inventory) {}



}