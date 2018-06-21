package main;

import java.io.File;

import de.fhpotsdam.unfolding.providers.*;

public class Configuration {

	private static String path = File.separator + "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator;
	public static AbstractMapProvider provider = new Microsoft.RoadProvider();
	public static String districtPath = new File("").getAbsolutePath() + path + "districts.geo.json";
	public static String locationPath = new File("").getAbsolutePath() + path + "filmlocation.csv";
	public static int windowWidth = 1920;
	public static int windowsHeight = 1080;
	public static int limit = windowsHeight / 100;

	public static int yearDiagrammSize = 12;
	public static int imdbDiagrammSize = 7;
	public static boolean iExpo = true;
}
