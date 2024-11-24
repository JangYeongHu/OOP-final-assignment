package com.item.interfaces;

public interface Item {
    int getCount();

    void minCount();

    int getPrice();
    String getName();
    void addCount();
    boolean match(String itemName);
    void useItem();

    void setCount();
}
