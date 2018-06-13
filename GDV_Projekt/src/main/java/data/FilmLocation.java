package data;

public class FilmLocation {

	private String title;
	private int releaseYear;
	private String location;
	private String funFacts;
	private String productionCompany;
	private String distributor;
	private String director;
	private String writer;
	private String actor1;
	private String actor2;
	private String actor3;
	private double breitengrad;
	private double laengengrad;
	private double imdbRanking;
	private String genre;
	private String district;

	/**
	 * Default Constructor
	 */
	public FilmLocation() {

	}

	/**
	 * Constructor
	 * 
	 * @param title
	 *            the name of the movie
	 * @param releaseYear
	 *            the release year of the movie
	 * @param funFacts
	 *            fun facts abaout the movie
	 * @param productionCompany
	 *            the production company of the movie
	 * @param distributor
	 *            the distributor company of the movie
	 * @param director
	 *            the director of the movie
	 * @param writer
	 *            the writer of the movie
	 * @param actor1
	 *            the main actor
	 * @param actor2
	 *            the second actor
	 * @param actor3
	 *            the third actor
	 * @param breitengrad
	 *            the latitude of the film location
	 * @param laengengrad
	 *            the longitude of the film location
	 * @param imdbRanking
	 *            the IMDb rank
	 * @param genre
	 *            the film gerne
	 */
	public FilmLocation(String title, int releaseYear, String funFacts, String productionCompany, String distributor,
			String director, String writer, String actor1, String actor2, String actor3, double breitengrad,
			double laengengrad, double imdbRanking, String genre, String district) {
		this.title = title;
		this.releaseYear = releaseYear;
		this.funFacts = funFacts;
		this.productionCompany = productionCompany;
		this.distributor = distributor;
		this.setDirector(director);
		this.writer = writer;
		this.actor1 = actor1;
		this.actor2 = actor2;
		this.actor3 = actor3;
		this.breitengrad = breitengrad;
		this.laengengrad = laengengrad;
		this.imdbRanking = imdbRanking;
		this.genre = genre;
		this.setDistrict(district);

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFunFacts() {
		return funFacts;
	}

	public void setFunFacts(String funFacts) {
		this.funFacts = funFacts;
	}

	public String getProductionCompany() {
		return productionCompany;
	}

	public void setProductionCompany(String productionCompany) {
		this.productionCompany = productionCompany;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getActor1() {
		return actor1;
	}

	public void setActor1(String actor1) {
		this.actor1 = actor1;
	}

	public String getActor2() {
		return actor2;
	}

	public void setActor2(String actor2) {
		this.actor2 = actor2;
	}

	public String getActor3() {
		return actor3;
	}

	public void setActor3(String actor3) {
		this.actor3 = actor3;
	}

	public double getBreitengrad() {
		return breitengrad;
	}

	public void setBreitengrad(double breitengrad) {
		this.breitengrad = breitengrad;
	}

	public double getLaengengrad() {
		return laengengrad;
	}

	public void setLaengengrad(double laengengrad) {
		this.laengengrad = laengengrad;
	}

	public double getImdbRanking() {
		return imdbRanking;
	}

	public void setImdbRanking(double imdbRanking) {
		this.imdbRanking = imdbRanking;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
}
