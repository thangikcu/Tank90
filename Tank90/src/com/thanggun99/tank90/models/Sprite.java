package com.thanggun99.tank90.models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sprite {
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	private BufferedImage currentImage;
	private BufferedImage fullImage;
	protected int x = 0;
	protected int y = 0;
	protected int frameWidth;
	protected int frameHeight;
	private int frameColNum;
	protected int currentFrame;
	protected int currentIndex;
	protected int currentDirection;
	protected int[] frameStrip;
	protected int rectangleBoundX;
	protected int rectangleBoundY;
	protected int rectangleBoundHeight;
	protected int rectangleBoundWidth;
	public boolean isDestroyed = false;
	
	public void setFrame(int frameNumber) {
		int offsetX = frameNumber%frameColNum;
		int offsetY = frameNumber/frameColNum;
		this.currentFrame = frameNumber;
		this.currentImage = fullImage.getSubimage(offsetX*frameWidth, offsetY*frameHeight, frameWidth, frameHeight);
	}
	
	public Sprite(BufferedImage image, int frameHeight, int frameWidth) {
		this.fullImage = image;
		this.frameColNum =  fullImage.getWidth()/frameWidth;
		this.frameHeight = frameHeight;
		this.frameWidth = frameWidth;
		this.currentIndex = 0;
		this.setFrame(0);
	}


	public void render(Graphics g) {
		this.setFrame(this.currentFrame);
		g.drawImage(this.currentImage, x, y, frameWidth, frameHeight, null);
	}
	public void drawBoss(Graphics g){
		g.drawImage(this.currentImage, x, y, frameWidth, frameHeight, null);
	}

	public void nextFrame(){
		this.currentIndex++;
		if (this.currentIndex == this.frameStrip.length) this.currentIndex = 0;
		this.currentFrame = this.frameStrip[currentIndex];
		this.setFrame(this.currentFrame);
	}
	
	public int getCurrentFrame(){
		return this.currentFrame;
	}
	
	public void setPositionAndBound(int x, int y){
		this.x = x;
		this.y = y;
		this.setBound(0, 0, frameWidth, frameHeight);
	}
	
	protected synchronized void setCurrentDirection(int direct) {
		currentDirection = direct;
		
	}
	
	protected int getCurrentDirection(){
		return currentDirection;
	}
	
	protected void setFrameStrip(int[] frames){
		this.frameStrip = frames;
	}
	
	protected synchronized void setBound(int dx, int dy, int width, int height){
		/*
		 *dx, dy = distance from x, y
		 */
		this.rectangleBoundX = this.x + dx;
		this.rectangleBoundY = this.y + dy;
		this.rectangleBoundWidth = width;
		this.rectangleBoundHeight = height;
	}
	
	public int getBoundX(){
		return this.rectangleBoundX;
	}
	
	public int getBoundY(){
		return this.rectangleBoundY;
	}
	
	public int getBoundHeight(){
		return this.rectangleBoundHeight;
	}
	
	public int getBoundWidth(){
		return this.rectangleBoundWidth;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getHeight(){
		return this.frameHeight;
	}
	
	public int getWidth(){
		return this.frameWidth;
	}

	
	public void drawBound(Graphics g){
		g.setColor(Color.BLACK);
		g.drawRect(this.getBoundX(), this.getBoundY(), this.getBoundWidth(), this.getBoundHeight());
	}
	
	public void setDestroyed(boolean value){
		this.isDestroyed = value;
	}
	public boolean isDestroyed(){
		return this.isDestroyed;
	}
}
