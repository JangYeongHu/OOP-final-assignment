package src.player;

import src.item.interfaces.Item;
import src.item.Sword;

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
}