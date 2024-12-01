package com.player;

import com.app.MainController;
import com.item.interfaces.Item;
import com.item.Sword;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Player {

    private static Player[] instances = new Player[3];
    private static Player currentPlayer = new Player();
    private static int cnt = 0;

    private static int selectedIdx = 0;

    private int money = 0;
    private Sword nowSword;
    ArrayList<Item> inventory = new ArrayList<>();

    private String updatedDate;

    private int bestSword;

    private ArrayList<int[]> statics = new ArrayList<>(); // {SuccessCount,FailureCount}
    private ArrayList<int[]> log = new ArrayList<>();


    //현재 플레이어가 선택중인 프로필을 반환
    public static Player getInstance() {
        return currentPlayer;
    }

    //플레이어가 어떤 프로필을 골랐는지와 별개로 index번째 프로필을 반환
    public static Player getInstance(int index) {
        if(instances[0] == null) {
            for(int i = 0; i < 3; i++) instances[i] = new Player();
            currentPlayer = new Player();
        }
        return instances[index];
    }

    //플레이어가 선택한 프로필을 갱신
    public static void setSelectedIdx(int changePlayer) {
        selectedIdx = changePlayer;
        copyPlayer(instances[selectedIdx], currentPlayer);
        MainController.updateSwordStatistics();
    }

    private static void copyPlayer(Player instance, Player currentPlayer) {
        currentPlayer.money = instance.money;
        currentPlayer.inventory = instance.inventory;
        currentPlayer.updatedDate = instance.updatedDate;
        currentPlayer.statics = instance.statics;
        currentPlayer.log = instance.log;
        currentPlayer.nowSword = instance.nowSword;
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
    public int getBestSword() {
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

    public void setBestSword(int bestSword) {
        this.bestSword = bestSword;
    }
}