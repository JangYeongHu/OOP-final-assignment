package com.item;


import com.app.MainController;
import com.item.interfaces.Item;
import com.screen.GameScreen;

import javax.swing.*;

public class Ticket implements Item {
    String name = "";
    int price;
    int count = 0;
    String type;

    @Override
    public int getCount() {//현재아이템갯수리턴
        return count;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addCount() {
        count++;
    }
    @Override
    public void minCount() {
        count--;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void useItem() {//아이템사용메서드
        if(count >= 1){
            count--;
            JOptionPane.showMessageDialog(null, "아이템이사용되었습니다");
        }
        else{
            JOptionPane.showMessageDialog(null, "아이템이부족합니다");
        }
    }

    @Override
    public boolean match(String itemName){
        return name.contains(itemName);
    }
    public static Item ticketType(String type,int possibility,int count){
        return switch (type) {
            case "Save" -> new SaveTicket(count);
            case "Push" -> new PushTicket(count);
            case "Upgrade" -> new UpgradeTicket(possibility,count);
            default -> new Ticket();
        };
    }
    @Override
    public void setCount() {
    }

    public String getType() {
        return type;
    }

    @Override
    public int getPossibility() {
        return 0;
    }
}

class PushTicket extends Ticket{//확정업
    static String name = "확정업 티켓";

    public PushTicket(int count){
        price = 150000000;
        if(count == 0) addCount();
        else {
            this.count = count;
        }
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void useItem() {//아이템사용메서드
        if(count > 0){
            GameScreen.pushTicketActive();
            minCount();
            JOptionPane.showMessageDialog(null, "아이템 강화의 성공했습니다");
        }
        else{
            JOptionPane.showMessageDialog(null, "아이템이부족합니다");
        }
    }

    @Override
    public String getType() {
        return "Push";
    }
}

class UpgradeTicket extends Ticket {//워프권 10,12,14,16,18
    String name;
    int possibility = 0;
    public UpgradeTicket(int x,int count){
        name = x+"강 워프권";
        possibility = x-1;
        price = MainController.swordList[x].getsellPrice()*2;

        if(count == 0) addCount();
        else {
            this.count = count;
        }
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public void useItem() {//아이템사용메서드
        if(count > 0){
            GameScreen.upgradeTicketActive(possibility);
            minCount();
            JOptionPane.showMessageDialog(null, "아이템 활성화");
        }
        else{
            JOptionPane.showMessageDialog(null, "아이템이부족합니다");
        }
    }

    @Override
    public String getType() {
        return "Upgrade";
    }

    @Override
    public int getPossibility() {
        return possibility;
    }
}
class SaveTicket extends Ticket{//파괴방지권
    static String name = "파괴방지권";
    public SaveTicket(int count){
        price = 10000;
        if(count == 0) addCount();
        else {
            this.count = count;
        }
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public boolean match(String itemName){
        return name.equals(itemName);
    }
    @Override
    public void useItem() {//아이템사용메서드
        if(count > 0 && !GameScreen.getIsSaveTicketActive()){
            GameScreen.setIsSaveTicketActive(true);
            JOptionPane.showMessageDialog(null, "파괴방지권 활성화");
        }
        else{
            JOptionPane.showMessageDialog(null, "아이템이부족하거나 \n아이템이 이미 활성화 되어있습니다");
        }
    }

    @Override
    public String getType() {
        return "Save";
    }
}


// /*
//    10강 이후 파편(아이템1)
//    파편은 모아서 검을 구매
//
//    파괴 방지권(아이템2)
//
//    워프 권(아이템3) -> 15강
//
//    확률 업 아이템? (아이템4)
//
//    확정 업 아이템? (아이템5) 20강 -> 21강
//     */