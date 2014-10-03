import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.net.MalformedURLException;
import java.net.URL;


public class Main extends JFrame {

	private JPanel menu;
	private JPanel divider;
	private JPanel graph;
	private JPanel content;

	private JLabel functionLabel;
	private JTextField functionTextBox;
	private JButton bigRedButton;

	private JToggleButton functionButton;
	private JToggleButton firstDerivButton;
	private JToggleButton secondDerivButton;

	private JToggleButton extremaButton;
	private JToggleButton POIsButton;
	private JToggleButton asymptotesButton;
	private JToggleButton holesButton;

	private JPanel functionKey;
	private JPanel firstDerivKey;
	private JPanel secondDerivKey;

	private JPanel extremaKey;
	private JPanel POIsKey;
	private JPanel asymptotesKey;
	private JPanel holesKey;

	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JButton clearButton;

	private GraphingCalculator g;

	public Main() throws MalformedURLException {
		createContent();
		setContentPane(content);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 618);
		setTitle("Tu-Yuan Graphing Calculator");
		setVisible(true);
	}

	private void createMenu() throws MalformedURLException {

		// ************** Basics **************
		menu = new JPanel();
		menu.setPreferredSize(new Dimension(220, 618));
		menu.setLayout(null);
		menu.setBackground(Color.WHITE);

		// ************ Input Box *************		
		functionLabel = new JLabel("f(x) = ");
		functionLabel.setBounds(7, 3, 40, 25);
		menu.add(functionLabel);
		functionTextBox = new JTextField();
		functionTextBox.setBounds(40, 5, 175, 25);
		menu.add(functionTextBox);

		// ********** Big Red Button **********
		bigRedButton = new JButton(new ImageIcon(new URL("http://i43.tinypic.com/160poww.png")));
		bigRedButton.setPressedIcon(new ImageIcon(new URL("http://i42.tinypic.com/2hx0ldv.png")));
		bigRedButton.setBounds(5, 32, 210, 210);
		bigRedButton.setBackground(Color.WHITE);
		bigRedButton.setBorder(null);
		bigRedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int width = graph.getPreferredSize().width;
				int height = graph.getPreferredSize().height;

				functionButton.setSelected(true);
				firstDerivButton.setSelected(true);
				secondDerivButton.setSelected(true);
				extremaButton.setSelected(true);
				POIsButton.setSelected(true);
				asymptotesButton.setSelected(true);
				holesButton.setSelected(true);

				g = new GraphingCalculator(functionTextBox.getText(), width, height);
				g.blowUpComputer(false);
				displayGraphs(graph.getGraphics());
			}
		});
		menu.add(bigRedButton);

		// ********** Toggle Buttons **********
		functionButton = new JToggleButton("Function");
		functionButton.setBounds(5, 255, 175, 30);
		functionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (functionButton.isSelected()) {
					System.out.println("Function displayed.");
				}
				else {
					System.out.println("Function hidden.");
				}
				displayGraphs(graph.getGraphics());
			}
		});
		menu.add(functionButton);

		firstDerivButton = new JToggleButton("First Derivative");
		firstDerivButton.setBounds(5, 290, 175, 30);
		firstDerivButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (firstDerivButton.isSelected()) {
					System.out.println("First derivative displayed.");
				}
				else {
					System.out.println("First derivative hidden.");
				}
				displayGraphs(graph.getGraphics());
			}
		});
		menu.add(firstDerivButton);

		secondDerivButton = new JToggleButton("Second Derivative");
		secondDerivButton.setBounds(5, 325, 175, 30);
		secondDerivButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (secondDerivButton.isSelected()) {
					System.out.println("Second derivative displayed.");
				}
				else {
					System.out.println("Second derivative hidden.");
				}
				displayGraphs(graph.getGraphics());
			}
		});
		menu.add(secondDerivButton);

		extremaButton = new JToggleButton("Relative Extrema");
		extremaButton.setBounds(5, 370, 175, 30);
		extremaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (extremaButton.isSelected()) {
					System.out.println("Extrema displayed.");
				}
				else {
					System.out.println("Extrema hidden.");
				}
				displayGraphs(graph.getGraphics());
			}
		});
		menu.add(extremaButton);

		POIsButton = new JToggleButton("Points of Inflection");
		POIsButton.setBounds(5, 405, 175, 30);
		POIsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (POIsButton.isSelected()) {
					System.out.println("Points of inflection displayed.");
				}
				else {
					System.out.println("Points of inflection hidden.");
				}
				displayGraphs(graph.getGraphics());
			}
		});
		menu.add(POIsButton);

		asymptotesButton = new JToggleButton("Asymptotes");
		asymptotesButton.setBounds(5, 440, 175, 30);
		asymptotesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (asymptotesButton.isSelected()) {
					System.out.println("Asymptotes displayed.");
				}
				else {
					System.out.println("Asymptotes hidden.");
				}
				displayGraphs(graph.getGraphics());
			}
		});
		menu.add(asymptotesButton);

		holesButton = new JToggleButton("Holes");
		holesButton.setBounds(5, 475, 175, 30);
		holesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (holesButton.isSelected()) {
					System.out.println("Holes displayed.");
				}
				else {
					System.out.println("Holes hidden.");
				}
				displayGraphs(graph.getGraphics());
			}
		});
		menu.add(holesButton);

		// ************ Color Keys ************

		functionKey = new JPanel();
		functionKey.setBounds(185, 255, 30, 30);
		functionKey.setBackground(Color.BLACK);
		menu.add(functionKey);

		firstDerivKey = new JPanel();
		firstDerivKey.setBounds(185, 290, 30, 30);
		firstDerivKey.setBackground(new Color(200, 100, 255));
		menu.add(firstDerivKey);

		secondDerivKey = new JPanel();
		secondDerivKey.setBounds(185, 325, 30, 30);
		secondDerivKey.setBackground(new Color(150, 255, 100));
		menu.add(secondDerivKey);

		extremaKey = new JPanel();
		extremaKey.setBounds(185, 370, 30, 30);
		extremaKey.setBackground(new Color(200, 100, 255));
		menu.add(extremaKey);

		POIsKey = new JPanel();
		POIsKey.setBounds(185, 405, 30, 30);
		POIsKey.setBackground(new Color(150, 255, 100));
		menu.add(POIsKey);

		asymptotesKey = new JPanel();
		asymptotesKey.setBounds(185, 440, 30, 30);
		asymptotesKey.setBackground(new Color(150, 220, 240));
		menu.add(asymptotesKey);

		holesKey = new JPanel();
		holesKey.setBounds(185, 475, 30, 30);
		holesKey.setBackground(Color.LIGHT_GRAY);
		menu.add(holesKey);

		// *********** Zoom & Clear ***********
		zoomInButton = new JButton("Zoom In");
		zoomInButton.setBounds(5, 520, 102, 30);
		zoomInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Zoomed in.");
				g.zoomIn();
				g.blowUpComputer(true);
				displayGraphs(graph.getGraphics());
			}
		});
		menu.add(zoomInButton);

		zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.setBounds(112, 520, 103, 30);
		zoomOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (g.getScale() > 1.25) {
					System.out.println("Zoomed out.");
					g.zoomOut();
					g.blowUpComputer(true);
					displayGraphs(graph.getGraphics());
				}
				else System.out.println("Cannot zoom out any further.");
			}
		});
		menu.add(zoomOutButton);

		clearButton = new JButton("Clear");
		clearButton.setBounds(5, 555, 210, 30);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Graph cleared.");
				functionTextBox.setText("");

				functionButton.setSelected(false);
				firstDerivButton.setSelected(false);
				secondDerivButton.setSelected(false);
				extremaButton.setSelected(false);
				POIsButton.setSelected(false);
				asymptotesButton.setSelected(false);
				holesButton.setSelected(false);

				clearGraph(graph.getGraphics());
			}
		});
		menu.add(clearButton);

		// *********** Set Visible! ***********
		menu.setVisible(true);
	}

	private void createDivider() {
		divider = new JPanel();
		//divider.setPreferredSize(new Dimension(1, 593));
		divider.setLayout(null);
		divider.setBackground(Color.GRAY);
	}

	private void createGraph() {
		graph = new JPanel();
		graph.setLayout(null);
		graph.setPreferredSize(new Dimension(572, 618));
		graph.setBackground(Color.WHITE);
	}

	private void createContent() throws MalformedURLException{
		content = new JPanel();
		content.setLayout(new BorderLayout());
		createDivider();
		createGraph();
		createMenu();
		content.add(menu, BorderLayout.WEST);
		content.add(divider, BorderLayout.CENTER);
		content.add(graph, BorderLayout.EAST);
		setResizable(false);
	}

	public void clearGraph(Graphics gr) {
		gr.setColor(Color.WHITE); // Heh, my cheap way of clearing the graph.
		gr.fillRect(0, 0, (int)graph.getSize().getWidth(), (int)graph.getSize().getHeight());
		g.drawAxes(gr); 
	}

	public void displayGraphs(Graphics gr) {
		clearGraph(gr);
		if (asymptotesButton.isSelected()) g.drawAsymptotes(gr);
		if (secondDerivButton.isSelected()) g.drawSecondDeriv(gr);
		if (firstDerivButton.isSelected()) g.drawFirstDeriv(gr);
		if (functionButton.isSelected()) g.drawFunction(gr);
		if (extremaButton.isSelected()) g.drawExtrema(gr);
		if (POIsButton.isSelected()) g.drawPOIs(gr);
		if (holesButton.isSelected()) g.drawHoles(gr);
	}

	public static void main(String[] blargalarghs) throws MalformedURLException {
		new Main();
	}

}
