package com.screen;

import com.app.MainController;
import com.player.Player;
import com.screen.interfaces.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CollectionScreen extends JPanel implements Screen {

    private MainController mainController;

    private Player player = Player.getInstance();

    private ImageIcon swordImages[] = new ImageIcon[20];
    private ImageIcon blackSwordImages[] = new ImageIcon[20];

    private int nowIndex = 0;

    private Image centerImage;
    private Image leftImage;
    private Image rightImage;

    private ImageDisplayPanel imageDisplayPanel;

    public CollectionScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }
    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        setBackground(new Color(0x40534C));
        loadSwordImages();
        generateLowArrow();
        genereateMiddleImage();
    }

    private void generateLowArrow() {
        JPanel LowPanel = new JPanel(new BorderLayout());
        LowPanel.setBackground(this.getBackground());
        generateLeftArrow(LowPanel);
        generateRightArrow(LowPanel);
        add(LowPanel,BorderLayout.PAGE_END);
    }

    private void genereateMiddleImage() {
        imageDisplayPanel = new ImageDisplayPanel();
        imageDisplayPanel.setBackground(this.getBackground());
        add(imageDisplayPanel);
    }

    private void generateLeftArrow(JPanel middlePanel) {
        ImageIcon leftImageIcon = new ImageIcon("src/main/resources/left-arrow-button.png");
        Image leftImage = leftImageIcon.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH);
        JLabel leftArrowLabel = new JLabel(new ImageIcon(leftImage));
        leftArrowLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(nowIndex > 0) {
                    nowIndex--;
                    imageDisplayPanel.repaint();
                }
            }
        });
        middlePanel.add(leftArrowLabel,BorderLayout.WEST);
    }

    private void generateRightArrow(JPanel middlePanel) {
        ImageIcon rightImageIcon = new ImageIcon("src/main/resources/right-arrow-button.png");
        Image rightImage = rightImageIcon.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH);
        JLabel rightImageLabel = new JLabel(new ImageIcon(rightImage));
        rightImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(nowIndex < 19) {
                    nowIndex++;
                    imageDisplayPanel.repaint();
                }
            }
        });
        middlePanel.add(rightImageLabel,BorderLayout.EAST);
    }

    @Override
    public void showScreen() {
        update();
        setVisible(true);
    }

    @Override
    public void hideScreen() {
        setVisible(false);
    }

    public void update() {

    }

    private void loadSwordImages() {
        for (int i = 0; i < 20; i++)
            swordImages[i] = new ImageIcon("src/main/resources/"+(i+1)+".png");
        for (int i = 0; i < 20; i++)
            blackSwordImages[i] = new ImageIcon("src/main/resources/collectionScreen/"+(i+1)+".png");
    }

    private class ImageDisplayPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            System.out.println(nowIndex);
            super.paintComponent(g);

            // 패널 크기 가져오기
            int width = getWidth();
            int height = getHeight();

            // 가운데 이미지 크기와 위치
            int centerSize = 400;
            int centerX = (width - centerSize) / 2;
            int centerY = (height - centerSize) / 2;

            // 양쪽 이미지 크기와 위치
            int sideSize = 300;
            int leftIndex = nowIndex-1;
            int rightIndex = nowIndex+1;

            int leftX = centerX - sideSize - 40;
            int leftY = centerY + 20;
            int rightX = centerX + centerSize + 40;
            int rightY = centerY + 20;

            // 이미지 그리기
            if(nowIndex != 0)
                if(player.getBestSword() < leftIndex)
                    g.drawImage(blackSwordImages[leftIndex].getImage(), leftX, leftY, sideSize, sideSize, this);
                else
                    g.drawImage(swordImages[leftIndex].getImage(), leftX, leftY, sideSize, sideSize, this);
            if(player.getBestSword() < nowIndex)
                g.drawImage(blackSwordImages[nowIndex].getImage(), centerX, centerY, centerSize, centerSize, this);
            else
                g.drawImage(swordImages[nowIndex].getImage(), centerX, centerY, centerSize, centerSize, this);
            if(nowIndex != 19)
                if(player.getBestSword() < rightIndex)
                    g.drawImage(blackSwordImages[rightIndex].getImage(), rightX, rightY, sideSize, sideSize, this);
                else
                    g.drawImage(swordImages[rightIndex].getImage(), rightX, rightY, sideSize, sideSize, this);
        }
    }
}