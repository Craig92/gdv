package data;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;

public class FilmLocationMarker extends SimplePointMarker {

	private FilmLocation filmLocation;

	public FilmLocationMarker() {

	}

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
