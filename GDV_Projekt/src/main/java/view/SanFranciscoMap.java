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
	private List<FilmLocation> locationList = manager.getList();

	private UnfoldingMap unfoldingMap;
	private Location sanFrancisco = new Location(37.7577627f, -122.4726194f);
	private int zoom = 12;

	private List<FilmLocationMarker> filmLocationMarkers = new ArrayList<>();

	public SanFranciscoMap(PApplet pApplet, int x, int y, int width, int height) {
		this.pApplet = pApplet;
		this.startDrawX = x;
		this.startDrawY = y;
		this.width = width;
		this.height = height;
		setup();
	}

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
			location.setHighlightColor(pApplet.color(211, 211, 211, 50));
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
				// TODO Label einblenden
			} else {
				location.setHighlightColor(pApplet.color(211, 211, 211, 50));
			}
		}
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

	public void mouseClicked(int mouseX, int mouseY) {

		for (FilmLocationMarker location : filmLocationMarkers)
			if (location.isInside(unfoldingMap, mouseX, mouseY)) {
				System.out.println(location.getFilmLocation().getTitle());
				location.setSelected(true);
			} else {
				location.setSelected(false);
			}

	}
}
