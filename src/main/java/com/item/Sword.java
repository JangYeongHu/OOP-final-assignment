package com.item;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Sword {

    private int upgradeFee = 0;//강화비용
    private int sellPrice = 0;//판매가
    private int possibility = 0;//강화도

    //성공횟수,실패횟수,시도횟수
    private int SuccessCount = 0;
    private int FailureCount = 0;
    private int TotalAttempts = 0;

    private String imageSourcePath;

    private String name;

    private String description;

    public String upGradeSword() {
        return "Success";
    }
    public boolean UpgradeProbability() {// 현재 강화율과 비교해서 강화확률조정
        int num = 105 - 5*possibility;// 성공확률계산
        Random rand = new Random();
        int n = rand.nextInt(1,101);// 성공
        if(n > num){
            FailureCount++;
            return false;
        }
        SuccessCount++;
        return true;
    }

    public ImageIcon Imageicon(){
        ImageIcon imageicon = new ImageIcon(imageSourcePath);//
        Image image = imageicon.getImage();
        int newWidth = 400;
        int newHeight = 400;
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }


    public Sword (String img, int i){//검만들기 검이름과 이미지파일 이름을넣는다
        imageSourcePath = img;
        possibility = i;
        SetSellPrice();
    }

    public int SetSellPrice(){//특정강화도 이상부터 판매가설정
        // = 100*possibility;
        if (possibility == 1)
            return 0;
        if (possibility > 4)
            sellPrice = 200*possibility;
        if (possibility > 8)
            sellPrice = 400*possibility;
        if (possibility > 12)
            sellPrice = 800*possibility;
        if (possibility > 16)
            sellPrice = 1600*possibility;
        else
            sellPrice = 110*possibility;
        return sellPrice;
    }

    public int SetupgradeFee(){//특정강화도 이상부터 판매가설정
        // = 100*possibility;
        if (possibility == 1)
            return 0;
        if (possibility > 4)
            upgradeFee = 100*possibility;
        if (possibility > 8)
            upgradeFee = 200*possibility;
        if (possibility > 12)
            upgradeFee = 300*possibility;
        if (possibility > 16)
            upgradeFee = 400*possibility;
        else
            upgradeFee = 50*possibility;
        return upgradeFee;
    }

    public String setSwordName(String name){
        this.name = name;
        return this.name;
    }
    public String setSwordDescription(String description){
        this.description = description;
        return this.description;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getSuccessCount(){
        return SuccessCount;
    }
    public int getFailureCount(){
        return FailureCount;
    }
    public int getTotalAttempts(){
        TotalAttempts = SuccessCount+FailureCount;
        return TotalAttempts;
    }
    public int getSuccessRate(){//실제 성공확률
        int a = getTotalAttempts();
        return getSuccessCount()/a;
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