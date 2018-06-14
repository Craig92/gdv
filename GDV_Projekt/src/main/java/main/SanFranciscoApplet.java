package main;

import java.util.ArrayList;
import java.util.List;

import data.FilmLocation;
import data.FilmLocationManager;
import processing.core.PApplet;
import processing.core.PFont;
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

	public static PFont headerFont;
	public static PFont textFont;
	public static int textColor;
	public static int backgroundColor;
	public static int selectedColor;
	public static int unselectedColor;
	public static int buttonColor;
	public static int buttonActivColor;
	public static int filmLocationMarkerColor;
	public static int filmLocationMarkerActivColor;
	public static int filmLocationMarkerActivColorTransparent;
	public static int districtMarkerColor;

	private static final FilmLocationManager manager = FilmLocationManager.getInstance();
	private List<FilmLocation> filmLocationList = manager.getFilmLocationList();

	/**
	 * 
	 * @param args
	 */
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
		surface.setTitle("GDV-Projekt WestSideMovie | Hochschule Mannheim Sommersemester 2018");
		headerFont = createFont("Georgia", 20);
		textFont = createFont("Georgia", 14);
		textColor = color(0, 0, 0);
		backgroundColor = color(255, 255, 255);
		selectedColor = color(25, 118, 210);
		unselectedColor = color(144, 202, 249);
		buttonColor = color(13, 71, 161);
		buttonActivColor = color(13, 71, 161);
		filmLocationMarkerColor = color(25, 118, 210, 33);
		filmLocationMarkerActivColor = color(255, 235, 59);
		filmLocationMarkerActivColorTransparent = color(255, 235, 59, 40);
		districtMarkerColor = color(227, 242, 253);

		// set map
		mapGraphic = createGraphics((int) (Configuration.windowWidth * 0.68),
				(int) (Configuration.windowsHeight * 0.78), P2D);
		map = new SanFranciscoMap(this, 0, 0, (int) (Configuration.windowWidth * 0.68),
				(int) (Configuration.windowsHeight * 0.78), filmLocationList);

		// set timeslider
		timeSliderGraphic = createGraphics((int) (Configuration.windowWidth * 0.68),
				(int) (Configuration.windowsHeight * 0.2), P2D);
		yearSlider = new YearSlider(this, 0, (int) (Configuration.windowsHeight * 0.80),
				(int) (Configuration.windowWidth * 0.68), (int) (Configuration.windowsHeight * 0.78));

		// set filter
		filterGraphic = createGraphics((int) (Configuration.windowWidth * 0.15), Configuration.windowsHeight, P2D);
		filter = new Filter(this, (int) (Configuration.windowWidth * 0.70), 0, (int) (Configuration.windowWidth * 0.15),
				Configuration.windowsHeight);

		// set imdbslider
		imbdRankingSliderGraphic = createGraphics((int) (Configuration.windowWidth * 0.15), Configuration.windowsHeight,
				P2D);
		imdbSlider = new IMDBSlider(this, (int) (Configuration.windowWidth * 0.85), 0,
				(int) (Configuration.windowWidth * 0.15), Configuration.windowsHeight);
	}

	/**
	 * Draw the different elements
	 */
	public void draw() {

		background(backgroundColor);
		imbdRankingSliderGraphic.beginDraw();
		imbdRankingSliderGraphic.background(backgroundColor);
		imbdRankingSliderGraphic.endDraw();
		image(imbdRankingSliderGraphic, (int) (Configuration.windowWidth * 0.85), 0);

		filterGraphic.beginDraw();
		filterGraphic.background(backgroundColor);
		filterGraphic.endDraw();
		image(filterGraphic, (int) (Configuration.windowWidth * 0.70), 0);

		timeSliderGraphic.beginDraw();
		timeSliderGraphic.background(backgroundColor);
		timeSliderGraphic.endDraw();
		image(timeSliderGraphic, 0, (int) (Configuration.windowsHeight * 0.8));

		mapGraphic.beginDraw();
		mapGraphic.background(backgroundColor);
		mapGraphic.endDraw();
		image(mapGraphic, 0, 0);

		yearSlider.draw();

		if (Configuration.windowWidth > 1024) {
			imdbSlider.draw();
		}
		filter.draw();
		map.draw(mouseX, mouseY);
	}

	/**
	 * Handle the dragged of the mouse in the different areas
	 */
	public void mouseDragged() {
		if (yearSlider.isOnSlider(pmouseX, pmouseY)
				&& yearSlider.startOrEndDateChanged(yearSlider.getStartDate(), yearSlider.getEndDate())) {
			yearSlider.changeFloatLabelToIntLabel();
			yearSlider.updateDiagramm();
			filter();
		} else if (imdbSlider.isOnLowHandle(pmouseX, pmouseY)) {
			imdbSlider.changeLowRectanglePostion(mouseY);
			imdbSlider.updateDiagramm();
			filter();
		} else if (imdbSlider.isOnHighHandle(pmouseX, pmouseY)) {
			imdbSlider.changeHighRectanglePostion(mouseY);
			imdbSlider.updateDiagramm();
			filter();
		} else if (imdbSlider.isOnRangeHandle(pmouseX, pmouseY)) {
			imdbSlider.changeRectanglesPostion(pmouseY, mouseY);
			imdbSlider.updateDiagramm();
			filter();
		}
	}

	/**
	 * Handle the clicks of the mouse in the different areas
	 */
	public void mouseClicked() {

		if (mouseX > 0 && mouseX < (Configuration.windowWidth * 0.70) && mouseY < (Configuration.windowsHeight * 0.8)) {
			map.mouseClicked(mouseX, mouseY);
		} else if (mouseX > (Configuration.windowWidth * 0.70) && mouseX < (Configuration.windowWidth * 0.85)) {
			filter.mouseClicked(mouseX, mouseY);
			if (filter.getSelectedParameterList("Titel").isEmpty() && filter.getSelectedParameterList("Regie").isEmpty()
					&& filter.getSelectedParameterList("Produktion").isEmpty()
					&& filter.getSelectedParameterList("Vertrieb").isEmpty()
					&& filter.getSelectedParameterList("Genre").isEmpty()) {
				filmLocationList = new ArrayList<>();
				map.setupFilmLocationMarker(filmLocationList);
				filmLocationList = manager.getFilmLocationList();

			} else {
				filter();
			}
		} else if (mouseX > (Configuration.windowWidth * 0.85)) {
			filter();
		}
	}

	/**
	 * Filter the map with the different filters
	 */
	private void filter() {

		filmLocationList = manager.filterByYear(filmLocationList, yearSlider.getStartDate(), yearSlider.getEndDate());
		filmLocationList = manager.filterByIMDBRanking(filmLocationList, imdbSlider.getStartValue(),
				imdbSlider.getEndValue());
		filmLocationList = manager.filterByDirector(filmLocationList, filter.getSelectedParameterList("Regie"));
		filmLocationList = manager.filterByProductionCompany(filmLocationList,
				filter.getSelectedParameterList("Produktion"));
		filmLocationList = manager.filterByDistributor(filmLocationList, filter.getSelectedParameterList("Vertrieb"));
		filmLocationList = manager.filterByGenre(filmLocationList, filter.getSelectedParameterList("Genre"));
		map.setupFilmLocationMarker(filmLocationList);
		map.sumFilmLocationInDistrict(filmLocationList);
		filmLocationList = manager.getFilmLocationList();

	}
}
