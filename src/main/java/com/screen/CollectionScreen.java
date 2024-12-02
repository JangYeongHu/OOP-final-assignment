package com.screen;

import com.app.MainController;
import com.player.Player;
import com.screen.interfaces.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CollectionScreen extends JPanel implements Screen {

    private MainController mainController;

    private Player player = Player.getInstance();

    private ImageIcon swordImages[] = new ImageIcon[20];
    private ImageIcon blackSwordImages[] = new ImageIcon[20];

    private int nowIndex = 0;

    private JLabel centerImage;
    private JLabel leftImage;
    private JLabel rightImage;

    public CollectionScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }
    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        setBackground(new Color(0x40534C));
        generateUpperButton();
        loadSwordImages();
        generateMiddleImages();
        generateLowArrow();
    }

    private void generateUpperButton() {
        JButton exitButton = new JButton("돌아가기");
        exitButton.setForeground(new Color(214, 189, 152));
        exitButton.setFont(new Font("DungGeunMo", Font.PLAIN, 16));
        exitButton.setBackground(new Color(64, 83, 76));
        exitButton.setBounds(50,25,200,50);
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(0x677d6a), 3));;
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Statistics");
            }
        });
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(new Color(103, 125, 106));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(new Color(64, 83, 76));
            }
        });

        add(exitButton);
    }

    private void generateMiddleImages() {
        JPanel middleImages = new JPanel(null);
        middleImages.setBackground(this.getBackground());
        int centerX = (1200-400)/2;
        int centerY = (800-400)/2;

        centerImage = new JLabel();
        centerImage.setBounds(centerX,centerY,400,400);

        leftImage = new JLabel();
        leftImage.setBounds(centerX-300-40,centerY+20,300,300);

        rightImage = new JLabel();
        rightImage.setBounds(centerX+400+40,centerY+20,300,300);

        middleImages.add(centerImage);
        middleImages.add(leftImage);
        middleImages.add(rightImage);
        showSwordImages();
        add(middleImages);
    }

    private void generateLowArrow() {
        JPanel LowPanel = new JPanel(new BorderLayout());
        LowPanel.setBackground(this.getBackground());
        generateLeftArrow(LowPanel);
        generateRightArrow(LowPanel);
        add(LowPanel,BorderLayout.PAGE_END);
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
                    showSwordImages();
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
                    showSwordImages();
                }
            }
        });
        middlePanel.add(rightImageLabel,BorderLayout.EAST);
    }

    @Override
    public void showScreen() {
        setVisible(true);
    }

    @Override
    public void hideScreen() {
        setVisible(false);
    }

    private void loadSwordImages() {
        for (int i = 0; i < 20; i++)
            swordImages[i] = new ImageIcon("src/main/resources/"+(i+1)+".png");
        for (int i = 0; i < 20; i++)
            blackSwordImages[i] = new ImageIcon("src/main/resources/collectionScreen/"+(i+1)+".png");
    }

    private void showSwordImages() {
        centerImage.setIcon(new ImageIcon(isSwordShow(nowIndex).getImage().getScaledInstance(400,400,Image.SCALE_SMOOTH)));
        if(nowIndex > 0)
            leftImage.setIcon(new ImageIcon(isSwordShow(nowIndex-1).getImage().getScaledInstance(300,300,Image.SCALE_SMOOTH)));
        else leftImage.setIcon(new ImageIcon());
        if(nowIndex < 19)
            rightImage.setIcon(new ImageIcon(isSwordShow(nowIndex+1).getImage().getScaledInstance(300,300,Image.SCALE_SMOOTH)));
        else rightImage.setIcon(new ImageIcon());
    }

    private ImageIcon isSwordShow(int index) {
        if(player.getBestSword() >= index) return swordImages[index];
        return blackSwordImages[index];
    }
}