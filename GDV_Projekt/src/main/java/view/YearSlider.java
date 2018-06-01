package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controlP5.ControlP5;
import controlP5.Slider;
import controlP5.Textlabel;
import data.FilmLocationManager;
import main.Configuration;
import processing.core.PApplet;

@SuppressWarnings("unused")
public class YearSlider {

	private PApplet pApplet;

	private int startDrawX;
	private int startDrawY;
	private int width;
	private int height;

	private ControlP5 cp5;
	private Textlabel label;
	private Textlabel descriptionLabel;

	private FilmLocationManager manager = FilmLocationManager.getInstance();
	private List<Slider> yearSliderList = new ArrayList<>();
	private Map<Integer, Integer> yearList = manager.getReleaseYearList();
	private int yearValue;

	public YearSlider(PApplet pApplet, int x, int y, int width, int height) {
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

		cp5 = new ControlP5(pApplet);

		label = new Textlabel(cp5, "Zeitachse", startDrawX, startDrawY, 400, 200)
				.setFont(pApplet.createFont("Georgia", 20)).setColor(pApplet.color(0, 0, 0, 0));

		descriptionLabel = new Textlabel(cp5, "Wählen Sie den zu filternden Zeitraum aus:", startDrawX, startDrawY + 35,
				400, 200).setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		setValues();
		addSlider(150);
	}

	/**
	 * Draw the different elements
	 */
	public void draw() {

		label.draw(pApplet);
		descriptionLabel.draw(pApplet);
	}

	private void addSlider(int positionY) {

		int size = 20;
		for (Map.Entry<Integer, Integer> element : yearList.entrySet()) {

			Slider slider = null;

			slider = setSlider(element.getKey(), positionY, size, element.getValue(), 100);
			yearSliderList.add(slider);

			size += 10;

		}
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
	private Slider setSlider(Integer name, int size, int positionY, int value, int maxValue) {

		Slider slider = cp5.addSlider("Slider: " + name).setPosition(positionY + size, startDrawY + 50).setSize(10, 75)
				.setRange(0, maxValue).setValue(value).setColorBackground(pApplet.color(255, 255, 255, 75))
				.setValueLabel("").setColorCaptionLabel(pApplet.color(0, 0, 0, 100)).setLock(true);

		if ((name % 5) == 0) {
			slider.setCaptionLabel(name.toString());
		} else {
			slider.setCaptionLabel("");
		}
		return slider;
	}

	private void setValues() {

		for (Integer i : yearList.values()) {
			yearValue += i;
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
