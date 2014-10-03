// (x-3)/x/(x-5)/(x-3)

import java.text.DecimalFormat;
import java.util.ArrayList;


import java.awt.Color;
import java.awt.Graphics;


public class GraphingCalculator {

	private MathEvaluator m;
	private String equation;

	private ArrayList<Point> function;
	private ArrayList<Point> firstDeriv;
	private ArrayList<Point> secondDeriv;

	private int windowWidth;
	private int windowHeight;
	private Point origin;
	private int originX;
	private int originY;

	private double scale; 		// # of pixels per point
	private double[] domain;
	private double[] range;
	private double width;
	private double height;

	private ArrayList<Point> minima;
	private ArrayList<Point> maxima;
	private ArrayList<Point> POImins;
	private ArrayList<Point> POImaxs;
	private ArrayList<Double> vertAsymptotes;
	private ArrayList<Point> holes;
	private ArrayList<Double> horizAsymptotes;

	private enum POI{min, max};
	private enum Extremum{min, max};
	private enum Discontinuity{hole, asymptote};

	/**
	 * Creates a GraphingCalculator object.
	 * @param equation the string of the equation
	 */
	public GraphingCalculator(String equation, int width, int height) {
		m = new MathEvaluator(equation);
		this.equation = equation;

		function = new ArrayList<Point>();
		firstDeriv = new ArrayList<Point>();
		secondDeriv = new ArrayList<Point>();	
		windowWidth = width;
		windowHeight = height;

		origin = new Point(new Double(width / 2), new Double(height / 2));
		originX = (int) origin.getX().doubleValue();
		originY = (int) origin.getY().doubleValue();

		scale = 20;
		domain = new double[2];
		domain[0] = -(double) width / 2.0 / scale;
		domain[1] = (double) width / 2.0 / scale;
		range = new double[2];
		range[0] = -(double) height / 2.0 / scale;
		range[0] = (double) height / 2.0 / scale;
		this.width = domain[1] - domain[0];
		this.height = range[1] - range[0];

		minima = new ArrayList<Point>();
		maxima = new ArrayList<Point>();
		POImins = new ArrayList<Point>();
		POImaxs = new ArrayList<Point>();
		vertAsymptotes = new ArrayList<Double>();
		holes = new ArrayList<Point>();
		horizAsymptotes = new ArrayList<Double>();
	}

	/**
	 * Makes scale 10 pixels per unit larger.
	 * Changes domain, range, width, and height accordingly.
	 */
	public void zoomIn() {
		if (scale - 10 > 0) scale += 10;
		else scale *= 2;
		domain[0] = -(double) windowWidth / (2.0 * scale);
		domain[1] = (double) windowWidth / (2.0 * scale);
		range[0] = -(double) windowHeight / (2.0 * scale);
		range[1] = (double) windowHeight / (2.0 * scale);
		width = domain[1] - domain[0];
		height = range[1] - range[0];
	}

	/**
	 * Makes scale 10 pixels per unit smaller.
	 * Changes domain, range, width, and height accordingly.
	 */
	public void zoomOut() {
		if (scale - 10 > 0) scale -= 10;
		else scale /= 2;
		domain[0] = -(double) windowWidth / (2.0 * scale);
		domain[1] = (double) windowWidth / (2.0 * scale);
		range[0] = -(double) windowHeight / (2.0 * scale);
		range[1] = (double) windowHeight / (2.0 * scale);
		width = domain[1] - domain[0];
		height = range[1] - range[0];
	}

	/**
	 * Prints domain and range.
	 */
	public void printDomainAndRange() {
		System.out.println("************ Domain and Range ************");
		System.out.println("Domain: [" + domain[0] + ", " + domain[1] + "]");
		System.out.println("Range: [" + range[0] + ", " + range[1] + "]\n");
	}

	public int round(double number) {return (int) Math.floor(number + 0.5d);}
	public double[] getDomain() {return domain;}
	public double[] getRange() {return range;}
	public double getScale() {return scale;}

	/**
	 * Calculates the point of a function at a given x.
	 * @param x the x value to be plugged into the equation
	 * @return the value of the function at the given x
	 */
	public double calculatePoint(double x) {
		m.addVariable("x", x);
		return m.getValue();
	}

