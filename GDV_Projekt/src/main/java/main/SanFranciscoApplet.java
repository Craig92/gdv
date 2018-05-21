package main;

import controlP5.ControlEvent;
import processing.core.PApplet;
import processing.core.PGraphics;
import view.Filter;
import view.SanFranciscoMap;

public class SanFranciscoApplet extends PApplet {

	private SanFranciscoMap map;
	private Filter filter;

	private PGraphics mapGraphic;
	private PGraphics imbdRankingSliderGraphic;
	private PGraphics timeSliderGraphic;
	private PGraphics filterGraphic;

	public static void main(String[] args) {
		PApplet.main(new String[] { SanFranciscoApplet.class.getName() });
	}

	public void settings() {

		// size of the windows
		size(Configuration.windowWidth, Configuration.windowsHeight, P2D);

	}

	/**
	 * Legt die Groeße der Bereiche fest
	 */
	public void setup() {

		surface.setResizable(true);

		// set map
		mapGraphic = createGraphics((int) (Configuration.windowWidth * 0.68),
				(int) (Configuration.windowsHeight * 0.78), P2D);

		// set timeslider
		timeSliderGraphic = createGraphics((int) (Configuration.windowWidth * 0.68),
				(int) (Configuration.windowsHeight * 0.2), P2D);

		// set imdbslider
		imbdRankingSliderGraphic = createGraphics((int) (Configuration.windowWidth * 0.15), Configuration.windowsHeight,
				P2D);

		// set filter
		filterGraphic = createGraphics((int) (Configuration.windowWidth * 0.85), Configuration.windowsHeight, P2D);

		// set map
		map = new SanFranciscoMap(this, (int) (Configuration.windowWidth * 0.16), 0,
				(int) (Configuration.windowWidth * 0.68), (int) (Configuration.windowsHeight * 0.78));

		filter = new Filter(this, (int) (Configuration.windowWidth * 0.85), 0, (int) (Configuration.windowWidth * 0.15),
				Configuration.windowsHeight);
	}

	/**
	 * Zeichnet die verschiedenen Bereiche an die entsprechenden Positionen
	 */
	public void draw() {

		background(color(255, 255, 255, 100));
		imbdRankingSliderGraphic.beginDraw();
		imbdRankingSliderGraphic.background(color(255, 255, 255, 100));
		imbdRankingSliderGraphic.endDraw();
		image(imbdRankingSliderGraphic, 0, 0);

		filterGraphic.beginDraw();
		filterGraphic.background(color(255, 255, 255, 100));
		filterGraphic.endDraw();
		image(filterGraphic, (int) (Configuration.windowWidth * 0.85), 0);

		timeSliderGraphic.beginDraw();
		timeSliderGraphic.background(color(255, 255, 255, 100));
		timeSliderGraphic.endDraw();
		image(timeSliderGraphic, (int) (Configuration.windowWidth * 0.16), (int) (Configuration.windowsHeight * 0.8));

		mapGraphic.beginDraw();
		mapGraphic.background(color(255, 255, 255, 100));
		mapGraphic.endDraw();
		image(mapGraphic, (int) (Configuration.windowWidth * 0.16), 0);

		filter.draw();
		map.draw();

	}

	/**
	 * Verarbeitet Maus-Klicks
	 */
	public void mouseClicked() {

		if (mouseX < (Configuration.windowWidth * 0.15)) {
			System.out.println("IMDB");
		} else if (mouseX > (Configuration.windowWidth * 0.15) && mouseX < (Configuration.windowWidth * 0.85)
				&& mouseY > (Configuration.windowsHeight * 0.8)) {
			System.out.println("Zeitleiste");
		} else if (mouseX > (Configuration.windowWidth * 0.15) && mouseX < (Configuration.windowWidth * 0.85)
				&& mouseY < (Configuration.windowsHeight * 0.8)) {
			System.out.println("Karte");
			map.mouseClicked(mouseX, mouseY);
		} else {
			System.out.println("Filter");
			filter.mouseClicked(mouseX, mouseY);
		}
	}

	/**
	 * Verarbeitet Events
	 * 
	 * @param event
	 */
	public void controlEvent(ControlEvent event) {
		filter.controlEvent(event);
	}

}
