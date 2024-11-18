package com.item;


import com.item.interfaces.Item;

public class Ticket implements Item {
    private boolean activate = false;
    String name;
    int price;
    int count = 0;
    public void activatTicket(){
        if(activate)
            activate = false;
        else
            activate = true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCount(int count) {
        this.count += count;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    /*
    10강 이후 파편(아이템1)
    파편은 모아서 검을 구매

    파괴 방지권(아이템2)

    워프 권(아이템3) -> 15강

    확률 업 아이템? (아이템4)

    확정 업 아이템? (아이템5) 20강 -> 21강
     */

}