package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.RadioButton;
import controlP5.Textlabel;
import data.FilmLocationManager;
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
	private Textlabel descriptionLabel;
	private List<RadioButton> titleButtonList = new ArrayList<>();
	private List<RadioButton> directorButtonList = new ArrayList<>();
	private List<RadioButton> productionCompanyButtonList = new ArrayList<>();
	private List<RadioButton> distributorButtonList = new ArrayList<>();

	private Button filterButton;
	private Button resetButton;
	private Button selectAllButton;
	private Button deselectAllButton;

	private DropdownList filterDropdown;

	private FilmLocationManager manager = FilmLocationManager.getInstance();
	private Set<String> titleList = manager.getTitleList();
	private Set<String> directorList = manager.getDirectorList();
	private Set<String> productionCompanyList = manager.getProductionCompanyList();
	private Set<String> distributorList = manager.getDistributorList();

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
	 */
	private void setup() {

		cp5 = new ControlP5(pApplet);

		label = new Textlabel(cp5, "Filter", startDrawX, startDrawY + 10, 400, 200)
				.setFont(pApplet.createFont("Georgia", 20)).setColor(pApplet.color(0, 0, 0, 0));

		descriptionLabel = new Textlabel(cp5, "Wählen Sie die zu filternden Parameter aus:", startDrawX,
				startDrawY + 35, 400, 200).setFont(pApplet.createFont("Georgia", 14))
						.setColor(pApplet.color(0, 0, 0, 0));

		filterButton = cp5.addButton("Filtern").setPosition(startDrawX, startDrawY + 60).setSize(80, 30)
				.setColorForeground(pApplet.color(120)).setColorActive(pApplet.color(255));

		resetButton = cp5.addButton("Zurücksetzen").setPosition(startDrawX + 100, startDrawY + 60).setSize(80, 30)
				.setColorForeground(pApplet.color(120)).setColorActive(pApplet.color(255));

		filterDropdown = cp5.addDropdownList("Filtern nach...").setPosition(startDrawX, startDrawY + 120)
				.setSize(200, 400).setColorForeground(pApplet.color(120)).setColorActive(pApplet.color(255));

		filterDropdown.addItem("Titel", 1);
		filterDropdown.addItem("Regie", 2);
		filterDropdown.addItem("Produktion", 3);
		filterDropdown.addItem("Vertrieb", 4);

		selectAllButton = cp5.addButton("Alle auswählen").setPosition(startDrawX, startDrawY + 160).setSize(80, 30)
				.setColorForeground(pApplet.color(120)).setColorActive(pApplet.color(255)).setVisible(false);

		deselectAllButton = cp5.addButton("Alle abwählen").setPosition(startDrawX + 100, startDrawY + 160)
				.setSize(80, 30).setColorForeground(pApplet.color(120)).setColorActive(pApplet.color(255))
				.setVisible(false);

		addRadioButtons("Titel");
		addRadioButtons("Regie");
		addRadioButtons("Produktion");
		addRadioButtons("Vertrieb");

	}

	/**
	 * Draw the different elements
	 */
	public void draw() {

		label.draw(pApplet);
		descriptionLabel.draw(pApplet);

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

		if (filterDropdown.isOpen()) {
			selectAllButton.setVisible(false);
			deselectAllButton.setVisible(false);
		} else {
			selectAllButton.setVisible(true);
			deselectAllButton.setVisible(true);
		}
	}

	/**
	 * Add the new RadioButton to the specific list
	 * 
	 * @param parameter
	 *            the type of the RadioButton
	 */
	public void addRadioButtons(String parameter) {

		Set<String> list = new TreeSet<>();
		int size = 20;

		if (parameter.equals("Titel")) {
			list = titleList;
		} else if (parameter.equals("Regie")) {
			list = directorList;
		} else if (parameter.equals("Produktion")) {
			list = productionCompanyList;
		} else if (parameter.equals("Vertrieb")) {
			list = distributorList;
		}

		for (String element : list) {
			RadioButton button = setRadioButton(parameter + ": " + element, size, element, 1);
			size += 20;
			if (parameter.equals("Titel")) {
				titleButtonList.add(button);
			} else if (parameter.equals("Regie")) {
				directorButtonList.add(button);
			} else if (parameter.equals("Produktion")) {
				productionCompanyButtonList.add(button);
			} else if (parameter.equals("Vertrieb")) {
				distributorButtonList.add(button);
			}

		}
	}

	/**
	 * Set the properties of the RadioButton
	 * 
	 * @param name
	 *            the name of the button
	 * @param size
	 *            the y size of the button
	 * @param itemName
	 *            the item name of the button
	 * @param itemID
	 *            the id of the item
	 * @return the RadioButton
	 */
	private RadioButton setRadioButton(String name, int size, String itemName, int itemID) {

		return cp5.addRadioButton(name).setPosition(startDrawX, 200 + size).setSize(20, 20)
				.setBackgroundColor(pApplet.color(190, 190, 190, 100)).setColorValue(pApplet.color(190, 190, 190, 100))
				.setColorForeground(pApplet.color(190, 190, 190, 100)).setColorActive(pApplet.color(46, 139, 87, 100))
				.setColorLabel(pApplet.color(0)).setItemsPerRow(1).addItem(itemName, itemID).setVisible(false);
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
		}
	}

	/**
	 * Get the selected parameter of the RadioButtons
	 * 
	 * @param parameter
	 *            the typ of the parameter
	 * @return list with the name of the selected items
	 */
	public List<String> getSelectedParameterList(String parameter) {

		List<String> list = new ArrayList<>();
		List<RadioButton> rbList = new ArrayList<>();

		if (parameter.equals("Titel")) {
			rbList = titleButtonList;
		} else if (parameter.equals("Regie")) {
			rbList = directorButtonList;
		} else if (parameter.equals("Produktion")) {
			rbList = productionCompanyButtonList;
		} else if (parameter.equals("Vertrieb")) {
			rbList = distributorButtonList;
		}

		for (RadioButton button : rbList) {
			if (button.getItem(0).getState()) {
				list.add(button.getItem(0).getName());
			}
		}

		return list;
	}

	/**
	 * Set the visibility of the RadioButton
	 * 
	 * @param type
	 *            the type of the button
	 * @return the current type
	 */
	public float setVisibility(float typ) {

		for (RadioButton button : titleButtonList) {
			if (typ == 0) {
				button.setVisible(true);
			} else {
				button.setVisible(false);
			}
		}
		for (RadioButton button : directorButtonList) {
			if (typ == 1) {
				button.setVisible(true);
			} else {
				button.setVisible(false);
			}
		}
		for (RadioButton button : productionCompanyButtonList) {
			if (typ == 2) {
				button.setVisible(true);
			} else {
				button.setVisible(false);
			}
		}

		for (RadioButton button : distributorButtonList) {
			if (typ == 3) {
				button.setVisible(true);
			} else {
				button.setVisible(false);
			}
		}
		return typ;
	}
}
