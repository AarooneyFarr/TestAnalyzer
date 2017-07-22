package test.view;

import test.controller.TestController;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestPanel extends JPanel
{

	private JFileChooser fileChooser;
	private File currentFile;
	private Scanner fileScanner;
	private TestController baseController;
	private JButton fileButton;
	private JButton analyzeButton;
	private ArrayList<ArrayList<Object>> infoMatrix;
	private ArrayList<ArrayList<Object>> scaledMatrix;
	//private ArrayList<Object> tempList;

	private JLabel corellationOne;
	private JLabel corellationTwo;
	private JLabel corellationThree;
	private JLabel corellationFour;
	private JLabel corellationFive;
	private JLabel corellationSix;
	
	
	public TestPanel(TestController baseController)
	{
		super();
		this.baseController = baseController;

		fileChooser = new JFileChooser();
		currentFile = null;

		fileButton = new JButton("File");
		analyzeButton = new JButton("Analyze");

		infoMatrix = new ArrayList<ArrayList<Object>>();
		scaledMatrix = new ArrayList<ArrayList<Object>>();
		//tempList = new ArrayList<Object>();

		//tempList.add(new String());
		//tempList.add(new Double(0.0));
		//tempList.add(new String());
		//tempList.add(new Double(0.0));
		//tempList.add(new Double(0.0));
		
		
		corellationOne = new JLabel("The standard deviation between Sage and Maps is: ");
		corellationTwo = new JLabel("The standard deviation between Sage and Reading is: ");
		corellationThree = new JLabel("The standard deviation between Sage and Dibels is: ");
		corellationFour = new JLabel("The standard deviation between Maps and Reading is: ");
		corellationFive = new JLabel("The standard deviation between Maps and Dibels is: ");
		corellationSix = new JLabel("The standard deviation between Reading and Dibels is: ");

		setupPanel();
		setupLayout();
		setupListeners();

	}

	private void setupPanel()
	{
		//this.add(analyzeButton);
		this.add(fileButton);
		this.add(corellationOne);
		this.add(corellationTwo);
		this.add(corellationThree);
		this.add(corellationFour);
		this.add(corellationFive);
		this.add(corellationSix);
	}

	private void setupLayout()
	{
		setLayout(null);
		fileButton.setBounds(188, 334, 123, 30);
		analyzeButton.setBounds(291, 334, 123, 30);
		corellationOne.setBounds(52,181,423,50);
		corellationTwo.setBounds(52,150,423,50);
		corellationThree.setBounds(52,120,423,50);
		corellationFour.setBounds(52,89,423,50);
		corellationFive.setBounds(52,59,423,50);
		corellationSix.setBounds(52,28,423,50);

	}

	private void setupListeners()
	{
		fileButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				int returnVal = fileChooser.showOpenDialog(null);

				currentFile = null;
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					infoMatrix.clear();
					scaledMatrix.clear();
					
					currentFile = fileChooser.getSelectedFile();
					scanFile();
					
					for(ArrayList<Object> current : scaledMatrix)
					{
						
						String hello = (String) current.get(1);
						
						current.set(1, (scale(Double.parseDouble((String)current.get(1)), 0,800)));
						//This one needs alphabet scaling
						current.set(2,  scaleAlphabet((String) current.get(2)));
						
						current.set(3,  scale(Double.parseDouble((String)current.get(3)), 0,350));
						current.set(4 ,  scale(Double.parseDouble((String)current.get(4)), 0,999));
						
						
						
						
						
					}
					


					//System.out.print(" " + testCorellation(1, 2));
					testCorellations();
				}
			}
		});

	}

	private void scanFile()
	{
		try
		{
			fileScanner = new Scanner(currentFile);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		infoMatrix.clear();

		String tempString = new String();
		tempString = "";
		String delims = new String("[\t ]+");

		while (fileScanner.hasNextLine())
		{
			String tempoList[] = new String[5];
			
			ArrayList<Object> tempList = new ArrayList<Object>();
			tempList.add(new String());
			tempList.add(new Double(0.0));
			tempList.add(new String());
			tempList.add(new Double(0.0));
			tempList.add(new Double(0.0));
			
			tempString = fileScanner.nextLine();
			tempoList = tempString.split(delims);
			
			for(int i = 0; i < 5; i++)
			{
				tempList.set(i, tempoList[i]);
			}

			infoMatrix.add(tempList);
			scaledMatrix.add(tempList);

		}
		
		for(ArrayList<Object> current : infoMatrix)
		{
			try
			{
				
				Double.parseDouble((String) current.get(1));
				Double.parseDouble((String) current.get(3));
				Double.parseDouble((String) current.get(4));
				//JOptionPane.showMessageDialog(null, "All is well for this line");
				
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Error detected in File. Please Check test score file " + e.getMessage());
				
			}
		}

	}

	private int testCorellation(int testOne, int testTwo)
	{
		if (testOne == testTwo)
		{
			return -99999999;
		}

		double average = 0;
		ArrayList<Double> difList = new ArrayList<Double>();
		for (ArrayList<Object> current : scaledMatrix)
		{
			double one =  (Double) current.get(testOne);
			double two = (Double) current.get(testTwo);
			double dif = Math.abs(two - one);
			difList.add(dif);
		}

		double sum = 0;

		for (double currentNumber : difList)
		{
			sum += currentNumber;
		}
		average = sum / difList.size();

		return (int) average;
	}
	
	/**
	 * Scales test scores to 0-100
	 * @param score Score received on test
	 * @param lower Lowest possible Score on test
	 * @param upper Highest possible score on test
	 * @return New score one the 0-100 scale
	 */
	public double scale(Double score, double lower, double upper)
	{
		double scaledScore = 0;
		double range = upper - lower;
		double percent = score/range;
		
		scaledScore = percent * 100;
		
		
		return scaledScore;
	
	}
	
	public double scaleAlphabet(String letter)
	{
		
		double score = 0;
		if(letter.equalsIgnoreCase("AA"))
		{
			score = 0;
		}
		if(letter.equalsIgnoreCase("A"))
		{
			score = 1;
		}
		if(letter.equalsIgnoreCase("B"))
		{
			score = 2;
		}
		if(letter.equalsIgnoreCase("C"))
		{
			score = 3;
		}
		if(letter.equalsIgnoreCase("D"))
		{
			score = 4;
		}
		if(letter.equalsIgnoreCase("E"))
		{
			score = 5;
		}
		if(letter.equalsIgnoreCase("F"))
		{
			score = 6;
		}
		if(letter.equalsIgnoreCase("G"))
		{
			score = 7;
		}
		if(letter.equalsIgnoreCase("H"))
		{
			score = 8;
		}
		if(letter.equalsIgnoreCase("I"))
		{
			score = 9;
		}
		if(letter.equalsIgnoreCase("J"))
		{
			score = 10;
		}
		if(letter.equalsIgnoreCase("K"))
		{
			score = 11;
		}
		if(letter.equalsIgnoreCase("L"))
		{
			score = 12;
		}
		if(letter.equalsIgnoreCase("M"))
		{
			score = 13;
		}
		if(letter.equalsIgnoreCase("N"))
		{
			score = 14;
		}
		if(letter.equalsIgnoreCase("O"))
		{
			score = 15;
		}
		if(letter.equalsIgnoreCase("P"))
		{
			score = 16;
		}
		if(letter.equalsIgnoreCase("Q"))
		{
			score = 17;
		}
		if(letter.equalsIgnoreCase("R"))
		{
			score = 18;
		}
		if(letter.equalsIgnoreCase("S"))
		{
			score = 19;
		}
		if(letter.equalsIgnoreCase("T"))
		{
			score = 20;
		}
		if(letter.equalsIgnoreCase("U"))
		{
			score = 21;
		}
		if(letter.equalsIgnoreCase("V"))
		{
			score = 22;
		}
		if(letter.equalsIgnoreCase("W"))
		{
			score = 23;
		}
		if(letter.equalsIgnoreCase("X"))
		{
			score = 24;
		}
		if(letter.equalsIgnoreCase("Y"))
		{
			score = 25;
		}
		if(letter.equalsIgnoreCase("Z"))
		{
			score = 26;
		}
		
		double scaledScore = scale(score,0,26);
				
		return scaledScore;
	}
	
	private void testCorellations()
	{
		int closest = testCorellation(1,3);
		
		corellationSix.setText("The standard deviation between Reading and Dibels is: " + testCorellation(1,2));
		corellationFive.setText("The standard deviation between Maps and Dibels is: " + testCorellation(3,1));
		corellationFour.setText("The standard deviation between Maps and Reading is: " + testCorellation(3,2));
		corellationThree.setText("The standard deviation between Sage and Dibels is: " + testCorellation(1,4));
		corellationTwo.setText("The standard deviation between Sage and Reading is: " + testCorellation(4,2));
		corellationOne.setText("The standard deviation between Sage and Maps is: " + testCorellation(4,3));
		
	}
	
	
	
	
}
