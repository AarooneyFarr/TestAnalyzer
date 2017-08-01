package test.view;

import test.controller.TestController;
import test.model.Test;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.ScrollPane;
import java.awt.Font;
import java.awt.Checkbox;

public class TestPanel2 extends JPanel
{

	private JFileChooser fileChooser;
	private File currentFile;
	private Scanner fileScanner;
	private TestController baseController;

	private JSpinner testSpinner;
	public int testNumber;

	private ArrayList<ArrayList<Object>> infoMatrix;
	private ArrayList<ArrayList<Object>> scaledMatrix;
	private ArrayList<ArrayList<Object>> deviationMatrix;
	private Object[][] tableMatrix;
	private ArrayList<Vector<Integer>> outlierList;

	// TextFields
	private JTextField testOneField;
	private JTextField testTwoField;
	private JTextField testThreeField;
	private JTextField testFourField;

	// Test Type selectors
	private JComboBox testOneType;
	private JComboBox testTwoType;
	private JComboBox testThreeType;
	private JComboBox testFourType;

	// Labels
	private JLabel testTypeLabel;
	private JLabel lowerScoreLabel;
	private JLabel upperScoreLabel;
	private JLabel testScanLabel;
	private JLabel outlierLabel;
	private JLabel fileNameLabel;

	private JLabel correlationLabel;
	private JLabel correlationLabel2;
	private JLabel correlationLabel3;
	private JLabel correlationLabel4;
	private JLabel correlationLabel5;
	private JLabel correlationLabel6;

	// Spinners (Lower Limit)
	private JSpinner testOneLowerSpinner;
	private JSpinner testTwoLowerSpinner;
	private JSpinner testThreeLowerSpinner;
	private JSpinner testFourLowerSpinner;

	// Spinners (Upper Limit)
	private JSpinner testOneUpperSpinner;
	private JSpinner testTwoUpperSpinner;
	private JSpinner testThreeUpperSpinner;
	private JSpinner testFourUpperSpinner;

	// Scroll Pane and Table
	private JTable table;
	private JScrollPane scrollPane;

	// Action Buttons
	private JButton outliersButton;
	private JButton fileButton;
	private JButton analyzeButton;
	private JButton scaleButton;
	private JButton corButton;
	private JButton oriButton;
	private JCheckBox quaBox;
	private JCheckBox tarBox;
	private JButton printButton;

	// Saver
	private JButton saveButton;
	private JFileChooser saveChooser;
	private String fileName;

	// Target Score
	private JLabel targetLabel;
	private JSpinner targetSpinner1;
	private JSpinner targetSpinner2;
	private JSpinner targetSpinner3;
	private JSpinner targetSpinner4;

	// Booleans
	private boolean isOneScaled;
	private boolean isTwoScaled;
	private boolean isThreeScaled;
	private boolean isFourScaled;
	private boolean isOriginal;
	private boolean hasOutliers;
	private boolean hasCorrelations;

	// Separators
	private JSeparator sepOne;
	private JSeparator sepTwo;

	// Outlier Coefficient Spinner
	private JSpinner outlierSpinner;

	public TestPanel2(TestController baseController)
	{
		super();
		this.baseController = baseController;

		fileChooser = new JFileChooser();
		currentFile = null;

		// Action Buttons
		fileButton = new JButton("File");
		analyzeButton = new JButton("Analyze");
		outliersButton = new JButton("Show Outliers");
		outliersButton.setEnabled(false);
		scaleButton = new JButton("Scale Tests");
		corButton = new JButton("Test Correlations");
		corButton.setEnabled(false);
		oriButton = new JButton("Show Original Scores");
		oriButton.setEnabled(false);
		quaBox = new JCheckBox("Show Quartiles");
		quaBox.setEnabled(false);
		tarBox = new JCheckBox("Target Score");
		tarBox.setEnabled(false);
		testSpinner = new JSpinner();
		printButton = new JButton("Print Table");
		

		// Saver
		saveButton = new JButton("Save Report");
		saveButton.setEnabled(false);
		saveChooser = new JFileChooser();
		saveChooser.setCurrentDirectory(new java.io.File("."));
		saveChooser.setDialogTitle("Choose where to save your test report");
		saveChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		saveChooser.setAcceptAllFileFilterUsed(false);
		fileName = new String("Test Report");

		// Target Score
		targetLabel = new JLabel("Average Score");
		targetSpinner1 = new JSpinner();
		targetSpinner1.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		targetSpinner1.setEnabled(false);
		targetSpinner2 = new JSpinner();
		targetSpinner2.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		targetSpinner2.setEnabled(false);
		targetSpinner3 = new JSpinner();
		targetSpinner3.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		targetSpinner3.setEnabled(false);
		targetSpinner4 = new JSpinner();
		targetSpinner4.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		targetSpinner4.setEnabled(false);

		// TestFields
		testOneField = new JTextField("Test One");
		testTwoField = new JTextField("Test Two");
		testThreeField = new JTextField("Test Three");
		testFourField = new JTextField("Test Four");

		// Test type selectors
		String[] choices = new String[] { "No Test", "Number Scale", "G. Reading Scale" };
		testOneType = new JComboBox(choices);
		testTwoType = new JComboBox(choices);
		testThreeType = new JComboBox(choices);
		testFourType = new JComboBox(choices);

		// Labels
		testTypeLabel = new JLabel("Test Type");
		lowerScoreLabel = new JLabel("Lowest possible score");
		upperScoreLabel = new JLabel("Highest possible score");
		testScanLabel = new JLabel("Tests to scan for:");
		outlierLabel = new JLabel("Outlier Coefficient");
		outlierLabel.setEnabled(false);
		fileNameLabel = new JLabel("Current File is: ");

		correlationLabel = new JLabel("");
		correlationLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		correlationLabel2 = new JLabel("");
		correlationLabel2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		correlationLabel3 = new JLabel("");
		correlationLabel3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		correlationLabel4 = new JLabel("");
		correlationLabel4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		correlationLabel5 = new JLabel("");
		correlationLabel5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		correlationLabel6 = new JLabel("");
		correlationLabel6.setFont(new Font("Tahoma", Font.PLAIN, 10));

		// Spinners (lower limit)
		testOneLowerSpinner = new JSpinner();
		testTwoLowerSpinner = new JSpinner();
		testThreeLowerSpinner = new JSpinner();
		testFourLowerSpinner = new JSpinner();

		// Spinners (Upper Limit)
		testOneUpperSpinner = new JSpinner();
		testOneUpperSpinner.setModel(new SpinnerNumberModel(new Integer(100), null, null, new Integer(1)));
		testTwoUpperSpinner = new JSpinner();
		testTwoUpperSpinner.setModel(new SpinnerNumberModel(new Integer(100), null, null, new Integer(1)));
		testThreeUpperSpinner = new JSpinner();
		testThreeUpperSpinner.setModel(new SpinnerNumberModel(new Integer(100), null, null, new Integer(1)));
		testFourUpperSpinner = new JSpinner();
		testFourUpperSpinner.setModel(new SpinnerNumberModel(new Integer(100), null, null, new Integer(1)));

		infoMatrix = new ArrayList<ArrayList<Object>>();
		scaledMatrix = new ArrayList<ArrayList<Object>>();
		outlierList = new ArrayList<Vector<Integer>>();

		// Booleans
		isOneScaled = false;
		isTwoScaled = false;
		isThreeScaled = false;
		isFourScaled = false;
		isOriginal = false;
		hasOutliers = false;
		hasCorrelations = false;

		// Separators
		sepOne = new JSeparator();
		sepTwo = new JSeparator();

		// Outlier Coefficient Spinner
		outlierSpinner = new JSpinner();
		outlierSpinner.setModel(new SpinnerNumberModel(new Double(2), new Double(0), null, new Double(0.1)));
		outlierSpinner.setEnabled(false);

		setupTable();
		setupPanel();
		setupLayout();
		setupListeners();

	}

