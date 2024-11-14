package com.screen;



import com.app.MainController;

import javax.swing.*;

import com.screen.interfaces.Screen;

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
