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

    private final Color COLOR_DARK_GREEN = new Color(0x1A3636); // #1a3636
    private final Color COLOR_GREEN = new Color(0x40534C); // #40534c
    private final Color COLOR_LIGHT_GREEN = new Color(0x677D6A); // #677d6a
    private final Color COLOR_YELLOW = new Color(0xD6BD98); // #d6bd98

    private MainController mainController;

    private Player player = Player.getInstance();

    private ImageIcon swordImages[] = new ImageIcon[20];
    private ImageIcon blackSwordImages[] = new ImageIcon[20];

    private int nowIndex = 0;

    private JLabel centerImage;
    private JPanel centerLabelBox;
    private JLabel centerLabel;
    private JLabel centerDescription;
    private JLabel leftImage;
    private JLabel rightImage;

    public CollectionScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }
    @Override
    public void initialize() {
        setLayout(new BorderLayout());
        setBackground(COLOR_GREEN);
        generateUpperButton();
        loadSwordImages();
        generateMiddleImages();
        generateLowArrow();
        showSwordImages();
    }

    private void generateUpperButton() {
        JButton exitButton = new JButton("돌아가기");
        exitButton.setForeground(COLOR_YELLOW);
        exitButton.setFont(new Font("DungGeunMo", Font.PLAIN, 16));
        exitButton.setBackground(COLOR_GREEN);
        exitButton.setBounds(50,25,200,50);
        exitButton.setBorder(BorderFactory.createLineBorder(COLOR_LIGHT_GREEN, 3));;
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.switchTo("Statistics");
            }
        });
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(COLOR_LIGHT_GREEN);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(COLOR_GREEN);
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
        add(middleImages);
    }

    private void generateLowArrow() {
        JPanel LowPanel = new JPanel(new BorderLayout());
        LowPanel.setOpaque(false);
        generateLeftArrow(LowPanel);
        generateRightArrow(LowPanel);

        centerLabelBox = new JPanel(new BorderLayout());
        centerLabelBox.setBounds(450,650,200,50);
        centerLabelBox.setOpaque(false);
        centerLabel = new JLabel();
        centerLabel.setFont(new Font("DungGeunMo",Font.PLAIN,35));
        centerLabel.setForeground(COLOR_YELLOW);
        centerLabel.setHorizontalAlignment(JLabel.CENTER);
        centerLabel.setVerticalAlignment(JLabel.CENTER);
        centerLabelBox.add(centerLabel,BorderLayout.NORTH);

        centerDescription = new JLabel();
        centerDescription.setFont(new Font("DungGeunMo",Font.PLAIN,15));
        centerDescription.setForeground(COLOR_YELLOW);
        centerDescription.setHorizontalAlignment(JLabel.CENTER);
        centerDescription.setVerticalAlignment(JLabel.CENTER);
        centerLabelBox.add(centerDescription,BorderLayout.CENTER);

        LowPanel.add(centerLabelBox,BorderLayout.CENTER);
        add(LowPanel,BorderLayout.PAGE_END);
    }

    private void generateLeftArrow(JPanel middlePanel) {
        ImageIcon leftImageIcon = new ImageIcon("resources/left-arrow-button.png");
        ImageIcon leftImageIconClicked = new ImageIcon("resources/left-arrow-button-clicked.png");
        ImageIcon leftImage = new ImageIcon(leftImageIcon.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH));
        ImageIcon leftImageClicked = new ImageIcon(leftImageIconClicked.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH));
        JLabel leftArrowLabel = new JLabel(leftImage);
        leftArrowLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(nowIndex > 0) {
                    nowIndex--;
                    showSwordImages();
                }
            }
        });

        leftArrowLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                leftArrowLabel.setIcon(leftImageClicked);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                leftArrowLabel.setIcon(leftImage);
            }
        });

        middlePanel.add(leftArrowLabel,BorderLayout.WEST);
    }

    private void generateRightArrow(JPanel middlePanel) {
        ImageIcon rightImageIcon = new ImageIcon("resources/right-arrow-button.png");
        ImageIcon rightImageIconClicked = new ImageIcon("resources/right-arrow-button-clicked.png");
        ImageIcon rightImage = new ImageIcon(rightImageIcon.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH));
        ImageIcon rightImageClicked = new ImageIcon(rightImageIconClicked.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH));
        JLabel rightImageLabel = new JLabel(rightImage);
        rightImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(nowIndex < 19) {
                    nowIndex++;
                    showSwordImages();
                }
            }
        });

        rightImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rightImageLabel.setIcon(rightImageClicked);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                rightImageLabel.setIcon(rightImage);
            }
        });


        middlePanel.add(rightImageLabel,BorderLayout.EAST);
    }

    @Override
    public void showScreen() {
        showSwordImages();
        setVisible(true);
    }

    @Override
    public void hideScreen() {
        setVisible(false);
    }

    private void loadSwordImages() {
        for (int i = 0; i < 20; i++)
            swordImages[i] = new ImageIcon("resources/"+(i+1)+".png");
        for (int i = 0; i < 20; i++)
            blackSwordImages[i] = new ImageIcon("resources/collectionScreen/"+(i+1)+".png");
    }

    private void showSwordImages() {
        centerImage.setIcon(new ImageIcon(isSwordShow(nowIndex).getImage().getScaledInstance(400,400,Image.SCALE_SMOOTH)));
        isTextShow(nowIndex);
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

    private void isTextShow(int index) {
        if(player.getBestSword() >= index) {
            centerLabel.setText(MainController.swordList[nowIndex].getName());
            centerDescription.setText(MainController.swordList[nowIndex].getDescription());
        } else {
            centerLabel.setText("?????");
            centerDescription.setText("??????????????????????????");
        }
    }
}