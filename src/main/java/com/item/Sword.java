package com.item;

import com.player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Sword {

    private int upgradeFee = 0;//강화비용
    private int sellPrice = 0;//판매가
    private int possibility = 0;//강화도

    //성공횟수,실패횟수,시도횟수
    private int successCount = 0;
    private int failureCount = 0;
    private int totalAttempts = 0;

    private String imageSourcePath;

    private int id;

    private String name;

    private String description;

    private ImageIcon gifIcon;
    private ImageIcon failedIcon;

    public boolean upgradeProbability() {// 현재 강화율과 비교해서 강화확률조정
        Random rand = new Random();
        int n = rand.nextInt(1,101);// 성공
        if(n > possibility){
            failureCount++;
            return false;
        }
        successCount++;
        return true;
    }

    public ImageIcon imageIcon(){
        ImageIcon imageicon = new ImageIcon(imageSourcePath);

        return imageicon;
    }

    public ImageIcon gifIcon(){
        return gifIcon;
    }

    public ImageIcon failedIcon(){
        return failedIcon;
    }




    public Sword (String img, int i){//검만들기 검아이디와 이미지파일 이름을넣는다
        imageSourcePath = img;
        id = i;
        gifIcon = new ImageIcon("resources/game/" + id + ".gif");
        failedIcon = new ImageIcon("resources/failed/" + id + ".gif");

    }

    public void setSellPrice(int price){//특정강화도 이상부터 판매가설정
        sellPrice = price;
    }

    public void setUpgradeFee(int price){//특정강화도 이상부터 판매가설정
        upgradeFee = price;
    }

    public String setSwordName(String name){
        this.name = name;
        return this.name;
    }
    public String setSwordDescription(String description){
        this.description = description;
        return this.description;
    }

    public void setPossibility(int possibility) {
        this.possibility = possibility;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    //getter
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getSuccessCount(){
        return successCount;
    }
    public int getFailureCount(){
        return failureCount;
    }
    public int getTotalAttempts(){
        totalAttempts = successCount+failureCount;
        return totalAttempts;
    }
    public double getSuccessRate(){//실제 성공확률
        int a = getTotalAttempts();
        if(a == 0) return 0.0;
        return (float) getSuccessCount()/a;
    }

    public int getId() {
        return id;
    }

    public int getUpgradeFee(){
        return upgradeFee;
    }
    public int getsellPrice(){
        return this.sellPrice;
    }
    public int getpossibility(){//강화도
        return possibility;
    }
    public String getimage() {
        return imageSourcePath;
    }


}