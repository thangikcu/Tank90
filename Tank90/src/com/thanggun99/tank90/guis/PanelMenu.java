package com.thanggun99.tank90.guis;

import com.thanggun99.tank90.models.Tank;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PanelMenu extends JPanel implements ActionListener {
    private static final String ACTION_PLAY = "ACTION_PLAY";
    private static final String ACTION_SCORE = "ACTION_SCORE";
    private static final String ACTION_EXIT = "ACTION_EXIT";

    private JButton btnPlay;
    private JButton btnScore;
    private JButton btnExit;
    private IActionShowGame iActionShowGame;

    private Image bg = new ImageIcon(Tank.class.getResource("/res/images/bg.jpg")).getImage().getScaledInstance(
                                        GUI.WIDTH_FRAME, GUI.HEIGHT_FRAME, Image.SCALE_DEFAULT);

    public PanelMenu() {

        initPanel();
        initComponents();
        addComponents();
    }


    public void setiActionShowGame(IActionShowGame iActionShowGame) {
        this.iActionShowGame = iActionShowGame;
    }

    private void initPanel() {
        setLayout(null);
    }

    private void initComponents() {
        Image btPlay = new ImageIcon(Tank.class.getResource("/res/images/play.jpg")).getImage().getScaledInstance(
                110, 35, Image.SCALE_SMOOTH);
        Image btScore = new ImageIcon(Tank.class.getResource("/res/images/score.jpg")).getImage().getScaledInstance(
                110, 35, Image.SCALE_SMOOTH);
        Image btExit = new ImageIcon(Tank.class.getResource("/res/images/exit.jpg")).getImage().getScaledInstance(
                110, 35, Image.SCALE_SMOOTH);

        btnPlay = new JButton();
        btnPlay.setBounds(200, 450, 100, 35);
        btnPlay.setText("Play");
        btnPlay.setIcon(new ImageIcon(btPlay));
        btnPlay.setActionCommand(ACTION_PLAY);

        btnPlay.addActionListener(this);

        btnScore = new JButton();
        btnScore.setBounds(315, 450, 100, 35);
        btnScore.setText("Score");
        btnScore.setIcon(new ImageIcon(btScore));

        btnScore.setActionCommand(ACTION_SCORE);

        btnScore.addActionListener(this);

        btnExit = new JButton();
        btnExit.setBounds(430, 450, 70, 35);
        btnExit.setText("Exit");
        btnExit.setIcon(new ImageIcon(btExit));

        btnExit.setActionCommand(ACTION_EXIT);

        btnExit.addActionListener(this);

    }

    private void addComponents() {
        add(btnPlay);
        add(btnScore);
        add(btnExit);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font f = new Font("Georgia", Font.ITALIC, 12);
        g.setFont(f);
        g.drawImage(bg, 0, 0, null);
        g.drawString("Credit: Thanggun99", 620, 510);
        setForeground(Color.yellow);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = e.getActionCommand();
        switch (id) {
            case ACTION_PLAY:
                System.out.println("play");
                iActionShowGame.showGame();
                break;
            case ACTION_SCORE:
                System.out.println("Score");
                JOptionPane.showMessageDialog(null, "Chức năng chưa hoàn thiện !");
                break;
            case ACTION_EXIT:
                System.out.println("Exit");
                int result = JOptionPane.showConfirmDialog(null, "Are you sure ?", "thoát", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                break;
        }

    }
}
