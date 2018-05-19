package view;

import processing.core.PApplet;

public class IMDBSlider {
	
	private PApplet pApplet;
	private int startDrawX;
	private int startDrawY;
	private int width;
	private int height;
		
	public IMDBSlider(PApplet pApplet, int x, int y, int width, int height) {
		this.pApplet = pApplet;
		this.startDrawX = x;
		this.startDrawY = y;
		this.width = width;
		this.height = height;
		setup();
	}

	private void setup() {
		// TODO Auto-generated method stub
		
	}
	
}
