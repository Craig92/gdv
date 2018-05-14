package main;

import processing.core.PApplet;

import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class SanFranciscoMap extends PApplet {

	private static final long serialVersionUID = 1L;

	private UnfoldingMap map;
	private Location sanFrancisco = new Location(37.7577627f, -122.4726194f);
	private AbstractMapProvider provider = new OpenStreetMap.OpenStreetMapProvider();
	private String districtPath = "C:\\Users\\Thorsten\\Git\\GDV\\Projekt\\GDV_Projekt\\src\\main\\resources\\districts.geo.json";

	@SuppressWarnings("deprecation")
	public void setup() {

		// size of the windows
		size(1920, 1080, P2D);

		map = new UnfoldingMap(this, provider);
		map.zoomAndPanTo(sanFrancisco, 12);
		MapUtils.createDefaultEventDispatcher(this, map);

	}

	public void draw() {
		map.draw();
		Location mouseLocation = map.getLocation(mouseX, mouseY);
		fill(0);
		text("Aktuelle Position: " + mouseLocation.getLat() + ", " + mouseLocation.getLon(), mouseX, mouseY);

		List<Feature> districts = GeoJSONReader.loadData(this, districtPath);
		List<Marker> districtMarker = MapUtils.createSimpleMarkers(districts);
		map.addMarkers(districtMarker);

		// TODO drehorte

	}
}
