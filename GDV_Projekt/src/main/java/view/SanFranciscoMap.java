package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import controlP5.Button;
import controlP5.ControlP5;
import data.FilmLocation;
import data.FilmLocationMarker;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.marker.SimplePolygonMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import main.Configuration;
import main.SanFranciscoApplet;
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

	private List<Feature> districts;
	private List<Marker> districtMarkers;

	private List<FilmLocationMarker> filmLocationMarkers = new ArrayList<>();
	private List<FilmLocation> filmLocationList;

	private Button resetButton;

	private MarkerManager<Marker> filmLocationMarkerManager;
	private ControlP5 cp5;

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
		districts = GeoJSONReader.loadData(pApplet, Configuration.districtPath);
		districtMarkers = MapUtils.createSimpleMarkers(districts);
		setup();
	}

	/**
	 * 
	 */
	private void setup() {

		cp5 = new ControlP5(pApplet);

		unfoldingMap = new UnfoldingMap(pApplet, startDrawX, startDrawY, width, height);
		unfoldingMap.zoomAndPanTo(zoom, sanFrancisco);
		MapUtils.createDefaultEventDispatcher(pApplet, unfoldingMap);
		filmLocationMarkerManager = unfoldingMap.getDefaultMarkerManager();
		unfoldingMap.setPanningRestriction(sanFrancisco, 5);
		unfoldingMap.setZoomRange(12, 15);

		setupFilmLocationMarker(filmLocationList);
		setupDistrictName();
		sumFilmLocationInDistrict();

		resetButton = cp5.addButton("Karte zurücksetzen").setPosition((int) (width - 100), (int) (height - 30))
				.setSize(100, 30).setColorForeground(SanFranciscoApplet.buttonColor)
				.setColorActive(SanFranciscoApplet.buttonActivColor);
	}

	/**
	 * Set the Marker for the border of the district to the map
	 */
	private void setupDistrictMarker() {

		for (Marker district : districtMarkers) {
			district.setColor(pApplet.color(0, 0, 0, 10));
			// district.setHighlightColor(pApplet.color(0, 0, 0, 10));
			district.setStrokeColor(SanFranciscoApplet.textColor);
			district.setStrokeWeight(1);
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
			location.setDiameter(zoom - 2);
			location.setColor(SanFranciscoApplet.filmLocationMarkerColor);
			location.setStrokeColor(SanFranciscoApplet.filmLocationMarkerColor);
			location.setHighlightColor(SanFranciscoApplet.filmLocationMarkerActivColor);
			filmLocationMarkers.add(location);
			filmLocationMarkerManager.addMarker(location);
		}
		setupDistrictMarker();
	}
	
	/**
	 * 
	 */
	public void setupDistrictName() {
		int i = 0;
		for (FilmLocationMarker location : filmLocationMarkers) {
			for (Marker district : districtMarkers) {
				MultiMarker m = (MultiMarker) district;
				SimplePolygonMarker p = (SimplePolygonMarker) m.getMarkers().get(0);
				if (p.isInsideByLocation(location.getLocation())) {
					location.getFilmLocation().setDistrict(district.getProperty("supervisor").toString() + " | "
							+ district.getProperty("supname").toString());
					this.filmLocationList.set(i, location.getFilmLocation());
				}
			}
			i++;
		}
	}

	/**
	 * 
	 */
	public void sumFilmLocationInDistrict() {

		Map<String, Integer> map = new TreeMap<>();
		for (FilmLocation location : filmLocationList) {
			String key = "";
			if (location.getDistrict() == null) {
				key = "Kein District";
			} else {
				key = location.getDistrict();
			}
			if (map.containsKey(key)) {
				map.put(key, map.get(key).intValue() + 1);
			} else {
				map.put(key, 1);
			}
		}

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " | " + entry.getValue());
		}
	}

	/**
	 * Set the TextLabel of the FilmLokationMarker
	 * 
	 * @param filmLocation
	 *            the current FilmLocation
	 * @return the TextLabel as a String
	 */
	private String setFilmLocationTextLabel(FilmLocation filmLocation) {

		String result = "";
		result += "Titel: " + filmLocation.getTitle() + "\n";
		result += "Regie: " + filmLocation.getDirector() + "\n";
		result += "Produktion: " + filmLocation.getProductionCompany() + "\n";
		result += "Vertrieb: " + filmLocation.getDistributor() + "\n";
		result += "IMDb Wertung: " + filmLocation.getImdbRanking() + "\n";
		result += "Genre: " + filmLocation.getGenre() + "\n";
		result += "Drehjahr: " + filmLocation.getReleaseYear() + "\n";
		result += "Distrikt: " + filmLocation.getDistrict();
		return result;
	}

	/**
	 * 
	 */
	public void draw() {
		unfoldingMap.draw();

		boolean toMany = false;
		int labelHight = 0;
		for (FilmLocationMarker location : filmLocationMarkers) {
			if (location.isSelected()) {
				// set TextLabel
				if (labelHight < (int) (Configuration.windowsHeight * 0.60)) {
					pApplet.text(setFilmLocationTextLabel(location.getFilmLocation()), 25, 25 + labelHight);
					pApplet.fill(SanFranciscoApplet.textColor);
					labelHight += 185;
				} else if (!toMany) {
					pApplet.text("Weitere Drehorte an dieser Position vorhanden", 25, 25 + labelHight);
					pApplet.fill(SanFranciscoApplet.textColor);
					toMany = true;
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

		if (mouseX >= resetButton.getPosition()[0] - 100 && mouseX <= resetButton.getPosition()[0] + 100
				&& mouseY >= resetButton.getPosition()[1] - 30 && mouseY <= resetButton.getPosition()[1] + 30) {
			unfoldingMap.zoomAndPanTo(zoom, sanFrancisco);
		}
		for (FilmLocationMarker location : filmLocationMarkers) {
			if (location.isInside(unfoldingMap, mouseX, mouseY)) {
				location.setSelected(true);
			} else {
				location.setSelected(false);
			}
		}
		// for (Marker district : districtMarkers) {
		// if (district.isInside(unfoldingMap, mouseX, mouseY)) {
		// district.setSelected(true);
		// } else {
		// district.setSelected(false);
		// }
		// }
	}
}