	private void setupTable()
	{
		DefaultTableModel data = new DefaultTableModel(tableMatrix, new String[] { "Name", testOneField.getText(), testTwoField.getText(), testThreeField.getText(), testFourField.getText() })
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Class getColumnClass(int column)
			{
				return getValueAt(0, column).getClass();
			}
		};

		table = new JTable(data)
		{

			private static final long serialVersionUID = 1L;
			private Border outside = new MatteBorder(1, 0, 1, 0, Color.red);
			private Border inside = new EmptyBorder(0, 1, 0, 1);
			private Border highlight = new CompoundBorder(outside, inside);

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col)
			{

				Component comp = super.prepareRenderer(renderer, row, col);

				Vector<Integer> point = new Vector<Integer>();
				point.add(row);
				point.add(col);
				point.add(0);

				Vector<Integer> point2 = new Vector<Integer>();
				point2.add(row);
				point2.add(col);
				point2.add(1);

				Vector<Integer> point3 = new Vector<Integer>();
				point3.add(row);
				point3.add(col);
				point3.add(2);

				Vector<Integer> point4 = new Vector<Integer>();
				point4.add(row);
				point4.add(col);
				point4.add(3);

				if (outlierList.contains(point) && hasOutliers == true)
				{

					comp.setBackground(Color.RED);
					comp.setForeground(Color.BLACK);

				}
				else if (outlierList.contains(point2) && hasOutliers == true)
				{

					comp.setBackground(Color.BLUE.brighter());
					comp.setForeground(Color.WHITE);
				}
				else if (outlierList.contains(point3) && quaBox.isSelected() && hasOutliers == true)
				{
					comp.setBackground(Color.GREEN);
					comp.setForeground(Color.BLACK);
				}
				else if (outlierList.contains(point4) && quaBox.isSelected() && hasOutliers == true)
				{
					comp.setBackground(Color.YELLOW);
					comp.setForeground(Color.BLACK);

				}

				else
				{
					comp.setBackground(table.getBackground());
					comp.setForeground(Color.BLACK);
				}

				return comp;
			}

		};

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(30);
		table.getColumnModel().getColumn(0).setPreferredWidth(180);
		table.setMinimumSize(new Dimension(200, 200));
		table.setEnabled(false);
		table.setModel(data);

		scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	}

	private void setupPanel()
	{
		// this.add(analyzeButton);
		this.add(fileButton);
		this.add(testOneField);
		add(testTwoField);
		add(testThreeField);
		add(testFourField);
		add(testOneType);
		add(testTwoType);
		add(testThreeType);
		add(testFourType);
		add(testTypeLabel);
		add(testOneLowerSpinner);
		add(testTwoLowerSpinner);
		add(testThreeLowerSpinner);
		add(testFourLowerSpinner);
		add(lowerScoreLabel);
		add(testOneUpperSpinner);
		add(testTwoUpperSpinner);
		add(testThreeUpperSpinner);
		add(testFourUpperSpinner);
		add(upperScoreLabel);
		add(scrollPane);
		add(testScanLabel);
		add(outliersButton);
		add(scaleButton);
		add(corButton);
		add(correlationLabel);
		add(correlationLabel2);
		add(correlationLabel3);
		add(correlationLabel4);
		add(correlationLabel5);
		add(correlationLabel6);
		add(sepOne);
		add(sepTwo);
		add(outlierLabel);
		add(outlierSpinner);
		add(fileNameLabel);
		add(oriButton);
		add(quaBox);
		add(saveButton);
		add(targetLabel);
		add(targetSpinner1);
		add(targetSpinner2);
		add(targetSpinner3);
		add(targetSpinner4);
		add(tarBox);

		// add(table);

		add(testSpinner);
		testSpinner.setModel(new SpinnerNumberModel(4, 1, 4, 1));

	}

	private void setupLayout()
	{
		setLayout(null);
		fileButton.setBounds(209, 690, 123, 30);
		testOneField.setBounds(10, 38, 86, 20);
		testTwoField.setBounds(10, 69, 86, 20);
		testThreeField.setBounds(10, 100, 86, 20);
		testFourField.setBounds(10, 131, 86, 20);
		testOneType.setBounds(122, 38, 133, 20);
		testTwoType.setBounds(122, 69, 133, 20);
		testThreeType.setBounds(122, 100, 133, 20);
		testFourType.setBounds(122, 131, 133, 20);
		testTypeLabel.setBounds(122, 11, 55, 16);

		// Lower limit grouping
		testOneLowerSpinner.setBounds(291, 38, 86, 20);
		testTwoLowerSpinner.setBounds(291, 69, 86, 20);
		testThreeLowerSpinner.setBounds(291, 100, 86, 20);
		testFourLowerSpinner.setBounds(291, 131, 86, 20);
		lowerScoreLabel.setBounds(290, 11, 139, 16);

		// Upper limit grouping
		testOneUpperSpinner.setBounds(453, 38, 86, 20);
		testTwoUpperSpinner.setBounds(453, 69, 86, 20);
		testThreeUpperSpinner.setBounds(453, 100, 86, 20);
		testFourUpperSpinner.setBounds(453, 131, 86, 20);
		upperScoreLabel.setBounds(453, 11, 139, 16);

		// Target score grouping
		targetLabel.setBounds(612, 11, 139, 16);
		targetSpinner1.setBounds(612, 38, 86, 20);
		targetSpinner2.setBounds(612, 69, 86, 20);
		targetSpinner3.setBounds(612, 100, 86, 20);
		targetSpinner4.setBounds(612, 131, 86, 20);

		scrollPane.setBounds(369, 175, 505, 545);
		table.setBounds(30, 311, 147, 102);
		testSpinner.setBounds(147, 695, 29, 20);
		testScanLabel.setBounds(40, 698, 99, 14);
		analyzeButton.setBounds(291, 334, 123, 30);
		fileNameLabel.setBounds(10, 573, 322, 30);

		correlationLabel.setBounds(10, 327, 322, 30);
		correlationLabel2.setBounds(10, 368, 322, 30);
		correlationLabel3.setBounds(10, 409, 322, 30);
		correlationLabel4.setBounds(10, 450, 322, 30);
		correlationLabel5.setBounds(10, 491, 322, 30);
		correlationLabel6.setBounds(10, 532, 322, 30);

		// Button Grouping
		scaleButton.setBounds(10, 175, 146, 30);
		oriButton.setBounds(166, 175, 166, 30);
		sepOne.setBounds(10, 210, 292, 20);

		outliersButton.setBounds(10, 215, 146, 30);
		outlierLabel.setBounds(166, 215, 110, 30);
		outlierSpinner.setBounds(286, 220, 46, 20);
		quaBox.setBounds(10, 245, 146, 30);
		tarBox.setBounds(166, 245, 146, 30);
		sepTwo.setBounds(10, 281, 292, 20);

		corButton.setBounds(10, 286, 146, 30);
		saveButton.setBounds(209, 649, 123, 30);

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
					resetAllFields();
					fileNameLabel.setText("Current File is:    " + fileChooser.getSelectedFile().getName());

					testNumber = (int) testSpinner.getValue();

					currentFile = fileChooser.getSelectedFile();
					scanFile();

					// scaleTests(scaledMatrix);
				}
			}
		});
		
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				try {
				    boolean complete = table.print();
				    if (complete) {
				        /* show a success message  */
				        System.out.println("Print successful");
				    } else {
				        /*show a message indicating that printing was cancelled */
				        System.out.println("Print canceled");
				    }
				} catch (PrinterException pe) {
				    /* Printing failed, report to the user */
				    System.out.println("Print failed");
				}
			}
		});
