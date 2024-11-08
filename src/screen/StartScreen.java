package src.screen;

import src.screen.interfaces.Screen;
import src.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel implements Screen {

    private MainFrame mainFrame;

    public StartScreen(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initialize();
    }
    @Override
    public void initialize() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Start Screen");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Go to Setting Screen");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.switchTo("Setting");
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
