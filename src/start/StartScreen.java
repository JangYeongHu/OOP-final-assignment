package src.start;

import src.interfaces.Screen;
import src.main.MainFrame;

import javax.swing.*;

public class StartScreen extends JPanel implements Screen {

    private MainFrame mainFrame;

    public StartScreen(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initialize();
    }
    @Override
    public void initialize() {

    }

    @Override
    public void showScreen() {

    }

    @Override
    public void hideScreen() {

    }
}
