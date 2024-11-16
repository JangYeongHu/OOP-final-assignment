package com.player;

import com.item.interfaces.Item;
import com.item.Sword;

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

    public void doUpgradeSword() {

    }

    public void soldSword() {
    }

    public Sword getNowSword(Sword s) {
        return nowSword;
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



}