	/**
	 * Calculates the slope of a function at a given x.
	 * @param x the x value to be plugged into the derivative
	 * @return the value of the slope at the given x
	 */
	public double calculateSlope(double x) {
		double y1 = calculatePoint(x - 0.000001);
		double y2 = calculatePoint(x + 0.000001);
		double rise = y2 - y1;
		double run = 0.000002;
		return rise / run;
	}

	/**
	 * Calculates the concavity of a function at a given x.
	 * @param x the x value to be plugged into the second derivative
	 * @return the value of the concavity at the given x
	 */
	public double calculateConcavity(double x) {
		double y1 = calculateSlope(x - 0.000001);
		double y2 = calculateSlope(x + 0.000001);
		double rise = y2 - y1;
		double run = 0.000002;
		return rise / run;
	}

	/**
	 * Adds a point to a specified ArrayList.
	 * @param functionID identifies which ArrayList to add a point to
	 * 					 0 --> function
	 * 					 1 --> first derivative
	 * 					 2 --> second derivative
	 * @param x the x value at which a point should be added
	 */
	public void addPoint(int functionID, double x) {
		switch (functionID) {
		case 0: function.add(new Point(x, calculatePoint(x)));
		break;
		case 1: firstDeriv.add(new Point(x, calculateSlope(x)));
		break;
		case 2: secondDeriv.add(new Point(x, calculateConcavity(x)));
		break;
		default: break;
		}
	}

	/**
	 * Prints the function.
	 */
	public void printFunction() {
		DecimalFormat df = new DecimalFormat("#.####");
		System.out.println("**************** Function ****************");
		for (int i = 0; i < function.size(); i++)
			System.out.println("(" + df.format(function.get(i).getX())
					+ ", " + df.format(function.get(i).getY()) + ")");
	}

	/**
	 * Prints the first derivative.
	 */
	public void printFirstDeriv() {
		DecimalFormat df = new DecimalFormat("#.####");
		System.out.println("\n************ First Derivative ************");
		for (int i = 0; i < firstDeriv.size(); i++)
			System.out.println("(" + df.format(firstDeriv.get(i).getX())
					+ ", " + df.format(firstDeriv.get(i).getY()) + ")");
	}

	/**
	 * Prints the second derivative.
	 */
	public void printSecondDeriv() {
		DecimalFormat df = new DecimalFormat("#.####");
		System.out.println("\n*********** Second Derivative ************");
		for (int i = 0; i < secondDeriv.size(); i++)
			System.out.println("(" + df.format(secondDeriv.get(i).getX())
					+ ", " + df.format(secondDeriv.get(i).getY()) + ")");
	}

	/**
	 * Calculates extrema.
	 */
	public void calcExtrema(){
		int size = firstDeriv.size();
		double preSlope, slope;
		for (int i = 1; i < size; i++) {
			preSlope = firstDeriv.get(i - 1).getY();
			slope = firstDeriv.get(i).getY();
			findExtrema(i, slope, preSlope);
		}
	}

	/**
	 * Finds extrema and adds them to their respective ArrayLists using createExtrema().
	 * @param i index in function ArrayList
	 * @param slope current slope
	 * @param preSlope slope from previous index
	 */
	public void findExtrema(int i, double slope, double preSlope){
		if ((slope >= 0) && (preSlope<0))
			createExtremum(i, Extremum.min);
		else if ((slope <= 0) && (preSlope>0))
			createExtremum(i, Extremum.max);
	}

	/**
	 * Adds extrema to respective ArrayLists.
	 * @param i index in function ArrayList
	 * @param mm identifies whether the point is a min or max
	 */
	public void createExtremum(int i, Extremum mm){
		double x = function.get(i).getX();
		double y = function.get(i).getY();
		if (mm == Extremum.min)
			minima.add(new Point(x, y));
		else if (mm == Extremum.max )
			maxima.add(new Point(x, y));
	}

