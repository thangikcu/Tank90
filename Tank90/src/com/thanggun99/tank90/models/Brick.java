package com.thanggun99.tank90.models;

import com.thanggun99.tank90.guis.MainCanvas;
import com.thanggun99.tank90.managers.AudioPlayer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Brick extends Sprite {

	private static int[][] bricksFrameStrip = { { 0, 1, 2, 3 }, { 4, 5, 6, 7 },
			{ 16, 17, 18, 19 }, { 20, 21, 22, 23 }, { 12, 13, 14, 15 },
			{ 28, 29, 31, 31 }, {11}};
	private int index;
	private int type;
	public static final int RED_V_BRICK = 0;
	public static final int RED_H_BRICK = 1;
	public static final int BLUE_V_BRICK = 2;
	public static final int BLUE_H_BRICK = 3;
	public static final int WHITE_ROCK = 4;
	public static final int GREEN_ROCK = 5;
	public static int SEA = 6; 
	public int row;
	public int col;

	private int currentHealth;

	public Brick(BufferedImage image, int frameHeight, int frameWidth,
			int index, int type) {
		super(image, frameHeight, frameWidth);
		// TODO Auto-generated constructor stub
		this.setType(type);
		this.setFrameStrip(bricksFrameStrip[type]);
		this.setFrame(this.frameStrip[0]);
		this.index = index;
		this.setBound(0, 0, frameWidth, frameHeight);
		this.currentHealth = 100;
	}

	public static boolean isBrick(int frameNumber) {
		for (int i = 0; i < bricksFrameStrip.length; i++)
			for (int j = 0; j < bricksFrameStrip[i].length; j++)
				if (frameNumber == bricksFrameStrip[i][j])
					return true;
		return false;
	}

	public void update() {
		this.setFrameStrip(bricksFrameStrip[this.type]);
		if (this.currentHealth > 75) {
			this.setFrame(this.frameStrip[0]);
		} else if (this.currentHealth > 50) {
			this.setFrame(this.frameStrip[1]);
		} else if (this.currentHealth > 25) {
			this.setFrame(this.frameStrip[2]);
		} else if (this.currentHealth > 0) {
			this.setFrame(this.frameStrip[3]);
		} else {
			MainCanvas.audioPlayer.playSound(AudioPlayer.EXPLOSION);
			this.setDestroyed(true);
			MainCanvas.tm.cleanBrick(this.index);
		}
	}

	public void computeDamage(int damage, Bullet b) {
		// TODO Auto-generated method stub
		if (this.type == Brick.SEA) return;
		if (this.type == Brick.WHITE_ROCK || this.type == Brick.GREEN_ROCK){
			if (b.getBulletType() == Bullet.MISSILE_BULLET){
				this.addDamage(25);
			} else this.addDamage(0);
		} else {
			this.addDamage(damage);
		}
	}

	private void addDamage(int i) {
		if (this.currentHealth - i > 0){
			this.currentHealth -= i;
		} else this.currentHealth = 0;
	}

	public static int getBrickType(int c) {
		for (int i = 0; i < bricksFrameStrip.length; i++) {
			for (int j = 0; j < bricksFrameStrip[i].length; j++) {
				if (c == bricksFrameStrip[i][j])
					return i;
			}
		}
		return 0;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setRowAndCol(int i, int j) {
		// TODO Auto-generated method stub
		this.row = i;
		this.col = j;
	}

	
}
