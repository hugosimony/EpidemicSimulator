package fr.hugosimony.epidemicsimulator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

public class CellChoice extends JRadioButton {
	private static final long serialVersionUID = 1L;
	
	public CellChoice(Frame frame, String choice, int locX, int locY)
	{
		this.setText(choice);
		this.setFont(frame.font);
		this.setSize(150, 60);
		this.setFocusable(false);
		this.setVerticalAlignment(CENTER);
		this.setHorizontalAlignment(CENTER);
		this.setLocation(locX, 10);
		
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				frame.healthy.setSelected(false);
				frame.exposed.setSelected(false);
				frame.infected.setSelected(false);
				frame.dead.setSelected(false);
				frame.empty.setSelected(false);
				setSelected(true);
			}
		});
	}

}
