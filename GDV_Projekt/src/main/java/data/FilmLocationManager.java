package data;

import java.io.FileReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import au.com.bytecode.opencsv.CSVReader;
import main.Configuration;

public class FilmLocationManager {

	private static final FilmLocationManager manager = new FilmLocationManager();

	private List<FilmLocation> filmLocationList = new ArrayList<FilmLocation>();
	private Set<String> productionCompanyList = new TreeSet<>();
	private Set<String> distributorList = new TreeSet<>();
	private Set<String> directorList = new TreeSet<>();
	private Set<String> titleList = new TreeSet<>();

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
	@SuppressWarnings("deprecation")
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
				titleList.add(line[0]);
				location.setReleaseYear(new Date(Integer.valueOf(line[1]), 1, 1));
				location.setLocation(line[2]);
				location.setFunFacts(line[3]);
				location.setProductionCompany(line[4]);
				productionCompanyList.add(line[4]);
				location.setDistributor(line[5]);
				distributorList.add(line[5]);
				location.setDirector(line[6]);
				directorList.add(line[6]);
				location.setWriter(line[7]);
				location.setActor1(line[8]);
				location.setActor2(line[9]);
				location.setActor3(line[10]);
				location.setBreitengrad(Double.parseDouble(line[11].replaceAll(",", ".")));
				location.setLaengengrad(Double.parseDouble(line[12].replaceAll(",", ".")));
				// TODO CSV anpassen
				// location.setImdbRanking(Double.parseDouble(line[13].replaceAll(",", ".")));
				// location.setDistrict(line[14]);
				filmLocationList.add(location);
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Filter the list by the start end end date
	 * 
	 * @param list
	 *            the handed list
	 * @param start
	 *            the start date
	 * @param end
	 *            the end date
	 * @return the filtered list
	 */
	public List<FilmLocation> filterByDate(List<FilmLocation> list, Date start, Date end) {

		if (list == null || start == null || end == null || start.after(end)) {
			return list;
		} else {
			list = list.stream().filter(f -> f.getReleaseYear().after(start) && f.getReleaseYear().before(end))
					.collect(Collectors.toList());
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
	public List<FilmLocation> filterByProductionCompany(List<FilmLocation> list, List<String> productionCompany) {

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
	public List<FilmLocation> filterByDistributor(List<FilmLocation> list, List<String> distributor) {

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

		if (list == null) {
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
	 * @param district
	 *            the list of the districts
	 * @return the filtered list
	 */
	public List<FilmLocation> filterByDistrict(List<FilmLocation> list, List<String> district) {

		if (list == null || district == null || district.isEmpty()) {
			return list;
		} else {
			List<FilmLocation> temp = new ArrayList<FilmLocation>();
			for (String element : district) {
				temp.addAll(list.stream().filter(f -> f.getDistrict().equals(element)).collect(Collectors.toList()));

			}
			list = temp;
			return list;
		}
	}

	public List<FilmLocation> getFilmLocationList() {
		return filmLocationList;
	}

	public Set<String> getProductionCompanyList() {
		return productionCompanyList;
	}

	public Set<String> getDistributorList() {
		return distributorList;
	}

	public Set<String> getDirectorList() {
		return directorList;
	}

	public Set<String> getTitleList() {
		return titleList;
	}

}
