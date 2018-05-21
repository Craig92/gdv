package view;

import java.util.ArrayList;
import java.util.List;

import data.FilmLocation;
import data.FilmLocationManager;
import data.FilmLocationMarker;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import main.Configuration;
import processing.core.PApplet;

public class SanFranciscoMap {

	private PApplet pApplet;
	private int startDrawX;
	private int startDrawY;
	private int width;
	private int height;

	private FilmLocationManager manager = FilmLocationManager.getInstance();
	private List<FilmLocation> locationList = manager.getFilmLocationList();

	private UnfoldingMap unfoldingMap;
	private Location sanFrancisco = new Location(37.7577627f, -122.4726194f);
	private int zoom = 12;

	private List<FilmLocationMarker> filmLocationMarkers = new ArrayList<>();

	/**
	 * 
	 * @param pApplet
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public SanFranciscoMap(PApplet pApplet, int x, int y, int width, int height) {
		this.pApplet = pApplet;
		this.startDrawX = x;
		this.startDrawY = y;
		this.width = width;
		this.height = height;
		setup();
	}

	/**
	 * 
	 */
	private void setup() {

		unfoldingMap = new UnfoldingMap(pApplet, startDrawX, startDrawY, width, height);
		unfoldingMap.zoomAndPanTo(zoom, sanFrancisco);

		// add districts to the map
		List<Feature> districts = GeoJSONReader.loadData(pApplet, Configuration.districtPath);
		List<Marker> districtMarkers = MapUtils.createSimpleMarkers(districts);
		for (Marker district : districtMarkers) {
			district.setColor(pApplet.color(255, 255, 255, 10));
		}
		unfoldingMap.addMarkers(districtMarkers);

		// add locations to the map
		for (FilmLocation filmLocation : locationList) {
			FilmLocationMarker location = new FilmLocationMarker();
			location.setLocation(new Location(filmLocation.getBreitengrad(), filmLocation.getLaengengrad()));
			location.setFilmLocation(filmLocation);
			location.setDiameter(10);
			filmLocationMarkers.add(location);
			unfoldingMap.addMarker(location);
		}
	}

	public void draw() {
		unfoldingMap.draw();

		for (FilmLocationMarker location : filmLocationMarkers) {
			if (location.isSelected()) {
				location.setHighlightColor(pApplet.color(255, 0, 0, 100));
				// set TextLabel
				pApplet.text(getLabel(location.getFilmLocation()), Configuration.windowWidth / 2,
						(float) (Configuration.windowsHeight * 0.65));
				pApplet.fill(pApplet.color(0, 0, 0, 100));
			} else {
				location.setHighlightColor(pApplet.color(211, 211, 211, 50));
			}
		}
	}

	/**
	 * 
	 * @param filmLocation
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String getLabel(FilmLocation filmLocation) {

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
	 * @param districtMarkers
	 */
	public void addMarkers(List<Marker> districtMarkers) {
		unfoldingMap.addMarkers(districtMarkers);
	}

	/**
	 * 
	 * @param location
	 */
	public void addMarker(FilmLocationMarker location) {
		unfoldingMap.addMarker(location);
	}

	/**
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
