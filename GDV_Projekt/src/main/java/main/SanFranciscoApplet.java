package main;

import java.util.List;

import controlP5.ControlEvent;
import data.FilmLocation;
import data.FilmLocationManager;
import processing.core.PApplet;
import processing.core.PGraphics;
import view.Filter;
import view.IMDBSlider;
import view.SanFranciscoMap;
import view.YearSlider;

public class SanFranciscoApplet extends PApplet {

	private SanFranciscoMap map;
	private Filter filter;
	private IMDBSlider imdbSlider;
	private YearSlider yearSlider;

	private PGraphics mapGraphic;
	private PGraphics imbdRankingSliderGraphic;
	private PGraphics timeSliderGraphic;
	private PGraphics filterGraphic;
	private float typ;

	private static final FilmLocationManager manager = FilmLocationManager.getInstance();
	private List<FilmLocation> filmLocationList = manager.getFilmLocationList();

	public static void main(String[] args) {
		PApplet.main(new String[] { SanFranciscoApplet.class.getName() });
	}

	/**
	 * Set the windows size
	 */
	public void settings() {

		size(Configuration.windowWidth, Configuration.windowsHeight, P2D);

	}

	/**
	 * Set the size and position of the different elements
	 */
	public void setup() {

		surface.setResizable(true);
		surface.setTitle("GDV-Projekt WestSideMovie");

		// set map
		mapGraphic = createGraphics((int) (Configuration.windowWidth * 0.68),
				(int) (Configuration.windowsHeight * 0.78), P2D);
		map = new SanFranciscoMap(this, (int) (Configuration.windowWidth * 0.16), 0,
				(int) (Configuration.windowWidth * 0.68), (int) (Configuration.windowsHeight * 0.78), filmLocationList);

		// set timeslider
		timeSliderGraphic = createGraphics((int) (Configuration.windowWidth * 0.68),
				(int) (Configuration.windowsHeight * 0.2), P2D);
		yearSlider = new YearSlider(this, (int) (Configuration.windowWidth * 0.16),
				(int) (Configuration.windowsHeight * 0.80), (int) (Configuration.windowWidth * 0.68),
				(int) (Configuration.windowsHeight * 0.78));

		// set imdbslider
		imbdRankingSliderGraphic = createGraphics((int) (Configuration.windowWidth * 0.15), Configuration.windowsHeight,
				P2D);
		imdbSlider = new IMDBSlider(this, 0, 0, (int) (Configuration.windowWidth * 0.15), Configuration.windowsHeight);

		// set filter
		filterGraphic = createGraphics((int) (Configuration.windowWidth * 0.85), Configuration.windowsHeight, P2D);
		filter = new Filter(this, (int) (Configuration.windowWidth * 0.85), 0, (int) (Configuration.windowWidth * 0.15),
				Configuration.windowsHeight);
	}

	/**
	 * Draw the different elements
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

		yearSlider.draw();
		imdbSlider.draw();
		filter.draw();
		map.draw();

	}

	/**
	 * Handle the clicks of the mouse in the different areas
	 */
	public void mouseClicked() {

		if (mouseX < (Configuration.windowWidth * 0.15)) {
			imdbSlider.mouseClicked(mouseX, mouseY);
		} else if (mouseX > (Configuration.windowWidth * 0.15) && mouseX < (Configuration.windowWidth * 0.85)
				&& mouseY > (Configuration.windowsHeight * 0.8)) {
			yearSlider.mouseClicked(mouseX, mouseY);
		} else if (mouseX > (Configuration.windowWidth * 0.15) && mouseX < (Configuration.windowWidth * 0.85)
				&& mouseY < (Configuration.windowsHeight * 0.8)) {
			map.mouseClicked(mouseX, mouseY);
		} else if (mouseX > (Configuration.windowWidth * 0.85)) {
			filter.mouseClicked(mouseX, mouseY);
		}
	}

	/**
	 * Handle the events in the different areas
	 * 
	 * @param event
	 *            the current event
	 */
	public void controlEvent(ControlEvent event) {

		if (event.getName().equals("Filtern")) {
			filmLocationList = manager.filterByTitle(filmLocationList, filter.getSelectedParameterList("Titel"));
			filmLocationList = manager.filterByDirector(filmLocationList, filter.getSelectedParameterList("Regie"));
			filmLocationList = manager.filterByProductionCompany(filmLocationList,
					filter.getSelectedParameterList("Produktion"));
			filmLocationList = manager.filterByDistributor(filmLocationList,
					filter.getSelectedParameterList("Vertrieb"));
			map.setupFilmLocationMarker(filmLocationList);
			filmLocationList = manager.getFilmLocationList();
		} else if (event.getName().equals("Zurücksetzen")) {
			filter.setRadioButtonActive(false, 5f);
			filmLocationList = manager.getFilmLocationList();
			map.setupFilmLocationMarker(filmLocationList);
		} else if (event.getName().equals("Filtern nach...")) {
			this.typ = filter.setVisibility(event.getController().getValue());
		} else if (event.getName().equals("Alle auswählen")) {
			filter.setRadioButtonActive(true, this.typ);
		} else if (event.getName().equals("Alle abwählen")) {
			filter.setRadioButtonActive(false, this.typ);
		}

	}

}
