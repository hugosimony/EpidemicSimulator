package fr.hugosimony.epidemicsimulator;

import javax.swing.JButton;

public class Cell extends JButton {
	private static final long serialVersionUID = 1L;

	private Frame frame;
	private int x;
	private int y;
	
	public Cell(Frame frame, int x, int y)
	{
		this.setSize(20, 20);
	}
}
