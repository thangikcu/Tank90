package com.thanggun99.tank90.models;

import com.thanggun99.tank90.guis.MainCanvas;
import com.thanggun99.tank90.managers.AudioPlayer;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerTank extends Tank {

    int lives;
    int score;
    boolean delay = false;

    public boolean[] keyPressedState = {false, false, false, false};

    public PlayerTank(BufferedImage image, int frameHeight, int frameWidth) {
        super(image, frameHeight, frameWidth);
        this.lives = 3;
        this.score = 0;
    }

    public void spawnd() {
        this.setPositionAndBound(10 * 32, 16 * 32);
        setCurrentDirection(Sprite.UP);
        this.setBulletType(Bullet.SMALL_BULLET);
        this.setTotalHealth(100);
        this.setSpeedStep(3);
        this.currentHealth = this.totalHealth;
        this.setDestroyed(false);
    }

    public void delayTank() {
        this.delay = true;
        this.setRunning(false);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                delay = false;
            }
        }, 1500);
    }

    public void keyPressedReact(KeyEvent e) {
        // System.out.println("Key pressed = " + e.getKeyCode());
        if (!delay) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                MainCanvas.audioPlayer.loopSound(AudioPlayer.PLAYER_MOVE);
                keyPressedState[Sprite.UP] = true;
                setCurrentDirection(Sprite.UP);
                this.setRunning(true);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                MainCanvas.audioPlayer.loopSound(AudioPlayer.PLAYER_MOVE);
                keyPressedState[Sprite.DOWN] = true;
                this.setCurrentDirection(Sprite.DOWN);
                this.setRunning(true);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                MainCanvas.audioPlayer.loopSound(AudioPlayer.PLAYER_MOVE);
                keyPressedState[Sprite.LEFT] = true;
                this.setCurrentDirection(Sprite.LEFT);
                this.setRunning(true);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                MainCanvas.audioPlayer.loopSound(AudioPlayer.PLAYER_MOVE);
                keyPressedState[Sprite.RIGHT] = true;
                this.setCurrentDirection(Sprite.RIGHT);
                this.setRunning(true);
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                this.fire();
            }
        }
    }

    public void update() {
        if (!delay) {
            super.update();
            if (this.isDestroyed()) {
                this.lives--;

                if (this.lives != 0) {
                    MainCanvas.audioPlayer.playSound(AudioPlayer.PLAYER_DIE);
                    this.spawnd();
                    delayTank();
                } else {
                    MainCanvas.audioPlayer.playSound(AudioPlayer.GAME_OVER);
                    MainCanvas.setGameOver(true);
                }
            } else {
                int count = 0;
                for (int i = 0; i < this.keyPressedState.length; i++) {
                    if (this.keyPressedState[i]) {
                        count++;
                    }
                }

                for (int i = 0; i < this.keyPressedState.length; i++) {
                    if (this.keyPressedState[i]) {
                        if (count >= 2) {
                            if (this.getCurrentDirection() != i) {
                                this.setRunning(true);
                            }
                        }

                        if (count == 1) {
                            this.setCurrentDirection(i);
                            this.setRunning(true);
                        }
                    }
                }
            }
            checkCollisonWithItem();
        }
    }

    public void keyReleasedReact(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            this.keyPressedState[Sprite.UP] = false;
            if (this.getCurrentDirection() == Sprite.UP)
                this.setRunning(false);
        }
        if (key == KeyEvent.VK_DOWN) {
            this.keyPressedState[Sprite.DOWN] = false;
            if (this.getCurrentDirection() == Sprite.DOWN)
                this.setRunning(false);
        }
        if (key == KeyEvent.VK_LEFT) {
            this.keyPressedState[Sprite.LEFT] = false;
            if (this.getCurrentDirection() == Sprite.LEFT)
                this.setRunning(false);
        }
        if (key == KeyEvent.VK_RIGHT) {
            this.keyPressedState[Sprite.RIGHT] = false;
            if (this.getCurrentDirection() == Sprite.RIGHT)
                this.setRunning(false);
        }
        if (key == KeyEvent.VK_SPACE) {
        }
    }

    public void setLives(int value) {
        this.lives = value;
    }

    public int getLives() {
        return this.lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int i) {
        this.score += i;
    }
}
