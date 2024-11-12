package src.item;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Sword {

    private int upgradeFee;//강화비용
    private int sellPrice;//판매가
    private int possibility;//강화도

    //성공횟수,실패횟수,시도횟수
    private int SuccessCount;
    private int getFailureCount;
    private int getTotalAttempts;

    private String imageSourcePath;

    private String name;

    private String description;

    public String upGradeSword() {
        return "Success";
    }
    boolean upgrade_probability(int U) {// 현재 강화율과 비교해서 강화확률조정
        int num = 100 - 5*possibility;// 성공확률계산
        Random rand = new Random();
        int n = rand.nextInt(1,101);// 성공
        if(n > num){
            return false;
        }
        return true;
    }

    public JLabel imageLabel(){
        ImageIcon imageicon = new ImageIcon(imageSourcePath);//
        Image image = imageicon.getImage();
        int newWidth = 400;
        int newHeight = 400;
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return new JLabel(resizedIcon);
    }


    public Sword (String n, String d,String img){//검만들기 검이름과 이미지파일 이름을넣는다
        name = n;
        description = d;
        imageSourcePath = img;
        possibility = 0;
    }
    public void priceSetting(){//강화도비례 특정강화도 이상부터 판매가와 강화비용설정
        upgradeFee = 100*possibility;
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
    }



    public String getName() {
        return name;
    }
    public int SuccessCount(){
        return SuccessCount;
    }
    public int getFailureCount(){
        return getFailureCount;
    }
    public int getTotalAttempts(){
        return getTotalAttempts;
    }
    public int getSuccessRate(){//성공비율
        return 0;
    }

}