package data;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import au.com.bytecode.opencsv.CSVReader;
import main.Configuration;

public class FilmLocationManager {

	private static final FilmLocationManager manager = new FilmLocationManager();

	private List<FilmLocation> filmLocationList = new ArrayList<FilmLocation>();
	private Map<String, Integer> genreList = new HashMap<>();
	private Map<Integer, Integer> releaseYearList = new HashMap<>();
	private Map<String, Integer> imdbRankingList = new HashMap<>();
	private Map<String, Integer> productionCompanyList = new HashMap<>();
	private Map<String, Integer> distributorList = new HashMap<>();
	private Map<String, Integer> directorList = new HashMap<>();
	private Map<String, Integer> titleList = new HashMap<>();

	/**
	 * Singeton
	 * 
	 * @return the FilmLocationManager object
	 */
	public static FilmLocationManager getInstance() {
		return manager;
	}

	/**
	 * Constructor
	 */
	private FilmLocationManager() {
		setup();
	}

	/**
	 * 
	 */
	public void setup() {

		try {
			CSVReader reader = new CSVReader(new FileReader(Configuration.locationPath), ';');
			String[] line;
			// ignore headerline
			reader.readNext();

			// create filmlocation from every line
			while ((line = reader.readNext()) != null) {
				FilmLocation location = new FilmLocation();
				location.setTitle(line[0]);
				setMap("Titel", line[0]);
				location.setReleaseYear(Integer.parseInt(line[1]));
				setMap("Jahr", line[1]);
				location.setLocation(line[2]);
				location.setFunFacts(line[3]);
				location.setProductionCompany(line[4]);
				setMap("Produktion", line[4]);
				location.setDistributor(line[5]);
				setMap("Vertrieb", line[5]);
				location.setDirector(line[6]);
				setMap("Regie", line[6]);
				location.setWriter(line[7]);
				location.setActor1(line[8]);
				location.setActor2(line[9]);
				location.setActor3(line[10]);
				location.setBreitengrad(Double.parseDouble(line[11].replaceAll(",", ".")));
				location.setLaengengrad(Double.parseDouble(line[12].replaceAll(",", ".")));
				location.setImdbRanking(Double.parseDouble(line[13].replaceAll(",", ".")));
				setMap("IMDB", line[13]);
				location.setGenre(line[14]);
				setMap("Genre", line[14]);
				filmLocationList.add(location);
			}
			reader.close();
			completeMap();
			sortMap();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param type
	 * @param key
	 */
	private void setMap(String type, String key) {

		if (type.equals("Titel")) {
			if (titleList.containsKey(key)) {
				titleList.put(key, titleList.get(key) + 1);
			} else {
				titleList.put(key, 1);
			}
		} else if (type.equals("Regie")) {
			if (directorList.containsKey(key)) {
				directorList.put(key, directorList.get(key) + 1);
			} else {
				directorList.put(key, 1);
			}
		} else if (type.equals("Produktion")) {
			if (productionCompanyList.containsKey(key)) {
				productionCompanyList.put(key, productionCompanyList.get(key) + 1);
			} else {
				productionCompanyList.put(key, 1);
			}
		} else if (type.equals("Vertrieb")) {
			if (distributorList.containsKey(key)) {
				distributorList.put(key, distributorList.get(key) + 1);
			} else {
				distributorList.put(key, 1);
			}
		} else if (type.equals("IMDB")) {
			if (imdbRankingList.containsKey(key)) {
				imdbRankingList.put(key, imdbRankingList.get(key) + 1);
			} else {
				imdbRankingList.put(key, 1);
			}
		} else if (type.equals("Jahr")) {
			if (releaseYearList.containsKey(Integer.valueOf(key))) {
				releaseYearList.put(Integer.valueOf(key), releaseYearList.get(Integer.valueOf(key)) + 1);
			} else {
				releaseYearList.put(Integer.valueOf(key), 1);
			}
		} else if (type.equals("Genre")) {
			for (String element : key.split(",")) {
				element = element.replaceAll(" ", "");
				if (genreList.containsKey(element)) {
					genreList.put(element, genreList.get(element) + 1);
				} else {
					genreList.put(element, 1);
				}
			}
		}
	}

	/**
	 * 
	 */
	private void completeMap() {

		for (int dezimalzahl = 0; dezimalzahl != 10; dezimalzahl++) {
			for (int dezimalstelle = 0; dezimalstelle != 10; dezimalstelle++) {
				String key = "" + dezimalzahl + "," + dezimalstelle;
				if (!imdbRankingList.containsKey(key)) {
					imdbRankingList.put(key, 0);
				}
			}
		}

		for (int i = 1915; i != 2018; i++) {
			if (!releaseYearList.containsKey(i)) {
				releaseYearList.put(i, 0);
			}
		}
	}

	private void sortMap() {

		titleList = titleList.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))

				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		directorList = directorList.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))

				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		distributorList = distributorList.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		productionCompanyList = productionCompanyList.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		imdbRankingList = imdbRankingList.entrySet().stream()
				.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		releaseYearList = releaseYearList.entrySet().stream()
				.sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		genreList = genreList.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

	}

	/**
	 * Filter the list by the handed parameter
	 * 
	 * @param list
	 *            the handed list
	 * @param title
	 *            the list of the titles
	 * @return the filtered list
	 */
	public List<FilmLocation> filterByTitle(List<FilmLocation> list, Set<String> title) {

		if (list == null || title == null || title.isEmpty()) {
			return list;
		} else {
			List<FilmLocation> temp = new ArrayList<FilmLocation>();
			for (String element : title) {
				temp.addAll(list.stream().filter(f -> f.getTitle().equals(element)).collect(Collectors.toList()));

			}
			list = temp;
			return list;
		}
	}

	/**
	 * Filter the list by the handed parameter
	 * 
	 * @param list
	 *            the handed list
	 * @param title
	 *            the list of the director
	 * @return the filtered list
	 */
	public List<FilmLocation> filterByDirector(List<FilmLocation> list, Set<String> director) {

		if (list == null || director == null || director.isEmpty()) {
			return list;
		} else {
			List<FilmLocation> temp = new ArrayList<FilmLocation>();
			for (String element : director) {
				temp.addAll(list.stream().filter(f -> f.getDirector().equals(element)).collect(Collectors.toList()));

			}
			list = temp;
			return list;
		}
	}

	/**
	 * Filter the list by the handed parameter
	 * 
	 * @param list
	 *            the handed list
	 * @param productionCompany
	 *            the list of the companies
	 * @return the filtered list
	 */
	public List<FilmLocation> filterByProductionCompany(List<FilmLocation> list, Set<String> productionCompany) {

		if (list == null || productionCompany == null || productionCompany.isEmpty()) {
			return list;
		} else {
			List<FilmLocation> temp = new ArrayList<FilmLocation>();
			for (String element : productionCompany) {
				temp.addAll(list.stream().filter(f -> f.getProductionCompany().equals(element))
						.collect(Collectors.toList()));

			}
			list = temp;
			return list;
		}
	}

	/**
	 * Filter the list by the handed parameter
	 * 
	 * @param list
	 *            the handed list
	 * @param distributor
	 *            the list of the distributors
	 * @return the filtered list
	 */
	public List<FilmLocation> filterByDistributor(List<FilmLocation> list, Set<String> distributor) {

		if (list == null || distributor == null || distributor.isEmpty()) {
			return list;
		} else {
			List<FilmLocation> temp = new ArrayList<FilmLocation>();
			for (String element : distributor) {
				temp.addAll(list.stream().filter(f -> f.getDistributor().equals(element)).collect(Collectors.toList()));

			}
			list = temp;
			return list;
		}
	}

	/**
	 * Filter the list by the handed parameter
	 * 
	 * @param list
	 *            the handed list
	 * @param start
	 *            the lowest ranking
	 * @param end
	 *            the highest rankling
	 * @return the filtered list
	 */
	public List<FilmLocation> filterByIMDBRanking(List<FilmLocation> list, double start, double end) {

		if (list == null || start > end) {
			return list;
		} else {
			list = list.stream().filter(f -> f.getImdbRanking() >= start && f.getImdbRanking() <= end)
					.collect(Collectors.toList());
			return list;
		}
	}

	/**
	 * Filter the list by the handed parameter
	 * 
	 * @param list
	 *            the handed list
	 * @param genre
	 *            the list of the genre
	 * @return the filtered list
	 */
	public List<FilmLocation> filterByGenre(List<FilmLocation> list, Set<String> genre) {

		if (list == null || genre == null || genre.isEmpty()) {
			return list;
		} else {
			List<FilmLocation> temp = new ArrayList<FilmLocation>();
			for (String element : genre) {
				temp.addAll(list.stream().filter(f -> f.getGenre().contains(element)).collect(Collectors.toList()));

			}
			list = temp;
			return list;
		}
	}

	public List<FilmLocation> filterByYear(List<FilmLocation> list, int startYear, int endYear) {

		if (list == null || startYear < 1915 || endYear < 1916 || startYear > 2018 || endYear > 2019
				|| endYear < startYear) {
			return list;
		} else {
			List<FilmLocation> temp = new ArrayList<FilmLocation>();
			temp.addAll(list.stream().filter(f -> f.getReleaseYear() <= endYear)
					.filter(f2 -> f2.getReleaseYear() >= startYear).collect(Collectors.toList()));
			list = temp;
			return list;
		}
	}

	public List<FilmLocation> getFilmLocationList() {
		return filmLocationList;
	}

	public Map<String, Integer> getGenreList() {
		return genreList;
	}

	public Map<Integer, Integer> getReleaseYearList() {
		return releaseYearList;
	}

	public Map<String, Integer> getIMDBRankingList() {
		return imdbRankingList;
	}

	public Map<String, Integer> getProductionCompanyList() {
		return productionCompanyList;
	}

	public Map<String, Integer> getDistributorList() {
		return distributorList;
	}

	public Map<String, Integer> getDirectorList() {
		return directorList;
	}

	public Map<String, Integer> getTitleList() {
		return titleList;
	}

}