	/**
	 * Prints extrema.
	 */
	public void printExtrema() {
		DecimalFormat df = new DecimalFormat("#.####");
		if (!minima.isEmpty() || !maxima.isEmpty())
			System.out.println("\n************ Relative Extrema ************");
		for (int i = 0; i < minima.size(); i++)
			System.out.println("Minimum at (" + df.format(minima.get(i).getX())
					+ ", " + df.format(minima.get(i).getY()) + ")");
		for (int i = 0; i < maxima.size(); i++)
			System.out.println("Maximum at (" + df.format(maxima.get(i).getX())
					+ ", " + df.format(maxima.get(i).getY()) + ")");
	}

	/**
	 * Calculates points of interest.
	 */
	public void calcPOIs() {
		int size = secondDeriv.size();
		double preConcavity, concavity;
		for (int i = 1; i < size; i++) {
			preConcavity = secondDeriv.get(i - 1).getY();
			concavity = secondDeriv.get(i).getY();
			findPOIs(i, concavity, preConcavity);
		}
	}

	/**
	 * Finds POIs and adds them to their respective ArrayLists using createPOI().
	 * @param i index in function ArrayList
	 * @param concavity current concavity
	 * @param preConcavity concavity from previous index
	 */	
	public void findPOIs(int i, double concavity, double preConcavity){
		if ((concavity >= 0) && (preConcavity < 0))
			createPOI(i, POI.min);
		else if ((concavity <= 0) && (preConcavity > 0))
			createPOI(i, POI.max);
	}

	/**
	 * Adds POIs to respective ArrayLists.
	 * @param i index in function ArrayList
	 * @param mm identifies whether the point is a min or max
	 */
	public void createPOI(int i, POI mm){
		double x = function.get(i).getX();
		double y = function.get(i).getY();
		if (mm == POI.min)
			POImins.add(new Point(x, y));
		else if (mm == POI.max )
			POImaxs.add(new Point(x, y));
	}

	/**
	 * Prints points of inflection.
	 */
	public void printPOIs() {
		DecimalFormat df = new DecimalFormat("#.####");
		if (!POImins.isEmpty() || !POImaxs.isEmpty()) 
			System.out.println("\n********** Points of Inflection **********");
		for (int i = 0; i < POImins.size(); i++)
			System.out.println("Point of inflection (- to +) at (" + df.format(POImins.get(i).getX())
					+ ", " + df.format(POImins.get(i).getY()) + ")");
		for (int i = 0; i < POImaxs.size(); i++)
			System.out.println("Point of inflection (+ to -) at (" + df.format(POImaxs.get(i).getX())
					+ ", " + df.format(POImaxs.get(i).getY()) + ")");
	}

	/**
	 * Finds discontinuities.
	 */
	public void findDiscontinuities() {

		// Finds indices where a slash exists.
		ArrayList<Integer> slashAt = new ArrayList<Integer>();
		for (int i = 0 ; i < equation.length(); i++) {
			if (equation.charAt(i) == '/') slashAt.add(new Integer(i));
		}

		String numerator;
		String denominator;
		double increment = width / windowWidth;
		boolean xAlreadyExists = false;

		// Divides the equation into numerator and denominator for each slash it encounters.
		for (int i = 0; i < slashAt.size(); i++) {

			ArrayList<Point> denominatorValues = new ArrayList<Point>();
			ArrayList<Point> numeratorValues = new ArrayList<Point>();
			
			denominator = equation.substring(slashAt.get(i).intValue() + 1);
			m.setExpression(denominator);
			// Creates an ArrayList of denominator values.
			for (double x = domain[0]; x <= domain[1]; x += increment) {
				m.addVariable("x", x);
				denominatorValues.add(new Point(x, m.getValue()));
			}
			
			numerator = equation.substring(0, slashAt.get(i).intValue());
			m.setExpression(numerator);
						// Creates an ArrayList of numerator values.
			for (double x = domain[0]; x <= domain[1]; x += increment) {
				m.addVariable("x", x);
				numeratorValues.add(new Point(x, m.getValue()));
			}
			
			// If denominator is zero, checks to see if discontinuity is a hole or asymptote.
			for (int j = 0; j < numeratorValues.size(); j++) {
				if (Math.abs(denominatorValues.get(j).getY()) < 0.0001) {
					if (Math.abs(numeratorValues.get(j).getY()) < 0.0001) {
						createDiscontinuity(j, Discontinuity.hole);
						//System.out.println("HOLE FOUND AT x = " + numeratorValues.get(j).getX());
					}
					else {
						createDiscontinuity(j, Discontinuity.asymptote);
						//System.out.println("ASYMPTOTE FOUND AT x = " + numeratorValues.get(j).getX());
					}
				}
				//System.out.println("Numerator value: " + new Point(discontinuities.get(j), numeratorValue));
			}
		}
	}

