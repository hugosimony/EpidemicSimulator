package fr.hugosimony.epidemicsimulator;

import java.util.TimerTask;

public class Simulator extends TimerTask {

	private Frame frame;
	public boolean started;
	public boolean paused;
	
	public int[][] tab;
	
	public Simulator(Frame frame)
	{
		this.frame = frame;
		this.started = false;
		this.paused = false;
		this.tab = frame.copyTab(frame.tab);
	}
	
	@Override
	public void run()
	{
		if (!isFinished(frame.tab))
		{
			
			if (!paused)
			{
				++frame.generation;
				frame.generationLabel.setText("Generation : " + frame.generation);
				tab = frame.copyTab(frame.tab);

				for (int i = 0; i < frame.tabX; ++i)
				{
					for (int j = 0; j < frame.tabY; ++j)
					{
						if (frame.tab[i][j] == 1 || frame.tab[i][j] == 2)
							tab[i][j] = checkInfection(i, j);
					}
				}
				
				for (int i = 0; i < frame.tabX; ++i)
				{
					for (int j = 0; j < frame.tabY; ++j)
					{
						if (tab[i][j] == 2)
						{
							double infection = Math.random();
							if (infection <= frame.contagiousness)
								tab[i][j] = 3;
						}
						else if (tab[i][j] == 3)
						{
							double death = Math.random();
							if (death <= frame.lethality)
								tab[i][j] = 9;
							double recovery = Math.random();
							if (recovery <= frame.recovery)
								tab[i][j] = 1;
						}
					}
				}
				
				frame.tab = frame.copyTab(tab);
				frame.updateCells();
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
	
	public boolean isIndexesValid(int i, int j)
	{
		return i >= 0 && j >= 0 && i < frame.tabX && j < frame.tabY;
	}
	
	public int checkInfection(int x, int y)
	{
		for (int i = -1 * frame.expositionRadius; i <= frame.expositionRadius; ++i)
		{
			for (int j = -1 * frame.expositionRadius; j <= frame.expositionRadius; ++j)
			{
				if (isIndexesValid(x + i, y + j) && (i != 0 || j != 0))
				{
					if (tab[x + i][y + j] == 3)
						return 2;
				}
			}
		}
		return 1;
	}
	
	public boolean isFinished(int[][] tab)
	{
		for (int i = 0; i < frame.tabX; ++i)
		{
			for (int j = 0; j < frame.tabY; ++j)
			{
				if (tab[i][j] == 2 || tab[i][j] == 3)
					return false;
			}
		}
		return true;
	}
}
