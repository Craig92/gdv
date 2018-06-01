package view;

import org.joda.time.DateTime;

import controlP5.CColor;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Range;
import controlP5.Textlabel;
import main.Configuration;
import processing.core.PApplet;

@SuppressWarnings("unused")
public class YearSlider {

	private PApplet pApplet;

	private int startDrawX;
	private int startDrawY;
	private int width;
	private int height;

	private ControlP5 cp5;
	private Textlabel label;
	private Textlabel descriptionLabel;
	private Range range;
	
	public YearSlider(PApplet pApplet, int x, int y, int width, int height) {
		this.pApplet = pApplet;
		this.startDrawX = x;
		this.startDrawY = y;
		this.width = width;
		this.height = height;
		setup();
	}

	/**
	 * Set the size and position of the different elements
	 */
	private void setup() {

		cp5 = new ControlP5(pApplet);

		label = new Textlabel(cp5, "Zeitachse", startDrawX, startDrawY, 400, 200)
				.setFont(pApplet.createFont("Georgia", 20)).setColor(pApplet.color(0, 0, 0, 0));

		descriptionLabel = new Textlabel(cp5, "Wählen Sie den zu filternden Zeitraum aus:", startDrawX, startDrawY + 35,
				400, 200).setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		 range = cp5.addRange("rangeController")
	             // disable broadcasting since setRange and setRangeValues will trigger an event
	             .setBroadcast(false)
	             .setPosition(startDrawX +40 ,startDrawY + 120)
	             .setSize((int) (Configuration.windowWidth * 0.50), 30)
	             .setHandleSize(15)
	             .setRange(1920,2018)
	             .setRangeValues(1950,1980)
	             .setColorValueLabel(100)
	             .setColorForeground(pApplet.color(150,40))
	             .setColorBackground(pApplet.color(210,40))
	             //distance between ticks to small
	             //.showTickMarks(true)
	             //.setNumberOfTickMarks(98)
	             //.setColorTickMark(150)
	             // after the initialization we turn broadcast back on again
	             .setBroadcast(true);
		 changeFloatLabelToIntLabel();
	}

	/**
	 * Draw the different elements
	 */
	public void draw() {

		label.draw(pApplet);
		descriptionLabel.draw(pApplet);
		pApplet.fill(255);
		pApplet.rect(0,0,width,height/2);
		//timeRangeSlider.draw();
		changeFloatLabelToIntLabel();
	}
	
	public int getStartDate() {
		return (int) range.getLowValue();
	}
	public int getEndDate() {
		return (int) range.getHighValue();
	}
	
	private void changeFloatLabelToIntLabel() {
		int v = (int)range.getLowValue();
		range.setLowValueLabel(""+v);
		v = (int) range.getHighValue();
		range.setHighValueLabel(""+v);
	}

	public boolean isOnSlider(int mouseX, int mouseY) {
		return (mouseX >= startDrawX +40 && mouseY >= startDrawY + 120 && 
				mouseX <= Configuration.windowWidth * 0.50 && mouseY <=startDrawY + 120+ 30);
	}
}
