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

@SuppressWarnings("unused")
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
	private List<Location> locationList = new ArrayList<>();

	private List<FilmLocationMarker> filmLocationMarkers = new ArrayList<>();
	private List<FilmLocation> filmLocationList;
	private Map<String, Integer> filmLocationDistrictMap = new TreeMap<>();

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
		setupMap();

		resetButton = cp5.addButton("Karte zurücksetzen").setPosition((int) (width - 100), (int) (height - 30))
				.setSize(100, 30).setColorForeground(SanFranciscoApplet.buttonColor)
				.setColorActive(SanFranciscoApplet.buttonActivColor);
	}

	/**
	 * Setup the map
	 */
	public void setupMap() {
		MapUtils.createDefaultEventDispatcher(pApplet, unfoldingMap);
		filmLocationMarkerManager = unfoldingMap.getDefaultMarkerManager();
		unfoldingMap.setPanningRestriction(sanFrancisco, 5);
		unfoldingMap.setZoomRange(12, 15);

		setupFilmLocationMarker(filmLocationList);
		setupDistrictName();
		sumFilmLocationInDistrict(filmLocationList);
		setMapPosition();
	}

	/**
	 * Setup the position in the map
	 */
	private void setMapPosition() {

		if (locationList.isEmpty()) {
			for (FilmLocationMarker location : filmLocationMarkers) {
				locationList.add(location.getLocation());
			}
		}
		unfoldingMap.zoomAndPanToFit(locationList);
	}

	/**
	 * Set the Marker for the border of the district to the map
	 */
	private void setupDistrictMarker() {

		for (Marker district : districtMarkers) {
			district.setColor(pApplet.color(0, 0, 0, 10));
			district.setHighlightColor(SanFranciscoApplet.filmLocationMarkerActivColorTransparent);
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
			location.setDiameter(unfoldingMap.getZoomLevel() - 2);
			location.setColor(SanFranciscoApplet.filmLocationMarkerColor);
			location.setStrokeColor(SanFranciscoApplet.filmLocationMarkerColor);
			location.setHighlightColor(SanFranciscoApplet.filmLocationMarkerActivColor);
			filmLocationMarkers.add(location);
			filmLocationMarkerManager.addMarker(location);
		}
		setupDistrictMarker();
	}

	/**
	 * Setup the name of the district
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
	 * Count the FilmLocations in a District
	 */
	public void sumFilmLocationInDistrict(List<FilmLocation> filmLocationList) {

		filmLocationDistrictMap = new TreeMap<>();
		for (FilmLocation location : filmLocationList) {
			String key = "";
			if (location.getDistrict() == null) {
				key = "Kein Distrikt";
			} else {
				key = location.getDistrict();
			}
			if (filmLocationDistrictMap.containsKey(key)) {
				filmLocationDistrictMap.put(key, filmLocationDistrictMap.get(key).intValue() + 1);
			} else {
				filmLocationDistrictMap.put(key, 1);
			}
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
		if (filmLocation.getDistrict() == null) {
			result += "Außerhalb von San Francisco";
		} else {
			result += "Distrikt: " + filmLocation.getDistrict();
		}
		return result;
	}

	/**
	 * Draw the map
	 */
	public void draw(int mouseX, int mouseY) {
		unfoldingMap.draw();

		drawFilmLocationText();
		drawDistrictText(mouseX, mouseY);

		for (FilmLocationMarker location : filmLocationMarkers) {
			location.setDiameter(unfoldingMap.getZoomLevel() - 2);
			if (filmLocationMarkers.size() < 100) {
				location.setColor(SanFranciscoApplet.selectedColor);
			}
		}

	}

	/**
	 * Setup the TextLabel about the FilmLocations
	 */
	private void drawFilmLocationText() {

		boolean toMany = false;
		int labelHight = 0;
		for (FilmLocationMarker location : filmLocationMarkers) {
			if (location.isSelected()) {
				if (labelHight < (int) (Configuration.windowsHeight * 0.60)) {
					pApplet.fill(SanFranciscoApplet.textColor);
					pApplet.text(setFilmLocationTextLabel(location.getFilmLocation()), 25, 25 + labelHight);
					labelHight += 185;
				} else if (!toMany) {
					pApplet.fill(SanFranciscoApplet.textColor);
					pApplet.text("Weitere Drehorte an dieser Position vorhanden", 25, 25 + labelHight);
					labelHight += 25;
					toMany = true;
				}
			}
		}
		pApplet.fill(SanFranciscoApplet.filmLocationMarkerColor);
		pApplet.stroke(SanFranciscoApplet.filmLocationMarkerColor);
		pApplet.rect(5, 5, 325, labelHight);
	}

	/**
	 * Setup the TextLabel about the Districts
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
	private void drawDistrictText(int mouseX, int mouseY) {
		boolean isShow = false;
		for (Marker district : districtMarkers) {
			MultiMarker m = (MultiMarker) district;
			if (m.isInside(unfoldingMap, mouseX, mouseY)) {
				district.setSelected(true);
				pApplet.fill(SanFranciscoApplet.filmLocationMarkerActivColorTransparent);
				// pApplet.rect(width / 2 - 5, 10, 200, 45);
				pApplet.rect(mouseX + 25, mouseY + 25, 200, 45);
				pApplet.fill(SanFranciscoApplet.textColor);
				String text = district.getProperty("supervisor").toString() + " | "
						+ district.getProperty("supname").toString();
				String value = "0";
				if (filmLocationDistrictMap.get(text) != null) {
					value = "" + filmLocationDistrictMap.get(text).intValue();
				}
				// pApplet.text("Distrikt: " + text + "\nAnzahl Drehorte: " + value, width / 2,
				// 25);
				pApplet.text("Distrikt: " + text + "\nAnzahl Drehorte: " + value, mouseX + 25, mouseY + 45);
				isShow = true;
			} else {
				district.setSelected(false);
			}
		}
		if (mouseX < width && mouseY < height && !isShow) {
			pApplet.fill(SanFranciscoApplet.filmLocationMarkerActivColorTransparent);
			// pApplet.rect(width / 2 - 5, 10, 200, 45);
			pApplet.rect(mouseX + 25, mouseY + 25, 200, 45);
			pApplet.fill(SanFranciscoApplet.textColor);
			// pApplet.text("Außerhalb von San Francisco \nAnzahl Drehorte: "
			// + filmLocationDistrictMap.get("Kein Distrikt").intValue(), width / 2, 25);
			pApplet.text("Außerhalb von San Francisco \nAnzahl Drehorte: "
					+ filmLocationDistrictMap.get("Kein Distrikt").intValue(), mouseX + 25, mouseY + 45);
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
			setMapPosition();
		}
		for (FilmLocationMarker location : filmLocationMarkers) {
			if (location.isInside(unfoldingMap, mouseX, mouseY)) {
				location.setSelected(true);
			} else {
				location.setSelected(false);
			}
		}
	}

	/**
	 * 
	 * @param key
	 */
	public void keyPressed(char key) {
		if (key == 'x') {
			unfoldingMap = new UnfoldingMap(pApplet, startDrawX, startDrawY, width, height, Configuration.provider);
			setupMap();
		} else if (key == 'y') {
			unfoldingMap = new UnfoldingMap(pApplet, startDrawX, startDrawY, width, height);
			setupMap();
		}
	}
}
