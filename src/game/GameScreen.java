package src.game;

import src.interfaces.Screen;
import src.main.MainFrame;

import javax.swing.*;

public class GameScreen extends JPanel implements Screen {

    private MainFrame mainFrame;
    public GameScreen(MainFrame mainFrame) {
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
