package fr.hugosimony.epidemicsimulator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Cell extends JButton {
	private static final long serialVersionUID = 1L;

	/*
	 * 0 : empty
	 * 1 : healthy
	 * 2 : exposed
	 * 3 : infected
	 * 4 : vaccinated //TODO
	 * 9 : dead
	 */
	
	private static final Color healthy = new Color(0, 255, 100);
	private static final Color exposed = Color.orange;
	private static final Color infected = Color.red;
	private static final Color dead = Color.black;
	private static final Color empty = Color.white;
	
	private Frame frame;
	private int x;
	private int y;
	
	public Cell(Frame frame, int x, int y)
	{
		this.frame = frame;
		this.x = x;
		this.y = y;
		this.setSize(20, 20);
		updateColor();
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (frame.healthy.isSelected())
					frame.tab[x][y] = 1;
				else if (frame.exposed.isSelected())
					frame.tab[x][y] = 2;
				else if (frame.infected.isSelected())
					frame.tab[x][y] = 3;
				else if (frame.dead.isSelected())
					frame.tab[x][y] = 9;
				else if (frame.empty.isSelected())
					frame.tab[x][y] = 0;
				updateColor();
			}
		});
	}
	
	public void updateColor()
	{
		if (frame.tab[x][y] == 1)
			this.setBackground(healthy);
		else if (frame.tab[x][y] == 2)
			this.setBackground(exposed);
		else if (frame.tab[x][y] == 3)
			this.setBackground(infected);
		else if (frame.tab[x][y] == 9)
			this.setBackground(dead);
		else if (frame.tab[x][y] == 0)
			this.setBackground(empty);
	}
}
