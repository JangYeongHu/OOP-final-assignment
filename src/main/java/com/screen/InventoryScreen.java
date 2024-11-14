package com.screen;

import com.app.MainController;

import javax.swing.*;

import com.screen.interfaces.Screen;

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