	/**
	 * Adds discontinuities to respective ArrayLists.
	 * @param i index in function ArrayList
	 * @param d identifies whether the discontinuity is a hole or asymptote
	 */
	public void createDiscontinuity(int i, Discontinuity d) {
		double x = function.get(i).getX();
		double y = function.get(i).getY();
		if (d == Discontinuity.hole)
			holes.add(new Point(x, y));
		else if (d == Discontinuity.asymptote)
			vertAsymptotes.add(new Double(x));
	}

	/**
	 * Prints extrema.
	 */
	public void printDiscontinuities() {
		DecimalFormat df = new DecimalFormat("#.####");
		if (!holes.isEmpty() || !vertAsymptotes.isEmpty()) 
			System.out.println("\n************ Discontinuities *************");
		for (int i = 0; i < holes.size(); i++)
			System.out.println("Hole at (" + df.format(holes.get(i).getX())
					+ ", " + df.format(holes.get(i).getY()) + ")");
		for (int i = 0; i < vertAsymptotes.size(); i++)
			System.out.println("Asymptote at x = " + df.format(vertAsymptotes.get(i)));
	}

	/**
	 * Finds discontinuities and adds them to their respective ArrayLists using createExtrema().
	 * @param i index in function ArrayList
	 * @param yValue current y value
	 * @param preYValue y value from previous index
	 */
	public void findHorizAsymptotes() {
		double slopeAtPosInfinity = calculateSlope(1000000);
		double slopeAtNegInfinity = calculateSlope(-1000000);
		if (Math.abs(slopeAtPosInfinity) < 0.00001) createHorizAsymptote(calculatePoint(1000000));
		System.out.println(calculatePoint(1000000));
		if (Math.abs(slopeAtNegInfinity) < 0.00001) createHorizAsymptote(calculatePoint(1000000));
	}

	/**
	 * Adds horizontal asymptotes to respective ArrayList.
	 * @param y y value of horizontal asymptote
	 */
	public void createHorizAsymptote(double y) {
		boolean yAlreadyExists = false;
		for (int i = 0; i < horizAsymptotes.size(); i++)
			if (y == horizAsymptotes.get(i)) yAlreadyExists = true;
		if (!yAlreadyExists) horizAsymptotes.add(new Double(y));
	}

	/**
	 * Prints extrema.
	 */
	public void printHorizAsymptotes() {
		DecimalFormat df = new DecimalFormat("#.####");
		if (!holes.isEmpty() || !horizAsymptotes.isEmpty()) 
			System.out.println("\n********** Horizontal Asymptotes *********");
		for (int i = 0; i < horizAsymptotes.size(); i++)
			System.out.println("Horizontal asymptote at y = " + df.format(horizAsymptotes.get(i)));
	}

	/**
	 * Blows up the computer.
	 * Draws all the graphs and prints all the info.
	 */
	public void blowUpComputer(boolean clear) {

		if (clear) clearArrays();

		double increment = width / windowWidth;
		for (double x = domain[0]; x <= domain[1]; x += increment) {
			addPoint(0, x);
			addPoint(1, x);
			addPoint(2, x);
		}

		if (clear) System.out.println();

		printDomainAndRange();

		calcExtrema();
		printExtrema();

		calcPOIs();
		printPOIs();	

		findDiscontinuities();
		printDiscontinuities();
		findHorizAsymptotes();
		printHorizAsymptotes();

		System.out.println();
	}

