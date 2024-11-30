package com.screen;

import com.app.MainController;
import com.item.Sword;
import com.player.Player;
import com.screen.interfaces.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CollectionScreen extends JPanel implements Screen {

    private MainController mainController;

    private ImageIcon swordImages[] = new ImageIcon[20];

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
    }

    void generateLowArrow() {
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(this.getBackground());
        generateLeftArrow(middlePanel);
        generateRightArrow(middlePanel);
        add(middlePanel,BorderLayout.PAGE_END);
    }

    private void generateLeftArrow(JPanel middlePanel) {
        ImageIcon leftImageIcon = new ImageIcon("src/main/resources/left-arrow-button.png");
        Image leftImage = leftImageIcon.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH);
        JLabel leftArrowLabel = new JLabel(new ImageIcon(leftImage));
        middlePanel.add(leftArrowLabel,BorderLayout.WEST);
    }

    private void generateRightArrow(JPanel middlePanel) {
        ImageIcon rightImageIcon = new ImageIcon("src/main/resources/right-arrow-button.png");
        Image rightImage = rightImageIcon.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH);
        JLabel rightImageLabel = new JLabel(new ImageIcon(rightImage));
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
        for (int i = 0; i < 20; i++) {
            Sword sword = MainController.swordList[i];
                swordImages[i] = new ImageIcon("src/main/resources/"+(i+1)+".png");
        }
    }
}
