package com.thanggun99.tank90.models;

import com.thanggun99.tank90.guis.MainCanvas;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Item extends Sprite {


	public final int MISSILE_BULLET = 0;
	public final int BIG_BULLET = 1;
	public final int LIVE = 2;
	public final int SHOVEL = 3;
	public final int WRECH = 4;
	public final int THUNDER_BOOST = 5;
	public final int CLOCK = 6;
	public final int BOMB = 7;
	private int type;

	public Item(BufferedImage image, int frameHeight, int frameWidth) {
		super(image, frameHeight, frameWidth);
		// TODO Auto-generated constructor stub
		this.setFrameStrip(new int[] { 0, 1, 2, 3, 4, 5, 6, 7 });
	}

	public void applyEffect(Tank a) {
		switch (this.type) {
		case BIG_BULLET:
			a.setBulletType(Bullet.BIG_BULLET);
			break;
		case MISSILE_BULLET:
			a.setBulletType(Bullet.MISSILE_BULLET);
			break;
		case WRECH:
			if (a.getCurrentHealth() + 50 >= a.totalHealth) {
				a.setCurrentHealth(a.totalHealth);
			} else
				a.setCurrentHealth(a.getCurrentHealth() + 50);
			break;
		case SHOVEL:
			Timer t = new Timer();
			t.schedule(new ToDoTank(this, a), 10000);
			if (a instanceof PlayerTank) {
				int row = MainCanvas.tm.goldenEagleRow;
				int col = MainCanvas.tm.goldenEagleCol;
				for (int i = 0; i < MainCanvas.tm.getTotalBrick(); i++) {
					if (MainCanvas.tm.getBrickArray()[i] != null) {
						Brick tmp = MainCanvas.tm.getBrickArray()[i];
						if (tmp.row == row
								&& ((tmp.col == col - 1) || (tmp.col == col + 1))) {
							MainCanvas.tm.getBrickArray()[i].setType(Brick.WHITE_ROCK);
						}
						if (tmp.row == row - 1 && tmp.col >= col - 1
								&& tmp.col <= col + 1) {
							MainCanvas.tm.getBrickArray()[i].setType(Brick.WHITE_ROCK);
						}
					}
				}
			}
			break;
		case THUNDER_BOOST:
			a.setSpeedStep(6);
			t = new Timer();
			t.schedule(new ToDoTank(this, a), 10000);
			break;
		case CLOCK:
			if (a instanceof PlayerTank) {
				for (int i = 0; i < MainCanvas.tankArray.size(); i++) {
					if (MainCanvas.tankArray.get(i) instanceof AITank) {
						((AITank) MainCanvas.tankArray.get(i)).setFrezzed(true);
					}
				}
			}
			t = new Timer();
			t.schedule(new ToDoTank(this, a), 10000);
			break;
		case LIVE:
			if (a instanceof PlayerTank)
				((PlayerTank) a).setLives(((PlayerTank) a).getLives() + 1);
			break;
		case BOMB:
			if (a instanceof PlayerTank) {
				for (int i = 0; i < MainCanvas.tankArray.size(); i++) {
					if (MainCanvas.tankArray.get(i) instanceof AITank) {
						MainCanvas.tankArray.get(i).receivedDamage += 50;
					}
				}
			}
			break;
		}
	}

	public void setPositionAndBound(int x, int y) {
		this.x = x;
		this.y = y;
		switch (this.type) {
		case BIG_BULLET:
			this.setBound(13, 13, 6, 6);
			break;
		case MISSILE_BULLET:
			this.setBound(12, 5, 9, 24);
			break;
		case LIVE:
			this.setBound(9, 8, 15, 18);
			break;
		case THUNDER_BOOST:
			this.setBound(5, 0, 24, 32);
		case CLOCK:
			this.setBound(4, 6, 24, 23);
		case BOMB:
			this.setBound(5, 2, 22, 27);
			break;
		case WRECH:
		case SHOVEL:
			this.setBound(0, 0, 32, 32);
			break;
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		this.setFrame(type);
	}

	class ToDoTank extends TimerTask {

		private Item item;
		private Tank tank;

		public ToDoTank(Item x, Tank y) {
			this.item = x;
			this.tank = y;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			switch (item.type) {
			case THUNDER_BOOST:
				tank.setSpeedStep(3);
				break;
			case CLOCK:
				for (int i = 0; i < MainCanvas.tankArray.size(); i++) {
					if (MainCanvas.tankArray.get(i) instanceof AITank) {
						((AITank) MainCanvas.tankArray.get(i))
								.setFrezzed(false);
					}
				}
				break;
			case SHOVEL:
				int row = MainCanvas.tm.goldenEagleRow;
				int col = MainCanvas.tm.goldenEagleCol;
				for (int i = 0; i < MainCanvas.tm.getTotalBrick(); i++) {
					if (MainCanvas.tm.getBrickArray()[i] != null) {
						Brick tmp = MainCanvas.tm.getBrickArray()[i];
						if (tmp.row == row
								&& ((tmp.col == col - 1) || (tmp.col == col + 1))) {
							tmp.setType(Brick.RED_H_BRICK);
						}
						if (tmp.row == row - 1 && tmp.col >= col - 1
								&& tmp.col <= col + 1) {
							tmp.setType(Brick.RED_H_BRICK);
						}
					}
				}
				break;
			}
		}
	}
}
