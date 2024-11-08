package src.screen;

import src.screen.interfaces.Screen;
import src.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingScreen extends JPanel implements Screen {

    private MainFrame mainFrame;

    public SettingScreen(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initialize();
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Setting Screen");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Go to Start Screen");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchTo("Start");
            }
        });
        add(backButton, BorderLayout.SOUTH);
    }

    @Override
    public void showScreen() {
        setVisible(true);
    }

    @Override
    public void hideScreen() {
        setVisible(false);
    }
}
