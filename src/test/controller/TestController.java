package test.controller;

import test.view.TestFrame;
import test.view.TestPanel2;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;


public class TestController
{

	private TestFrame appFrame;
	private TestPanel2 tester;
	
	public TestController()
	{
		appFrame = new TestFrame(this);
		tester = new TestPanel2(this);
		
	}
	
	public void start()
	{
		double[] x = {1,2,3,4,5,6,7,8,9,0};
		double[] y = {8,7,6,5,4,3,2,1,0,9};
		double corr = new PearsonsCorrelation().correlation(x,y);
		
		System.out.print(corr);
		
		
	}
}
