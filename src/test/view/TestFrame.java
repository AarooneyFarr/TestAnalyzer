package test.view;

import javax.swing.JFrame;
import test.controller.TestController;

import java.awt.Color;
import java.awt.Dimension;

public class TestFrame extends JFrame
{

	private TestController baseController;
	private TestPanel2 appPanel2;

	public TestFrame(TestController baseController)
	{
		this.baseController = baseController;
		
		appPanel2 = new TestPanel2(baseController);
		
		setupPanel(850, 810);
		setupListeners();
	}

	private void setupPanel(int width, int height)
	{
		this.setBackground(Color.blue);
		this.setContentPane(appPanel2);
		this.setTitle("Test Analyzer");
		this.setSize(new Dimension(width, height));
		this.setVisible(true);

	}

	private void setupListeners()
	{
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{

			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent)
			{
				System.exit(0);
			}

		});
	}
}