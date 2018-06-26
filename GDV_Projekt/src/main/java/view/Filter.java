package view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import controlP5.Button;
import controlP5.ControlP5;
import controlP5.RadioButton;
import controlP5.Slider;
import controlP5.Textlabel;
import data.FilmLocationManager;
import main.Configuration;
import main.SanFranciscoApplet;
import processing.core.PApplet;

@SuppressWarnings("unused")
public class Filter {

	private PApplet pApplet;
	private int startDrawX;
	private int startDrawY;
	private int width;
	private int height;

	private ControlP5 cp5;

	private Textlabel label;
	private Textlabel descriptionLabel1;
	private Textlabel descriptionLabel2;
	private Textlabel descriptionTitelLabel;
	private Textlabel descriptionDirectorLabel;
	private Textlabel descriptionProductionCompanyLabel;
	private Textlabel descriptionDistributionLabel;

	private Textlabel descriptionTitleDiagramm;
	private Textlabel descriptionDirectorDiagramm;
	private Textlabel descriptionProductionCompanyDiagramm;
	private Textlabel descriptionDistributionDiagramm;

	private List<RadioButton> titleButtonList = new ArrayList<>();
	private List<RadioButton> directorButtonList = new ArrayList<>();
	private List<RadioButton> productionCompanyButtonList = new ArrayList<>();
	private List<RadioButton> distributorButtonList = new ArrayList<>();
	private List<RadioButton> genreButtonList = new ArrayList<>();

	private List<Slider> titleSliderList = new ArrayList<>();
	private List<Slider> directorSliderList = new ArrayList<>();
	private List<Slider> productionCompanySliderList = new ArrayList<>();
	private List<Slider> distributorSliderList = new ArrayList<>();
	private List<Slider> genreSliderList = new ArrayList<>();

	private Button selectAllButton;
	private Button deselectAllButton;
	private boolean selectAll = true;

	private FilmLocationManager manager = FilmLocationManager.getInstance();
	private Map<String, Integer> titleList = manager.getTitleList();
	private Map<String, Integer> directorList = manager.getDirectorList();
	private Map<String, Integer> productionCompanyList = manager.getProductionCompanyList();
	private Map<String, Integer> distributorList = manager.getDistributorList();
	private Map<String, Integer> genreList = manager.getGenreList();

	private Integer titleValue = setupValues(titleList);
	private Integer directorValue = setupValues(directorList);
	private Integer productionCompanyValue = setupValues(productionCompanyList);
	private Integer distributorValue = setupValues(distributorList);
	private Integer genreValue = setupValues(genreList);

	private Integer titleMaxValue = titleList.values().stream().findFirst().get();
	private Integer directorMaxValue = directorList.values().stream().findFirst().get();
	private Integer productionCompanyMaxValue = productionCompanyList.values().stream().findFirst().get();
	private Integer distributorMaxValue = distributorList.values().stream().findFirst().get();
	private Integer genreMaxValue = genreList.values().stream().findFirst().get();

	/**
	 * Constructor
	 * 
	 * @param pApplet
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Filter(PApplet pApplet, int x, int y, int width, int height) {

		this.pApplet = pApplet;
		this.startDrawX = x;
		this.startDrawY = y;
		this.width = width;
		this.height = height;
		setup();
	}

	/**
	 * Set the size and position of the different elements
	 * 
	 * @param distributionValue
	 */
	private void setup() {

		int currentY = startDrawY + 140;
		cp5 = new ControlP5(pApplet);
		cp5.setFont(SanFranciscoApplet.buttonFont);

		// header line
		label = new Textlabel(cp5, "Filter", startDrawX, startDrawY + 10, 400, 200)
				.setFont(SanFranciscoApplet.headerFont).setColor(SanFranciscoApplet.textColor);
		descriptionLabel1 = new Textlabel(cp5, "Wählen Sie die zu filternden", startDrawX, startDrawY + 35, 400, 200)
				.setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		descriptionLabel2 = new Textlabel(cp5, "Parameter aus:", startDrawX, startDrawY + 50, 400, 200)
				.setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);

		// buttons
		if (Configuration.iExpo) {
			selectAllButton = cp5.addButton("Alles auswaehlen").setPosition(startDrawX, startDrawY + 80)
					.setSize(160, 30).setColorForeground(SanFranciscoApplet.buttonColor)
					.setColorActive(SanFranciscoApplet.buttonActivColor);
			deselectAllButton = cp5.addButton("Alles abwaehlen").setPosition(startDrawX + 200, startDrawY + 80)
					.setSize(160, 30).setColorForeground(SanFranciscoApplet.buttonColor)
					.setColorActive(SanFranciscoApplet.buttonActivColor);
		} else {
			selectAllButton = cp5.addButton("Alles auswaehlen").setPosition(startDrawX, startDrawY + 70).setSize(80, 30)
					.setColorForeground(SanFranciscoApplet.buttonColor)
					.setColorActive(SanFranciscoApplet.buttonActivColor);
			deselectAllButton = cp5.addButton("Alles abwaehlen").setPosition(startDrawX + 100, startDrawY + 70)
					.setSize(80, 30).setColorForeground(SanFranciscoApplet.buttonColor)
					.setColorActive(SanFranciscoApplet.buttonActivColor);
		}

