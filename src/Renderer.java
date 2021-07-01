package src;

import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Renderer extends JPanel
{

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if (ComponentInWindow.dttwt != null)
		{
			ComponentInWindow.dttwt.render(g);
		}
	}
	
}
