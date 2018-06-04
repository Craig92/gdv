package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import controlP5.Range;
import controlP5.CColor;
import controlP5.ControlEvent;
import controlP5.ControlP5;
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
	private Textlabel descriptionLabel, lowHandleLabel, highHandleLabel;
	private Range range;

	private FilmLocationManager manager = FilmLocationManager.getInstance();
	private List<Slider> yearSliderList = new ArrayList<>();
	private Map<Integer, Integer> yearList = manager.getReleaseYearList();
	private int yearValue;
	// Hilfsvariable um mit der Methode startOrEndDateChanged die Aufrufhäufigkeit
	// in MouseDragged zu verringern
	private int previousStartDate = 1915, previousEndDate = 2018;

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
		addSlider(startDrawX + 40);
		addRange();

		lowHandleLabel = new Textlabel(cp5, "" + range.getLowValue(),
				(int) ((int) range.getPosition()[0] + (10 * (range.getLowValue() - 1915))),
				(int) range.getPosition()[1] + 20, 400, 200).setFont(pApplet.createFont("Georgia", 12))
						.setColor(pApplet.color(0, 0, 0, 0));

		highHandleLabel = new Textlabel(cp5, "" + range.getLowValue(),
				(int) ((int) range.getPosition()[0] + (10 * (range.getHighValue() - 1915))),
				(int) range.getPosition()[1] + 20, 400, 200).setFont(pApplet.createFont("Georgia", 12))
						.setColor(pApplet.color(0, 0, 0, 0));
	}

	/**
	 * Draw the different elements
	 */
	public void draw() {
		label.draw(pApplet);
		descriptionLabel.draw(pApplet);
		pApplet.fill(255);
		pApplet.rect(0, 0, width / 2, height);
		changeFloatLabelToIntLabel();
		updateHandleLabels();
		lowHandleLabel.draw(pApplet);
		highHandleLabel.draw(pApplet);
	}

	/**
	 * Add the range
	 */
	private void addRange() {

		range = cp5.addRange("rangeController")
				// disable broadcasting since setRange and setRangeValues will trigger an event
				.setBroadcast(false).setPosition(startDrawX + 58, startDrawY + 140)
				.setSize(28 + (previousEndDate - previousStartDate) * 10, 20).setHandleSize(15)
				.setRange(previousStartDate, previousEndDate).setRangeValues(previousStartDate, previousEndDate)
				.setLabelVisible(false).setColorBackground(pApplet.color(150, 80))
				.setColorActive(pApplet.color(46, 139, 87, 80)).setBroadcast(true);
		changeFloatLabelToIntLabel();

	}
	public boolean isOnSlider(int mouseX, int mouseY) {
		changeFloatLabelToIntLabel();
		return (mouseX >= startDrawX +20 && mouseY >= startDrawY + 120);
	}

	/**
	 * Check if the start or the end date changed
	 * 
	 * @param newStartDate
	 *            the start date as integer
	 * @param newEndDate
	 *            the end date as a integer
	 * @return
	 */
	public boolean startOrEndDateChanged(int newStartDate, int newEndDate) {

		if (!(newStartDate == previousStartDate && newEndDate == previousEndDate)) {
			previousStartDate = newStartDate;
			previousEndDate = newEndDate;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Convert the float value to an integer value
	 */
	public void changeFloatLabelToIntLabel() {
		range.setLowValueLabel("" + (int) range.getLowValue());
		range.setHighValueLabel("" + (int) range.getHighValue());
	}

	/**
	 * Set the value label
	 */
	private void updateHandleLabels() {
		lowHandleLabel.setPosition(range.getPosition()[0] + (10 * (range.getLowValue() - 1915)),
				lowHandleLabel.getPosition()[1]);
		highHandleLabel.setPosition(range.getPosition()[0] + (10 * (range.getHighValue() - 1915)),
				highHandleLabel.getPosition()[1]);
		lowHandleLabel.setText("" + (int) range.getLowValue());
		highHandleLabel.setText("" + (int) range.getHighValue());
	}

	/**
	 * Add the slider to the list
	 * 
	 * @param positionY
	 *            the y position of the slider
	 */
	private void addSlider(int positionY) {

		int size = 20;
		for (Map.Entry<Integer, Integer> element : yearList.entrySet()) {
			Slider slider = setSlider(element.getKey(), positionY, size, element.getValue(), 100);
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

	/**
	 * Set the sum of the the values of the different lists
	 */
	private void setValues() {

		for (Integer i : yearList.values()) {
			yearValue += i;
		}
	}

	public int getStartDate() {
		return (int) range.getLowValue();
	}

	public int getEndDate() {
		return (int) range.getHighValue();
	}
}
