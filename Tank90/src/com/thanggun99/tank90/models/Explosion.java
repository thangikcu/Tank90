package com.thanggun99.tank90.models;

import com.thanggun99.tank90.guis.MainCanvas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Explosion extends Sprite {

	public Explosion(BufferedImage image, int frameHeight, int frameWidth) {
		super(image, frameHeight, frameWidth);
		// TODO Auto-generated constructor stub
		this.setFrameStrip(new int[]{0, 1, 2, 3, 4, 5, 6});
		this.setFrame(this.frameStrip[0]);
	}
	
	public void update(){
		if (this.currentFrame == this.frameStrip.length - 1){
			this.setDestroyed(true);
		} else if (MainCanvas.animationClock % 5 == 0) this.nextFrame();
	}
	
	public void render(Graphics g){
		if (!this.isDestroyed()){
			super.render(g);
		}
	}
}
