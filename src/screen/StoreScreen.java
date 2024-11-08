package src.screen;

import src.screen.interfaces.Screen;
import src.main.MainController;

import javax.swing.*;

public class StoreScreen extends JPanel implements Screen {

    private MainController mainController;

    public StoreScreen(MainController mainController) {
        this.mainController = mainController;
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
