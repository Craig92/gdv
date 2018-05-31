package view;

import java.lang.reflect.Method;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.joda.time.Years;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class YearTimeRangeSlider extends TimeRangeSlider{

	public int totalYears;
	public float widthPerYear;
	private boolean draggedSelectedTimeRange = false;
	private boolean draggedStartHandle = false;
	private boolean draggedEndHandle = false;
	private float handleHeight;
	private PApplet pApplet;
	// For multitouch purposes, i.e. to allow multiple dragging at the same time
	private String startHandleId = null;
	private String endHandleId = null;
	private String timeRangeHandleId = null;
	private static final String MOUSE_ID = "mouse";

	// Event ------------------------------------
	private Method timeUpdatedMethod;

	// ------------------------------------------

	public YearTimeRangeSlider(PApplet p, float x, float y, float width, float height, DateTime startDateTime,
			DateTime DateTime, int aggregationIntervalSeconds) {
		super(p, x, y, width, height, startDateTime, startDateTime, 60);
		
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.currentStartDateTime = startDateTime;
		this.currentEndDateTime = this.currentStartDateTime.plusYears(5);	
		this.pApplet = p;
		this.totalYears = Years.yearsBetween(startDateTime, endDateTime).getYears();
		this.widthPerYear = this.width/this.totalYears;
	}
	/**
	 * Draws this TimeRangeSlider.
	 */
	public void draw() {
		if ((p.frameCount % framesPerInterval == 0) && running) {
			nextAnimationStep();
		}

		drawTimeLine();
		drawStartAndEndTics();

		// Show tics
		if (showTicks) {
			float distancePerTic = widthPerYear * tickIntervalSeconds;
			for (float tx = x; tx < x + width; tx += distancePerTic) {
				drawTic(tx);
			}
		}

		int currentStartYears = Years.yearsBetween(startDateTime, currentStartDateTime).getYears();
		int currentEndYears = Years.yearsBetween(startDateTime, currentEndDateTime).getYears();
		currentStartX = x + widthPerYear * currentStartYears;
		currentEndX = x + widthPerYear * currentEndYears;

		// Show selected time range
		if (showSelectedTimeRange) {
			drawSelectedTimeRange(draggedSelectedTimeRange);
		}

		// Show handles to change time range
		startHandleX = currentStartX;
		endHandleX = currentEndX;
		if (centeredHandle) {
			startHandleX -= handleWidth / 2;
			endHandleX -= handleWidth / 2;
		}

		if (inProximity || alwaysShowHandles) {
			drawHandle(startHandleX, draggedStartHandle, true);
			drawHandle(endHandleX, draggedEndHandle, false);
		}

		// Show labels for selected time range
		if (showTimeRangeLabels) {
			drawTimeRangeLabels();
		}
		// Show labels for complete time
		if (showStartEndTimeLabels) {
			drawStartEndTimeLabels();
		}
	}
	
	protected void drawStartEndTimeLabels() {
		PFont font = FontManager.getInstance().getLabelFont();
		p.fill(0, 200);
		p.textFont(font);

		String startTimeLabel = startDateTime.toString("YYYY");
		int startLabelX = (int) (x - p.textWidth(startTimeLabel) - labelPadding);
		int labelY = (int) (y + font.getSize() / 2 - 3);
		p.text(startTimeLabel, startLabelX, labelY);

		String endTimeLabel = endDateTime.toString("HH:mm");
		int endLabelX = (int) (x + width + labelPadding);
		p.text(endTimeLabel, endLabelX, labelY);
	}

	protected void drawTimeRangeLabels() {
		String timeRangeLabel = currentStartDateTime.toString("YYYY") + " - " + currentEndDateTime.toString("YYYY");
		PFont font = FontManager.getInstance().getLabelFont();
		p.textFont(font);
		int labelX = (int) (currentStartX + (currentEndX - currentStartX) / 2 - p.textWidth(timeRangeLabel) / 2);
		int labelY = (int) (y + font.getSize() + labelPadding / 2);
		drawLabel(timeRangeLabel, labelX, labelY);
	}

	protected void drawLabel(String timeRangeLabels, int labelX, int labelY) {
		p.fill(66);
		p.text(timeRangeLabels, labelX, labelY);
	}

	protected void drawTic(float tx) {
		float tyTop = y - 4;
		float tyBottom = y + 4;
		p.stroke(0, 50);
		p.line(tx, tyTop, tx, tyBottom);
	}

	protected void drawStartAndEndTics() {
		float yTop = y - height / 2;
		float yBottom = y + height / 2;
		p.line(x, yTop, x, yBottom);
		p.line(x + width, yTop, x + width, yBottom);
	}

	protected void drawSelectedTimeRange(boolean highlight) {
		float yTop = y - height / 2;
		if (highlight) {
			p.fill(200, 66, 66, 200);
		} else {
			p.fill(66, 200);
		}
		p.rect(currentStartX, yTop + height / 4, currentEndX - currentStartX, height / 2);
	}

	protected void drawTimeLine() {
		p.stroke(0);
		p.noFill();
		p.line(x, y, x + width, y);
	}

	protected void drawHandle(float handleX, boolean highlight, boolean start) {
		p.fill(250, 220);
		if (highlight) {
			p.stroke(140, 20, 20, 150);
		} else {
			p.stroke(140);
		}

		float handleY = y - height / 2;
		p.rect(handleX, handleY, handleWidth, handleHeight);
		p.line(handleX + 3, handleY + 4, handleX + 3, handleY + 12);
		p.line(handleX + 5, handleY + 4, handleX + 5, handleY + 12);
	}

	// --------------------------------------------------------------

	/**
	 * Goes to next animations step, i.e. slides the time by animationIntervalSeconds.
	 */
	public void nextAnimationStep() {
		currentStartDateTime = currentStartDateTime.plusSeconds(animationIntervalSeconds);
		if (currentStartDateTime.isAfter(endDateTime.minusSeconds(aggregationIntervalSeconds))) {
			currentStartDateTime = startDateTime;
		}
		updateAnimationStep();
	}

	/**
	 * Goes to previous animations step, i.e. slides the time by -animationIntervalSeconds.
	 */
	public void previousAnimationStep() {
		currentStartDateTime = currentStartDateTime.minusSeconds(animationIntervalSeconds);
		if (currentStartDateTime.isBefore(startDateTime)) {
			currentStartDateTime = endDateTime.minusSeconds(aggregationIntervalSeconds);
		}
		updateAnimationStep();
	}

	/**
	 * Goes to next interval step, i.e. slides the time by aggregationIntervalSeconds.
	 */
	public void nextInterval() {
		currentStartDateTime = currentStartDateTime.plusSeconds(aggregationIntervalSeconds);
		if (currentStartDateTime.isAfter(endDateTime.minusSeconds(aggregationIntervalSeconds))) {
			currentStartDateTime = startDateTime;
		}
		updateAnimationStep();
	}

	/**
	 * Goes to previous interval step, i.e. slides the time by -aggregationIntervalSeconds.
	 */
	public void previousInterval() {
		currentStartDateTime = currentStartDateTime.minusSeconds(aggregationIntervalSeconds);
		if (currentStartDateTime.isBefore(startDateTime)) {
			currentStartDateTime = endDateTime.minusSeconds(aggregationIntervalSeconds);
		}
		updateAnimationStep();
	}

	protected void updateAnimationStep() {
		currentEndDateTime = currentStartDateTime.plusSeconds(aggregationIntervalSeconds);

		// Two event mechanisms: Listener or Reflection
		if (listener != null) {
			// Call implemented method of listener

			// FIXME timeUpdated is called too often from TimeRangeSlider (even if not updated)
			listener.timeUpdated(currentStartDateTime, currentEndDateTime);

		} else if (timeUpdatedMethod != null) {
			// Call method of applet if implemented
			try {
				timeUpdatedMethod.invoke(p, new Object[] { currentStartDateTime, currentEndDateTime });
			} catch (Exception e) {
				System.err.println("Disabling timeUpdatedMethod()");
				e.printStackTrace();
				timeUpdatedMethod = null;
			}
		}

	}

	public void playOrPause() {
		running = !running;
	}

	public void play() {
		running = true;
	}

	public void pause() {
		running = false;
	}

	// Interactions -------------------------------------------------

	public void onMoved(int checkX, int checkY) {
		inProximity = checkX > x - inProximityPadding && checkX < x + width + inProximityPadding
				&& checkY > y - height / 2 - inProximityPadding && checkY < y + height / 2 + inProximityPadding;

		// Checks whether the main selector is moved
		draggedSelectedTimeRange = isOverTimeRange(checkX, checkY);

		draggedStartHandle = isOverStartHandle(checkX, checkY);
		draggedEndHandle = isOverEndHandle(checkX, checkY);

		onAdded(checkX, checkY, MOUSE_ID);
	}

	protected boolean isOverTimeRange(int checkX, int checkY) {
		float handlePadding = (centeredHandle) ? handleWidth / 2 : handleWidth;
		float yTop = y - height / 2;
		float yBottom = y + height / 2;
		return checkX > currentStartX + handlePadding && checkX < currentEndX - handlePadding && checkY > yTop
				&& checkY < yBottom;
	}

	protected boolean isOverStartHandle(int checkX, int checkY) {
		float handleY = y - height / 2;
		return checkX > startHandleX && checkX < startHandleX + handleWidth && checkY > handleY
				&& checkY < handleY + handleHeight;
	}

	protected boolean isOverEndHandle(int checkX, int checkY) {
		float handleY = y - height / 2;
		return checkX > endHandleX && checkX < endHandleX + handleWidth && checkY > handleY
				&& checkY < handleY + handleHeight;
	}

	public void onAdded(int checkX, int checkY, String id) {
		// Allow only one interaction at a time; either dragging handles OR timeRange.

		if (isOverStartHandle(checkX, checkY) && !draggedSelectedTimeRange) {
			draggedStartHandle = true;
			startHandleId = id;
		}

		if (isOverEndHandle(checkX, checkY) && !draggedSelectedTimeRange) {
			draggedEndHandle = true;
			endHandleId = id;
		}

		if (isOverTimeRange(checkX, checkY) && !draggedStartHandle && !draggedEndHandle) {
			draggedSelectedTimeRange = true;
			timeRangeHandleId = id;
		}
	}

	public void onReleased(int checkX, int checkY, String id) {
		if (id.equals(startHandleId)) {
			draggedStartHandle = false;
			startHandleId = null;
		}
		if (id.equals(endHandleId)) {
			draggedEndHandle = false;
			endHandleId = null;
		}
		if (id.equals(timeRangeHandleId)) {
			draggedSelectedTimeRange = false;
			timeRangeHandleId = null;
		}
	}

	public void onDragged(float checkX, float checkY, float oldX, float oldY) {
		onDragged(checkX, checkY, oldX, oldY, MOUSE_ID);
	}

	public void onDragged(float checkX, float checkY, float oldX, float oldY, String id) {

		float widthPerTic = widthPerYear * tickIntervalSeconds;
		// float widthPerTic = widthPerSecond * aggregationIntervalSeconds;

		int currentStartSeconds = Seconds.secondsBetween(startDateTime, currentStartDateTime).getSeconds();
		int currentEndSeconds = Seconds.secondsBetween(startDateTime, currentEndDateTime).getSeconds();
		currentStartX = x + widthPerYear * currentStartSeconds;
		currentEndX = x + widthPerYear * currentEndSeconds;

		if (draggedEndHandle && id.equals(endHandleId)) {
			float tx = PApplet.constrain(checkX, x, x + width);
			tx = Math.round((tx - currentStartX) / widthPerTic) * widthPerTic;
			int seconds = Math.round(tx / widthPerYear);
			// Update if larger than first tick, and different to prev value
			if (seconds >= tickIntervalSeconds && seconds != aggregationIntervalSeconds) {
				// if (seconds >= aggregationIntervalSeconds && seconds != aggregationIntervalSeconds) {
				aggregationIntervalSeconds = seconds;
				updateAnimationStep();
			}
		}

		if (draggedStartHandle && id.equals(startHandleId)) {
			float tx = PApplet.constrain(checkX, x, x + width);
			tx = Math.round((currentEndX - tx) / widthPerTic) * widthPerTic;
			int seconds = Math.round(tx / widthPerYear);
			if (seconds >= tickIntervalSeconds && seconds != aggregationIntervalSeconds) {
				// if (seconds >= aggregationIntervalSeconds && seconds != aggregationIntervalSeconds) {
				aggregationIntervalSeconds = seconds;
				currentStartDateTime = currentEndDateTime.minusSeconds(aggregationIntervalSeconds);
				updateAnimationStep();
			}
		}

		if (draggedSelectedTimeRange && timeRangeHandleId != null && timeRangeHandleId.equals(id)) {
			// TODO tn, Oct 7, 2011: Move slider correctly if borders are hit (use onClick and
			// onRelease)

			checkX = Math.round(checkX / widthPerTic) * widthPerTic;
			oldX = Math.round(oldX / widthPerTic) * widthPerTic;
			float diffX = checkX - oldX;

			if (currentStartX + diffX < x || currentEndX + diffX > x + width) {
				diffX = 0;
			}

			int seconds = Math.round(diffX / widthPerYear);
			if (Math.abs(seconds) >= tickIntervalSeconds) {
				// if (Math.abs(seconds) >= aggregationIntervalSeconds) {
				currentStartDateTime = currentStartDateTime.plusSeconds(seconds);
				updateAnimationStep();
			}
		}
	}
}
