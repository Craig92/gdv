package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import controlP5.Button;
import controlP5.Chart;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.RadioButton;
import controlP5.Slider;
import controlP5.Textlabel;
import data.FilmLocationManager;
import main.Configuration;
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

	private Textlabel descriptionDiagramm;

	private List<RadioButton> titleButtonList = new ArrayList<>();
	private List<RadioButton> directorButtonList = new ArrayList<>();
	private List<RadioButton> productionCompanyButtonList = new ArrayList<>();
	private List<RadioButton> distributorButtonList = new ArrayList<>();
	private List<RadioButton> genreButtonList = new ArrayList<>();

	private int titleValue;
	private int directorValue;
	private int productionCompanyValue;
	private int distributorValue;
	private int genreValue;

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
		setupValues();
		setup();
	}

	/**
	 * Set the size and position of the different elements
	 */
	private void setup() {

		int heightPosition = startDrawY + 110;

		cp5 = new ControlP5(pApplet);

		label = new Textlabel(cp5, "Filter", startDrawX, startDrawY + 10, 400, 200)
				.setFont(pApplet.createFont("Georgia", 20)).setColor(pApplet.color(0, 0, 0, 0));

		descriptionLabel1 = new Textlabel(cp5, "Wählen Sie die zu filternden", startDrawX, startDrawY + 35, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		descriptionLabel2 = new Textlabel(cp5, "Parameter aus:", startDrawX, startDrawY + 50, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		selectAllButton = cp5.addButton("Alles auswaehlen").setPosition(startDrawX, startDrawY + 70).setSize(80, 30)
				.setColorForeground(pApplet.color(120)).setColorActive(pApplet.color(255));

		deselectAllButton = cp5.addButton("Alles abwaehlen").setPosition(startDrawX + 100, startDrawY + 70)
				.setSize(80, 30).setColorForeground(pApplet.color(120)).setColorActive(pApplet.color(255));

		descriptionTitelLabel = new Textlabel(cp5, "Genre filtern*:", startDrawX, startDrawY + 110, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		descriptionDiagramm = new Textlabel(cp5, "Aktuelle | Gesamt", startDrawX + 175, startDrawY + 110, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));
		heightPosition = addRadioButtons("Genre", heightPosition + 5);

		descriptionDirectorLabel = new Textlabel(cp5, "Regisseure filtern*:", startDrawX, heightPosition + 5, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		heightPosition = addRadioButtons("Regie", heightPosition + 5);

		descriptionProductionCompanyLabel = new Textlabel(cp5, "Produktionsfirma filtern*:", startDrawX,
				heightPosition + 5, 400, 200).setFont(pApplet.createFont("Georgia", 14))
						.setColor(pApplet.color(0, 0, 0, 0));

		heightPosition = addRadioButtons("Produktion", heightPosition + 5);

		descriptionDistributionLabel = new Textlabel(cp5, "Vertriebsfirma filtern*:", startDrawX, heightPosition + 5,
				400, 200).setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));
		heightPosition = addRadioButtons("Vertrieb", heightPosition + 5);

		setRadioButtonActive(true, 0);
		setRadioButtonActive(true, 1);
		setRadioButtonActive(true, 2);
		setRadioButtonActive(true, 3);
		setRadioButtonActive(true, 4);

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
		// descriptionDiagramm.draw(pApplet);

		pApplet.fill(100);

	}

	/**
	 * Add the new RadioButton to the specific list
	 * 
	 * @param parameter
	 *            the type of the RadioButton
	 */
	public int addRadioButtons(String parameter, int positionY) {

		Map<String, Integer> list = new HashMap<>();
		int size = 20;

		if (parameter.equals("Titel")) {
			list = titleList;
		} else if (parameter.equals("Regie")) {
			list = directorList;
		} else if (parameter.equals("Produktion")) {
			list = productionCompanyList;
		} else if (parameter.equals("Vertrieb")) {
			list = distributorList;
		} else if (parameter.equals("Genre")) {
			list = genreList;
		}

		int limit = Configuration.limit;
		for (Map.Entry<String, Integer> element : list.entrySet()) {
			if (limit != 0) {
				RadioButton button = setRadioButton(parameter + ": " + element.getKey(), positionY, size,
						element.getKey(), 1);
				Slider slider = null;

				if (parameter.equals("Titel")) {
					slider = setSlider("Slider: " + element.getKey(), positionY, size, element.getValue(), titleValue);
					titleSliderList.add(slider);
					titleButtonList.add(button);
				} else if (parameter.equals("Regie")) {
					slider = setSlider("Slider: " + element.getKey(), positionY, size, element.getValue(),
							directorValue);
					directorButtonList.add(button);
					directorSliderList.add(slider);
				} else if (parameter.equals("Produktion")) {
					slider = setSlider("Slider: " + element.getKey(), positionY, size, element.getValue(),
							productionCompanyValue);
					productionCompanySliderList.add(slider);
					productionCompanyButtonList.add(button);
				} else if (parameter.equals("Vertrieb")) {
					slider = setSlider("Slider: " + element.getKey(), positionY, size, element.getValue(),
							distributorValue);
					distributorSliderList.add(slider);
					distributorButtonList.add(button);
				} else if (parameter.equals("Genre")) {
					slider = setSlider("Slider: " + element.getKey(), positionY, size, element.getValue(), genreValue);
					genreSliderList.add(slider);
					genreButtonList.add(button);
				}
				size += 20;
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

		return cp5.addRadioButton(name).setPosition(startDrawX, positionY + size).setSize(18, 18)
				.setBackgroundColor(pApplet.color(190, 190, 190, 100)).setColorValue(pApplet.color(190, 190, 190, 100))
				.setColorForeground(pApplet.color(190, 190, 190, 100)).setColorActive(pApplet.color(46, 139, 87, 100))
				.setColorLabel(pApplet.color(0)).setItemsPerRow(1).addItem(itemName, itemID).setVisible(true)
				.activate(itemID);
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

		return cp5.addSlider(name).setPosition(startDrawX + 175, positionY + size).setSize(75, 20).setRange(0, maxValue)
				.setValue(value).setCaptionLabel(Integer.toString(maxValue))
				.setColorBackground(pApplet.color(255, 255, 255, 75)).setColorValueLabel(pApplet.color(0, 0, 0, 100))
				.setColorCaptionLabel(pApplet.color(0, 0, 0, 100)).setLock(true);
	}

	/**
	 * Set the sum of the the values of the different lists
	 */
	private void setupValues() {

		for (Integer i : titleList.values()) {
			titleValue += i;
		}

		for (Integer i : directorList.values()) {
			directorValue += i;
		}

		for (Integer i : productionCompanyList.values()) {
			productionCompanyValue += i;
		}

		for (Integer i : distributorList.values()) {
			distributorValue += i;
		}

		for (Integer i : genreList.values()) {
			genreValue += i;
		}
	}

	/**
	 * Set the activity status of the RadioButtons
	 * 
	 * @param isSelect
	 *            the activity status of the button
	 * @param typ
	 *            the type of the button
	 */
	public void setRadioButtonActive(boolean isSelect, float typ) {

		if (typ == 0) {
			for (RadioButton button : titleButtonList) {
				if (isSelect) {
					button.activate(button.getItem(0).getName());
				} else {
					button.deactivate(button.getItem(0).getName());
				}
			}
		} else if (typ == 1) {
			for (RadioButton button : directorButtonList) {
				if (isSelect) {
					button.activate(button.getItem(0).getName());
				} else {
					button.deactivate(button.getItem(0).getName());
				}
			}
		} else if (typ == 2) {
			for (RadioButton button : productionCompanyButtonList) {
				if (isSelect) {
					button.activate(button.getItem(0).getName());
				} else {
					button.deactivate(button.getItem(0).getName());
				}
			}
		} else if (typ == 3) {
			for (RadioButton button : distributorButtonList) {
				if (isSelect) {
					button.activate(button.getItem(0).getName());
				} else {
					button.deactivate(button.getItem(0).getName());
				}
			}
		} else if (typ == 4) {
			for (RadioButton button : genreButtonList) {
				if (isSelect) {
					button.activate(button.getItem(0).getName());
				} else {
					button.deactivate(button.getItem(0).getName());
				}
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
			if (parameter.equals("Titel")) {
				list = titleList.keySet();
			} else if (parameter.equals("Regie")) {
				list = directorList.keySet();
			} else if (parameter.equals("Produktion")) {
				list = productionCompanyList.keySet();
			} else if (parameter.equals("Vertrieb")) {
				list = distributorList.keySet();
			} else if (parameter.equals("Genre")) {
				list = genreList.keySet();
			}
			return list;
		} else {
			if (parameter.equals("Titel")) {
				rbList = titleButtonList;
			} else if (parameter.equals("Regie")) {
				rbList = directorButtonList;
			} else if (parameter.equals("Produktion")) {
				rbList = productionCompanyButtonList;
			} else if (parameter.equals("Vertrieb")) {
				rbList = distributorButtonList;
			} else if (parameter.equals("Genre")) {
				rbList = genreButtonList;
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

		if (mouseX >= selectAllButton.getPosition()[0] - 100 && mouseX <= selectAllButton.getPosition()[0] + 100
				&& mouseY >= selectAllButton.getPosition()[1] - 30 && mouseY <= selectAllButton.getPosition()[1] + 30) {
			setRadioButtonActive(true, 0);
			setRadioButtonActive(true, 1);
			setRadioButtonActive(true, 2);
			setRadioButtonActive(true, 3);
			setRadioButtonActive(true, 4);
			selectAll = true;
		} else if (mouseX >= deselectAllButton.getPosition()[0] - 100
				&& mouseX <= deselectAllButton.getPosition()[0] + 100
				&& mouseY >= deselectAllButton.getPosition()[1] - 30
				&& mouseY <= deselectAllButton.getPosition()[1] + 30) {
			setRadioButtonActive(false, 0);
			setRadioButtonActive(false, 1);
			setRadioButtonActive(false, 2);
			setRadioButtonActive(false, 3);
			setRadioButtonActive(false, 4);
			selectAll = false;
		} else if (isSelected(mouseX, mouseY)) {
			if (selectAll) {
				setRadioButtonActive(false, 0);
				setRadioButtonActive(false, 1);
				setRadioButtonActive(false, 2);
				setRadioButtonActive(false, 3);
				setRadioButtonActive(false, 4);
			}
			selectAll = false;
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
		for (RadioButton button : titleButtonList) {
			if (mouseX >= button.getPosition()[0] - 10 && mouseX <= button.getPosition()[0] + 10
					&& mouseY >= button.getPosition()[1] - 10 && mouseY <= button.getPosition()[1] + 10) {
				selected = true;
			}
		}
		for (RadioButton button : directorButtonList) {
			if (mouseX >= button.getPosition()[0] - 10 && mouseX <= button.getPosition()[0] + 10
					&& mouseY >= button.getPosition()[1] - 10 && mouseY <= button.getPosition()[1] + 10) {
				selected = true;
			}
		}
		for (RadioButton button : productionCompanyButtonList) {
			if (mouseX >= button.getPosition()[0] - 10 && mouseX <= button.getPosition()[0] + 10
					&& mouseY >= button.getPosition()[1] - 10 && mouseY <= button.getPosition()[1] + 10) {
				selected = true;
			}
		}
		for (RadioButton button : distributorButtonList) {
			if (mouseX >= button.getPosition()[0] - 10 && mouseX <= button.getPosition()[0] + 10
					&& mouseY >= button.getPosition()[1] - 10 && mouseY <= button.getPosition()[1] + 10) {
				selected = true;
			}
		}
		for (RadioButton button : genreButtonList) {
			if (mouseX >= button.getPosition()[0] - 10 && mouseX <= button.getPosition()[0] + 10
					&& mouseY >= button.getPosition()[1] - 10 && mouseY <= button.getPosition()[1] + 10) {
				selected = true;
			}
		}
		return selected;
	}
}