		// genre filter
		descriptionTitelLabel = new Textlabel(cp5, "Genre filtern*:", startDrawX - 5, startDrawY + 130, 400, 200)
				.setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		descriptionTitleDiagramm = new Textlabel(cp5, "Gesamt: " + genreValue, startDrawX + 250 -5, startDrawY + 130, 400,
				200).setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		currentY = addFilterElements("Genre", currentY + 5, genreList, genreMaxValue, genreValue);

		// director filter
		descriptionDirectorLabel = new Textlabel(cp5, "Regisseure filtern*:", startDrawX - 5, currentY + 5, 400, 200)
				.setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		descriptionDirectorDiagramm = new Textlabel(cp5, "Gesamt: " + directorValue, startDrawX + 250 - 5, currentY + 5,
				400, 200).setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		currentY = addFilterElements("Regie", currentY + 5, directorList, directorMaxValue, directorValue);

		// production comany filter
		descriptionProductionCompanyLabel = new Textlabel(cp5, "Produktionsfirma filtern*:", startDrawX - 5, currentY + 5,
				400, 200).setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		descriptionProductionCompanyDiagramm = new Textlabel(cp5, "Gesamt: " + productionCompanyValue, startDrawX + 250 - 5,
				currentY + 5, 400, 200).setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		currentY = addFilterElements("Produktion", currentY + 5, productionCompanyList, productionCompanyMaxValue,
				productionCompanyValue);

		// distributor filter
		descriptionDistributionLabel = new Textlabel(cp5, "Vertriebsfirma filtern*:", startDrawX - 5, currentY + 5, 400,
				200).setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		descriptionDistributionDiagramm = new Textlabel(cp5, "Gesamt: " + distributorValue, startDrawX + 250 - 5,
				currentY + 5, 400, 200).setFont(SanFranciscoApplet.textFont).setColor(SanFranciscoApplet.textColor);
		currentY = addFilterElements("Vertrieb", currentY + 5, distributorList, distributorMaxValue, distributorValue);

