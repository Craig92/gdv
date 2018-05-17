package main;

import java.io.File;

import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;

public class Configuration {

	public static AbstractMapProvider provider = new Microsoft.RoadProvider();
	public static String districtPath = new File("").getAbsolutePath()+"/../../src/main/resources/districts.geo.json";
	public static String locationPath = new File("").getAbsolutePath()+"/../../src/main/resources/filmlocation.csv";
	public static int windowWidth = 1920;
	public static int windowsHeight = 1080;
}
