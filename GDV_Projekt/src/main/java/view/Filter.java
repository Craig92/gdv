package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.RadioButton;
import controlP5.Textlabel;
import data.FilmLocationManager;
import main.Configuration;
import processing.core.PApplet;

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
	private float typ;

	/**
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
	 * 
	 */
	private void setup() {
		cp5 = new ControlP5(pApplet);

		label = new Textlabel(cp5, "Filter", (int) (Configuration.windowWidth * 0.85), 10, 400, 200)
				.setFont(pApplet.createFont("Georgia", 20)).setColor(pApplet.color(0, 0, 0, 0));

		descriptionLabel = new Textlabel(cp5, "Wählen Sie die zu filternden Parameter aus:",
				(int) (Configuration.windowWidth * 0.85), 35, 400, 200).setFont(pApplet.createFont("Georgia", 14))
						.setColor(pApplet.color(0, 0, 0, 0));

		filterButton = cp5.addButton("Filtern").setPosition((int) (Configuration.windowWidth * 0.85), 60)
				.setSize(80, 30).setColorForeground(pApplet.color(120)).setColorActive(pApplet.color(255));

		resetButton = cp5.addButton("Zurücksetzen").setPosition((int) (Configuration.windowWidth * 0.85) + 100, 60)
				.setSize(80, 30).setColorForeground(pApplet.color(120)).setColorActive(pApplet.color(255));

		filterDropdown = cp5.addDropdownList("Filtern nach...")
				.setPosition((int) (Configuration.windowWidth * 0.85), 120).setSize(200, 400)
				.setColorForeground(pApplet.color(120)).setColorActive(pApplet.color(255));

		filterDropdown.addItem("Titel", 1);
		filterDropdown.addItem("Regie", 2);
		filterDropdown.addItem("Produktion", 3);
		filterDropdown.addItem("Vertrieb", 4);

		selectAllButton = cp5.addButton("Alle auswählen").setPosition((int) (Configuration.windowWidth * 0.85), 160)
				.setSize(80, 30).setColorForeground(pApplet.color(120)).setColorActive(pApplet.color(255))
				.setVisible(false);

		deselectAllButton = cp5.addButton("Alle abwählen")
				.setPosition((int) (Configuration.windowWidth * 0.85) + 100, 160).setSize(80, 30)
				.setColorForeground(pApplet.color(120)).setColorActive(pApplet.color(255)).setVisible(false);

		int size = 20;
		for (String title : titleList) {
			RadioButton button = setRadioButton("Titel: " + title, size, title, 1);
			size += 20;
			titleButtonList.add(button);
		}

		size = 20;
		for (String director : directorList) {
			RadioButton button = setRadioButton("Regie: " + director, size, director, 1);
			size += 20;
			directorButtonList.add(button);
		}

		size = 20;
		for (String productionCompany : productionCompanyList) {
			RadioButton button = setRadioButton("Produktion: " + productionCompany, size, productionCompany, 1);
			size += 20;
			productionCompanyButtonList.add(button);
		}

		size = 20;
		for (String distributor : distributorList) {
			RadioButton button = setRadioButton("Vertrieb: " + distributor, size, distributor, 1);
			size += 20;
			distributorButtonList.add(button);
		}

	}

	public void draw() {

		label.draw(pApplet);
		descriptionLabel.draw(pApplet);

	}

	public void controlEvent(ControlEvent event) {

		if (event.isFrom(filterButton)) {
			System.out.println("FilterButton");
		} else if (event.isFrom(resetButton)) {
			System.out.println("ResteButton");
		} else if (event.isFrom(filterDropdown)) {
			float typ = event.getController().getValue();
			System.out.println(typ);
			setVisibility(typ);
		} else if (event.isFrom(selectAllButton)) {
			System.out.println("selectAllButton");
			setRadioButtonActive(true);
		} else if (event.isFrom(deselectAllButton)) {
			System.out.println("deselectAllButton");
			setRadioButtonActive(false);
		} else {
			System.out.println("keine Aktion" + event.getName());

		}
	}

	/**
	 * 
	 * @param mouseX
	 * @param mouseY
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
	 * 
	 * @param button
	 * @param size
	 * @return
	 */
	private RadioButton setRadioButton(String name, int size, String itemName, int itemID) {

		return cp5.addRadioButton(name).setPosition((int) (Configuration.windowWidth * 0.85), 200 + size)
				.setSize(20, 20).setBackgroundColor(pApplet.color(190, 190, 190, 100))
				.setColorValue(pApplet.color(190, 190, 190, 100)).setColorForeground(pApplet.color(190, 190, 190, 100))
				.setColorActive(pApplet.color(46, 139, 87, 100)).setColorLabel(pApplet.color(0)).setItemsPerRow(1)
				.addItem(itemName, itemID).setVisible(false);
	}

	/**
	 * 
	 * @param isSelect
	 */
	private void setRadioButtonActive(boolean isSelect) {

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
	 * 
	 * @param typ
	 */
	private void setVisibility(float typ) {
		this.typ = typ;
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
	}
}
