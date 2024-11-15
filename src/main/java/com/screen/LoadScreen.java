package com.screen;

import com.app.MainController;
import com.screen.interfaces.Screen;

import javax.swing.*;

public class LoadScreen extends JPanel implements Screen {
    private MainController mainController;

    public LoadScreen(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    private void loadData() {

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