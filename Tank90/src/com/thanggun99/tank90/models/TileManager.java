package com.thanggun99.tank90.models;

import com.thanggun99.tank90.guis.MainCanvas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/*
 * Map file
 * TODO 
 * add more brick style
 * add sea style
 * add rock style
 * record around golden bird place for item use 
 */

public class TileManager {
	private final int MAP_WIDTH = 25;
	private final int MAP_HEIGHT = 25;

	private static Brick[] brickArray = new Brick[425];
	private int totalBrick = 0;
	private Sprite goldenBird;
	private BufferedImage background;
	public static int goldenEagleRow;
	public static int goldenEagleCol;

	public TileManager() throws IOException{
		background = MainCanvas.t.getTileImage().getSubimage(64, 32, 32, 32);
		
		goldenBird = new Sprite(MainCanvas.t.getTileImage().getSubimage(0, 96, 64, 32), 32, 32);
		goldenBird.setFrameStrip(new int[]{0, 1});
		
		for (int i = 0; i < 425; i++)
			brickArray[i] = null;
	}
	
	public void loadMap(int level){
		String mapFile = "src/res/maps/" + String.valueOf(level) + ".map";
		readMap(mapFile);
	}
	
	public void update(){
		for (int i = 0; i < totalBrick; i++){
			if (brickArray[i] != null) brickArray[i].update();
		}
	}

	public void readMap(String mapName) {
		try {
			FileInputStream in = new FileInputStream(mapName);
			for (int i = 0; i < MAP_HEIGHT; i++) {
				for (int j = 0; j < MAP_WIDTH; j++) {
					int c = in.read();
					// System.out.println("c  = " + c);
					if (Brick.isBrick(c)) {
						Brick tmp = new Brick(MainCanvas.t
								.getTileImage(), 32, 32, totalBrick, Brick.getBrickType(c));
						tmp.setRowAndCol(i, j);
						brickArray[totalBrick] = tmp;
						brickArray[totalBrick].setPositionAndBound(j * 32,
								i * 32);
						tmp = null;
						totalBrick++;
					} else if (c == 24) {
						// Golden Bird
						goldenBird.setPositionAndBound(j*32, i*32);
						this.goldenEagleRow = i;
						this.goldenEagleCol = j;
					}
				}
			}
			// System.out.print("total = " + totalBrick);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < MAP_HEIGHT; i++) {
			for (int j = 0; j < MAP_WIDTH; j++) {
				g.drawImage(background, j * 32, i * 32, null);
			}
		}
		
		for (int i = 0; i < totalBrick; i++)
			if (brickArray[i] != null)
				brickArray[i].render(g);
		goldenBird.render(g);
	}

	public Brick[] getBrickArray() {
		return brickArray;
	}

	public int getTotalBrick() {
		return totalBrick;
	}

	public void cleanBrick(int index) {
		// TODO Auto-generated method stub
		TileManager.brickArray[index] = null;
	}
	
	public void cleanAllBricks(){
		for (int i=0; i<425; i++){
			brickArray[i] = null;
		}
		totalBrick = 0;
		this.goldenBird.setFrame(0);
	}
	
	public boolean isCollisionWithBricks(Sprite a) {
		for (int i = 0; i < MainCanvas.tm.getTotalBrick(); i++) {
			if (brickArray[i] != null
					&& MainCanvas.t.isCollision(a, brickArray[i])) {
				if (a instanceof Tank) return true;
			}
		}
		return false;
	}

	public Sprite getGoldenBird() {
		return goldenBird;
	}

	public void setGoldenBird(Sprite goldenBird) {
		this.goldenBird = goldenBird;
	}
}
