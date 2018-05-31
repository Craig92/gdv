package view;

import org.joda.time.DateTime;

import controlP5.ControlP5;
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
	private TimeRangeSlider timeRangeSlider;
	
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
		
		timeRangeSlider = new YearTimeRangeSlider(this.pApplet, startDrawX +100 , startDrawY + 70, (int) (Configuration.windowWidth * 0.50), 16, new DateTime(1920, 01, 01, 01, 0, 0),
				new DateTime(2018, 01, 01, 01, 0, 0), 60*60*24*365);
		timeRangeSlider.setTickIntervalSeconds(60 * 60);
	}

	/**
	 * Draw the different elements
	 */
	public void draw() {

		label.draw(pApplet);
		descriptionLabel.draw(pApplet);
		
		timeRangeSlider.draw();
	}

	public void keyPressed() {
		timeRangeSlider.onKeyPressed(pApplet.key, pApplet.keyCode);
	}

	// Gets called each time the time ranger slider has changed, both by user interaction as well as by animation
	public void timeUpdated(DateTime startDateTime, DateTime endDateTime) {
		System.out.println("timeUpdated to " + startDateTime.toString("YYYY") + " - " + endDateTime.toString("YYYY"));
	}

	public void mouseMoved() {
		timeRangeSlider.onMoved(pApplet.mouseX, pApplet.mouseY);
	}

	public void mouseDragged() {
		timeRangeSlider.onDragged(pApplet.mouseX, pApplet.mouseY, pApplet.pmouseX, pApplet.pmouseY);
	}

}
