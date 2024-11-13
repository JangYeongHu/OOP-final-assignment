package src.item;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Sword {

    private int upgradeFee;//강화비용
    private int sellPrice = 1000;//판매가
    private static int possibility = 1;//강화도

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
    public boolean upgrade_probability() {// 현재 강화율과 비교해서 강화확률조정
        int num = 105 - 5*possibility;// 성공확률계산
        Random rand = new Random();
        int n = rand.nextInt(1,101);// 성공
        if(n > num){
            FailureCount++;
            possibility = 0;
            return false;
        }
        SuccessCount++;
        possibility++;
        System.out.println(possibility);
        return true;
    }

    public ImageIcon imageicon(){
        ImageIcon imageicon = new ImageIcon(imageSourcePath);//
        Image image = imageicon.getImage();
        int newWidth = 400;
        int newHeight = 400;
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }


    public Sword (String n, String d,String img){//검만들기 검이름과 이미지파일 이름을넣는다
        name = n;
        description = d;
        imageSourcePath = img;
        possibility = 0;
    }
    public int getSellPrice(){//특정강화도 이상부터 판매가설정
        // = 100*possibility;
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
        System.out.println("판매가 : "+sellPrice);
        return sellPrice;
    }


    public void getter(){

    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int SuccessCount(){
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

        return 0;
    }
    public int getpossibility(){//강화도
        return possibility;
    }
    public String getimage() {
        return imageSourcePath;
    }


    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }
}