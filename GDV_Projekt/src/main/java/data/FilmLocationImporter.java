package data;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import main.Configuration;

public class FilmLocationImporter {

	/**
	 * Transform the elements from the csv file to a list of filmlocation objects
	 * 
	 * @return the list of the filmlocation
	 */
	@SuppressWarnings("deprecation")
	public List<FilmLocation> importer() {

		List<FilmLocation> list = new ArrayList<FilmLocation>();

		try {
			CSVReader reader = new CSVReader(new FileReader(Configuration.locationPath), ';');
			String[] line;
			// ignore headerline
			reader.readNext();

			// create filmlocation from every line
			while ((line = reader.readNext()) != null) {
				FilmLocation location = new FilmLocation();

				location.setTitle(line[0]);
				location.setReleaseYear(new Date(1, 1, Integer.valueOf(line[1])));
				location.setLocation(line[2]);
				location.setFunFacts(line[3]);
				location.setProductionCompany(line[4]);
				location.setDistributor(line[5]);
				location.setDirector(line[6]);
				location.setWriter(line[7]);
				location.setActor1(line[8]);
				location.setActor2(line[9]);
				location.setActor3(line[10]);
				location.setBreitengrad(Double.parseDouble(line[11].replaceAll(",", ".")));
				location.setLaengengrad(Double.parseDouble(line[12].replaceAll(",", ".")));
				// TODO CSV anpassen
				// location.setImdbRanking(Double.parseDouble(line[13].replaceAll(",", ".")));
				// location.setDistrict(line[14]);
				list.add(location);
			}
			reader.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
}
