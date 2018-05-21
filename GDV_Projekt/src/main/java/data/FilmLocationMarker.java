package data;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;

public class FilmLocationMarker extends SimplePointMarker {

	private FilmLocation filmLocation;

	/**
	 * Default Constructor
	 */
	public FilmLocationMarker() {

	}

	/**
	 * Constructor
	 * 
	 * @param location
	 *            the location of the marker
	 * @param filmLocation
	 *            the information about the film location
	 */
	public FilmLocationMarker(Location location, FilmLocation filmLocation) {
		super(location);
		this.setFilmLocation(filmLocation);
	}

	public FilmLocation getFilmLocation() {
		return filmLocation;
	}

	public void setFilmLocation(FilmLocation filmLocation) {
		this.filmLocation = filmLocation;
	}

}