	public void clearArrays() {
		function = new ArrayList<Point>();
		firstDeriv = new ArrayList<Point>();
		secondDeriv = new ArrayList<Point>();
		minima = new ArrayList<Point>();
		maxima = new ArrayList<Point>();
		POImins = new ArrayList<Point>();
		POImaxs = new ArrayList<Point>();
		vertAsymptotes = new ArrayList<Double>();
		holes = new ArrayList<Point>();
		horizAsymptotes = new ArrayList<Double>();
	}

	/**
	 * Draws the x and y axes.
	 * @param g graphics
	 */
	public void drawAxes(Graphics g) {

		// axes
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(0, originY, windowWidth, originY);
		g.drawLine(originX, 0, originX, windowHeight);

		// x-axis tick marks
		int startX = originX;
		while (startX >= 0) startX -= scale;
		for (int i = startX; i < windowWidth; i += scale)
			g.drawLine(i, originY - 2, i, originY + 2);

		// y-axis tick marks
		int startY = originY;
		while (startY >= 0) startY -= scale;
		for (int i = startY; i < windowHeight; i += scale)
			g.drawLine(originX - 2, i, originX + 2, i);

		// x & y labels
		g.setColor(Color.DARK_GRAY);
		g.drawString("X", windowWidth - 8, originY + 5);
		g.drawString("Y", originX - 3, 10);
	}

	/**
	 * Draws the function
	 * @param g graphics
	 */
	public void drawFunction(Graphics g) {
		int size = function.size();
		int x1, y1, x2, y2;
		g.setColor(Color.BLACK);
		for (int i = 1; i < size; i++) {
			x1 = round((originX + ((function.get(i - 1)).getX()) * scale));
			y1 = round((originY - ((function.get(i - 1)).getY()) * scale));
			x2 = round((originX + ((function.get(i)).getX()) * scale));
			y2 = round((originY - ((function.get(i)).getY()) * scale));
			for (int j = 0; j < vertAsymptotes.size(); j++) {
				if (function.get(i - 1).getX() != vertAsymptotes.get(j) &&
					function.get(i).getX() != vertAsymptotes.get(j))
					g.drawLine(x1, y1, x2, y2);
			}
			
			g.drawLine(x1, y1, x2, y2);
		}
	}

	/**
	 * Draws the first derivative
	 * @param g graphics
	 */
	public void drawFirstDeriv(Graphics g) {
		int size = firstDeriv.size();
		int x1, y1, x2, y2;
		g.setColor(new Color(200, 100, 255));
		for (int i = 1; i < size; i++) {
			x1 = round((originX + ((firstDeriv.get(i - 1)).getX()) * scale));
			y1 = round((originY - ((firstDeriv.get(i - 1)).getY()) * scale));
			x2 = round((originX + ((firstDeriv.get(i)).getX()) * scale));
			y2 = round((originY - ((firstDeriv.get(i)).getY()) * scale));
			for (int j = 0; j < vertAsymptotes.size(); j++) {
				if (firstDeriv.get(i - 1).getX() != vertAsymptotes.get(j) &&
					firstDeriv.get(i).getX() != vertAsymptotes.get(j))
					g.drawLine(x1, y1, x2, y2);
			}
			
			g.drawLine(x1, y1, x2, y2);
		}
	}

	/**
	 * Draws the second derivative.
	 * @param g graphics
	 */
	public void drawSecondDeriv(Graphics g) {
		int size = secondDeriv.size();
		int x1, y1, x2, y2;
		g.setColor(new Color(150, 255, 100));
		for (int i = 1; i < size; i++) {
			x1 = round((originX + ((secondDeriv.get(i - 1)).getX()) * scale));
			y1 = round((originY - ((secondDeriv.get(i - 1)).getY()) * scale));
			x2 = round((originX + ((secondDeriv.get(i)).getX()) * scale));
			y2 = round((originY - ((secondDeriv.get(i)).getY()) * scale));
			for (int j = 0; j < vertAsymptotes.size(); j++) {
				if (secondDeriv.get(i - 1).getX() != vertAsymptotes.get(j) &&
					secondDeriv.get(i).getX() != vertAsymptotes.get(j))
					g.drawLine(x1, y1, x2, y2);
			}
			
			g.drawLine(x1, y1, x2, y2);
		}
	}

