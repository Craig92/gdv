package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.Slider;
import controlP5.Textlabel;
import data.FilmLocationManager;
import main.Configuration;
import main.SanFranciscoApplet;
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
	private Textlabel lowHandleLabel;
	private Textlabel highHandleLabel;
	private Button lowHandleRectangle;
	private Button highHandleRectangle;
	private Button rangeRectangle;
	private Button backgroundRectangle;

	private FilmLocationManager manager = FilmLocationManager.getInstance();
	private List<Slider> rankingSliderList = new ArrayList<>();
	private Map<String, Integer> imdbList = manager.getIMDBRankingList();
	private int imdbValue = setValues(imdbList);
	private int imdbMax = getMax(imdbList);

	private Slider highSlider;
	private Slider lowSlider;
	private int start;
	private int end;
	private double labelFactor;
	private int maxYRectanglePosition;
	private int minYRectanglePosition;
	private int heightPosition;
	private int heigthSlider;

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

		heightPosition = 150;
		cp5 = new ControlP5(pApplet);
		cp5.setFont(SanFranciscoApplet.buttonFont);

		descriptionIMDBLabel = new Textlabel(cp5, "IMDb-Bewertung filtern:", startDrawX, startDrawY + 130, 400, 200)
				.setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);

		infoLabel1 = new Textlabel(cp5, "* Es kann nur nach den " + Configuration.limit + " häufigsten", startDrawX,
				startDrawY + 35, 400, 200).setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		infoLabel2 = new Textlabel(cp5, "Attributen pro Typ gefiltert werden", startDrawX, startDrawY + 50, 400, 200)
				.setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);

		heightPosition += addSlider(heightPosition);

		// team info
		teamNameLabel = new Textlabel(cp5, "Team: WestCoastMovie", startDrawX, (int) (startDrawY + heightPosition + 15),
				400, 200).setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		teamMemberLabel1 = new Textlabel(cp5, "Silvia Altrichter, Dominique Bost,", startDrawX,
				(int) (startDrawY + heightPosition + 30), 400, 200).setFont(SanFranciscoApplet.textFont)
						.setColor(SanFranciscoApplet.textColor);
		teamMemberLabel2 = new Textlabel(cp5, "Thorsten Föhringer, Özkan Ünlü,", startDrawX,
				(int) (startDrawY + heightPosition + 45), 400, 200).setFont(SanFranciscoApplet.textFont)
						.setColor(SanFranciscoApplet.textColor);
		teamMemberLabel3 = new Textlabel(cp5, "Melissa Zindl", startDrawX, (int) (startDrawY + heightPosition + 60),
				400, 200).setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		vorlesungLabel = new Textlabel(cp5, "GDV - Grundlagen der Datenvisualisierung", startDrawX,
				(int) (startDrawY + heightPosition + 80), 400, 200).setFont(SanFranciscoApplet.textFont)
						.setColor(SanFranciscoApplet.textColor);
		hochschuleLabel = new Textlabel(cp5, "Hochschule Mannheim", startDrawX,
				(int) (startDrawY + heightPosition + 95), 400, 200).setFont(SanFranciscoApplet.textFont)
						.setColor(SanFranciscoApplet.textColor);
		yearLabel = new Textlabel(cp5, "Sommersemester 2018", startDrawX, (int) (startDrawY + heightPosition + 110),
				400, 200).setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);

		// slider
		highSlider = setRangeSlider("HightSlider", 10, 80, heightPosition).setVisible(false);
		lowSlider = setRangeSlider("LowSlider", 0, 70, heightPosition).setVisible(false);

		labelFactor = lowSlider.getHeight() / 10.0;

		lowHandleLabel = new Textlabel(cp5, createStringWith1DecimalPlace(lowSlider.getValue()),
				getXPostionHandleLabel(lowSlider), (int) lowSlider.getPosition()[1], 400, 200)
						.setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		highHandleLabel = new Textlabel(cp5, createStringWith1DecimalPlace(highSlider.getValue()),
				getXPostionHandleLabel(highSlider), (int) highSlider.getPosition()[1], 400, 200)
						.setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);

		// custom slider
		highHandleRectangle = cp5.addButton("highHandleButton")
				.setPosition(highSlider.getPosition()[0] - 10, (int) highSlider.getPosition()[1] + 15)
				.setSize(highSlider.getHandleSize() * 2, highSlider.getHandleSize() * 1).setMoveable(true)
				.setColorBackground(SanFranciscoApplet.selectedColor)
				.setColorForeground(SanFranciscoApplet.selectedColor).setColorCaptionLabel(SanFranciscoApplet.textColor)
				.setColorActive(SanFranciscoApplet.selectedColor).setLabelVisible(false);
		lowHandleRectangle = cp5.addButton("lowHandleButton").setLabelVisible(false)
				.setPosition(lowSlider.getPosition()[0],
						(int) lowSlider.getPosition()[1] + heightPosition - lowSlider.getHandleSize() - 150)
				.setSize(lowSlider.getHandleSize() * 2, lowSlider.getHandleSize()).setMoveable(true)
				.setColorBackground(SanFranciscoApplet.selectedColor)
				.setColorForeground(SanFranciscoApplet.selectedColor).setColorCaptionLabel(SanFranciscoApplet.textColor)
				.setColorActive(SanFranciscoApplet.selectedColor);
		rangeRectangle = cp5.addButton("rangeButton").setLabelVisible(false).setMoveable(true)
				.setColorBackground(SanFranciscoApplet.selectedColor)
				.setColorForeground(SanFranciscoApplet.selectedColor).setColorCaptionLabel(SanFranciscoApplet.textColor)
				.setColorActive(SanFranciscoApplet.selectedColor);
		updateRange();

		maxYRectanglePosition = (int) highHandleRectangle.getPosition()[1];
		minYRectanglePosition = (int) lowHandleRectangle.getPosition()[1];
		backgroundRectangle = cp5.addButton("").setPosition(lowSlider.getPosition()[0], maxYRectanglePosition)
				.setSize(lowSlider.getHandleSize() * 2,
						minYRectanglePosition + lowHandleRectangle.getHeight() - maxYRectanglePosition)
				.setColorBackground(SanFranciscoApplet.unselectedColor)
				.setColorForeground(SanFranciscoApplet.backgroundColor)
				.setColorCaptionLabel(SanFranciscoApplet.textColor).setColorActive(SanFranciscoApplet.selectedColor)
				.setColorLabel(SanFranciscoApplet.selectedColor);
		backgroundRectangle.lock();
		backgroundRectangle.update();

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
		hoverSlider();
		updateHandleLabels();
		lowHandleLabel.draw(pApplet);
		highHandleLabel.draw(pApplet);
		rangeRectangle.bringToFront();
		lowHandleRectangle.bringToFront();
		highHandleRectangle.bringToFront();
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
			Slider slider = setSlider(element.getKey(), positionY, size, element.getValue(), imdbMax);
			rankingSliderList.add(slider);
			size += Configuration.imdbDiagrammSize;
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
				.setSliderMode(Slider.FLEXIBLE).setColorBackground(SanFranciscoApplet.selectedColor)
				.setColorLabel(SanFranciscoApplet.backgroundColor).setLabelVisible(false);
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

		Slider slider = cp5.addSlider("Slider: " + name).setPosition(startDrawX + 100, positionY + size)
				.setSize(75, Configuration.imdbDiagrammSize).setRange(0, maxValue).setValue(value)
				.setColorForeground(SanFranciscoApplet.selectedColor)
				.setColorBackground(SanFranciscoApplet.backgroundColor).setValueLabel(" ")
				.setColorCaptionLabel(SanFranciscoApplet.textColor).setLock(true);

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
	 * Set the value label
	 */
	private void updateHandleLabels() {
		lowHandleLabel.setPosition(getXPostionHandleLabel(lowSlider), lowHandleRectangle.getPosition()[1]);
		highHandleLabel.setPosition(getXPostionHandleLabel(highSlider), highHandleRectangle.getPosition()[1]);
		lowHandleLabel.setText(createStringWith1DecimalPlace(lowSlider.getValue()));
		highHandleLabel.setText(createStringWith1DecimalPlace(highSlider.getValue()));
	}

	/**
	 * 
	 * @param pmouseX
	 * @param pmouseY
	 * @return
	 */
	public boolean isOnLowHandle(int pmouseX, int pmouseY) {
		return (pmouseY > lowHandleRectangle.getPosition()[1] - 10
				&& pmouseY < lowHandleRectangle.getPosition()[1] + lowHandleRectangle.getHeight() + 10)
				&& (pmouseX > lowHandleRectangle.getPosition()[0] - 10
						&& pmouseX < lowHandleRectangle.getPosition()[0] + lowHandleRectangle.getWidth() + 10);
	}

	/**
	 * 
	 * @param pmouseX
	 * @param pmouseY
	 * @return
	 */
	public boolean isOnHighHandle(int pmouseX, int pmouseY) {
		return (pmouseY > highHandleRectangle.getPosition()[1] - 10
				&& pmouseY < highHandleRectangle.getPosition()[1] + highHandleRectangle.getHeight() + 10)
				&& (pmouseX > highHandleRectangle.getPosition()[0] - 10
						&& pmouseX < highHandleRectangle.getPosition()[0] + highHandleRectangle.getWidth() + 10);
	}

	/**
	 * 
	 * @param pmouseX
	 * @param pmouseY
	 * @return
	 */
	public boolean isOnRangeHandle(int pmouseX, int pmouseY) {
		return (pmouseY > rangeRectangle.getPosition()[1]
				&& pmouseY < rangeRectangle.getPosition()[1] + rangeRectangle.getHeight())
				&& (pmouseX > rangeRectangle.getPosition()[0]
						&& pmouseX < rangeRectangle.getPosition()[0] + rangeRectangle.getWidth());
	}

	/**
	 * 
	 * @param newYPosition
	 */
	public void changeLowRectanglePostion(float newYPosition) {
		lowHandleRectangle.setColor(lowHandleRectangle.getColor().setForeground(SanFranciscoApplet.draggedColor));
		if (newYPosition > minYRectanglePosition) {
			newYPosition = minYRectanglePosition;
		} else if (newYPosition < highHandleRectangle.getPosition()[1] + highHandleRectangle.getHeight()) {
			newYPosition = (int) (highHandleRectangle.getPosition()[1] + highHandleRectangle.getHeight());
		}
		lowHandleRectangle.setPosition(lowHandleRectangle.getPosition()[0], newYPosition);
		float diff = (minYRectanglePosition + lowHandleRectangle.getHeight()) - maxYRectanglePosition;
		float value = (minYRectanglePosition + lowHandleRectangle.getHeight() - newYPosition) / diff * 10.0f;
		lowSlider.setValue(value);
		updateRange();
	}

	/**
	 * 
	 * @param newYPosition
	 */
	public void changeHighRectanglePostion(float newYPosition) {
		highHandleRectangle.setColor(lowHandleRectangle.getColor().setForeground(SanFranciscoApplet.draggedColor));
		if (newYPosition < maxYRectanglePosition) {
			newYPosition = maxYRectanglePosition;
		} else if (newYPosition + highHandleRectangle.getHeight() > lowHandleRectangle.getPosition()[1]) {
			newYPosition = (int) (lowHandleRectangle.getPosition()[1] - highHandleRectangle.getHeight());
		}
		highHandleRectangle.setPosition(highHandleRectangle.getPosition()[0], newYPosition);
		float diff = (minYRectanglePosition + lowHandleRectangle.getHeight()) - maxYRectanglePosition;
		float value = (minYRectanglePosition + lowHandleRectangle.getHeight() - newYPosition) / diff * 10.0f;
		highSlider.setValue(value);
		updateRange();
	}

	/**
	 * 
	 * @param pmouseY
	 * @param mouseY
	 */
	public void changeRectanglesPostion(float pmouseY, float mouseY) {
		rangeRectangle.setColor(lowHandleRectangle.getColor().setForeground(SanFranciscoApplet.draggedColor));
		// wenn positiv dann runter(höheres y) wenn negativ dann hoch (kleineres y)
		float yMove = mouseY - pmouseY;
		float highY = highHandleRectangle.getPosition()[1] + yMove;
		float lowY = lowHandleRectangle.getPosition()[1] + yMove;
		changeHighRectanglePostion(highY);
		changeLowRectanglePostion(lowY);
	}

	/**
	 * 
	 */
	private void updateRange() {
		rangeRectangle
				.setPosition(highHandleRectangle.getPosition()[0],
						(int) highHandleRectangle.getPosition()[1] + highHandleRectangle.getHeight())
				.setSize(highHandleRectangle.getWidth(),
						(int) (lowHandleRectangle.getPosition()[1] - highHandleRectangle.getPosition()[1])
								- highHandleRectangle.getHeight());
		// nötig da buttons befehl zum neuzeichnen braucht, anstatt .draw
		rangeRectangle.update();
		highHandleRectangle.update();
		lowHandleRectangle.update();
	}

	/**
	 * Create String of form "X.X" or "XX.X"
	 * 
	 * @param f
	 * @return String
	 */
	private String createStringWith1DecimalPlace(float f) {
		String s = "" + f;
		return s.substring(0, s.indexOf('.') + 2);
	}

	/**
	 * Change the color of the slider if the range slider changed
	 */
	public void updateDiagramm() {
		for (int i = 0; i != rankingSliderList.size(); i++) {
			if (Float.valueOf(rankingSliderList.get(i).getName().split(" ")[1].replaceAll(",", ".")) <= getEndValue()
					&& Float.valueOf(
							rankingSliderList.get(i).getName().split(" ")[1].replaceAll(",", ".")) >= getStartValue()) {
				rankingSliderList.set(i, rankingSliderList.get(i).setColorForeground(SanFranciscoApplet.selectedColor));
				rankingSliderList.get(i).setColorForeground(SanFranciscoApplet.selectedColor);
			} else {
				rankingSliderList.set(i,
						rankingSliderList.get(i).setColorForeground(SanFranciscoApplet.unselectedColor));
				rankingSliderList.get(i).setColorForeground(SanFranciscoApplet.unselectedColor);
			}
		}
	}

	/**
	 * Set the sum of the the values of the different lists
	 */
	private Integer setValues(Map<String, Integer> map) {

		Integer value = new Integer(0);
		for (Integer i : map.values()) {
			value += i;
		}
		return value;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	private Integer getMax(Map<String, Integer> map) {

		Integer max = new Integer(0);
		for (Integer i : map.values()) {
			if (max <= i) {
				max = i;
			}
		}
		return max;
	}

	/**
	 * Get the lower value as a rounded double to .0 or .5
	 * 
	 * @return the start value
	 */
	public double getStartValue() {
		return lowSlider.getValue();// Math.round(lowSlider.getValue());
	}

	/**
	 * Get the highter value as a rounded double to .0 or .5
	 * 
	 * @return the end value
	 */
	public double getEndValue() {
		return highSlider.getValue();// Math.round(highSlider.getValue());
	}

	/**
	 * 
	 * @param localSlider
	 * @return
	 */
	private int getXPostionHandleLabel(Slider localSlider) {
		return startDrawX + 25;
	}

	/**
	 * 
	 */
	private void hoverSlider() {
		if (isOnLowHandle(pApplet.mouseX, pApplet.mouseY)) {
			this.lowHandleRectangle
					.setColor(lowHandleRectangle.getColor().setForeground(SanFranciscoApplet.hoverColor));
		}
		if (isOnHighHandle(pApplet.mouseX, pApplet.mouseY)) {
			this.highHandleRectangle
					.setColor(highHandleRectangle.getColor().setForeground(SanFranciscoApplet.hoverColor));
		}
		if (isOnRangeHandle(pApplet.mouseX, pApplet.mouseY)) {
			this.rangeRectangle.setColor(rangeRectangle.getColor().setForeground(SanFranciscoApplet.hoverColor));
		}
		rangeRectangle.update();
		highHandleRectangle.update();
		lowHandleRectangle.update();
	}
}
