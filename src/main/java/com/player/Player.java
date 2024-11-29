package com.player;

import com.app.MainController;
import com.item.interfaces.Item;
import com.item.Sword;

import java.util.ArrayList;

public class Player {

    private static Player[] instances = new Player[3];

    private static int nowPlayer = 0;

    private int money = 0;
    private Sword nowSword;
    ArrayList<Item> inventory = new ArrayList<>();

    private String updatedDate;

    private Sword bestSword;

    private ArrayList<int[]> statics = new ArrayList<>(); // {SuccessCount,FailureCount}
    private ArrayList<int[]> log = new ArrayList<>();


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
        MainController.updateSwordStatistics();
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public Item useItem(Item item) {
        return null;
    }

    public void doUpgradeSword() {
        money -= nowSword.getUpgradeFee();
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

    public String getUpdateDate() {
        return updatedDate;
    }


    public ArrayList<int[]> getStatics() {
        return statics;
    }
    public Sword getBestSword() {
        return bestSword;
    }

    public ArrayList<int[]> getLog() {
        return log;
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

    public void setInventory(ArrayList<Item> inventory) {}
  
    public void setStatics(ArrayList<int[]> statics) {
        this.statics = statics;
    }

    public void setLog(ArrayList<int[]> log) {
        this.log = log;
    }

    public void setBestSword(Sword bestSword) {
        this.bestSword = bestSword;
    }
}