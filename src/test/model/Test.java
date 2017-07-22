package test.model;

import java.util.ArrayList;
import java.util.Vector;

public class Test
{

	private String name;
	private Double lower;
	private Double upper;
	public Boolean isScaled;
	public String type;
	private ArrayList<Double> testList;
	private ArrayList<Object> testList2;

	public Test(String name, Double lower, Double upper, Boolean isScaled, String type)
	{
		this.name = name;
		this.lower = lower;
		this.upper = upper;
		this.isScaled = isScaled;
		this.type = type;
	}
	
	public Test(ArrayList<Object> testList2)
	{
		testList = new ArrayList<Double>();
		this.testList2 = testList2;
		objectToDouble(testList2);
		
	}
	
	public void objectToDouble(ArrayList<Object> list)
	{
		for(Object current : list)
		{
			testList.add(Double.parseDouble((String) current));
		}
	}

	public void add(Double score)
	{
		testList.add(score);
	}

	public Double getAverageScore()
	{
		return this.getAverageFromList(testList);
	}

	private Double getAverageFromList(ArrayList<Double> list)
	{
		Double sum = 0.0;

		for (Object current : list)
		{
			sum += (Double) current;
		}

		sum = sum / list.size();

		return sum;
	}

	public Double getStandardDeviation()
	{
		ArrayList<Double> deviationList = new ArrayList<Double>();

		for (Object current : testList)
		{
			Double deviation = Math.abs(this.getAverageScore() - (Double) current);
			deviationList.add(deviation);
		}

		return this.getAverageFromList(deviationList);
	}

	public Double getScore(int index)
	{
		return testList.get(index);
	}

	public void setScore(int index, Double score)
	{
		testList.set(index, score);
	}

/*	public ArrayList<Integer> getOutliers()
	{
		ArrayList<Integer> outlierList = new ArrayList<Integer>();
		

		for (int i = 0; i < testList.size(); i++)
		{
			Double current = testList.get(i);
			Double average = this.getAverageScore();
			Double deviation = this.getStandardDeviation();
			Double sDeviation = (Math.abs(testList.get(i) - this.getAverageScore()) / this.getStandardDeviation());
			
			if (1.8 <= sDeviation)
			{
				outlierList.add(i);
			}

		}
		return outlierList;
	}*/
	
	public ArrayList<Vector<Integer>> getOutliers()
	{
		ArrayList<Vector<Integer>> outlierList = new ArrayList<Vector<Integer>>();
		

		for (int i = 0; i < testList.size(); i++)
		{
			Double current = testList.get(i);
			Double average = this.getAverageScore();
			Double deviation = this.getStandardDeviation();
			Double sDeviation = (Math.abs(testList.get(i) - this.getAverageScore()) / this.getStandardDeviation());
			Double pDeviation = ((testList.get(i) - this.getAverageScore()) / this.getStandardDeviation());
			
			if (1.8 <= sDeviation)
			{
				Vector<Integer> temp = new Vector<Integer>();
				temp.add(i);
				if(pDeviation > 0)
				{
					temp.add(1);
				}
				else
				{
					temp.add(0);
				}
				
				outlierList.add(temp);
				
				
			}

		}
		return outlierList;
	}

	/**
	 * Scales test scores to 0-100
	 * 
	 * @param score
	 *            Score received on test
	 * @param lower
	 *            Lowest possible Score on test
	 * @param upper
	 *            Highest possible score on test
	 * @return New score one the 0-100 scale
	 */
	public void scale()
	{

		for (Double current : testList)
		{
			current = current / (upper - lower) * 100;
		}
		
		isScaled = true;

	}

	public double scaleAlphabet(String letter)
	{
		
		double score = 0;
		if (letter.equalsIgnoreCase("AA"))
		{
			score = 0;
		}
		if (letter.equalsIgnoreCase("A"))
		{
			score = 1;
		}
		if (letter.equalsIgnoreCase("B"))
		{
			score = 2;
		}
		if (letter.equalsIgnoreCase("C"))
		{
			score = 3;
		}
		if (letter.equalsIgnoreCase("D"))
		{
			score = 4;
		}
		if (letter.equalsIgnoreCase("E"))
		{
			score = 5;
		}
		if (letter.equalsIgnoreCase("F"))
		{
			score = 6;
		}
		if (letter.equalsIgnoreCase("G"))
		{
			score = 7;
		}
		if (letter.equalsIgnoreCase("H"))
		{
			score = 8;
		}
		if (letter.equalsIgnoreCase("I"))
		{
			score = 9;
		}
		if (letter.equalsIgnoreCase("J"))
		{
			score = 10;
		}
		if (letter.equalsIgnoreCase("K"))
		{
			score = 11;
		}
		if (letter.equalsIgnoreCase("L"))
		{
			score = 12;
		}
		if (letter.equalsIgnoreCase("M"))
		{
			score = 13;
		}
		if (letter.equalsIgnoreCase("N"))
		{
			score = 14;
		}
		if (letter.equalsIgnoreCase("O"))
		{
			score = 15;
		}
		if (letter.equalsIgnoreCase("P"))
		{
			score = 16;
		}
		if (letter.equalsIgnoreCase("Q"))
		{
			score = 17;
		}
		if (letter.equalsIgnoreCase("R"))
		{
			score = 18;
		}
		if (letter.equalsIgnoreCase("S"))
		{
			score = 19;
		}
		if (letter.equalsIgnoreCase("T"))
		{
			score = 20;
		}
		if (letter.equalsIgnoreCase("U"))
		{
			score = 21;
		}
		if (letter.equalsIgnoreCase("V"))
		{
			score = 22;
		}
		if (letter.equalsIgnoreCase("W"))
		{
			score = 23;
		}
		if (letter.equalsIgnoreCase("X"))
		{
			score = 24;
		}
		if (letter.equalsIgnoreCase("Y"))
		{
			score = 25;
		}
		if (letter.equalsIgnoreCase("Z"))
		{
			score = 26;
		}

		

		
		
		double scaledScore = score / (26) * 100;
		
		return scaledScore;
	}
	
	
	
	
}
