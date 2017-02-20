package com.thanggun99.tank90.guis;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.JPanel;

/**
 * Created by ThangIKCU on 5/05/2016.
 */
public class MyContainer extends JPanel implements IActionShowGame, IBackToMenu{
    public static final String PLAY = "PLAY";
    public static final String MENU = "MENU";
    private MainCanvas mainCanvas;
    private PanelMenu panelMenu;
    private CardLayout cardLayout;
    public MyContainer() throws IOException {
        initPanel();
        initComponents();
        addComponents();
    }

    private void initPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setSize(GUI.WIDTH_FRAME, GUI.HEIGHT_FRAME);
    }

    private void initComponents() throws IOException {
        panelMenu = new PanelMenu();

        panelMenu.setiActionShowGame(this);
    }

    private void addComponents() {

        add(panelMenu, MENU);
        cardLayout.show(this, MENU);
    }

    @Override
    public void showGame() {
        mainCanvas = new MainCanvas();
        mainCanvas.setOnBackToMenuListener(this);
        add(mainCanvas, PLAY);
        cardLayout.show(this, PLAY);
        mainCanvas.requestFocus();
        mainCanvas.start();
    }

    @Override
    public void backToMenu() {
        cardLayout.show(this, MENU);
        this.remove(mainCanvas);
    }
}
