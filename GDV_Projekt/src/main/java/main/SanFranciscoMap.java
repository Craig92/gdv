package main;

import java.util.List;

import data.FilmLocation;
import data.FilmLocationManager;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class SanFranciscoMap extends PApplet {

	private static final long serialVersionUID = 1L;

	private UnfoldingMap map;
	private FilmLocationManager manager = FilmLocationManager.getInstance();
	private Location sanFrancisco = new Location(37.7577627f, -122.4726194f);
	private List<FilmLocation> locationList = manager.getList();

	@SuppressWarnings("deprecation")
	public void setup() {

		// size of the windows
		size(Configuration.windowWidth, Configuration.windowsHeight, P2D);

		// set position of the map
		map = new UnfoldingMap(this, Configuration.provider);
		map.zoomAndPanTo(sanFrancisco, 13);
		MapUtils.createDefaultEventDispatcher(this, map);

		// set timeslider
		// TODO

		// set filter
		// TODO

	}

	public void draw() {
		map.draw();

		// add districts to the map
		List<Feature> districts = GeoJSONReader.loadData(this, Configuration.districtPath);
		List<Marker> districtMarkers = MapUtils.createSimpleMarkers(districts);
		for (Marker district : districtMarkers) {
			district.setColor(color(255, 255, 255, 10));
		}
		map.addMarkers(districtMarkers);

		// add locations to the map
		for (FilmLocation filmLocation : locationList) {
			Marker location = new SimplePointMarker();
			location.setLocation(new Location(filmLocation.getBreitengrad(), filmLocation.getLaengengrad()));
			location.setColor(color(0, 0, 0, 0));
			map.addMarker(location);
		}

	}
}
