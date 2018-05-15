package data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilmLocationManager {

	private List<FilmLocation> list = null;
	private FilmLocationImporter importer = new FilmLocationImporter();
	private static final FilmLocationManager manager = new FilmLocationManager();

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
		setList(importer.importer());
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

	/**
	 * Get the full list
	 * 
	 * @return the list with all elements
	 */
	public List<FilmLocation> getList() {
		return list;
	}

	/**
	 * Set the full list
	 * 
	 * @param list
	 *            the new list
	 */
	public void setList(List<FilmLocation> list) {
		this.list = list;
	}

}
