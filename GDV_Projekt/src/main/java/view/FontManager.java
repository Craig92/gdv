package view;

import java.io.File;

import processing.core.PApplet;
import processing.core.PFont;

public class FontManager {

	private static FontManager _instance = null;

	private static PFont labelFont;
	private static PFont miniLabelFont;

	private static PFont headlineFont;

	protected FontManager(PApplet p) {
		//labelFont = p.loadFont("Miso-Light-15.vlw");
		labelFont = p.loadFont(new File("").getAbsolutePath()+"/src/main/resources/fonts/Helvetica-12.vlw");
		miniLabelFont = p.loadFont(new File("").getAbsolutePath()+"/src/main/resources/fonts/Helvetica-10.vlw");
		headlineFont = p.loadFont(new File("").getAbsolutePath()+"/src/main/resources/fonts/Miso-20.vlw");
	}

	// Neat trick: FontManager needs PApplet instance in order to load fonts.
	// Thus, optional parameter has to be provided on first initialization.
	public static FontManager getInstance(PApplet... papplets) {
		if (_instance == null) {
			if (papplets.length > 0) {
				_instance = new FontManager(papplets[0]);
			} else {
				throw new IllegalArgumentException("First getInstance call needs PApplet");
			}
		}
		return _instance;
	}

	public PFont getLabelFont() {
		return labelFont;
	}
	public PFont getMiniLabelFont() {
		return miniLabelFont;
	}
	
	public PFont getHeadlineFont() {
		return headlineFont;
	}

}