	/**
	 * Draws the extrema.
	 * @param g graphics
	 */
	public void drawExtrema(Graphics g) {

		g.setColor(new Color(200, 100, 255));

		Point minimum;
		for (int i = 0; i < minima.size(); i++) {
			minimum = minima.get(i);
			g.fillOval(round((minimum.getX() * scale + originX) - 3),
					round((originY - minimum.getY() * scale) - 3), 7, 7);
			/*
			String caption = "Rel Min: \n" + "(" + df.format(minimum.getX()) +
						 	 ", " + df.format(minimum.getY()) + ")";
			g.drawString(caption, (int) (minimum.getX() * scale + originX + 5),
						 (int) (originY - minimum.getY() * scale + 5));
			 */
		}

		Point maximum;
		for (int i = 0; i < maxima.size(); i++) {
			maximum = maxima.get(i);
			g.fillOval(round((maximum.getX() * scale + originX) - 3),
					round((originY - maximum.getY() * scale) - 3), 7, 7);
			/*
			String caption = "Rel Max: \n" + "(" + df.format(maximum.getX()) +
							 ", " + df.format(maximum.getY() )+ ")";
			g.drawString(caption, (int) (maximum.getX() * scale + originX + 5),
						 (int) (originY - maximum.getY() * scale + 5));
			 */
		}
	}

	/** 
	 * Draws the points of inflection
	 * @param g graphics
	 */
	public void drawPOIs(Graphics g) {

		g.setColor(new Color(150, 255, 100));

		Point minPOI;
		for (int i = 0; i < POImins.size(); i++) {
			minPOI = POImins.get(i);
			g.fillOval(round((minPOI.getX() * scale + originX) - 3),
					round((originY - minPOI.getY() * scale) - 3), 7, 7);
			/*
			String caption = "POI: \n" + "(" + df.format(minPOI.getX()) +
						 	 ", " + df.format(minPOI.getY()) + ")";
			g.drawString(caption, (int) (minPOI.getX() * scale + originX + 5),
						 (int) (originY - minPOI.getY() * scale + 5));
			 */
		}

		Point maxPOI;
		for (int i = 0; i < POImaxs.size(); i++) {
			maxPOI = POImaxs.get(i);
			g.fillOval(round((maxPOI.getX() * scale + originX) - 3),
					round((originY - maxPOI.getY() * scale) - 3), 7, 7);
			/*
			String caption = "POI: \n" + "(" + df.format(maxPOI.getX()) +
							 ", " + df.format(maxPOI.getY() )+ ")";
			g.drawString(caption, (int) (maxPOI.getX() * scale + originX + 5),
						 (int) (originY - maxPOI.getY() * scale + 5));
			 */
		}
	}

	/**
	 * Draws the asymptotes.
	 * @param g graphics
	 */
	public void drawAsymptotes(Graphics g) {
		g.setColor(new Color(150, 220, 240));
		double asymptote;
		for (int i = 0; i < vertAsymptotes.size(); i++) {
			asymptote = vertAsymptotes.get(i).doubleValue();
			g.drawLine(round(asymptote * scale + originX), 0,
					round(asymptote * scale + originX), windowHeight); 
		}
		for (int i = 0; i < horizAsymptotes.size(); i++) {
			asymptote = horizAsymptotes.get(i).doubleValue();
			g.drawLine(0, round(originY - asymptote * scale),
					windowWidth, round(originY - asymptote * scale)); 
		}
	}

	/**
	 * Draws the holes.
	 * @param g
	 */
	public void drawHoles(Graphics g) {
		Point hole;
		for (int i = 0; i < holes.size(); i++) {
			hole = holes.get(i);
			g.setColor(Color.WHITE);
			g.fillOval(round((hole.getX() * scale + originX) - 3),
					round((originY - hole.getY() * scale) - 3), 7, 7);
			g.setColor(Color.LIGHT_GRAY);
			g.drawOval(round((hole.getX() * scale + originX) - 3),
					round((originY - hole.getY() * scale) - 3), 7, 7);
		}
	}

}