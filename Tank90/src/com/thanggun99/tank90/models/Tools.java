package com.thanggun99.tank90.models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tools {

    public static BufferedImage playerTankImage = null;
    public static BufferedImage bulletImage = null;
    private static BufferedImage tileImage = null;
    private static BufferedImage explosionImage = null;
    private static BufferedImage aiTankImage;
    private BufferedImage itemImage;
    private BufferedImage redTankImage;
    private BufferedImage bossImage;

    public Tools() {
    }

    public BufferedImage getPlayerTankImage() throws IOException {
        if (playerTankImage == null) {
            playerTankImage = ImageIO.read(new File("src/res/images/playertank.png"));
        }
        return playerTankImage;
    }

    public BufferedImage getBulletImage() throws IOException {
        if (bulletImage == null) {
            bulletImage = ImageIO.read(new File("src/res/images/bullet.png"));
        }
        return bulletImage;
    }

    public BufferedImage getTileImage() throws IOException {
        if (tileImage == null) {
            tileImage = ImageIO.read(new File("src/res/images/tileset1.png"));
        }
        return tileImage;
    }

    public BufferedImage getAITankImage() throws IOException {
        if (aiTankImage == null) {
            aiTankImage = ImageIO.read(new File("src/res/images/aitank.png"));
        }
        return aiTankImage;
    }

    public BufferedImage getExplosionImage() throws IOException {
        if (explosionImage == null) {
            explosionImage = ImageIO.read(new File("src/res/images/explosion1.png"));
        }
        return explosionImage;
    }

    public BufferedImage getItemImage() throws IOException {
        if (itemImage == null) {
            itemImage = ImageIO.read(new File("src/res/images/items.png"));
        }
        return itemImage;
    }

    public BufferedImage getRedTankImage() throws IOException {
        if (redTankImage == null) {
            redTankImage = ImageIO.read(new File("src/res/images/redtank.png"));
        }
        return redTankImage;
    }

    public BufferedImage getBossImage() throws IOException {
        if (bossImage == null) {
            bossImage = ImageIO.read(new File("src/res/images/boss.png"));
        }
        return bossImage;
    }

    public boolean isCollision(Sprite a, Sprite b) {

        if (a.getBoundX() + a.getBoundWidth() < b.getBoundX() ||
                a.getBoundX() > b.getBoundX() + b.getBoundWidth() ||
                a.getBoundY() + a.getBoundHeight() < b.getBoundY() ||
                a.getBoundY() > b.getBoundY() + b.getBoundHeight()
                ) return false;
        return true;
    }

    public boolean isOutScreen(Sprite a) {
        if (a.getBoundX() < 0 ||
                a.getBoundY() < 0 ||
                a.getBoundY() + a.getBoundHeight() > 544 ||
                a.getBoundX() + a.getBoundWidth() > 800) {
            return true;
        }
        return false;
    }

    public boolean isInBound(Tank a, int x, int y, int width, int height) {
        if (a.getX() >= x + width || a.getX() + a.getWidth() <= x || a.getY() + a.getHeight() <= y || a.getY() >= y + height) {
            return false;
        }
        return true;
    }
}




