package view;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textlabel;
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
	private Textlabel hochschuleLabel;
	private Textlabel yearLabel;

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

		cp5 = new ControlP5(pApplet);

		descriptionIMDBLabel = new Textlabel(cp5, "IMDb-Bewertung filtern:", startDrawX, startDrawY + 110, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		infoLabel1 = new Textlabel(cp5, "* Es kann nur nach den " + Configuration.limit + " häufigsten", startDrawX,
				startDrawY + 35, 400, 200).setFont(pApplet.createFont("Georgia", 14))
						.setColor(pApplet.color(0, 0, 0, 0));
		infoLabel2 = new Textlabel(cp5, "Attributen pro Typ gefiltert werden", startDrawX, startDrawY + 50, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		teamNameLabel = new Textlabel(cp5, "Team: WestCostMovies", startDrawX, startDrawY + height - 90, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		teamMemberLabel1 = new Textlabel(cp5, "Silvia Altrichter, Dominique Bost,", startDrawX,
				startDrawY + height - 75, 400, 200).setFont(pApplet.createFont("Georgia", 14))
						.setColor(pApplet.color(0, 0, 0, 0));

		teamMemberLabel2 = new Textlabel(cp5, "Thorsten Föhringer, Özkan Ünlü,", startDrawX, startDrawY + height - 60,
				400, 200).setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		teamMemberLabel3 = new Textlabel(cp5, "Melissa Zindl", startDrawX, startDrawY + height - 45, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		hochschuleLabel = new Textlabel(cp5, "Hochschule Mannheim", startDrawX, startDrawY + height - 30, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

		yearLabel = new Textlabel(cp5, "Sommersemester 2018", startDrawX, startDrawY + height - 15, 400, 200)
				.setFont(pApplet.createFont("Georgia", 14)).setColor(pApplet.color(0, 0, 0, 0));

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
		hochschuleLabel.draw(pApplet);
		yearLabel.draw(pApplet);
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
