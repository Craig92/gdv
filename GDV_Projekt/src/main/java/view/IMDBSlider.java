package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.RadioButton;
import controlP5.Slider;
import controlP5.Textlabel;
import data.FilmLocationManager;
import main.Configuration;
import processing.core.PApplet;

@SuppressWarnings("unused")
public class IMDBSlider {

	private PApplet pApplet;
	private int startDrawX;
	private int startDrawY;
	private int width;
	private int height;

	private ControlP5 cp5;
	private Textlabel descriptionIMDBLabel;
	private Textlabel infoLabel1;
	private Textlabel infoLabel2;
	private Textlabel teamNameLabel;
	private Textlabel teamMemberLabel1;
	private Textlabel teamMemberLabel2;
	private Textlabel teamMemberLabel3;
	private Textlabel vorlesungLabel;
	private Textlabel hochschuleLabel;
	private Textlabel yearLabel;

	private FilmLocationManager manager = FilmLocationManager.getInstance();
	private List<Slider> rankingSliderList = new ArrayList<>();
	private Map<String, Integer> imdbList = manager.getIMDBRankingList();
	private int genreValue;

	private Slider highSlider;
	private Slider lowSlider;
	private int start;
	private int end;

	/**
	 * Constructor
	 * 
	 * @param pApplet
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public IMDBSlider(PApplet pApplet, int x, int y, int width, int height) {
		this.pApplet = pApplet;
		this.startDrawX = x;
		this.startDrawY = y;
		this.width = width;
		this.height = height;
		setup();
	}

	/**
	 * Set the size and position of the different elements
	 */
	private void setup() {

		int heightPosition = 150;
		cp5 = new ControlP5(pApplet);

		descriptionIMDBLabel = new Textlabel(cp5, "IMDb-Bewertung filtern:", startDrawX, startDrawY + 110, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		infoLabel1 = new Textlabel(cp5, "* Es kann nur nach den " + Configuration.limit + " häufigsten", startDrawX,
				startDrawY + 35, 400, 200).setFont(pApplet.createFont("Georgia", 14))
						.setColor(pApplet.color(0, 0, 0, 0));
		infoLabel2 = new Textlabel(cp5, "Attributen pro Typ gefiltert werden", startDrawX, startDrawY + 50, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		setValues();
		heightPosition += addSlider(150);

		teamNameLabel = new Textlabel(cp5, "Team: WestCostMovies", startDrawX, (int) (startDrawY + heightPosition + 15),
				400, 200).setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		teamMemberLabel1 = new Textlabel(cp5, "Silvia Altrichter, Dominique Bost,", startDrawX,
				(int) (startDrawY + heightPosition + 30), 400, 200).setFont(pApplet.createFont("Georgia", 14))
						.setColor(pApplet.color(0, 0, 0, 0));

		teamMemberLabel2 = new Textlabel(cp5, "Thorsten Föhringer, Özkan Ünlü,", startDrawX,
				(int) (startDrawY + heightPosition + 45), 400, 200).setFont(pApplet.createFont("Georgia", 14))
						.setColor(pApplet.color(0, 0, 0, 0));

		teamMemberLabel3 = new Textlabel(cp5, "Melissa Zindl", startDrawX, (int) (startDrawY + heightPosition + 60),
				400, 200).setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		vorlesungLabel = new Textlabel(cp5, "GDV - Grundlagen der Datenvisualisierung", startDrawX,
				(int) (startDrawY + heightPosition + 80), 400, 200).setFont(pApplet.createFont("Georgia", 14))
						.setColor(pApplet.color(0, 0, 0, 0));

		hochschuleLabel = new Textlabel(cp5, "Hochschule Mannheim", startDrawX,
				(int) (startDrawY + heightPosition + 95), 400, 200).setFont(pApplet.createFont("Georgia", 14))
						.setColor(pApplet.color(0, 0, 0, 0));

		yearLabel = new Textlabel(cp5, "Sommersemester 2018", startDrawX, (int) (startDrawY + heightPosition + 110),
				400, 200).setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		highSlider = setRangeSlider("HightSlider", 10, 80, heightPosition);
		lowSlider = setRangeSlider("LowSlider", 0, 70, heightPosition);

	}

	/**
	 * Draw the different elements
	 */
	public void draw() {

		descriptionIMDBLabel.draw(pApplet);
		infoLabel1.draw(pApplet);
		infoLabel2.draw(pApplet);
		teamNameLabel.draw(pApplet);
		teamMemberLabel1.draw(pApplet);
		teamMemberLabel2.draw(pApplet);
		teamMemberLabel3.draw(pApplet);
		vorlesungLabel.draw(pApplet);
		hochschuleLabel.draw(pApplet);
		yearLabel.draw(pApplet);
	}

	/**
	 * Add the slider to the list
	 * 
	 * @param positionY
	 *            the y position of the slider
	 * @return the y position of the slider end
	 */
	private int addSlider(int positionY) {

		int size = 20;
		for (Map.Entry<String, Integer> element : imdbList.entrySet()) {
			Slider slider = setSlider(element.getKey(), positionY, size, element.getValue(), 100);
			rankingSliderList.add(slider);
			size += 5;
		}
		return size;
	}

	/**
	 * Set the properties of the Slider for the Range
	 * 
	 * @param name
	 *            the name of the slider
	 * @param value
	 *            the value of the slider
	 * @param positionX
	 *            the x position of the slider
	 * @param positionY
	 *            the y position of the slider
	 * @return the slider
	 */
	private Slider setRangeSlider(String name, int value, int positionX, int positionY) {

		return cp5.addSlider(name).setPosition(startDrawX + positionX, 150).setSize(10, positionY - 140).setRange(0, 10)
				.setValue(value).setValueLabel("" + value).setCaptionLabel(" ").setLock(false)
				.setSliderMode(Slider.FLEXIBLE).setColorBackground(pApplet.color(150, 80))
				.setColorLabel(pApplet.color(0)).setLabelVisible(false);
	}

	/**
	 * Set the properties of the Slider
	 * 
	 * @param name
	 *            the name of the slider
	 * @param size
	 *            the y size of the slider
	 * @param positionY
	 *            the y position of the slider
	 * @param value
	 *            the current value of the slider
	 * @param maxValue
	 *            the max value of the slider
	 * @return the slider
	 */
	private Slider setSlider(String name, int size, int positionY, int value, int maxValue) {

		Slider slider = cp5.addSlider("Slider: " + name).setPosition(startDrawX + 100, positionY + size).setSize(75, 5)
				.setRange(0, maxValue).setValue(value).setColorBackground(pApplet.color(255, 255, 255, 75))
				.setValueLabel(" ").setColorCaptionLabel(pApplet.color(0, 0, 0, 100)).setLock(true);

		if (name.equals("0,0")) {
			slider.setCaptionLabel("Keine Bewertung");
		} else if (name.equals("9,9")) {
			slider.setCaptionLabel("10,0");
		} else if (name.contains(",5") || name.contains(",0")) {
			slider.setCaptionLabel(name);
		} else {
			slider.setCaptionLabel("");
		}
		return slider;
	}

	/**
	 * Set the sum of the the values of the different lists
	 */
	private void setValues() {

		for (Integer i : imdbList.values()) {
			genreValue += i;
		}
	}

	/**
	 * Get the lower value as a rounded double to .0 or .5
	 * 
	 * @return the start value
	 */
	public double getStartValue() {
		return highSlider.getValue() > lowSlider.getValue() ? Math.round(lowSlider.getValue() * 2) / 2
				: Math.round(highSlider.getValue() * 2) / 2;
	}

	/**
	 * Get the highter value as a rounded double to .0 or .5
	 * 
	 * @return the end value
	 */
	public double getEndValue() {
		return highSlider.getValue() < lowSlider.getValue() ? Math.round(lowSlider.getValue() * 2) / 2
				: Math.round(highSlider.getValue() * 2) / 2;
	}

}
