package view;

import java.util.ArrayList;
import java.util.List;

import data.FilmLocation;
import data.FilmLocationMarker;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import de.fhpotsdam.unfolding.utils.MapUtils;
import main.Configuration;
import processing.core.PApplet;

public class SanFranciscoMap {

	private PApplet pApplet;
	private int startDrawX;
	private int startDrawY;
	private int width;
	private int height;

	private UnfoldingMap unfoldingMap;
	private Location sanFrancisco = new Location(37.7881272f, -122.4296427f);
	private int zoom = 12;

	private List<FilmLocationMarker> filmLocationMarkers = new ArrayList<>();
	private List<FilmLocation> filmLocationList;

	private MarkerManager<Marker> filmLocationMarkerManager;

	/**
	 * Constructor
	 * 
	 * @param pApplet
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public SanFranciscoMap(PApplet pApplet, int x, int y, int width, int height, List<FilmLocation> filmLocationList) {

		this.pApplet = pApplet;
		this.startDrawX = x;
		this.startDrawY = y;
		this.width = width;
		this.height = height;
		this.filmLocationList = filmLocationList;
		setup();
	}

	/**
	 * 
	 */
	private void setup() {

		unfoldingMap = new UnfoldingMap(pApplet, startDrawX, startDrawY, width, height);
		unfoldingMap.zoomAndPanTo(zoom, sanFrancisco);
		MapUtils.createDefaultEventDispatcher(pApplet, unfoldingMap);
		filmLocationMarkerManager = unfoldingMap.getDefaultMarkerManager();

		setupDistrictMarker();
		setupFilmLocationMarker(filmLocationList);
	}

	/**
	 * Set the Marker for the border of the district to the map
	 */
	private void setupDistrictMarker() {

		List<Feature> districts = GeoJSONReader.loadData(pApplet, Configuration.districtPath);
		List<Marker> districtMarkers = MapUtils.createSimpleMarkers(districts);
		for (Marker district : districtMarkers) {
			district.setColor(pApplet.color(255, 255, 255, 10));
		}
		unfoldingMap.addMarkers(districtMarkers);
	}

	/**
	 * Set the FilmLocationMarker to the map
	 * 
	 * @param filmLocationList
	 *            the current list of the FilmLocations
	 */
	public void setupFilmLocationMarker(List<FilmLocation> filmLocationList) {

		filmLocationMarkerManager.clearMarkers();
		filmLocationMarkers = new ArrayList<>();

		for (FilmLocation filmLocation : filmLocationList) {
			FilmLocationMarker location = new FilmLocationMarker();
			location.setLocation(new Location(filmLocation.getBreitengrad(), filmLocation.getLaengengrad()));
			location.setFilmLocation(filmLocation);
			location.setDiameter(10);
			location.setColor(pApplet.color(211, 211, 211, 50));
			location.setHighlightColor(pApplet.color(255, 0, 0, 100));
			filmLocationMarkers.add(location);
			filmLocationMarkerManager.addMarker(location);
		}
	}

	/**
	 * Set the TextLabel of the FilmLokationMarker
	 * 
	 * @param filmLocation
	 *            the current FilmLocation
	 * @return the TextLabel as a String
	 */
	@SuppressWarnings("deprecation")
	private String setFilmLocationTextLabel(FilmLocation filmLocation) {

		String result = "";
		result += "Titel: " + filmLocation.getTitle() + "\n";
		result += "Regie: " + filmLocation.getDirector() + "\n";
		result += "Produktion: " + filmLocation.getProductionCompany() + "\n";
		result += "Vertrieb: " + filmLocation.getDistributor() + "\n";
		result += "IMDb Wertung: " + filmLocation.getImdbRanking() + "\n";
		result += "Drehjahr: " + (filmLocation.getReleaseYear().getYear());
		return result;
	}

	/**
	 * 
	 */
	public void draw() {
		unfoldingMap.draw();

		int height = 0;
		for (FilmLocationMarker location : filmLocationMarkers) {
			if (location.isSelected()) {
				// set TextLabel
				if (height != 750) {
					pApplet.text(setFilmLocationTextLabel(location.getFilmLocation()),
							(float) (Configuration.windowWidth * 0.175), 50 + height);
					pApplet.fill(pApplet.color(0, 0, 0, 100));
					height += 150;
				}
			}
		}
	}

	/**
	 * Handle the clicks of the mouse
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
	public void mouseClicked(int mouseX, int mouseY) {

		for (FilmLocationMarker location : filmLocationMarkers)
			if (location.isInside(unfoldingMap, mouseX, mouseY)) {
				location.setSelected(true);
			} else {
				location.setSelected(false);
			}
	}
}
