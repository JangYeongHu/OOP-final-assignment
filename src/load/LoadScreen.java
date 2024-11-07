package src.load;

import src.interfaces.Screen;
import src.main.MainFrame;

import javax.swing.*;

public class LoadScreen extends JPanel implements Screen {
    private MainFrame mainFrame;

    public LoadScreen(MainFrame mainFrame) {
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