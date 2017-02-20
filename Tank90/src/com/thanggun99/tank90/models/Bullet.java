package com.thanggun99.tank90.models;

import com.thanggun99.tank90.guis.MainCanvas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Bullet extends Sprite {

	static final int SMALL_BULLET = 0;
	static final int BIG_BULLET = 1;
	static final int MISSILE_BULLET = 2;
	private int speed = 4;
	private int currentType;
	private Tank parent;
	private boolean destroyed = false;

	public Bullet(BufferedImage image, int frameHeight, int frameWidth, Tank parent) {
		super(image, frameHeight, frameWidth);
		// TODO Auto-generated constructor stub
		this.setCurrentDirection(parent.getCurrentDirection());
		this.parent = parent;
		this.setPositionAndBound(parent.getX(), parent.getY());
	}
	
	public int getDamage(){
		switch (currentType){
		case SMALL_BULLET:
			return 25;
		case BIG_BULLET:
			return 35;
		case MISSILE_BULLET:
			return 100;
		}
		return 0;
	}

	public void setParent(Tank t) {
		this.parent = t;
	}
	
	public Tank getParent() {
		return this.parent;
	}
	

	public void render(Graphics g) {
		if (!this.destroyed) {
			super.render(g);
			//this.drawBound(g);
		}
	}
	
	public boolean isDestroyed(){
		return this.destroyed;
	}

	public void update() {
		switch (this.getCurrentDirection()) {
		case Sprite.UP:
			this.setPositionAndBound(this.x, this.y - speed);
			if (this.currentType == MISSILE_BULLET) this.setFrame(0);
			break;
		case Sprite.DOWN:
			this.setPositionAndBound(this.x, this.y + speed);
			if (this.currentType == MISSILE_BULLET) this.setFrame(2);
			break;
		case Sprite.LEFT:
			this.setPositionAndBound(this.x - speed, this.y);
			if (this.currentType == MISSILE_BULLET) this.setFrame(1);
			break;
		case Sprite.RIGHT:
			if (this.currentType == MISSILE_BULLET) this.setFrame(3);
			this.setPositionAndBound(this.x + speed, this.y);
			break;
		}
		boolean hit = false;
		for (int i = 0; i < MainCanvas.tm.getTotalBrick(); i++) {
			if (MainCanvas.tm.getBrickArray()[i] != null
					&& MainCanvas.t.isCollision(this, MainCanvas.tm
							.getBrickArray()[i]) && MainCanvas.tm.getBrickArray()[i].getType() != Brick.SEA) {
				hit = true;
				MainCanvas.tm.getBrickArray()[i].computeDamage(this.getDamage(), this);
			}
		}
		if (hit) this.makeExplosion();
	}

	public void makeExplosion() {
		MainCanvas.addExplosion(this.getX(), this.getY());
		this.destroyed = true;
	}
	

	public void setPositionAndBound(int x, int y) {
		this.x = x;
		this.y = y;
		switch (this.currentType){
		case SMALL_BULLET:
			this.setBound(13, 13, 6, 6);
			break;
		case BIG_BULLET:
			this.setBound(13, 13, 6, 6);
			break;
		case MISSILE_BULLET:
			switch (this.currentDirection){
			case Sprite.UP:
			case Sprite.DOWN:
				this.setBound(14, 12, 5, 8);
				break;
			case Sprite.LEFT:
			case Sprite.RIGHT:
				this.setBound(12, 14, 8, 5);
				break;
			}
		}
		
	}
	
	public void setBulletType(int type){
		this.currentType = type;
		this.currentType = type;
		if (type == SMALL_BULLET) this.setFrame(4);
		if (type == BIG_BULLET) this.setFrame(5);
	}
	
	public int getBulletType(){
		return this.currentType;
	}

	public void setSpeed(int speed){this.speed = speed;}
}
