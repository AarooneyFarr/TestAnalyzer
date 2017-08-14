package test.controller;

import test.model.Printer;
import test.view.TestFrame;
import test.view.TestPanel2;

import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JOptionPane;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;


public class TestController
{

	public TestFrame appFrame;
	private TestPanel2 tester;
	
	public TestController()
	{
		appFrame = new TestFrame(this);
		tester = new TestPanel2(this);
		
	}
	
	public void start()
	{
		
		
	}
	
	public void print()
	{
		PrinterJob pjob = PrinterJob.getPrinterJob();
		PageFormat preformat = pjob.defaultPage();
		preformat.setOrientation(PageFormat.LANDSCAPE);
		PageFormat postformat = pjob.pageDialog(preformat);
		//If user does not hit cancel then print.
		if (preformat != postformat) {
		    //Set print component
		    pjob.setPrintable(new Printer(appFrame), postformat);
		    if (pjob.printDialog()) {
		        try
				{
					pjob.print();
				}
				catch (PrinterException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
		
	}

	
	
	
}