		setRadioButtonActive(true);
		setSliderAktiv(true);

	}

	/**
	 * Draw the different elements
	 */
	public void draw() {

		label.draw(pApplet);
		descriptionLabel1.draw(pApplet);
		descriptionLabel2.draw(pApplet);
		descriptionTitelLabel.draw(pApplet);
		descriptionDirectorLabel.draw(pApplet);
		descriptionProductionCompanyLabel.draw(pApplet);
		descriptionDistributionLabel.draw(pApplet);
		descriptionTitleDiagramm.draw(pApplet);
		descriptionDirectorDiagramm.draw(pApplet);
		descriptionProductionCompanyDiagramm.draw(pApplet);
		descriptionDistributionDiagramm.draw(pApplet);

		pApplet.fill(100);

	}

	/**
	 * Add the new RadioButton to the specific list
	 * 
	 * @param parameter
	 *            the type of the RadioButton
	 */
	public int addFilterElements(String parameter, int positionY, Map<String, Integer> list, Integer maxValue,
			Integer currentValue) {

		int limit = Configuration.limit;
		int size = 20;
		RadioButton button = null;
		Slider slider = null;

		for (Map.Entry<String, Integer> element : list.entrySet()) {
			if (limit != 0) {

				button = setRadioButton(parameter + ": " + element.getKey(), positionY, size, element.getKey(), 1);
				slider = setSlider("Slider: " + element.getKey(), positionY, size, element.getValue(), maxValue,
						currentValue);

				switch (parameter) {
				case "Titel":
					titleSliderList.add(slider);
					titleButtonList.add(button);
					break;
				case "Genre":
					genreSliderList.add(slider);
					genreButtonList.add(button);
					break;
				case "Regie":
					directorButtonList.add(button);
					directorSliderList.add(slider);
					break;
				case "Produktion":
					productionCompanySliderList.add(slider);
					productionCompanyButtonList.add(button);
					break;
				case "Vertrieb":
					distributorSliderList.add(slider);
					distributorButtonList.add(button);
					break;
				}

				size += (Configuration.iExpo) ? 40 : 20;
				limit--;
			}
		}

		return positionY + size;
	}

	/**
	 * Set the properties of the RadioButton
	 * 
	 * @param name
	 *            the name of the button
	 * @param size
	 *            the y size of the button
	 * @param positionY
	 *            the y position of the button
	 * @param itemName
	 *            the item name of the button
	 * @param itemID
	 *            the id of the item
	 * @return the RadioButton
	 */
	private RadioButton setRadioButton(String name, int size, int positionY, String itemName, int itemID) {

		if (Configuration.iExpo) {
			return cp5.addRadioButton(name).setPosition(startDrawX, positionY + size).setSize(36, 36)
					.setColorLabel(SanFranciscoApplet.textColor).setItemsPerRow(1).addItem(itemName, itemID)
					.setVisible(true).activate(itemID).setColorActive(SanFranciscoApplet.selectedColor)
					.setColorBackground(SanFranciscoApplet.unselectedColor);
		} else {
			return cp5.addRadioButton(name).setPosition(startDrawX, positionY + size).setSize(18, 18)
					.setColorLabel(SanFranciscoApplet.textColor).setItemsPerRow(1).addItem(itemName, itemID)
					.setVisible(true).activate(itemID).setColorActive(SanFranciscoApplet.selectedColor)
					.setColorBackground(SanFranciscoApplet.unselectedColor);
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
	private Slider setSlider(String name, int size, int positionY, int value, int maxValue, int completeValue) {

		if (Configuration.iExpo) {
			return cp5.addSlider(name).setPosition(startDrawX + 250, positionY + size).setSize(75, 40)
					.setRange(0, maxValue).setValue(value).setCaptionLabel(Integer.toString(value)).setValueLabel(" ")
					.setColorForeground(SanFranciscoApplet.selectedColor)
					.setColorBackground(SanFranciscoApplet.backgroundColor)
					.setColorCaptionLabel(SanFranciscoApplet.textColor).setLock(true);
		} else {
			return cp5.addSlider(name).setPosition(startDrawX + 175, positionY + size).setSize(75, 20)
					.setRange(0, maxValue).setValue(value).setCaptionLabel(Integer.toString(value)).setValueLabel(" ")
					.setColorForeground(SanFranciscoApplet.selectedColor)
					.setColorBackground(SanFranciscoApplet.backgroundColor)
					.setColorCaptionLabel(SanFranciscoApplet.textColor).setLock(true);
		}
	}

	/**
	 * Set the sum of the the values of the different lists
	 */
	private Integer setupValues(Map<String, Integer> map) {

		Integer value = new Integer(0);
		for (Integer i : map.values()) {
			value += i;
		}
		return value;
	}

	/**
	 * Set the activity status of the RadioButtons
	 * 
	 * @param isSelect
	 *            the activity status of the button
	 * @param typ
	 *            the type of the button
	 */
	public void setRadioButtonActive(boolean isSelect) {

		List<RadioButton> buttonList = new ArrayList<>();
		buttonList.addAll(titleButtonList);
		buttonList.addAll(directorButtonList);
		buttonList.addAll(productionCompanyButtonList);
		buttonList.addAll(distributorButtonList);
		buttonList.addAll(genreButtonList);

		for (int i = 0; i != buttonList.size(); i++) {
			if (isSelect) {
				buttonList.get(i).activate(buttonList.get(i).getItem(0).getName());
			} else {
				buttonList.get(i).deactivate(buttonList.get(i).getItem(0).getName());
			}
		}
	}

	/**
	 * 
	 * @param isSelect
	 * @param typ
	 */
	public void setSliderAktiv(boolean isSelect) {

		List<Slider> sliderList = new ArrayList<>();
		sliderList.addAll(titleSliderList);
		sliderList.addAll(directorSliderList);
		sliderList.addAll(productionCompanySliderList);
		sliderList.addAll(distributorSliderList);
		sliderList.addAll(genreSliderList);
		for (int i = 0; i != sliderList.size(); i++) {
			if (isSelect) {
				sliderList.get(i).setColorForeground(SanFranciscoApplet.selectedColor);
			} else {
				sliderList.get(i).setColorForeground(SanFranciscoApplet.unselectedColor);
			}
		}
	}

	/**
	 * Get the selected parameter of the RadioButtons
	 * 
	 * @param parameter
	 *            the typ of the parameter
	 * @return list with the name of the selected items
	 */
	public Set<String> getSelectedParameterList(String parameter) {

		Set<String> list = new HashSet<>();
		List<RadioButton> rbList = new ArrayList<>();
		if (selectAll) {
			switch (parameter) {
			case "Titel":
				list = titleList.keySet();
				break;
			case "Genre":
				list = genreList.keySet();
				break;
			case "Regie":
				list = directorList.keySet();
				break;
			case "Produktion":
				rbList = productionCompanyButtonList;
				break;
			case "Vertrieb":
				list = distributorList.keySet();
				break;
			}
			return list;
		} else {
			switch (parameter) {
			case "Titel":
				rbList = titleButtonList;
				break;
			case "Genre":
				rbList = genreButtonList;
				break;
			case "Regie":
				rbList = directorButtonList;
				break;
			case "Produktion":
				rbList = productionCompanyButtonList;
				break;
			case "Vertrieb":
				rbList = distributorButtonList;
				break;
			}

			for (RadioButton button : rbList) {
				if (button.getItem(0).getState()) {
					list.add(button.getItem(0).getName());
				}
			}
			return list;
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

		if (Configuration.iExpo) {
			if (mouseX >= selectAllButton.getPosition()[0] - 200 && mouseX <= selectAllButton.getPosition()[0] + 200
					&& mouseY >= selectAllButton.getPosition()[1] - 30
					&& mouseY <= selectAllButton.getPosition()[1] + 30) {
				setRadioButtonActive(true);
				setSliderAktiv(true);
				selectAll = true;
			} else if (mouseX >= deselectAllButton.getPosition()[0] - 200
					&& mouseX <= deselectAllButton.getPosition()[0] + 200
					&& mouseY >= deselectAllButton.getPosition()[1] - 30
					&& mouseY <= deselectAllButton.getPosition()[1] + 30) {
				setRadioButtonActive(false);
				setSliderAktiv(false);
				selectAll = false;
			} else if (isSelected(mouseX, mouseY) && selectAll) {
				setRadioButtonActive(false);
				setSliderAktiv(false);
				selectAll = false;
			} else if (isSelected(mouseX, mouseY) && !selectAll) {
				updateDiagramm(mouseX, mouseY);
				selectAll = false;
			}
		} else {
			if (mouseX >= selectAllButton.getPosition()[0] - 100 && mouseX <= selectAllButton.getPosition()[0] + 100
					&& mouseY >= selectAllButton.getPosition()[1] - 30
					&& mouseY <= selectAllButton.getPosition()[1] + 30) {
				setRadioButtonActive(true);
				setSliderAktiv(true);
				selectAll = true;
			} else if (mouseX >= deselectAllButton.getPosition()[0] - 100
					&& mouseX <= deselectAllButton.getPosition()[0] + 100
					&& mouseY >= deselectAllButton.getPosition()[1] - 30
					&& mouseY <= deselectAllButton.getPosition()[1] + 30) {
				setRadioButtonActive(false);
				setSliderAktiv(false);
				selectAll = false;
			} else if (isSelected(mouseX, mouseY) && selectAll) {
				setRadioButtonActive(false);
				setSliderAktiv(false);
				selectAll = false;
			} else if (isSelected(mouseX, mouseY) && !selectAll) {
				updateDiagramm(mouseX, mouseY);
				selectAll = false;
			}
		}
	}

	/**
	 * Check if the button lists are selected
	 * 
	 * @param mouseX
	 *            the x position of the mouse
	 * @param mouseY
	 *            the y position of the mouse
	 * @return
	 */
	private boolean isSelected(int mouseX, int mouseY) {

		boolean selected = false;
		List<RadioButton> buttonList = new ArrayList<>();
		buttonList.addAll(titleButtonList);
		buttonList.addAll(directorButtonList);
		buttonList.addAll(productionCompanyButtonList);
		buttonList.addAll(distributorButtonList);
		buttonList.addAll(genreButtonList);

		for (RadioButton button : buttonList) {
			if (button.getItem(0).getState()) {
				selected = true;
			}
		}
		return selected;
	}

	/**
	 * Change the color of the slider if a button is clicked
	 * 
	 * @param mouseX
	 *            the x position of the mouse
	 * @param mouseY
	 *            the y position of the mouse
	 */
	private void updateDiagramm(int mouseX, int mouseY) {

		List<RadioButton> buttonList = new ArrayList<>();
		buttonList.addAll(titleButtonList);
		buttonList.addAll(directorButtonList);
		buttonList.addAll(productionCompanyButtonList);
		buttonList.addAll(distributorButtonList);
		buttonList.addAll(genreButtonList);

		List<Slider> sliderList = new ArrayList<>();
		sliderList.addAll(titleSliderList);
		sliderList.addAll(directorSliderList);
		sliderList.addAll(productionCompanySliderList);
		sliderList.addAll(distributorSliderList);
		sliderList.addAll(genreSliderList);

		for (int i = 0; i != buttonList.size(); i++) {
			if (buttonList.get(i).getItem(0).getState()) {
				sliderList.set(i, sliderList.get(i).setColorForeground(SanFranciscoApplet.selectedColor));
				sliderList.get(i).setColorForeground(SanFranciscoApplet.selectedColor);
			} else {
				sliderList.set(i, sliderList.get(i).setColorForeground(SanFranciscoApplet.unselectedColor));
				sliderList.get(i).setColorForeground(SanFranciscoApplet.unselectedColor);
			}
		}
	}
}
