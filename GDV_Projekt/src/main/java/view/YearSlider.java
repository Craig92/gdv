package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import controlP5.Range;
import controlP5.ControlP5;
import controlP5.Slider;
import controlP5.Textlabel;
import data.FilmLocationManager;
import main.SanFranciscoApplet;
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
	private int yearValue = setValues(yearList);
	private int previousStartDate = 1915, previousEndDate = 2018;

	/**
	 * 
	 * @param pApplet
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
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

		label = new Textlabel(cp5, "Zeitachse", startDrawX, startDrawY, 400, 200).setFont(SanFranciscoApplet.headerFont)
				.setColor(SanFranciscoApplet.textColor);

		descriptionLabel = new Textlabel(cp5, "Wählen Sie den zu filternden Zeitraum aus:", startDrawX, startDrawY + 35,
				400, 200).setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);

		addSlider(startDrawX + 40);
		addRange();

		lowHandleLabel = new Textlabel(cp5, "" + range.getLowValue(),
				(int) ((int) range.getPosition()[0] + (10 * (range.getLowValue() - 1915))),
				(int) range.getPosition()[1] + 20, 400, 200).setFont(SanFranciscoApplet.textFont)
						.setColor(SanFranciscoApplet.textColor);

		highHandleLabel = new Textlabel(cp5, "" + range.getLowValue(),
				(int) ((int) range.getPosition()[0] + (10 * (range.getHighValue() - 1915))),
				(int) range.getPosition()[1] + 20, 400, 200).setFont(SanFranciscoApplet.textFont)
						.setColor(SanFranciscoApplet.textColor);
	}

	/**
	 * Draw the different elements
	 */
	public void draw() {
		label.draw(pApplet);
		descriptionLabel.draw(pApplet);
		pApplet.fill(SanFranciscoApplet.selectedColor);
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
				.setSize(15 + (previousEndDate - previousStartDate) * 10, 20).setHandleSize(15)
				.setRange(previousStartDate, previousEndDate).setRangeValues(previousStartDate, previousEndDate)
				.setLabelVisible(false).setColorForeground(SanFranciscoApplet.selectedColor).setColorBackground(SanFranciscoApplet.unselectedColor)
				.setColorActive(SanFranciscoApplet.selectedColor).setBroadcast(true);
		changeFloatLabelToIntLabel();

	}

	/**
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public boolean isOnSlider(int mouseX, int mouseY) {
		changeFloatLabelToIntLabel();
		return (mouseX >= startDrawX + 20 && mouseY >= startDrawY + 120);
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
	 * Change the color of the slider if the range slider changed
	 */
	public void updateDiagramm() {
		for (int i = 0; i != yearSliderList.size(); i++) {
			if (Float.valueOf(yearSliderList.get(i).getName().split(" ")[1]) <= range.getHighValue()
					&& Float.valueOf(yearSliderList.get(i).getName().split(" ")[1]) >= range.getLowValue()) {
				yearSliderList.set(i, yearSliderList.get(i).setColorForeground(SanFranciscoApplet.selectedColor));
				yearSliderList.get(i).setColorForeground(SanFranciscoApplet.selectedColor);
			} else {
				yearSliderList.set(i, yearSliderList.get(i).setColorForeground(SanFranciscoApplet.unselectedColor));
				yearSliderList.get(i).setColorForeground(SanFranciscoApplet.unselectedColor);
			}
		}
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
				.setRange(0, maxValue).setValue(value).setColorForeground(SanFranciscoApplet.selectedColor)
				.setColorBackground(SanFranciscoApplet.backgroundColor).setValueLabel("")
				.setColorCaptionLabel(SanFranciscoApplet.textColor).setLock(true);

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
	private Integer setValues(Map<Integer, Integer> map) {

		Integer value = new Integer(0);
		for (Integer i : map.values()) {
			value += i;
		}
		return value;
	}

	/**
	 * 
	 * @return
	 */
	public int getStartDate() {
		return (int) range.getLowValue();
	}

	/**
	 * 
	 * @return
	 */
	public int getEndDate() {
		return (int) range.getHighValue();
	}
}
