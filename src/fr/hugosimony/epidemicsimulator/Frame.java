package fr.hugosimony.epidemicsimulator;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// Private instance
	private Frame frame;
	
	// Tabs
	public Cell[][] tabUI;
	public int[][] tab;
	
	// Tab length
	public int tabX = 30;
	public int tabY = 50;

	// Components
	public final Font font = new Font("Arial", Font.BOLD, 20);
	public JRadioButton healthy, exposed, infected, dead, empty;
	
	// Simulator
	public Simulator simulator;
	JButton start_pause;
	JLabel generationLabel;
	public int generation = 0;
	
	// Simulator settings
	public int expositionRadius = 1; // radius where you are exposed between 1 and max(tabX, tabY)
	public float contagiousness = 0.1f; // probability to contract between 0 and 1
	public float recovery = 0.1f; // probability to recover between 0 and 1
	public float lethality = 0.2f; // probability to be killed between 0 and 1
	
	private final String[] CONFIG_CHOICES =
	{
		"Config", "Cluster", "Dispersed"
	};
	
	public Frame()
	{
		// Main frame settings
		this.frame = this;
		setTitle("Epidemic Simulator --- Made by Hugo Simony-Jungo");
		setSize(tabX * 30 + 50, tabX * 30);
		setResizable(false);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(2);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosed(WindowEvent e)
			{
				System.exit(0);
			}
		});
		
		// Simulator settings
		tab = copyTab(Configurations.dispersed);
		simulator = new Simulator(this);
		
		// Main panel
		JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(null);
	    
	    // Top panel
	    JPanel topPanel = new JPanel();
	    topPanel.setLayout(null);
	    topPanel.setOpaque(true);
	    topPanel.setLocation(0, 0);
	    topPanel.setSize(tabX * 30 + 50, 60);
	    
	    healthy = new CellChoice(this, "Healthy", 20, 20);
	    topPanel.add(healthy);
	    exposed = new CellChoice(this, "Exposed", 160, 20);
	    topPanel.add(exposed);
	    infected = new CellChoice(this, "Infected", 300, 20);
	    infected.setSelected(true);
	    topPanel.add(infected);
	    dead = new CellChoice(this, "Dead", 440, 20);
	    topPanel.add(dead);
	    empty = new CellChoice(this, "Empty", 580, 20);
	    topPanel.add(empty);
	    
	    JComboBox<String> configBox = new JComboBox<>(CONFIG_CHOICES);
	    configBox.setFont(font);
	    configBox.setSize(150, 40);
	    configBox.setFocusable(false);
	    configBox.setLocation(740, 20);
	    ((JLabel) configBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
	    topPanel.add(configBox);
	    
	    mainPanel.add(topPanel);
	    
	    // Tab panel
	    JPanel tabPanel = new JPanel();
	    tabPanel.setLayout(new GridLayout(tabX, tabY));
	    tabPanel.setLocation(20, 60);
	    tabPanel.setSize(tabX * 30, 740);
	    
	    tabUI = new Cell[tabX][tabY];
	    for (int i = 0; i < tabX; i++) {
	    	for (int j = 0; j < tabY; j++) {
	    		Cell cell = new Cell(this, i, j);
	    		tabPanel.add(cell);
		        tabUI[i][j] = cell;
	    	} 
	    }
	    
	    mainPanel.add(tabPanel);
	    
	    // Low panel
	    JPanel lowPanel = new JPanel();
	    lowPanel.setLayout(null);
	    lowPanel.setSize(tabX * 30 + 50, 60);
	    lowPanel.setLocation(0, 800);
	    
	    start_pause = new JButton("Start");
	    start_pause.setFont(font);
	    start_pause.setFocusable(false);
	    start_pause.setVerticalAlignment(SwingConstants.CENTER);
	    start_pause.setHorizontalAlignment(SwingConstants.CENTER);
	    start_pause.setLocation(20, 0);
	    start_pause.setSize(300, 50);
	    start_pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (start_pause.getText().equals("Start"))
				{
					simulator.paused = false;
					start_pause.setText("Pause");
					System.out.println(simulator.started);
					if (!simulator.started)
					{
						System.out.println("test");
						simulator.started = true;
						new Timer().schedule(simulator, 50, 50);
					}
				}
				else {
					simulator.paused = true;
					start_pause.setText("Start");
				}
			}
		});
	    lowPanel.add(start_pause);
	    
	    generationLabel = new JLabel("Generation : 0");
	    generationLabel.setFont(font);
	    generationLabel.setFocusable(false);
	    generationLabel.setVerticalAlignment(SwingConstants.CENTER);
	    generationLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    generationLabel.setLocation(320, 0);
	    generationLabel.setSize(300, 50);
	    lowPanel.add(generationLabel);
	    
	    JButton reset = new JButton("Reset");
	    reset.setFont(font);
	    reset.setFocusable(false);
	    reset.setVerticalAlignment(SwingConstants.CENTER);
	    reset.setHorizontalAlignment(SwingConstants.CENTER);
	    reset.setLocation(620, 0);
	    reset.setSize(300, 50);
	    reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				simulator.cancel();
				start_pause.setText("Start");
				simulator = new Simulator(frame);
				generation = 0;
				generationLabel.setText("Generation : 0");
				tab = copyTab(Configurations.cluster);
				updateCells();
			}
		});
	    lowPanel.add(reset);
	    
	    mainPanel.add(lowPanel);
	    
	    this.add(mainPanel);
	}
	
	public int[][] copyTab(int[][] tab)
	{
		int[][] newTab = new int[tabX][tabY];
		for (int i = 0; i < tabX; ++i)
		{
			for (int j = 0; j < tabY; ++j)
				newTab[i][j] = tab[i][j];
		}
		return newTab;
	}
	
	public void updateCells()
	{
		for (int i = 0; i < tabX; ++i)
		{
			for (int j = 0; j < tabY; ++j)
				tabUI[i][j].updateColor();
		}
	}
}
