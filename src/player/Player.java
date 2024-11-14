package src.player;

import src.item.interfaces.Item;
import src.item.Sword;

import java.util.ArrayList;

public class Player {

    private static Player singletonPlayer;

    private int money = 0;
    private Sword nowSword = null;
    ArrayList<Item> inventory = new ArrayList<>();
//    private int Sword_possibility = 0;
//
//    public int getSword_possibility() {
//        return Sword_possibility;
//    }
//    public int setSword_possibility(int i) {
//        Sword_possibility = i;
//        return this.Sword_possibility;
//    }


    public static Player getInstance() {
        if(singletonPlayer == null)
            singletonPlayer = new Player();
        return singletonPlayer;
    }

    public void savePlayerData() {
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
    public void addMoney(int money) {
        this.money = money;
    }

    public void setNowSword(Sword nowSword) {
        this.nowSword = nowSword;
    }



}