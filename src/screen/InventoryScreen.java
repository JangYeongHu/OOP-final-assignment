package src.screen;

import src.main.MainController;
import src.screen.interfaces.Screen;

import javax.swing.*;

public class InventoryScreen extends JPanel implements Screen {

    private MainController mainController;
    public InventoryScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    @Override
    public void initialize() {
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