//TODO fix this
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				if (hasCorrelations == true || testNumber == 1)
				{
					fileName = JOptionPane.showInputDialog("Name for the test report file");

					if (saveChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						System.out.println("getCurrentDirectory(): " + saveChooser.getCurrentDirectory());
						System.out.println("getSelectedFile() : " + saveChooser.getSelectedFile());
						writeFile(saveChooser.getSelectedFile());
					}
					else
					{
						System.out.println("No Selection ");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "There is no report to save.");
				}
				
				
			}
		});

		outliersButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (hasOutliers == false)
				{
					getOutliers();
					outliersButton.setText("Hide Outliers");
					hasOutliers = true;
				}
				else if (hasOutliers == true)
				{
					hasOutliers = false;
					outliersButton.setText("Show Outliers");
					quaBox.setSelected(false);
					tarBox.setSelected(false);
					setTable(new String[] { "Name", testOneField.getText(), testTwoField.getText(), testThreeField.getText(), testFourField.getText() });
				}
			}
		});

		quaBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				fillTable(scaledMatrix);
			}
		});

		tarBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				if(tarBox.isSelected())
				{
				targetSpinner1.setEnabled(true);
				targetSpinner2.setEnabled(true);
				targetSpinner3.setEnabled(true);
				targetSpinner4.setEnabled(true);
				getOutliers((Double) targetSpinner1.getValue(), (Double) targetSpinner2.getValue(), (Double) targetSpinner3.getValue(), (Double) targetSpinner4.getValue());
				}
				else
				{
					targetSpinner1.setEnabled(false);
					targetSpinner2.setEnabled(false);
					targetSpinner3.setEnabled(false);
					targetSpinner4.setEnabled(false);
					getOutliers();
				}
			}
		});

		scaleButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				scaleTests(scaledMatrix);
				fillTable(scaledMatrix);
				table.repaint();

				if (isOneScaled == true)
				{

					oriButton.setEnabled(true);
					outliersButton.setEnabled(true);
					corButton.setEnabled(true);
					quaBox.setEnabled(true);
					tarBox.setEnabled(true);
					outlierLabel.setEnabled(true);
					outlierSpinner.setEnabled(true);
				}
			}
		});

		oriButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (isOriginal == false)
				{
					showOriginalScores();
					oriButton.setText("Show Scaled scores");
					isOriginal = true;
				}
				else if (isOriginal == true)
				{
					fillTable(scaledMatrix);
					oriButton.setText("Show Original scores");
					isOriginal = false;
				}

			}
		});

		corButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (isOneScaled == true)
				{
					testCorrelations();
					hasCorrelations = true;
					saveButton.setEnabled(true);

				}
			}
		});

		testOneField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setTable(new String[] { "Name", testOneField.getText(), testTwoField.getText(), testThreeField.getText(), testFourField.getText() });
			}
		});

		testTwoField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setTable(new String[] { "Name", testOneField.getText(), testTwoField.getText(), testThreeField.getText(), testFourField.getText() });
			}
		});

		testThreeField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setTable(new String[] { "Name", testOneField.getText(), testTwoField.getText(), testThreeField.getText(), testFourField.getText() });
			}
		});

		testFourField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setTable(new String[] { "Name", testOneField.getText(), testTwoField.getText(), testThreeField.getText(), testFourField.getText() });
			}
		});

		ChangeListener testSpinnerListener = new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if ((int) testSpinner.getValue() == 1)
				{
					testFourType.setSelectedIndex(0);
					testThreeType.setSelectedIndex(0);
					testTwoType.setSelectedIndex(0);
				}
				if ((int) testSpinner.getValue() == 2)
				{
					testFourType.setSelectedIndex(0);
					testThreeType.setSelectedIndex(0);
				}
				if ((int) testSpinner.getValue() == 3)
				{
					testFourType.setSelectedIndex(0);
				}
			}
		};
		testSpinner.addChangeListener(testSpinnerListener);
		
		ChangeListener outlierSpinnerListener = new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if(tarBox.isSelected())
				{
					getOutliers((Double) targetSpinner1.getValue(), (Double) targetSpinner2.getValue(), (Double) targetSpinner3.getValue(), (Double) targetSpinner4.getValue());
				}
				else
				{
					getOutliers();
				}
			}
		};
		outlierSpinner.addChangeListener(outlierSpinnerListener);
		
		ChangeListener targetSpinnerListener = new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if(tarBox.isSelected())
				{
					getOutliers((Double) targetSpinner1.getValue(), (Double) targetSpinner2.getValue(), (Double) targetSpinner3.getValue(), (Double) targetSpinner4.getValue());
				}
				else
				{
					getOutliers();
				}
			}
		};

		targetSpinner1.addChangeListener(targetSpinnerListener);
		targetSpinner2.addChangeListener(targetSpinnerListener);
		targetSpinner3.addChangeListener(targetSpinnerListener);
		targetSpinner4.addChangeListener(targetSpinnerListener);

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
		scaledMatrix.clear();

		String tempString = new String();
		tempString = "";
		String delims = new String("[\t ]+");

		while (fileScanner.hasNextLine())
		{
			String tempoList[] = new String[10];

			ArrayList<Object> tempList = new ArrayList<Object>();
			tempList.add(null);
			tempList.add(null);
			tempList.add(null);
			tempList.add(null);
			tempList.add(null);

			ArrayList<Object> itempList = new ArrayList<Object>();
			itempList.add(null);
			itempList.add(null);
			itempList.add(null);
			itempList.add(null);
			itempList.add(null);

			tempString = fileScanner.nextLine();
			tempoList = tempString.split(delims);

			testNumber = (int) testSpinner.getValue();

			String name = "";
			for (int i = 0; i < tempoList.length - testNumber; i++)
			{
				name += " " + tempoList[i];
			}

			for (int i = testNumber, j = 1; i > 0 && j <= testNumber; i--, j++)
			{

				tempList.set(i, tempoList[tempoList.length - j]);
				itempList.set(i, tempoList[tempoList.length - j]);

			}
			/*
			 * tempList.set(4, tempoList[tempoList.length - 1]); tempList.set(3,
			 * tempoList[tempoList.length - 2]); tempList.set(2,
			 * tempoList[tempoList.length - 3]); tempList.set(1,
			 * tempoList[tempoList.length - 4]);
			 */
			tempList.set(0, name);
			itempList.set(0, name);

			infoMatrix.add(itempList);
			scaledMatrix.add(tempList);

		}

		checkFile();
		fillTable(infoMatrix);
		setTable(new String[] { "Name", testOneField.getText(), testTwoField.getText(), testThreeField.getText(), testFourField.getText() });

	}

	public void writeFile(File file)
	{

		try
		{
			PrintWriter writer = new PrintWriter(file + "\\" + fileName + ".txt", "UTF-8");
			if (testNumber == 4)
			{
				writer.println(correlationLabel.getText());
				writer.println(correlationLabel2.getText());
				writer.println(correlationLabel3.getText());
				writer.println(correlationLabel4.getText());
				writer.println(correlationLabel5.getText());
				writer.println(correlationLabel6.getText());
				/*writer.println("______________________________________________________________________");
				writer.println();
				writer.println("<html><bold>Name</bold>                         " + testOneField.getText() + "       " + testTwoField.getText() + "       " + testThreeField.getText() + "       " + testFourField.getText());
				writer.println();

				for (ArrayList<Object> current : infoMatrix)
				{
					String list = "";
					if (testNumber == 4)
					{
						String name = (String) current.get(0);
						String testOne = (String) " " + current.get(1);
						String testTwo = (String) " " + current.get(2);
						String testThree = (String) " " + current.get(3);
						String testFour = (String) " " + current.get(4);

						while (name.length() < 29)
						{
							name = name + " ";
						}
						while (testOne.length() < testOneField.getText().length() + 7)
						{
							testOne = testOne + " ";
						}
						while (testTwo.length() < testTwoField.getText().length() + 7)
						{
							testTwo = testTwo + " ";
						}
						while (testThree.length() < testThreeField.getText().length() + 7)
						{
							testThree = testThree + " ";
						}
						while (testFour.length() < testFourField.getText().length() + 7)
						{
							testFour = testFour + " ";
						}

						list = name + testOne + testTwo + testThree + testFour;
						writer.println(list);
					}
					if (testNumber == 3)
					{
						String name = (String) current.get(0);
						String testOne = (String) " " + current.get(1);
						String testTwo = (String) " " + current.get(2);
						String testThree = (String) " " + current.get(3);

						while (name.length() < 29)
						{
							name = name + " ";
						}
						while (testOne.length() < testOneField.getText().length() + 7)
						{
							testOne = testOne + " ";
						}
						while (testTwo.length() < testTwoField.getText().length() + 7)
						{
							testTwo = testTwo + " ";
						}
						while (testThree.length() < testThreeField.getText().length() + 7)
						{
							testThree = testThree + " ";
						}

						list = name + testOne + testTwo + testThree;
						writer.println(list);
					}
					if (testNumber == 2)
					{
						String name = (String) current.get(0);
						String testOne = (String) " " + current.get(1);
						String testTwo = (String) " " + current.get(2);

						while (name.length() < 29)
						{
							name = name + " ";
						}
						while (testOne.length() < testOneField.getText().length() + 7)
						{
							testOne = testOne + " ";
						}
						while (testTwo.length() < testTwoField.getText().length() + 7)
						{
							testTwo = testTwo + " ";
						}

						list = name + testOne + testTwo;
						writer.println(list);
					}
					if (testNumber == 1)
					{
						String name = (String) current.get(0);
						String testOne = (String) " " + current.get(1);

						while (name.length() < 29)
						{
							name = name + " ";
						}
						while (testOne.length() < testOneField.getText().length() + 7)
						{
							testOne = testOne + " ";
						}

						list = name + testOne;
						writer.println(list);
					}
					writer.println();

				}
*/
			}
			if (testNumber == 3)
			{
				writer.println(correlationLabel.getText());
				writer.println(correlationLabel2.getText());
				writer.println(correlationLabel3.getText());
			}
			if (testNumber == 2)
			{
				writer.println(correlationLabel.getText());
			}
			if (testNumber == 1)
			{

			}
			writer.close();
			System.out.println("File has been written");

		}
		catch (Exception e)
		{
			System.out.println("Could not create file");
		}
	}

	private void fillTable(ArrayList<ArrayList<Object>> testList)
	{
		tableMatrix = new Object[testList.size()][testList.get(0).size()];

		for (int row = 0; row < tableMatrix.length; row++)
		{
			for (int col = 0; col < tableMatrix[0].length; col++)
			{
				tableMatrix[row][col] = testList.get(row).get(col);
			}
		}

		setTable(new String[] { "Name", testOneField.getText(), testTwoField.getText(), testThreeField.getText(), testFourField.getText() });
	}

	private void setTable(String[] header)
	{

		DefaultTableModel data = new DefaultTableModel(tableMatrix, header);
		table.setModel(data);
		table.getColumnModel().getColumn(0).setPreferredWidth(180);

		table.repaint();
	}

	private void checkFile()
	{
		try
		{
			for (ArrayList<Object> current : infoMatrix)
			{
				if (testOneType.getSelectedItem().equals("Number Scale"))
				{
					Double.parseDouble((String) current.get(1));
				}
				if (testTwoType.getSelectedItem().equals("Number Scale"))
				{
					Double.parseDouble((String) current.get(2));
				}
				if (testThreeType.getSelectedItem().equals("Number Scale"))
				{
					Double.parseDouble((String) current.get(3));
				}
				if (testFourType.getSelectedItem().equals("Number Scale"))
				{
					Double.parseDouble((String) current.get(4));
				}

			}

		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Error detected in File. Please Check test score file " + e.getMessage());

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
			double one = (Double) current.get(current.size() - 1 - testNumber + testOne);
			double two = (Double) current.get(current.size() - 1 - testNumber + testTwo);
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
	 * 
	 * @param score
	 *            Score received on test
	 * @param lower
	 *            Lowest possible Score on test
	 * @param upper
	 *            Highest possible score on test
	 * @return New score one the 0-100 scale
	 */
	public String scale(Double score, double lower, double upper)
	{
		double scaledScore = 0;
		double range = upper - lower;
		double percent = score / range;

		scaledScore = (Double) (((int) (percent * 100)) * 1.0);

		return scaledScore + "";

	}

	private void scaleTests(ArrayList<ArrayList<Object>> testList)
	{
		boolean scaling = false;

		for (ArrayList<Object> current : testList)
		{

			if (isOneScaled == false)
			{
				if (testOneType.getSelectedItem().equals((String) "Number Scale"))
				{
					current.set(1, (scale(Double.parseDouble((String) current.get(1)), (int) testOneLowerSpinner.getValue(), (int) testOneUpperSpinner.getValue())));
					scaling = true;

				}
				else if (testOneType.getSelectedItem().equals((String) "G. Reading Scale"))
				{
					current.set(1, scaleAlphabet((String) current.get(1)));
					scaling = true;

				}
			}

			if (isTwoScaled == false && testNumber >= 2)
			{
				if (testTwoType.getSelectedItem().equals((String) "Number Scale"))
				{
					current.set(2, (scale(Double.parseDouble((String) current.get(2)), (int) testTwoLowerSpinner.getValue(), (int) testTwoUpperSpinner.getValue())));
					scaling = true;

				}
				else if (testTwoType.getSelectedItem().equals((String) "G. Reading Scale"))
				{
					current.set(2, scaleAlphabet((String) current.get(2)));
					scaling = true;

				}
			}

			if (isThreeScaled == false && testNumber >= 3)
			{
				if (testThreeType.getSelectedItem().equals((String) "Number Scale"))
				{
					current.set(3, (scale(Double.parseDouble((String) current.get(3)), (int) testThreeLowerSpinner.getValue(), (int) testThreeUpperSpinner.getValue())));
					scaling = true;

				}
				else if (testThreeType.getSelectedItem().equals((String) "G. Reading Scale"))
				{
					current.set(3, scaleAlphabet((String) current.get(3)));
					scaling = true;

				}
			}

			if (isFourScaled == false && testNumber >= 4)
			{
				if (testFourType.getSelectedItem().equals((String) "Number Scale"))
				{
					current.set(4, (scale(Double.parseDouble((String) current.get(4)), (int) testFourLowerSpinner.getValue(), (int) testFourUpperSpinner.getValue())));
					scaling = true;

				}
				else if (testFourType.getSelectedItem().equals((String) "G. Reading Scale"))
				{
					current.set(4, scaleAlphabet((String) current.get(4)));
					scaling = true;

				}
			}

		}

		if (scaling == true)
		{
			isOneScaled = true;
			isTwoScaled = true;
			isThreeScaled = true;
			isFourScaled = true;
		}

	}

	private void getOutliers()
	{
		ArrayList<Object> testOneList = new ArrayList<Object>();
		ArrayList<Object> testTwoList = new ArrayList<Object>();
		ArrayList<Object> testThreeList = new ArrayList<Object>();
		ArrayList<Object> testFourList = new ArrayList<Object>();
		outlierList.clear();

		double outlierCoefficient = (double) this.outlierSpinner.getValue();

		for (ArrayList<Object> current : scaledMatrix)
		{
			if (testOneType.getSelectedItem().equals("Number Scale"))
			{
				testOneList.add(current.get(1));

			}
			else if (isOneScaled == true && testOneType.getSelectedIndex() != 0)
			{
				testOneList.add(current.get(1));
			}

			if (testTwoType.getSelectedItem().equals("Number Scale"))
			{
				testTwoList.add(current.get(2));
			}
			else if (isTwoScaled == true && testTwoType.getSelectedIndex() != 0)
			{
				testTwoList.add(current.get(2));
			}

			if (testThreeType.getSelectedItem().equals("Number Scale"))
			{
				testThreeList.add(current.get(3));
			}
			else if (isThreeScaled == true && testThreeType.getSelectedIndex() != 0)
			{
				testThreeList.add(current.get(3));
			}

			if (testFourType.getSelectedItem().equals("Number Scale"))
			{
				testFourList.add(current.get(4));
			}
			else if (isFourScaled == true && testFourType.getSelectedIndex() != 0)
			{
				testFourList.add(current.get(4));
			}

		}

		if (testOneType.getSelectedItem().equals("Number Scale") || isOneScaled == true)
		{
			Test testOne = new Test(testOneList);
			targetSpinner1.setValue(testOne.getAverageScore());
			for (Vector<Integer> index : testOne.getOutliers(outlierCoefficient))
			{
				index.insertElementAt(1, 1);

				outlierList.add(index);
			}

		}
		if (testTwoType.getSelectedItem().equals("Number Scale") || isTwoScaled == true && testNumber >= 2)
		{
			Test testTwo = new Test(testTwoList);
			targetSpinner2.setValue(testTwo.getAverageScore());
			for (Vector<Integer> index : testTwo.getOutliers(outlierCoefficient))
			{
				index.insertElementAt(2, 1);

				outlierList.add(index);
			}

		}
		if (testThreeType.getSelectedItem().equals("Number Scale") || isThreeScaled == true && testNumber >= 3)
		{
			Test testThree = new Test(testThreeList);
			targetSpinner3.setValue(testThree.getAverageScore());
			for (Vector<Integer> index : testThree.getOutliers(outlierCoefficient))
			{
				index.insertElementAt(3, 1);

				outlierList.add(index);
			}

		}
		if (testFourType.getSelectedItem().equals("Number Scale") || isFourScaled == true && testNumber >= 4)
		{
			Test testFour = new Test(testFourList);
			targetSpinner4.setValue(testFour.getAverageScore());
			for (Vector<Integer> index : testFour.getOutliers(outlierCoefficient))
			{
				index.insertElementAt(4, 1);

				outlierList.add(index);
			}

		}

		table.repaint();
	}

	private void getOutliers(double testOneTarget, double testTwoTarget, double testThreeTarget, double testFourTarget)
	{
		ArrayList<Object> testOneList = new ArrayList<Object>();
		ArrayList<Object> testTwoList = new ArrayList<Object>();
		ArrayList<Object> testThreeList = new ArrayList<Object>();
		ArrayList<Object> testFourList = new ArrayList<Object>();
		outlierList.clear();
		double outlierCoefficient = (double) this.outlierSpinner.getValue();

		for (ArrayList<Object> current : scaledMatrix)
		{
			if (testOneType.getSelectedItem().equals("Number Scale"))
			{
				testOneList.add(current.get(1));

			}
			else if (isOneScaled == true && testOneType.getSelectedIndex() != 0)
			{
				testOneList.add(current.get(1));
			}

			if (testTwoType.getSelectedItem().equals("Number Scale"))
			{
				testTwoList.add(current.get(2));
			}
			else if (isTwoScaled == true && testTwoType.getSelectedIndex() != 0)
			{
				testTwoList.add(current.get(2));
			}

			if (testThreeType.getSelectedItem().equals("Number Scale"))
			{
				testThreeList.add(current.get(3));
			}
			else if (isThreeScaled == true && testThreeType.getSelectedIndex() != 0)
			{
				testThreeList.add(current.get(3));
			}

			if (testFourType.getSelectedItem().equals("Number Scale"))
			{
				testFourList.add(current.get(4));
			}
			else if (isFourScaled == true && testFourType.getSelectedIndex() != 0)
			{
				testFourList.add(current.get(4));
			}

		}

		if (testOneType.getSelectedItem().equals("Number Scale") || isOneScaled == true)
		{
			Test testOne = new Test(testOneList);
			for (Vector<Integer> index : testOne.getOutliers(outlierCoefficient, testOneTarget))
			{
				index.insertElementAt(1, 1);

				outlierList.add(index);
			}

		}
		if (testTwoType.getSelectedItem().equals("Number Scale") || isTwoScaled == true && testNumber >= 2)
		{
			Test testTwo = new Test(testTwoList);
			for (Vector<Integer> index : testTwo.getOutliers(outlierCoefficient, testTwoTarget))
			{
				index.insertElementAt(2, 1);

				outlierList.add(index);
			}

		}
		if (testThreeType.getSelectedItem().equals("Number Scale") || isThreeScaled == true && testNumber >= 3)
		{
			Test testThree = new Test(testThreeList);
			for (Vector<Integer> index : testThree.getOutliers(outlierCoefficient, testThreeTarget))
			{
				index.insertElementAt(3, 1);

				outlierList.add(index);
			}

		}
		if (testFourType.getSelectedItem().equals("Number Scale") || isFourScaled == true && testNumber >= 4)
		{
			Test testFour = new Test(testFourList);
			for (Vector<Integer> index : testFour.getOutliers(outlierCoefficient, testFourTarget))
			{
				index.insertElementAt(4, 1);

				outlierList.add(index);
			}

		}

		table.repaint();
	}

	public String scaleAlphabet(String letter)
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

		double percent = score / 26;

		double scaledScore = (Double) (((int) (percent * 100)) * 1.0);

		return scaledScore + "";
	}

	public void showOriginalScores()
	{
		fillTable(infoMatrix);

	}

	private void testCorrelations()
	{
		double[] testOneArray = new double[scaledMatrix.size()];
		double[] testTwoArray = new double[scaledMatrix.size()];
		double[] testThreeArray = new double[scaledMatrix.size()];
		double[] testFourArray = new double[scaledMatrix.size()];

		for (int i = 0; i < scaledMatrix.size() - 1; i++)
		{
			if (testNumber == 4)
			{
				testOneArray[i] = Double.parseDouble((String) scaledMatrix.get(i).get(scaledMatrix.get(i).size() - 4));
				testTwoArray[i] = Double.parseDouble((String) scaledMatrix.get(i).get(scaledMatrix.get(i).size() - 3));
				testThreeArray[i] = Double.parseDouble((String) scaledMatrix.get(i).get(scaledMatrix.get(i).size() - 2));
				testFourArray[i] = Double.parseDouble((String) scaledMatrix.get(i).get(scaledMatrix.get(i).size() - 1));
			}
			if (testNumber == 3)
			{
				testOneArray[i] = Double.parseDouble((String) scaledMatrix.get(i).get(scaledMatrix.get(i).size() - 4));
				testTwoArray[i] = Double.parseDouble((String) scaledMatrix.get(i).get(scaledMatrix.get(i).size() - 3));
				testThreeArray[i] = Double.parseDouble((String) scaledMatrix.get(i).get(scaledMatrix.get(i).size() - 2));
			}
			if (testNumber == 2)
			{
				testOneArray[i] = Double.parseDouble((String) scaledMatrix.get(i).get(scaledMatrix.get(i).size() - 4));
				testTwoArray[i] = Double.parseDouble((String) scaledMatrix.get(i).get(scaledMatrix.get(i).size() - 3));
			}

		}

		if (testNumber == 4)
		{
			int corr1 = (int) (new PearsonsCorrelation().correlation(testOneArray, testTwoArray) * 100);
			int corr2 = (int) (new PearsonsCorrelation().correlation(testOneArray, testThreeArray) * 100);
			int corr3 = (int) (new PearsonsCorrelation().correlation(testTwoArray, testThreeArray) * 100);
			int corr4 = (int) (new PearsonsCorrelation().correlation(testOneArray, testFourArray) * 100);
			int corr5 = (int) (new PearsonsCorrelation().correlation(testTwoArray, testFourArray) * 100);
			int corr6 = (int) (new PearsonsCorrelation().correlation(testThreeArray, testFourArray) * 100);

			correlationLabel.setText("The correlation for " + testOneField.getText() + " and " + testTwoField.getText() + " is: " + corr1 + "%");
			correlationLabel2.setText("The correlation for " + testOneField.getText() + " and " + testThreeField.getText() + " is: " + corr2 + "%");
			correlationLabel3.setText("The correlation for " + testTwoField.getText() + " and " + testThreeField.getText() + " is: " + corr3 + "%");
			correlationLabel4.setText("The correlation for " + testOneField.getText() + " and " + testFourField.getText() + " is: " + corr4 + "%");
			correlationLabel5.setText("The correlation for " + testTwoField.getText() + " and " + testFourField.getText() + " is: " + corr5 + "%");
			correlationLabel6.setText("The correlation for " + testThreeField.getText() + " and " + testFourField.getText() + " is: " + corr6 + "%");
		}
		if (testNumber == 3)
		{
			int corr1 = (int) (new PearsonsCorrelation().correlation(testOneArray, testTwoArray) * 100);
			int corr2 = (int) (new PearsonsCorrelation().correlation(testOneArray, testThreeArray) * 100);
			int corr3 = (int) (new PearsonsCorrelation().correlation(testTwoArray, testThreeArray) * 100);

			correlationLabel.setText("The correlation for " + testOneField.getText() + " and " + testTwoField.getText() + " is: " + corr1 + "%");
			correlationLabel2.setText("The correlation for " + testOneField.getText() + " and " + testThreeField.getText() + " is: " + corr2 + "%");
			correlationLabel3.setText("The correlation for " + testTwoField.getText() + " and " + testThreeField.getText() + " is: " + corr3 + "%");

		}
		if (testNumber == 2)
		{
			int corr1 = (int) (new PearsonsCorrelation().correlation(testOneArray, testTwoArray) * 100);

			correlationLabel.setText("The correlation for " + testOneField.getText() + " and " + testTwoField.getText() + " is: " + corr1 + "%");
		}
		if (testNumber == 1)
		{
			JOptionPane.showMessageDialog(null, "A Correlation cannot be calculated with only one test");
		}

	}

	private void resetAllFields()
	{
		infoMatrix.clear();
		scaledMatrix.clear();
		outlierList.clear();
		tableMatrix = null;
		isOneScaled = false;
		isTwoScaled = false;
		isThreeScaled = false;
		isFourScaled = false;
		correlationLabel.setText("");
		correlationLabel2.setText("");
		correlationLabel3.setText("");
		correlationLabel4.setText("");
		correlationLabel5.setText("");
		correlationLabel6.setText("");
		targetLabel.setText("Average score");
		oriButton.setEnabled(false);
		corButton.setEnabled(false);
		outliersButton.setEnabled(false);
		quaBox.setEnabled(false);
		tarBox.setEnabled(false);
		outlierLabel.setEnabled(false);
		outlierSpinner.setEnabled(false);
		saveButton.setEnabled(false);
		targetSpinner1.setEnabled(false);
		targetSpinner2.setEnabled(false);
		targetSpinner3.setEnabled(false);
		targetSpinner4.setEnabled(false);
	}

	private Vector<Integer> compareTests(ArrayList<Object> test)
	{
		Vector<Integer> tests = new Vector<Integer>();

		if (testNumber == 4)
		{
			double sum = Math.abs(Double.parseDouble((String) test.get(test.size() - 4)) - Double.parseDouble((String) test.get(test.size() - 3)));
			double comp1 = Math.abs(Double.parseDouble((String) test.get(test.size() - 4)) - Double.parseDouble((String) test.get(test.size() - 3)));
			double comp2 = Math.abs(Double.parseDouble((String) test.get(test.size() - 4)) - Double.parseDouble((String) test.get(test.size() - 2)));
			double comp3 = Math.abs(Double.parseDouble((String) test.get(test.size() - 4)) - Double.parseDouble((String) test.get(test.size() - 1)));
			double comp4 = Math.abs(Double.parseDouble((String) test.get(test.size() - 3)) - Double.parseDouble((String) test.get(test.size() - 2)));
			double comp5 = Math.abs(Double.parseDouble((String) test.get(test.size() - 3)) - Double.parseDouble((String) test.get(test.size() - 1)));
			double comp6 = Math.abs(Double.parseDouble((String) test.get(test.size() - 2)) - Double.parseDouble((String) test.get(test.size() - 1)));
			double[] comps = new double[] { comp1, comp2, comp3, comp4, comp5, comp6 };

			for (int i = 0; i < 5; i++)
			{
				if (comps[i] <= sum)
				{
					sum = comps[i];
				}
			}

			if (sum == comp1)
			{
				tests.add(1);
				tests.add(2);

			}
			if (sum == comp2)
			{
				tests.add(1);
				tests.add(3);

			}
			if (sum == comp3)
			{
				tests.add(1);
				tests.add(4);

			}
			if (sum == comp4)
			{
				tests.add(2);
				tests.add(3);

			}
			if (sum == comp5)
			{
				tests.add(2);
				tests.add(4);

			}
			if (sum == comp6)
			{
				tests.add(3);
				tests.add(4);

			}

		}
		if (testNumber == 3)
		{
			double sum = Math.abs(Double.parseDouble((String) test.get(test.size() - 4)) - Double.parseDouble((String) test.get(test.size() - 3)));
			double comp1 = Math.abs(Double.parseDouble((String) test.get(test.size() - 4)) - Double.parseDouble((String) test.get(test.size() - 3)));
			double comp2 = Math.abs(Double.parseDouble((String) test.get(test.size() - 4)) - Double.parseDouble((String) test.get(test.size() - 2)));
			double comp3 = Math.abs(Double.parseDouble((String) test.get(test.size() - 3)) - Double.parseDouble((String) test.get(test.size() - 2)));

			double[] comps = new double[] { comp1, comp2, comp3, };

			for (int i = 0; i < 5; i++)
			{
				if (comps[i] <= sum)
				{
					sum = comps[i];
				}
			}

			if (sum == comp1)
			{
				tests.add(1);
				tests.add(2);

			}
			if (sum == comp2)
			{
				tests.add(1);
				tests.add(3);

			}
			if (sum == comp3)
			{
				tests.add(2);
				tests.add(3);

			}

		}
		if (testNumber == 2)
		{
			tests.add(1);
			tests.add(2);
		}

		return tests;
	}
}
