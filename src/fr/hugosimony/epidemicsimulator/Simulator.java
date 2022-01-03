package fr.hugosimony.epidemicsimulator;

import java.util.TimerTask;

public class Simulator extends TimerTask {

	private Frame frame;
	public boolean started;
	public boolean paused;
	
	public Simulator(Frame frame)
	{
		this.frame = frame;
		this.started = false;
		this.paused = false;
	}
	
	@Override
	public void run()
	{
		if (frame.generation < 100)
		{
			if (!paused)
			{
				frame.generation++;
				frame.generationLabel.setText("Generation : " + frame.generation);
			}
		}
		else
		{
			this.cancel();
			frame.start_pause.setText("Start");
			frame.simulator = new Simulator(frame);
			frame.generation = 0;
		}
	}
}
