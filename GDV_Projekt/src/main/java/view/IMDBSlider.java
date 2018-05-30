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
	private Map<String, Integer> genreList = manager.getIMDBRankingList();
	private int genreValue;

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

	private int addSlider(int positionY) {

		int size = 20;
		for (Map.Entry<String, Integer> element : genreList.entrySet()) {

			Slider slider = null;

			slider = setSlider(element.getKey(), positionY, size, element.getValue(), 100);
			rankingSliderList.add(slider);

			size += 5;

		}
		return size;
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
				.setValueLabel("").setColorCaptionLabel(pApplet.color(0, 0, 0, 100)).setLock(true);

		if (name.equals("0")) {
			slider.setCaptionLabel("Keine Bewertung");
		} else if (name.contains(",5") || !name.contains(",")) {
			slider.setCaptionLabel(name);
		} else {
			slider.setCaptionLabel("");
		}
		return slider;
	}

	private void setValues() {

		for (Integer i : genreList.values()) {
			genreValue += i;
		}
	}

	/**
	 * Handle the clicks of the mouse
	 * 
	 * @param mouseX
	 *            the x position of the mouse
	 * @param mouseY
	 *            the y position of the mouse
	 */
	public void mouseClicked(int mouseX, int mouseY) {
		// TODO Auto-generated method stub

	}

}
