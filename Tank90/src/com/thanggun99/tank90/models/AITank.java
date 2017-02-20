package com.thanggun99.tank90.models;

import com.thanggun99.tank90.guis.MainCanvas;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class AITank extends Tank {

	private Random rnd;

	private boolean keepDoing = false;

	private boolean frezzed = false;
	public static final int RED_TANK = 0;
	public static final int BLUE_TANK = 1;
	int level = MainCanvas.currentLevel;
	private int type;
	int playerx;
	int playery;
	int tankx;
	int tanky;

	public AITank(BufferedImage image, int frameHeight, int frameWidth, int type) {
		super(image, frameHeight, frameWidth);
		// TODO Auto-generated constructor stub
		rnd = new Random(new Date().getTime());
		this.setCurrentDirection(Sprite.DOWN);
		this.bulletDelayTime = 800 - (level-1) * 50;
		if (this.bulletDelayTime < 150) this.bulletDelayTime = 150;
		this.setTotalHealth(100 + (level-1) *20);
		this.currentHealth = this.totalHealth;
		this.type = type;
	}

	public void think() {
		if (frezzed){
			this.setRunning(false);
		}
		else if (getLuck()){
			playerx = MainCanvas.tankArray.get(0).getX();
			playery = MainCanvas.tankArray.get(0).getY();
			tankx = this.getX();
			tanky = this.getY();
			if (!InHorizon() && !InVertical()) {
				RandomAction();
			}
			else if (InVertical() && !InHorizon()){
				if (tanky + 32 <= playery) {
					SetDirectionAndFire(Sprite.DOWN);
				}
				else if (tanky - 32 >= playery ){
					SetDirectionAndFire(Sprite.UP);
				}
			}
			else if (InHorizon() && !InVertical()){
				if (tankx + 32 < playerx){
					SetDirectionAndFire(Sprite.RIGHT);
				}
				else if (tankx -32 > playerx){
					SetDirectionAndFire(Sprite.LEFT);
				}
			}
			else if (InHorizon() && InVertical()){
				if (tankx + 32 > playerx && tankx - 32 < playerx)
					if (tanky>playery) SetDirectionAndFire(Sprite.UP);
					else SetDirectionAndFire(Sprite.DOWN);
				if (tanky + 32 > playery && tanky - 32 < playery)
					if (tankx>playerx) SetDirectionAndFire(Sprite.LEFT);
					else SetDirectionAndFire(Sprite.RIGHT);
			}
		}
		else RandomAction();
	}
	
	public boolean InVertical(){
		if ((tankx - 32 < playerx) && (playerx < tankx + 32)) 
			return true;
		else return false;
	}
	
	public boolean InHorizon(){
		if ((tanky - 32 < playery) && (playery < tanky + 32)) 
			return true;
		else return false;
	}
	
	public boolean getLuck(){
		rnd = new Random(new Date().getTime());
		if (level + rnd.nextInt(10) > 8) return true;
		return false ;
	}
	
	public void SetDirectionAndFire(int dir){
		this.setCurrentDirection(dir);
		this.fire();
	}
	
	public void RandomAction(){
		rnd = new Random(new Date().getTime());
		int i = 0;
		for (int j = 0; j < 10; j++)
			i = rnd.nextInt(100);

		if (i % 37 == 0) {
			// change direction
			this.setKeepDoing(false);
			this.setRunning(true);
			this.setCurrentDirection(rnd.nextInt(4));
		}

		if (i < 50) {
			this.setRunning(true);
			this.setKeepDoing(true);
			if (this.pathBlocked) {
				this.fire();
				this.setCurrentDirection(rnd.nextInt(4));
			}
		}

		if (i % 11 == 0)
			fire();
	}

	public boolean isKeepDoing() {
		return keepDoing;
	}

	public void setKeepDoing(boolean keepDoing) {
		this.keepDoing = keepDoing;
	}
	
	public void setFrezzed(boolean value){
		this.frezzed = value;
	}
	
	public int getType() {
		return this.type;
	}
}
